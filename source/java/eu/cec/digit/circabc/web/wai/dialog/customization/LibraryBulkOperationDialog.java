package eu.cec.digit.circabc.web.wai.dialog.customization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.alfresco.model.ApplicationModel;
import org.alfresco.model.ContentModel;
import org.alfresco.model.ForumModel;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.lock.NodeLockedException;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.bean.clipboard.ClipboardBean;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;
import eu.cec.digit.circabc.web.ui.common.component.UIActionLink;
import eu.cec.digit.circabc.web.wai.bean.navigation.ResolverHelper;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;
import eu.cec.digit.circabc.web.wai.dialog.generic.ManagedNodes;

public class LibraryBulkOperationDialog extends BaseWaiDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5337175195473365007L;


	public String getPageIconAltText() {
		return translate("library_bulk_operation_dialog_icon_tooltip");
	}


	public String getBrowserTitle() {
		return translate("library_bulk_operation_dialog_browser_title");
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) {
				
		
		for (NavigableNode navNode : getContainers())
		{
			if (navNode.isSelected())
			{
				navNode.getName();
			}
		}
		

				
		String bulkOperation = getSelectedOperation();
		ClipboardBean clipboardBean = Beans.getClipboardBean();
		
		String[] selectedNodes = (String[]) ArrayUtils.addAll(this.getSelectedContents(), this.getSelectedContainers());
		
		for (String nodeRef : selectedNodes) {
			if (bulkOperation.equals("delete"))
			{
				deleteNode(nodeRef);
				continue;
			}			
			UIActionLink link = new UIActionLink();
			link.getParameterMap().put("ref", nodeRef);
			ActionEvent ae = new ActionEvent(link);
			if (bulkOperation.equals("copy"))
			{
				clipboardBean.copyNode(ae);
			}
			if (bulkOperation.equals("cut"))
			{ 
				clipboardBean.cutNode(ae);
			}
		}

				
		
		return outcome;
	}
	
    public void init(final Map<String, String> parameters)
    {
    	super.init(parameters);
    	this.contents = null;
    	this.containers = null;
    }

    
    private void deleteNode(String nodeRef)
    {    
    	NodeRef currentNodeRef = new NodeRef(nodeRef);
    	Node currentNode = new Node(currentNodeRef);
    	ManagedNodes nodeType = ManagedNodes.resolve(currentNode);
    	
		if(currentNode != null && nodeType != null)
		{
			if(ManagedNodes.ML_CONTAINER.equals(nodeType))
	        {
	            if (logger.isDebugEnabled())
	                logger.debug("Trying to delete multilingual container: " + currentNode.getId() + " and its translations" );
	            // delete the mlContainer and its translations
	            getMultilingualContentService().deleteTranslationContainer(currentNodeRef);
	        }
	        else
	        {
	            if (logger.isDebugEnabled())
	                logger.debug("Trying to delete " + nodeType.toString() +  " node: " + currentNode.getId());
	
	            final String  nodeName = (String) this.getNodeService().getProperty(currentNodeRef, ContentModel.PROP_NAME );
	            logRecord.setInfo("deleted " + nodeName);
	            try {
	            	// delete the node
	            	this.getNodeService().deleteNode(currentNodeRef);
	            } catch(final NodeLockedException e) {
	            	if(logger.isWarnEnabled()) {
						logger.warn("Can't delete the node - node is locked:" + currentNode.getNodeRef());
	            	}
	            	Utils.addStatusMessage(FacesMessage.SEVERITY_WARN, translate("msg_error_cant_delete_space_that_contains_checkout_documents"));
	            }
	        }
		}
    }
    
	
	private static final Log logger = LogFactory.getLog(LibraryBulkOperationDialog.class);
	/** Lists of container nodes for display */
	private List<NavigableNode> containers = null;
	/** Lists of content nodes for display */
	private List<NavigableNode> contents = null;
	
	/**
	 * Get list of container nodes for the currentNodeRef
	 *
	 * @return The list of container nodes for the currentNodeRef
	 */
	public List<NavigableNode> getContainers()
	{
		if (this.containers == null)
		{
			fillBrowseNodes(getNavigator().getCurrentNode());
		}

		return this.containers;
	}
	
	public void setContainers(List<NavigableNode> containers)
	{		
		this.containers = containers;
	}
	
	
    // Init --------------------------------------------------------------------------------------

    private List<NavigableNode> selectedDataList;

    // Actions -----------------------------------------------------------------------------------

    private Map<Long, Boolean> selectedIds = new HashMap<Long, Boolean>();
    
    public Map<Long, Boolean> getSelectedIds() {
        return selectedIds;
    }
    
    public String getSelectedItems() {

        // Get selected items.
        selectedDataList = new ArrayList<NavigableNode>();
        for (NavigableNode dataItem : containers) {
            if (dataItem.isSelected()) {
                selectedDataList.add(dataItem);
                dataItem.setSelected(false); // Reset.
            }
        }

        // Do your thing with the MyData items in List selectedDataList.

        return "selected"; // Navigation case.
    }

    // Getters -----------------------------------------------------------------------------------

    public List<NavigableNode> getSelectedDataList() {
        return selectedDataList;
    }	
	
	
    private String selectedOperation;  
    
    public String getSelectedOperation() {  
        if (selectedOperation == null) {  
        	selectedOperation = "copy"; // This will be the default selected item.  
        }  
        return selectedOperation;  
    }  
       
    public void setSelectedOperation(String selectedOperation) {  
        this.selectedOperation = selectedOperation;  
    }  
       
    public List<SelectItem> getOperations() {  
    	List<SelectItem> operations = new ArrayList<SelectItem>();  
        operations.add(new SelectItem("copy", translate("library_bulk_operation_dialog_copy")));  
        operations.add(new SelectItem("cut", translate("library_bulk_operation_dialog_cut")));  
        operations.add(new SelectItem("delete", translate("library_bulk_operation_dialog_delete")));
        return operations;
    }
    
    
		
	String[] selectedContainers;	

	public String[] getSelectedContainers() {
		   return selectedContainers;
	}

	public void setSelectedContainers(String containers[]) {
	   this.selectedContainers = containers.clone();
	}
	
	
	String[] selectedContents;
	
	public String[] getSelectedContents() {
		   return selectedContents;
	}

	public void setSelectedContents(String contents[]) {
	   this.selectedContents = contents.clone();
	}

	
	ArrayList<SelectItem> allContainers;
	
	public List<SelectItem> getAllContainers() {
		allContainers = new ArrayList<SelectItem>();
		for (NavigableNode node : getContainers())
	    {
		   allContainers.add(new SelectItem(node.getNodeRefAsString(), node.getName()));
	    }
		return allContainers;
	}
	
	
	ArrayList<SelectItem> allContents;
	
	public List<SelectItem> getAllContents() {
		allContents = new ArrayList<SelectItem>();
		for (NavigableNode node : getContents())
	    {
			allContents.add(new SelectItem(node.getNodeRefAsString(), node.getName()));
	    }
		return allContents;
	}	
	

	/**
	 * Get list of content nodes for the currentNodeRef
	 *
	 * @return The list of content nodes for the currentNodeRef
	 */
	public List<NavigableNode> getContents()
	{
		if (this.contents == null || this.contents.isEmpty())
		{
			// user performs a simple browsing
			fillBrowseNodes(getNavigator().getCurrentNode());
		}

		return this.contents;
	}	
	
	
	/**
	 * Query a list of nodes for the specified parent node Id<br>
	 * Based on BrowseBean.queryBrowseNodes()
	 *
	 * @param currentNode of the parent node or null for the root node
	 */
	private void fillBrowseNodes(final Node currentNode)
	{
		long startTime = 0;
		if (logger.isDebugEnabled())
			startTime = System.currentTimeMillis();

		final FacesContext context = FacesContext.getCurrentInstance();
		final RetryingTransactionHelper txnHelper = Repository.getRetryingTransactionHelper(context);
		final RetryingTransactionCallback<Object> callback = new RetryingTransactionCallback<Object>()
		{
			public Object execute() throws Throwable
			{
				try
				{
					final List<FileInfo> children = getFileFolderService().list(currentNode.getNodeRef());
					containers = new ArrayList<NavigableNode>(children.size());
					contents = new ArrayList<NavigableNode>(children.size());				
					
					//NodeRef nodeRef;
					QName type;
					for (final FileInfo fileInfo : children)
					{
						type = getNodeService().getType(fileInfo.getNodeRef());
						if(fileInfo.isLink())
						{
							//look for File Link object node
							if (ApplicationModel.TYPE_FILELINK.equals(type))
							{
								final NavigableNode node = ResolverHelper.createFileLinkRepresentation(fileInfo, getNodeService(), getBrowseBean());

								if(node != null)
								{
									contents.add(node);
								}
							}
							else if (ApplicationModel.TYPE_FOLDERLINK.equals(type))
							{
								final NavigableNode node = ResolverHelper.createFolderLinkRepresentation(fileInfo, getNodeService(), getBrowseBean());

								if(node != null)
								{
									containers.add(node);
								}
							}
						}
						else if(fileInfo.isFolder())
						{
							// hide discussions forum node
							if(ForumModel.TYPE_FORUM.equals(type) == false)
							{
								final NavigableNode node = ResolverHelper.createFolderRepresentation(fileInfo, getNodeService(), getBrowseBean());

								if(node != null)
								{
									containers.add(node);
								}
							}
						}
						else
						{
							final NavigableNode node = ResolverHelper.createContentRepresentation(fileInfo, getNodeService(), getBrowseBean());

							if(node != null)
							{
								contents.add(node);
							}
						}
					}

					// all is OK, mem the node id

				}
				catch (final InvalidNodeRefException refErr)
				{
					Utils.addErrorMessage(translate(Repository.ERROR_NODEREF, refErr.getNodeRef()), refErr);
					containers = Collections.<NavigableNode> emptyList();
					contents = Collections.<NavigableNode> emptyList();

				}
				catch (final Throwable err)
				{
					Utils.addErrorMessage(translate(Repository.ERROR_GENERIC, err.getMessage()), err);
					containers = Collections.<NavigableNode> emptyList();
					contents = Collections.<NavigableNode> emptyList();

					if(logger.isErrorEnabled())
					{
						logger.error("Unexpected error:" + err.getMessage(), err);
					}
				}
				return null;
			}

		};
		txnHelper.doInTransaction(callback, true);

		if (logger.isDebugEnabled())
		{
			long endTime = System.currentTimeMillis();
			logger.debug("Time to query and build map nodes: " + (endTime - startTime) + "ms");
		}
	}

	
	

}
