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
 * To take the ownership on a logical document, the current user must have the right to take ownsership
 * on all these translations.
 *
 * @author Yanick Pignot
 */
public class TakeOwnershipMLContainerEvaluator extends BaseActionEvaluator
{
	private static final long serialVersionUID = 2164368522290163419L;

	public boolean evaluate(final Node node)
	{
		final MultilingualContentService mlService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getMultilingualContentService();

		boolean allow = true;

		final Map<Locale, NodeRef> translations = mlService.getTranslations(node.getNodeRef());
		Node translation;
		for (final Map.Entry<Locale, NodeRef> entry : translations.entrySet())
		{
			translation = new Node(entry.getValue());

		    if(translation.hasPermission(PermissionService.TAKE_OWNERSHIP) == false)
		    {
		        allow = false;
		        break;
		    }
		}

		return allow;

	}

}
