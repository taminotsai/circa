/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.customisation.nav;

import java.util.Date;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.cache.SimpleCache;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.util.ParameterCheck;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.repo.customisation.CustomizationException;
import eu.cec.digit.circabc.service.customisation.NodePreferencesService;
import eu.cec.digit.circabc.service.customisation.nav.NavigationConfigService;
import eu.cec.digit.circabc.service.customisation.nav.NavigationPreference;
import eu.cec.digit.circabc.service.customisation.nav.NavigationPreferencesService;

/**
 * Implementation of the MailPreferencesService.
 *
 * @author Yanick Pignot
 */
public class NavigationPreferencesServiceImpl implements NavigationPreferencesService
{
	/**
	 * The folder name in which all navigation customization are inclue
	 */
	private static final String CUSTOMIZATION_ROOT_FOLDER = "navigation";

	private static final String FILE_NAME = "default.xml";

	private SimpleCache<NodeRef, NavigationPreferenceImpl> navPreferenceCache;

	/** Logger */
	private static final Log logger = LogFactory.getLog(NavigationPreferencesServiceImpl.class);

	private NodePreferencesService nodePreferencesService;
	private NavigationConfigService navigationConfigService;
	private NodeService nodeService;

	public NavigationPreference getServicePreference(NodeRef nodeRef, String serviceName, String nodeType)
	{
		try
		{
			ParameterCheck.mandatory("The nodeRef ", nodeRef);
			ParameterCheck.mandatoryString("The node type ", nodeType);
			ParameterCheck.mandatoryString("The service name", serviceName);

			final NodeRef file = nodePreferencesService.getDefaultConfigurationFile(nodeRef, CUSTOMIZATION_ROOT_FOLDER, serviceName, nodeType);
			final NodeRef fileRef = file;
			final Date fileDate = (Date) nodeService.getProperty(file, ContentModel.PROP_MODIFIED);

			NavigationPreferenceImpl config = navPreferenceCache.get(fileRef);

			if(config != null)
			{
				if(fileDate.equals(config.getModifiedDate()) == false)
				{
					navPreferenceCache.remove(fileRef);
					config = null;
				}

			}
			if(config == null)
			{
				// cache not found
				config = (NavigationPreferenceImpl) navigationConfigService.buildPreference(serviceName, nodeType, fileRef);
				config.setModifiedDate(fileDate);
				config.setCustomizedOn(nodePreferencesService.getCustomizationFromNode(fileRef));
				navPreferenceCache.put(fileRef, config);
			}

			return config;
		}
		catch (Exception e)
		{
			logger.error("Impossible to retreive navigation preference " + serviceName + ":" + nodeType + " on node " + nodeRef + ". The default one is returned. ", e);

			// TODO return root default config !!!
			return null;
		}
	}


	public NodeRef addServicePreference(NodeRef nodeRef, String serviceName, String nodeType, NavigationPreference navigationPreference) throws CustomizationException
	{
		ParameterCheck.mandatory("The nodeRef ", nodeRef);
		ParameterCheck.mandatory("The navigationPreference ", navigationPreference);
        ParameterCheck.mandatoryString("The node type ", nodeType);
        ParameterCheck.mandatoryString("The service name", serviceName);

        if(nodePreferencesService.isNodeConfigurable(nodeRef) == false)
		{
			nodePreferencesService.makeConfigurable(nodeRef);
		}

        final NodeRef customizationRef = nodePreferencesService.addCustomizationFile(nodeRef,
        			CUSTOMIZATION_ROOT_FOLDER,
					serviceName,
					nodeType,
					FILE_NAME,
					navigationConfigService.buildPreferenceXML(navigationPreference));

        navPreferenceCache.remove(customizationRef);

		return customizationRef;
	}


	public void removeServicePreference(final NodeRef nodeRef, final String serviceName, final String nodeType) throws CustomizationException
	{
		ParameterCheck.mandatory("The nodeRef ", nodeRef);
        ParameterCheck.mandatoryString("The node type ", nodeType);
        ParameterCheck.mandatoryString("The service name", serviceName);

		final NodeRef customizationRef = nodePreferencesService.getCustomization(nodeRef,
				CUSTOMIZATION_ROOT_FOLDER,
				serviceName,
				nodeType,
				FILE_NAME);

		navPreferenceCache.remove(customizationRef);

		nodePreferencesService.removeCustomization(nodeRef, CUSTOMIZATION_ROOT_FOLDER, serviceName, nodeType, FILE_NAME);
	}

	//---------
	//-- Helpers


	//---------
	//-- IOC


	/**
	 * @param nodePreferencesService the nodePreferencesService to set
	 */
	public final void setNodePreferencesService(NodePreferencesService nodePreferencesService)
	{
		this.nodePreferencesService = nodePreferencesService;
	}


	/**
	 * @param navigationConfigService the navigationConfigService to set
	 */
	public final void setNavigationConfigService(NavigationConfigService navigationConfigService)
	{
		this.navigationConfigService = navigationConfigService;
	}


	/**
	 * @param navPreferenceCache the navPreferenceCache to set
	 */
	public final void setNavPreferenceCache(SimpleCache<NodeRef, NavigationPreferenceImpl> navPreferenceCache)
	{
		this.navPreferenceCache = navPreferenceCache;
	}


	/**
	 * @param nodeService the nodeService to set
	 */
	public final void setNodeService(NodeService nodeService)
	{
		this.nodeService = nodeService;
	}

}
