/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;


/**
 * Test if the document editable (locked or not)
 *
 * @author Yanick Pignot
 */
public class NodeEditableEvaluator extends BaseActionEvaluator
{
	private static final long serialVersionUID = 2888369999290163419L;

	public boolean evaluate(final Node node)
	{
		return !node.isLocked();
	}

}
