/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.model.ForumModel;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.model.DocumentModel;

/**
 *	Class containing behaviour for the post type.
 * 	Inside Circabc, all posts must be versionnable.
 *
 * 	{@link ForumModel#TYPE_POST post type}
 *
 * @author Yanick Pignot
 */
public class PostType
{

	private static final Log logger = LogFactory.getLog(PostType.class);

   //     Dependencies
   private PolicyComponent policyComponent;
   private NodeService nodeService;

   public void init()
   {
       this.policyComponent.bindClassBehaviour(
               QName.createQName(NamespaceService.ALFRESCO_URI, "onCreateNode"),
               ForumModel.TYPE_POST,
               new JavaBehaviour(this, "makeVersionnable"));

       this.policyComponent.bindClassBehaviour(
               QName.createQName(NamespaceService.ALFRESCO_URI, "onCreateNode"),
               ForumModel.TYPE_TOPIC,
               new JavaBehaviour(this, "addBProperties"));
   }

   /**
    * @param policyComponent the policy component to register behaviour with
    */
   public void setPolicyComponent(final PolicyComponent policyComponent)
   {
       this.policyComponent = policyComponent;
   }

   public void makeVersionnable(final ChildAssociationRef childAssocRef)
   {
	   final NodeRef postRef = childAssocRef.getChildRef();
	   if(isArchived(postRef) == false && nodeService.hasAspect(postRef, ContentModel.ASPECT_VERSIONABLE) == false)
	   {
			//add versionable aspect (set auto-version)
			final Map<QName, Serializable> versionProps = new HashMap<QName, Serializable>();
			versionProps.put(ContentModel.PROP_AUTO_VERSION, true);
			nodeService.addAspect(postRef, ContentModel.ASPECT_VERSIONABLE, versionProps);

			if(logger.isInfoEnabled())
			{
				logger.info("Add AutoVersionning on post:" + postRef);
			}
	   }
   }

   public void addBProperties(final ChildAssociationRef childAssocRef)
   {
	   final NodeRef topicRef = childAssocRef.getChildRef();
	   if(isArchived(topicRef) == false && nodeService.hasAspect(topicRef, DocumentModel.ASPECT_BPROPERTIES) == false)
	   {
			final Map<QName, Serializable> bProps = new HashMap<QName, Serializable>(2);
			bProps.put(DocumentModel.PROP_EXPIRATION_DATE, null);
			bProps.put(DocumentModel.PROP_SECURITY_RANKING, DocumentModel.SECURITY_RANKINGS_PUBLIC);
	        nodeService.addAspect(topicRef, DocumentModel.ASPECT_BPROPERTIES, bProps);

	        if (logger.isInfoEnabled())
	        {
	        	logger.info("addBPropertiesToTopics with properties: " + bProps + " on " + topicRef);
	        }
	   }
   }

   public final void setNodeService(NodeService nodeService)
   {
	   this.nodeService = nodeService;
   }

   private final boolean isArchived(final NodeRef nodeRef)
   {
	   return StoreRef.STORE_REF_ARCHIVE_SPACESSTORE.equals(nodeRef.getStoreRef());
   }
}
