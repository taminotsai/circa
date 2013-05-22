package eu.cec.digit.circabc.service.log;

import java.util.Date;
import java.util.List;

import eu.cec.digit.circabc.repo.log.ActivityCountDAO;
import eu.cec.digit.circabc.repo.log.LogActivityDAO;
import eu.cec.digit.circabc.repo.log.LogCountResultDAO;
import eu.cec.digit.circabc.repo.log.LogSearchResultDAO;



//Logger

public interface LogService
{

	public void log(LogRecord logRecord);

	public void logBatch(List<LogRecord> logRecords);
	
	public List<LogActivityDAO> getActivities();

	public List<LogSearchResultDAO> search(long igID, String user, String service, String method, Date fromDate, Date toDate);

	public int searchCount(long igID, String user, String service, String method, Date fromDate, Date toDate);

	public List<LogSearchResultDAO> searchPage(long igID, String user, String service, String method, Date fromDate, Date toDate,int startRecord,int pageSize);

	public List<LogSearchResultDAO> getHistory(long itemID);
	
	public void deleteInterestgroupLog(long igID);
	
	public Date getLastLoginDateOfUser(String username);
	
	public List<LogCountResultDAO> getNumberOfActionsYesterdayPerHour();

	public List<ActivityCountDAO> getListOfActivityCountForInterestGroup(Long igDbNode);

}
