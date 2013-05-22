/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.action.evaluator;

import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.ml.MultilingualContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.web.Services;


/**
* Evaluates whether the Modify mlContainer details is visible.
*
* @author Yanick Pignot
* */


public class ModifyMLDetailsEvaluator extends BaseActionEvaluator
{
	private static final long serialVersionUID = 2839741048206551370L;

	public boolean evaluate(final Node node) {

		final MultilingualContentService mlService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getMultilingualContentService();

		boolean allow = true;

		final Map<Locale, NodeRef> translations = mlService.getTranslations(node.getNodeRef());
		Node translation;
        for (final Map.Entry<Locale, NodeRef> entry : translations.entrySet())
        {
            translation = new Node(entry.getValue());

            if(translation.hasPermission(PermissionService.WRITE_PROPERTIES) == false)
            {
                allow = false;
                break;
            }
        }

		// must be not already versionable.
		return allow;

	}
}