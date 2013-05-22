/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.repo.lock.ibatis;

import java.sql.SQLException;

import org.alfresco.repo.domain.activities.ibatis.IBatisSqlMapper;

/**
 * @author Slobodan Filipovic
 *
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * IBatisSqlMapper was moved to Spring.
 */
public class IBatisLockDaoServiceImpl extends IBatisSqlMapper implements LockDaoService
{




	@SuppressWarnings("unchecked")
	public int getLockCount(String item) throws SQLException
	{
		return (Integer) getSqlMapClient().queryForObject("CircabcLock.select_lock_count",item);
	}

	public void insertLock(String item) throws SQLException
	{
		getSqlMapClient().insert("CircabcLock.insert_lock",item);
	}

	public void deleteLock(String item) throws SQLException
	{
		getSqlMapClient().delete("CircabcLock.delete_lock",item);
	}




}
