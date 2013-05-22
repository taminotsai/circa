/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.model.ContentModel;
import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;


/**
* Evaluates whether the ApplyVersionable action should be visible.
*
*
* @author patrice.coppens@trasys.lu
* */


public class ApplyVersionableEvaluator extends BaseActionEvaluator {

	private static final long serialVersionUID = -1794534856856435723L;

	public boolean evaluate(final Node node) {

		// must be not already versionable.
		return ! (node.getAspects().contains(ContentModel.ASPECT_VERSIONABLE)) && !node.isLocked();

	}
}