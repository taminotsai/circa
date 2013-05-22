/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.INFORMATION_CHILD;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.LIBRARY_CHILD;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.NEWSGROUP_CHILD;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.SURVEY_CHILD;

import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

/**
 * Evaluate if the user can manage the permission of the current space
 *
 * @author Yanick Pignot
 */
public class ManageSpacePermissionEvaluator extends BaseActionEvaluator
{
	private static final long serialVersionUID = 308051234539899525L;

	/**
	 * Return true if the given node is a library space and not if libarry root
	 */
	public boolean evaluate(final Node node)
	{
		return LIBRARY_CHILD.isNodeFromType(node) ||
				NEWSGROUP_CHILD.isNodeFromType(node) ||
				INFORMATION_CHILD.isNodeFromType(node) ||
				SURVEY_CHILD.isNodeFromType(node);

	}
}
