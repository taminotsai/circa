package eu.cec.digit.circabc.repo.external.repositories;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import eu.cec.digit.circabc.action.evaluator.ExternalRepositoryActionEvaluator;
import eu.cec.digit.circabc.action.evaluator.ManageExternalRepositoriesActionEvaluator;
import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.repo.hrs.ws.AddAssignmentsRequest;
import eu.cec.digit.circabc.repo.hrs.ws.AssignmentTaskToAdd;
import eu.cec.digit.circabc.repo.hrs.ws.AssignmentTaskToAddCode;
import eu.cec.digit.circabc.repo.hrs.ws.AssignmentWorkflow;
import eu.cec.digit.circabc.repo.hrs.ws.AttachmentTypeToAdd;
import eu.cec.digit.circabc.repo.hrs.ws.CurrentExternalEntity;
import eu.cec.digit.circabc.repo.hrs.ws.CurrentExternalEntityRetrievalOptions;
import eu.cec.digit.circabc.repo.hrs.ws.CurrentExternalPerson;
import eu.cec.digit.circabc.repo.hrs.ws.CurrentInternalEntity;
import eu.cec.digit.circabc.repo.hrs.ws.CurrentInternalPerson;
import eu.cec.digit.circabc.repo.hrs.ws.DocumentRegistrationRequest;
import eu.cec.digit.circabc.repo.hrs.ws.DocumentService_PortType;
import eu.cec.digit.circabc.repo.hrs.ws.DocumentWebService;
import eu.cec.digit.circabc.repo.hrs.ws.DocumentWebServiceLocator;
import eu.cec.digit.circabc.repo.hrs.ws.EntityService_PortType;
import eu.cec.digit.circabc.repo.hrs.ws.EntityWebService;
import eu.cec.digit.circabc.repo.hrs.ws.EntityWebServiceLocator;
import eu.cec.digit.circabc.repo.hrs.ws.FindCurrentExternalEntityRequest;
import eu.cec.digit.circabc.repo.hrs.ws.FindCurrentExternalEntityRequestSearchForPerson;
import eu.cec.digit.circabc.repo.hrs.ws.FindCurrentInternalEntityRequest;
import eu.cec.digit.circabc.repo.hrs.ws.FindCurrentInternalEntityRequestSearchForPerson;
import eu.cec.digit.circabc.repo.hrs.ws.ItemKindToAdd;
import eu.cec.digit.circabc.repo.hrs.ws.MailType;
import eu.cec.digit.circabc.repo.hrs.ws.RecipientCode;
import eu.cec.digit.circabc.repo.hrs.ws.RecipientsToAddRecipient;
import eu.cec.digit.circabc.repo.hrs.ws.RegistrationSummary;
import eu.cec.digit.circabc.repo.hrs.ws.SecurityClassification;
import eu.cec.digit.circabc.repo.hrs.ws.SendersToAddSender;
import eu.cec.digit.circabc.repo.hrs.ws.UploadedItemToAdd;
import eu.cec.digit.circabc.repo.hrs.ws.UserProfile;
import eu.cec.digit.circabc.repo.hrs.ws.UserService_PortType;
import eu.cec.digit.circabc.repo.hrs.ws.UserWebService;
import eu.cec.digit.circabc.repo.hrs.ws.UserWebServiceLocator;
import eu.cec.digit.circabc.repo.hrs.ws.WorkflowService_PortType;
import eu.cec.digit.circabc.repo.hrs.ws.WorkflowWebService;
import eu.cec.digit.circabc.repo.hrs.ws.WorkflowWebServiceLocator;
import eu.cec.digit.circabc.service.external.repositories.ExternalRepositoriesManagementService;
import eu.cec.digit.circabc.web.WebClientHelper;

/**
 * Implementation of the service that manages all the concerns about Hermes 
 * document publishing.
 * 
 * @author schwerr
 */
public class ExternalRepositoriesManagementServiceImpl implements
		ExternalRepositoriesManagementService {
	
	private static final Log logger = LogFactory.getLog(ExternalRepositoriesManagementServiceImpl.class);
	
	private NodeService nodeService = null;
	
	private TransactionService transactionService = null;
	
	private ContentService contentService = null;
	
	// HRS
	private String applicationId = "CIRCAmN67t";
	private UserNameResolver userNameResolver = null;
	private ProxyTicketResolver proxyTicketResolver = null;
	private String uploadUrl = "http://myserver:1234/hrs-dts/DataTransferService";
	private String endpointBaseAddress = "http://dighbust.cc.cec.eu.int:11031/hermes/Proxy";
	
	private EntityWebService entityWebService = null;
	private DocumentWebService documentWebService = null;
	private WorkflowWebService workflowWebService = null;
	private UserWebService userWebService = null;
	
	private long minutesToCheck = 60 * 12;
	
	// Is the system operational? By default, not
	private boolean operational = false;
	
	// Internal sender cache (synchronized set)
	private static Set<String> internalEntities = 
						Collections.synchronizedSet(Collections.newSetFromMap(
						        new WeakHashMap<String, Boolean>()));
	/**
	 * Initializes the web services and endpoints.
	 */
	public void init() {
		
		// Init evaluators
		ExternalRepositoryActionEvaluator.setExternalRepositoriesManagementService(this);
		ManageExternalRepositoriesActionEvaluator.setExternalRepositoriesManagementService(this);
		
		// Init web services
		entityWebService = new EntityWebServiceLocator();
		documentWebService = new DocumentWebServiceLocator();
		workflowWebService = new WorkflowWebServiceLocator();
		userWebService = new UserWebServiceLocator();
		
		((EntityWebServiceLocator) entityWebService).setEntityServiceEndpointAddress(endpointBaseAddress + "/1.16/EntityWebServicePS");
		((DocumentWebServiceLocator) documentWebService).setDocumentServiceEndpointAddress(endpointBaseAddress + "/1.16/DocumentWebServicePS");
		((WorkflowWebServiceLocator) workflowWebService).setWorkflowServiceEndpointAddress(endpointBaseAddress + "/1.16/WorkflowWebServicePS");
		((UserWebServiceLocator) userWebService).setUserServiceEndpointAddress(endpointBaseAddress + "/1.16/UserWebServicePS");
	}
	
	/**
	 * @see eu.cec.digit.circabc.service.external.repositories.ExternalRepositoriesManagementService#isOperational()
	 */
	@Override
	public boolean isOperational() {
		return operational;
	}
	
	/**
	 * @see eu.cec.digit.circabc.service.external.repositories.ExternalRepositoriesManagementService#getAvailableRepositories()
	 */
	@Override
	public Collection<RepositoryConfiguration> getAvailableRepositories() {
		
		List<RepositoryConfiguration> availableRepositories = 
									new ArrayList<RepositoryConfiguration>();
		
		RepositoryConfiguration data = new RepositoryConfiguration();
		data.setName(ExternalRepositoriesManagementService.EXTERNAL_REPOSITORY_NAME);
		availableRepositories.add(data);
		
		return availableRepositories;
	}
	
	/**
	 * @see eu.cec.digit.circabc.service.external.repositories.ExternalRepositoriesManagementService#addRepository(java.lang.String, eu.cec.digit.circabc.repo.external.repositories.RepositoryConfiguration)
	 */
	@Override
	public void addRepository(final String parentNodeId, final RepositoryConfiguration configuration) {
		
		final RetryingTransactionHelper txnHelper = transactionService
				.getRetryingTransactionHelper();
		
		final RetryingTransactionCallback<Object> callback = 
				new RetryingTransactionCallback<Object>() {
			
			public Object execute() throws Throwable {
				
				NodeRef parentNodeRef = createOrGetRepositoryConfigurationFolder(parentNodeId, true);
				
				Map<QName, Serializable> properties = new HashMap<QName, Serializable>();
				
				properties.put(ContentModel.PROP_NAME, configuration.getName());
				
				nodeService.createNode(parentNodeRef, 
								CircabcModel.ASSOC_CONTAINSCON_FIGURATIONS, 
								QName.createQName(ContentModel.PROP_NAME.getNamespaceURI(), 
								QName.createValidLocalName(configuration.getName())), 
								CircabcModel.TYPE_EXTERNAL_REPOSITORY_CONFIGURATION, 
								properties).getChildRef();
				return null;
			}
		};
		
		AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>() {
			
     		public Object doWork() {
     			return txnHelper.doInTransaction(callback, false, true);
     		}
     	}, AuthenticationUtil.getSystemUserName());
	}
	
	/**
	 * Gets or creates the parent folder to store the configurations of the 
	 * external repositories in the current IG
	 * 
	 * @param parentNodeId the node id of the current IG
	 * @param create should the parent folder be created?
	 * @return
	 */
	public NodeRef createOrGetRepositoryConfigurationFolder(String parentNodeId, boolean create) {
		
		NodeRef parentNodeRef = new NodeRef(parentNodeId);
		
		String localName = "ExternalRepositoryConfigurations";
		
		NodeRef externalRepositoryNodeRef = nodeService.getChildByName(
					parentNodeRef, ContentModel.ASSOC_CONTAINS, localName);
		
		if (externalRepositoryNodeRef != null) {
			return externalRepositoryNodeRef;
		}
		
		if (!create) {
			return null;
		}
		
		Map<QName, Serializable> properties = new HashMap<QName, Serializable>();
		
		properties.put(ContentModel.PROP_NAME, localName);
		
		externalRepositoryNodeRef = nodeService.createNode(
					parentNodeRef, ContentModel.ASSOC_CONTAINS, 
					QName.createQName(ContentModel.PROP_NAME.getNamespaceURI(), 
					QName.createValidLocalName(localName)), 
					CircabcModel.TYPE_EXTERNAL_REPOSITORY_CONFIGURATION_FOLDER, 
					properties).getChildRef();
		
		return externalRepositoryNodeRef;
	}
	
	/**
	 * @see eu.cec.digit.circabc.service.external.repositories.ExternalRepositoriesManagementService#removeRepository(java.lang.String, java.lang.String)
	 */
	@Override
	public void removeRepository(String parentNodeId, String repositoryName) {
		
		final NodeRef parentNodeRef = 
				createOrGetRepositoryConfigurationFolder(parentNodeId, false);
		
		if (parentNodeRef == null) {
			return;
		}
		
		final NodeRef configuredRepositoryNodeRef = nodeService.getChildByName(
				parentNodeRef, CircabcModel.ASSOC_CONTAINSCON_FIGURATIONS, 
					repositoryName);
		
		if (configuredRepositoryNodeRef == null) {
			return;
		}
		
		final RetryingTransactionHelper txnHelper = transactionService
											.getRetryingTransactionHelper();
		
		final RetryingTransactionCallback<Object> callback = 
				new RetryingTransactionCallback<Object>() {
			
			public Object execute() throws Throwable {
				
				nodeService.removeChild(parentNodeRef, configuredRepositoryNodeRef);
				
				return null;
			}
		};
		
		AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>() {
			
     		public Object doWork() {
     			return txnHelper.doInTransaction(callback, false, true);
     		}
     	}, AuthenticationUtil.getSystemUserName());
	}
	
	/**
	 * @see eu.cec.digit.circabc.service.external.repositories.ExternalRepositoriesManagementService#getConfiguredRepositories(java.lang.String)
	 */
	@Override
	public Collection<RepositoryConfiguration> getConfiguredRepositories(String parentNodeId) {
		
		NodeRef parentNodeRef = 
				createOrGetRepositoryConfigurationFolder(parentNodeId, false);
		
		Collection<RepositoryConfiguration> repositories = new ArrayList<RepositoryConfiguration>();
		
		if (parentNodeRef == null) {
			return repositories;
		}
		
		List<ChildAssociationRef> children = nodeService.getChildAssocs(parentNodeRef);
		
		for (ChildAssociationRef child : children) {
			
			RepositoryConfiguration configuration = new RepositoryConfiguration();
			
			NodeRef childNodeRef = child.getChildRef();
			
			String name = (String) nodeService.getProperty(childNodeRef, ContentModel.PROP_NAME);
			Date registrationDate = (Date) nodeService.getProperty(childNodeRef, ContentModel.PROP_CREATED);
			
			configuration.setName(name);
			configuration.setRegistrationDate(registrationDate);
			
			repositories.add(configuration);
		}
		
		return repositories;
	}
	
	/**
	 * @see eu.cec.digit.circabc.service.external.repositories.ExternalRepositoriesManagementService#getRepositoryDataForDocument(java.lang.String)
	 */
	@SuppressWarnings("all")
	@Override
	public Map<String, Map<String, String>> getRepositoryDataForDocument(String documentId) {
		
		final NodeRef nodeRef = new NodeRef(documentId);
		
		if (nodeService.hasAspect(nodeRef, 
				CircabcModel.ASPECT_EXTERNALLY_PUBLISHED)) {
			
			final HashMap<String, HashMap<String, String>> repositoriesInfo = 
					(HashMap<String, HashMap<String,String>>) 
						nodeService.getProperty(nodeRef, 
							CircabcModel.PROP_REPOSITORIES_INFO);
			
			HashMap<String, String> data = 
							repositoriesInfo.get(EXTERNAL_REPOSITORY_NAME);
			
			// Check if it's a version in which case, the properties cannot 
			// change
			StoreRef storeRef = nodeRef.getStoreRef();
			
			if (!VERSION_STORE.equals(storeRef.getProtocol())) {
				
				// First try to get the document data from Hermes and 
				// update Alfreso entries, and if it does not respond, 
				// get it from Alfresco
				boolean checked = getDataFromExternalRepository(data);
				
				// Update properties
				final RetryingTransactionHelper txnHelper = transactionService
						.getRetryingTransactionHelper();
				
				final RetryingTransactionCallback<Object> callback = 
									new RetryingTransactionCallback<Object>() {
					
					public Object execute() throws Throwable {
						
						nodeService.setProperty(nodeRef, CircabcModel.
									PROP_REPOSITORIES_INFO, repositoriesInfo);
						
						return null;
					}
				};
				
				if (checked) {
					
					AuthenticationUtil.runAs(
								new AuthenticationUtil.RunAsWork<Object>() {
						
						public Object doWork() {
							return txnHelper.doInTransaction(callback, false, 
																true);
						}
					}, AuthenticationUtil.getSystemUserName());
				}
			}
			
			// After updating properties, return the data to display
			// Translate properties according to active language
			HashMap<String, String> displayData = new HashMap<String, String>();
			for (Map.Entry<String, String> entry : data.entrySet()) {
				displayData.put(translate(entry.getKey()), entry.getValue());
			}
			
			HashMap<String, Map<String, String>> displayRepositoriesInfo = 
					new HashMap<String, Map<String, String>>();
			
			displayRepositoriesInfo.put(EXTERNAL_REPOSITORY_NAME, displayData);
			
			return (Map) displayRepositoriesInfo;
		}
		
		return new HashMap<String, Map<String, String>>();
	}
	
	/**
	 * Tries to get the data for the given document from Hermes
	 * 
	 * @param data
	 */
	private boolean getDataFromExternalRepository(HashMap<String, String> data) {
		
		if (!mustCheck(data.get(PROPERTY_LAST_CHECKED))) {
			return false;
		}
		
		// At this point document data can be retrieved again from the 
		// repository
		try {
			DocumentService_PortType documentService = 
									documentWebService.getDocumentService();
			
			eu.cec.digit.circabc.repo.hrs.ws.Document document = 
								documentService.getDocument(createHeader(
									userNameResolver.getUserName()), 
										data.get(PROPERTY_DOCUMENT_ID));
			
			data.put(PROPERTY_TITLE, document.getTitle());
			data.put(PROPERTY_COMMENTS, document.getComments());
			data.put(PROPERTY_MAIL_TYPE, document.getMailType().toString());
			
			SimpleDateFormat simpleDateFormat =	new SimpleDateFormat(EXTENDED_DATE_FORMAT);
			data.put(PROPERTY_LAST_CHECKED, simpleDateFormat.format(new Date()));
			
			data.put(PROPERTY_DATA_STATUS, STATUS_OK);
			
			return true;
		} 
		catch (Exception e) {
			logger.warn("Unable to retrieve document data from Hermes. " +
					"Showing old data. Data could have changed from the " +
					"last view.", e);
			data.put(PROPERTY_DATA_STATUS, STATUS_UNABLE);
			return false;
		}
	}
	
	/**
	 * Is it necessary to check for updates to the document from Hermes?
	 * 
	 * @param data
	 * @return
	 */
	private boolean mustCheck(String lastCheckedString) {
		
		SimpleDateFormat simpleDateFormat =	new SimpleDateFormat(EXTENDED_DATE_FORMAT);
		
		Date currentDate = new Date();
		
		if (lastCheckedString != null) {
			
			Date lastChecked = null;
			
			try {
				lastChecked = simpleDateFormat.parse(lastCheckedString);
			} 
			catch (ParseException e) {
				logger.error("Error parsing the last check date.", e);
				return false;
			}
			
			// 12 hs
			if (currentDate.getTime() - lastChecked.getTime() < 
								1000 * 60 * minutesToCheck) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * @see eu.cec.digit.circabc.service.external.repositories.ExternalRepositoriesManagementService#publishDocument(java.lang.String, java.lang.String, eu.cec.digit.circabc.repo.external.repositories.RegistrationRequest)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String publishDocument(final String repositoryName, final String nodeId,
			final RegistrationRequest registrationRequest) {
		
		final NodeRef nodeRef = new NodeRef(nodeId);
		
		registrationRequest.setRegistrationUserName(userNameResolver.getUserName());
		
		final RetryingTransactionHelper txnHelper = transactionService
				.getRetryingTransactionHelper();
		
		final RetryingTransactionCallback<String> callback = 
					new RetryingTransactionCallback<String>() {
			
			public String execute() throws Throwable {
				
				// Publish
				RegistrationSummary registrationSummary = 
								publishToHermes(nodeRef, registrationRequest);
				// Error
				if (registrationSummary.getRegistrationNumber() == null) {
					return registrationSummary.getDocumentId();
				}
				
				// Add workflow for recipients
				AssignmentTaskRequest request = new AssignmentTaskRequest();
				request.setAssignmentUserName(registrationRequest.getRegistrationUserName());
				request.setDocumentId(registrationSummary.getDocumentId());
				request.setAssigneeIds(registrationRequest.getSenderIds());
				
				AssignmentWorkflow workflow = addAssignmentTask(request);
				
				String workflowId = null;
				
				// TODO Retry workflow?
				if (workflow.getWorkflowId() != 0) {
					workflowId = String.valueOf(workflow.getWorkflowId());
				}
				else {
					workflowId = workflow.getDocumentId();
				}
				
				// Add to Alfresco
				HashMap<String, HashMap<String, String>> repositoriesInfo = null;
				
				if (nodeService.hasAspect(nodeRef, 
						CircabcModel.ASPECT_EXTERNALLY_PUBLISHED)) {
					
					repositoriesInfo = (HashMap<String, HashMap<String, String>>) 
							nodeService.getProperty(
									nodeRef, CircabcModel.PROP_REPOSITORIES_INFO);
				}
				else {
					repositoriesInfo = new HashMap<String, HashMap<String, String>>();
					
					Map<QName, Serializable> aspectProperties = 
										new HashMap<QName, Serializable>();
					
					nodeService.addAspect(nodeRef, 
							CircabcModel.ASPECT_EXTERNALLY_PUBLISHED, 
							aspectProperties);
				}
				
				// Add the new published info
				if (wasPublishedTo(repositoryName, nodeId)) {
					repositoriesInfo.remove(repositoryName);
				}
				
				String senderIds = registrationRequest.getSenderIds().toString();
				String recipientIds = registrationRequest.getRecipientIds().toString();
				
				HashMap<String, String> data = new HashMap<String, String>();
				
				data.put(PROPERTY_DOCUMENT_ID, registrationSummary.getDocumentId());
				data.put(PROPERTY_REGISTRATION_NUMBER, 
								registrationSummary.getRegistrationNumber());
				data.put(PROPERTY_SAVE_NUMBER, 
								registrationSummary.getSaveNumber());
				SimpleDateFormat simpleDateFormat = 
								new SimpleDateFormat(DATE_FORMAT);
				data.put(PROPERTY_REGISTRATION_DATE, 
								simpleDateFormat.format(
									registrationSummary.getRegistrationDate()));
				data.put(PROPERTY_ENCODING_DATE, simpleDateFormat.format(
									registrationSummary.getEncodingDate()));
				data.put(PROPERTY_WORKFLOW_ID, workflowId);
				data.put(PROPERTY_REGISTRATION_USER, 
								registrationRequest.getRegistrationUserName());
				data.put(PROPERTY_INTERNAL_SENDERS, 
							senderIds.substring(1, senderIds.length() - 1));
				data.put(PROPERTY_EXTERNAL_RECIPIENTS, 
							recipientIds.substring(1, recipientIds.length() - 1));
				
				getDataFromExternalRepository(data);
				
				repositoriesInfo.put(repositoryName, data);
				
				nodeService.setProperty(nodeRef, 
						CircabcModel.PROP_REPOSITORIES_INFO, repositoriesInfo);
				
				// In case the workflow failed
				if (workflow.getWorkflowId() == 0) {
					return PUBLISH_SUCCESS_WORKFLOW_FAILURE;
				}
				
				return null;
			}
		};
		
		String error = AuthenticationUtil.runAs(
					new AuthenticationUtil.RunAsWork<String>() {
			
			public String doWork() {
				return txnHelper.doInTransaction(callback, false, true);
			}
		}, AuthenticationUtil.getSystemUserName());
		
		return error;
	}
	
	/**
	 * Translates locale messages from bundles. 
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	protected final String translate(final String key, final Object ... params) {
		return WebClientHelper.translate(key, params);
	}
	
	/**
	 * @see eu.cec.digit.circabc.service.external.repositories.ExternalRepositoriesManagementService#wasPublishedTo(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean wasPublishedTo(String repositoryName, String nodeId) {
		
		NodeRef nodeRef = new NodeRef(nodeId);
		
		if (nodeService.hasAspect(nodeRef, 
					CircabcModel.ASPECT_EXTERNALLY_PUBLISHED)) {
			
			if (repositoryName == null) {
				return true;
			}
			
			HashMap<String, HashMap<String, String>> repositoriesInfo = 
					(HashMap<String, HashMap<String, String>>) 
						nodeService.getProperty(
							nodeRef, CircabcModel.PROP_REPOSITORIES_INFO);
			
			return repositoriesInfo != null && repositoriesInfo.containsKey(repositoryName);
		}
		
		return false;
	}
	
	/**
	 * @see eu.cec.digit.circabc.service.external.repositories.ExternalRepositoriesManagementService#getExternalRecipients(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> getExternalRecipients(String firstName,
			String lastName, String organization) {
		
		List<String> recipients = new ArrayList<String>();
		
		if ((firstName == null || firstName.isEmpty()) && (lastName == null 
				 				|| lastName.isEmpty())) {
			return recipients;
		}
		
		try {
			EntityService_PortType entityService = entityWebService.getEntityService();
			
			FindCurrentExternalEntityRequest entityRequest = 
					new FindCurrentExternalEntityRequest();
			
			entityRequest.setSearchForPerson(
					new FindCurrentExternalEntityRequestSearchForPerson(
							lastName, firstName, organization, null, null));
			
			CurrentExternalEntityRetrievalOptions retrievalOptions = 
					new CurrentExternalEntityRetrievalOptions(false, false);
			
			CurrentExternalEntity[] currentExternalEntities = entityService.
					findCurrentExternalEntity(createHeader(
							userNameResolver.getUserName()), entityRequest, 
							null, retrievalOptions);
			
			if (currentExternalEntities == null) {
				return recipients;
			}
			
			for (CurrentExternalEntity entity : currentExternalEntities) {
				
				CurrentExternalPerson person = entity.getCurrentExternalPerson();
				
				recipients.add(person.getFirstName() + " " + 
								person.getLastName() + " - " + 
								entity.getCurrentEntityId());
			}
		} 
		catch (Exception e) {
			logger.error("Error getting the external recipients.", e);
		}
		
		return recipients;
	}
	
// =========================================================================	
	
	/**
	 * 
	 * 
	 * @param xmlToParse
	 * @param xPaths
	 * @return
	 */
//	private String getRepos(String xmlToParse, String xPathExpression) {
//		
//		NodeList nodes = (NodeList) evaluateXPath(xmlToParse, xPathExpression, 
//							XPathConstants.NODESET);
//		
//		for (int i = 0; i < nodes.getLength(); i ++) {
//			
//			Node node = nodes.item(i);
//			
//			NamedNodeMap map = node.getAttributes();
//			
//			RepositoryConfiguration data = new RepositoryConfiguration();
//			
//			data.setName(map.getNamedItem("name").getNodeValue());
//			
////				availableRepositories.add(data);
//		}
//		
//		return null;
//	}
	
	private RegistrationSummary publishToHermes(NodeRef nodeRef, 
				RegistrationRequest registrationRequest) throws Exception {
		
		InputStream content = null;
		
		try {
			ContentReader reader = contentService.getReader(nodeRef, ContentModel.PROP_CONTENT);
			
			content = reader.getContentInputStream();
			
			String uploadResponse = uploadDocument(registrationRequest.getRegistrationUserName(), content);
			
			boolean success = Boolean.parseBoolean((String) evaluateXPath(uploadResponse, "/hrs:response/hrs:success/text()", XPathConstants.STRING));
			
			if (!success) {
				String error = (String) evaluateXPath(uploadResponse, "/hrs:response/hrs:error/text()", XPathConstants.STRING);
				return new RegistrationSummary(ERROR_UPLOAD_CONTENT + " " + error, null, null, null, null);
			}
			
			String uploadedContentId = (String) evaluateXPath(uploadResponse, "/hrs:response/hrs:content-id/text()", XPathConstants.STRING);
			
			String fileName = (String) nodeService.getProperty(nodeRef, ContentModel.PROP_NAME);
			
			registrationRequest.setUploadedContentId(uploadedContentId);
			registrationRequest.setFileName(fileName);
			
			return registerDocument(registrationRequest);
		} 
		finally {
			if (content != null) {
				content.close();
			}
		}
	}
	
	/**
	 * Builds and evaluates an XPath expression given an XML
	 * 
	 * @param xml
	 * @param xPathExpression
	 * @param qName
	 * @return The result of evaluating the expression.
	 */
	private Object evaluateXPath(String xml, String xPathExpression, 
							javax.xml.namespace.QName qName) throws Exception {
		
		InputStream xmlInputStream = null;
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			xmlInputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			Document document = builder.parse(xmlInputStream);
			
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();
			xPath.setNamespaceContext(new EUNamespaceContext());
			
			XPathExpression expr = xPath.compile(xPathExpression);
			
			return expr.evaluate(document, qName);
		} 
		finally {
			if (xmlInputStream != null) {
				try {
					xmlInputStream.close();
				} 
				catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}
	
	/**
	 * Class to manage the default namespaces of Hermes XML answers.
	 *
	 * @author schwerr
	 */
	private static class EUNamespaceContext implements NamespaceContext {
		
		public String getNamespaceURI(String prefix) {
			if("hrs".equals(prefix)) {
				return "http://ec.europa.eu/sg/hrs/dts/1.0";
			}
			return null;
		}
		
		public String getPrefix(String namespaceURI) {
			return null;
		}
		
		public Iterator<String> getPrefixes(String namespaceURI) {
			return null;
		}
	}
	
	/**
	 * Uploads a document to the Hermes temp space and returns its id embedded 
	 * in an XML, if successful
	 * This id has to be extracted to be used to link the uploaded content when 
	 * registering the document.
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	private String uploadDocument(String userName, InputStream content) throws Exception {
		
		PostMethod postMethod = new PostMethod(uploadUrl);
		
		RequestEntity requestEntity = new InputStreamRequestEntity(content, 
								"application/octet-stream; charset=UTF-8");
		try {
			postMethod.setRequestHeader(new Header("X-HRS-USER", userName));
			postMethod.setRequestHeader(new Header("X-HRS-TICKET", 
								proxyTicketResolver.getProxyTicket()));
			postMethod.setRequestHeader(new Header("X-HRS-APPLICATION", 
								applicationId));
			
			postMethod.setRequestEntity(requestEntity);
			
			HttpClient httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams().
								setConnectionTimeout(5000);
			
			int status = httpClient.executeMethod(postMethod);
			
			if (status == HttpStatus.SC_OK) {
				return postMethod.getResponseBodyAsString();
			} 
			else {
				return buildError(HttpStatus.getStatusText(status) + 
						" Response=" + postMethod.getResponseBodyAsString());
			}
		} 
		catch (Exception e) {
			logger.error("Error uploading the given document.", e);
			return buildError(e.getMessage());
		}
		finally {
			postMethod.releaseConnection();
		}
	}
	
	/**
	 * Builds an Hermes-like XML error message to keep a standard response in 
	 * case there is another kind of error.
	 * 
	 * @param message
	 * @return
	 */
	private String buildError(String message) {
		return "<response xmlns=\"http://ec.europa.eu/sg/hrs/dts/1.0\" " +
				"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
				"xsi:schemaLocation=\"http://ec.europa.eu/sg/hrs/dts/1.0 " +
				"http://www.cc.cec/hrs-dts/DataTransferService?XSD\">" +
					"<success>false</success>" +
					"<error>Status=" + message + "</error>" +
			"</response>";
	}
	
	/**
	 * Gets the ids of the internal persons (senders)
	 * 
	 * @see eu.cec.digit.circabc.service.external.repositories.ExternalRepositoriesManagementService#getInternalSenders(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> getInternalSenders(String firstName, String lastName) {
		
		List<String> senders = new ArrayList<String>();
		
		if ((firstName == null || firstName.isEmpty()) && (lastName == null 
				 				|| lastName.isEmpty())) {
			return senders;
		}
		
		try {
			EntityService_PortType entityService = entityWebService.getEntityService();
			
			FindCurrentInternalEntityRequest entityRequest = 
					new FindCurrentInternalEntityRequest();
			
			entityRequest.setSearchForPerson(
					new FindCurrentInternalEntityRequestSearchForPerson(
							lastName, firstName, null, null));
			
			CurrentInternalEntity[] currentInternalEntities = entityService.
					findCurrentInternalEntity(createHeader(
						userNameResolver.getUserName()), entityRequest, null);
			
			if (currentInternalEntities == null) {
				return senders;
			}
			
			for (CurrentInternalEntity entity : currentInternalEntities) {
				
				CurrentInternalPerson person = entity.getCurrentInternalPerson();
				
				senders.add(person.getFirstName() + " " + person.getLastName() 
									+ " - " + entity.getCurrentEntityId());
			}
		} 
		catch (Exception e) {
			logger.error("Error getting the internal senders.", e);
		}
		
		return senders;
    }
	
	/**
	 * @see eu.cec.digit.circabc.service.external.repositories.ExternalRepositoriesManagementService#isUserAuthorizedtoPublish(java.lang.String)
	 */
	@Override
	public boolean isUserAuthorizedtoPublish(String ecasUserName) {
		
		// Check if the user is already cached
		if (internalEntities.contains(ecasUserName)) {
			return true;
		}
		
		try {
			UserService_PortType userService = userWebService.getUserService();
			
			UserProfile userProfile = userService.getUserProfile(
											createHeader(ecasUserName));
			
			// If we get BASE_USER the user is not allowed to publish, any 
			// other profile is ok
			if (UserProfile._BASE_USER.equals(userProfile.getValue())) {
				return false;
			}
		} 
		catch (Exception e) {
			// If an exception is thrown, the user is not registered or there 
			// was an error in Hermes, so don't allow to publish this time
			return false;
		} 
		
		// Add user to the cache (not need to synchronize since the 
		// map was build with Collections.synchronizedMap)
		internalEntities.add(ecasUserName);
		
		return true;
	}
	
	/**
	 * Registers a document in Hermes.
	 * 
	 * @param registrationRequest
	 * @return
	 */
	private RegistrationSummary registerDocument(RegistrationRequest registrationRequest) {
		
		try {
			DocumentService_PortType documentService = documentWebService.getDocumentService();
			
			// Add sender id
			List<String> senderIds = registrationRequest.getSenderIds();
			SendersToAddSender[] senders = new SendersToAddSender[senderIds.size()];
			for (int idx = 0; idx < senderIds.size(); idx ++) {
				SendersToAddSender sender = new SendersToAddSender();
				sender.setCurrentEntityId(senderIds.get(idx));
				senders[idx] = sender;
			}
			
			// Add recipient ids
			List<String> recipientIds = registrationRequest.getRecipientIds();
			RecipientsToAddRecipient[] recipients = 
						new RecipientsToAddRecipient[recipientIds.size()];
			for (int idx = 0; idx < recipientIds.size(); idx ++) {
				RecipientsToAddRecipient recipient = new RecipientsToAddRecipient();
				recipient.setCurrentEntityId(recipientIds.get(idx));
				recipient.setCode(RecipientCode.TO);
				recipients[idx] = recipient;
			}
			
			// Add items
			UploadedItemToAdd item = new UploadedItemToAdd(
					registrationRequest.getFileName(), 
					registrationRequest.getUploadedContentId(), 
						AttachmentTypeToAdd.NATIVE_ELECTRONIC, "EN", 
						ItemKindToAdd.MAIN, null, null);
			
			DocumentRegistrationRequest request = new DocumentRegistrationRequest(
					registrationRequest.getSubject(), new Date(), new Date(), 
					SecurityClassification.NORMAL, false, null, 
					null, registrationRequest.getComments(), 
					MailType.fromString(registrationRequest.getMailType()), 
					senders, recipients, new UploadedItemToAdd[] {item});
			
			return documentService.registerDocument(createHeader(
					registrationRequest.getRegistrationUserName()), request);
		} 
		catch (Exception e) {
			logger.error("Error registering document.", e);
			return new RegistrationSummary(ERROR_REGISTER_DOCUMENT + " " + 
									e.getMessage(), null, null, null, null);
		}
	}
	
	/**
	 * Starts an assignment task workflow.
	 * 
	 * @param request
	 * @return
	 */
	private AssignmentWorkflow addAssignmentTask(AssignmentTaskRequest request) {
		
		AssignmentWorkflow workflow = null;
		
		try {
			WorkflowService_PortType workflowService = 
									workflowWebService.getWorkflowService();
			
			// Add all the assignment tasks
			List<String> assigneeIds = request.getAssigneeIds();
			AssignmentTaskToAdd[] tasks = 
								new AssignmentTaskToAdd[assigneeIds.size()];
			for (int idx = 0; idx < assigneeIds.size(); idx ++) {
				AssignmentTaskToAdd task = new AssignmentTaskToAdd();
				task.setAssigneeId(assigneeIds.get(idx));
				task.setCode(AssignmentTaskToAddCode.CLASS);
				tasks[idx] = task;
			}
			
			AddAssignmentsRequest assignmentsRequest = 
										new AddAssignmentsRequest(
											request.getDocumentId(), tasks);
			
			workflow = workflowService.addAssignments(createHeader(
						request.getAssignmentUserName()), assignmentsRequest);
		} 
		catch (Exception e) {
			logger.error("Error while assigning workflow.", e);
			workflow = new AssignmentWorkflow(0, 
							"Error assigning workflow to document with Id: " + 
									request.getDocumentId() + " - " + 
										e.getMessage(), null, null);
		}
		
		return workflow;
	}
	
	/**
	 * Builds the header for the HRS requests.
	 * 
	 * @return
	 */
	private eu.cec.digit.circabc.repo.hrs.ws.Header createHeader(String userName) {
		return new eu.cec.digit.circabc.repo.hrs.ws.Header(userName, null, 
				proxyTicketResolver.getProxyTicket(), applicationId);
	}
	
	/**
	 * Sets the value of the nodeService
	 * 
	 * @param nodeService the nodeService to set.
	 */
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}
	
	/**
	 * Sets the value of the transactionService
	 * 
	 * @param transactionService the transactionService to set.
	 */
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	/**
	 * Sets the value of the contentService
	 * 
	 * @param contentService the contentService to set.
	 */
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}
	
	/**
	 * Sets the value of the applicationId
	 * 
	 * @param applicationId the applicationId to set.
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	
	/**
	 * Sets the value of the userNameResolver
	 * 
	 * @param userNameResolver the userNameResolver to set.
	 */
	public void setUserNameResolver(UserNameResolver userNameResolver) {
		this.userNameResolver = userNameResolver;
	}
	
	/**
	 * Gets the value of the userNameResolver
	 * 
	 * @return the userNameResolver
	 */
	@Override
	public UserNameResolver getUserNameResolver() {
		return userNameResolver;
	}
	
	/**
	 * Sets the value of the proxyTicketResolver
	 * 
	 * @param proxyTicketResolver the proxyTicketResolver to set.
	 */
	public void setProxyTicketResolver(ProxyTicketResolver proxyTicketResolver) {
		this.proxyTicketResolver = proxyTicketResolver;
	}
	
	/**
	 * Sets the value of the uploadUrl
	 * 
	 * @param uploadUrl the uploadUrl to set.
	 */
	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}
	
	/**
	 * Sets the value of the endpointBaseAddress
	 * 
	 * @param endpointBaseAddress the endpointBaseAddress to set.
	 */
	public void setEndpointBaseAddress(String endpointBaseAddress) {
		this.endpointBaseAddress = endpointBaseAddress;
	}
	
	/**
	 * Sets the value of the minutesToCheck
	 * 
	 * @param minutesToCheck the minutesToCheck to set.
	 */
	public void setMinutesToCheck(long minutesToCheck) {
		this.minutesToCheck = minutesToCheck;
	}
	
	/**
	 * Sets the value of the operational
	 * 
	 * @param operational the operational to set.
	 */
	public void setOperational(boolean operational) {
		this.operational = operational;
	}
}
