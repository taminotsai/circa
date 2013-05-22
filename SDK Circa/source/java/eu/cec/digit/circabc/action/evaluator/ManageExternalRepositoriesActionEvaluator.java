package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.service.external.repositories.ExternalRepositoriesManagementService;

/**
 * Evaluates if the external repositories management action has to be shown 
 * in the admin page.
 * 
 * @author schwerr
 */
public class ManageExternalRepositoriesActionEvaluator extends
		AllIgServicesAdminEvaluator {
	
	private static final long serialVersionUID = -4285205449710953828L;
	
	private static ExternalRepositoriesManagementService externalRepositoriesManagementService = null;
	
	/**
	 * @see eu.cec.digit.circabc.action.evaluator.AllIgServicesAdminEvaluator#evaluate(org.alfresco.web.bean.repository.Node)
	 */
	@Override
	public boolean evaluate(Node node) {
		return super.evaluate(node) && externalRepositoriesManagementService.isOperational();
	}
	
	/**
	 * Sets the value of the externalRepositoriesManagementService
	 * 
	 * @param externalRepositoriesManagementService the externalRepositoriesManagementService to set.
	 */
	public static void setExternalRepositoriesManagementService(
			ExternalRepositoriesManagementService externalRepositoriesManagementService) {
		ManageExternalRepositoriesActionEvaluator.externalRepositoriesManagementService = externalRepositoriesManagementService;
	}
}
