/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.customisation.nav;

import org.alfresco.service.PublicService;
import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.repo.customisation.CustomizationException;

/**
 * Interface for the customisation of the navigation thought Interest Groups.
 * Under any Interest Group service (Inforation, library, ...), we can define
 * own navigation properties: Column properties, actions, list size ...
 * 
 * @see eu.cec.digit.circabc.service.customisation.NodePreferencesService
 * 
 * @author Yanick Pignot
 */
@PublicService
public interface NavigationPreferencesService
{
	public static final String SEARCH_SERVICE = "search";
	public static final String LIBRARY_SERVICE = "library";
	public static final String INFORMATION_SERVICE = "information";
	public static final String NEWSGROUP_SERVICE = "newsgroup";

	public static final String DISCUSSION_TYPE = "discussion";
	public static final String CONTAINER_TYPE = "container";
	public static final String CONTENT_TYPE = "content";
	public static final String FORUM_TYPE = "forum";
	public static final String TOPIC_TYPE = "topic";

	/**
	 * Return the navigation preference of a sepecif service of a given interest
	 * group.
	 * 
	 * @param interestGroup
	 * @param service
	 * @return
	 */
	public abstract NodeRef addServicePreference(final NodeRef interestGroup, final String serviceName, final String nodeType,
			final NavigationPreference navigationPreference) throws CustomizationException;

	/**
	 * Return the navigation preference of a sepecif service of a given interest
	 * group.
	 * 
	 * @param interestGroup
	 * @param service
	 * @return
	 */
	public abstract NavigationPreference getServicePreference(final NodeRef interestGroup, final String serviceName,
			final String nodeType);

	/**
	 * Remove a given navigation preference for a given node.
	 * 
	 * @param interestGroup
	 * @param service
	 * @return
	 */
	public abstract void removeServicePreference(final NodeRef interestGroup, final String serviceName, final String nodeType)
			throws CustomizationException;

}
