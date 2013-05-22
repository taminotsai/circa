package eu.cec.digit.circabc.repo.lock.ibatis;

import java.sql.SQLException;

/**
 * @author Slobodan Filipovic
 *
 */
public interface LockDaoService
{

	public abstract void insertLock(String item) throws SQLException;

	public abstract void deleteLock(String item)throws SQLException;

	public abstract int getLockCount(String item)throws SQLException;
}