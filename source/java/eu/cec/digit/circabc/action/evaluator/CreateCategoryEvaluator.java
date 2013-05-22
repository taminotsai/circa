/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.bean.override.CircabcNavigationBean;

/**
 * Evaluate if the user can create a category.
 *
 *
 * @author Yanick Pignot
 */
public class CreateCategoryEvaluator extends BaseActionEvaluator
{
	private static final long serialVersionUID = 308051234539899525L;

	/**
	 * Return false if the given node is a dossier
	 */
	public boolean evaluate(final Node node)
	{
		if(NavigableNodeType.CIRCABC_ROOT.isNodeFromType(node) == true || NavigableNodeType.CATEGORY_HEADER.isNodeFromType(node) == true)
		{
			return isSuperAdmin() || isCircabcAdmin();
		}
		else
		{
			return false;
		}
	}

	private boolean isSuperAdmin()
	{
		final CircabcNavigationBean navigator = Beans.getWaiNavigator();

		return navigator.getCurrentUser().isAdmin();
	}

	private boolean isCircabcAdmin()
	{
		final CircabcNavigationBean navigator = Beans.getWaiNavigator();

		return navigator.getCircabcHomeNode().hasPermission("CircaBCAdmin");
	}
}
