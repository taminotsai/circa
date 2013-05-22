/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service;

/**
 * @author Stephane Clinckart
 */
import java.util.Collection;
import java.util.concurrent.ThreadPoolExecutor;

import org.alfresco.repo.policy.BehaviourFilter;
import org.alfresco.service.NotAuditable;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

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

public class CircabcServiceDescriptor implements BeanFactoryAware, CircabcServiceRegistry
{
	// Bean Factory
    private BeanFactory beanFactory = null;

    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException
    {
        this.beanFactory = beanFactory;
    }

    public Collection<QName> getServices()
    {
        // TODO: Implement
        throw new UnsupportedOperationException();
    }

    public boolean isServiceProvided(final QName service)
    {
    	try
    	{
    		return getService(service) != null;
    	}
    	catch(org.springframework.beans.factory.NoSuchBeanDefinitionException ex)
    	{
    		return false;
    	}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.alfresco.repo.service.ServiceRegistry#getService(org.alfresco.repo.ref.QName)
     */
    public Object getService(final QName service)
    {
        return beanFactory.getBean(service.getLocalName());
    }

    public CircabcRootProfileManagerService getCircabcRootProfileManagerService()
    {
        return (CircabcRootProfileManagerService) getService(ROOT_PROFILE_MANAGER_SERVICE);
    }

    public CategoryProfileManagerService getCategoryProfileManagerService()
    {
        return (CategoryProfileManagerService) getService(CATEGORY_PROFILE_MANAGER_SERVICE);
    }

    public IGRootProfileManagerService getIGRootProfileManagerService()
    {
        return (IGRootProfileManagerService) getService(IGROOT_PROFILE_MANAGER_SERVICE);
    }

    public LibraryProfileManagerService getLibraryProfileManagerService()
	{
		return (LibraryProfileManagerService) getService(LIBRARY_PROFILE_MANAGER_SERVICE);
	}

	public NewsGroupProfileManagerService getNewsGroupProfileManagerService()
	{
		return (NewsGroupProfileManagerService) getService(NEWSGROUP_PROFILE_MANAGER_SERVICE);
	}

	public SurveyProfileManagerService getSurveyProfileManagerService()
	{
		return (SurveyProfileManagerService) getService(SURVEY_PROFILE_MANAGER_SERVICE);
	}

	public InformationProfileManagerService getInformationProfileManagerService()
	{
		return (InformationProfileManagerService) getService(INFORMATION_PROFILE_MANAGER_SERVICE);
	}

	public EventProfileManagerService getEventProfileManagerService()
	{
		return (EventProfileManagerService) getService(EVENT_PROFILE_MANAGER_SERVICE);
	}

    public ProfileManagerServiceFactory getProfileManagerServiceFactory()
    {
        return (ProfileManagerServiceFactory) getService(PROFILE_MANAGER_SERVICE_FACTORY);
    }

    public ManagementService getManagementService()
    {
        return (ManagementService) getService(MANAGEMENT_SERVICE);
    }

	public UserService getUserService()
	{
		return (UserService) getService(CIRCABC_USER_SERVICE);
	}

	public KeywordsService getKeywordsService()
	{
		return (KeywordsService) getService(KEYWORDS_SERVICE);
	}

	public MailService getMailService()
	{
		return (MailService) getService(MAIL_SERVICE);
	}

	public MailToMembersService getMailToMembersService() {
		return (MailToMembersService) getService(MAIL_TO_MEMBERS_SERVICE);
	}

	public NotificationSubscriptionService getNotificationSubscriptionService()
	{
		return (NotificationSubscriptionService) getService(NOTIFICATION_SUBSCRIPTION_SERVICE);
	}

	public BulkService getBulkService()
	{
		return (BulkService) getService(BULK_SERVICE);
	}

	public BulkService getNonSecureBulkService()
	{
		return (BulkService) getService(NON_SECURED_BULK_SERVICE);
	}

	public NotificationService getNotificationService()
	{
		return (NotificationService) getService(NOTIFICATION_SERVICE);
	}

	public DynamicPropertyService getDynamicPropertieService() {

		return (DynamicPropertyService) getService(DYNAMIC_PROPERTY_SERVICE);
	}

	public ServerConfigurationService getServerConfigurationService()
	{
		return (ServerConfigurationService) getService(SERVER_CONFIGURATION_SERVICE);
	}

	public NodePreferencesService getNodePreferencesService()
	{
		return (NodePreferencesService) getService(NODE_CUSTOMISATION_SERVICE);
	}

	public NavigationConfigService getNavigationConfigService()
	{
		return (NavigationConfigService) getService(NAVIGATION_CONFIGURATION_SERVICE);
	}

	public NodePreferencesService getgetNavigationPreferencesService()
	{
		return (NodePreferencesService) getService(NAVIGATION_CUSTOMISATION_SERVICE);
	}

	public MailPreferencesService getMailPreferencesService()
	{
		return (MailPreferencesService) getService(MAIL_CUSTOMISATION_SERVICE);
	}

	public NavigationPreferencesService getNavigationPreferencesService()
	{
		return (NavigationPreferencesService) getService(NAVIGATION_CUSTOMISATION_SERVICE);
	}

	public LogoPreferencesService getLogoPreferencesService()
	{
		return (LogoPreferencesService) getService(LOGO_CUSTOMISATION_SERVICE);
	}

	public EventService getEventService()
	{
		return (EventService) getService(EVENT_SERVICE);
	}

	public ShareSpaceService getShareSpaceService()
	{
		return (ShareSpaceService) getService(SHARE_SPACE_SERVICE);
	}

	public EventService getNonSecureEventService()
	{
		return (EventService) getService(NON_SECURED_EVENT_SERVICE);
	}

	public ProfileManagerServiceFactory getNonSecureProfileManagerServiceFactory()
	{
	    return (ProfileManagerServiceFactory) getService(NON_SECURED_PROFILE_MANAGER_SERVICE_FACTORY);
	}

	public LogService getLogService()
	{
		return (LogService) getService(NON_SECURED_LOG_SERVICE);
	}

	public LockService getLockService()
	{
		return (LockService) getService(LOCK_SERVICE);
	}

	public ModerationService getModerationService()
	{
		return (ModerationService) getService(MODERATION_SERVICE);
	}

	public SupportService getSupportService()
	{
		return (SupportService) getService(SUPPORT_SERVICE);
	}

	public AttachementService getAttachementService()
	{
		return (AttachementService) getService(ATTACHEMENT_SERVICE);
	}

	public ServiceRegistry getAlfrescoServiceRegistry()
	{
		return (ServiceRegistry) getService(ALFRESCO_REGISTRY_SERVICE);
	}



	public ZipService getNonSecuredZipService()
	{
		return (ZipService) getService(NON_SECURED_ZIP_SERVICE);
	}

	public BulkService getNonSecuredBulkService()
	{
		return (BulkService) getService(NON_SECURED_BULK_SERVICE);
	}

	public IndexService getNonSecuredIndexService()
	{
		return (IndexService) getService(NON_SECURED_INDEX_SERVICE);
	}
	
	public DictionaryService getNonSecuredDictionaryService()
	{
		return (DictionaryService) getService(NON_SECURED_DICTIONARY_SERVICE);
	}

	public TransactionService getNonSecuredTransactionService()
	{
		return (TransactionService) getService(NON_SECURED_TRANSACTION_SERVICE);
	}

	public NodeService getNonSecuredNodeService()
	{
		return (NodeService) getService(NON_SECURED_NODE_SERVICE);
	}

	public SearchService getNonSecuredSearchService()
	{
		return (SearchService) getService(NON_SECURED_SEARCH_SERVICE);
	}
	
	public BehaviourFilter getPolicyBehaviourFilter()
	{
		return (BehaviourFilter) getService(POLICY_BEHAVIOUR_FILTER);
	}

	public PersonService getNonSecuredPersonService()
	{
		return (PersonService) getService(NON_SECURED_PERSON_SERVICE);
	}

	public NotificationManagerService getNotificationManagerService()
	{
		return (NotificationManagerService) getService(NOTIFICATION_MANAGER_SERVICE);
	}

	
	public ThreadPoolExecutor getAsyncThreadPoolExecutor() {
		return (ThreadPoolExecutor) getService(ASYNC_THREAD_POOL_EXECUTOR);
	}
}

