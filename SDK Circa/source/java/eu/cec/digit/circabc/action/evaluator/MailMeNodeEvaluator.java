/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.ml.MultilingualContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.model.DocumentModel;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.Services;

/**
 * We can't email a document if it is an empty translation.
 *
 * @author Yanick Pignot
 */
public class MailMeNodeEvaluator extends BaseActionEvaluator
{
	private static final long serialVersionUID = 2164369999290163419L;

	public boolean evaluate(final Node node)
	{
		if (Beans.getWaiNavigator().getIsGuest())
		{
			return false;
		}
		if (node.hasAspect(org.alfresco.model.ContentModel.ASPECT_MULTILINGUAL_EMPTY_TRANSLATION))
		{
			return false;
		}
		Node containerNode = null;
		if (node.hasAspect(org.alfresco.model.ContentModel.ASPECT_MULTILINGUAL_DOCUMENT))
		{
			MultilingualContentService multilingualContentService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getMultilingualContentService() ;
			NodeRef  containerNodeRef  = multilingualContentService.getTranslationContainer(node.getNodeRef());
			containerNode =  new Node(containerNodeRef);
		}
		else
		{
			containerNode =  node;
		}
		Object securityRanking = containerNode.getProperties().get(DocumentModel.PROP_SECURITY_RANKING.toString());
		if  (securityRanking != null)
		{
			final String securityRankingString = securityRanking.toString();
			if ( !securityRankingString.equalsIgnoreCase("PUBLIC"))
			{
				return false;
			}

		}
		return true;


	}

}
