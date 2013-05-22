/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.service.profile.permissions.CategoryPermissions;
import eu.cec.digit.circabc.service.profile.permissions.CircabcRootPermissions;
import eu.cec.digit.circabc.service.profile.permissions.DirectoryPermissions;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;


/**
 * Evaluate if the current user can send an email to the members.
 *
 * @author Yanick Pignot
 **/
public class MailToMembersEvaluator extends BaseActionEvaluator {

	private static final long serialVersionUID = 969643930963034329L;

	public boolean evaluate(final Node node)
	{
		if(NavigableNodeType.CIRCABC_ROOT.isNodeFromType(node))
		{
			if(Beans.getWaiNavigator().getCurrentUser().isAdmin())
			{
				return true;
			}
			else
			{
				return node.hasPermission(CircabcRootPermissions.CIRCABCMANAGEMEMBERS.toString());
			}
		}
		else if(NavigableNodeType.CATEGORY.isNodeFromType(node))
		{
			return node.hasPermission(CategoryPermissions.CIRCACATEGORYMANAGEMEMBERS.toString());
		}
		else
		{
			return node.hasPermission(DirectoryPermissions.DIRMANAGEMEMBERS.toString());
		}
	}
}