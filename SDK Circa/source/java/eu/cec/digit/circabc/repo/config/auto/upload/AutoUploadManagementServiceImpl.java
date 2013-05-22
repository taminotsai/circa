/**
 * 
 */
package eu.cec.digit.circabc.repo.config.auto.upload;

import java.io.File;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.action.evaluator.CompareMimeTypeEvaluator;
import org.alfresco.repo.action.executer.ImporterActionExecuter;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ActionService;
import org.alfresco.service.cmr.action.CompositeAction;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.MimetypeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.Path;
import org.alfresco.service.cmr.rule.Rule;
import org.alfresco.service.cmr.rule.RuleService;
import org.alfresco.service.cmr.rule.RuleType;
import org.alfresco.service.transaction.TransactionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.action.CircabcImporterActionExecuter;
import eu.cec.digit.circabc.business.api.content.CociContentBusinessSrv;
import eu.cec.digit.circabc.repo.log.DBLogServiceImpl;
import eu.cec.digit.circabc.service.config.auto.upload.AutoUploadConfigurationService;
import eu.cec.digit.circabc.service.config.auto.upload.AutoUploadJobResult;
import eu.cec.digit.circabc.service.config.auto.upload.AutoUploadManagementService;
import eu.cec.digit.circabc.service.customisation.mail.MailTemplate;
import eu.cec.digit.circabc.service.lock.LockService;
import eu.cec.digit.circabc.service.log.LogRecord;
import eu.cec.digit.circabc.service.notification.NotificationService;
import eu.cec.digit.circabc.util.PathUtils;

/**
 * @author beaurpi
 *
 */
public class AutoUploadManagementServiceImpl implements
		AutoUploadManagementService {

	private AutoUploadConfigurationService autoUploadConfigurationService;
	
	private RuleService ruleService;
	
	private ActionService actionService;
	
	private NodeService nodeService;
	
	private ContentService contentService;

	private DBLogServiceImpl logService;
	
	private CociContentBusinessSrv cociContentBusinessSrv;
	
	private NotificationService notificationService;

	private TransactionService transactionService;
	
	private FileFolderService fileFolderService;
	
	private MimetypeService mimetypeService;
	
	private LockService circabcLockService;
	
	/**
	 * @return the circabcLogService
	 */
	public DBLogServiceImpl getLogService() {
		return logService;
	}

	/**
	 * @param circabcLogService the circabcLogService to set
	 */
	public void setLogService(DBLogServiceImpl logService) {
		this.logService = logService;
	}

	private static final String CIRCABC_EXTRACT_RULE_NAME = "CIRCABCRuleAutoExtract";
	private static final String CIRCABC_EXTRACT_RULE_DESC = "Auto extract files inside one ZIP";
	
	/** A logger for the class */
	final static Log logger = LogFactory.getLog(AutoUploadManagementServiceImpl.class);
	
	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.service.config.auto.upload.AutoUploadManagementService#registerConfiguration(eu.cec.digit.circabc.repo.config.auto.upload.Configuration)
	 */
	@Override
	public void registerConfiguration(Configuration config) throws SQLException {
		autoUploadConfigurationService.registerConfiguration(config);
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.service.config.auto.upload.AutoUploadManagementService#listConfigurations(java.lang.String)
	 */
	@Override
	public List<Configuration> listConfigurations(String igName)
			throws SQLException {
		
		return autoUploadConfigurationService.listConfigurations(igName);
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.service.config.auto.upload.AutoUploadManagementService#deleteConfiguration(eu.cec.digit.circabc.repo.config.auto.upload.Configuration)
	 */
	@Override
	public void deleteConfiguration(Configuration config) throws SQLException {
		
		autoUploadConfigurationService.deleteConfiguration(config);

	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.service.config.auto.upload.AutoUploadManagementService#updateConfiguration(eu.cec.digit.circabc.repo.config.auto.upload.Configuration)
	 */
	@Override
	public void updateConfiguration(Configuration config) throws SQLException {

		autoUploadConfigurationService.updateConfiguration(config);

	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.service.config.auto.upload.AutoUploadManagementService#getConfiguration(java.lang.Integer)
	 */
	@Override
	public Configuration getConfigurationById(Integer idConfig) throws SQLException {
		
		return autoUploadConfigurationService.getConfigurationById(idConfig);
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.service.config.auto.upload.AutoUploadManagementService#buildDefaultExtractRule(org.alfresco.service.cmr.repository.NodeRef)
	 */
	@Override
	public Rule buildDefaultExtractRule(NodeRef spaceRef) {

		Rule rule = new Rule();
	       rule.setTitle(CIRCABC_EXTRACT_RULE_NAME);
	       rule.setDescription(CIRCABC_EXTRACT_RULE_DESC);
	       rule.applyToChildren(false);
	       rule.setExecuteAsynchronously(true);
	       rule.setRuleDisabled(false);
	       rule.setRuleType(RuleType.INBOUND);
	       CompositeAction compositeAction = actionService.createCompositeAction();
	       rule.setAction(compositeAction);
		
	       // Conditions for the Rule
	       Map<String, Serializable> actionMap = new HashMap<String, Serializable>();
	       actionMap.put(CompareMimeTypeEvaluator.PARAM_VALUE, "application/zip");
	       compositeAction.addActionCondition(actionService.createActionCondition(CompareMimeTypeEvaluator.NAME, actionMap));
	       
	       // Action
	       Action myAction= actionService.createAction(CircabcImporterActionExecuter.NAME);
	       
	       Map<String, Serializable> parameterValues = new HashMap<String, Serializable>();
	       parameterValues.put(ImporterActionExecuter.PARAM_DESTINATION_FOLDER, spaceRef);
	       parameterValues.put(CircabcImporterActionExecuter.PARAM_DELETE_FILE, true);
	       parameterValues.put(CircabcImporterActionExecuter.PARAM_UPDATE_CONTENT, true);
	       parameterValues.put(CircabcImporterActionExecuter.PARAM_DISABLE_FILE_NOTIFICATION,true);

	       myAction.setParameterValues(parameterValues);    
	       
	       compositeAction.addAction(myAction);
		return rule;
		
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.service.config.auto.upload.AutoUploadManagementService#addAutoExtractRuleToSpace(org.alfresco.service.cmr.repository.NodeRef)
	 */
	@Override
	public void addAutoExtractRuleToSpace(NodeRef spaceRef) {

		// Add Rule
	       Rule rule = buildDefaultExtractRule(spaceRef);
	       
	       // Save the rule
	       ruleService.saveRule(spaceRef, rule);

	}

	/**
	 * @return the autoUploadConfigurationService
	 */
	public AutoUploadConfigurationService getAutoUploadConfigurationService() {
		return autoUploadConfigurationService;
	}

	/**
	 * @param autoUploadConfigurationService the autoUploadConfigurationService to set
	 */
	public void setAutoUploadConfigurationService(
			AutoUploadConfigurationService autoUploadConfigurationService) {
		this.autoUploadConfigurationService = autoUploadConfigurationService;
	}

	/**
	 * @return the ruleService
	 */
	public RuleService getRuleService() {
		return ruleService;
	}

	/**
	 * @param ruleService the ruleService to set
	 */
	public void setRuleService(RuleService ruleService) {
		this.ruleService = ruleService;
	}

	/**
	 * @return the actionService
	 */
	public ActionService getActionService() {
		return actionService;
	}

	/**
	 * @param actionService the actionService to set
	 */
	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}

	@Override
	public void removeAutoExtractRule(NodeRef spaceRef) {
		
		List<Rule> listRules = ruleService.getRules(spaceRef);
		
		for(Rule rule: listRules)
		{
			if(rule.getTitle().equals(CIRCABC_EXTRACT_RULE_NAME))
			{
				ruleService.removeRule(spaceRef, rule);
				break;
			}
		}
		
	}


	@Override
	public Configuration getConfigurationByNodeRef(NodeRef nodeRef)
			throws SQLException {
		
		return autoUploadConfigurationService.getConfigurationByNodeRef(nodeRef);
	}

	@Override
	public List<Configuration> listAllConfigurations() throws SQLException {
		
		return autoUploadConfigurationService.getAllConfigurations();
	}

	/**
	 * @return the nodeService
	 */
	public NodeService getNodeService() {
		return nodeService;
	}

	/**
	 * @param nodeService the nodeService to set
	 */
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	/**
	 * @return the contentService
	 */
	public ContentService getContentService() {
		return contentService;
	}

	/**
	 * @param contentService the contentService to set
	 */
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	@Override
	public void updateContent(NodeRef fileRef, File file) {
		
		ContentWriter writer = contentService.getWriter(fileRef, ContentModel.PROP_CONTENT, true);
		writer.putContent(file);
		
	}

	@Override
	public void logJobResult(Configuration conf, AutoUploadJobResult result) {
		
		LogRecord logRecord = new LogRecord();
		
		
		
		if(result.equals(AutoUploadJobResult.JOB_OK) || result.equals(AutoUploadJobResult.JOB_ERROR))
		{
			logRecord.setActivity("Update Content");
		}
		else if(result.equals(AutoUploadJobResult.JOB_REMOTE_FTP_PROBLEM))
		{
			logRecord.setActivity("Remode Ftp Connection");
		}
		
		
		logRecord.setService("Auto-upload");
		
		logRecord.setOK((result.equals(AutoUploadJobResult.JOB_OK) ? true : false));
		
		logRecord.setDate(new Date());
		
		Long igId= Long.parseLong(nodeService.getProperty(new NodeRef(conf.getIgName()), ContentModel.PROP_NODE_DBID).toString());
		logRecord.setIgID(igId);
		
		logRecord.setUser("admin");
		logRecord.setInfo("From FTP:"+conf.getFtpHost()+" path:"+conf.getFtpPath());
		
		Long docId= Long.parseLong(nodeService.getProperty(new NodeRef(conf.getFileNodeRef()), ContentModel.PROP_NODE_DBID).toString());
		logRecord.setDocumentID(docId);
		
		logRecord.setIgName(nodeService.getProperty(new NodeRef(conf.getIgName()), ContentModel.PROP_NAME).toString());
		
		Path path = getNodeService().getPath(new NodeRef(conf.getFileNodeRef()));
		logRecord.setPath(PathUtils.getCircabcPath(path,true));

		logService.log(logRecord);

	}

	/**
	 * @return the cociContentBusinessSrv
	 */
	public CociContentBusinessSrv getCociContentBusinessSrv() {
		return cociContentBusinessSrv;
	}

	/**
	 * @param cociContentBusinessSrv the cociContentBusinessSrv to set
	 */
	public void setCociContentBusinessSrv(CociContentBusinessSrv cociContentBusinessSrv) {
		this.cociContentBusinessSrv = cociContentBusinessSrv;
	}

	/**
	 * @return the notificationService
	 */
	public NotificationService getNotificationService() {
		return notificationService;
	}

	/**
	 * @param notificationService the notificationService to set
	 */
	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@Override
	public void sendJobNofitication(Configuration conf, AutoUploadJobResult result) {
		
		List<String> mails = new ArrayList<String>();
		
		if(conf.getEmails() != null )
		{
			for(String mail: conf.getEmails().split(","))
			{
				mails.add(mail);
			}
		}

		try {
			
			if(result.equals(AutoUploadJobResult.JOB_OK))
			{
				this.notificationService.notify(new NodeRef(conf.getFileNodeRef()), mails , MailTemplate.AUTO_UPLOAD_SUCCESS);
			}
			else if(result.equals(AutoUploadJobResult.JOB_ERROR))
			{
				this.notificationService.notify(new NodeRef(conf.getFileNodeRef()), mails , MailTemplate.AUTO_UPLOAD_ERROR);
			}
			else if(result.equals(AutoUploadJobResult.JOB_REMOTE_FTP_PROBLEM))
			{
				this.notificationService.notify(new NodeRef(conf.getFileNodeRef()), mails , MailTemplate.AUTO_UPLOAD_FTP_PROBLEM);
			}

		} catch (Exception e) {
			if(logger.isErrorEnabled())
			{
				logger.error("Error during notification phase of auto upload for document"+conf.getFileNodeRef(), e);
			}
		}
		
		
		
	}

	@Override
	public void extractZip(NodeRef fileRef) {
		
		// build the action params map based on the bean's current state
        Map<String, Serializable> params = new HashMap<String, Serializable>(2, 1.0f);
        params.put(ImporterActionExecuter.PARAM_DESTINATION_FOLDER, nodeService.getParentAssocs(fileRef).get(0).getParentRef() );
        params.put(ImporterActionExecuter.PARAM_ENCODING, "UTF-8");
        params.put(CircabcImporterActionExecuter.PARAM_DELETE_FILE, false);
        params.put(CircabcImporterActionExecuter.PARAM_NOTIFY_USER,"");
        params.put(CircabcImporterActionExecuter.PARAM_UPDATE_CONTENT,true);
        params.put(CircabcImporterActionExecuter.PARAM_DISABLE_FILE_NOTIFICATION,true);
        
        
        
        // build the action to execute
        Action action = getActionService().createAction(CircabcImporterActionExecuter.NAME, params);
        action.setExecuteAsynchronously(true);
        
        // execute the action on the ACP file
        getActionService().executeAction(action, fileRef);
		
	}
	
	/***
	 * 
	 */
	public boolean documentExists(NodeRef nodeRef)
	{
		
		return nodeService.exists(nodeRef);
		
	}

	@Override
	public NodeRef createContent(NodeRef fileRef, final File tmpFile, final NodeRef destinationFolder, final String fileName) {
		
		RetryingTransactionHelper helper = transactionService.getRetryingTransactionHelper();
	     return helper.doInTransaction(new RetryingTransactionHelper.RetryingTransactionCallback<NodeRef>()
       {
           public NodeRef execute() throws Throwable
           {
           	NodeRef node = nodeService.getChildByName(destinationFolder, ContentModel.ASSOC_CONTAINS, fileName);
               	 
           	 if(node == null)
                {
           		 final FileInfo fileInfo = fileFolderService.create(destinationFolder, fileName, ContentModel.TYPE_CONTENT);
                    final NodeRef createdNodeRef = fileInfo.getNodeRef();
                    nodeService.setProperty(createdNodeRef, ContentModel.PROP_DESCRIPTION, "Document created by auto upload job");
            		 
                    // get a writer for the content and put the file
                    final ContentWriter writer = contentService.getWriter(createdNodeRef, ContentModel.PROP_CONTENT, true);

                    writer.setMimetype(mimetypeService.guessMimetype(fileName));
                                    
                    writer.putContent(tmpFile);

                    node = createdNodeRef;
                }

               
               return node; 
           }
       }, false, true);
		
	}

	/**
	 * @return the transactionService
	 */
	public TransactionService getTransactionService() {
		return transactionService;
	}

	/**
	 * @param transactionService the transactionService to set
	 */
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	/**
	 * @return the fileFolderService
	 */
	public FileFolderService getFileFolderService() {
		return fileFolderService;
	}

	/**
	 * @param fileFolderService the fileFolderService to set
	 */
	public void setFileFolderService(FileFolderService fileFolderService) {
		this.fileFolderService = fileFolderService;
	}

	/**
	 * @return the mimetypeService
	 */
	public MimetypeService getMimetypeService() {
		return mimetypeService;
	}

	/**
	 * @param mimetypeService the mimetypeService to set
	 */
	public void setMimetypeService(MimetypeService mimetypeService) {
		this.mimetypeService = mimetypeService;
	}

	@Override
	public Integer lockJobFile(Integer idConfiguration) {

		Integer result = 0; 
		
		if(!circabcLockService.isLocked("autoupload"+idConfiguration))
		{
			circabcLockService.lock("autoupload"+idConfiguration);
			result = 1;
		}
		
		return result;
		
	}

	@Override
	public Integer unlockJobFile(Integer idConfiguration) {
		
		Integer result = 0; 
		
		if(circabcLockService.isLocked("autoupload"+idConfiguration))
		{
			circabcLockService.unlock("autoupload"+idConfiguration);
			result = 1;
		}
		
		return result;
		
	}

	/**
	 * @return the circabcLockService
	 */
	public LockService getCircabcLockService() {
		return circabcLockService;
	}

	/**
	 * @param circabcLockService the circabcLockService to set
	 */
	public void setCircabcLockService(LockService circabcLockService) {
		this.circabcLockService = circabcLockService;
	}

}