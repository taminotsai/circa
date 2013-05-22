package eu.cec.digit.circabc.action.evaluator;

import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.service.external.repositories.ExternalRepositoriesManagementService;
import eu.cec.digit.circabc.web.Services;

/**
 * Evaluator to determine if there are external repositories configured at the 
 * IG root level and therefore show the action to publish into them.
 * It also evaluates if the current node was already published and if the 
 * current user can publish.
 * 
 * @author schwerr
 */
public class ExternalRepositoryActionEvaluator extends BaseActionEvaluator {
	
	private static final long serialVersionUID = -3696155440737331589L;
	
	private static ExternalRepositoriesManagementService externalRepositoriesManagementService = null;
	
	/**
	 * @see org.alfresco.web.action.evaluator.BaseActionEvaluator#evaluate(org.alfresco.web.bean.repository.Node)
	 */
	@Override
	public boolean evaluate(Node node) {
		
		final FacesContext fc = FacesContext.getCurrentInstance();
		final NodeService nodeService = 
					Services.getAlfrescoServiceRegistry(fc).getNodeService();
		
		NodeRef documentNodeRef = node.getNodeRef();
		
		// Evaluate if the current node is a working copy
		if (nodeService.hasAspect(documentNodeRef, ContentModel.ASPECT_WORKING_COPY)) {
			return false;
		}
		
		// Evaluate if the current user can publish
		String userName = externalRepositoriesManagementService.
									getUserNameResolver().getUserName();
		if (!externalRepositoriesManagementService.isUserAuthorizedtoPublish(userName)) {
			return false;
		}
		
		// Evaluate if the node was already published
		if (externalRepositoriesManagementService.wasPublishedTo(
				ExternalRepositoriesManagementService.EXTERNAL_REPOSITORY_NAME, 
				documentNodeRef.toString())) {
			return false;
		}
		
		// Evaluate if the external repository was configured
		NodeRef igNodeRef = getIGRoot(documentNodeRef, nodeService);
		
		if (igNodeRef != null) {
			
			NodeRef externalRepositoriesNodeRef = nodeService.
					getChildByName(igNodeRef, ContentModel.ASSOC_CONTAINS, 
										"ExternalRepositoryConfigurations");
			
			return externalRepositoriesNodeRef != null && 
				nodeService.getChildAssocs(externalRepositoriesNodeRef).size() > 0;
		}
		
		return false;
	}
	
	/**
	 * Retrieves the nodeRef of the IG where this nodeRef is stored.
	 * If there is no IG root, it returns null.
	 * 
	 * @param nodeRef
	 * @param nodeService
	 * @return
	 */
	public static NodeRef getIGRoot(NodeRef nodeRef, NodeService nodeService) {
		
		if (nodeRef == null) {
			return null;
		}
		
		do {
			nodeRef = nodeService.getParentAssocs(nodeRef).get(0).getParentRef();
		} while (nodeRef != null && !nodeService.hasAspect(nodeRef, CircabcModel.ASPECT_IGROOT));
		
		if (nodeRef != null && nodeService.hasAspect(nodeRef, CircabcModel.ASPECT_IGROOT)) {
			return nodeRef;
		}
		
		return null;
	}
	
	/**
	 * Sets the value of the externalRepositoriesManagementService
	 * 
	 * @param externalRepositoriesManagementService the externalRepositoriesManagementService to set.
	 */
	public static void setExternalRepositoriesManagementService(
			ExternalRepositoriesManagementService externalRepositoriesManagementService) {
		ExternalRepositoryActionEvaluator.externalRepositoriesManagementService = 
				externalRepositoriesManagementService;
	}
}
