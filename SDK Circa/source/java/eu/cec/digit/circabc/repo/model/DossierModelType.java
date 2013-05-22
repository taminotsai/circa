package eu.cec.digit.circabc.repo.model;
import org.alfresco.model.ApplicationModel;
import org.alfresco.model.ContentModel;
import org.alfresco.model.ForumModel;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.model.DossierModel;

/**
 * Dossier model type behaviour.
 * Do not allow user to add content to dossier (only file links and folder links are allowed)
 *
 * @author Slobodan Filipovic
 */
public class DossierModelType implements NodeServicePolicies.BeforeCreateNodePolicy
{

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(DossierModelType.class);

	 /** The policy component */
    private PolicyComponent policyComponent;

    /** The node service */
    private NodeService nodeService;

	/**
	 * Spring initialise method used to register the policy behaviours
	 */
	public void init(){
		// Register the policy behaviours
        policyComponent.bindClassBehaviour(
                QName.createQName(NamespaceService.ALFRESCO_URI, "beforeCreateNode"),
                ContentModel.TYPE_CMOBJECT,
                new JavaBehaviour(this, "beforeCreateNode"));

	}

	/**
	 * Allow only to create types filelink and folderlink in dossier
	 */
	public void beforeCreateNode(final NodeRef parentRef, final QName assocTypeQName, final QName assocQName, final QName nodeTypeQName) {
		final QName parentType =  nodeService.getType(parentRef);
		if (parentType.equals(DossierModel.TYPE_DOSSIER_SPACE) &&
				!(nodeTypeQName.equals(ApplicationModel.TYPE_FILELINK) || nodeTypeQName.equals(ApplicationModel.TYPE_FOLDERLINK)|| nodeTypeQName.equals(ForumModel.TYPE_FORUM)))
		{
			throw new RuntimeException("You can paste only links in dossier.");
		}
	}

	/**
     * Set the policy component
     *
     * @param policyComponent   the policy component
     */
    public void setPolicyComponent(final PolicyComponent policyComponent)
    {
        this.policyComponent = policyComponent;
    }

    /**
     * Set the node service
     *
     * @param nodeService       the node service
     */
    public void setNodeService(final NodeService nodeService)
    {
        this.nodeService = nodeService;
    }



}
