/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.api.space;

import java.util.Date;
import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Business service to manage spaces.
 *
 * @author Yanick Pignot
 */
public interface SpaceBusinessSrv
{
    /**
     *  Add a space node a given parent. The name will be unique and unique and computed from the title.
     *
     * @param parent								An existing parent
     * @param title									A title (not empty) that will become the title
     * @param description							An optional desciption of the space
     * @param iconName								An optional icon name of the space
     * @param expirationDate						An optional date for the space expiration (The date may be null but not earlier than today).
     * @return
     */
    public NodeRef createSpace(final NodeRef parent, final String title, final String description, final String iconName, final Date expirationDate);


    /**
     *  Add a space node a given parent. The name will be unique and unique and computed from the title.
     *
     * @param parent								An existing parent
     * @param title									A title (not empty) that will become the title
     * @param description							An optional descption of the space
     * @param icon									An optional icon of the space
     * @param expirationDate						An optional date for the space expiration (The date may be null but not earlier than today).
     * @return
     */
    public NodeRef createSpace(final NodeRef parent, final String title, final String description, final ContainerIcon icon, final Date expirationDate);

    /**
     * Return the available icons for spaces.
     *
     * @return			All defined icons for spaces. Never null.
     */
    public List<ContainerIcon> getSpaceIcons();

}
