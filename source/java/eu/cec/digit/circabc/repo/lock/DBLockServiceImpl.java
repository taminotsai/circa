package eu.cec.digit.circabc.repo.lock;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.repo.lock.ibatis.LockDaoService;
import eu.cec.digit.circabc.service.lock.LockService;

public class DBLockServiceImpl implements LockService
{
	private static final Log logger = LogFactory.getLog(DBLockServiceImpl.class);
	private LockDaoService lockDaoService;

	public boolean isLocked(String item)
	{
		int lockCount=0;
		try
		{
			final String itemUpper = item.toUpperCase();
			lockCount = lockDaoService.getLockCount(itemUpper);
		} catch (SQLException e)
		{
			if (logger.isErrorEnabled())
			{
				logger.error("Erorr executing lockDaoService.getLockCount  for item  " + item,e);
			}
		}
		return (lockCount==1);
	}

	public void lock(String item)
	{
		try
		{
			final String itemUpper = item.toUpperCase();
			lockDaoService.insertLock(itemUpper);
		} catch (SQLException e)
		{
			throw new  IllegalStateException("Can not lock item " + item  +" it is already locked.");
		}

	}

	public void unlock(String item)
	{
		try
		{
			final String itemUpper = item.toUpperCase();
			lockDaoService.deleteLock(itemUpper);
		} catch (SQLException e)
		{
			if (logger.isErrorEnabled())
			{
				logger.error("Erorr executing lockDaoService.deleteLock for item  " + item,e);
			}
		}


	}

	/**
	 * @param lockDaoService the lockDaoService to set
	 */
	public void setLockDaoService(LockDaoService lockDaoService)
	{
		this.lockDaoService = lockDaoService;
	}
}

