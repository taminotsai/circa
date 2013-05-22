/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.wizard.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.app.Application;
import org.alfresco.web.app.context.UIContextService;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.bean.repository.User;
import org.alfresco.web.ui.common.SortableSelectItem;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.util.I18NUtil;

import eu.cec.digit.circabc.CircabcConfig;
import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.model.UserModel;
import eu.cec.digit.circabc.repo.applicant.Applicant;
import eu.cec.digit.circabc.service.cmr.security.CircabcConstant;
import eu.cec.digit.circabc.service.customisation.mail.MailTemplate;
import eu.cec.digit.circabc.service.iam.SynchronizationService;
import eu.cec.digit.circabc.service.profile.CircabcRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.Profile;
import eu.cec.digit.circabc.service.profile.ProfileException;
import eu.cec.digit.circabc.service.profile.ProfileManagerService;
import eu.cec.digit.circabc.util.CircabcUserDataBean;
import eu.cec.digit.circabc.web.PermissionUtils;
import eu.cec.digit.circabc.web.ProfileUtils;
import eu.cec.digit.circabc.web.WebClientHelper;
import eu.cec.digit.circabc.web.WebClientHelper.ExtendedURLMode;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.ui.common.component.UIGenericPicker;
import eu.cec.digit.circabc.web.wai.AbstractMailToUsersBean;

/**
 * @author Ph Dubois
 * @author Yanick Pignot
 * @author Stephane Clinckart
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * I18NUtil was moved to Spring.
 * This class seems to be developed for CircaBC
 */
public class InviteCircabcUsersWizard extends AbstractMailToUsersBean
{
    private static final long serialVersionUID = -5021216351699138205L;

    private static final Log logger = LogFactory.getLog(InviteCircabcUsersWizard.class);

    private final Class<?>[] CALLBACK_METHOD_ARGS = {int.class, String.class};
    private static final String  CALLBACK_METHOD_NAME = "#{InviteCircabcUsersWizard.pickerCallback}";


    /** I18N message strings */
    protected static final String MESSAGE_ID_INVITATION_ERROR = "invitation_process_failed";
    protected static final String USER_SPECIFIED_TWICE = "user_specified_twice";
    private static final String PROFILE_NOT_SELECTED = "profile_not_specified";
    private static final String USER_NOT_SELECTED = "user_not_selected";
    private static final String AT_LEAST_TREE_CHAR = "invite_user_3char_error";

    /** Index of the search filter index when it is not set */
    private static final int UNSET_FILTER_IDX = -1;

    private SelectItem[] domainFilters = null;

    private static final String USERNAME_REGEX_TINYMCE = "[USERNAME]";
    private static final String FIRSTNAME_REGEX = "<USER_FIRST_NAME>";
    private static final String FIRSTNAME_REGEX_TINYMCE = "[USER_FIRST_NAME]";
    private static final String LASTNAME_REGEX = "<USER_LAST_NAME>";
    private static final String LASTNAME_REGEX_TINYMCE = "[USER_LAST_NAME]";
    private static final String ECASUNAME_REGEX = "<USER_ECAS_USERNAME>";
    private static final String ECASUNAME_REGEX_TINYMCE = "[USER_ECAS_USERNAME]";
    private static final String PROFILE_REGEX = "<PROFILE>";
    private static final String PROFILE_REGEX_TINYMCE = "[PROFILE]";
    private static final String NOTIFY_YES = "yes";
    private static final String KEY_PROFILE = "profile";

	private static final String WARN_RIGHTS = "warning_space_permissions";

    private int realSearchResult = 0;

    /** dialog state */
    private String notify = NOTIFY_YES;

    /** list of user/group profile wrapper objects */
    public transient final List<UserGroupProfile> userGroupProfiles = new ArrayList<UserGroupProfile>();

    /**
     * if value is not null, the list of users doens't be pre filled.
     * If not, this user will serve to the automatic prefilled of the user list.
     **/
    private String filledUsers;

    private int filledUsersFilterIdx;

    boolean topNodeInvitation;

    private Boolean applicantPresent = null;
    private Boolean nonApplicantPresent = null;

    /** datamodel for table of profiles for users */
    public transient DataModel userProfileDataModel = null;
    
    
    private SynchronizationService synchronizationService;
    

    /**
     * Initialises the wizard
     */
    public void init(final Map<String, String> params)
    {
        super.init(params);
        notify = NOTIFY_YES;
        applicantPresent = null;
        nonApplicantPresent = null;
        userGroupProfiles.clear();

        if(params != null && (this.filledUsers = params.get("users")) != null)
        {
            // Ensure that the second call should not erase the setted data.

            // the user to pre fill if required is filled
            if(this.filledUsers != null)
            {
                final String searchFilter = params.get("filter");

                this.filledUsersFilterIdx = (searchFilter == null) ? (getFilters().length - 1) : Integer.parseInt(searchFilter);
            }
        }
         else
        {
            this.filledUsersFilterIdx = UNSET_FILTER_IDX;
        }

        final Node node = getActionNode();
        if(NavigableNodeType.CIRCABC_ROOT.isNodeFromType(node) || NavigableNodeType.CATEGORY.isNodeFromType(node)  || NavigableNodeType.CATEGORY_HEADER.isNodeFromType(node))
        {
            topNodeInvitation = true;
        }
        else
        {
            topNodeInvitation = false;
        }
    }

    /**
     * Returns the properties for current user-roles JSF DataModel
     *
     * @return JSF DataModel representing the current user-roles
     */
    public DataModel getUserProfilesDataModel() {
        if (this.userProfileDataModel == null)
        {
            this.userProfileDataModel = new ListDataModel();
        }

        this.userProfileDataModel.setWrappedData(this.userGroupProfiles);

        return this.userProfileDataModel;
    }


    /**
     * @see org.alfresco.web.bean.wizard.AbstractWizardBean#finish()
     */
    @Override
    protected String finishImpl(final FacesContext context, final String outcome) throws Exception {
        final RetryingTransactionHelper txnHelper = Repository.getRetryingTransactionHelper(context);
        final RetryingTransactionCallback<String> callback = new RetryingTransactionCallback<String>()
        {
            public String execute() throws Throwable
            {
                try
                {
                    //do the job here
                    grantUsers(context, getActionNode().getNodeRef());

                    UIContextService.getInstance(context).notifyBeans();

                    if(logger.isDebugEnabled()) {
                        logger.debug("outcome=" + outcome);
                    }
                    return outcome;
                }
                catch(final ProfileException pe)
                {
                    if(logger.isErrorEnabled()) {
                        logger.error("Profile:" + pe.getProfileName() + " Explanation:" + pe.getExplanation(), pe);
                    }
                    Utils.addErrorMessage(translate(MESSAGE_ID_INVITATION_ERROR, pe.getExplanation()), pe);
                    return null;
                }
                catch (final Throwable e)
                {
                    if(logger.isErrorEnabled()) {
                        logger.error("Invitation error", e);
                    }
                    Utils.addErrorMessage(translate(MESSAGE_ID_INVITATION_ERROR, e.getMessage()), e);
                    return null;
                }
            }

        };
        final StringBuilder info = new StringBuilder() ;
        for (UserGroupProfile profile : this.userGroupProfiles)
        {
            info.append(profile.getLabel());
            info.append(" ");
        }
        logRecord.setInfo(info.toString());
        String newOutcome =txnHelper.doInTransaction(callback, false);
        if (newOutcome != null)
        {
        	if (CircabcConfig.ENT)
        	{	
	        	if(this.getNodeService().hasAspect(getActionNode().getNodeRef(), CircabcModel.ASPECT_IGROOT))
	        	{
		        	String ecordaThemeId = synchronizationService.getEcordaThemeId(getActionNode().getNodeRef());
		        	
		        	if ( !ecordaThemeId.isEmpty())
					{
						for (final UserGroupProfile userGroupProfile : userGroupProfiles)
				        { 
							synchronizationService.grantThemeRole(userGroupProfile.authority,ecordaThemeId);
				        }
					}
	        	}
        	}
        }
        
        
        return newOutcome;
    }

    /**
     * Action handler called when the Add button is pressed to process the
     * current selection
     */
    public void addSelection(final ActionEvent event) {
    	
    	final List<String> guestsPermissions  =  Collections.unmodifiableList(Arrays.asList("LibNoAccess", "LibAccess","NwsAccess","NwsNoAccess", "SurAccess","SurNoAccess","InfAccess","InfNoAccess"));
    	final List<String> regiteredPermissions  =  Collections.unmodifiableList(Arrays.asList("LibNoAccess", "LibAccess","NwsPost","NwsAccess","NwsNoAccess","SurEncode", "SurAccess","SurNoAccess","InfAccess","InfNoAccess"));
        
        final Object picker =  event.getComponent().findComponent("picker");
        String[] results = null ;
        if  ( picker instanceof eu.cec.digit.circabc.web.ui.common.component.UIGenericPicker )
        {
        	results = ((eu.cec.digit.circabc.web.ui.common.component.UIGenericPicker)picker).getSelectedResults();
        }
        else if  (picker instanceof org.alfresco.web.ui.common.component.UIGenericPicker)
        {
        	results = ((org.alfresco.web.ui.common.component.UIGenericPicker)picker).getSelectedResults();
        }
        // test erreur

        
        if (results == null)
        {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(translate(USER_NOT_SELECTED)));
            return;
        }

        final UISelectOne profilePicker = (UISelectOne) event.getComponent()
                .findComponent("profiles");
        //	 the list of proportion of user/applicant my be change
        this.applicantPresent = null;
        this.nonApplicantPresent = null;

        final String profileGroupName = (String) profilePicker.getValue();

        if (profileGroupName == null)
        {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(translate(PROFILE_NOT_SELECTED)));
            return;
        }
        else
        {

            NodeRef rootNode = null;

            final NavigableNodeType type = getNavigator().getCurrentNodeType();

            if(type == null)
            {
                throw new IllegalStateException("Impossible to perform a Circabc invitation in an non Circabc node.");
            }

            if(type.isStrictlyUnder(NavigableNodeType.IG_ROOT))
            {
                rootNode = getNavigator().getCurrentIGRoot().getNodeRef();
            }
            else
            {
                rootNode = getNavigator().getCurrentNode().getNodeRef();
            }

            final ProfileManagerService profileManagerService = getProfileManagerServiceFactory().getProfileManagerService(rootNode);

            String profileDisplayName;

            final Profile profile = profileManagerService.getProfileFromGroup(rootNode, profileGroupName);

            if(profile != null)
            {
                profileDisplayName = profile.getProfileDisplayName();
            }
            else
            {
                profileDisplayName = profileGroupName;
            }

            String authority;
            String userName;
            boolean foundExisting = false;
            UserGroupProfile wrapper;
            StringBuilder label;
            AuthorityType authType;
            CircabcUserDataBean user;
            CircabcUserDataBean ldapUserDetail;
            NodeRef ref;
            String firstName;
            String lastName;

            // invite all selected users
            for (int i = 0; i < results.length; i++)
            {
                authority = results[i];
                userName = "" ;

                // only add if authority not already present in the list
                // with same CircaRole
                foundExisting = false;
                for (int n = 0; n < this.userGroupProfiles.size(); n++)
                {
                    wrapper = this.userGroupProfiles.get(n);
                    if (authority.equals(wrapper.getAuthority()))
                    {
                        foundExisting = true;
                        break;
                    }
                }

                // if found existing then user has to
                if (foundExisting == false)
                {
                    label = new StringBuilder(64);

                    // build a display label showing the user and their profile for the space
                    authType = AuthorityType.getAuthorityType(authority);

                    if (authType == AuthorityType.GUEST || authType == AuthorityType.USER)
                    {

                        //clean user id
                        if (authority.contains("@") )
                        {
                            authority = authority.substring(0, authority.indexOf('@') );
                        }
                        userName = authority ;


                        if (!getPersonService().personExists(userName))
                        {
                            user = new CircabcUserDataBean();
                            user.setUserName(userName);
                            user.setHomeSpaceNodeRef(getManagementService().getGuestHomeNodeRef());
                            ldapUserDetail = getUserService().getLDAPUserDataByUid(authority);
                            user.copyLdapProperties(ldapUserDetail);
                            getUserService().createUser(user, true);
                        }

                        // found a User authority
                        ref = getPersonService().getPerson(userName);
                        firstName = (String) getNodeService()
                                .getProperty(ref, ContentModel.PROP_FIRSTNAME);
                        lastName = (String) getNodeService()
                                .getProperty(ref, ContentModel.PROP_LASTNAME);

                        label.append(firstName)
                                .append(" ")
                                .append(lastName != null ? lastName : "")
                                .append(" (")
                                //.append(Application.getMessage(FacesContext.getCurrentInstance(), profile))
                                .append(profileDisplayName)
                                .append(")");
                    }
                    else
                    {
                        userName = authority ;

                        // get the the name of the group
                        label.append(profileManagerService.getProfileFromGroup(rootNode, authority).getProfileDisplayName())
                                .append(" (")
                                .append(profileDisplayName)
                                .append(")");
                    }
                    if(authority.equalsIgnoreCase("guest") &&  !guestsPermissions.contains(profileGroupName))
                    {
                    	Utils.addStatusMessage(FacesMessage.SEVERITY_ERROR, translate(WARN_RIGHTS, userName, profileGroupName));
                    	continue;
                    }
                	
                    if(authority.equalsIgnoreCase(CircabcRootProfileManagerService.ALL_CIRCA_USERS_AUTHORITY) && !regiteredPermissions.contains(profileGroupName))
                    {
                    	Utils.addStatusMessage(FacesMessage.SEVERITY_ERROR, translate(WARN_RIGHTS, userName, profileGroupName));
                    	continue;
                    }
                    this.userGroupProfiles.add(new UserGroupProfile(
                            userName, profileGroupName, label.toString()));
                } else
                // foundExisting = true
                {
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(translate(USER_SPECIFIED_TWICE)));
                }
            } // end for each selected user
        }
    }

    /**
     * Action handler called when the Remove button is pressed to remove a
     * user+Circarole
     */
    public void removeSelection(final ActionEvent event) {
        final UserGroupProfile wrapper = (UserGroupProfile) this.userProfileDataModel.getRowData();
        if (wrapper != null)
        {
            this.userGroupProfiles.remove(wrapper);
        }
        //	 the list of proportion of user/applicant my be change
        this.applicantPresent = null;
        this.nonApplicantPresent = null;
    }

    /**
     * Build a set of elements in the interest group. The link between the IG
     * name and the IG_GROUP is based on the name.
     *
     * @return
     */
    protected List<String> buildInvitedSet()
    {
        List<String> res = null;
        final NodeRef nodeRef = new NodeRef(Repository.getStoreRef(), getNavigator().getCurrentNodeId());
        final ProfileManagerService profileManagerService = getProfileManagerServiceFactory() .getProfileManagerService(nodeRef);

        // current node is supposed to be the root of an IG
        if (profileManagerService != null)
        {
            final String nonPrefixedName = profileManagerService
                    .getInvitedUsersGroupName(nodeRef);

            // the group of users has the same name
            final String fullGroupName = getAuthorityService().getName(
                    AuthorityType.GROUP, nonPrefixedName);
            // include groups and users and also groups included in group (subgroups)
            // limit the result to users
            res = new ArrayList<String>();
            
            if (getAuthorityService().authorityExists(fullGroupName))
			{
            	res.addAll(getAuthorityService().getContainedAuthorities(AuthorityType.USER, fullGroupName, false));
			}
			else
			{
				if (logger.isErrorEnabled())
				{
					logger.error("Authority does bot exists: " + fullGroupName);
				}
			}
            
        }

        if(res == null)
        {
            res = new ArrayList<String>(2);
        }

        res.add(CircabcConstant.GUEST_AUTHORITY);
        res.add("admin");

        return res;
    }

    /**
     * Query callback method executed by the Generic Picker component. This
     * method is part of the contract to the Generic Picker, it is up to the
     * backing bean to execute whatever query is appropriate and return the
     * results.
     *
     * @param filterIndex
     *            Index of the filter drop-down selection
     * @param contains
     *            Text from the contains textbox
     * @return An array of SelectItem objects containing the results to display
     *         in the picker.
     */
    public SelectItem[] pickerCallback(final int filterIndex, final String contains) {
        final FacesContext fc = FacesContext.getCurrentInstance();
        final RetryingTransactionHelper txnHelper = Repository.getRetryingTransactionHelper(fc);
        final RetryingTransactionCallback<List<SortableSelectItem>> callback = new RetryingTransactionCallback<List<SortableSelectItem>>()
        {
            @SuppressWarnings("unchecked")
            public List<SortableSelectItem> execute() throws Throwable
            {
                List<SortableSelectItem> items = null;

                final String filterValue = getFilters()[filterIndex].getDescription() ;

                if (PermissionUtils.FILTER_VALUE_APPLICANT.equals(filterValue))
                {
                    final NodeRef currentNodeRef = getActionNode().getNodeRef();

                    final ProfileManagerService profileManagerService = getProfileManagerServiceFactory()
                            .getProfileManagerService(currentNodeRef);

                    final Map<String, Applicant> applicants = profileManagerService.getApplicantUsers(profileManagerService
                            .getCurrentAspectRoot(currentNodeRef));

                    final List<SortableSelectItem> itemsAsList = new ArrayList<SortableSelectItem>();

                    Applicant app;
                    String username;
                    String firstName;
                    String lastName;
                    String login;
                    for (final Map.Entry<String, Applicant> entry : applicants.entrySet())
                    {
                        app = entry.getValue();
                        username = app.getUserName();
                        firstName = app.getFirstName();
                        lastName = app.getLastName();
                        login = PermissionUtils.computeUserLogin(username, getPersonService(), getNodeService());
                        if (contains == null || contains.trim().length() < 1 || contains.trim().equals("*")
                                || username.contains(contains) || firstName.contains(contains) || lastName.contains(contains))
                        {
                            itemsAsList.add(new SortableSelectItem(username, firstName + " " + lastName + " (" + login + ")", lastName));
                        }
                    }

                    items = itemsAsList;
                }
                else
                {
                    if(contains == null || contains.trim().length() < PermissionUtils.MIN_CHAR_ALLOWED_FOR_QUERY)
                    {
                        if(logger.isDebugEnabled()) {
                            logger.debug("Invalid params for query:" + contains);
                        }
                        Utils.addErrorMessage(translate(AT_LEAST_TREE_CHAR, PermissionUtils.MIN_CHAR_ALLOWED_FOR_QUERY));
                        return null;
                    }

                    // build the set of users or groups who are already invited
                    final List<String> alreadyInvitedSet = buildInvitedSet();
                    items = executeAndFillList(filterValue, contains, alreadyInvitedSet);
                }

                if (items != null)
                {
                    if (items.size() > 1)
                    {
                        Collections.sort(items);
                    }
                }
                else
                {
                    items = Collections.<SortableSelectItem> emptyList();
                }

                return items;
            }

        };
        List<SortableSelectItem> result = null;
        try
        {
            result = txnHelper.doInTransaction(callback, false, true);

        } catch (final Exception e)
        {
            if (logger.isErrorEnabled())
            {
                logger.error("Error during query execution." , e );
            }
            Utils.addErrorMessage("Error during query execution." , e);
        }
        if (result == null)
        {
            result = Collections.<SortableSelectItem> emptyList();
        }
        return result.toArray(new SelectItem[result.size()]);
    }

    @Override
    public List<SelectItem> getEmailTemplates()
    {
        if(topNodeInvitation)
        {
            return Collections.<SelectItem>emptyList();
        }
        else
        {
            return super.getEmailTemplates();
        }
    }

    @Override
    public String getBuildTextMessage()
    {
    	 if(topNodeInvitation)
         {
    		 /* workaround due to the lack of template for invitation under circabc or category */

    		 final NodeRef current = getCurrentPerson();
    		 final Map<QName, Serializable> currentProperties = getNodeService().getProperties(current);

    		 final String fromFullName = currentProperties.get(ContentModel.PROP_FIRSTNAME) + " " + currentProperties.get(ContentModel.PROP_LASTNAME);
    		 
    		 final String userName; 
    		 if (CircabcConfig.ENT)
    		 {
    			 userName = ECASUNAME_REGEX_TINYMCE; 
    		 }
    		 else
    		 {
    			 userName = USERNAME_REGEX_TINYMCE;
    		 }
    			 
    		 final StringBuffer buffer = new StringBuffer(1024);    		 
    		 buffer
    		 	.append(I18NUtil.getMessage("mails_common_dear_user", FIRSTNAME_REGEX_TINYMCE + " " + LASTNAME_REGEX_TINYMCE))
    		 	.append("<br/><br/>")
    		 	//.append(I18NUtil.getMessage("invite_circabc_user_topfolders_template_mail_body", fromFullName, getBestTitle(getActionNode()), WebClientHelper.getGeneratedWaiFullUrl(getActionNode(), ExtendedURLMode.HTTP_WAI_BROWSE)))
    		 	.append(I18NUtil.getMessage("invite_circabc_user_topfolders_template_mail_body", fromFullName, "[NODE_BEST_TITLE]", "[NODE_REF]"))
    		 	.append("<br/><br/>")
    		 	.append("<u><i>")
    		 	.append(I18NUtil.getMessage("invite_circabc_user_template_username"))
    		 	.append("</i></u>: ")
    		 	.append(userName)
    		 	.append("<br/>")
    		 	.append("<u><i>")
    		 	.append(I18NUtil.getMessage("invite_circabc_user_template_profile"))
    		 	.append("</i></u>: ")
    		 	.append(PROFILE_REGEX_TINYMCE)
    		 	.append("<br/><br/>");
    		 	//.append(I18NUtil.getMessage("mails_common_signature").replace("<br />", "\n"));

    		 setSubject(I18NUtil.getMessage("invite_circabc_user_template_subject", "[NODE_BEST_TITLE]"));
    		 setBody(cleanBody(buffer.toString()));

    		 return "true";
         }
         else
         {
        	 String result = super.getBuildTextMessage();
        	 String htmlBody = getBody();        	 
        	 htmlBody = htmlBody.replace(FIRSTNAME_REGEX, FIRSTNAME_REGEX_TINYMCE);
        	 htmlBody = htmlBody.replace(LASTNAME_REGEX, LASTNAME_REGEX_TINYMCE);
        	 htmlBody = htmlBody.replace(ECASUNAME_REGEX, ECASUNAME_REGEX_TINYMCE);
        	 htmlBody = htmlBody.replace(PROFILE_REGEX, PROFILE_REGEX_TINYMCE);
        	 setBody(cleanBody(htmlBody));        	 
        	 return result;
         }
    }

    @Override
    public String getCallBackMethodName()
    {
        return CALLBACK_METHOD_NAME;
    }

    public Class<?>[] getCallBackMethodArgs()
    {
        return CALLBACK_METHOD_ARGS;
    }

    /**
     * @return Returns the spacesRichList.
     */
    public UIGenericPicker getPrefilledPicker() {
        final UIGenericPicker genericPicker = new UIGenericPicker();

        final FacesContext ctx = FacesContext.getCurrentInstance();
        genericPicker.setQueryCallback(ctx.getApplication().createMethodBinding(getCallBackMethodName(), getCallBackMethodArgs()));

        if(filledUsers != null)
        {
            final int PICKER_SEARCH_ACTION = 0;

            // set the call back
            final UIGenericPicker.PickerEvent event = new UIGenericPicker.PickerEvent(
                    genericPicker, PICKER_SEARCH_ACTION, filledUsersFilterIdx, filledUsers, new String[]{});
            // perform the search
            genericPicker.broadcast(event);

            filledUsers = null;
        }

        return genericPicker;
    }

    /**
    * @param spacesRichList The spacesRichList to set.
    */
    public void setPrefilledPicker(final UIGenericPicker genericPicker) {
        // nothing to do
    }

    /**
     * Already invited users are not displayed anymore in the list
     *
     * @param xpathQuery
     *            return list of people matching the xpath query
     * @param alreadyInvited
     *            set of already invited users
     * @return
     */
    private List<SortableSelectItem> executeAndFillList(final String domain, final String criteria, final List<String> userToExclude)
    {
        return  PermissionUtils.buildInvitableUserItems(domain, criteria, userToExclude, logger);
    }

    /**
     * Property accessed by the Generic Picker component.
     *
     * @return the array of filter options to show in the users/groups picker
     */
    public SelectItem[] getFilters()
    {
        if  (domainFilters == null)
        {
            domainFilters = PermissionUtils.getDomainFilters(true, false, true);
        }
        return domainFilters;
    }

    /**
     * Current node is supposed to be an IGROOT
     *
     * @return The list of available profiles for the users/groups on the
     *         current node.
     *
     */
    public List<SortableSelectItem> getProfiles() {

        return ProfileUtils.buildAssignableProfileItems(getActionNode(), logger);
    }

    /**
     * decide if next button in the wizard should be disabled or not
     */
    public boolean getNextButtonDisabled() {
        return this.userGroupProfiles.isEmpty();
    }

    /**
     * @return Returns the notify listbox selection.
     */
    public String getNotify() {
        return this.notify;
    }

    /**
     * @param notify
     *            The notify listbox selection to set.
     */
    public void setNotify(final String notify) {
        this.notify = notify;
    }

    /**
     * @return true if <b>at least one</b> applicant is found in the list of user to invite
     */
    public boolean isApplicantPresents()
    {
        if(applicantPresent == null)
        {
            boolean hasApplicant = false;

            final NodeRef currentNodeRef = getActionNode().getNodeRef();
            NodeRef currentAspectRoot = null;
            ProfileManagerService profileManagerService = null;

            profileManagerService = getProfileManagerServiceFactory().getProfileManagerService(currentNodeRef);

            if(profileManagerService !=  null)
            {
                currentAspectRoot = profileManagerService.getCurrentAspectRoot(currentNodeRef);
            }

            if(currentAspectRoot != null)
            {
                // get the list of applicants of the current interest group
                final Map<String, Applicant> applicants = profileManagerService.getApplicantUsers(currentAspectRoot);

                if(applicants != null && applicants.size() > 0)
                {
                    // for each user to invite
                    for (final UserGroupProfile user :  this.userGroupProfiles)
                    {
                        // only if a user is found in the list of applicants return true
                        if(applicants.containsKey(user.getAuthority()))
                        {
                            hasApplicant = true;
                            break;
                        }
                    }
                }
            }

            applicantPresent = Boolean.valueOf(hasApplicant);
        }

        return applicantPresent.booleanValue();
    }

    /**
     * @return true if <b>at least one</b> non applicant is found in the list of user to invite
     */
    public boolean isNonApplicantPresents()
    {
        if(nonApplicantPresent == null)
        {
            final NodeRef currentNodeRef = getActionNode().getNodeRef();

            final ProfileManagerService profileManagerService = getProfileManagerServiceFactory()
                    .getProfileManagerService(currentNodeRef);

            // get the list of applicants of the current interest group
            final Map<String, Applicant> applicants = profileManagerService.getApplicantUsers(profileManagerService
                    .getCurrentAspectRoot(currentNodeRef));

            boolean hasNonApplicant = true;

            if(applicants != null && applicants.size() > 0)
            {
                // for each user to invite
                for (final UserGroupProfile user :  this.userGroupProfiles)
                {
                    // only if a user is found in the list of applicants return true
                    if(applicants.containsKey(user.getAuthority()))
                    {
                        hasNonApplicant = false;
                    }
                    else
                    {
                        hasNonApplicant = true;
                        break;
                    }
                }
            }

            nonApplicantPresent = Boolean.valueOf(hasNonApplicant);
        }

        return nonApplicantPresent;
    }

    /**
     * @return the real number of entries returned by the user search query
     */
    public boolean isSearchResultLimitExceeded() {
        return this.realSearchResult > PermissionUtils.MAX_ELEMENTS_IN_LIST;
    }
    /**
     * Grant the selected user in the given profile and invite them in the given node
     *
     * @param context					The context of the application
     * @param nodeRef					The noderef where the user must be granted
     */
    protected void grantUsers(final FacesContext context, final NodeRef nodeRef)
    {
        final User user = Application.getCurrentUser(context);
        String from = (String) getNodeService().getProperty(user.getPerson(), ContentModel.PROP_EMAIL);

        if (from == null || from.length() == 0)
        {
            // if the user does not have an email address get the default one from the config service
            from = Application.getClientConfig(context).getFromEmailAddress();
        }

        final ProfileManagerService profileManagerService = getProfileManagerServiceFactory().getProfileManagerService(nodeRef);

        final boolean isServiceRootNode = nodeRef.equals(profileManagerService.getCurrentAspectRoot(nodeRef));

        // get the list of applicants of the current interest group
        final Map<String, Applicant> applicants = profileManagerService.getApplicantUsers(nodeRef);

        // The home space folder of the invited user will be the home space folder of the inviter
        // TODO Use this parameter !!!
        // NodeRef rootRef = this.computeCircaHomeSpaceLocation();

        // set permissions for each user and send them a mail
        for (final UserGroupProfile userGroupProfile : userGroupProfiles)
        {
            final String authority = userGroupProfile.getAuthority();

            // Remember if the user is an applicant before invite him.
            boolean wasApplicant = false;

            // if the invited authority is a user then change user's home
            // folder to the home folder of the inviter
            final AuthorityType authType = AuthorityType.getAuthorityType(authority);

            final String profileTitle;

            if(isServiceRootNode)
            {
                // The selected authorities will be added to a given profile!!
                Profile profile = profileManagerService.getProfileFromGroup(nodeRef, userGroupProfile.getProfile());
                if(profile == null)
                {
                    profile = profileManagerService.getProfile(nodeRef, userGroupProfile.getProfile());
                }

                profileTitle = profile.getProfileDisplayName();


                //	After add he in a profile, he will not longer be an applicant.
                wasApplicant = applicants != null && applicants.containsKey(authority);
                profileManagerService.addPersonToProfile(nodeRef, authority, profile.getProfileName());
                if (wasApplicant)
                {
                	profileManagerService.removeApplicantPerson(nodeRef, authority);
                }
            }
            // we are under a space, and the right of the selected authorities must be cutted
            else
            {
                profileTitle = userGroupProfile.getProfile();

                // the right selected authorities (already available for the current library) changed from the current space
                getPermissionService().setPermission(nodeRef, authority, userGroupProfile.getProfile(), true);
            }

            // if User, email then, else if Group get all members and email them
            // Create the mail message for sending to each User
            if (NOTIFY_YES.equals(this.notify))
            {
                if (authType.equals(AuthorityType.USER))
                {
                    if (getPersonService().personExists(authority) == true)
                    {
                        final NodeRef person = getPersonService().getPerson(authority);
                        Map<String, String> customParams = buildCustomParams(person, profileTitle, nodeRef);                    
                        mailToUser(person, nodeRef, from, buildModelParams(profileTitle), customParams);
                    }
                }
                else if (authType.equals(AuthorityType.GROUP))
                {
                	final String all_circa_users = ((CircabcRootProfileManagerService)getProfileManagerServiceFactory().getCircabcRootProfileManagerService()).getPrefixedAllCircaUsersGroupName();
                	if(!authority.equals(all_circa_users))
                	{

	                    // else notify all members of the group
	                    final Set<String> users = getAuthorityService()
	                            .getContainedAuthorities(AuthorityType.USER, authority, false);

	                    for (final String userAuth : users)
	                    {
	                        if (getPersonService().personExists(userAuth) == true)
	                        {
	                            final NodeRef person = getPersonService().getPerson(userAuth);
	                            Map<String, String> customParams = buildCustomParams(person, profileTitle, nodeRef);
	                            mailToUser(person, nodeRef, from, buildModelParams(profileTitle), customParams);
	                        }
	                    }
                	} else {
                		if(logger.isWarnEnabled()) {
                			logger.warn("Preventing Spam");
                		}
                	}
                }
            }
            // if notify = false, send an email to the user if it is an applicant !!!
            else if(wasApplicant)
            {
                if (authType.equals(AuthorityType.USER) && getPersonService().personExists(authority))
                {
                    final NodeRef person = getPersonService().getPerson(authority);
                    Map<String, String> customParams = buildCustomParams(person, profileTitle, nodeRef);
                    mailToUser(person, nodeRef, from, buildModelParams(profileTitle), customParams);
                }
            }
        }
    }

    private final Map<String, String> buildCustomParams(final NodeRef person, final String profile, NodeRef newNodeRef)
    {
        final Map<QName, Serializable> personProperties = getNodeService().getProperties(person);
        final Map<String, String> params = new HashMap<String, String>(3);

        final String login = PermissionUtils.computeUserLogin(personProperties);

        params.put(FIRSTNAME_REGEX_TINYMCE, (String) personProperties.get(ContentModel.PROP_FIRSTNAME));
        params.put(LASTNAME_REGEX_TINYMCE, (String) personProperties.get(ContentModel.PROP_LASTNAME));
        params.put(ECASUNAME_REGEX_TINYMCE, (String) personProperties.get(UserModel.PROP_ECAS_USER_NAME));
        params.put(USERNAME_REGEX_TINYMCE, login);
        params.put(PROFILE_REGEX_TINYMCE, profile);
        Node createdNode = new Node(newNodeRef);
        params.put("[NODE_BEST_TITLE]", getBestTitle(createdNode));
        params.put("[NODE_REF]", WebClientHelper.getGeneratedWaiFullUrl(createdNode, ExtendedURLMode.HTTP_WAI_BROWSE));          

        return params;
    }

    private final Map<String, Object> buildModelParams(final String profile)
    {
        return Collections.<String, Object> singletonMap(KEY_PROFILE, profile);
    }

    @Override
    protected  Map<String, Object> getDisplayModelToAdd()
    {
        return Collections.<String, Object> singletonMap(KEY_PROFILE, PROFILE_REGEX);
    }

    public String getBrowserTitle()
    {
        return translate("invite_circabc_user_browser_title");
    }

    public String getPageIconAltText()
    {
        return translate("invite_circabc_user_icon_tooltip");
    }

    @Override
    protected MailTemplate getMailTemplateDefinition()
    {
        return MailTemplate.INVITE_USER;

    }



    public void setSynchronizationService(SynchronizationService synchronizationService) {
		this.synchronizationService = synchronizationService;
	}

	public SynchronizationService getSynchronizationService() {
		return synchronizationService;
	}



	/**
     * Simple wrapper class to represent a user/group and a Profile combination
     */
    public static class UserGroupProfile
    {
        private String authority;
        private String profile;
        private String label;

        public UserGroupProfile(final String authority, final String role, final String label)
        {
            this.authority = authority;
            this.profile = role;
            this.label = label;
        }

        public String getAuthority()
        {
            return this.authority;
        }

        public String getProfile()
        {
            return this.profile;
        }

        public String getLabel()
        {
            return this.label;
        }

        public String toString()
        {
            return authority + " as " + profile;
        }
    }
}