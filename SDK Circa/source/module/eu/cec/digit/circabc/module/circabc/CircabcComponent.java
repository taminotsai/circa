/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.module.circabc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ApplicationModel;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.module.AbstractModuleComponent;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.CategoryService;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.CircabcConfig;
import eu.cec.digit.circabc.config.CircabcConfiguration;
import eu.cec.digit.circabc.service.cmr.security.CircabcConstant;
import eu.cec.digit.circabc.service.customisation.NodePreferencesService;
import eu.cec.digit.circabc.service.customisation.mail.MailPreferencesService;
import eu.cec.digit.circabc.service.profile.CircabcRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.ProfileManagerServiceFactory;
import eu.cec.digit.circabc.service.struct.ManagementService;
import eu.cec.digit.circabc.service.user.UserService;
import eu.cec.digit.circabc.util.CircabcUserDataBean;

/**
 * A basic component that will be started for this module. This class is started
 * only once by default. A parameter "executeOnceOnly" can be use to force
 * starting of this class.
 * 
 * This class is loaded after Spring configuration
 * 
 * @author Clinckart Stephane
 * 
 *         Migration 3.1 -> 3.4.6 - 02/12/2011 PersonDaoImpl was commented.
 */
public class CircabcComponent extends AbstractModuleComponent {
	/** Logger */
	private static final Log logger = LogFactory.getLog(CircabcComponent.class);

	private NodeService nodeService;
	private ManagementService managementService;
	private ProfileManagerServiceFactory profileManagerServiceFactory;
	private PersonService personService;
	private UserService userService;
	private CategoryService categoryService;
	private TransactionService transactionService;
	private MailPreferencesService mailPreferencesService;
	private NodePreferencesService nodePreferencesService;
	
	private String execute;
	
	private boolean isExecute;
	
	protected void executeInternal() throws Throwable {
		final RetryingTransactionHelper txnHelper = transactionService
				.getRetryingTransactionHelper();
		
		if (! isExecute)
		{
			return;
		}
		
		runSetGuestReadPermissionOnVersionStoreCallback(txnHelper);

		runCreateCircabcCallback(txnHelper);

		runCreateCircabcAdminUser(txnHelper);

		runCreateCategoryHeaderRoot(txnHelper);

		runUpdateRootPreferance(txnHelper);

		runCreateTemplateUser(txnHelper);
	}

	private void runCreateTemplateUser(final RetryingTransactionHelper txnHelper) {
		final RetryingTransactionCallback<Object> createTemplateUserCallback = new RetryingTransactionCallback<Object>() {
			public Object execute() throws Throwable {
				createTemplateUser();
				return null;
			}

		};

		AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>() {
			public Object doWork() {

				txnHelper.doInTransaction(createTemplateUserCallback, false,
						true);

				return null;
			}

		}, AuthenticationUtil.getSystemUserName());
	}

	private void runUpdateRootPreferance(
			final RetryingTransactionHelper txnHelper) {
		final RetryingTransactionCallback<Object> updateRootPreferanceCallback = new RetryingTransactionCallback<Object>() {
			public Object execute() throws Throwable {
				updateRootPreferance();
				return null;
			}

		};

		AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>() {
			public Object doWork() {

				txnHelper.doInTransaction(updateRootPreferanceCallback, false,
						true);

				return null;
			}

		}, AuthenticationUtil.getSystemUserName());
	}

	private void runCreateCategoryHeaderRoot(
			final RetryingTransactionHelper txnHelper) {
		final RetryingTransactionCallback<Object> createCategoryHeaderRootCallback = new RetryingTransactionCallback<Object>() {
			public Object execute() throws Throwable {

				createCategoryHeaderRoot();
				return null;
			}

		};

		AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>() {
			public Object doWork() {

				txnHelper.doInTransaction(createCategoryHeaderRootCallback,
						false, true);

				return null;
			}

		}, AuthenticationUtil.getSystemUserName());
	}

	private void runCreateCircabcAdminUser(
			final RetryingTransactionHelper txnHelper) {
		final RetryingTransactionCallback<Object> createCircabcAdminUserCallback = new RetryingTransactionCallback<Object>() {
			public Object execute() throws Throwable {
				createCircabcAdminUser();
				return null;
			}

		};

		AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>() {
			public Object doWork() {

				txnHelper.doInTransaction(createCircabcAdminUserCallback,
						false, true);

				return null;
			}

		}, AuthenticationUtil.getSystemUserName());
	}

	private void runCreateCircabcCallback(
			final RetryingTransactionHelper txnHelper) {
		final RetryingTransactionCallback<Object> createCircabcCallback = new RetryingTransactionCallback<Object>() {
			public Object execute() throws Throwable {

				createCircabc();
				return null;
			}

		};
		AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>() {
			public Object doWork() {

				txnHelper.doInTransaction(createCircabcCallback, false, true);

				return null;
			}
		}, AuthenticationUtil.getSystemUserName());
	}

	private void runSetGuestReadPermissionOnVersionStoreCallback(
			final RetryingTransactionHelper txnHelper) {
		final RetryingTransactionCallback<Object> setGuestReadPermissionOnVersionStoreCallback = new RetryingTransactionCallback<Object>() {
			public Object execute() throws Throwable {

				setGuestReadPermissionOnVersionStore();
				return null;
			}

		};

		AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>() {
			public Object doWork() {

				txnHelper.doInTransaction(
						setGuestReadPermissionOnVersionStoreCallback, false,
						true);

				return null;
			}
		}, AuthenticationUtil.getSystemUserName());
	}

	private void updateRootPreferance() {
		nodePreferencesService.updateRootReference();
	}

	private void createTemplateUser() {
		final CircabcUserDataBean templateUser = mailPreferencesService
				.getTemplateUserDetails();
		if (personService.personExists(templateUser.getUserName()) == false) {
			userService.createUser(templateUser, true);

			if (logger.isInfoEnabled()) {
				logger.info("Circabc template dirty user successfully created");
			}
		}
	}

	private void createCircabc() {
		if (managementService.getCircabcNodeRef() == null) {
			// create it

			final NodeRef companyHome = managementService
					.getCompanyHomeNodeRef();
			final String description = CircabcConfiguration
					.getProperty(CircabcConfiguration.CIRCABC_ROOT_NODE_DESCRIPTION_PROPERTIES);
			final String title = CircabcConfiguration
					.getProperty(CircabcConfiguration.CIRCABC_ROOT_NODE_TITLE_PROPERTIES);

			final NodeRef circabcNodeRef = managementService
					.createCircabc(companyHome);

			// apply the uifacets aspect - icon, title and description props
			final Map<QName, Serializable> uiFacetsProps = new HashMap<QName, Serializable>(
					5);
			uiFacetsProps.put(ApplicationModel.PROP_ICON, "space-icon-default");
			uiFacetsProps.put(ContentModel.PROP_TITLE, title);
			uiFacetsProps.put(ContentModel.PROP_DESCRIPTION, description);

			nodeService.addAspect(circabcNodeRef,
					ApplicationModel.ASPECT_UIFACETS, uiFacetsProps);

		}
	}

	private void createCircabcAdminUser() {
		final NodeRef circabcNodeRef = managementService.getCircabcNodeRef();
		String adminName = CircabcConfiguration
				.getProperty(CircabcConfiguration.ROOT_ADMIN_NAME_PROPERTIES);
		if (personService.getUserNamesAreCaseSensitive()) {
			adminName = adminName.toLowerCase();
		}

		if ((circabcNodeRef != null) && !personService.personExists(adminName)) {

			final String profile = CircabcRootProfileManagerService.Profiles.CIRCABC_ADMIN;
			final CircabcUserDataBean circaBCAdmin = new CircabcUserDataBean();
			if (CircabcConfig.OSS)
			{
				circaBCAdmin.setUserName(adminName);
		        circaBCAdmin.setFirstName(adminName);
		        circaBCAdmin.setLastName(adminName);
		        circaBCAdmin.setEmail(adminName);
			}
			final CircabcUserDataBean ldapUserDetail = userService
					.getLDAPUserDataByUid(adminName);
			circaBCAdmin.copyLdapProperties(ldapUserDetail);
			circaBCAdmin.setHomeSpaceNodeRef(circabcNodeRef);
			userService.createUser(circaBCAdmin, true);

			final CircabcRootProfileManagerService circabcRootProfileManagerService = getProfileManagerServiceFactory()
					.getCircabcRootProfileManagerService();
			circabcRootProfileManagerService.addPersonToProfile(circabcNodeRef,
					circaBCAdmin.getUserName(), profile);
		}
	}

	private void createCategoryHeaderRoot() {
		if (managementService.getRootCategoryHeader() == null) {
			// create it
			if (logger.isDebugEnabled()) {
				logger.debug("Creation of the circabc root category header ... ");
			}

			final String name = CircabcConfiguration
					.getProperty(CircabcConfiguration.ROOT_HEADER_NAME_PROPERTIES);
			final String description = CircabcConfiguration
					.getProperty(CircabcConfiguration.ROOT_HEADER_DESCRIPTION_PROPERTIES);

			final Map<QName, Serializable> titledProps = new HashMap<QName, Serializable>(
					1, 1.0f);

			final StoreRef spaceStore = new StoreRef(
					StoreRef.PROTOCOL_WORKSPACE, "SpacesStore");

			final NodeRef catHeaderRoot = categoryService.createRootCategory(
					spaceStore, ContentModel.ASPECT_GEN_CLASSIFIABLE, name);
			titledProps.put(ContentModel.PROP_DESCRIPTION, description);

			nodeService.addAspect(catHeaderRoot, ContentModel.ASPECT_TITLED,
					titledProps);

			if (logger.isDebugEnabled()) {
				logger.debug("The root category header successfully created with the name "
						+ name);
			}
		}
	}

	/**
	 * Workaround: Execute this method at the startup of Circabc to allow the
	 * Guest authority to access to the version store.
	 */
	private void setGuestReadPermissionOnVersionStore() {

		if (logger.isDebugEnabled()) {
			logger.debug("The Read permission is being setted to the Guest Authority on the version store...");
		}

		final NodeRef versionRootNodeRef = nodeService
				.getRootNode(this.serviceRegistry.getVersionService()
						.getVersionStoreReference());

		this.serviceRegistry.getPermissionService().setPermission(
				versionRootNodeRef, CircabcConstant.GUEST_AUTHORITY,
				PermissionService.READ, true);

		if (logger.isDebugEnabled()) {
			logger.debug("The Read permission successfully setted to the Guest Authority on the version store");
		}
	}

	protected boolean needToCreateCircabc() {
		return managementService.getCircabcNodeRef() == null;
	}

	/**
	 * @param nodeService
	 *            the nodeService to set
	 */
	public final void setNodeService(final NodeService nodeService) {
		this.nodeService = nodeService;
	}

	/**
	 * @param managementService
	 *            the managementService to set
	 */
	public final void setManagementService(
			final ManagementService managementService) {
		this.managementService = managementService;
	}

	/**
	 * @param personService
	 *            the personService to set
	 */
	public void setPersonService(final PersonService personService) {
		this.personService = personService;
	}

	/**
	 * @param UserService
	 *            the UserService to set
	 */
	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	/**
	 * @param categoryService
	 *            the categoryService to set
	 */
	public void setCategoryService(final CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/**
	 * @return the profileManagerServiceFactory
	 */
	protected final ProfileManagerServiceFactory getProfileManagerServiceFactory() {
		return profileManagerServiceFactory;
	}

	/**
	 * @param profileManagerServiceFactory
	 *            the profileManagerServiceFactory to set
	 */
	public final void setProfileManagerServiceFactory(
			final ProfileManagerServiceFactory profileManagerServiceFactory) {
		this.profileManagerServiceFactory = profileManagerServiceFactory;
	}

	/**
	 * @return the transactionService
	 */
	public final TransactionService getTransactionService() {
		return transactionService;
	}

	/**
	 * @param transactionService
	 *            the transactionService to set
	 */
	public final void setTransactionService(
			final TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	/**
	 * @return the categoryService
	 */
	public final CategoryService getCategoryService() {
		return categoryService;
	}

	/**
	 * @return the managementService
	 */
	public final ManagementService getManagementService() {
		return managementService;
	}

	/**
	 * @return the nodeService
	 */
	public final NodeService getNodeService() {
		return nodeService;
	}

	/**
	 * @return the personService
	 */
	public final PersonService getPersonService() {
		return personService;
	}

	/**
	 * @return the userService
	 */
	public final UserService getUserService() {
		return userService;
	}

	/**
	 * @param mailPreferencesService
	 *            the mailPreferencesService to set
	 */
	public final void setMailPreferencesService(
			final MailPreferencesService mailPreferencesService) {
		this.mailPreferencesService = mailPreferencesService;
	}

	public final void setNodePreferencesService(
			NodePreferencesService nodePreferencesService) {
		this.nodePreferencesService = nodePreferencesService;
	}

	public String getExecute() {
		return execute;
	}

	public void setExecute(String execute) {
		
		this.execute = execute;
		isExecute = Boolean.valueOf(execute);
	}
	

}
