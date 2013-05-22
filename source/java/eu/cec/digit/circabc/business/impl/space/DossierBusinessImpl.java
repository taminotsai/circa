/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.impl.space;

import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import eu.cec.digit.circabc.business.api.space.ContainerIcon;
import eu.cec.digit.circabc.business.api.space.DossierBusinessSrv;
import eu.cec.digit.circabc.model.DossierModel;

/**
 * Business service implementation to manage Dossiers.
 *
 * @author Yanick Pignot
 */
public class DossierBusinessImpl extends ContainerBaseBusinessService implements DossierBusinessSrv
{

    //--------------
    //-- public methods

    /* (non-Javadoc)
     * @see eu.cec.digit.circabc.business.api.space.DossierBusinessSrv#createDossier(org.alfresco.service.cmr.repository.NodeRef, java.lang.String, java.lang.String)
     */
    public NodeRef createDossier(final NodeRef parent, final String title, final String description, final String iconName)
    {
        return createContainerImpl(parent, DossierModel.TYPE_DOSSIER_SPACE, title, description, getConfigManager().getIcon(DossierModel.TYPE_DOSSIER_SPACE, iconName), null);
    }

    /* (non-Javadoc)
     * @see eu.cec.digit.circabc.business.api.space.DossierBusinessSrv#getDossierIcons()
     */
    public List<ContainerIcon> getDossierIcons()
    {
        return getIcons();
    }

    @Override
    protected QName getIconType()
	{
		return DossierModel.TYPE_DOSSIER_SPACE;
	}

    //--------------
    //-- private helpers



    //--------------
    //-- IOC

   }
