/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.model.ModerationModel;
import eu.cec.digit.circabc.service.profile.permissions.NewsGroupPermissions;

/**
 * Evaluates whether the current user can post a reply or not.
 *
 * @author yanick pignot
 */
public class PostReplyEvaluator extends BaseActionEvaluator
{
	private static final long serialVersionUID = 6422283424092387176L;

	public boolean evaluate(final Node node)
	{
		if(node.hasAspect(ModerationModel.ASPECT_WAITING_APPROVAL)
				|| node.hasAspect(ModerationModel.ASPECT_REJECTED))
		{
			return false;
		}
		if(node.hasAspect(CircabcModel.ASPECT_NEWSGROUP))
		{
			return node.hasPermission(NewsGroupPermissions.NWSPOST.toString());
		}
		else if(node.hasAspect(CircabcModel.ASPECT_LIBRARY))
		{
			return node.hasPermission(PermissionService.CREATE_CHILDREN);
		}
		else
		{
			return false;
		}
	}

}
