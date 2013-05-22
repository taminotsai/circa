/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.admin.debug;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import eu.cec.digit.circabc.service.admin.debug.CacheReport;




/**
 * Concrete impelmentation of a Cache Report
 *
 * @author Yanick Pignot
 */
public class CacheReportImpl implements CacheReport
{
	private Cache cache;
    private long size = 0L;
    private double sizeMB;
    private long maxSize;
    private long currentSize;
    private long hitCount;
    private double percentageFull;
    private double estMaxSize;
	private long totalMissCount;
	private double hitRatio;
    private String name;
    private String statusAsString;
	private boolean eternal;
	private boolean overflowToDisk;
	private MemoryStoreEvictionPolicy memoryStoreEvictionPolicy;
	private long timeToLiveSeconds;
	private long timeToIdleSeconds;
	private boolean diskPersistent;
	private long diskExpiryThreadIntervalSeconds;
	private long memoryStoreHitCount;
	private long diskStoreHitCount;

    public CacheReportImpl(Cache cache) throws CacheException
    {
        this.cache = cache;
        if (this.cache.getStatus().equals(Status.STATUS_ALIVE))
        {
            try
            {
                calculateSize();
            }
            catch (Throwable e)
            {
                // just ignore
            }
        }
    }

    /* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getSize()
	 */
    public synchronized long getSize()
    {
        return size;
    }

    /* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getEstimatedMaxSize()
	 */
    public synchronized double getEstimatedMaxSize()
    {
        return estMaxSize;
    }

    @SuppressWarnings("deprecation")
    private synchronized void calculateSize() throws CacheException
    {
        // calculate the cache deep size - EHCache 1.1 is always returning 0L
    	@SuppressWarnings("unchecked")
        List<Serializable> keys = cache.getKeys();
        // only count a maximum of 1000 entities
        int count = 0;
        for (Serializable key : keys)
        {
            Element element = cache.get(key);
            size += getSize(element);
            count++;
            if (count >= 50)
            {
                break;
            }
        }

        // the size must be multiplied by the ratio of the count to actual size
        size = count > 0 ? (long) ((double)size * ((double)keys.size()/(double)count)) : 0L;

        sizeMB = (double)size/1024.0/1024.0;
        maxSize = cache.getCacheConfiguration().getMaxElementsInMemory();
        currentSize = cache.getMemoryStoreSize();
        hitCount = cache.getStatistics().getCacheHits();
        totalMissCount = cache.getStatistics().getCacheMisses();
        percentageFull = (double)currentSize / (double)maxSize * 100.0;
        estMaxSize = size / (double) currentSize * (double) maxSize;
        hitRatio = (double)hitCount / (double)(totalMissCount + hitCount) * 100.0;
        name = cache.getName();
        eternal = cache.getCacheConfiguration().isEternal();
        overflowToDisk = cache.getCacheConfiguration().isOverflowToDisk();

        memoryStoreEvictionPolicy = cache.getCacheConfiguration().getMemoryStoreEvictionPolicy();

        timeToLiveSeconds = cache.getCacheConfiguration().getTimeToLiveSeconds();
        timeToIdleSeconds = cache.getCacheConfiguration().getTimeToIdleSeconds();

        diskPersistent = cache.getCacheConfiguration().isDiskPersistent();
        diskExpiryThreadIntervalSeconds = cache.getCacheConfiguration().getDiskExpiryThreadIntervalSeconds();
        memoryStoreHitCount = cache.getStatistics().getInMemoryHits();
        diskStoreHitCount = cache.getStatistics().getOnDiskHits();
        statusAsString = cache.getStatus().toString();
    }

    private long getSize(Serializable obj)
    {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
        ObjectOutputStream oos = null;
        try
        {
            oos = new ObjectOutputStream(bout);
            oos.writeObject(obj);
            return bout.size();
        }
        catch (IOException e)
        {
            return 0L;
        }
        finally
        {
            try { 
            		if(oos != null)
            		{
            			oos.close();
            		}
            	} catch (IOException e) {}
        }
    }

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getCurrentSize()
	 */
	public final String getCurrentSize()
	{
		return String.format("%10d entries", currentSize);
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getEstMaxSize()
	 */
	public final String getEstMaxSize()
	{
		return String.format("%10.2f MB", estMaxSize);
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getHitCount()
	 */
	public final String getHitCount()
	{
		return String.format("%10d hits", hitCount);
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getHitRatio()
	 */
	public final String getHitRatio()
	{
		return String.format("%10.2f percent", hitRatio);
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getMaxSize()
	 */
	public final String getMaxSize()
	{
		return String.format("%10d entries", maxSize);
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getPercentageFull()
	 */
	public final String getPercentageFull()
	{
		return String.format("%10.2f percent", percentageFull);
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getSizeMB()
	 */
	public final String getSizeMB()
	{
		return String.format("%10.2f MB", sizeMB);
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getTotalMissCount()
	 */
	public final String getTotalMissCount()
	{
		return String.format("%10d misses", totalMissCount);
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getName()
	 */
	public final String getName()
	{
		return name;
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getStatus()
	 */
	public final String getStatus()
	{
		return statusAsString;
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getDiskExpiryThreadIntervalSeconds()
	 */
	public final String getDiskExpiryThreadIntervalSeconds()
	{
		return String.format("%10d seconds", diskExpiryThreadIntervalSeconds);
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#isDiskPersistent()
	 */
	public final boolean isDiskPersistent()
	{
		return diskPersistent;
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getDiskStoreHitCount()
	 */
	public final String getDiskStoreHitCount()
	{
		return String.format("%10d hits", diskStoreHitCount);
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#isEternal()
	 */
	public final boolean isEternal()
	{
		return eternal;
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getMemoryStoreEvictionPolicy()
	 */
	public final String getMemoryStoreEvictionPolicy()
	{
		return memoryStoreEvictionPolicy.toString();
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getMemoryStoreHitCount()
	 */
	public final String getMemoryStoreHitCount()
	{
		return String.format("%10d hits", memoryStoreHitCount);
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#isOverflowToDisk()
	 */
	public final boolean isOverflowToDisk()
	{
		return overflowToDisk;
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getTimeToIdleSeconds()
	 */
	public final String getTimeToIdleSeconds()
	{
		return String.format("%10d seconds", timeToIdleSeconds);
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.admin.debug.CacheReport#getTimeToLiveSeconds()
	 */
	public final String getTimeToLiveSeconds()
	{
		return String.format("%10d seconds", timeToLiveSeconds);
	}
}

