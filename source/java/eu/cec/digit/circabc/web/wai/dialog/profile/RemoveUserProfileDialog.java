/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.profile;

import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.CircabcConfig;
import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.service.iam.SynchronizationService;
import eu.cec.digit.circabc.service.profile.Profile;
import eu.cec.digit.circabc.service.profile.ProfileException;
import eu.cec.digit.circabc.service.profile.ProfileManagerService;
import eu.cec.digit.circabc.web.PermissionUtils;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * Bean that backs remove user profile dialog (for interest group and category).
 *
 * @author Yanick Pignot
 */
public class RemoveUserProfileDialog extends BaseWaiDialog
{
	private static final String CAN_NOT_DELETE_LAST_CIRCABC_ADMIN = "can_not_delete_last_circabc_admin";

	private static final long serialVersionUID = 222220868117140631L;

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(RemoveUserProfileDialog.class);

	public static final String BEAN_NAME = "RemoveUserProfileDialog";

	private String userName;
	private String interestGroupName;
	private String categoryName;

	private boolean isSelfRemoveUserProfile;

	private String name;
	
	private SynchronizationService synchronizationService;
	
	

	private enum ContainerType {
	    UNKNOWN ,INTEREST_GROUP, CATEGORY 
	}
	private ContainerType containerType = ContainerType.UNKNOWN;

	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null)
		{
			if(getActionNode() == null)
			{
				throw new IllegalArgumentException("The node id is a mandatory parameter");
			}
			else if(parameters.get("userName") == null )
			{
				throw new IllegalArgumentException("The user name is a mandatory parameter ");
			}

			userName = parameters.get("userName");
			interestGroupName = parameters.get("interestGroupName");
			categoryName = parameters.get("categoryName");
			
			if (interestGroupName != null )
			{
				containerType = ContainerType.INTEREST_GROUP;
				name = interestGroupName;
			}
			
			if (categoryName  != null )
			{
				containerType = ContainerType.CATEGORY;
				name = categoryName;
			}
			
			String currentUserName = getNavigator().getCurrentUserName();
			isSelfRemoveUserProfile = userName.equalsIgnoreCase(currentUserName);
		}

	}

	public String getConfirmationMessage()
	{
		String message = translate("uninvite_user_confirm");
		if (isSelfRemoveUserProfile)
		{
			message = translate("self_uninvite_user_confirm");
		}

		return message ;

	}

	@Override
	public String getCancelButtonLabel()
	{
	   return translate("no");
	}
	@Override
	public String getFinishButtonLabel()
	{
		return translate("yes");
	}

	@Override
	public String getContainerTitle()
	{
		String message = translate("uninvite_user_title");
		if (isSelfRemoveUserProfile &&  containerType == ContainerType.INTEREST_GROUP )
		{
			message = translate("self_uninvite_user_title_ig",  name   );
		}
		else if (isSelfRemoveUserProfile &&  containerType == ContainerType.CATEGORY )
		{
			message = translate("self_uninvite_user_title_category",  name   );
		}
		return message ;
	}

	@Override
	public String getContainerDescription()
	{
		final String message;
		if (isSelfRemoveUserProfile &&  containerType == ContainerType.INTEREST_GROUP )
		{
			message = translate("self_uninvite_user_description_ig", name );
		}
		else if (isSelfRemoveUserProfile &&  containerType == ContainerType.CATEGORY )
		{
			message = translate("self_uninvite_user_description_category", name );
		}
		else
		{
			message = translate("uninvite_user_description", 
					"<i><b>" + PermissionUtils.computeUserLogin(userName) + "</b></i>");
		}
		return message ;

	}

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
				Set<String> invitedUsers;
				invitedUsers= profileManagerService.getInvitedUsersProfiles(nodeRef).keySet();
				return invitedUsers;

			}
		};

		return txnHelper.doInTransaction(callback, true);
	}
	
	
	@Override
	protected String finishImpl(final FacesContext context, String outcome) throws Exception
	{
		try
        {
			final NodeRef nodeRef = getActionNode().getNodeRef();
			final ProfileManagerService profileManagerService = getProfileManagerServiceFactory().getProfileManagerService(nodeRef);
			final String  personProfile = profileManagerService.getPersonProfile(nodeRef,userName);
			if (personProfile ==null)
			{
				if (logger.isErrorEnabled())
	            {
	                logger.error("Can not not delete last admin : " + userName + ", noderef :"  + nodeRef.toString() );
	            }
				Utils.addErrorMessage("Person profile is null : " + userName + " noderef : "  +  nodeRef.toString());
			}
			Profile profile = profileManagerService.getProfile(nodeRef, personProfile);
			if (profile.isAdmin() &&  getNumberofAdmins(nodeRef, profileManagerService) ==1)
			{
				if (logger.isWarnEnabled())
	            {
	                logger.warn("Can not not delete last admin : " + userName + ", noderef :"  + nodeRef.toString() );
	            }
				
				final String message;   
				if(getActionNode().hasAspect(CircabcModel.ASPECT_CIRCABC_ROOT))
				{
					message =CAN_NOT_DELETE_LAST_CIRCABC_ADMIN;
				}
				else if(getActionNode().hasAspect(CircabcModel.ASPECT_CATEGORY))
				{
					message ="can_not_delete_last_category_admin";
				}
				else
				{
					message ="self_uninvite_error_message_last_ig_leader";
				}
	            Utils.addErrorMessage(translate(message));
	            outcome = null;
				
			}
			else
			{
				logRecord.setInfo("Removed membership for user " + userName);
				profileManagerService.uninvitePerson(nodeRef, userName);
			}
			if (CircabcConfig.ENT)
			{
				String ecordaThemeId = synchronizationService.getEcordaThemeId(getActionNode().getNodeRef());
				if ( !ecordaThemeId.isEmpty())
				{
					synchronizationService.revokeThemeRole(userName,ecordaThemeId);
				}
			}
			
        }
		catch (ProfileException profileException)
		{
			 if (logger.isErrorEnabled())
	            {
	                logger.error("Unexpected error:" + profileException.getExplanation(), profileException);
	            }

	            Utils.addErrorMessage(translate(Repository.ERROR_GENERIC, profileException.getExplanation()), profileException);

	            outcome = null;

		}
		catch (Throwable err)
        {
            if (logger.isErrorEnabled())
            {
                logger.error("Unexpected error:" + err.getMessage(), err);
            }

            Utils.addErrorMessage(translate(Repository.ERROR_GENERIC, err.getMessage()), err);

            outcome = null;
        }


		return outcome;
	}

	private int getNumberofAdmins(final NodeRef nodeRef,
			final ProfileManagerService profileManagerService) {
		int currentNodeAdminCount = 0;
		for (final String authority : buildInvitedSet())
		{
			String personProfile;
			Profile profile;
			personProfile = profileManagerService.getPersonProfile(nodeRef,authority);
			if (personProfile == null)
			{
				continue;
			}
			profile = profileManagerService.getProfile(nodeRef, personProfile);
			if (profile.isAdmin() )
			{
				currentNodeAdminCount ++;
			}
		}
		return currentNodeAdminCount;
	}


	public String getBrowserTitle()
	{
		String message = translate("uninvite_user_browser_title");
		if (isSelfRemoveUserProfile)
			{
				message = translate("self_uninvite_user_browser_title" );
			}
		return message ;
	}

	public String getPageIconAltText()
	{
		String message = translate("uninvite_user_icon_tooltip");
		if (isSelfRemoveUserProfile)
			{
				message = translate("self_uninvite_user_icon_tooltip" );
			}
		return message ;
	}

	public void setSynchronizationService(SynchronizationService synchronizationService) {
		this.synchronizationService = synchronizationService;
	}

	public SynchronizationService getSynchronizationService() {
		return synchronizationService;
	}
	
	@Override
	public boolean isFinishAsyncButtonVisible() {
 
		return true;
 
	}
}
