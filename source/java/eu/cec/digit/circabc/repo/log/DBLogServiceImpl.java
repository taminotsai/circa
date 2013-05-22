package eu.cec.digit.circabc.repo.log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.repo.log.ibatis.LogDaoService;
import eu.cec.digit.circabc.service.log.LogRecord;
import eu.cec.digit.circabc.service.log.LogService;

/**
 * @author Slobodan Filipovic
 *
 */
public class DBLogServiceImpl implements LogService
{

	private static final String ALPHABETIC_SPACE_REGEX = "[a-z A-Z]*";
	private static final Log logger = LogFactory.getLog(DBLogServiceImpl.class);
	private LogDaoService logDaoService ;

	public void log(LogRecord logRecord)
	{
		if ((logRecord.getActivity() == null ) || (logRecord.getService() == null ))
		{
			return;
		}

		try
		{
			LogRecordDAO dbLogRecord = new LogRecordDAO();

			int activityID = logDaoService.getActivityID(logRecord.getService() ,logRecord.getActivity());
			if (activityID == -1)
			{
				activityID = logDaoService.insertActivity(logRecord.getService() ,logRecord.getActivity());
			}
			dbLogRecord.setActivityID(activityID);

			if(logRecord.getDate() != null)
			{
				dbLogRecord.setDate(logRecord.getDate());
			}
			else
			{
				dbLogRecord.setDate(new Date());
			}

			Long igDBID = logRecord.getIgID();
			if (igDBID != null)
			{
				dbLogRecord.setIgID(igDBID);
			}

			Long documentDBID = logRecord.getDocumentID();
			if (documentDBID != null)
			{
				dbLogRecord.setDocumentID(documentDBID);
			}

			dbLogRecord.setInfo(logRecord.getInfo());
			dbLogRecord.setPath(logRecord.getPath());
			dbLogRecord.setUser(logRecord.getUser());
			dbLogRecord.setIgName(logRecord.getIgName());

			if (logRecord.isOK())
			{
				dbLogRecord.setIsOK(1);
			}
			else
			{
				dbLogRecord.setIsOK(0);
			}
			logDaoService.log(dbLogRecord);

		} catch (SQLException e)
		{
			if (logger.isErrorEnabled())
			{
				logger.error("Error logging activity :", e);
			}
		}
	}

	/**
	 * @param logDaoService the logDaoService to set
	 */
	public void setLogDaoService(LogDaoService logDaoService)
	{
		this.logDaoService = logDaoService;
	}

	/**
	 * @return the logDaoService
	 */
	public LogDaoService getLogDaoService()
	{
		return logDaoService;
	}

	public List<LogSearchResultDAO> search(long igID, String user, String service, String method, Date fromDate, Date toDate)
	{
		List<LogSearchResultDAO> logSearchResult =null;
		try
		{
			logSearchResult =  logDaoService.search(igID, user, service, method, fromDate, toDate);
		} catch (SQLException e)
		{
			if (logger.isErrorEnabled())
			{
				logger.error("Error searching log :", e);
			}
		}
		return logSearchResult;
	}

	public List<LogActivityDAO> getActivities()
	{
		List<LogActivityDAO> logActivities =null;
		try
		{
			logActivities = logDaoService.selectLogActivities();

		} catch (SQLException e)
		{
			if (logger.isErrorEnabled())
			{
				logger.error("Error getting activities :", e);
			}
		}
		return logActivities;
	}

	public int searchCount(long igID, String user, String service, String activity, Date fromDate, Date toDate)
	{
		Integer result = 0;
		try
		{
			result =  logDaoService.searchCount(igID, user, service, activity, fromDate, toDate);
		} catch (SQLException e)
		{
			if (logger.isErrorEnabled())
			{
				logger.error("Error searching log :", e);
			}
		}
		return result;
	}

	public List<LogSearchResultDAO> searchPage(long igID, String user, String service, String activity, Date fromDate, Date toDate, int startRecord, int pageSize)
	{
		List<LogSearchResultDAO> logSearchResult = new ArrayList<LogSearchResultDAO>();
		try
		{
			logSearchResult =  logDaoService.searchPage(igID, user, service, activity, fromDate, toDate, startRecord, pageSize);
		} catch (SQLException e)
		{
			if (logger.isErrorEnabled())
			{
				logger.error("Error searching log :", e);
			}
		}
		return logSearchResult;
	}

	public List<LogSearchResultDAO> getHistory(long itemID)
	{
		List<LogSearchResultDAO> logSearchResult = new ArrayList<LogSearchResultDAO>();
		try
		{
			logSearchResult =  logDaoService.getHistory(itemID);
		}
		catch (SQLException e)
		{
			if (logger.isErrorEnabled())
			{
				logger.error("Error getting history for id: " + itemID , e);
			}
		}
		return logSearchResult;
	}

	
	public void logBatch(List<LogRecord> logRecords)
	{
		List<LogRecordDAO> dbLogRecords = new ArrayList<LogRecordDAO>(logRecords.size()); 
		
		for (LogRecord logRecord : logRecords)
			{
				
			
			final String activity = logRecord.getActivity();
			final String service = logRecord.getService();
			if ((activity == null ) || (service == null ))
			{
				if (logger.isErrorEnabled())
				{
					logger.error("Invalid activity " + activity  + " or service " +  service );
					
				}
				continue;
			}
	
			try
			{
				
				LogRecordDAO dbLogRecord = new LogRecordDAO();
	
				int activityID = logDaoService.getActivityID(service ,activity);
				if (activityID == -1)
				{
					// check if service and activity are  alphabetic string with spaces 
					if (service.matches(ALPHABETIC_SPACE_REGEX) &&  activity.matches(ALPHABETIC_SPACE_REGEX)) 
					{
						activityID = logDaoService.insertActivity(service ,activity);
					}
					else
					{
						if (logger.isErrorEnabled())
						{
							logger.error("Service : " + service+ " and activity : " + activity +  " do not exists" );
							logger.error("Log record : " + logRecord.toString());
						}
						continue;
					}
				}
				dbLogRecord.setActivityID(activityID);
	
				if(logRecord.getDate() != null)
				{
					dbLogRecord.setDate(logRecord.getDate());
				}
				else
				{
					dbLogRecord.setDate(new Date());
				}
	
				Long igDBID = logRecord.getIgID();
				if (igDBID != null)
				{
					dbLogRecord.setIgID(igDBID);
				}
	
				Long documentDBID = logRecord.getDocumentID();
				if (documentDBID != null)
				{
					dbLogRecord.setDocumentID(documentDBID);
				}
	
				dbLogRecord.setInfo(logRecord.getInfo());
				dbLogRecord.setPath(logRecord.getPath());
				dbLogRecord.setUser(logRecord.getUser());
				dbLogRecord.setIgName(logRecord.getIgName());
	
				if (logRecord.isOK())
				{
					dbLogRecord.setIsOK(1);
				}
				else
				{
					dbLogRecord.setIsOK(0);
				}
				dbLogRecords.add(dbLogRecord);
	
			} catch (SQLException e)
			{
				if (logger.isErrorEnabled())
				{
					logger.error("Error preparing log batch :", e);
				}
			}
			
			
			
		}
		
		try
		{
			logDaoService.logBatch(dbLogRecords);
		} catch (SQLException e)
		{
			if (logger.isErrorEnabled())
			{
				logger.error("Error performing log batch insert ", e);
			}
		}
		
	}

	public void deleteInterestgroupLog(long igID)
	{
		try
		{
			logDaoService.deleteInterestgroupLog(igID);
		} catch (SQLException e)
		{
			if (logger.isErrorEnabled())
			{
				logger.error("Error performing deletion of interest group :" + String.valueOf(igID), e);
			}
		}
		
	}

	public Date getLastLoginDateOfUser(String username) {
		try
		{
			return logDaoService.getLastLoginDateOfUser(username);
		} catch (SQLException e)
		{
			if (logger.isErrorEnabled())
			{
				logger.error("Error during getting last login date of"+username, e);
			}
			
			return null;
		}
	}

	public List<LogCountResultDAO> getNumberOfActionsYesterdayPerHour() {
		
		try
		{
			return logDaoService.getNumberOfActionsYesterdayPerHour();
			
		} catch (SQLException e)
		{
			if (logger.isErrorEnabled())
			{
				logger.error("Error during getting number of actions in log service", e);
			}
			
			return Collections.emptyList();
		}
	}

	public List<ActivityCountDAO> getListOfActivityCountForInterestGroup(Long igDbNode) {
		
		try {
			
			return logDaoService.getListOfActivityCountForInterestGroup(igDbNode);
			
		} catch (SQLException e) {
			
			if (logger.isErrorEnabled())
			{
				logger.error("Error during getting number of activity in log service for group id:"+igDbNode, e);
			}
			
			return Collections.emptyList();
		}
	}



}
