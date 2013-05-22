package eu.cec.digit.circabc.web;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.cmr.security.PersonService;

import eu.cec.digit.circabc.service.profile.ProfileManagerServiceFactory;
import eu.cec.digit.circabc.service.struct.ManagementService;
import eu.cec.digit.circabc.service.user.UserService;
import eu.cec.digit.circabc.web.bean.override.CircabcNavigationBean;

/*package*/ abstract class AbstractSearchUtils {

	/** Minimum characters for a user search query */
	public static final int MIN_CHAR_ALLOWED_FOR_QUERY = 3;

	/** Maximim number of elements put in the list */
	public static final int MAX_ELEMENTS_IN_LIST = 1000;

	public static final  String ALL_PROFILE = "all_profile";

	protected final static String STAR_WILDCARD_REGEX = "[\\*\\ ]*";


	/**
	 * @return the profileManagerServiceFactory
	 */
	protected static final CircabcNavigationBean getNavigator() {
		return Beans.getWaiNavigator();
	}


	/**
	 * @return the profileManagerServiceFactory
	 */
	protected static final ProfileManagerServiceFactory getProfileManagerServiceFactory() {
		return Services.getCircabcServiceRegistry(
				FacesContext.getCurrentInstance())
				.getProfileManagerServiceFactory();
	}

	/**
	 * @return the managementService
	 */
	protected static final ManagementService getManagementService() {
		return Services.getCircabcServiceRegistry(
				FacesContext.getCurrentInstance()).getManagementService();
	}

	/**
	 * @return the userService
	 */
	protected static final UserService getUserService() {
		return Services.getCircabcServiceRegistry(
				FacesContext.getCurrentInstance()).getUserService();
	}

	/**
	 * @return the person service
	 */
	protected static final PersonService getPersonService() {
		return Services.getAlfrescoServiceRegistry(
				FacesContext.getCurrentInstance()).getPersonService();
	}

	/**
	 * @return the nodeService
	 */
	protected static final NodeService getNodeService() {
		return Services.getAlfrescoServiceRegistry(
				FacesContext.getCurrentInstance()).getNodeService();
	}

	/**
	 * @return the permissionService
	 */
	protected static final PermissionService getPermissionService() {
		return Services.getAlfrescoServiceRegistry(
				FacesContext.getCurrentInstance()).getPermissionService();
	}

	/**
	 * @return the Authority Service
	 */
	protected static final AuthorityService getAuthorityService() {
		return Services.getAlfrescoServiceRegistry(
				FacesContext.getCurrentInstance()).getAuthorityService();
	}
}
