package eu.cec.digit.circabc.repo.log.ibatis;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import eu.cec.digit.circabc.repo.log.ActivityCountDAO;
import eu.cec.digit.circabc.repo.log.LogActivityDAO;
import eu.cec.digit.circabc.repo.log.LogCountResultDAO;
import eu.cec.digit.circabc.repo.log.LogRecordDAO;
import eu.cec.digit.circabc.repo.log.LogSearchResultDAO;

/**
 * @author Slobodan Filipovic
 *
 */
public interface LogDaoService
{

	public abstract Long log(LogRecordDAO logRecord) throws SQLException;
	
	public abstract void logBatch(List<LogRecordDAO>  logRecord) throws SQLException;

	public abstract Integer getActivityID(String service, String activity)throws SQLException;

	public abstract Integer insertActivity(String service, String activity)throws SQLException;

	public abstract List<LogActivityDAO> selectLogActivities() throws SQLException;

	public abstract List<LogSearchResultDAO> search(Long igID, String userName, String service, String activity, Date fromDate, Date toDate) throws SQLException;

	public abstract Integer searchCount(Long igID, String userName, String service, String activity, Date fromDate, Date toDate) throws SQLException;

	public abstract List<LogSearchResultDAO> searchPage(Long igID, String userName, String service, String activity, Date fromDate, Date toDate,int startRecord,int pageSize) throws SQLException;

	public abstract List<LogSearchResultDAO> getHistory(Long itemID) throws SQLException;
	
	public void deleteInterestgroupLog(long igID) throws SQLException;
	
	public Date getLastLoginDateOfUser(String username) throws SQLException;
	
	public List<LogCountResultDAO> getNumberOfActionsYesterdayPerHour() throws SQLException;

	public abstract List<ActivityCountDAO> getListOfActivityCountForInterestGroup(Long igDbNode) throws SQLException;
}