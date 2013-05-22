package eu.cec.digit.circabc.web.wai.dialog.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.permissions.AccessDeniedException;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.Path;
import org.alfresco.service.cmr.repository.Path.ChildAssocElement;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.bean.repository.MapNode;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.bean.repository.NodePropertyResolver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.service.profile.CircabcRootProfileManagerService;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;
import eu.cec.digit.circabc.web.bean.override.CircabcBrowseBean;
import eu.cec.digit.circabc.web.wai.bean.navigation.CategoryHeadersBeanData;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

public class DeleteCategoryDialogBean extends BaseWaiDialog 
{
	private static final long serialVersionUID = 6314029149469295263L;
	private static final Log logger = LogFactory.getLog(DeleteCategoryDialogBean.class);

	private final String PARAM_OPERATION = "operation";
	private final String OPERATION_DELETE = "delete";
	private final String OPERATION_CLEAN = "clean";
	
	private final String MSG_DELETE_NO_PERMISSION_ADMIN = "delete_no_permission_admin";
	private final String MSG_DELETE_NO_PERMISSION = "delete_no_permission";
	
	//***********************************************************************
	//                                                              OVERRIDES
	//***********************************************************************
	
	@Override
	public void init(final Map<String, String> parameters)
	{
		super.init(parameters);
		this.nodesForDeletion = new ArrayList<MapNode>();
		if(parameters != null)
		{
			this.setOperation(parameters.get(PARAM_OPERATION));
			if(this.getOperation() != null)
			{
				//reset the list of deletable nodes
				NavigableNode cat = getNavigator().getCurrentCategory();
				List<NavigableNode> igs = cat.getNavigableChilds();
	
				if (this.getOperation().equals(OPERATION_CLEAN))
				{
					// mark all files and folders in the IGs
					for (NavigableNode ig: igs) 
					{
						markChildrenForDeletion(ig.getNodeRef());
					}
				}
				else if (this.getOperation().equals(OPERATION_DELETE))
				{
					if(checkCircabcAdminAccess())
					{
						setDeleteCategoryNode(true);
						this.nodesForDeletion.add(new MapNode(cat.getNodeRef()));
					}
					else
					{
						// mark all IGs of the category
						for (NavigableNode ig: igs) 
						{
							this.nodesForDeletion.add(new MapNode(ig.getNodeRef()));
						}
					}
				}
			}
		}
	}
	
	@Override
	protected String finishImpl(FacesContext context, String outcome)
		throws Exception {
		
		String userName = getNavigator().getCurrentUser().getUserName();
		if(isDeleteCategoryNode())
		{
			NodeRef currentCat = getNavigator().getCurrentCategory().getNodeRef();
			String catName = getNavigator().getCurrentCategory().getName();
			
			if(checkCircabcAdminAccess())
			{
				// grant the delete permission to current user
				getPermissionService().setPermission(
						currentCat,
						userName, 
						PermissionService.DELETE_NODE,
						true);
				
				// delete the category node
				getNodeService().deleteNode(currentCat);
				
				// log
				logger.debug("The category "+catName+" has been deleted successfully by "+userName);
			}
			
            //reset cache
            final CategoryHeadersBeanData categoryHeadersBeanData = (CategoryHeadersBeanData) Beans.getBean("CategoryHeadersBeanData");
            categoryHeadersBeanData.reset();

            // browse back to the circabc home
			getBrowseBean().clickWai(getNavigator().getCircabcHomeNode().getNodeRef());
		    return CircabcNavigationHandler.CLOSE_WAI_DIALOG_OUTCOME
            + CircabcNavigationHandler.OUTCOME_SEPARATOR
            + CircabcBrowseBean.PREFIXED_WAI_BROWSE_OUTCOME;
		}
		else
		{
			for(MapNode node: getNodesForDeletion())
			{
				try
				{
					getNodeService().deleteNode(node.getNodeRef());
					
					// log
					logger.debug("The node "+node.getName()+" has been deleted successfully by "+userName);
				}
				catch(InvalidNodeRefException inrex)
				{
					// node has already been deleted
					logger.warn("finishImpl:" + inrex.getMessage(), inrex);
				}
				catch(AccessDeniedException adex)
				{
					// do not have the permission to delete the node
					logger.error("finishImpl:" + adex.getMessage(), adex);
				}
			}

			return CircabcNavigationHandler.CLOSE_WAI_DIALOG_OUTCOME;
		}
	}

	@Override
	public String getContainerTitle()
	{
		String catName = getNavigator().getCurrentCategory().getName();
		String title = null;
		if (this.getOperation().equals(OPERATION_CLEAN))
		{
			title = this.translate("clean_category_title");
		}
		else
		{
			title = this.translate("delete_category_title");
		}
		return title + ": " + catName; 
	}
	
	public String getPageIconAltText() {
		return null;
	}

	public String getBrowserTitle() {
		return null;
	}

	//***********************************************************************
	//                                                      GETTER AND SETTER
	//***********************************************************************

	private String operation;
	private String getOperation() {
		return operation;
	}
	private void setOperation(String operation) {
		this.operation = operation;
	}

	private boolean deleteCategoryNode = false;
	public boolean isDeleteCategoryNode() {
		return deleteCategoryNode;
	}
	public void setDeleteCategoryNode(boolean deleteCategoryNode) {
		this.deleteCategoryNode = deleteCategoryNode;
	}

	private List<MapNode> nodesForDeletion;
	public List<MapNode> getNodesForDeletion() {
		return nodesForDeletion;
	}
	public void setNodesForDeletion(List<MapNode> nodes) {
		this.nodesForDeletion = nodes;
	}
	
	transient private NodeService internalNodeService;
	public void setInternalNodeService(NodeService internalNodeService) {
		this.internalNodeService = internalNodeService;
	}
	public NodeService getInternalNodeService() {
		if(internalNodeService == null)
        {
        	internalNodeService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getNonSecuredNodeService();
        }
        return this.internalNodeService;
	}
	
	//***************************************************************
	//                                              PROPERTY RESOLVER
	//***************************************************************
	
	private NodePropertyResolver resolverPath = new NodePropertyResolver() 
	{
		private static final long serialVersionUID = -2501720368642759082L;
		public Object get(Node node) 
		{
			boolean record = false;
			StringBuilder result = new StringBuilder();
			NodeRef cat = getNavigator().getCurrentCategory().getNodeRef();
			Path path = getNodeService().getPath(node.getNodeRef());
			for(int i=0; i<(path.size()-1); i++)
			{
				ChildAssocElement elem = (ChildAssocElement)path.get(i);
				ChildAssociationRef ref = elem.getRef();
				Node pathNode = new Node(ref.getChildRef());
				if(record)
				{
					result.append("/");
					result.append(pathNode.getName());
				}
				else if(cat.equals(pathNode.getNodeRef()))
				{
					record = true;
				}
			}
			return result.toString();
		}
	};
	
	private NodePropertyResolver resolverAuthor = new NodePropertyResolver() 
	{
		private static final long serialVersionUID = 9178556770343499694L;
		public Object get(Node node) {
			return node.getProperties().get(ContentModel.PROP_AUTHOR);
		}
	};
	
	private NodePropertyResolver resolverName = new NodePropertyResolver() 
	{
		private static final long serialVersionUID = 9178556770343499694L;
		public Object get(Node node) {
			return node.getProperties().get(ContentModel.PROP_NAME);
		}
	};
	
	//***********************************************************************
	//                                                         PRIVATE HELPER
	//***********************************************************************
	
	public boolean checkCircabcAdminAccess() 
	{
		NavigableNode node = getNavigator().getCircabcHomeNode();
		return node.hasPermission(CircabcRootProfileManagerService.Profiles.CIRCABC_ADMIN);
	}
	
	/**
	 * Recursive
	 */
	private void markChildrenForDeletion(NodeRef container)
	{
		List<ChildAssociationRef> childAssocs = getNodeService().getChildAssocs(container);
		for (ChildAssociationRef assoc: childAssocs) 
		{
			MapNode childNode = new MapNode(assoc.getChildRef(), getInternalNodeService(), false);
			childNode.addPropertyResolver("name", resolverName);
			childNode.addPropertyResolver("path", resolverPath);
			childNode.addPropertyResolver("author", resolverAuthor);
			
			QName childName = assoc.getQName();
			QName childType = getNodeService().getType(childNode.getNodeRef());
			if(childType.equals(CircabcModel.TYPE_INTEREST_GROUP_PROFILE ))
			{
				// IG Root
				markChildrenForDeletion(childNode.getNodeRef());
			}
			else if(childType.equals(ContentModel.TYPE_FOLDER))
			{
				if(childName.getLocalName().equals("Library"))
				{
					// IG Library
					markChildrenForDeletion(childNode.getNodeRef());
				}
				else if(childName.getLocalName().equals("Information"))
				{
					// IG Information
				}
				else if(childName.getLocalName().equals("Events"))
				{
					// IG Events
				}
				else
				{
					// common folder
					this.getNodesForDeletion().add(childNode);
					markChildrenForDeletion(childNode.getNodeRef());
				}
			}
			else if(childType.equals(ContentModel.TYPE_CONTENT ))
			{
				// common file
				this.getNodesForDeletion().add(childNode);
			}	
		}
	}	
}
