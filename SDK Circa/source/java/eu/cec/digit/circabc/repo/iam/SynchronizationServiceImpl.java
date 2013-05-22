package eu.cec.digit.circabc.repo.iam;

import java.sql.SQLException;
import java.util.Set;

import javax.mail.MessagingException;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.task.TaskExecutor;

import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.service.iam.IamWSClient;
import eu.cec.digit.circabc.service.iam.SynchronizationService;
import eu.cec.digit.circabc.service.log.LogService;
import eu.cec.digit.circabc.service.mail.MailService;

public class SynchronizationServiceImpl implements SynchronizationService {
	
	
	private static final String SUBJECT_MAIL_TEMPLATE = "Did not succeed to synchronize CIRCABC with IAM operation : %1$s ,userID : %2$s , themaID : %3$s , roleID: %4$s";
	private static final String  BODY_MAIL_TEMPLATE = "Did not succeed to synchronize CIRCABC with IAM operation : %1$s ,userID : %2$s , themaID : %3$s , roleID:  %4$s . %n Please do it manualy. %n Exception details : %5$s ";
	
	
	private static final Log logger = LogFactory.getLog(SynchronizationServiceImpl.class);
	
	private static final String DEFAULT_ECORDA_ROLE = "THEME_MEMBER";
	
	private NodeService nodeService;
	
	private MailService mailService;
	
	private LogService logService;
	
	private TaskExecutor taskExecutor;
	
	private IamWSClient iamWSClient;
	
	private EcordaDaoServiceImpl ecordaDaoServiceImpl;
	
	private String emailErrorAdress; 
	
	private static final int MAX_RETRIES = 5;  
	
	private static final int RETRY_PAUSE_IN_MILISECONDS  = 120000;
	
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public NodeService getNodeService() {
		return nodeService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public LogService getLogService() {
		return logService;
	}
	
	

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setIamWSClient(IamWSClient iamWSClient) {
		this.iamWSClient = iamWSClient;
	}

	public IamWSClient getIamWSClient() {
		return iamWSClient;
	}

	public void setEcordaDaoServiceImpl(EcordaDaoServiceImpl ecordaDaoServiceImpl) {
		this.ecordaDaoServiceImpl = ecordaDaoServiceImpl;
	}

	public EcordaDaoServiceImpl getEcordaDaoServiceImpl() {
		return ecordaDaoServiceImpl;
	}

	public void setEmailErrorAdress(String emailErrorAdress) {
		this.emailErrorAdress = emailErrorAdress;
	}

	public String getEmailErrorAdress() {
		return emailErrorAdress;
	}

	@Override
	public void grantThemeRole(final String userName, final String themeID) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				boolean isError = false;
				Exception lastException = null;
				for (int i = 0; i < MAX_RETRIES; i++) {
					try {
						iamWSClient.grantThemeRole(userName, themeID, DEFAULT_ECORDA_ROLE);
						return;
					} catch (Exception e) {
						try {
							isError =true;
							lastException = e;
							Thread.sleep(RETRY_PAUSE_IN_MILISECONDS);
						} catch (InterruptedException e1) {
						}

					}

				}
				if (isError)
				{
					sendEmail(userName,themeID,DEFAULT_ECORDA_ROLE,"grantThemeRole", lastException);
				}

			}

		});

	}

	protected void sendEmail(String userName, String themeID, String roleID, String operation, Exception lastException) {
		String from = mailService.getNoReplyEmailAddress() ;
		String subject =  String.format(SUBJECT_MAIL_TEMPLATE,operation,userName,themeID,roleID );
		String body = String.format(BODY_MAIL_TEMPLATE,operation,userName,themeID,roleID ,lastException.getMessage());
		  		try 
  		{
			mailService.send(from,emailErrorAdress,null,subject,body,false);
		} 
  		catch (MessagingException e) 
  		{
  			if(logger.isErrorEnabled())
			{
				logger.error("Error when sending email to user that import is finished", e);
			}
		}
		
	}

	@Override
	public void revokeThemeRole(final String userName, final String themeID) {
		
		
		taskExecutor.execute(new Runnable()
		{
			public void run()
			{
				Exception lastException = null;
				boolean isError = false;
				for (int i = 0; i < MAX_RETRIES; i++) {
					try {
						iamWSClient.revokeThemeRole(userName, themeID, DEFAULT_ECORDA_ROLE);
						return;
					} catch (Exception e) {
						try {
							isError =true;
							lastException = e;
							Thread.sleep(RETRY_PAUSE_IN_MILISECONDS);
						} catch (InterruptedException e1) {
						}

					}
				}
				if (isError)
				{
					sendEmail(userName,themeID,DEFAULT_ECORDA_ROLE,"revokeThemeRole",lastException);
				}

			}

		});
		
	}

	

	@Override
	public String getEcordaThemeId(NodeRef interestGroup) {
		if (!nodeService.hasAspect(interestGroup, CircabcModel.ASPECT_IGROOT) )
		{
			throw new IllegalArgumentException("Not a interest group node " + interestGroup); 
		}
		String result = StringUtils.EMPTY;
		try {
			result = ecordaDaoServiceImpl.getEcordaThemaID(interestGroup.toString());
		} catch (SQLException e) {
			if (logger.isErrorEnabled())
			{
				logger.error("Error when geting ECORDA thema id for interest group :" + interestGroup.toString() , e);
			}
		} 
		if (result == null)
		{
			result = StringUtils.EMPTY;
		}
		return result;
		
	}

	@Override
	public void grantThemeRoles(Set<String> userName, String themeID) {
		for (String userID : userName) {
			grantThemeRole(userID, themeID);
			
		}
		
	}

	

}
