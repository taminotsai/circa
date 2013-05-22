/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service;

import java.util.Collection;
import java.util.concurrent.ThreadPoolExecutor;

import org.alfresco.repo.policy.BehaviourFilter;
import org.alfresco.service.NotAuditable;
import org.alfresco.service.PublicService;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;

import eu.cec.digit.circabc.service.admin.debug.ServerConfigurationService;
import eu.cec.digit.circabc.service.bulk.BulkService;
import eu.cec.digit.circabc.service.bulk.indexes.IndexService;
import eu.cec.digit.circabc.service.compress.ZipService;
import eu.cec.digit.circabc.service.customisation.NodePreferencesService;
import eu.cec.digit.circabc.service.customisation.logo.LogoPreferencesService;
import eu.cec.digit.circabc.service.customisation.mail.MailPreferencesService;
import eu.cec.digit.circabc.service.customisation.nav.NavigationConfigService;
import eu.cec.digit.circabc.service.customisation.nav.NavigationPreferencesService;
import eu.cec.digit.circabc.service.dynamic.property.DynamicPropertyService;
import eu.cec.digit.circabc.service.event.EventService;
import eu.cec.digit.circabc.service.keyword.KeywordsService;
import eu.cec.digit.circabc.service.lock.LockService;
import eu.cec.digit.circabc.service.log.LogService;
import eu.cec.digit.circabc.service.mail.MailService;
import eu.cec.digit.circabc.service.mail.MailToMembersService;
import eu.cec.digit.circabc.service.namespace.CircabcNameSpaceService;
import eu.cec.digit.circabc.service.newsgroup.AttachementService;
import eu.cec.digit.circabc.service.newsgroup.ModerationService;
import eu.cec.digit.circabc.service.notification.NotificationManagerService;
import eu.cec.digit.circabc.service.notification.NotificationService;
import eu.cec.digit.circabc.service.notification.NotificationSubscriptionService;
import eu.cec.digit.circabc.service.profile.CategoryProfileManagerService;
import eu.cec.digit.circabc.service.profile.CircabcRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.EventProfileManagerService;
import eu.cec.digit.circabc.service.profile.IGRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.InformationProfileManagerService;
import eu.cec.digit.circabc.service.profile.LibraryProfileManagerService;
import eu.cec.digit.circabc.service.profile.NewsGroupProfileManagerService;
import eu.cec.digit.circabc.service.profile.ProfileManagerServiceFactory;
import eu.cec.digit.circabc.service.profile.SurveyProfileManagerService;
import eu.cec.digit.circabc.service.sharespace.ShareSpaceService;
import eu.cec.digit.circabc.service.struct.ManagementService;
import eu.cec.digit.circabc.service.support.SupportService;
import eu.cec.digit.circabc.service.user.UserService;

/**
 * This interface represents the registry of public Repository Services.
 * The registry provides meta-data about each service and provides
 * access to the service interface.
 * @author Clinckart Stephane
 */
@PublicService
public interface CircabcServiceRegistry
{
    static final String CIRCABC_SERVICE_REGISTRY = "circabcServiceRegistry";

    static final QName ALFRESCO_REGISTRY_SERVICE = QName.createQName(NamespaceService.ALFRESCO_URI, "ServiceRegistry");

    static final QName ROOT_PROFILE_MANAGER_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, CircabcRootProfileManagerService.PROXIED_SERVICE_NAME);
    static final QName CATEGORY_PROFILE_MANAGER_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, CategoryProfileManagerService.PROXIED_SERVICE_NAME);
    static final QName IGROOT_PROFILE_MANAGER_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, IGRootProfileManagerService.PROXIED_SERVICE_NAME);
    static final QName LIBRARY_PROFILE_MANAGER_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, LibraryProfileManagerService.PROXIED_SERVICE_NAME);
    static final QName NEWSGROUP_PROFILE_MANAGER_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, NewsGroupProfileManagerService.PROXIED_SERVICE_NAME);
    static final QName SURVEY_PROFILE_MANAGER_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, SurveyProfileManagerService.PROXIED_SERVICE_NAME);
    static final QName INFORMATION_PROFILE_MANAGER_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, InformationProfileManagerService.PROXIED_SERVICE_NAME);
    static final QName EVENT_PROFILE_MANAGER_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, EventProfileManagerService.PROXIED_SERVICE_NAME);

    static final QName PROFILE_MANAGER_SERVICE_FACTORY = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "ProfileManagerServiceFactory");
    static final QName MANAGEMENT_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "ManagementService");
    static final QName KEYWORDS_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "KeywordsService");
    static final QName CIRCABC_USER_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "UserService");
    static final QName MAIL_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "CircabcMailService");
    static final QName MAIL_TO_MEMBERS_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "MailToMembersService");
    static final QName BULK_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "BulkService");
    static final QName NOTIFICATION_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "NotificationService");
    static final QName NOTIFICATION_SUBSCRIPTION_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "NotificationSubscriptionService");
    static final QName NOTIFICATION_MANAGER_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "NotificationManagerService");
    static final QName DYNAMIC_PROPERTY_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "DynamicPropertiesService");
    static final QName SERVER_CONFIGURATION_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "ServerConfigurationService");
    static final QName NODE_CUSTOMISATION_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "NodePreferencesService");
    static final QName MAIL_CUSTOMISATION_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "MailPreferencesService");
    static final QName NAVIGATION_CUSTOMISATION_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "NavigationPreferencesService");
    static final QName NAVIGATION_CONFIGURATION_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "navigationConfigService");
    static final QName LOGO_CUSTOMISATION_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "LogoPreferencesService");
    static final QName EVENT_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "EventService");
    static final QName SHARE_SPACE_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "ShareSpaceService");
    static final QName NON_SECURED_EVENT_SERVICE= QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "eventService");
    static final QName LOCK_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "circabcLockService");
    static final QName MODERATION_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "ModerationService");
    static final QName SUPPORT_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "SupportService");
    static final QName ATTACHEMENT_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "AttachementService");
    static final QName STATISTICS_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "StatisticsService");
    static final QName ASYNC_THREAD_POOL_EXECUTOR = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "AsyncThreadPoolExecutor");

    static final QName NON_SECURED_PROFILE_MANAGER_SERVICE_FACTORY = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "profileManagerServiceFactory");
    static final QName NON_SECURED_LOG_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "logService");
    static final QName NON_SECURED_ZIP_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "zipService");
    static final QName NON_SECURED_BULK_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "bulkService");
    static final QName NON_SECURED_INDEX_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "indexService");
    static final QName NON_SECURED_DICTIONARY_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "dictionaryService");
    static final QName NON_SECURED_TRANSACTION_SERVICE = QName.createQName(NamespaceService.ALFRESCO_URI, "transactionService");
    static final QName NON_SECURED_NODE_SERVICE = QName.createQName(NamespaceService.ALFRESCO_URI, "nodeService");
    static final QName NON_SECURED_SEARCH_SERVICE = QName.createQName(NamespaceService.ALFRESCO_URI, "searchService");
    static final QName POLICY_BEHAVIOUR_FILTER = QName.createQName(NamespaceService.ALFRESCO_URI, "policyBehaviourFilter");
    static final QName NON_SECURED_PERSON_SERVICE = QName.createQName(NamespaceService.ALFRESCO_URI, "personService");



    /**
     * Get the list of services provided by Circabc
     *
     * @return  list of provided Services
     */
    @NotAuditable
    Collection<QName> getServices();

    /**
     * Is the specified service provided by Circabc?
     *
     * @param service  name of service to test provision of
     * @return true => provided, false => not provided
     */
    @NotAuditable
    boolean isServiceProvided(QName service);

    /**
     * Get the specified service.
     *
     * @param service  name of service to retrieve
     * @return the service interface (must cast to interface as described in service meta-data)
     */
    @NotAuditable
    Object getService(QName service);

    /**
     * @return the ProfileManagerServiceFactory service
     */
    @NotAuditable
    ProfileManagerServiceFactory getProfileManagerServiceFactory();

    /**
     * @return the CircabcRootProfileManagerServie service
     */
    @NotAuditable
    CircabcRootProfileManagerService getCircabcRootProfileManagerService();

    /**
     * @return the CategoryProfileManagerService service
     */
    @NotAuditable
    CategoryProfileManagerService getCategoryProfileManagerService();

    /**
     * @return the IGRootProfileManagerService service
     */
    @NotAuditable
    IGRootProfileManagerService getIGRootProfileManagerService();

    /**
     * @return the LibraryProfileManagerService service
     */
    @NotAuditable
    LibraryProfileManagerService getLibraryProfileManagerService();

    /**
     * @return the NewsGroupProfileManagerService service
     */
    @NotAuditable
    NewsGroupProfileManagerService getNewsGroupProfileManagerService();

    /**
     * @return the SurveyProfileManagerService service
     */
    @NotAuditable
    SurveyProfileManagerService getSurveyProfileManagerService();

    /**
     * @return the EventProfileManagerService service
     */
    @NotAuditable
    EventProfileManagerService getEventProfileManagerService();

    /**
     * @return the InformationProfileManagerService service
     */
    @NotAuditable
    InformationProfileManagerService getInformationProfileManagerService();

    /**
     * @return the global circabc management service
     */
    @NotAuditable
    ManagementService getManagementService();

    /**
     * @return the keywords service
     */
    @NotAuditable
    KeywordsService getKeywordsService();

    /**
     * @return the user service for circabc
     */
    @NotAuditable
    UserService getUserService();

    @NotAuditable
    MailService getMailService();

    @NotAuditable
    MailToMembersService getMailToMembersService();

    /**
     *
     * @return the bulk service
     */
    @NotAuditable
    BulkService getBulkService();


    /**
    *
    * @return the bulk service
    */
   @NotAuditable
   BulkService getNonSecuredBulkService();

    /**
     * @return the Notification Subscription Service
     */
    @NotAuditable
    NotificationSubscriptionService getNotificationSubscriptionService();

    /**
     * @return the Notification Service
     */
    @NotAuditable
    NotificationService getNotificationService();

    /**
     * @return the Dynamic Properties Service
     */
    @NotAuditable
    DynamicPropertyService getDynamicPropertieService();


    /**
     * @return the Server Configuration Service
     */
    @NotAuditable
    ServerConfigurationService getServerConfigurationService();

    /**
     * @return the Node Preferences Service for customization purposes
     */
    @NotAuditable
    NodePreferencesService getNodePreferencesService();

    /**
     * @return the Navigation Preferences Service for customization purposes
     */
    @NotAuditable
    NavigationPreferencesService getNavigationPreferencesService();

    /**
     * @return the Navigation Preferences config Service
     */
    @NotAuditable
    NavigationConfigService getNavigationConfigService();

    /**
     * @return the Mail Preferences Service for customization purposes
     */
    @NotAuditable
    MailPreferencesService getMailPreferencesService();

    /**
     * @return the Logo Preferences Service for customization purposes
     */
    @NotAuditable
    LogoPreferencesService getLogoPreferencesService();

    /**
     * @return the Event Service
     */

    @NotAuditable
	EventService getEventService();


    @NotAuditable
	EventService getNonSecureEventService();

    /**
     * @return the SharedSpace Service
     */

    @NotAuditable
    ShareSpaceService getShareSpaceService();

    /**
     * @return the non secure ProfileManagerServiceFactory service
     */
    @NotAuditable
    ProfileManagerServiceFactory getNonSecureProfileManagerServiceFactory();

    /**
     * @return the non secure service
     */
    @NotAuditable
    LogService getLogService();

    /**
     * @return the non secure service
     */
    @NotAuditable
	LockService getLockService();

    /**
     * @return the moderation service
     */
    @NotAuditable
	ModerationService getModerationService();

    /**
     * @return the attachment service
     */
    @NotAuditable
    AttachementService getAttachementService();

    /**
     * @return the support service
     */
    @NotAuditable
    SupportService getSupportService();

    /**
     * @return the alfresco service registry
     */
    @NotAuditable
    ServiceRegistry getAlfrescoServiceRegistry();

    /**
     * @return the non secure zipservice
     */
    @NotAuditable
    ZipService getNonSecuredZipService();

    /**
     * @return the non secure indexservice
     */
    @NotAuditable
	IndexService getNonSecuredIndexService();
    
    /**
     * @return the non secure dictionaryService
     */
    @NotAuditable
	DictionaryService getNonSecuredDictionaryService();

    /**
     * @return the non secure Transaction Service
     */
    @NotAuditable
	TransactionService getNonSecuredTransactionService();

    /**
     * @return the non secure Node Service
     */
    @NotAuditable
	NodeService getNonSecuredNodeService();

    /**
     * @return the non secure Search Service
     */
    @NotAuditable
	SearchService getNonSecuredSearchService();
    
    /**
     * @return the policy Behaviour Filter
     */
    @NotAuditable
    BehaviourFilter getPolicyBehaviourFilter();
    
    /**
     * @return the non secure Person Service
     */
    @NotAuditable
	PersonService getNonSecuredPersonService();

    
    /**
     * @return the non secure Notification Manager Service
     */
    @NotAuditable
	NotificationManagerService getNotificationManagerService();

    /**
     * @return thread pool executor in order to execute long running wai dialog  action  async
     */
    @NotAuditable
	ThreadPoolExecutor getAsyncThreadPoolExecutor();
}


