/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.api.link;

import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.business.api.space.ContainerIcon;
import eu.cec.digit.circabc.service.profile.permissions.LibraryPermissions;

/**
 * Business service to manage file links, folder links and shared spaces.
 *
 * @author Yanick Pignot
 */
public interface LinksBusinessSrv
{
    /**
     * Add a space or content link to a given parent. The name will be unique and unique and computed from the target name.
     *
     * @param parent								An existing parent
     * @param target								An existing link target
     * @return
     */
    public NodeRef createLink(final NodeRef parent, final NodeRef target);

    /**
     *  Add a share space link to the given parent. The name will be unique and unique and computed from the title.
     *
     * @param parent								An existing parent
     * @param target								An existing link target
     * @param title									A title (not empty) that will become the title
     * @param description							An optional descption of the link
     * @return
     * @
     */
    public NodeRef createSharedSpaceLink(final NodeRef parent, final NodeRef target, final String title, final String description);

    /**
     *  Share a space with an interest group by applying a gien permissions
     *
     * @param shareSpace							Any library space
     * @param interestGroup							Any interest group that is not shared yet with the space
     * @param libraryPermission						A library permission
     */
    public void applySharing(final NodeRef shareSpace, final NodeRef interestGroup, final LibraryPermissions libraryPermission);

	/**
	 *  Remove a sharing between a space and an interest group
	 *
	 * @param shareSpace							Any library space
	 * @param interestGroup							Any interest group that shared with the space
	 */
	public void removeSharing(final NodeRef shareSpace, final NodeRef interestGroup);

    /**
     * Return all available shared space for the given Interest group, Interest group child or category.
     *
     * @param nodeRef							Any nodeRef located at or under a category.
     * @return									The list of available SharedSpace. Never null.
     */
    public List<ShareSpaceItem> getAvailableSharedSpaces(final NodeRef nodeRef);

    /**
     * Return all shared space defined recursivly under any location.
     *
     * @param nodeRef							Any nodeRef located at or under a category.
     * @return									The list of available SharedSpace. Never null.
     */
    public List<ShareSpaceItem> findSharedSpaces(final NodeRef nodeRef);

    /**
     * Return all interest group that are available for an invitation for sharing
     *
     * @param nodeRef							Any library space
     * @return									The list avaliable Interest Group. Never null.
     */
    public List<InterestGroupItem> getInterestGroupForSharing(final NodeRef nodeRef);

    /**
     * Return all interest group that are already invited for sharing
     *
     * @param nodeRef							Any library space
     * @return									The list invited Interest Group. Never null.
     */
    public List<InterestGroupItem> getInvitationsForSharing(final NodeRef nodeRef);

    /**
     * Return the available icons for a folder link (shared or not).
     *
     * @return			All defined icons for folder link. Never null.
     */
    public List<ContainerIcon> getSpaceLinkIcons();


}
