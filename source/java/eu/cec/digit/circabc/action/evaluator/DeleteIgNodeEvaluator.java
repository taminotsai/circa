/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.profile.CategoryProfileManagerService;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.repository.CategoryNode;


/**
 * Evaluate if current user has Delete Permission on an Interest Group
 * (in other words: Is the current user a CategoryAdmin?)
 *
 * @author Stephane Clinckart
 **/


public class DeleteIgNodeEvaluator extends BaseActionEvaluator {

	/** A logger for the class */
	private static final Log logger = LogFactory.getLog(DeleteIgNodeEvaluator.class);

	private static final long serialVersionUID = -3468920341034630518L;

	/**
	 *
	 */


	public boolean evaluate(final Node node) {

		boolean isAdmin = false;

		final CategoryNode categoryNode = (CategoryNode)Beans.getWaiNavigator().getCurrentCategory();

		if(categoryNode != null) {
			if(categoryNode.hasPermission(CategoryProfileManagerService.Profiles.CIRCA_CATEGORY_ADMIN)) {
				isAdmin = true;
			}
		}
		if(logger.isTraceEnabled()) {
			logger.trace("return :" + isAdmin);
		}

		return isAdmin;
	}
}