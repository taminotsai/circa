/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.IG_ROOT;

import java.util.List;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.web.Services;

/**
 * Return true if the given node
 *
 * @author Yanick Pignot
 */
public class CreateSurveyEvaluator extends AllIgServicesAdminEvaluator
{
	private static final long serialVersionUID = 308059674639899525L;

	/**
	 * return true if the current node is a ig root and this ig has not a survey space yet
	 */
	public boolean evaluate(final Node node)
	{
		boolean result = false;

		if(IG_ROOT.isNodeFromType(node) && super.evaluate(node))
		{
			final FacesContext fc = FacesContext.getCurrentInstance();
			final NodeService nodeService = Services.getAlfrescoServiceRegistry(fc).getNodeService();

			final List<ChildAssociationRef> childAssocs = nodeService.getChildAssocs(node.getNodeRef());
			for(final ChildAssociationRef assoc : childAssocs)
			{
				if(nodeService.hasAspect(assoc.getChildRef(), CircabcModel.ASPECT_SURVEY_ROOT))
				{
					result = false;
					break;
				}
				else
				{
					result = true;
				}
			}
		}


		return result;
	}

}
