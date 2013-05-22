/*--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--*/

package eu.cec.digit.circabc.web.ui.common.component.debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.alfresco.web.ui.common.component.debug.BaseDebugComponent;
import org.dom4j.DocumentException;

import eu.cec.digit.circabc.repo.admin.debug.ServerConfigurationServiceImpl;
import eu.cec.digit.circabc.service.admin.debug.ServerConfigurationService;
import eu.cec.digit.circabc.web.Services;

/**
 * Component which displays the Circabc property files (in a static way)
 *
 * @author Yanick Pignot
 */
public class UIConfigFileReader extends BaseDebugComponent
{
	private transient ServerConfigurationService serverConfigurationService;

	private static final File[] PROPERTY_FILES_TO_READ =
	{
		ServerConfigurationServiceImpl.CIRCABC_VERSION_PROPS,
		ServerConfigurationServiceImpl.ALFRESCO_REPO_PROPS,
		ServerConfigurationServiceImpl.ALFRESCO_CACHE_STATEGIES,
		ServerConfigurationServiceImpl.ALFRESCO_TRANSACTION_PROPS,
		ServerConfigurationServiceImpl.ALFRESCO_EMAIN_CONFIGURATION,
		ServerConfigurationServiceImpl.CIRCABC_SHARED_REPO_PROPS,
		ServerConfigurationServiceImpl.CIRCABC_REPO_PROPS,
		ServerConfigurationServiceImpl.CIRCABC_SETTINGS,
		ServerConfigurationServiceImpl.CIRCABC_BUILD_CONF,
		ServerConfigurationServiceImpl.CIRCABC_SHARED_HIBERNATE_CONFIG,
		ServerConfigurationServiceImpl.CIRCABC_HIBERNATE_CONFIG,
		ServerConfigurationServiceImpl.CIRCABC_QUARTZ_PROPS,
		ServerConfigurationServiceImpl.CIRCABC_DB_POOL,
		ServerConfigurationServiceImpl.CIRCABC_RMI,
		ServerConfigurationServiceImpl.CIRCABC_SHARED_PROPS,
		ServerConfigurationServiceImpl.CIRCABC_FILE_SERVER_CONF,
		ServerConfigurationServiceImpl.ECAS_PROPS
	};


	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	 public String getFamily()
	 {
	      return "eu.cec.digit.circabc.faces.debug.ConfigFileReader";
	 }

	 /**
	  * @see org.alfresco.web.ui.common.component.debug.BaseDebugComponent#getDebugData()
	  */
	 @SuppressWarnings("unchecked")
	 public Map getDebugData()
	 {
		 final Map properties = new LinkedHashMap();

		 for(final File file : PROPERTY_FILES_TO_READ)
		 {
			final String displayPath = "<b><i>" + file.getPath() + "</i></b>";

			try
			{
				// if it will be not the case, the key will be overriden ...
				properties.put(displayPath, "<b><font color=\"blue\">Successfully read</font></b>");

				putAllKeyAsc(properties, getServerConfigurationService().getConfigurationFileResume(file));
			}
			catch(FileNotFoundException ex)
			{
				properties.put(displayPath, "<b><font color=\"green\">Not exists</font></b>");
			}
			catch (IOException ex)
			{
				properties.put(displayPath, "<b><font color=\"red\">Can't be opened due to: " + ex.getMessage() + "</font></b>");
			}
			catch (DocumentException ex)
			{
				properties.put(displayPath, "<b><font color=\"red\">Can't be opened due to: " + ex.getMessage() + "</font></b>");
			}
		}

		 return properties;
	 }


	 protected void putAllKeyAsc(final Map<String, String> destination, final Map<String, String> toSort)
	 {
		final Set<String> keys = toSort.keySet();
		final List<String> keyAsList = new ArrayList<String>(keys.size());

		keyAsList.addAll(keys);
		Collections.sort(keyAsList);

		for(String key : keyAsList)
		{
			if(destination.containsKey(key))
			{
				destination.put(key + " [override]", toSort.get(key));
			}
			else
			{
				destination.put(key, toSort.get(key));
			}

		}
	}

	/**
	 * @return the serverConfigurationService
	 */
	protected final ServerConfigurationService getServerConfigurationService()
	{
		if(serverConfigurationService == null)
		{
			serverConfigurationService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getServerConfigurationService();
		}
		return serverConfigurationService;
	}
}
