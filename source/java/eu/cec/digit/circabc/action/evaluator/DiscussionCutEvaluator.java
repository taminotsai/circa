/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.web.bean.repository.Node;


/**
 * Evaluate if the current user can accept a meeting. It means check if it is not already done.
 *
 * @author Yanick Pignot
 */
public class DiscussionCutEvaluator extends org.alfresco.web.action.evaluator.DiscussionCutEvaluator
{
	private static final long serialVersionUID = 456936852785621419L;

	public boolean evaluate(final Node node)
	{
		return !node.isLocked() && super.evaluate(node);
	}
}
