/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.profile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.web.bean.repository.MapNode;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.ui.common.SortableSelectItem;
import org.alfresco.web.ui.common.Utils;
import org.alfresco.web.ui.repo.WebResources;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.profile.Profile;
import eu.cec.digit.circabc.service.profile.ProfileManagerService;
import eu.cec.digit.circabc.web.PermissionUtils;
import eu.cec.digit.circabc.web.ProfileUtils;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * Bean that back the manage profiles dialog.
 *
 * @author Yanick Pignot, Markus K�lzer
 */
public class ManageUserProfilesDialog extends BaseWaiDialog
{
	private static final long serialVersionUID = -2476739780479536405L;

	private static final String PARAM_SELECTED_PROFILE = "profileName";

	private static final String VALUE_ALL_PROFILES = "__ALL_PROFILES";
	private static final String MSG_ALL_PROFILE = "members_home_disable_profile_filter";

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ManageUserProfilesDialog.class);

	private transient PersonService personService;
	private transient AuthenticationService authenticationService;
	private transient AuthorityService authorityService;

	private String selectedProfile;
	private String newProfile;
	private String searchText;
	private List<Map> cachedUsers = Collections.<Map> emptyList();

	@Override
	public void init(final Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null)
		{
			if(parameters.containsKey(PARAM_SELECTED_PROFILE))
			{
				selectedProfile	= parameters.get(PARAM_SELECTED_PROFILE);
			}
			else
			{
				selectedProfile = VALUE_ALL_PROFILES;
			}

			if(getActionNode() == null)
			{
				throw new IllegalArgumentException("The node id is a mandatory parameter");
			}
		}
	}

	@Override
	protected String finishImpl(final FacesContext context, final String outcome) throws Exception
	{
		return outcome;
	}

	public List<SortableSelectItem> getProfiles()
	{
		final String allProfileText = translate(MSG_ALL_PROFILE);
		final List<SortableSelectItem> profiles = new ArrayList<SortableSelectItem>();

		profiles.add(new SortableSelectItem(VALUE_ALL_PROFILES, "<" + allProfileText + ">", allProfileText));
		profiles.addAll(ProfileUtils.buildMailableProfileItems(getActionNode(), logger));
		return profiles;
	}
	
	public List<SortableSelectItem> getProfileList()
	{
		final List<SortableSelectItem> profiles = new ArrayList<SortableSelectItem>();
		profiles.addAll(ProfileUtils.buildMailableProfileItems(getActionNode(), logger));
		return profiles;
	}
	
	public List<Map> getFilteredUsers()
	{
		setCachedUsers(getFilterUsers());
		return getCachedUsers();
	}
	
	public List<Map> getCachedUsers()
	{
		return cachedUsers;
	}
	public void setCachedUsers(List<Map> cu)
	{
		cachedUsers = cu;
	}
	
	/**
	 * Filters for the users by the given filter options.<br/>
	 * Use the method only if you want to apply a new filter.<br/>
	 * Otherwise you should use getCachedUsers().
	 */
	public List<Map> getFilterUsers()
	{	

		final FacesContext context = FacesContext.getCurrentInstance();
		final List<Map> personNodes = new ArrayList<Map>();

		final RetryingTransactionHelper txnHelper = Repository.getRetryingTransactionHelper(context);
		final RetryingTransactionCallback<Object> callback = new RetryingTransactionCallback<Object>()
		{
			public Object execute() throws Throwable
			{
				final Set<String> allInvited = buildInvitedSet();

				ProfileManagerService profileManagerService;

				// count the number of admin invited in the current node. If they are only one admin
				// this last can't be univited !!!
				

				final NodeRef activeNode = getActionNode().getNodeRef();
				profileManagerService = getProfileManagerServiceFactory().getProfileManagerService(activeNode);
				int currentNodeAdminCount = 0;
				for (final String authority : allInvited)
				{
					String personProfile;
					Profile profile;
					personProfile = profileManagerService.getPersonProfile(activeNode,authority);
					if (personProfile == null)
					{
						continue;
					}
					profile = profileManagerService.getProfile(activeNode, personProfile);
					if (profile.isAdmin() )
					{
						currentNodeAdminCount ++;
					}
				}


				String personProfile;
				Profile profile;
				
				if (searchText != null   )
				{
					searchText = searchText.toLowerCase().trim(); 
				}
				
				for (final String authority : allInvited)
				{
					final NodeRef nodeRef = getPersonService().getPerson(authority);
					final MapNode node = new MapNode(nodeRef);
					final String firstName = (String) node.get(ContentModel.PROP_FIRSTNAME);
					final String lastName = (String) node.get(ContentModel.PROP_LASTNAME);
					final String email = (String) node.get(ContentModel.PROP_EMAIL);
					
					if (searchText != null  &&  !searchText.isEmpty() )
					{
						
						if ( !firstName.toLowerCase().contains(searchText)  &&  !lastName.toLowerCase().contains(searchText) && !email.toLowerCase().contains(searchText) )
						{
							continue;
						}
					}
					node.put(PermissionUtils.KEY_USER_FULL_NAME, firstName + " " + lastName);
					node.put("firstName", firstName);
					node.put("lastName", lastName);
					node.put(PermissionUtils.KEY_EMAIL, email);
					node.put(PermissionUtils.KEY_AUTHORITY, authority);
					node.put(PermissionUtils.KEY_DISPLAY_NAME, authority);
					node.put(PermissionUtils.KEY_USER_NAME, getCurrentUserName(node));

					personProfile = profileManagerService.getPersonProfile(activeNode,authority);
					if (personProfile == null)
					{
						continue;
					}
					profile = profileManagerService.getProfile(activeNode, personProfile);
					node.put(PermissionUtils.KEY_USER_PROFILE, profile.getProfileDisplayName());
					node.put(PermissionUtils.KEY_ICON, WebResources.IMAGE_PERSON);
					if (profile.isImported())
					{
						node.put("canBeRemoved", Boolean.FALSE);
					}
					else if(authority.equals(getAuthenticationService().getCurrentUserName())) 
					{
						node.put("canBeRemoved", Boolean.FALSE );
					}
					else 
					{
						//Check if this is last leader
						if (currentNodeAdminCount == 1 && profile.isAdmin())
						{
							node.put("canBeRemoved", Boolean.FALSE );
						}
						else 
						{
							node.put("canBeRemoved", Boolean.TRUE );
						}
					}	
					personNodes.add(node);
				}
				return null;
			}
		};

		try
		{
			txnHelper.doInTransaction(callback, true);
		}
		catch (final InvalidNodeRefException refErr)
		{
			Utils.addErrorMessage(translate(Repository.ERROR_NODEREF, refErr.getNodeRef()));
			return Collections.<Map> emptyList();
		} catch (final Throwable err)
		{
			Utils.addErrorMessage(translate(Repository.ERROR_GENERIC, err.getMessage()), err);
			return Collections.<Map> emptyList();
		}

		return personNodes;
	}

	 public String getCurrentUserName(final MapNode node)
	 {
		 return PermissionUtils.computeUserLogin(node.getProperties());
	 }

	/**
	 * Build a set of users in the interest group. The link between the IG name
	 * and the IG_GROUP is based on the name.
	 *
	 * @return
	 */
	private Set<String> buildInvitedSet()
	{
		final FacesContext context = FacesContext.getCurrentInstance();
		final RetryingTransactionHelper txnHelper = Repository.getRetryingTransactionHelper(context);
		final RetryingTransactionCallback<Set<String>> callback = new RetryingTransactionCallback<Set<String>>()
		{
			public Set<String> execute() throws Throwable
			{
				final NodeRef nodeRef = getActionNode().getNodeRef();
				final ProfileManagerService profileManagerService = getProfileManagerServiceFactory()
						.getProfileManagerService(nodeRef);

				final String selectedProf = getSelectedProfile();
				
				Set<String> invitedUsers= Collections.emptySet();

				if(selectedProf == null || selectedProf.length() < 1 || VALUE_ALL_PROFILES.equals(selectedProf))
				{
					invitedUsers= profileManagerService.getInvitedUsersProfiles(nodeRef).keySet();
				}
				else
				{
					if(authorityService.authorityExists(selectedProf))  
					{
						invitedUsers= getAuthorityService().getContainedAuthorities(AuthorityType.USER,
								selectedProf, false);
					}
					else
					{
						if (logger.isErrorEnabled())
						{
							logger.error("Authority does not exists: " + selectedProf );
						}
					}
					
				}

				
				
				// include groups and users and also groups included in group (subgroups)
				// limit the result to users
				return invitedUsers;

			}
		};

		return txnHelper.doInTransaction(callback, true);
	}
	
	public String applyNewProfile()
	{
		try
		{
            final NodeRef IGNodeRef = getActionNode().getNodeRef();
            final ProfileManagerService profileManagerService = getProfileManagerServiceFactory().getProfileManagerService(IGNodeRef);           
            for (Map map : getCachedUsers()) {
            	MapNode node = (MapNode)map;
            	String userID = (String) node.get(PermissionUtils.KEY_AUTHORITY);
                final Profile newProfile = profileManagerService.getProfileFromGroup(IGNodeRef, this.newProfile);
                final String newProfileName = newProfile.getProfileName();
            	
//            	final String profileName = profileManagerService.getPersonProfile(IGNodeRef, userID);
//            	final Profile profile = profileManagerService.getProfileFromGroup(IGNodeRef, profileName);
            	profileManagerService.changePersonProfile(IGNodeRef, userID, newProfileName);
			}
//			final String info =MessageFormat.format("Changed profile of user {0} from {1} to {2}", new Object[]{fullName , oldUserProfile, profileName });
//			logRecord.setInfo(info ) ;			
			//profileManagerService.changePersonProfile(currentNode.getNodeRef(), userName, profileName);
		}
		catch (Throwable err)
        {
           if (logger.isErrorEnabled())
           {
               logger.error("Unexpected error:" + err.getMessage(), err);
           }

           Utils.addErrorMessage(translate(Repository.ERROR_GENERIC, err.getMessage()), err);
        }
		
		return "wai:dialog:close:wai:dialog:manageUserProfilesDialogWai";
	}


	@Override
	public String getContainerTitle()
	{
	      return translate("manage_invited_users_dialog_title", getActionNode().getName());
	}

	@Override
	public String getContainerDescription()
	{
	      return translate("manage_invited_users_dialog_description");
	}


	public String getBrowserTitle()
	{
		return translate("manage_invited_users_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("manage_invited_users_icon_tooltip");
	}

	@Override
	public String getCancelButtonLabel()
	{
	   return translate("close");
	}

	/**
	 * @return the authenticationService
	 */
	protected final AuthenticationService getAuthenticationService()
	{
		if(authenticationService == null)
		{
			authenticationService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getAuthenticationService();
		}
		return authenticationService;
	}

	/**
	 * @param authenticationService the authenticationService to set
	 */
	public final void setAuthenticationService(final AuthenticationService authenticationService)
	{
		this.authenticationService = authenticationService;
	}

	/**
	 * @return the personService
	 */
	protected final PersonService getPersonService()
	{
		if(personService == null)
		{
			personService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getPersonService();
		}
		return personService;
	}

	/**
	 * @param personService the personService to set
	 */
	public final void setPersonService(final PersonService personService)
	{
		this.personService = personService;
	}

	/**
	 * @return the authorityService
	 */
	protected final AuthorityService getAuthorityService()
	{
		if(authorityService == null)
		{
			authorityService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getAuthorityService();
		}
		return authorityService;
	}

	/**
	 * @param authorityService the authorityService to set
	 */
	public final void setAuthorityService(final AuthorityService authorityService)
	{
		this.authorityService = authorityService;
	}

	/**
	 * @return the selectedProfile
	 */
	public final String getSelectedProfile()
	{
		return selectedProfile;
	}

	/**
	 * @param selectedProfile the selectedProfile to set
	 */
	public final void setSelectedProfile(final String selectedProfile)
	{
		this.selectedProfile = selectedProfile;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	/**
	 * Change listener for the method select box
	 */
	public void updateList(final ValueChangeEvent event)
    {
		this.selectedProfile = (String) event.getNewValue();
    }
	
	
	/**
	 * Change listener for the method select box
	 */
	public void updateSearchText(final ValueChangeEvent event)
    {
		this.searchText = (String) event.getNewValue();
    }
	
	
	/**
	 * @return the newProfile
	 */
	public final String getNewProfile()
	{
		return newProfile;
	}

	/**
	 * @param newProfile the new Profile to set
	 */
	public final void setNewProfile(final String newProfile)
	{
		this.newProfile = newProfile;
	}	
	
	
	public void updateNewProfileList(final ValueChangeEvent event)
    {
		this.newProfile = (String) event.getNewValue();
    }
	
	public Boolean getDisableApplyNewProfileButton()
	{
		return selectedProfile == null || selectedProfile.endsWith(VALUE_ALL_PROFILES) || (selectedProfile.length() == 0) || getCachedUsers() == null || getCachedUsers().isEmpty();
	}



}
