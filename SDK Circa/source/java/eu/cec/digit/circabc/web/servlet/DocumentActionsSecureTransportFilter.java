package eu.cec.digit.circabc.web.servlet;



import javax.servlet.http.HttpServletRequest;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.ml.MultilingualContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.model.DocumentModel;

public class DocumentActionsSecureTransportFilter extends BaseSecureTransportFilter {

	private static final Log logger = LogFactory.getLog(DocumentActionsSecureTransportFilter.class);
	@Override
	protected boolean isSecureDocument(HttpServletRequest httpReq) {
		boolean result = false;

		String formId = httpReq.getParameter("FormPrincipal:_idcl");

		if (formId != null &&
				( formId.startsWith("FormPrincipal:details_doc_wai_id") ||
				  formId.startsWith("FormPrincipal:checkin_doc_wai_id") ||
				  formId.startsWith("FormPrincipal:update_doc_wai_id") ||
				  formId.startsWith("FormPrincipal:copy_node_id") ||
				  formId.startsWith("FormPrincipal:edit_details_doc_wai_id") ||
				  formId.startsWith("FormPrincipal:checkout_doc_wai_id") ||
				  formId.startsWith("FormPrincipal:take_ownership_doc_id") ||
				  formId.startsWith("FormPrincipal:update_doc_wai_id") ||
				  formId.startsWith("FormPrincipal:cut_node_id") ||
				  formId.startsWith("FormPrincipal:delete_doc_wai_id") ||
				  formId.startsWith("FormPrincipal:make_doc_multilingual_wai_id") ||
				  formId.startsWith("FormPrincipal:apply_versionable_id") ||
				  formId.startsWith("FormPrincipal:create_forum_node_wai_id") ||
				  formId.startsWith("FormPrincipal:add_translation_wai_id") ||
				  formId.startsWith("FormPrincipal:add_translation_without_content_wai_id") ||
				  formId.startsWith("FormPrincipal:ml_details_wai_id")
				))
		{
			NodeRef nodeRef ;
			String id ;
			id  = httpReq.getParameter("id");
			String ref ;
			ref  = httpReq.getParameter("ref");
			if (id != null)
			{
				ServiceRegistry serviceRegistry = getServiceRegistry(getServletContext() );
				final StoreRef spaceStroreRef = new StoreRef("workspace", "SpacesStore");
				nodeRef = new NodeRef(spaceStroreRef, id);
			    NodeService nodeService = serviceRegistry.getNodeService();
			    if (nodeService.hasAspect(nodeRef, org.alfresco.model.ContentModel.ASPECT_MULTILINGUAL_DOCUMENT))
				{
			    	MultilingualContentService multilingualContentService = serviceRegistry.getMultilingualContentService();
			    	nodeRef = multilingualContentService.getTranslationContainer(nodeRef);
				}
				String securityRanking = (String) nodeService.getProperty(nodeRef, DocumentModel.PROP_SECURITY_RANKING);
			    result =   securityRanking != null && !securityRanking.equalsIgnoreCase("PUBLIC") ;
			}
			else if (ref != null)
			{
				nodeRef = new NodeRef(ref);
				ServiceRegistry serviceRegistry = getServiceRegistry(getServletContext() );
			    NodeService nodeService = serviceRegistry.getNodeService();
			    if (nodeService.hasAspect(nodeRef, org.alfresco.model.ContentModel.ASPECT_MULTILINGUAL_DOCUMENT))
				{
			    	MultilingualContentService multilingualContentService = serviceRegistry.getMultilingualContentService();
			    	nodeRef = multilingualContentService.getTranslationContainer(nodeRef);
				}
				String securityRanking = (String) nodeService.getProperty(nodeRef, DocumentModel.PROP_SECURITY_RANKING);
			    result =   securityRanking != null && !securityRanking.equalsIgnoreCase("PUBLIC") ;

			}
			else
			{
				logger.warn("Request parameter id and ref are null! ");
			}
		}

		return result;



	}
	@Override
	protected Log getLogger() {

		return logger;
	}

}
