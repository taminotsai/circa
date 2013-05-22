/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.model.ModerationModel;
import eu.cec.digit.circabc.service.profile.permissions.LibraryPermissions;
import eu.cec.digit.circabc.service.profile.permissions.NewsGroupPermissions;

/**
 * Evaluates whether the current user can remove an abuse the given node in the moderation process
 *
 * @author yanick pignot
 */
public class RemoveAbuseEvaluator extends BaseActionEvaluator
{
	private static final long serialVersionUID = 6112283424092389996L;

	public boolean evaluate(final Node node)
	{
		if(node.hasAspect(ModerationModel.ASPECT_ABUSE_SIGNALED))
		{
			if(node.hasAspect(CircabcModel.ASPECT_LIBRARY))
			{
				return node.hasPermission(LibraryPermissions.LIBADMIN.toString());
			}
			else
			{
				return node.hasPermission(NewsGroupPermissions.NWSMODERATE.toString());
			}
		}
		else
		{
			return false;
		}
	}
}
