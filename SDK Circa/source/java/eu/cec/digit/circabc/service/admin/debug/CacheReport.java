/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.admin.debug;

/**
 * Base interface to compute the report of a cache.
 *
 * @see net.sf.ehcache.Cache
 *
 * @author Yanick Pignot
 */
public interface CacheReport
{

	/**
	 * @return the size
	 */
	 long getSize();

	/**
	 * @return the Estimated Max Size
	 */
	 double getEstimatedMaxSize();

	/**
	 * @return the currentSize
	 */
	 String getCurrentSize();

	/**
	 * @return the estMaxSize
	 */
	 String getEstMaxSize();

	/**
	 * @return the hitCount
	 */
	 String getHitCount();

	/**
	 * @return the hitRatio
	 */
	 String getHitRatio();

	/**
	 * @return the maxSize
	 */
	 String getMaxSize();

	/**
	 * @return the percentageFull
	 */
	 String getPercentageFull();

	/**
	 * @return the sizeMB
	 */
	 String getSizeMB();

	/**
	 * @return the totalMissCount
	 */
	 String getTotalMissCount();

	/**
	 * @return the name
	 */
	 String getName();

	/**
	 * @return the status
	 */
	 String getStatus();

	/**
	 * @return the diskExpiryThreadIntervalSeconds
	 */
	 String getDiskExpiryThreadIntervalSeconds();

	/**
	 * @return the diskPersistent
	 */
	 boolean isDiskPersistent();

	/**
	 * @return the diskStoreHitCount
	 */
	 String getDiskStoreHitCount();

	/**
	 * @return the eternal
	 */
	 boolean isEternal();

	/**
	 * @return the memoryStoreEvictionPolicy
	 */
	 String getMemoryStoreEvictionPolicy();

	/**
	 * @return the memoryStoreHitCount
	 */
	 String getMemoryStoreHitCount();

	/**
	 * @return the overflowToDisk
	 */
	 boolean isOverflowToDisk();

	/**
	 * @return the timeToIdleSeconds
	 */
	 String getTimeToIdleSeconds();

	/**
	 * @return the timeToLiveSeconds
	 */
	 String getTimeToLiveSeconds();

}