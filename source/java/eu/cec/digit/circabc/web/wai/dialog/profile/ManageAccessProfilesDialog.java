/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.profile;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.ui.common.Utils;
import org.alfresco.web.ui.common.component.UIActionLink;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.ext.SortableModel;

import eu.cec.digit.circabc.service.cmr.security.CircabcConstant;
import eu.cec.digit.circabc.service.log.LogRecord;
import eu.cec.digit.circabc.service.profile.CircabcRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.CircabcServices;
import eu.cec.digit.circabc.service.profile.EventProfileManagerService;
import eu.cec.digit.circabc.service.profile.IGRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.InformationProfileManagerService;
import eu.cec.digit.circabc.service.profile.LibraryProfileManagerService;
import eu.cec.digit.circabc.service.profile.NewsGroupProfileManagerService;
import eu.cec.digit.circabc.service.profile.Profile;
import eu.cec.digit.circabc.service.profile.ProfileException;
import eu.cec.digit.circabc.service.profile.ProfileManagerService;
import eu.cec.digit.circabc.service.profile.SurveyProfileManagerService;
import eu.cec.digit.circabc.service.profile.permissions.DirectoryPermissions;
import eu.cec.digit.circabc.service.profile.permissions.EventPermissions;
import eu.cec.digit.circabc.service.profile.permissions.InformationPermissions;
import eu.cec.digit.circabc.service.profile.permissions.LibraryPermissions;
import eu.cec.digit.circabc.service.profile.permissions.NewsGroupPermissions;
import eu.cec.digit.circabc.service.profile.permissions.SurveyPermissions;
import eu.cec.digit.circabc.service.profile.permissions.VisibilityPermissions;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.comparator.CircabcProfileDisplayTitleSort;
import eu.cec.digit.circabc.web.repository.IGServicesNode;
import eu.cec.digit.circabc.web.repository.InterestGroupNode;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * Baked bean for profiles management
 *
 * @author Yanick Pignot
 */
public class ManageAccessProfilesDialog extends BaseWaiDialog
{
    private static final long serialVersionUID = -7326739780479536405L;

    private static final Log logger = LogFactory.getLog(ManageAccessProfilesDialog.class);

    private static final String MSG_IMPORT_EMPTY_SELECTION = "import_profile_empty_selection";
    private static final String MSG_IMPORT_ERROR = "import_profile_error";

    /*package*/ static final String MSG_NO_LONGER_PERM = "no_longer_manage_member_permission";

    private static final String MSG_DEACTIVATE_GUEST_SUCCESS = "deactivate_guest_success";
    private static final String MSG_ACTIVATE_GUEST_SUCCESS = "activate_guest_success";
    private static final String MSG_ACTIVATE_GUEST_ERRROR = "activate_guest_error";
    private static final String MSG_DEACTIVATE_REGISTRED_SUCCESS = "deactivate_registred_success";
    private static final String MSG_ACTIVATE_REGISTRED_SUCCESS = "activate_registred_success";
    private static final String MSG_ACTIVATE_REGISTRED_ERROR = "activate_registred_error";

    private static final String MSG_EXPORT_PROFILE_SUCCESS = "export_profile_success";
    private static final String MSG_EXPORT_PROFILE_FAILURE = "export_profile_failure";
    private static final String MSG_UNEXPORT_PROFILE_SUCCESS = "unexport_profile_success";
    private static final String MSG_UNEXPORT_PROFILE_FAILURE = "unexport_profile_failure";

    private static final String IMPORT_SELECT_EMPTY = "__SELECT_ONE__";
    private static final String PARAM_ACTIVATE = "activate";

    private static final HashMap<String, Set<String>> DEFAULT_PERMISSIONS = new HashMap<String, Set<String>>();

    static{
    	DEFAULT_PERMISSIONS.put(CircabcServices.VISIBILITY.toString(), Collections.singleton(VisibilityPermissions.NOVISIBILITY.toString()));
    	DEFAULT_PERMISSIONS.put(CircabcServices.INFORMATION.toString(), Collections.singleton(InformationPermissions.INFNOACCESS.toString()));
    	DEFAULT_PERMISSIONS.put(CircabcServices.LIBRARY.toString(), Collections.singleton(LibraryPermissions.LIBNOACCESS.toString()));
    	DEFAULT_PERMISSIONS.put(CircabcServices.DIRECTORY.toString(), Collections.singleton(DirectoryPermissions.DIRNOACCESS.toString()));
    	DEFAULT_PERMISSIONS.put(CircabcServices.EVENT.toString(), Collections.singleton(EventPermissions.EVENOACCESS.toString()));
    	DEFAULT_PERMISSIONS.put(CircabcServices.NEWSGROUP.toString(), Collections.singleton(NewsGroupPermissions.NWSNOACCESS.toString()));
    	DEFAULT_PERMISSIONS.put(CircabcServices.SURVEY.toString(), Collections.singleton(SurveyPermissions.SURNOACCESS.toString()));

    	Collections.unmodifiableMap(DEFAULT_PERMISSIONS);
    }

    private String exportedProfile;
    private Map<String, AccessProfileWrapper> categoryExportedProfiles;
    private DataModel profileDataModel;
    private DataModel importedProfileDataModel;
    private Boolean guestAvailable;
    private Boolean registredAvailable;
    private Boolean isIgLeader;

	private AuthorityService authorityService;

    @Override
    public void init(Map<String, String> arg0)
    {
        super.init(arg0);

        exportedProfile = null;
        categoryExportedProfiles = null;
        profileDataModel = null;
        importedProfileDataModel = null;
        guestAvailable = null;
        registredAvailable = null;

        Node actionNode = getActionNode();
        if(actionNode == null)
        {
            throw new IllegalArgumentException("The node id is a mandatory parameter");
        }
        else if(NavigableNodeType.IG_ROOT.isNodeFromType(actionNode) == false)
        {
            throw new IllegalArgumentException("This page is accessible only for an interest group");
        }
        
        if(getActionNode().hasPermission(DirectoryPermissions.DIRADMIN.toString()) == false)
        {
            Utils.addErrorMessage(translate(MSG_NO_LONGER_PERM));
        }
    }

    @Override
    public void restored()
    {
    	 init(null);
    }


    @Override
    protected String finishImpl(FacesContext context, String outcome) throws Throwable
    {
        // nothing to do
        return outcome;
    }


    @Override
	public String getCancelButtonLabel()
	{
	   return translate("close");
	}

    /**
     * Action handler called when the 'Import Profile' action is performed in the model
     */
    public void importProfile(final ActionEvent event)
    {
        if(exportedProfile == null  || exportedProfile.equals(IMPORT_SELECT_EMPTY))
        {
            Utils.addErrorMessage(translate(MSG_IMPORT_EMPTY_SELECTION));
        }
        else if(getCategoryExportedProfiles().containsKey(exportedProfile) == false)
        {
        	Utils.addErrorMessage(translate(MSG_IMPORT_EMPTY_SELECTION));
        }
        else
        {
        	final LogRecord importLogRecord = new LogRecord();
	        setLogRecord(importLogRecord, "Administration", "Import a profile" );
        	  try
              {
        		  final RetryingTransactionHelper txnHelper = Repository.getRetryingTransactionHelper(FacesContext.getCurrentInstance());
        		  final AccessProfileWrapper wrapper = getCategoryExportedProfiles().get(exportedProfile);
        		  final String profile = wrapper.getDisplayTitle();
      			  final String igName = (String) getNodeService().getProperty(wrapper.getFromIg(), ContentModel.PROP_NAME);
                  final String info =MessageFormat.format("Profile {0} has been imported from IG {1}", new Object[]{profile,igName});
      			  importLogRecord.setInfo(info );

        		  final RetryingTransactionCallback<Object> callback = new RetryingTransactionCallback<Object>()
        		  {
        			  public Object execute() throws Throwable
        			  {
                          getIGRootProfileManager().importProfile(getIgNodeRef(), wrapper.getFromIg(), wrapper.getName(), DEFAULT_PERMISSIONS);

                          return null;
        			  }
        		  };


                  txnHelper.doInTransaction(callback, false, false);
                  this.init(null);
              }
              catch(final Exception e)
              {
                  Utils.addErrorMessage(translate(MSG_IMPORT_ERROR, e.getMessage()));

                  if(logger.isErrorEnabled())
                  {
                	  logger.error(translate(MSG_IMPORT_ERROR), e);
                  }
              }
              finally
              {
              	getLogService().log(importLogRecord);
              }
        }
    }

    /**
     * Action handler called when the 'Activate Guest' action is performed in the model
     */
    public void activateGuest(final ActionEvent event)
    {

    	final LogRecord activateGuestLogRecord = new LogRecord();
        setLogRecord(activateGuestLogRecord, "Administration", "Modify guest visibility");

        final Boolean valueAsBool = extractActivateParamValue(event);
        try
        {
            final Profile guest = getGuestProfile();
            setVisibility(valueAsBool, guest);
            if(valueAsBool.booleanValue())
            {
                Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_ACTIVATE_GUEST_SUCCESS));
                activateGuestLogRecord.setInfo("visibility activated");
                if (!isRegistredActivated()){
                	//activate registered as well
                	activateRegistered(event);
                }
            }
            else
            {
                Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_DEACTIVATE_GUEST_SUCCESS));
                activateGuestLogRecord.setInfo("visibility deactivated");
            }

        }
        catch(final Exception e)
        {
        	if (logger.isErrorEnabled())
        	{
        		logger.error("Error when changing guest access.", e);
        	}
            Utils.addErrorMessage(translate(MSG_ACTIVATE_GUEST_ERRROR, e.getMessage()));
            activateGuestLogRecord.setOK(false);
        }
        finally
        {
        	getLogService().log(activateGuestLogRecord);
        }
    }

	private void setLogRecord(final LogRecord logRecord, String service, String activity)
	{
		logRecord.setIgID(super.actionNodeDatabaseID);
		logRecord.setIgName((String) getNodeService().getProperty(super.getActionNode().getNodeRef(), ContentModel.PROP_NAME));
        logRecord.setService(service);
        logRecord.setActivity(activity);
        logRecord.setOK(true);
        updateLogDocument(this.getIgNodeRef(),logRecord);
	}

    /**
     * Action handler called when the 'Activate Registered' action is performed in the model
     */
    public void activateRegistered(final ActionEvent event)
    {
    	final LogRecord activateRegisteredLogRecord = new LogRecord();
        setLogRecord(activateRegisteredLogRecord, "Administration", "Modify registred visibility");
        final Boolean valueAsBool = extractActivateParamValue(event);

        try
        {
            final Profile registred = getRegistredProfile();
            setVisibility(valueAsBool, registred);

            if(valueAsBool.booleanValue())
            {
                Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_ACTIVATE_REGISTRED_SUCCESS));
                activateRegisteredLogRecord.setInfo("visibility activated");
            }
            else
            {
                Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_DEACTIVATE_REGISTRED_SUCCESS));
                activateRegisteredLogRecord.setInfo("visibility deactivated");
                if (isGuestActivated()) {
                	//deactivate guest as well
                	activateGuest(event);
                }
            }
        }
        catch(final Exception e)
        {
        	if (logger.isErrorEnabled())
        	{
        		logger.error("Error when changing registered access.", e);
        	}
            Utils.addErrorMessage(translate(MSG_ACTIVATE_REGISTRED_ERROR, e.getMessage()));
            activateRegisteredLogRecord.setOK(false);
        }
        finally
        {
        	getLogService().log(activateRegisteredLogRecord);
        }
    }


    public boolean isGuestActivated()
    {
    	if(guestAvailable == null)
    	{
    		final Profile profile = getGuestProfile();
    		guestAvailable = computeVisibility(profile);
    	}
        return guestAvailable;
    }

    public boolean isRegistredActivated()
    {
    	if(registredAvailable == null)
        {
    		final Profile profile = getRegistredProfile();
    		registredAvailable = computeVisibility(profile);
        }
        return registredAvailable;
    }

    private Profile getGuestProfile()
    {
        final Profile profile = getIGRootProfileManager().getProfile(getIgNodeRef(), CircabcConstant.GUEST_AUTHORITY);
        return profile;
    }

    private Profile getRegistredProfile()
    {
        final NodeRef igNodeRef = getIgNodeRef();
        final String registredGroupName = getCircabcProfileManager().getAllCircaUsersGroupName();
        final Profile profile = getIGRootProfileManager().getProfile(igNodeRef, registredGroupName);
        return profile;
    }

    public void unexportProfile(final ActionEvent event)
    {
    	final LogRecord exportLogRecord = new LogRecord();
        setLogRecord(exportLogRecord, "Administration", "Cancel export a profile");
        try
        {

        	 final AccessProfileWrapper wrapper = (AccessProfileWrapper) this.profileDataModel.getRowData();
             getIGRootProfileManager().exportProfile(getIgNodeRef(), wrapper.getName(), false);

             Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_UNEXPORT_PROFILE_SUCCESS,
                     wrapper.getDisplayTitle(),
                     getNavigator().getCurrentCategory().getName()));

             this.init(null);

			String igName = (String) getNodeService().getProperty(getIgNodeRef(), ContentModel.PROP_NAME);
            String info =MessageFormat.format("Profile {0} has been exported from IG {1}", new Object[]{wrapper.getName(), igName});
			exportLogRecord.setInfo(info );

            this.init(null);
        }
        catch(final Exception e)
        {
        	 Utils.addErrorMessage(translate(MSG_UNEXPORT_PROFILE_FAILURE, e.getMessage()));
            exportLogRecord.setOK(false);
        }
        finally
        {
        	getLogService().log(exportLogRecord);
        }
    }

    /**
     * @return
     */
    private NodeRef getIgNodeRef()
    {
        return getActionNode().getNodeRef();
    }


    public void exportProfile(final ActionEvent event)
    {
    	final LogRecord exportLogRecord = new LogRecord();
        setLogRecord(exportLogRecord, "Administration","Export a profile" );
        try
        {
            final AccessProfileWrapper wrapper = (AccessProfileWrapper) this.profileDataModel.getRowData();
            getIGRootProfileManager().exportProfile(getIgNodeRef(), wrapper.getName(), true);

            String profile = wrapper.getDisplayTitle();
			Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_EXPORT_PROFILE_SUCCESS,
                    profile,
                    getNavigator().getCurrentCategory().getName()));
			String igName = (String) getNodeService().getProperty(getIgNodeRef(), ContentModel.PROP_NAME);
            String info =MessageFormat.format("Profile {0} has been exported from IG {1}", new Object[]{profile,igName});
			exportLogRecord.setInfo(info );

            this.init(null);
        }
        catch(final Exception e)
        {
            Utils.addErrorMessage(translate(MSG_EXPORT_PROFILE_FAILURE, e.getMessage()));
            exportLogRecord.setOK(false);
        }
        finally
        {
        	getLogService().log(exportLogRecord);
        }
    }

    /**
     * Returns the properties for current profile DataModel
     *
     * @return JSF DataModel representing the current Profiles
     */
    public DataModel getProfilesDataModel()
    {
    	if(this.profileDataModel == null)
        {
    		this.profileDataModel = getDataModel(false);
        }
        return this.profileDataModel;
    }

    /**
     * Returns the properties for current profile DataModel
     *
     * @return JSF DataModel representing the current Profiles
     */
    public DataModel getImportedProfilesDataModel()
    {
    	if(importedProfileDataModel == null)
        {
    		this.importedProfileDataModel = getDataModel(true);
        }
        return this.importedProfileDataModel;
    }


    private DataModel getDataModel(final boolean onlyImported)
    {
        final DataModel profilesDataModel = new SortableModel();

        NodeRef igNodeRef = getIgNodeRef();
		final List<Profile> profiles = getIGRootProfileManager().getProfiles(igNodeRef);
        final List<AccessProfileWrapper> webProfiles = new ArrayList<AccessProfileWrapper>(profiles.size());
        final String currentUserName = getNavigator().getCurrentUser().getUserName();
        String userProfile = getIGRootProfileManager().getPersonProfile(igNodeRef, currentUserName);
        boolean hasExportedProfiles = false;
        for(final Profile profile: profiles)
        {
            boolean imported = profile.isImported();
            if(imported == onlyImported)
            {
                webProfiles.add(new AccessProfileWrapper(profile, igNodeRef, currentUserName));
            }
            if (!onlyImported)
            {
            	if (profile.isExported())
            	{
            		hasExportedProfiles = true;
            	}
            }
        }
        if (hasExportedProfiles )
        {
        	Set<String>  prefixedAlfrescoGroupNames = new HashSet<String>(128);
    		final NodeRef category = getManagementService().getCurrentCategory(igNodeRef);

    		for(final NodeRef ig: getManagementService().getInterestGroups(category))
    		{
    			if(!ig.equals(igNodeRef))
    			{
    				for(final Profile prof: getProfileManagerServiceFactory().getIGRootProfileManagerService().getProfiles(ig))
    				{
    					prefixedAlfrescoGroupNames.add(prof.getPrefixedAlfrescoGroupName());
    				}
    			}
    		}
        	for (AccessProfileWrapper accessProfileWrapper : webProfiles) 
        	{
        		if (accessProfileWrapper.isExported() && !prefixedAlfrescoGroupNames.contains(accessProfileWrapper.getAutority()) )
        		{
        			accessProfileWrapper.setUnexportedActionAvailable(true);
        		}
			} 
        }
        
        if (!onlyImported)
        {
        	for (AccessProfileWrapper accessProfileWrapper : webProfiles) 
        	{
        		if ( userProfile != null && userProfile.equals(accessProfileWrapper.getName()) )
        		{
        			accessProfileWrapper.setEditActionAvailable(false);
        		}
			}
        	
        	for (AccessProfileWrapper accessProfileWrapper : webProfiles) 
        	{
				if (!accessProfileWrapper.isSpecialProfile()
						&& getAuthorityService().authorityExists(
								accessProfileWrapper.getAutority())
						&& getAuthorityService().getContainedAuthorities(
								AuthorityType.USER,
								accessProfileWrapper.getAutority(), true)
								.size() == 0) {
					accessProfileWrapper.setDeleteActionAvailable(true);
				}
			}
        }
        
        Collections.sort(webProfiles, new CircabcProfileDisplayTitleSort(getUserPreferencesBean().getLanguage()) );
        profilesDataModel.setWrappedData(webProfiles);
        return profilesDataModel;
    }

    public  AuthorityService getAuthorityService() {

		return authorityService;
	}
    
    public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	protected IGRootProfileManagerService getIGRootProfileManager()
    {
        return getProfileManagerServiceFactory().getIGRootProfileManagerService();
    }

    protected CircabcRootProfileManagerService getCircabcProfileManager()
    {
        return getProfileManagerServiceFactory().getCircabcRootProfileManagerService();
    }

    protected InformationProfileManagerService getInformationProfileManager() {
    	return getProfileManagerServiceFactory().getInformationProfileManagerService();
    }

    protected LibraryProfileManagerService getLibraryProfileManager() {
    	return getProfileManagerServiceFactory().getLibraryProfileManagerService();
    }

    protected NewsGroupProfileManagerService getNewsGroupProfileManager() {
    	return getProfileManagerServiceFactory().getNewsGroupProfileManagerService();
    }

    protected SurveyProfileManagerService getSurveyProfileManager() {
    	return getProfileManagerServiceFactory().getSurveyProfileManagerService();
    }

    protected EventProfileManagerService getEventProfileManager() {
    	return getProfileManagerServiceFactory().getEventProfileManagerService();
    }

    public String getBrowserTitle()
    {
        return translate("edit_circa_ig_profiles_browser_title");
    }

    public String getPageIconAltText()
    {
        return translate("edit_circa_ig_profiles_icon_tooltip");
    }

    /**
     * @return the exportedProfile
     */
    public final String getExportedProfile()
    {
        return exportedProfile;
    }

    /**
     * @return the exportedProfile
     */
    public final void setExportedProfile(final String exportedProfile)
    {
        this.exportedProfile = exportedProfile;
    }

    /**
     * @return the exportedProfiles
     */
    public final List<SelectItem> getExportedProfiles()
    {
    	List<SelectItem> exportedProfiles  = AuthenticationUtil.runAs(new RunAsWork<List<SelectItem>>() {
			public List<SelectItem> doWork() throws Exception
			{

		    	final Map<String, AccessProfileWrapper> exportedProfiles = getCategoryExportedProfiles();
		    	final List<SelectItem> profileItems = new ArrayList<SelectItem>(exportedProfiles.size());

		        NodeRef ig;
		        String igName;
		        for(final Map.Entry<String, AccessProfileWrapper> entry: exportedProfiles.entrySet())
		        {
		        	ig = entry.getValue().getFromIg();
		        	igName = (String) getNodeService().getProperty(ig, ContentModel.PROP_NAME);

		        	profileItems.add(new SelectItem(entry.getKey(), igName + ":" + entry.getValue().getDisplayTitle()));
		        }
		        return profileItems;
			}
		}, AuthenticationUtil.getSystemUserName());
    	return exportedProfiles;
    }

    /**
     * @param event
     * @return
     * @throws IllegalArgumentException
     */
    private Boolean extractActivateParamValue(final ActionEvent event) throws IllegalArgumentException
    {
        final String value = buildParameterMap(event).get(PARAM_ACTIVATE);

        if(value == null || (!Boolean.TRUE.toString().equals(value) &&  !Boolean.FALSE.toString().equals(value)))
        {
            throw new IllegalArgumentException("Param 'activate' accept only true or false value.");
        }

        Boolean valueAsBool = Boolean.parseBoolean(value);
        return valueAsBool;
    }

    /**
     * @param event
     * @return
     */
    private Map<String, String> buildParameterMap(final ActionEvent event)
    {
        final List childs;
        if(event.getComponent() instanceof HtmlCommandButton)
        {
            final HtmlCommandButton button = (HtmlCommandButton) event.getComponent();
            childs = button.getChildren();
        }
        else
        {
            final UIActionLink command = (UIActionLink) event.getComponent();
            childs = command.getChildren();
        }

        UIParameter parameter;
        final Map<String, String> parameters = new HashMap<String, String>();

        for(final Object child : childs)
        {
            if(child instanceof UIParameter)
            {
                parameter = (UIParameter) child;
                parameters.put(
                        parameter.getName(),
                        parameter.getValue().toString());
            }
        }
        return parameters;
    }

    private boolean computeVisibility(final Profile profile)
    {
        final Set<String> servicePermissions = profile.getServicePermissions(CircabcServices.VISIBILITY.toString());
        if(servicePermissions == null || servicePermissions.size() < 0)
        {
            return false;
        }
        else
        {
            final String visibility = servicePermissions.iterator().next();
            return VisibilityPermissions.VISIBILITY.toString().equals(visibility);
        }

    }

    private Map<String, AccessProfileWrapper> getCategoryExportedProfiles()
    {
    	 if (this.categoryExportedProfiles == null)
		{
			AuthenticationUtil.runAs(new RunAsWork<Object>()
			{
				public Object doWork() throws Exception
				{

					categoryExportedProfiles = new HashMap<String, AccessProfileWrapper>();

					final NodeRef category = getNavigator().getCurrentCategory().getNodeRef();
					final NodeRef currentIg = getIgNodeRef();
					final ProfileManagerService profService = getIGRootProfileManager();
					final List<NodeRef> allIg = getManagementService().getInterestGroups(category);

					final Set<String> importedProfileAuthority = new HashSet<String>();

					for (final Profile profile : profService.getProfiles(currentIg))
					{
						if (profile.isImported() == true)
						{
							importedProfileAuthority.add(profile.getPrefixedAlfrescoGroupName());
						}
					}

					for (final NodeRef ig : allIg)
					{
						if (currentIg.equals(ig) == false)
						{
							for (final Profile profile : profService.getExportedProfiles(ig))
							{
								if (importedProfileAuthority.contains(profile.getPrefixedAlfrescoGroupName()) == false)
								{
									categoryExportedProfiles.put(profile.getPrefixedAlfrescoGroupName(), new AccessProfileWrapper(profile, ig));
								}
							}
						}
					}
					return null;
				}
			}, AuthenticationUtil.getSystemUserName());
		}
		return this.categoryExportedProfiles;
    }

    /**
	 * @param valueAsBool
	 * @param profile
	 * @throws ProfileException
	 */
    private void setVisibility(final Boolean valueAsBool, final Profile profile) throws ProfileException
    {
		final HashMap<String, Set<String>> servicesPermissions;

		final InterestGroupNode igRoot = (InterestGroupNode) getNavigator().getCurrentIGRoot();
		final IGServicesNode library = igRoot.getLibrary();
		final IGServicesNode newsgroup = igRoot.getNewsgroup();
		final IGServicesNode information = igRoot.getInformation();
		final IGServicesNode event = igRoot.getEvent();
		final IGServicesNode survey = igRoot.getSurvey();

        if(valueAsBool == true)
        {
       		servicesPermissions = profile.getServicesPermissions();
       		servicesPermissions.putAll(DEFAULT_PERMISSIONS);
       		servicesPermissions.put(CircabcServices.VISIBILITY.toString(),
            		Collections.singleton(VisibilityPermissions.VISIBILITY.toString()));

            profile.setServicesPermissions(servicesPermissions);
        }
        else
        {
        	servicesPermissions = new HashMap<String, Set<String>>();
        	servicesPermissions.putAll(DEFAULT_PERMISSIONS);
        	profile.setServicesPermissions(servicesPermissions);
        }

        if(getIgNodeRef() != null) {
        	getIGRootProfileManager().updateProfile(getIgNodeRef(), profile.getProfileName(), profile.getServicesPermissions(), true);
        }

        if(information != null) {
        	getInformationProfileManager().updateProfile(information.getNodeRef(), profile.getProfileName(), profile.getServicesPermissions(), true);
        }
        if(library != null) {
        	getLibraryProfileManager().updateProfile(library.getNodeRef(), profile.getProfileName(), profile.getServicesPermissions(), true);
        }
        if(newsgroup != null) {
        	getNewsGroupProfileManager().updateProfile(newsgroup.getNodeRef(), profile.getProfileName(), profile.getServicesPermissions(), true);
        }
        if(survey != null) {
        	getSurveyProfileManager().updateProfile(survey.getNodeRef(), profile.getProfileName(), profile.getServicesPermissions(), true);
        }
        if(event != null) {
        	getEventProfileManager().updateProfile(event.getNodeRef(), profile.getProfileName(), profile.getServicesPermissions(), true);
        }

        /* TODO
        if(getDirectoryNodeRef() != null) {
        	getDirectoryProfileManager().updateProfile(getDirectoryNodeRef(), profile.getProfileName(), profile.getServicesPermissions(), true);
        }
        */




        // reset permission cache on the action node
        getActionNode().reset();
        // reset permission cache on all navigation node
        getNavigator().updateCircabcNavigationContext();
    }

	public Boolean getIsIgLeader() {
		return isIgLeader;
	}

	public void setIsIgLeader(Boolean isIgLeader) {
		this.isIgLeader = isIgLeader;
	}

	
}
