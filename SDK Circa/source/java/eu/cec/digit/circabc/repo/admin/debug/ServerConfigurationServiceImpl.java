/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.admin.debug;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.alfresco.repo.cache.InternalEhCacheManagerFactoryBean;
import org.alfresco.service.cmr.repository.NodeService;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import eu.cec.digit.circabc.service.admin.debug.CacheReport;
import eu.cec.digit.circabc.service.admin.debug.JobReport;
import eu.cec.digit.circabc.service.admin.debug.ServerConfigurationService;
import eu.cec.digit.circabc.service.admin.debug.TriggerReport;

/**
 * Interface for Server configuration reader.
 *
 * @author Yanick Pignot
 */
public class ServerConfigurationServiceImpl implements ServerConfigurationService
{
	private NodeService nodeService;
	private Scheduler scheduler;

	public static final File ALFRESCO_REPO_PROPS = new File("classpath:alfresco", "repository.properties");
	public static final File ALFRESCO_CACHE_STATEGIES = new File("classpath:alfresco/domain", "cache-strategies.properties");
	public static final File ALFRESCO_TRANSACTION_PROPS = new File("classpath:alfresco/domain", "transaction.properties");
	public static final File ALFRESCO_EMAIN_CONFIGURATION = new File("classpath:alfresco/emailserver", "email-server.properties");
//	public static final File CIRCABC_SHARED_REPO_PROPS = new File("classpath:alfresco/extension/config", "circabc-shared-repository.properties");
	public static final File CIRCABC_SHARED_REPO_PROPS = new File("classpath:alfresco/alfresco-global.properties");
	public static final File CIRCABC_REPO_PROPS = new File("classpath:alfresco/extension/config", "circabc-repository.properties");
//  Migration 3.1 -> 3.4.6 - 09/01/2012 - Replaced configuration location by alfresco-global.properties
//	public static final File CIRCABC_SETTINGS = new File("classpath:alfresco/extension/config", "circabc-settings.properties");
	public static final File CIRCABC_SETTINGS = new File("classpath:", "alfresco-global.properties");
	public static final File CIRCABC_SHARED_HIBERNATE_CONFIG = new File("classpath:alfresco/extension/domain", "circabc-shared-hibernate-cfg.properties");
	public static final File CIRCABC_HIBERNATE_CONFIG = new File("classpath:alfresco/extension/domain", "circabc-hibernate-cfg.properties");
	public static final File CIRCABC_QUARTZ_PROPS = new File("classpath:alfresco/extension/domain", "quartz.properties");
	public static final File CIRCABC_DB_POOL = new File("classpath:alfresco/extension", "circabc-dbpool-context.xml");
	public static final File CIRCABC_RMI = new File("classpath:alfresco/extension", "circabc-rmi-core-services-context.xml");
	public static final File CIRCABC_SHARED_PROPS = new File("classpath:alfresco/extension", "circabc-shared.properties");
	public static final File CIRCABC_FILE_SERVER_CONF = new File("classpath:alfresco/extension", "file-servers-custom.xml");
	public static final File CIRCABC_BUILD_CONF = new File("classpath:alfresco/extension", "build-config.properties");
	public static final File ECAS_PROPS = new File("classpath:", "ecas-config.properties");
	public static final File CIRCABC_VERSION_PROPS = new File("classpath:alfresco/extension/messages", "circabc-version.properties");

	public List<JobReport> getNoTriggeredJobs() throws SchedulerException
	{
		final List<JobReport> jobReports = new ArrayList<JobReport>(5);
		final List<Trigger> triggers = getTriggersObject();

		final List<String> trigeredJobs = new ArrayList<String>(triggers.size());

		for(Trigger trigger : triggers)
		{
			trigeredJobs.add(trigger.getJobGroup() + "." + trigger.getJobName());
		}


		final String[] jobGroups = scheduler.getJobGroupNames();
		for(final String groupName : jobGroups)
		{
			for(final String jobName : scheduler.getJobNames(groupName))
			{
				final JobDetail jobDetail = scheduler.getJobDetail(jobName, groupName);

				if(!trigeredJobs.contains(jobDetail.getFullName()))
				{
					jobReports.add( new JobReportImpl(jobDetail));
				}
			}
		}

		return jobReports;
	}

	public List<TriggerReport> getTriggers() throws SchedulerException
	{
		final List<Trigger> triggers = getTriggersObject();
		final List<TriggerReport> triggerReports = new ArrayList<TriggerReport>(triggers.size());

		for (final Trigger trigger : triggers)
		{
			final JobDetail jobDetail = scheduler.getJobDetail(trigger.getJobName(), trigger.getJobGroup());

			triggerReports.add(new TriggerReportImpl(trigger, jobDetail));
		}

		return triggerReports;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getConfigurationFileResume(File specificFile) throws FileNotFoundException, IOException, DocumentException
	{
		final Map properties = new LinkedHashMap();

		final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		final Resource  resource = resolver.getResource(specificFile.getPath());

		if(specificFile.getName().endsWith(".xml"))
		{
			parseXmlRessourceAndFill(properties, resource);
		}
		else
		{
			parsePropertiesRessourceAndFill(properties, resource);
		}

		 return properties;
	}

	public List<CacheReport> getCaches() throws SchedulerException
	{
		final CacheManager cacheManager = InternalEhCacheManagerFactoryBean.getInstance();
		final String[] cacheNames = cacheManager.getCacheNames();

		final List<CacheReport> caches = new ArrayList<CacheReport>(cacheNames.length);

		for (String cacheName : cacheNames)
        {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache == null)  // perhaps a temporary cache
            {
                continue;
            }
            else
            {
            	caches.add(new CacheReportImpl(cache));
            }
        }

		return caches;
	}


	// ----------
	// ---  Util methods

	private List<Trigger> getTriggersObject() throws SchedulerException
	{
		final List<Trigger> triggerReports = new ArrayList<Trigger>(20);

		final String[] triggersGroups = scheduler.getTriggerGroupNames();
		for(final String groupName : triggersGroups)
		{
			for(final String triggerName : scheduler.getTriggerNames(groupName))
			{
				final Trigger trigger = scheduler.getTrigger(triggerName, groupName);
				triggerReports.add(trigger);
			}
		}

		return triggerReports;
	}

	 @SuppressWarnings("unchecked")
	 private void parsePropertiesRessourceAndFill(final Map properties, Resource resource) throws FileNotFoundException, IOException
	 {

		 final InputStream is = resource.getInputStream();

		 try
		 {
			 if(is != null)
			 {
				 Properties props = new Properties();
				 props.load(is);
				 properties.putAll(props);
			 }
		 }
		 finally
		 {
			 if(is != null)
			 {
				 is.close();
			 }
		 }
	 }

	 @SuppressWarnings("unchecked")
	 private void parseXmlRessourceAndFill(final Map properties, Resource resource) throws FileNotFoundException, DocumentException, IOException
	 {
		 final InputStream is = resource.getInputStream();

		 try
		 {
			 if(is != null)
			 {
				 final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
				 final SAXReader reader = new SAXReader(false);
				 reader.setIgnoreComments(true);
				 reader.setIncludeExternalDTDDeclarations(false);
				 reader.setIncludeInternalDTDDeclarations(false);
				 reader.setValidation(false);
				 final Document document = reader.read(bufferedReader);
				 final Element rootElement = document.getRootElement();

				 parseXmlRessourceAndFill(rootElement.getName(), properties, rootElement);
			 }
		 }
		 finally
		 {
			 if(is != null)
			 {
				 is.close();
			 }
		 }
	 }


	 @SuppressWarnings("unchecked")
	 private void parseXmlRessourceAndFill(String prefixName, final Map properties, Element element)
	 {
		 final String name = prefixName + "." + element.getName();
		 final String value = element.getTextTrim();
		 final Iterator attributes = element.attributeIterator();
		 final Iterator childs = element.elementIterator();

		 while(attributes.hasNext())
		 {
			 Attribute attribute = (Attribute) attributes.next();

			 properties.put(name + " [attribute:" + attribute.getName() + "]", attribute.getValue());
		 }

		 if(value != null && value.length() > 0)
		 {
			 properties.put(name + " [value]", value);
		 }

		 while(childs.hasNext())
		 {
			 Element child = (Element) childs.next();
			 parseXmlRessourceAndFill(name, properties, child);
		 }

	 }


	// ----------
	// ---  IOC

	/**
	 * @return the nodeService
	 */
	protected final NodeService getNodeService()
	{
		return nodeService;
	}

	/**
	 * @param nodeService the nodeService to set
	 */
	public final void setNodeService(NodeService nodeService)
	{
		this.nodeService = nodeService;
	}


	/**
	 * @return the scheduler
	 */
	protected final Scheduler getSchedulerFactoryBean()
	{
		return scheduler;
	}


	/**
	 * @param scheduler the scheduler to set
	 */
	public final void setScheduler(Scheduler scheduler)
	{
		this.scheduler = scheduler;
	}
}
