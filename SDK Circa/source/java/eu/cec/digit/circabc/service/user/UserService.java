/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.user;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.repo.security.authentication.AuthenticationException;
import org.alfresco.service.Auditable;
import org.alfresco.service.NotAuditable;
import org.alfresco.service.PublicService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.bean.users.UserPreferencesBean;

import eu.cec.digit.circabc.util.CircabcUserDataBean;

/**
 * It is a spring bean that manages basic operations over CircabcUsers like
 * reading, updating, deleting and creating.
 * 
 * @author atadian - Trasys
 * @author Yanick Pignot
 * 
 */

@PublicService
public interface UserService
{
	public static final QName PREF_CONTENT_FILTER_LANGUAGE = QName.createQName(NamespaceService.APP_MODEL_1_0_URI,
			"content-filter-language");
	public static final QName PREF_INTERFACE_LANGUAGE = QName.createQName(NamespaceService.APP_MODEL_1_0_URI,
			UserPreferencesBean.PREF_INTERFACELANGUAGE /*
														 * this durty trick is
														 * used to avoid
														 * problems during
														 * alfresco version
														 * migration
														 * "interface-language"
														 */);

	public static final QName PREF_SIGNATURE = QName.createQName(NamespaceService.APP_MODEL_1_0_URI, "signature");

	/**
	 * Creates a new User in Alfresco with the Circabc Aspect
	 * 
	 * @param user
	 *            id
	 */
	@Auditable( parameters = { "circabcUser" })
	public NodeRef createLdapUser(final String userId);

	/**
	 * Creates a new User in Alfresco with the Circabc Aspect
	 * 
	 * @param pCircabcUser
	 *            the data of the new user
	 */
	@Auditable(parameters = { "circabcUser" })
	public NodeRef createUser(final CircabcUserDataBean circabcUser);

	/**
	 * Creates a new User in Alfresco with the Circabc Aspect
	 * 
	 * @param pCircabcUser
	 *            the data of the new user
	 * @param enabled
	 *            if the user should be enbled or not. If not, the user can't
	 *            authenticate itself
	 */
	@Auditable(parameters = { "circabcUser", "enabled" })
	public NodeRef createUser(final CircabcUserDataBean circabcUser, final boolean enabled);

	/**
	 * It deletes a Circabc user on alfresco
	 * 
	 * @param pCircabcUser
	 *            the user to delete
	 * @throws AuthenticationException
	 *             any error
	 */
	@Auditable(parameters = { "pUserName" })
	public void deleteUser(final String pUserName) throws AuthenticationException;

	/**
	 * @return the number of days in which the account will be expired if the
	 *         account is not activated after the self registration process
	 */
	@NotAuditable
	public int getAccountExpirationDays();

	/**
	 * @return list of category, profile for given user
	 */
	@Auditable(/* key = Auditable.Key.NO_KEY */)
	public List<UserCategoryMembershipRecord> getCategories(final String pUserName);

	@Auditable(/* key = Auditable.Key.NO_KEY */)
	public CircabcUserDataBean getCircabcUserDataBean(final String userName);

	/**
	 * @return list event roots for given user
	 */
	@Auditable(/* key = Auditable.Key.NO_KEY */)
	public List<NodeRef> getEventRootNodes(final String pUserName);

	/**
	 * @return list of category, interest group and profile for given user
	 */
	@Auditable(/* key = Auditable.Key.NO_KEY */)
	public List<UserIGMembershipRecord> getInterestGroups(final String pUserName);
	
	
	/**
	 * @return list of interest groups for given user and categories
	 */
	@Auditable(/*key = Auditable.Key.NO_KEY*/)
	public List<UserIGMembershipRecord> getInterestGroups(final String userName, List<NodeRef> categories);

	/** LDAP Implementation */
	@Auditable(parameters = { "pLdapUserID" })
	public CircabcUserDataBean getLDAPUserDataByUid(final String pLdapUserID);

	@Auditable(parameters = { "mail", "uid", "moniker", "cn", "conjunction" })
	public List<String> getLDAPUserIDByIdMonikerEmailCn(final String uid, final String moniker, final String email,
			final String cn, final boolean conjunction);

	@Auditable(parameters = { "pMail" })
	public List<String> getLDAPUserIDByMail(final String mail);

	@Auditable(parameters = { "mail", "domain" })
	public List<String> getLDAPUserIDByMailDomain(final String mail, final String domain);

	/**
	 * Get the noderef of the user
	 * 
	 * @param pUserName
	 *            the user
	 */
	@Auditable(parameters = { "pUserName" })
	public NodeRef getPerson(final String pUserName);

	/**
	 * Get a setted preference of the given user
	 * 
	 * @param person
	 * @param preferenceQname
	 * @return
	 */
	@Auditable(parameters = { "person", "preferenceQname" })
	public Serializable getPreference(final NodeRef person, final QName preferenceQname);

	/**
	 * Get the user by an email
	 * 
	 * @param email
	 * @return
	 */
	@Auditable(parameters = { "email" })
	public String getUserByEmail(final String email);

	/**
	 * Get the domain of the user or null if it is an alfresco user.
	 * 
	 * @param pUserName
	 *            the user
	 */
	@Auditable(parameters = { "pUserName" })
	public String getUserDomain(final String pUserName);

	/**
	 * Get email for Circabc user on alfresco
	 * 
	 * @param pUserName
	 *            the user
	 */
	@Auditable(parameters = { "pUserName" })
	public String getUserEmail(final String pUserName);

	/**
	 * Get first and last name for Circabc user concanate with space
	 * 
	 * @param pUserName
	 *            the user
	 */
	@Auditable(parameters = { "pUserName" })
	public String getUserFullName(final String pUserName);

	/**
	 * Return user name for given user email
	 * 
	 * @param email
	 * @return user name
	 */
	@Auditable()
	public String getUserNameByEmail(final String email);

	@Auditable(parameters = { "pDomain", "pCriteria" })
	public List<SearchResultRecord> getUsersByDomainFirstNameLastNameEmail(final String pDomain, final String pCriteria);

	@Auditable(parameters = { "mail", "domain" })
	public List<SearchResultRecord> getUsersByMailDomain(final String mail, final String domain);

	/**
	 * @param nodeRef
	 * @param service
	 * @param permission
	 * @return
	 */
	@NotAuditable
	abstract public Set<String> getUsersWithPermission(final NodeRef nodeRef, final String permission);

	/**
	 * Return if the given email is already used by another user in Circabc.
	 * This method is usefull to garentee the unicity of the email inside
	 * alfresco.
	 * 
	 * @param email
	 *            the email to test
	 * @param excludeDataInTheCurrentTransaction
	 *            if the data setted in the current transaction must be excluded
	 * @return true if the email is already used.
	 */
	@NotAuditable
	public boolean isEmailExists(final String email, final boolean excludeDataInTheCurrentTransaction);

	/**
	 * Set password for Circabc user on alfresco
	 * 
	 * @param pUserName
	 *            the user
	 * @param pNewPassword
	 */
	@Auditable(parameters = { "pUserName", "pNewPassword" }, recordable = { true, false })
	public void setPassword(final String pUserName, final char[] pNewPassword);

	/**
	 * set a preference of the given user
	 * 
	 * @param person
	 * @param preferenceQname
	 * @param value
	 */
	@Auditable(parameters = { "person", "preferenceQname", "value" })
	public void setPreference(final NodeRef person, final QName preferenceQname, final Serializable value);

	@Auditable()
	public void updateMissingLastNamePersons();

	/**
	 * Update all the data of a Circabc user
	 * 
	 * @param pCircabcUser
	 *            Circabc user
	 */
	@Auditable(parameters = { "pCircabcUser" })
	public void updateUser(final CircabcUserDataBean pCircabcUser);

	/**
	 * Update the data of a Circabc user
	 * 
	 * @param pCircabcUser
	 *            Circabc user
	 * @param pNonAspectProperties
	 *            true if you want to udpate just the NonAspect Properties (the
	 *            one that a user is not allow change and are changed by the
	 *            batch process)
	 */
	@Auditable(parameters = { "pCircabcUser", "pNonAspectProperties" })
	public void updateUser(final CircabcUserDataBean pCircabcUser, final boolean pNonAspectProperties);
	
	/**
	 * Return true if the user is connected
	 */
	@Auditable(/*key =  Auditable.Key.NO_KEY*/)
	public boolean isUserOnline(final String user);
	
	/**
	 * @return	list of online users
	 */
	@Auditable(/*key =  Auditable.Key.NO_KEY*/)
	public Set<String> getAllOnlineUsers();
	
	/**
	 * @return list of online users for a specific InterestGroup
	 */
	@Auditable(/*key = Auditable.Key.ARG_0*/)
	public Set<String> getOnlineUsers(final NodeRef igNodeRef);
	
	
	
	@NotAuditable
	public void setRawPassword(final String userName ,final String password );
	
	/**
	 * create user newUserName 
	 * copy all properties from 
	 */
	@Auditable()
	public void copyUser(final String oldUserName , final String newUserName , boolean deleteOldUser,boolean copyOwnership,boolean copyMembership);
	
	
	@Auditable()
	public Map<String,String> getEcasUserDomains();
	
	/**
	 * @return true if the user with given userName exists
	 */
	@NotAuditable
	public boolean isUserExists(final String userName );
	
	/**
	 * delete users
	 *  
	 */
	@Auditable(/*key =  Auditable.Key.NO_KEY*/)
	public void deleteUsers(List<String> userNames );
	
	
	
	
}
