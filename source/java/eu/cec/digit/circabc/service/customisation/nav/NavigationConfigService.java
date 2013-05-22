/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.customisation.nav;

import java.util.Collection;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * @author Yanick Pignot
 */
public interface NavigationConfigService
{

	/**
	 * @return
	 */
	public abstract Collection<ServiceConfig> getAllServiceConfig();

	/**
	 * @param serviceName
	 * @param nodeType
	 * @return
	 */
	public abstract ServiceConfig getServiceConfig(final String serviceName, final String nodeType);

	/**
	 * @param serviceName
	 * @param serviceType
	 * @param ref
	 * @return
	 */
	public abstract NavigationPreference buildPreference(final String serviceName, final String serviceType, final NodeRef ref);

	/**
	 * @param navigationPreference
	 * @return
	 */
	public abstract String buildPreferenceXML(NavigationPreference navigationPreference);

}