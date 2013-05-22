/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.profile;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.ui.common.SortableSelectItem;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.profile.Profile;
import eu.cec.digit.circabc.service.profile.ProfileManagerService;
import eu.cec.digit.circabc.web.ProfileUtils;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * Bean that backs edit user profile dialog (for ig root only).
 *
 * @author Yanick Pignot
 */
public class EditUserProfileDialog extends BaseWaiDialog
{
	private static final long serialVersionUID = 444440868117140631L;

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(EditUserProfileDialog.class);

	public static final String BEAN_NAME = "EditUserProfileDialog";

	private String userName;
	private String userProfile;
	private String fullName;
	private Node currentNode;
	private String oldUserProfile;

	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null)
		{
			if(getActionNode() == null)
			{
				// ensure that the node Id is passed as parameter.
				throw new IllegalArgumentException("The node id is a mandatory parameter");
			}

			if(parameters.get("userName") != null || parameters.get("fullName") != null)
			{
				// the node passed as parameter is the current node.
				currentNode = getActionNode();

				userName = parameters.get("userName");
				fullName  = parameters.get("fullName");

				if(userName == null || fullName == null)
				{
					throw new IllegalArgumentException("The user name and its full name are mandatory parameter ");
				}
			}
			else
			{
				// the node passed as parameter is the user.
				currentNode = getNavigator().getCurrentIGRoot();

				Map<String, Object> properties = getActionNode().getProperties();
				String firstName = (String) properties.get("firstName");
				String lastName = (String) properties.get("lastName");
				userName = (String) properties.get("userName");
				fullName  = firstName + " " + lastName;
			}

	        final ProfileManagerService profileManagerService = getProfileService();
	        userProfile = profileManagerService.getPersonProfile(currentNode.getNodeRef(), userName);
	        oldUserProfile = userProfile;
		}

	}

	/**
	 * @return
	 */
	private ProfileManagerService getProfileService()
	{
		return getProfileManagerServiceFactory().getProfileManagerService(currentNode.getNodeRef());
	}

	@Override
	protected String finishImpl(final FacesContext context, String outcome) throws Exception
	{
		try
		{
            final NodeRef nodeRef = getActionNode().getNodeRef();
            final ProfileManagerService profileManagerService = getProfileManagerServiceFactory().getProfileManagerService(nodeRef);
            final Profile profile = profileManagerService.getProfileFromGroup(currentNode.getNodeRef(), userProfile);
            final String profileName = profile.getProfileName();
			final String info =MessageFormat.format("Changed profile of user {0} from {1} to {2}", new Object[]{fullName , oldUserProfile, profileName });
			logRecord.setInfo(info ) ;
			profileManagerService.changePersonProfile(currentNode.getNodeRef(), userName, profileName);
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

	public List<SortableSelectItem> getProfiles()
    {
		return ProfileUtils.buildAssignableProfileItems(currentNode, logger);
    }


	/**
	 * @return the userProfile
	 */
	public final String getUserProfile()
	{
		return userProfile;
	}

	/**
	 * @param userProfile the userProfile to set
	 */
	public final void setUserProfile(String userProfile)
	{
		this.userProfile = userProfile;
	}

	public String getBrowserTitle()
	{
		return translate("edit_user_profile_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("edit_user_profile_icon_tooltip");
	}

	/**
	 * @return the userName
	 */
	public final String getUserName()
	{
		return userName;
	}


	/**
	 * @return the fullName
	 */
	public final String getFullName() {
		return fullName;
	}

}
