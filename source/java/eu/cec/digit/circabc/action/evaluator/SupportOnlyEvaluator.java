/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import javax.faces.context.FacesContext;

import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.service.support.SupportService;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.Services;


/**
 * Due to a lack of the Alfresco security model, this evaluator tests if the current user is
 * administrator on <b>each</b> ig services.
 *
 * @author Stephane Clinckart
 **/
public class SupportOnlyEvaluator extends BaseActionEvaluator {

	private static final long serialVersionUID = -3812188271951597814L;

	public boolean evaluate(final Node node) {
		final SupportService supportService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getSupportService();
		final String currentUser = Beans.getWaiNavigator().getCurrentUser().getUserName();
		if(supportService.isUserFromSupport(currentUser))
			return true;
		else
			return false;
	}
}