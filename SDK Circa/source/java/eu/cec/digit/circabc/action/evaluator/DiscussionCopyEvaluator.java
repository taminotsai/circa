/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.web.bean.repository.Node;


/**
 *
 * @author Yanick Pignot
 */
public class DiscussionCopyEvaluator extends org.alfresco.web.action.evaluator.DiscussionCopyEvaluator
{
	private static final long serialVersionUID = 216431234585621419L;

	public boolean evaluate(final Node node)
	{
		return !node.isLocked() && super.evaluate(node) ;
	}

}
