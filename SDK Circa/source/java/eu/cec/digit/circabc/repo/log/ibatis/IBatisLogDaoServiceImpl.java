/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.repo.log.ibatis;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.alfresco.repo.domain.activities.ibatis.IBatisSqlMapper;

import com.ibatis.sqlmap.client.SqlMapClient;

import eu.cec.digit.circabc.repo.log.ActivityCountDAO;
import eu.cec.digit.circabc.repo.log.LogActivityDAO;
import eu.cec.digit.circabc.repo.log.LogCountResultDAO;
import eu.cec.digit.circabc.repo.log.LogRecordDAO;
import eu.cec.digit.circabc.repo.log.LogSearchLimitParameterDAO;
import eu.cec.digit.circabc.repo.log.LogSearchParameterDAO;
import eu.cec.digit.circabc.repo.log.LogSearchResultDAO;

/**
 * @author Slobodan Filipovic
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * IBatisSqlMapper was moved to org.alfresco.repo.domain.activities.ibatis
 */
public class IBatisLogDaoServiceImpl extends IBatisSqlMapper implements LogDaoService
{

    private static final String NULL_STRING = "null";

	/* (non-Javadoc)
     * @see eu.cec.digit.circabc.repo.log.ibatis.LogDaoService#insertPost(eu.cec.digit.circabc.service.log.LogRecord)
     */
    public Long log(LogRecordDAO logRecord) throws SQLException
    {
        Long id = (Long)getSqlMapClient().insert("CircabcLog.insert_log_record", logRecord);
        return (id != null ? id : -1);
    }

    public Integer getActivityID(String service, String activity) throws SQLException
    {
        LogActivityDAO logActivityDAO = new LogActivityDAO(service,activity);
        Integer id = (Integer)getSqlMapClient().queryForObject("CircabcLog.select_activity_id",  logActivityDAO);
        return (id != null ? id : -1);
    }

    public Integer insertActivity(String service, String activity) throws SQLException
    {
    	LogActivityDAO logActivityDAO = new LogActivityDAO(service,activity);
    	Integer id = (Integer)getSqlMapClient().insert("CircabcLog.insert_activity", logActivityDAO);
        return (id != null ? id : -1);
    }

	@SuppressWarnings("unchecked")
	public List<LogActivityDAO> selectLogActivities() throws SQLException
	{
		return (List<LogActivityDAO>)getSqlMapClient().queryForList("CircabcLog.select_log_activity");

	}

	@SuppressWarnings("unchecked")
	public List<LogSearchResultDAO> search(Long igID, String userName, String serviceDescription, String activityDescription, Date fromDate, Date toDate) throws SQLException
	{

		LogSearchParameterDAO params = new LogSearchParameterDAO();
		params.setIgID(igID);
		params.setFromDate(fromDate);
		params.setToDate(toDate);
		if (userName !=null && userName.equals(NULL_STRING))
		{
			params.setUserName(null);
		}
		else
		{
			params.setUserName(userName);
		}
		params.setActivityDescription(activityDescription);
		params.setServiceDescription(serviceDescription);

		return (List<LogSearchResultDAO>)getSqlMapClient().queryForList("CircabcLog.select_log_records",params);
	}

	@SuppressWarnings("unchecked")
	public List<LogSearchResultDAO> searchPage(Long igID, String userName, String serviceDescription, String activityDescription, Date fromDate, Date toDate, int startRecord, int pageSize) throws SQLException
	{
		LogSearchLimitParameterDAO  params = new LogSearchLimitParameterDAO();
		params.setIgID(igID);
		params.setFromDate(fromDate);
		params.setToDate(toDate);
		if (userName !=null && userName.equals(NULL_STRING))
		{
			params.setUserName(null);
		}
		else
		{
			params.setUserName(userName);
		}
		params.setActivityDescription(activityDescription);
		params.setServiceDescription(serviceDescription);
		params.setStartRecord(startRecord);
		params.setPageSize(pageSize);

		return (List<LogSearchResultDAO>)getSqlMapClient().queryForList("CircabcLog.select_log_records_page",params);

	}

	public Integer searchCount(Long igID, String userName, String serviceDescription, String activityDescription, Date fromDate, Date toDate) throws SQLException
	{
		LogSearchParameterDAO params = new LogSearchParameterDAO();
		params.setIgID(igID);
		params.setFromDate(fromDate);
		params.setToDate(toDate);
		if (userName !=null && userName.equals(NULL_STRING))
		{
			params.setUserName(null);
		}
		else
		{
			params.setUserName(userName);
		}
		params.setActivityDescription(activityDescription);
		params.setServiceDescription(serviceDescription);

		return (Integer) getSqlMapClient().queryForObject("CircabcLog.select_log_records_count",params);
	}

	@SuppressWarnings("unchecked")
	public List<LogSearchResultDAO> getHistory(Long itemID) throws SQLException
	{

		return (List<LogSearchResultDAO>) getSqlMapClient().queryForList("CircabcLog.select_item_history",itemID);
	}

	public void logBatch(List<LogRecordDAO> logRecords) throws SQLException
	{
		final SqlMapClient sqlMapClient = getSqlMapClient();
		try
		{
			sqlMapClient.startTransaction();
			sqlMapClient.startBatch();
			for (LogRecordDAO logRecordDAO : logRecords)
			{
				sqlMapClient.insert("CircabcLog.insert_log_record", logRecordDAO);
			}
			sqlMapClient.executeBatch();
			sqlMapClient.commitTransaction();
		} finally
		{
			sqlMapClient.endTransaction();
		}		
        
		
	}

	public void deleteInterestgroupLog(long igID) throws SQLException
	{
		
		getSqlMapClient().delete("CircabcLog.delete_log_by_ig", igID);
    }
	
	public Date getLastLoginDateOfUser(String username) throws SQLException {
		
		return (Date) getSqlMapClient().queryForObject("CircabcLog.select_last_login_date_of_user", username);
	}

	@SuppressWarnings("unchecked")
	public List<LogCountResultDAO> getNumberOfActionsYesterdayPerHour()	throws SQLException {
		return (List<LogCountResultDAO>) getSqlMapClient().queryForList("CircabcLog.select_count_actions_per_hour_yesterday");
	}


	@SuppressWarnings("unchecked")
	public List<ActivityCountDAO> getListOfActivityCountForInterestGroup(Long igDbNode) throws SQLException {
		
		return  (List<ActivityCountDAO>) getSqlMapClient().queryForList("CircabcLog.select_activity_of_interest_group", igDbNode);
	}

}
