/**
 * 
 */
package eu.cec.digit.circabc.service.config.auto.upload;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronExpression;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import eu.cec.digit.circabc.repo.config.auto.upload.Configuration;
import eu.cec.digit.circabc.repo.lock.DBLockServiceImpl;
import eu.cec.digit.circabc.service.ftp.SimpleFtpClient;
import eu.cec.digit.circabc.service.ftp.SimpleFtpClientImpl;

/**
 * @author beaurpi
 *
 */
public class AutoUploadJobListener implements Job {

	private DBLockServiceImpl circabcLockService;
	private AutoUploadManagementService autoUploadManagementService;
	private SimpleFtpClient ftpClient;
	
	/** A logger for the class */
	final static Log logger = LogFactory.getLog(AutoUploadJobListener.class);
	
	private static final String FILE_PROCESSED = ".old";

	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		AuthenticationUtil.setRunAsUserSystem();
		
		JobDataMap jobData = context.getJobDetail().getJobDataMap();
		
		setCircabcLockService((DBLockServiceImpl) jobData.get("circabcLockService"));
		setAutoUploadManagementService((AutoUploadManagementService) jobData.get("autoUploadManagementService"));
		
		try {
			
			
			List<Configuration> allConfigurations = autoUploadManagementService.listAllConfigurations();
			for(Configuration conf: allConfigurations)
			{
				/*
				 * 1 = lock successful
				 * 0 = already locked
				 */
				Integer lock = autoUploadManagementService.lockJobFile(conf.getIdConfiguration());
				
				if(conf.getStatus()==1 && lock==1)
				{
					CronExpression cExp = new CronExpression(conf.getDateRestriction());
					Boolean result = cExp.isSatisfiedBy(new Date());
					
					
					
					/*
					 * All ok to start looking for update
					 */
					if(result)
					{
						NodeRef fileRef =null;
						
						if(conf.getFileNodeRef() != null)
						{
							fileRef = new NodeRef(conf.getFileNodeRef());
						}
						else
						{
							fileRef = new NodeRef(conf.getParentNodeRef());
						}
						
						if(autoUploadManagementService.documentExists(fileRef))
						{
							/*
							 * -2 FTP Error
							 * -1 error
							 * 0 no update
							 * 1 success update
							 */
							AutoUploadJobResult jobResult = AutoUploadJobResult.JOB_NOTHING_TO_DO;
							
							
							
							ftpClient = new SimpleFtpClientImpl();
							String fileName="";
							File tmpFile =null;
							
							try {
														
								ftpClient.initParameters(conf.getFtpHost(), conf.getFtpPort(), conf.getFtpUsername(), conf.getFtpPassword(), conf.getFtpPath());
								fileName = ftpClient.getFileName();
								
								if(ftpClient.fileExists(fileName))
								{
									tmpFile = ftpClient.downloadFile(fileName);
									
									
									if(conf.getFileNodeRef() != null)
									{
										autoUploadManagementService.updateContent(fileRef, tmpFile);
										
										if(conf.getAutoExtract())
										{
											autoUploadManagementService.extractZip(fileRef);
										}
									}
									else //file not yet createde, so no update -> create content and update configuration
									{
										NodeRef resultRef = autoUploadManagementService.createContent(fileRef, tmpFile, new NodeRef(conf.getParentNodeRef()) , fileName);
										conf.setFileNodeRef(resultRef.toString());
										autoUploadManagementService.updateConfiguration(conf);
										
										if(conf.getAutoExtract())
										{
											autoUploadManagementService.extractZip(resultRef);
										}
									}
									
									
									
									ftpClient.renameRemoteFile(fileName, fileName+FILE_PROCESSED);
									
									jobResult = AutoUploadJobResult.JOB_OK;
								}
								
							} catch (IllegalStateException e) {
								if(logger.isErrorEnabled())
								{
									logger.error("Error during execution of FTP autoupload for file:"+fileName+" noderef:"+fileRef, e);
								}
								jobResult = AutoUploadJobResult.JOB_ERROR;
							} catch (IOException e) {
								
								if(e.getClass().equals(java.net.ConnectException.class))
								{
									if(logger.isErrorEnabled())
									{
										logger.error("Error during execution of FTP : connection problem, is FTP up and running ?", e);
									}
									jobResult = AutoUploadJobResult.JOB_REMOTE_FTP_PROBLEM;
								}
								else
								{
									if(logger.isErrorEnabled())
									{
										logger.error("Error during execution of FTP :File IO: autoupload for file:"+fileName+" noderef:"+fileRef, e);
									}
									jobResult = AutoUploadJobResult.JOB_ERROR;
								}
								
								
								
							} catch (FTPIllegalReplyException e) {
								if(logger.isErrorEnabled())
								{
									logger.error("Error during execution of FTP :bad reply from ftp server: autoupload for file:"+fileName+" noderef:"+fileRef, e);
								}
								jobResult = AutoUploadJobResult.JOB_ERROR;
								
							} catch (FTPException e) {
								if(logger.isErrorEnabled())
								{
									logger.error("Error during execution of FTP autoupload for file:"+fileName+" noderef:"+fileRef, e);
								}
								jobResult = AutoUploadJobResult.JOB_ERROR;
								
							} catch (FTPDataTransferException e) {
								if(logger.isErrorEnabled())
								{
									logger.error("Error during execution of FTP :download problem: autoupload for file:"+fileName+" noderef:"+fileRef, e);
								}
								jobResult = AutoUploadJobResult.JOB_ERROR;
								
							} catch (FTPAbortedException e) {
								if(logger.isErrorEnabled())
								{
									logger.error("Error during execution of FTP :aborted process: autoupload for file:"+fileName+" noderef:"+fileRef, e);
								}
								jobResult = AutoUploadJobResult.JOB_ERROR;
								
							} catch (FTPListParseException e) {
								if(logger.isErrorEnabled())
								{
									logger.error("Error during execution of FTP :problem for list files: autoupload for file:"+fileName+" noderef:"+fileRef, e);
								}
								jobResult = AutoUploadJobResult.JOB_ERROR;
								
							}
							finally
							{
								ftpClient.logout();
								
								if(tmpFile != null)
								{
									boolean isDeleted = tmpFile.delete();
									if (!isDeleted && logger.isWarnEnabled())
							         {
							      	   try {
											logger.warn ("Unable to delete file : " + tmpFile.getCanonicalPath() ) ;
										} catch (IOException e) {
											logger.warn ("Unable to get CanonicalPath for : " + tmpFile.getPath() ,e ) ;
										}
							         }
								}
								
								if(conf.getJobNotifications())
								{
									if(jobResult.equals(AutoUploadJobResult.JOB_OK))
									{
										this.autoUploadManagementService.sendJobNofitication(conf, AutoUploadJobResult.JOB_OK);
									}
									else if(jobResult.equals(AutoUploadJobResult.JOB_ERROR))
									{
										this.autoUploadManagementService.sendJobNofitication(conf, AutoUploadJobResult.JOB_ERROR);
									}
									else if(jobResult.equals(AutoUploadJobResult.JOB_REMOTE_FTP_PROBLEM))
									{
										this.autoUploadManagementService.sendJobNofitication(conf, AutoUploadJobResult.JOB_REMOTE_FTP_PROBLEM);
									}
								}
								
								if(jobResult.equals(AutoUploadJobResult.JOB_OK))
								{
									this.autoUploadManagementService.logJobResult(conf, AutoUploadJobResult.JOB_OK);
								}
								else if(jobResult.equals(AutoUploadJobResult.JOB_ERROR))
								{
									this.autoUploadManagementService.logJobResult(conf, AutoUploadJobResult.JOB_ERROR);
								}
								else if(jobResult.equals(AutoUploadJobResult.JOB_REMOTE_FTP_PROBLEM))
								{
									this.autoUploadManagementService.logJobResult(conf, AutoUploadJobResult.JOB_REMOTE_FTP_PROBLEM);
								}
								
								
							}
						}
						else
						{
							/*
							 * remove configuration as file do not exist anymore
							 */
							conf.setStatus(-1);
							autoUploadManagementService.updateConfiguration(conf);
						}
						
							
					}
				}
				
				Integer unlock = autoUploadManagementService.unlockJobFile(conf.getIdConfiguration());
			}
			
		} catch (SQLException e) {
			
			if(logger.isErrorEnabled())
			{
				logger.error("Error during fetching all configiration from autoupload DB", e);
			}
		} catch (ParseException e) {
			
			if(logger.isErrorEnabled())
			{
				logger.error("Error during parsing dateRestriction field", e);
			}
		}
		finally
		{
			AuthenticationUtil.clearCurrentSecurityContext();
		}

	}

	/**
	 * @return the circabcLockService
	 */
	public DBLockServiceImpl getCircabcLockService() {
		return circabcLockService;
	}

	/**
	 * @param circabcLockService the circabcLockService to set
	 */
	public void setCircabcLockService(DBLockServiceImpl circabcLockService) {
		this.circabcLockService = circabcLockService;
	}

	/**
	 * @return the autoUploadManagementService
	 */
	public AutoUploadManagementService getAutoUploadManagementService() {
		return autoUploadManagementService;
	}

	/**
	 * @param autoUploadManagementService the autoUploadManagementService to set
	 */
	public void setAutoUploadManagementService(
			AutoUploadManagementService autoUploadManagementService) {
		this.autoUploadManagementService = autoUploadManagementService;
	}


	/**
	 * @return the ftpClient
	 */
	public SimpleFtpClient getFtpClient() {
		return ftpClient;
	}

	/**
	 * @param ftpClient the ftpClient to set
	 */
	public void setFtpClient(SimpleFtpClient ftpClient) {
		this.ftpClient = ftpClient;
	}
}
