/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.admin.debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.quartz.SchedulerException;

/**
 * Interface for Server configuration reader. Generates reports on different critical points of the configuration.
 *
 * @author Yanick Pignot
 */
public interface ServerConfigurationService
{
	/**
	 * Read the scheduler implemetation to retrieve the relevant information about configured Triggers of the system.
	 *
	 * @return the list of trigger defined in a report
	 */
	public List<TriggerReport> getTriggers() throws SchedulerException;


	/**
	 * Read the scheduler implemetation to retrieve the relevant information about <b>The Non Trigered</b> jobs.
	 *
	 * @return the list of trigger defined in a report
	 */
	public List<JobReport> getNoTriggeredJobs() throws SchedulerException;

	/**
	 * Read a give property or configuration files.
	 *
	 * @return a list of key/value with the property of the file
	 */
	public Map<String, String> getConfigurationFileResume(File specificFile) throws FileNotFoundException, IOException, DocumentException;


	/**
	 * Read the cache manager to retrieve the relevant information about configured Caches of the system.
	 *
	 * @return the list of caches defined in the cache manager
	 */
	public List<CacheReport> getCaches() throws SchedulerException;
}
