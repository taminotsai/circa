package eu.cec.digit.circabc.service.external.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import eu.cec.digit.circabc.repo.external.repositories.RegistrationRequest;
import eu.cec.digit.circabc.repo.external.repositories.RepositoryConfiguration;
import eu.cec.digit.circabc.repo.external.repositories.UserNameResolver;

/**
 * Interface for the service that manages the external repository publishing.
 * 
 * @author schwerr
 */
public interface ExternalRepositoriesManagementService {
	
	public static final String EXTERNAL_REPOSITORY_NAME = "Hermes";
	
	// Error messages (text is translated from the bundle)
	public static final String ERROR_UPLOAD_CONTENT = "error_upload_content";
	public static final String ERROR_REGISTER_DOCUMENT = "error_register_document";
	
	// Published document properties (text is translated from the bundle)
	public static final String PROPERTY_DOCUMENT_ID = "property_document_id";
	public static final String PROPERTY_REGISTRATION_NUMBER = "property_registration_number";
	public static final String PROPERTY_SAVE_NUMBER = "property_save_number";
	public static final String PROPERTY_REGISTRATION_DATE = "property_registration_date";
	public static final String PROPERTY_ENCODING_DATE = "property_encoding_date";
	public static final String PROPERTY_LAST_CHECKED = "property_last_checked";
	public static final String PROPERTY_DATA_STATUS = "property_data_status";
	public static final String PROPERTY_WORKFLOW_ID = "property_workflow_id";
	public static final String PROPERTY_INTERNAL_SENDERS = "property_internal_senders";
	public static final String PROPERTY_EXTERNAL_RECIPIENTS = "property_external_recipients";
	public static final String PROPERTY_REGISTRATION_USER = "property_registration_user";

	public static final String PROPERTY_TITLE = "publish_in_external_repositories_dialog_subject";
	public static final String PROPERTY_COMMENTS = "publish_in_external_repositories_dialog_comment";
	public static final String PROPERTY_MAIL_TYPE = "publish_in_external_repositories_dialog_mail_type";
	
	// Status
	public static final String STATUS_REGISTERED = "Registered";
	public static final String STATUS_FILED = "Filed";
	public static final String PUBLISH_SUCCESS_WORKFLOW_FAILURE = "publish_success_workflow_failure";
	
	public static final String STATUS_UNABLE = "Unable to check";
	public static final String STATUS_OK = "OK";
	
	// General format for the dates
	public static final String DATE_FORMAT = "dd MMMM yyyy";
	public static final String EXTENDED_DATE_FORMAT = "dd MMMM yyyy HH:mm";
	
	public static final String VERSION_STORE = "versionStore"; 
	
	/**
	 * Returns true if the external repositories system is operational.
	 * 
	 * @return
	 */
	public boolean isOperational();
	
	/**
	 * Retrieves the list of the available repositories configured to publish to.
	 * 
	 * @return
	 */
	public Collection<RepositoryConfiguration> getAvailableRepositories();
	
	/**
	 * Adds a repository to the list of configured repositories.
	 * 
	 * @param parentNodeId
	 * @param configuration
	 */
	public void addRepository(String parentNodeId, RepositoryConfiguration configuration);
	
	/**
	 * Removes a repository from the list of configured repositories.
	 * 
	 * @param parentNodeId
	 * @param repositoryName
	 */
	public void removeRepository(String parentNodeId, String repositoryName);
	
	/**
	 * Returns the list of all configured repositories.
	 * 
	 * @param parentNodeId
	 * @return
	 */
	public Collection<RepositoryConfiguration> getConfiguredRepositories(String parentNodeId);
	
	/**
	 * Gets the properties of a document for the repositories. 
	 * 
	 * @param documentId
	 * @return
	 */
	public Map<String, Map<String, String>> getRepositoryDataForDocument(String documentId);
	
	/**
	 * Publishes the given document to the given repository.
	 * 
	 * @param repositoryName
	 * @param nodeId
	 * @param registrationRequest
	 * @return
	 */
	public String publishDocument(String repositoryName, String nodeId, RegistrationRequest registrationRequest);
	
	/**
	 * Checks if the given document was published to the given repository.
	 * 
	 * @param repositoryName
	 * @param nodeId
	 * @return
	 */
	public boolean wasPublishedTo(String repositoryName, String nodeId);
	
	/**
	 * Gets the ids about the recipients given the user names.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param organization
	 * @return
	 */
	public List<String> getExternalRecipients(String firstName, String lastName, String organization);
	
	/**
	 * Gets the id of the given ECAS user if registered in Hermes.
	 * 
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public List<String> getInternalSenders(String firstName, String lastName);
	
	/**
	 * Retrieves the user's profile and according to the response determines if 
	 * the user is allowed to publish.
	 * 
	 * @param ecasUserName
	 * @return
	 */
	public boolean isUserAuthorizedtoPublish(String ecasUserName);
	
	/**
	 * Gets the value of the userNameResolver
	 * 
	 * @return the userNameResolver
	 */
	public UserNameResolver getUserNameResolver();
}
