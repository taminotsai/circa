/**
 * 
 */
package eu.cec.digit.circabc.service.statistics.global;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import eu.cec.digit.circabc.repo.lock.DBLockServiceImpl;

/**
 * @author beaurpi
 *
 */
public class GlobalStatisticsJobListener implements Job {
	
	private static final String GLOBAL_STATISTICS_LOCK = "globalStatisticsRunningLock";
	
	private GlobalStatisticsService globalStatisticsService;
	private DBLockServiceImpl circabcLockService;

	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException 
	{
		
		JobDataMap jobData = context.getJobDetail().getJobDataMap();
		
		circabcLockService = (DBLockServiceImpl) jobData.get("circabcLockService");
		globalStatisticsService = (GlobalStatisticsService) jobData.get("globalStatisticsService");
		
		Integer lockResult = lockJobFile();
		if( lockResult == 1)
		{
		
			try {
				
				AuthenticationUtil.setRunAsUserSystem();

					
					
					if(!globalStatisticsService.isReportSaveFolderExisting())
					{
						globalStatisticsService.prepareFolderRecipient();
					}
					
					GregorianCalendar gc = new GregorianCalendar();
					gc.setTime(new Date());
					
					if(gc.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
					{
						globalStatisticsService.cleanAndZipPreviousReportFiles();
					}
	
					globalStatisticsService.saveStatsToExcel(globalStatisticsService.getReportSaveFolder(), globalStatisticsService.makeGlobalStats());

				}
				finally
				{
					AuthenticationUtil.clearCurrentSecurityContext();
				}
		
		}
		unlockJobFile();
		
		
	}

	/**
	 * @return the globalStatisticsService
	 */
	public GlobalStatisticsService getGlobalStatisticsService() {
		return globalStatisticsService;
	}

	/**
	 * @param globalStatisticsService the globalStatisticsService to set
	 */
	public void setGlobalStatisticsService(GlobalStatisticsService globalStatisticsService) {
		this.globalStatisticsService = globalStatisticsService;
	}

	/**
	 * @return the lockDaoService
	 */
	public DBLockServiceImpl getCircabcLockService() {
		return circabcLockService;
	}

	/**
	 * @param lockDaoService the lockDaoService to set
	 */
	public void setCircabcLockService(DBLockServiceImpl circabcLockService) {
		this.circabcLockService = circabcLockService;
	}

	/**
	 * @return the globalStatisticsLock
	 */
	public static String getGlobalStatisticsLock() {
		return GLOBAL_STATISTICS_LOCK;
	}
	

	public Integer lockJobFile() {

		Integer result = 0; 
		
		if(!circabcLockService.isLocked(GLOBAL_STATISTICS_LOCK))
		{
			circabcLockService.lock(GLOBAL_STATISTICS_LOCK);
			result = 1;
		}
		
		return result;
		
	}

	public Integer unlockJobFile() {
		
		Integer result = 0; 
		
		if(circabcLockService.isLocked(GLOBAL_STATISTICS_LOCK))
		{
			circabcLockService.unlock(GLOBAL_STATISTICS_LOCK);
			result = 1;
		}
		
		return result;
		
	}

}
