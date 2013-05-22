/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.impl.space;

import java.util.Date;
import java.util.List;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import eu.cec.digit.circabc.business.api.space.ContainerIcon;
import eu.cec.digit.circabc.business.api.space.SpaceBusinessSrv;

/**
 * Business service implementation to manage spaces.
 *
 * @author Yanick Pignot
 */
public class SpaceBusinessImpl extends ContainerBaseBusinessService implements SpaceBusinessSrv
{
    //--------------
    //-- public methods

    /* (non-Javadoc)
     * @see eu.cec.digit.circabc.business.api.space.SpaceBusinessSrv#createSpace(org.alfresco.service.cmr.repository.NodeRef, java.lang.String, java.lang.String, java.lang.String, java.util.Date)
     */
    public NodeRef createSpace(final NodeRef parent, final String title, final String description, final String iconName, final Date expirationDate)
    {
    	return createSpace(parent, title, description, getConfigManager().getIcon(ContentModel.TYPE_FOLDER, iconName), expirationDate);
    }

    /* (non-Javadoc)
     * @see eu.cec.digit.circabc.business.api.space.SpaceBusinessSrv#createSpace(org.alfresco.service.cmr.repository.NodeRef, java.lang.String, java.lang.String, eu.cec.digit.circabc.business.api.space.ContainerIcon, java.util.Date)
     */
    public NodeRef createSpace(final NodeRef parent, final String title, final String description, final ContainerIcon icon, final Date expirationDate)
    {
        return createContainerImpl(parent, ContentModel.TYPE_FOLDER, title, description, icon, expirationDate);
    }

    /* (non-Javadoc)
     * @see eu.cec.digit.circabc.business.api.space.SpaceBusinessSrv#getSpaceIcons()
     */
    public List<ContainerIcon> getSpaceIcons()
    {
        return getIcons();
    }
    
    @Override
    protected QName getIconType()
	{
		return ContentModel.TYPE_FOLDER;
	}

    //--------------
    //-- private helpers


  
    //--------------
    //-- IOC
}
