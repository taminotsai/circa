/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.web.bean.repository.Node;


/**
 * Evaluates whether the current user can delete or edit a topic
 *
 * @author yanick pignot
 */
public class DeleteAndEditTopicEvaluator extends DeleteAndEditForumEvaluator
{
	private static final long serialVersionUID = 6427824694092387176L;

	public boolean evaluate(final Node node)
	{
		return super.evaluate(node);
	}
}
