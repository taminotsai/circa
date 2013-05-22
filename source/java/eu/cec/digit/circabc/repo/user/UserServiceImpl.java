/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.user;

import static org.alfresco.model.ContentModel.PROP_USERNAME;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.extensions.surf.util.I18NUtil;
import org.alfresco.model.ApplicationModel;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.configuration.ConfigurableService;
import org.alfresco.repo.security.authentication.AuthenticationException;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.TicketComponent;
import org.alfresco.repo.security.permissions.dynamic.OwnerDynamicAuthority;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.MLText;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.repository.datatype.DefaultTypeConverter;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.ResultSetRow;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.AccessPermission;
import org.alfresco.service.cmr.security.AccessStatus;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.service.cmr.security.MutableAuthenticationService;
import org.alfresco.service.cmr.security.OwnableService;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.bean.repository.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.config.CircabcConfiguration;
import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.model.UserModel;
import eu.cec.digit.circabc.service.cmr.security.CircabcConstant;
import eu.cec.digit.circabc.service.profile.CircabcRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.IGRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.ProfileManagerServiceFactory;
import eu.cec.digit.circabc.service.profile.permissions.UserPermissions;
import eu.cec.digit.circabc.service.struct.ManagementService;
import eu.cec.digit.circabc.service.user.LdapUserService;
import eu.cec.digit.circabc.service.user.SearchResultRecord;
import eu.cec.digit.circabc.service.user.UserCategoryMembershipRecord;
import eu.cec.digit.circabc.service.user.UserCategoryMembershipRecordComparator;
import eu.cec.digit.circabc.service.user.UserIGMembershipRecord;
import eu.cec.digit.circabc.service.user.UserIGMembershipRecordComparator;
import eu.cec.digit.circabc.service.user.UserService;
import eu.cec.digit.circabc.util.CircabcUserDataBean;
import eu.cec.digit.circabc.util.CircabcUserDataFillerUtil;
import eu.cec.digit.circabc.web.validator.PasswordValidator;

/**
 * It is a spring bean that manages basic operations over CircaUsers like
 * reading, updating, deleting and creating.
 *
 * @author atadian - Trasys
 * @TODO redefine the password for the new user created
 */
public class UserServiceImpl implements UserService
{
	/** Logger */
	private static final Log logger = LogFactory.getLog(UserServiceImpl.class);

	/** PersonService reference */
	private PersonService personService;

	/** NodeService reference (not secure) */
	private NodeService nodeService;

	/** PermissionService reference */
	private PermissionService permissionService;

	/** AuthenticationService reference */
	private MutableAuthenticationService authenticationService;

	/** ticketComponent */
	private TicketComponent ticketComponent;

	/** The search service reference */
	private SearchService searchService;

	/** ManagementService reference */
	private ManagementService managementService;

	/** configurableService reference */
	private ConfigurableService configurableService;

	/** namespaceService reference */
	private NamespaceService namespaceService;

	/** ldapService reference */
	private LdapUserService ldapUserService;
	
	
	/** ownableService reference */
	private OwnableService ownableService;


	/** profileManagerServiceFactory reference */
	transient private ProfileManagerServiceFactory profileManagerServiceFactory;


	private NodeRef peopleRef = null;

	/**
	 * The default expiration number of days after the
	 * self registration process. This value should be used
	 * only if the related properties is not found
	 **/
	private static final int DEFAULT_EXIPRATION_NUMBER_OF_DAYS = 60;



	private AuthorityService authorityService;

	private NodeRef nodeRef;

	private static final String CI_CIRCA_IGROOT_MASTER_GROUP = "@ci\\:circaIGRootMasterGroup:";

	private static final String CI_CIRCA_CATEGORY_MASTER_GROUP = "@ci\\:circaCategoryMasterGroup:";

	private static final String GROUP_PREFIX = "GROUP_";

	private static final String GROUP_CIRCA_BC_MASTER_GROUP = "GROUP_CircaBC--MasterGroup";

	private static final String MASTER_GROUP = "--MasterGroup--";

	private static final String MASTER_GROUP_REVERSE = "--puorGretsaM--";


	public void updateMissingLastNamePersons()
	{
		String nullLastNamePersons = "ISNULL:\"cm:lastName\" AND TYPE:\"{http://www.alfresco.org/model/content/1.0}person\"";
		ResultSet resultSet = null;
		try
		{
			final SearchParameters sp = new SearchParameters();
			sp.setLanguage(SearchService.LANGUAGE_LUCENE);
			sp.setQuery(nullLastNamePersons);
			sp.addStore(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE);
			resultSet = searchService.query(sp);
			for (final ResultSetRow row : resultSet)
			{
				final String name = (String) row.getValue(ContentModel.PROP_USERNAME);
				//do not wont to update alfresco technical account guest and admin
				if (!(name.equalsIgnoreCase(CircabcConstant.GUEST_AUTHORITY) || name.equalsIgnoreCase("admin")))
				{
					final CircabcUserDataBean user = getLDAPUserDataByUid(name);
					if (user == null)
					{
						if (logger.isWarnEnabled())
						{
							logger.warn("user "+ name + " does not exists");
						}
					}
					else
					{
						updateUser(user);
					}
				}
			}
		}
		catch (final Exception e) {
			if (logger.isErrorEnabled())
			{
				logger.error("Error when updating missing last name persons",e);
			}
		}
		finally
		{
			if(resultSet != null)
			{
				resultSet.close();
			}
		}


	}

	/**
	 * Get or create the node used to store user preferences. Utilises the
	 * 'configurable' aspect on the Person linked to this user.
	 */
	@Deprecated // see eu.cec.digit.circabc.business.helper.UserManager
    private synchronized NodeRef getUserPreferencesRef(final NodeRef person, final boolean create)
    {
       NodeRef prefRef = null;

       if (nodeService.hasAspect(person, ApplicationModel.ASPECT_CONFIGURABLE) == false)
       {
    	   if(!create)
    	   {
    		   return null;
    	   }

          // create the configuration folder for this Person node
          configurableService.makeConfigurable(person);
       }

       // target of the assoc is the configurations folder ref
       final NodeRef configRef = configurableService.getConfigurationFolder(person);
       if (configRef == null)
       {
          throw new IllegalStateException("Unable to find associated 'configurations' folder for node: " + person);
       }

       final String xpath = NamespaceService.APP_MODEL_PREFIX + ":" + "preferences";
       final List<NodeRef> nodes = searchService.selectNodes(
             configRef,
             xpath,
             null,
             namespaceService,
             false);


       if (nodes.size() == 1)
       {
          prefRef = nodes.get(0);
       }
       else
       {
          // create the preferences Node for this user
          final ChildAssociationRef childRef = nodeService.createNode(
                configRef,
                ContentModel.ASSOC_CONTAINS,
                QName.createQName(NamespaceService.APP_MODEL_1_0_URI, "preferences"),
                ContentModel.TYPE_CMOBJECT);

          prefRef = childRef.getChildRef();
      }



       return prefRef;
    }

	@Deprecated // see eu.cec.digit.circabc.business.helper.UserManager
    public Serializable getPreference(final NodeRef person, final QName preferenceQname)
    {
    	final NodeRef prefRef = getUserPreferencesRef(person, false);

    	if(prefRef == null)
    	{
    		return null;
    	}
    	else
    	{
    		if(UserService.PREF_INTERFACE_LANGUAGE.equals(preferenceQname))
    		{
    			final Serializable value = nodeService.getProperty(prefRef, preferenceQname);
    			if(value instanceof Locale) {
    				final Locale locale = (Locale)value;
    				return locale.getLanguage();
    			} else {
    				return value;
    			}
    		}
    		return nodeService.getProperty(prefRef, preferenceQname);
    	}
    }

	@Deprecated // see eu.cec.digit.circabc.business.helper.UserManager
	public void setPreference(final NodeRef person, final QName preferenceQname, final Serializable value)
	{
		final NodeRef prefRef = getUserPreferencesRef(person, true);

		if(UserService.PREF_INTERFACE_LANGUAGE.equals(preferenceQname))
		{
			if(value instanceof Locale)
			{
				final Locale locale = (Locale)value;
				nodeService.setProperty(prefRef, preferenceQname, locale.getLanguage());
			}
			else
			{
				nodeService.setProperty(prefRef, preferenceQname, value);
			}
		}
		else
		{
			nodeService.setProperty(prefRef, preferenceQname, value);
		}
	}

	/**
	 * Creates a new User in Alfresco with the Circabc Aspect
	 *
	 * @param pCircabcUser
	 *            the data of the new user
	 */
	public NodeRef createUser(final CircabcUserDataBean pCircabcUser)
	{
		// TODO : Manage correctly the creation Date in circauseraspect - DIGIT-CIRCABC-213

		// create the node to represent the Person
		final NodeRef newPerson = this.personService.createPerson(pCircabcUser.getAttributesAsMap());

		//fix DIGIT-CIRCABC-407
		char[] newPassword = null;
		try
		{
			newPassword =  new PasswordValidator().generate();
		}
		catch (final NoSuchAlgorithmException e)
		{
			if(logger.isErrorEnabled())
				logger.error("NoSuchAlgorithmException");
			throw  new RuntimeException("Can not generate password Algoritam does not exists");
		}

		final String password = (pCircabcUser.getPassword() == null) ? new String(newPassword) : pCircabcUser.getPassword();

		if  (!this.authenticationService.authenticationExists(pCircabcUser.getUserName()))
		{
			this.authenticationService.createAuthentication(pCircabcUser.getUserName(), password.toCharArray());
		}


		// ensure the user can access their own Person object
		this.permissionService.setPermission(newPerson, pCircabcUser.getUserName(), permissionService.getAllPermission(), true);

		final NodeRef homeSpace = pCircabcUser.getHomeSpaceNodeRef();
		if(homeSpace != null)
		{
			//	Restrict user permission in his HomeSpace
			try
			{
				this.permissionService.clearPermission(homeSpace, pCircabcUser.getUserName());
			}
			catch(final NullPointerException ex)
			{
				// don't worry, just a workaround without effect.
				if(logger.isWarnEnabled())
				{
					logger.warn("Impossible to clear permissions for user " + pCircabcUser.getUserName() + " on node " + homeSpace, ex);
				}
			}
		}


		// create the ACEGI Authentication instance for the new user

		// set user as consumer on root
		final NodeRef root = this.managementService.getCompanyHomeNodeRef();

		this.permissionService.clearPermission(root, pCircabcUser.getUserName());

		if(!authorityService.authorityExists(pCircabcUser.getUserName()))
		{
			authorityService.addAuthority(CircabcRootProfileManagerService.ALL_CIRCA_USERS_AUTHORITY, pCircabcUser.getUserName());
		}

		// add the circa aspect
		nodeService.addAspect(newPerson, UserModel.TYPE_CIRCA_ASPECT, pCircabcUser.getAspectAttributesInMap());

		return newPerson;
	}

	public NodeRef createUser(final CircabcUserDataBean pCircabcUser, final boolean enabled)
	{
		// create the user
		final NodeRef userNodeRef = createUser(pCircabcUser);

		// Allow guest to read circabc profile
		permissionService.setPermission(userNodeRef, CircabcConstant.GUEST_AUTHORITY, UserPermissions.PERSONINFOREAD.toString(), true);

		// set him enbled or not. If not the user can't authenticate himself.
		authenticationService.setAuthenticationEnabled(pCircabcUser.getUserName(), enabled);

		return userNodeRef;
	}

	/**
	 * Creates a new User in Alfresco with the Circabc Aspect
	 *
	 * @param user id
	 */
	public NodeRef createLdapUser(final String userId)
	{
		String authority = userId;

		//	clean user id
        if (authority.contains("@") )
        {
            authority = authority.substring(0, authority.indexOf('@') );
        }

		final CircabcUserDataBean user = new CircabcUserDataBean();
    	user.setUserName(authority);
    	final CircabcUserDataBean ldapUserDetail = ldapUserService.getLDAPUserDataByUid(authority);
    	user.copyLdapProperties(ldapUserDetail);


    	// initialize other properties.
    	user.setCompanyId("");
    	user.setURL("");
    	user.setVisibility(Boolean.FALSE);
    	user.setGlobalNotification(Boolean.TRUE);
    	user.setLastLoginTime(new Date());
    	user.setLastModificationDetailsTime(new Date());
    	user.setCreationDate(new Date());

    	final NodeRef circaBC = managementService.getCircabcNodeRef();
    	final NodeRef circabcUserHome = getProfileManagerServiceFactory().getProfileManagerService(circaBC).getCircaHome(circaBC);
    	user.setHomeSpaceNodeRef(managementService.getGuestHomeNodeRef());

    	return createUser(user, true);
	}


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
	public void updateUser(final CircabcUserDataBean pCircabcUser, final boolean pNonAspectProperties)
	{
			// Get the node to represent the Person
			final NodeRef person = this.personService.getPerson(pCircabcUser.getUserName());

			// update the properties
			Map<QName, Serializable> prop = null;
			if (pNonAspectProperties)
			{
				//modify only if modify time is before time from batch
				final Date lastModify= (Date)nodeService.getProperty(person, UserModel.PROP_LAST_MODIFICATION_DETAILS_TIME);
				if(lastModify.before(pCircabcUser.getLastModificationDetailsTime())){
					prop = nodeService.getProperties(person);
					prop.putAll(pCircabcUser.getAttributesAsMap());
					nodeService.setProperties(person, prop);
				}
				else{
					if(logger.isDebugEnabled())
						logger.debug(pCircabcUser.getUserName()
								+" not updated. Last modify: "
								+lastModify
								+ " . (date in import file("
								+pCircabcUser.getLastModificationDetailsTime()+")");
				}
			} else
			{
				prop = pCircabcUser.getAllAttributesInMap();
				nodeService.setProperty(person, UserModel.PROP_LAST_MODIFICATION_DETAILS_TIME, new Date());
				nodeService.setProperties(person, prop);
			}

	}

	/**
	 * Update all the data of a Circabc user
	 *
	 * @param pCircabcUser
	 *            Circabc user
	 */
	public void updateUser(final CircabcUserDataBean pCircabcUser)
	{
		this.updateUser(pCircabcUser, false);

	}

	/**
	 * It deletes a Circabc user on alfresco
	 *
	 * @param pUserName
	 *            the user to delete
	 * @throws AuthenticationException
	 *             any error
	 */
	public void deleteUser(final String pUserName) throws AuthenticationException
	{//TODO check if consistent
	       	try
        	{
        		authenticationService.deleteAuthentication(pUserName);
        	}
        	catch (final AuthenticationException authErr)
        	{
        		if(logger.isErrorEnabled())
        			logger.error(authErr);
        	}
        	this.personService.deletePerson(pUserName);
	}


	@Deprecated // see eu.cec.digit.circabc.business.helper.UserManager
	public boolean isEmailExists(final String email, final boolean excludeDataInTheCurrentTransaction)
	{
		if(email == null || email.length() < 1)
    	{
    		return false;
    	}

        boolean emailExists = false;

        final SearchParameters sp = new SearchParameters();
        sp.setLanguage(SearchService.LANGUAGE_LUCENE);
        sp.setQuery("TYPE:\\{http\\://www.alfresco.org/model/content/1.0\\}person +@cm\\:email:\""
                + email + "\"");

        if(peopleRef == null) {
			peopleRef = personService.getPeopleContainer();
		}

        sp.addStore(peopleRef.getStoreRef());
        sp.excludeDataInTheCurrentTransaction(excludeDataInTheCurrentTransaction);

        ResultSet rs = null;
        try
        {
            rs = searchService.query(sp);

            if (rs.length() != 0)
            {
                emailExists = true;
            }
        }
        catch(final Exception e)
        {
        	if(logger.isErrorEnabled()) {
        		logger.error(e);
        	}
        }
        finally
        {
            if (rs != null)
            {
                try
                {
                	rs.close();
                }
                catch(final Exception e2)
                {
                	if(logger.isWarnEnabled())
                	{
                		logger.warn("ResultSet Close Exception", e2);
                	}
                }
            }
        }

        return emailExists;
	}

	@Deprecated // not longer a production feature
	public int getAccountExpirationDays()
	{
		final String numberStr = CircabcConfiguration.getProperty(CircabcConfiguration.EXPIRATION_TIME_PROPERTIES);

		int response = DEFAULT_EXIPRATION_NUMBER_OF_DAYS;

		if(numberStr != null && numberStr.length() > 1)
		{
			try
			{
				response = Integer.parseInt(numberStr);
			}

			catch(final NumberFormatException ex)
			{
				if(logger.isErrorEnabled())
					logger.error("The property file "
										+ CircabcConfiguration.DEFAULT_PROPERTY_FILE
										+ " is corrupted. The value of the key "
										+ CircabcConfiguration.EXPIRATION_TIME_PROPERTIES
										+ " must be a valid integer and not "
										+ numberStr
										+ "\nPlease correct the problem and restart the server. For instance the value used by default is "
										+ DEFAULT_EXIPRATION_NUMBER_OF_DAYS);
			}
		}
		else
		{
			if(logger.isErrorEnabled())
				logger.error("The property file "
						+ CircabcConfiguration.DEFAULT_PROPERTY_FILE
						+ " not complete. The value of the key "
						+ CircabcConfiguration.EXPIRATION_TIME_PROPERTIES
						+ " must be set and must be a valid integer and not "
						+ "\nPlease correct the problem and restart the server. For instance the value used by default is "
						+ DEFAULT_EXIPRATION_NUMBER_OF_DAYS);
		}

		return response;
	}

	public void setAuthenticationService(final MutableAuthenticationService authenticationService)
	{
		this.authenticationService = authenticationService;
	}

	public void setNodeService(final NodeService nodeService)
	{
		this.nodeService = nodeService;
	}

	public void setPermissionService(final PermissionService permissionService)
	{
		this.permissionService = permissionService;
	}

	public void setPersonService(final PersonService personService)
	{
		this.personService = personService;
	}

	/**
	 * @param ManagementService the ManagementService to set
	 */
	public final void setManagementService(final ManagementService managementService)
	{
		this.managementService = managementService;
	}

	/**
	 * @param authorityService the authorityService to set
	 */
	public final void setAuthorityService(final AuthorityService authorityService)
	{
		this.authorityService = authorityService;
	}


	/**
	 * @return the searchService
	 */
	public SearchService getSearchService()
	{
		return searchService;
	}

	/**
	 * @param searchService the searchService to set
	 */
	public void setSearchService(final SearchService searchService)
	{
		this.searchService = searchService;
	}


	/**
	 * @param configurableService the configurableService to set
	 */
	public void setConfigurableService(final ConfigurableService configurableService)
	{
		this.configurableService = configurableService;
	}


	/**
	 * @param namespaceService the namespaceService to set
	 */
	public void setNamespaceService(final NamespaceService namespaceService)
	{
		this.namespaceService = namespaceService;
	}

	/**
	 * @param LdapUserService the ldapService to set
	 */
	public void setLdapService(final LdapUserService ldapService)
	{
		this.ldapUserService = ldapService;
	}


	public void setPassword(final String pUserName, final char[] pNewPassword) throws AuthenticationException {
		this.authenticationService.setAuthentication(pUserName, pNewPassword);

	}

	public String getUserEmail(final String pUserName) {
		final NodeRef nodeRef = getPerson(pUserName);
		return DefaultTypeConverter.INSTANCE.convert(String.class, nodeService.getProperty(nodeRef, ContentModel.PROP_EMAIL));
	}


	public String getUserDomain(final String pUserName) {
		final NodeRef nodeRef = getPerson(pUserName);
		final Serializable domain = nodeService.getProperty(nodeRef, UserModel.PROP_DOMAIN);
		return (domain == null) ? null : domain.toString();
	}


	public String getUserFullName(final String pUserName)
	{
		final NodeRef nodeRef = getPerson(pUserName);
		final String firstName = DefaultTypeConverter.INSTANCE.convert(String.class, nodeService.getProperty(nodeRef, ContentModel.PROP_FIRSTNAME));
		final String lastName = DefaultTypeConverter.INSTANCE.convert(String.class, nodeService.getProperty(nodeRef, ContentModel.PROP_LASTNAME));
		return ((firstName == null) ? "" : firstName + " ") + ((lastName == null) ? "" : lastName);
	}

	public NodeRef getPerson(final String pUserName)
	{
		return personService.getPerson(pUserName);
	}

	public String getUserByEmail(final String email)
	{
		 String userName = null;

        final SearchParameters sp = new SearchParameters();
        sp.setLanguage(SearchService.LANGUAGE_LUCENE);
        sp.setQuery("TYPE:\\{http\\://www.alfresco.org/model/content/1.0\\}person +@cm\\:email:\"" + email + "\"");

         sp.addStore(personService.getPeopleContainer().getStoreRef());
         sp.excludeDataInTheCurrentTransaction(false);

         ResultSet rs = null;
         try
         {
             rs = searchService.query(sp);

             if (rs.length() != 0)
             {
                 final NodeRef person =  rs.getNodeRef(0);
                 userName = (String) nodeService.getProperty(person, ContentModel.PROP_USERNAME);
             }
         }
         finally
         {
             if (rs != null)
             {
                try
                {
                	 rs.close();
                }
                 catch(final Exception e2)
                 {
                 	if(logger.isWarnEnabled())
                 	{
                 		logger.warn("ResultSet Close Exception", e2);
                 	}
                 }
             }
         }

         return userName;
	}

	public List<UserCategoryMembershipRecord> getCategories(final String pUserName) {
		final List<UserCategoryMembershipRecord> result = new ArrayList<UserCategoryMembershipRecord>();
		final Set<String> authorities=  authorityService.getAuthoritiesForUser( pUserName);
		for (final String authority : authorities) {
			if(authority.contains(MASTER_GROUP))
			{
				// ignore CIRCABC root
				if (authority.startsWith(GROUP_CIRCA_BC_MASTER_GROUP))
				{
					continue;
				}
				final String searchItem = authority.replace(GROUP_PREFIX, "");
				final NodeRef categoNodeRef = getCategoryNoderef(searchItem);
				if (categoNodeRef != null)
				{
					String category  = (String) nodeService.getProperty(categoNodeRef, ContentModel.PROP_NAME);
					final String categoryTitle  = (String) nodeService.getProperty(categoNodeRef, ContentModel.PROP_TITLE);
					if (categoryTitle != null && categoryTitle.length() > 0 )
					{
						category = categoryTitle;
					}
					final String profile = getProfileManagerServiceFactory().getCategoryProfileManagerService().getPersonProfile(categoNodeRef, pUserName);
					if ((profile != null)  && !profile.equals(CircabcRootProfileManagerService.ALL_CIRCA_USERS_PROFILE_NAME))
					{
						result.add(new UserCategoryMembershipRecord( category ,profile, categoNodeRef.getId().toString()) );
					}
				}
			}
		}

		Collections.sort(result ,UserCategoryMembershipRecordComparator.getInstance());
		return result;
	}

	private NodeRef getCategoryNoderef(final String searchItem) {

		NodeRef result = null;
        final SearchParameters sp = new SearchParameters();
        sp.setLanguage(SearchService.LANGUAGE_LUCENE);
        sp.addStore(Repository.getStoreRef());
        sp.setQuery(CI_CIRCA_CATEGORY_MASTER_GROUP + "\"" + searchItem + "\"");

        ResultSet rs = null;
        try
        {
            rs = searchService.query(sp);
            if (rs.length() != 0)
            {
            	result = rs.getRow(0).getNodeRef();
            }
        }
        catch(final Exception e)
        {
            if (logger.isErrorEnabled())
            {
            	logger.error("Error when serching for  interest group "  + searchItem ,e);
            }
        }
        finally
        {
            if (rs != null)
            {
                try
                {
                	rs.close();
                }
                catch(final Exception e2)
                {
                	if(logger.isWarnEnabled())
                	{
                		logger.warn("ResultSet Close Exception", e2);
                	}
                }
            }
        }


		return result;
	}
	public List<UserIGMembershipRecord> getInterestGroups(final String pUserName)
	{
		final List<UserIGMembershipRecord> result = new ArrayList<UserIGMembershipRecord>();

		AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>() {
			public Object doWork() {

		final Set<String> authorities=  authorityService.getAuthoritiesForUser( pUserName);

		final IGRootProfileManagerService rootProfileManagerService = getProfileManagerServiceFactory().getIGRootProfileManagerService();

		for (final String authority : authorities)
		{
			if (authority.contains(MASTER_GROUP))
			{
				// ignore CIRCABC root
				if (authority.startsWith(GROUP_CIRCA_BC_MASTER_GROUP))
				{
					continue;
				}
				final String searchItem = authority.replace(GROUP_PREFIX, "");
				final NodeRef igRootNodeRef = getIGNoderef(searchItem);
				if (igRootNodeRef != null)
				{

					final NodeRef categoNodeRef = nodeService.getPrimaryParent(igRootNodeRef).getParentRef();
					final String category  = (String) nodeService.getProperty(categoNodeRef, ContentModel.PROP_NAME);
					final String categoryTitle  = (String) nodeService.getProperty(categoNodeRef, ContentModel.PROP_TITLE);
					final String interesGroup = (String) nodeService.getProperty(igRootNodeRef, ContentModel.PROP_NAME);
					final String interesGroupTitle = (String) nodeService.getProperty(igRootNodeRef, ContentModel.PROP_TITLE);
					final String profile = rootProfileManagerService.getPersonProfile(igRootNodeRef, pUserName);
					final MLText profileTitle = rootProfileManagerService.getProfile(igRootNodeRef, profile).getTitle();

					result.add(new UserIGMembershipRecord(igRootNodeRef.getId(),interesGroup, categoNodeRef.getId(), category, profile, categoryTitle, interesGroupTitle, profileTitle == null ? null : profileTitle.getDefaultValue()));
				}
			}
		}
		return null;

			}
		}, AuthenticationUtil.getSystemUserName());
		Collections.sort(result ,UserIGMembershipRecordComparator.getInstance());
		return result;

	}
	
	public List<UserIGMembershipRecord> getInterestGroups(final String pUserName, List<NodeRef> categories)
	{
		return getFileteredIGs(pUserName, categories);
	}

	private List<UserIGMembershipRecord> getFileteredIGs(final String pUserName, final List<NodeRef> categories)
	{
		final List<UserIGMembershipRecord> result = new ArrayList<UserIGMembershipRecord>();

		AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>() {
			public Object doWork() {

		final Set<String> authorities = authorityService.getAuthoritiesForUser( pUserName);

		final IGRootProfileManagerService rootProfileManagerService = getProfileManagerServiceFactory().getIGRootProfileManagerService();

		for (final String authority : authorities)
		{
			if (authority.contains(MASTER_GROUP) || authority.contains(MASTER_GROUP_REVERSE))
			{
				// ignore CIRCABC root
				if (authority.startsWith(GROUP_CIRCA_BC_MASTER_GROUP))
				{
					continue;
				}
				final String searchItem = authority.replace(GROUP_PREFIX, "");
				final NodeRef igRootNodeRef = getIGNoderef(searchItem);
				if (igRootNodeRef != null)
				{
					final NodeRef categoNodeRef = nodeService.getPrimaryParent(igRootNodeRef).getParentRef();
					boolean isFilterMatch = true;
					
					if(categories != null && !categories.isEmpty() && !categories.contains(categoNodeRef))
					{
						// a category filter was applied but this category does not match
						isFilterMatch = false;
					}
					
					if(isFilterMatch)
					{
						
						final String category  = upFirtLetterInString((String) nodeService.getProperty(categoNodeRef, ContentModel.PROP_NAME));
						final String categoryTitle  = upFirtLetterInString((String) nodeService.getProperty(categoNodeRef, ContentModel.PROP_TITLE));
						final String interesGroup = upFirtLetterInString((String) nodeService.getProperty(igRootNodeRef, ContentModel.PROP_NAME));
						final String interesGroupTitle  = upFirtLetterInString((String) nodeService.getProperty(igRootNodeRef, ContentModel.PROP_TITLE));
						final String profile = rootProfileManagerService.getPersonProfile(igRootNodeRef, pUserName);
						final MLText profileTitle = rootProfileManagerService.getProfile(igRootNodeRef, profile).getTitle();
	
						result.add(new UserIGMembershipRecord(igRootNodeRef.getId(),interesGroup, categoNodeRef.getId(), category, profile, categoryTitle, interesGroupTitle, profileTitle == null ? null : profileTitle.getDefaultValue()));
					}
				}
			}
		}
		return null;

			}
		}, AuthenticationUtil.getSystemUserName());
		Collections.sort(result ,UserIGMembershipRecordComparator.getInstance());
		return result;

	}
	
	private String upFirtLetterInString(String myString)
	{
		String result = "";
		if(myString != null && myString.length()>0)
		{
		result = myString.substring(0, 1).toUpperCase() + myString.substring(1);
		}
		return result;
	}
	
	private NodeRef getIGNoderef(final String searchItem) {

		Locale previousLocal = I18NUtil.getLocale();
		I18NUtil.setLocale(new Locale("en"));
		NodeRef result = null;
        final SearchParameters sp = new SearchParameters();
        sp.setLanguage(SearchService.LANGUAGE_LUCENE);
        sp.addStore(new StoreRef(StoreRef.PROTOCOL_WORKSPACE, "SpacesStore"));
        sp.setQuery(CI_CIRCA_IGROOT_MASTER_GROUP + "\"" + searchItem + "\"");

        ResultSet rs = null;
        try
        {
            rs = searchService.query(sp);
            if (rs.length() != 0)
            {
            	result = rs.getRow(0).getNodeRef();
            }
        }
        catch(final Exception e)
        {

            if (logger.isErrorEnabled())
            {
            	logger.error("Error when serching for  interest group "  + searchItem ,e);
            }

        }
        finally
        {
        	I18NUtil.setLocale(previousLocal);
            if (rs != null)
            {
                try
                {
                	rs.close();
                }
                catch(final Exception e2)
                {
                	if(logger.isWarnEnabled())
                	{
                		logger.warn("ResultSet Close Exception", e2);
                	}
                }
            }
        }


		return result;
	}

	public List<NodeRef> getEventRootNodes(final String pUserName)
	{
		final List<NodeRef> result = new ArrayList<NodeRef>();
		final Set<String> authorities=  authorityService.getAuthoritiesForUser( pUserName);
		for (String authority : authorities) {
			if ( authority.contains(MASTER_GROUP))
			{
				// ignore CIRCABC root
				if (authority.startsWith(GROUP_CIRCA_BC_MASTER_GROUP))
				{
					continue;
				}
				final String searchItem = authority.replace(GROUP_PREFIX, "");
				final NodeRef igRootNodeRef = getIGNoderef(searchItem);
				if (igRootNodeRef != null)
				{
					final NodeRef eventRoot =  getChildByAspect(igRootNodeRef, CircabcModel.ASPECT_EVENT_ROOT);
					if (eventRoot != null)
					{
						result.add(eventRoot);
					}
				}
			}
		}
		return result;

	}

	/**
	 * @param nodeRef
	 * @param service
	 * @param permission
	 * @return
	 */
	
	public Set<String> getUsersWithPermission(final NodeRef nodeRef, final String permission)
	{
		final Set<String> users = new HashSet<String>();

		final Set<AccessPermission> accessPermissions = permissionService.getAllSetPermissions(nodeRef);

		String authority;
		for(final AccessPermission accessPermission: accessPermissions)
		{
			if(accessPermission.getPermission().equals(permission)
					&& accessPermission.getAccessStatus().equals(AccessStatus.ALLOWED))
			{
				authority = accessPermission.getAuthority();

				final AuthorityType authType = AuthorityType.getAuthorityType(authority);

				if(authType.equals(AuthorityType.USER))
				{
					users.add(authority);
				}
				else if(authType.equals(AuthorityType.GROUP) && authority.equals(CircabcRootProfileManagerService.ALL_CIRCA_USERS_AUTHORITY) == false)
				{
					if (authorityService.authorityExists(authority))
					{
						users.addAll(authorityService.getContainedAuthorities(AuthorityType.USER, authority, false));
					}
					else
					{
						if (logger.isErrorEnabled())
						{
							logger.error("Authority does bot exists: " + authority  );
						}
					}
				}
			}
		}

		return users;
	}

	public boolean isUserOnline(final String user) {
		return ticketComponent.getUsersWithTickets(true).contains(user);
	}
	
	public Set<String> getAllOnlineUsers() {
		return ticketComponent.getUsersWithTickets(true);
	}
	
	public Set<String> getOnlineUsers(final NodeRef igNodeRef) {
		final Set<String> onlineUsers = getAllOnlineUsers();
		final Set<String> igOnlineUser = getProfileManagerServiceFactory().getProfileManagerService(igNodeRef).getInvitedUsers(igNodeRef);
		onlineUsers.retainAll(igOnlineUser);
		return onlineUsers;
	}

	private NodeRef getChildByAspect(final NodeRef parent, final QName aspect)
	{
		final List<ChildAssociationRef> assocs = nodeService.getChildAssocs(parent);
		NodeRef ref;
		for(final ChildAssociationRef assoc : assocs)
	    {
			ref = assoc.getChildRef();
			if(nodeService.hasAspect(ref, aspect))
			{
				return ref;
			}
		}

		return null;
	}

	/**
	 * @return the profileManagerServiceFactory
	 */
	protected final ProfileManagerServiceFactory getProfileManagerServiceFactory()
	{
		return profileManagerServiceFactory;
	}

	/**
	 * @param profileManagerServiceFactory the profileManagerServiceFactory to set
	 */
	public final void setProfileManagerServiceFactory(final ProfileManagerServiceFactory profileManagerServiceFactory)
	{
		this.profileManagerServiceFactory = profileManagerServiceFactory;
	}

	public String getUserNameByEmail(final String email)
	{
		String result = null ;
        final SearchParameters sp = new SearchParameters();
        sp.setLanguage(SearchService.LANGUAGE_LUCENE);
        sp.addStore(new StoreRef(StoreRef.PROTOCOL_WORKSPACE, "SpacesStore"));

        sp.setQuery("TYPE:\"{http://www.alfresco.org/model/content/1.0}person\" AND @cm\\:email:" +  "\"" + email + "\"");

        ResultSet rs = null;
        try
        {
            rs = searchService.query(sp);
            if (rs.length() != 0)
            {
            	result = nodeService.getProperty(rs.getRow(0).getNodeRef(), PROP_USERNAME).toString();
            }
        }

        catch(final Exception e)
        {

            if (logger.isErrorEnabled())
            {
            	logger.error("Error when serching for user with email:" + email ,e);
            }

        }
        finally
        {
            if (rs != null)
            {
                try
                {
                	rs.close();
                }
                catch(final Exception e2)
                {
                	if(logger.isWarnEnabled())
                	{
                		logger.warn("ResultSet Close Exception", e2);
                	}
                }
            }
        }


		return result;
	}

	public CircabcUserDataBean getCircabcUserDataBean(final String userName)
	{
		//Get the node to represent the Person
		final NodeRef person = this.personService.getPerson(userName);
		final Map<QName, Serializable> properties = nodeService.getProperties(person);
		return CircabcUserDataFillerUtil.getCircabcUserDataBean(properties);
	}

	public CircabcUserDataBean getLDAPUserDataByUid(final String pLdapUserID) {
		return ldapUserService.getLDAPUserDataByUid(pLdapUserID);
	}

	public List<String> getLDAPUserIDByMail(final String mail) {
		return ldapUserService.getLDAPUserIDByMail(mail);
	}

	public List<String> getLDAPUserIDByMailDomain(final String mail, final String domain) {
		return ldapUserService.getLDAPUserIDByMailDomain(mail, domain);
	}

	public List<SearchResultRecord> getUsersByMailDomain(final String mail, final String domain) {
		return ldapUserService.getUsersByMailDomain(mail, domain);
	}

	public List<SearchResultRecord> getUsersByDomainFirstNameLastNameEmail(final String pDomain, final String pCriteria) {
		return ldapUserService.getUsersByDomainFirstNameLastNameEmail(pDomain, pCriteria);
	}

	public List<String> getUsersByDomainFirstNameLastNameWithoutEmail(final String pDomain, final String pCriteria) {
		return ldapUserService.getUsersByDomainFirstNameLastNameWithoutEmail(pDomain, pCriteria);
	}

	public List<String> getLDAPUserIDByIdMonikerEmailCn(final String uid, final String moniker, final String email, final String cn, final boolean conjunction){
		return ldapUserService.getLDAPUserIDByIdMonikerEmailCn(uid, moniker, email, cn, conjunction);
	}

	public LdapUserService getLdapUserService() {
		return ldapUserService;
	}

	public void setLdapUserService(final LdapUserService ldapUserService) {
		this.ldapUserService = ldapUserService;
	}

	public void setTicketComponent(final TicketComponent ticketComponent) {
		this.ticketComponent = ticketComponent;
	}

	public void copyUser(final String oldUserName , final String newUserName , boolean deleteOldUser,boolean copyOwnership,boolean copyMembership)
	{
		//final boolean oldAuthorityExists = authorityService.authorityExists(oldUserName);
		final boolean oldPersonExists = personService.personExists(oldUserName);
		if (! oldPersonExists)
		{
			throw new IllegalStateException("Person does not exists : "+ oldUserName);
		}
		//final boolean newAuthorityExists = authorityService.authorityExists(newUserName);
		final boolean newPersonExists = personService.personExists(newUserName);
		
		if (!newPersonExists)
		{
			CircabcUserDataBean circabcUserDataBean = getCircabcUserDataBean(oldUserName);
			circabcUserDataBean.setUserName(newUserName);
			circabcUserDataBean = getLDAPUserDataByUid(newUserName);
			createUser(circabcUserDataBean, true);
		}
		
		copyGroupMebership(oldUserName, newUserName); 
		
		copyOwnership(oldUserName, newUserName);
		
		if (deleteOldUser)
		{
			personService.deletePerson(oldUserName);
			//authorityService.deleteAuthority(oldUserName);
			
		}
		
	}

	private void copyOwnership(String oldUserName, String newUserName)
	{
	
		String luceneQuery = null;
		luceneQuery = "@cm\\:owner:\"" + oldUserName + "\"" ;

		ResultSet resultSet = null;
		try
		{
			resultSet = executeLuceneQuery(luceneQuery);
			for (final ResultSetRow row : resultSet)
			{
				try
				{
					ownableService.setOwner(row.getNodeRef(), newUserName);
				} catch (Exception e)
				{
					if (logger.isErrorEnabled())
					{
						logger.error("Could not set owner from user : " + oldUserName + " to user :"+ newUserName +" on node " + row.getNodeRef() , e);
					}
				}
				
			}
		}
		finally
		{
			if(resultSet != null)
			{
				resultSet.close();
			}
		}
	}
	
	private ResultSet executeLuceneQuery(final String query)
	{
		final SearchParameters sp = new SearchParameters();
		sp.setLanguage(SearchService.LANGUAGE_LUCENE);
		sp.setQuery(query);
		sp.addStore(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE);
		return searchService.query(sp);
	}

	/**
	 * @param oldUserName
	 * @param newUserName
	 */
	private void copyGroupMebership(String oldUserName, String newUserName)
	{
		final Set<String> authoritiesForUser = authorityService.getAuthoritiesForUser(oldUserName);
		for (String authority : authoritiesForUser)
		{
			if (authority.equals("GROUP_EVERYONE"))
			{
				continue ;
			}
			Set<String> containedAuthorities = Collections.emptySet();
			if(authorityService.authorityExists(authority))  
			{
				containedAuthorities = authorityService.getContainedAuthorities(AuthorityType.USER, authority, true);
			}
			else
			{
				if (logger.isErrorEnabled())
				{
					logger.error("Authorities does not exists" + authority  );
				}
			}
			if (containedAuthorities.size() > 0 )
			{
				authorityService.addAuthority(authority, newUserName);
			}
		}
	}

	public Map<String, String> getEcasUserDomains()
	{
		return ldapUserService.getEcasUserDomains();
	}

	public void setOwnableService(OwnableService ownableService)
	{
		this.ownableService = ownableService;
	}

	public OwnableService getOwnableService()
	{
		return ownableService;
	}

	public boolean isUserExists(String userName)
	{
		return personService.personExists(userName);
	}
	
public void setRawPassword(String userName, String password) {
		
        SearchParameters sp = new SearchParameters();
        sp.setLanguage(SearchService.LANGUAGE_LUCENE);
        sp.setQuery("@usr\\:username:\"" + userName + "\"");
        StoreRef STOREREF_USERS = new StoreRef("user", "alfrescoUserStore");
        sp.addStore(STOREREF_USERS);
        sp.excludeDataInTheCurrentTransaction(false);

        
        ResultSet rs = null;
        try
        {
            rs = searchService.query(sp);

            for (ResultSetRow row : rs)
            {
            	NodeRef userNodeRef = null;
                userNodeRef = row.getNodeRef();
                if (nodeService.exists(userNodeRef))
                {            
                	 Map<QName, Serializable> properties = nodeService.getProperties(userNodeRef);
            	     String salt = null; // GUID.generate();
            	     properties.remove(ContentModel.PROP_SALT);
            	     properties.put(ContentModel.PROP_SALT, salt);
            	     properties.remove(ContentModel.PROP_PASSWORD);
            	     properties.put(ContentModel.PROP_PASSWORD,password );
            	     nodeService.setProperties(userNodeRef, properties);
                }
                else
                {
                	logger.error("For user " + userName + " node reference does not exists" +  userNodeRef.toString());
                }
            }
        }
        finally
        {
            if (rs != null)
            {
                rs.close();
            }
        }
		
		
		
		
	}

	@Override
	public void deleteUsers(List<String> userNames) {
		for (String userName : userNames) {
			personService.deletePerson(userName);
		}
		
	}
}
