/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.bean.repository.User;

import eu.cec.digit.circabc.service.struct.ManagementService;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.bean.override.CircabcNavigationBean;


/**
 * Evaluate if the current user can set its own notification status for the given node
 *
 * @author yanick pignot
 **/


public class EditOwnNotificationStatusEvaluator extends ViewNotificationEvaluator {

	private static final long serialVersionUID = -1711532256856435555L;

	public boolean evaluate(final Node node) 
	{		
		if(!super.evaluate(node) || node.isLocked())
		{
			return false;
		}
		
		final CircabcNavigationBean navigator = Beans.getWaiNavigator();
		final User user = navigator.getCurrentUser();
		if(user == null || user.isAdmin() || navigator.isGuest())
		{
			// guest and alfresco admin can't
			return false;
		}

		final ManagementService managementService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getManagementService();
		final NodeRef igRef = managementService.getCurrentInterestGroup(node.getNodeRef());
		if(igRef == null)
		{
			// we are not under an interest group
			return false;
		}

		return true;
	}
}