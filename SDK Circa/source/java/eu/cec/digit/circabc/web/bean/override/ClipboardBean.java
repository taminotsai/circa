/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.bean.override;

import static org.alfresco.web.ui.repo.component.shelf.UIClipboardShelfItem.ACTION_PASTE_ALL;
import static org.alfresco.web.ui.repo.component.shelf.UIClipboardShelfItem.ACTION_PASTE_ITEM;
import static org.alfresco.web.ui.repo.component.shelf.UIClipboardShelfItem.ACTION_PASTE_LINK;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.node.MLPropertyInterceptor;
import org.alfresco.repo.policy.BehaviourFilter;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.Path;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.alfresco.util.TempFileProvider;
import org.alfresco.web.app.Application;
import org.alfresco.web.app.servlet.FacesHelper;
import org.alfresco.web.bean.NavigationBean;
import org.alfresco.web.bean.clipboard.ClipboardItem;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.ui.common.Utils;
import org.alfresco.web.ui.repo.component.shelf.UIClipboardShelfItem.ClipboardEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.aspect.DisableNotificationThreadLocal;
import eu.cec.digit.circabc.service.bulk.BulkService;
import eu.cec.digit.circabc.service.bulk.indexes.IndexRecord;
import eu.cec.digit.circabc.service.bulk.indexes.IndexService;
import eu.cec.digit.circabc.service.cmr.security.CircabcConstant;
import eu.cec.digit.circabc.service.compress.ZipService;
import eu.cec.digit.circabc.service.log.LogRecord;
import eu.cec.digit.circabc.service.log.LogService;
import eu.cec.digit.circabc.service.notification.NotificationManagerService;
import eu.cec.digit.circabc.service.struct.ManagementService;
import eu.cec.digit.circabc.util.PathUtils;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.app.context.ICircabcContextListener;
import eu.cec.digit.circabc.web.app.context.UICircabcContextService;
import eu.cec.digit.circabc.web.ui.common.WebResourcesCircabc;
import eu.cec.digit.circabc.web.ui.repo.component.shelf.UIClipboardShelfItem;

/**
 * @author yanick pignot
 * @author stephane clinckart
 */
public class ClipboardBean extends
		org.alfresco.web.bean.clipboard.ClipboardBean implements
		ICircabcContextListener {

	

	private static final String BULK_DOWNLOAD = "/bulkDownload/";

	private static final long serialVersionUID = -3459193962175859331L;

	private static Log logger = LogFactory.getLog(ClipboardBean.class);

	/** I18N messages */
	private static final String MSG_ERROR_DOWNLOAD_ALL  = "error_download_all";

	private transient ZipService zipService;

	private transient BulkService bulkService;

	private transient IndexService indexService;

	private transient NodeService nodeService;

	private transient TransactionService transactionService;

	private transient LogService logService;

	private transient ManagementService managementService;

	private LogRecord logRecord = new LogRecord();
	
	private BehaviourFilter policyBehaviourFilter;
	
	private NotificationManagerService notificationManagerService;



	/**
	 * Default Constructor
	 */
	public ClipboardBean() {
		super();
		UICircabcContextService.getInstance(FacesContext.getCurrentInstance())
				.registerBean(this);
		logRecord.setService("Library");
		logRecord.setUser(AuthenticationUtil.getFullyAuthenticatedUser());

	}

	@Override
	public void pasteAll(ActionEvent event)
	{
		super.pasteAll(event);
		// Bug fix: MultilingualContentServiceImpl.copyTranslationContainer misses code::   finally{MLPropertyInterceptor.setMLAware(wasMLAware);}
		MLPropertyInterceptor.setMLAware(false);
	}

	@Override
    public void copyNode(ActionEvent event)
    {
		super.copyNode(event);
    }
	@Override
	public void cutNode(ActionEvent event)
	{
		super.cutNode(event);

	}

	private void setLogRecordNodeRef(NodeRef nodeRef)
	{

		logRecord.setDocumentID((Long) getNodeService().getProperty(nodeRef, ContentModel.PROP_NODE_DBID));
		final NodeRef igNodeRef = getManagementService().getCurrentInterestGroup(nodeRef);
		if (igNodeRef != null)
		{
			logRecord.setIgID((Long) getNodeService().getProperty(igNodeRef, ContentModel.PROP_NODE_DBID));
			logRecord.setIgName((String) getNodeService().getProperty(igNodeRef, ContentModel.PROP_NAME));
		}
		logRecord.setUser(AuthenticationUtil.getFullyAuthenticatedUser());
		Path path = getNodeService().getPath(nodeRef);
		logRecord.setPath(PathUtils.getCircabcPath(path,true));
		logRecord.setOK(true);

	}

	@Override
	public void pasteItem(ActionEvent event)
	{
		final NodeRef targetNodeRef = getTargetNodeRef();
		final String targetName = (String) getNodeService().getProperty(targetNodeRef, ContentModel.PROP_NAME);
		final  UIClipboardShelfItem.ClipboardEvent clipEvent = (UIClipboardShelfItem.ClipboardEvent)event;
		logRecordSetActivity(clipEvent);
		logRecordSetInfo(clipEvent,targetName);
		setLogRecordNodeRef(targetNodeRef);
		boolean shouldNotify = getNotificationStatus(targetNodeRef,clipEvent);
		DisableNotificationThreadLocal disableNotificationThreadLocal = new DisableNotificationThreadLocal();
		if(!shouldNotify)
    	{
			 disableNotificationThreadLocal.set(true); 
    	}
    	super.pasteItem(event);
     	disableNotificationThreadLocal.remove(); 
    	getLogService().log(logRecord);
		// Bug fix: MultilingualContentServiceImpl.copyTranslationContainer misses code::   finally{MLPropertyInterceptor.setMLAware(wasMLAware);}
		MLPropertyInterceptor.setMLAware(false);

	}

	private boolean getNotificationStatus(NodeRef nodeRef, ClipboardEvent clipEvent)
	{
		boolean result = false;
		final NodeRef igNodeRef = getManagementService().getCurrentInterestGroup(nodeRef);
		if (igNodeRef != null)
		{
			if (clipEvent.Action == ACTION_PASTE_ALL)
			 {
				 result = getNotificationManagerService().isPasteAllNotificationEnabled(igNodeRef);
			 }
			 else if (clipEvent.Action == ACTION_PASTE_ITEM)
			 {
				 result = getNotificationManagerService().isPasteNotificationEnabled(igNodeRef);
			 }
		}
		return result;
	}

	private void logRecordSetActivity(final UIClipboardShelfItem.ClipboardEvent clipEvent)
	{
		String activity  ="";
		 if (clipEvent.Action == ACTION_PASTE_ALL)
		 {
			 activity = "Paste all";
		 }
		 else if (clipEvent.Action == ACTION_PASTE_ITEM)
		 {
			 activity = "Paste item";
		 }
		 else if (clipEvent.Action == ACTION_PASTE_LINK)
		 {
			 activity = "Paste link";
		 }
		 logRecord.setActivity(activity);
	}

	private void logRecordSetInfo(ClipboardEvent clipEvent, String targetName)
	{


        StringBuffer infoBuffer = new StringBuffer(256);
        infoBuffer.append("Pasted: ");


		 int index = clipEvent.Index;
	     if (index == -1)
	     {
	    	 for (ClipboardItem clipboardItem: this.getItems())
			{
	    		 if (getNodeService().exists(clipboardItem.getNodeRef()))
	    		 {
	    			 QName type = getNodeService().getType(clipboardItem.getNodeRef());
		    		 if (type.equals(ContentModel.TYPE_FOLDER))
		    		 {
		    			 infoBuffer.append(" space ");
		    		 }
		    		 else if (type.equals(ContentModel.TYPE_CONTENT))
		    		 {
		    			 infoBuffer.append(" content ");
		    		 }
		    		 else
		    		 {
		    			 infoBuffer.append(" item ");
		    		 }
		    		 infoBuffer.append(clipboardItem.getName());
		    		 infoBuffer.append('\n');
	    		 }
			}

	     }

	     if (index > -1 && index < this.getItems().size())
	     {

	    	 ClipboardItem clipboardItem = this.getItems().get(index);
	    	 if ( getNodeService().exists(clipboardItem.getNodeRef()))
	    	 {
	    		 QName type = getNodeService().getType(clipboardItem.getNodeRef());
	    		 if (type.equals(ContentModel.TYPE_FOLDER))
	    		 {
	    			 infoBuffer.append(" space ");
	    		 }
	    		 else if (type.equals(ContentModel.TYPE_CONTENT))
	    		 {
	    			 infoBuffer.append(" content ");
	    		 }
	    		 else
	    		 {
	    			 infoBuffer.append(" item ");
	    		 }
	    		 infoBuffer.append(clipboardItem.getName());
	    	 }
	    }
	    infoBuffer.append(" to space ");
	    infoBuffer.append(targetName);
	    logRecord.setInfo(infoBuffer.toString());
	}

	private NodeRef getTargetNodeRef()
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		NavigationBean navigator = (NavigationBean)FacesHelper.getManagedBean(fc, NavigationBean.BEAN_NAME);
		return new NodeRef(Repository.getStoreRef(), navigator.getCurrentNodeId());
	}

	public void categoryChanged() {

	}

	public void categoryHeaderChanged() {

	}

	public void circabcEntered() {
		emptyClipboard();
	}

	public void circabcLeaved() {
		emptyClipboard();
	}

	public void igRootChanged() {
		emptyClipboard();
	}

	public void igServiceChanged() {

	}

	protected void emptyClipboard() {
		this.setItems(new ArrayList<ClipboardItem>(4));
	}

	/**
	 * Action handler called to download all items from the clipboard
	 */
	public void downloadAllItem(final ActionEvent event) {
		final UIClipboardShelfItem.ClipboardEvent clipEvent = (UIClipboardShelfItem.ClipboardEvent) event;

		int index = clipEvent.Index;
		if (index >= getItems().size()) {
			throw new IllegalStateException(
					"Clipboard attempting paste a non existent item index: "
							+ index);
		}
		performDownloadAllItems();

		final NodeRef targetNodeRef = getTargetNodeRef();
        logRecord.setActivity("Download all");
		setLogRecordNodeRef(targetNodeRef);
		getLogService().log(logRecord);
	}

	/**
	    * Perform a DownloadAll action
	    */
	   
	private void performDownloadAllItems() {
		final FacesContext context = FacesContext.getCurrentInstance();
		File tempZipFile = null;
		final UserTransaction txn = getTransactionService().getNonPropagatingUserTransaction(false);
		File tempIndexFile = null;
		try {
			txn.begin();
			tempZipFile = TempFileProvider.createTempFile("bulk",
					".zip", TempFileProvider.getTempDir());
			tempIndexFile = TempFileProvider.createTempFile("bulk",
					".txt", TempFileProvider.getTempDir());

			NodeRef nodeRef;
			final List<NodeRef> nodes = new LinkedList<NodeRef>();
			for (final ClipboardItem item : getItems()) {
				nodeRef = item.getNodeRef();
				nodes.add(nodeRef);
			}

			final List<IndexRecord> indexRecords = getBulkService().getMetaData(nodes);

			getIndexService().generateIndexRecords(tempIndexFile, indexRecords);

			getZipService().addingFileIntoArchive(nodes, tempZipFile, tempIndexFile);
			setBulkDownloadFile(context,tempZipFile.getPath());
			final long length = tempZipFile.length();
			logRecord.addInfo(" Download zip size: " + String.valueOf(length) + " bytes");
			final String viewId = BULK_DOWNLOAD + "dummy?" + WebResourcesCircabc.URL_FIX_ISPOSTBACK ;
		    final UIViewRoot viewRoot = context.getApplication().getViewHandler().createView(context, viewId);
			context.setViewRoot(viewRoot);
			context.renderResponse();
		} catch (final Throwable err) {
			Utils.addErrorMessage(Application.getMessage(context, MSG_ERROR_DOWNLOAD_ALL) + err.getMessage(), err);
		} finally {
			try {
				txn.commit();
			} catch (final IllegalStateException e) {
				if (logger.isErrorEnabled()) {
					logger.error("Exception during rollback:" + e.getMessage());
				}
			} catch (final SecurityException e) {
				if (logger.isErrorEnabled()) {
					logger.error("Exception during upload:" + e.getMessage());
				}
			} catch (final SystemException e) {
				if (logger.isErrorEnabled()) {
					logger.error("Exception during upload:" + e.getMessage());
				}
			} catch (RollbackException e) {
				if (logger.isErrorEnabled()) {
					logger.error("Exception during rollback:" + e.getMessage());
				}
			} catch (HeuristicMixedException e) {
				if (logger.isErrorEnabled()) {
					logger.error("Exception during rollback:" + e.getMessage());
				}
			} catch (HeuristicRollbackException e) {
				if (logger.isErrorEnabled()) {
					logger.error("Exception during rollback:" + e.getMessage());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void setBulkDownloadFile(final FacesContext context, String file)
	{
		context.getExternalContext().getSessionMap().put(CircabcConstant.BULK_DOWNLOAD_FILE, file);
	}

	/**
	 * @return the zipService
	 */
	public ZipService getZipService() {

		if (zipService == null)
		{
			zipService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getNonSecuredZipService();
		}
		return zipService;

	}

	/**
	 * @param zipService
	 *            the zipService to set
	 */
	public void setZipService(final ZipService zipService) {
		this.zipService = zipService;
	}


	/**
	 * @return the nodeService
	 */
	public NodeService getNodeService()
	{
		if (nodeService == null)
		{
			nodeService = Repository.getServiceRegistry(FacesContext.getCurrentInstance()).getNodeService();
		}
		return nodeService;
	}

	/**
	 * @param nodeService the nodeService to set
	 */
	public void setNodeService(final NodeService nodeService) {
		this.nodeService = nodeService;
	}

	/**
	 * @return the transactionService
	 */
	public TransactionService getTransactionService()
	{
		if (transactionService == null)
		{
			transactionService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getNonSecuredTransactionService();
		}
		return transactionService;
	}

	/**
	 * @param transactionService the transactionService to set
	 */
	public void setTransactionService(final TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	/**
	 * @return the bulkService
	 */
	public BulkService getBulkService() {

		if ( bulkService == null)
		{
			bulkService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getNonSecuredBulkService() ;
		}
		return bulkService;
	}

	/**
	 * @param bulkService the bulkService to set
	 */
	public void setBulkService(final BulkService bulkService) {
		this.bulkService = bulkService;
	}

	/**
	 * @return the indexService
	 */
	public IndexService getIndexService() {

		if ( indexService == null)
		{
			indexService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getNonSecuredIndexService() ;
		}
		return indexService;
	}

	/**
	 * @param indexService the indexService to set
	 */
	public void setIndexService(final IndexService indexService) {
		this.indexService = indexService;
	}

	/**
	 * @param logService the logService to set
	 */
	public void setLogService(LogService logService)
	{
		this.logService = logService;
	}

	/**
	 * @return the logService
	 */
	public LogService getLogService()
	{

		if (logService == null)
		{
			logService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getLogService();
		}
		return logService;
	}

	/**
	 * @param managementService the managementService to set
	 */
	public void setManagementService(ManagementService managementService)
	{
		this.managementService = managementService;
	}

	/**
	 * @return the managementService
	 */
	public ManagementService getManagementService()
	{
		if (managementService == null)
		{
			managementService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getManagementService();
		}
		return managementService;
	}

	public void setPolicyBehaviourFilter(BehaviourFilter policyBehaviourFilter)
	{
		this.policyBehaviourFilter = policyBehaviourFilter;
	}

	public BehaviourFilter getPolicyBehaviourFilter()
	{
		if (policyBehaviourFilter == null)
		{
			policyBehaviourFilter = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getPolicyBehaviourFilter();
		}
		return policyBehaviourFilter;
	}

	public void setNotificationManagerService(NotificationManagerService notificationManagerService)
	{
		this.notificationManagerService = notificationManagerService;
	}

	public NotificationManagerService getNotificationManagerService()
	{
		if (notificationManagerService == null)
		{
			notificationManagerService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getNotificationManagerService();
		}
		return notificationManagerService;
	}
}
