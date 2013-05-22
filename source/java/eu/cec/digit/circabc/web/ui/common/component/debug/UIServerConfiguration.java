/*--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--*/

package eu.cec.digit.circabc.web.ui.common.component.debug;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.web.ui.common.component.debug.BaseDebugComponent;

import eu.cec.digit.circabc.service.admin.debug.ServerConfigurationService;
import eu.cec.digit.circabc.web.Services;

/**
 * Component which displays the Server configuration properties
 *
 * @author Yanick Pignot
 */
public class UIServerConfiguration extends BaseDebugComponent
{
	private transient ServerConfigurationService serverConfigurationService;

	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	 public String getFamily()
	 {
	      return "eu.cec.digit.circabc.faces.debug.ServerConfiguration";
	 }

	 /**
	  * @see org.alfresco.web.ui.common.component.debug.BaseDebugComponent#getDebugData()
	  */
	 @SuppressWarnings("unchecked")
	 public Map getDebugData()
	 {
		 final Map properties = new LinkedHashMap();

		 return properties;
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
