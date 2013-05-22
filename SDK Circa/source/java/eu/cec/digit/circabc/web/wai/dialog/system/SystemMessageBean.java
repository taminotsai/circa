package eu.cec.digit.circabc.web.wai.dialog.system;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.bean.repository.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.model.SystemMessageModel;
import eu.cec.digit.circabc.web.Beans;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Facade to read from the System Message node.
 * The bean is initialized as a managed application bean.
 * 
 * @author makz
 */
public class SystemMessageBean 
{

    private static final Log logger = LogFactory.getLog(SystemMessageBean.class);
	
	//***********************************************************************
	//                                                      GETTER AND SETTER
	//***********************************************************************
	
	public boolean isShowMessage()
	{
		boolean result = false;
		try 
		{
			NodeRef systemMessageNode = this.getSystemMessageNode();
			if(systemMessageNode != null)
			{
				Object msgEnabled = this.getNodeService().getProperty(
						systemMessageNode, 
						SystemMessageModel.PROP_IS_SYSTEMMESSAGE_ENABLED);
				if(msgEnabled != null && msgEnabled.getClass().equals(Boolean.class))
				{
					return (Boolean)msgEnabled;
				}
			}
		}
		catch (Exception e) {
			if (logger.isErrorEnabled())
			{
				logger.error("Cannot get value for isShowMessage", e);
			}
		}
		return result;
	}
	
	public String getMessage()
	{
		String result = "";
		try 
		{
			NodeRef systemMessageNode = this.getSystemMessageNode();
			if(systemMessageNode != null)
			{
				Object msgText = this.getNodeService().getProperty(
						systemMessageNode, 
						SystemMessageModel.PROP_SYSTEMMESSAGE_TEXT);
				if(msgText != null)
				{
					Whitelist basicsAndStyle = new Whitelist();
					basicsAndStyle.addTags("p", "span", "strong", "b", "i", "u", "br", "sub", "sup", "a");
					basicsAndStyle.addAttributes(":all", "style");
					result = Jsoup.clean(msgText.toString(), basicsAndStyle);
				}
			}
		}
		catch (Exception e) {
			if (logger.isErrorEnabled())
			{
				logger.error("Cannot get value for getMessage", e);
			}
		}
		return result;
	}
	
	public void updateProperties(boolean showMessage, String message)
	{
		final Map<QName, Serializable> properties = new HashMap<QName, Serializable>(2);
		properties.put(SystemMessageModel.PROP_IS_SYSTEMMESSAGE_ENABLED, showMessage);
		properties.put(SystemMessageModel.PROP_SYSTEMMESSAGE_TEXT, message);
		this.getNodeService().setProperties(this.getSystemMessageNode(), properties);
	}
	
	transient private NodeService nodeService;
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public NodeService getNodeService() {
		return nodeService;
	}
	
	transient private SearchService searchService;
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
	public SearchService getSearchService() {
		return searchService;
	}
	
	//***********************************************************************
	//                                                         PRIVATE HELPER
	//***********************************************************************
	private ServiceRegistry getServiceRegistry()
	{
		return Repository.getServiceRegistry(FacesContext.getCurrentInstance());
	}
	
	private NodeRef messageNodeRef;
	private NodeRef getSystemMessageNode()
	{
		if(messageNodeRef == null)
		{
 
			final RetryingTransactionHelper txnHelper = getServiceRegistry().getTransactionService().getRetryingTransactionHelper();

			final RetryingTransactionCallback<Object> callback = new RetryingTransactionCallback<Object>()
			{
				public NodeRef execute() throws Throwable
				{	        
					ChildAssociationRef childAssoc = null;
					
					// search for the system message node
					NodeRef rootRef = Beans.getWaiNavigator().getCircabcHomeNode().getNodeRef();
					String query = String.format("PARENT:\"%s\" AND TYPE:\"%s\"", 
							rootRef.toString(), 
							SystemMessageModel.TYPE_SYSTEMMESSAGE);
					ResultSet results = getSearchService().query(
							StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, 
							SearchService.LANGUAGE_LUCENE, 
							query);
					
					if(results.length() > 0)
					{
						// get the system message node from the results
						childAssoc = results.getChildAssocRef(0);
						messageNodeRef = childAssoc.getChildRef();
					}
					else
					{
						try
						{
							// there is no system message node, create it
							childAssoc = getNodeService().createNode(
									rootRef, 
									ContentModel.ASSOC_CONTAINS, 
									QName.createQName(SystemMessageModel.CIRCABC_SYSTEMMESSAGE_MODEL_1_0_URI, "System Message"),
									SystemMessageModel.TYPE_SYSTEMMESSAGE,
									null);
							messageNodeRef = childAssoc.getChildRef();
						}
						catch(Exception ade)
						{
							//user has no right to create the node, do nothing
							logger.warn("User "+AuthenticationUtil.getSystemUserName()+" has no right to access the System Message Node");
						}
					}
					results.close();
			    	return messageNodeRef;
				}
			};
			messageNodeRef =  (NodeRef) AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>()
         	{
         		public Object doWork()
         		{
         			return txnHelper.doInTransaction(callback, false, true);
	        }
         	}, AuthenticationUtil.getSystemUserName());

		}
		return messageNodeRef;
	}
}
