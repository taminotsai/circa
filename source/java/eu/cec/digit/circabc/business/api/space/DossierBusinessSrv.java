/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.api.space;

import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Business service to manage Dossiers.
 *
 * @author Yanick Pignot
 */
public interface DossierBusinessSrv
{

    /**
     *  Add a dossier node a given parent. The name will be unique and unique and computed from the title.
     *
     * @param parent								An existing parent
     * @param title									A title (not empty) that will become the title
     * @param description							An optional descption of the dossier
     * @param iconName								An optional icon name of the dossier
     * @return
     */
    public NodeRef createDossier(final NodeRef parent, final String title, final String description, final String iconName);


    /**
     * Return the available icons for dossiers.
     *
     * @return			All defined icons for dossier. Never null.
     */
    public List<ContainerIcon> getDossierIcons();
}
