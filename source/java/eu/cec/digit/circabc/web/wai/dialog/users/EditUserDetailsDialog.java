/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.dialog.users;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.springframework.extensions.surf.util.I18NUtil;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.bean.users.UserPreferencesBean;
import org.alfresco.web.ui.common.Utils;

import eu.cec.digit.circabc.business.api.BusinessStackError;
import eu.cec.digit.circabc.business.api.user.UserDetails;
import eu.cec.digit.circabc.business.api.user.UserDetailsBusinessSrv;
import eu.cec.digit.circabc.service.user.UserService;
import eu.cec.digit.circabc.web.Beans;

/**
 * Dialog bean to edit user profile for the WAI
 *
 * @author Guillaume
 */
public class EditUserDetailsDialog extends BaseUserDetailsBean
{
	private static final long serialVersionUID = -5363740078894071426L;

	public static final String BEAN_NAME = "EditUserDetailsDialog";

	private static final String MSG_ERR_UNEXPECTED_ERROR = "edit_user_details_erreor_unexpected";

	private static final String MSG_AVATAR_DELETED = "edit_user_details_avatar_del_success";
	private static final String MSG_DATA_LOADED = "edit_user_details_reload_success";
	private static final String MSG_SUCCESS = "edit_user_details_success";

	public static final String DIALOG_NAME = "userConsoleWai";

	/**
	 * @see org.alfresco.web.bean.dialog#finish()
	 */
	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		final UserDetails userDetails = getUserDetails();
		try
    	{
			getBusinessRegistry().getUserDetailsBusinessSrv().updateUserDetails(userDetails.getNodeRef(), userDetails);
			// if user interface language is changed update ui
	        final Map<QName, Serializable> updatedPreferences = userDetails.getUpdatedPreferences();
			if (updatedPreferences.containsKey(UserService.PREF_INTERFACE_LANGUAGE))
	        {
	        	final UserPreferencesBean userPreferencesBean = getUserPreferencesBean();
	        	final String language = (String) updatedPreferences.get(UserService.PREF_INTERFACE_LANGUAGE);
	        	userPreferencesBean.setLanguage(language);
	        }
			
			Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_SUCCESS));
    	}
		catch(final BusinessStackError validationErrors)
		{
			for(final String msg: validationErrors.getI18NMessages())
			{
				Utils.addErrorMessage(msg);
			}

			this.isFinished = false;
			return null;
		}
		catch(Throwable t)
        {
        	isFinished = false;
        	Utils.addErrorMessage(translate(MSG_ERR_UNEXPECTED_ERROR, userDetails.getDisplayId(), t.getMessage()));
        	return null;
        }
		
		
		
		return outcome;
	}

	/**
	 * Refresh user data with ldap values
	 *
	 * @param event
	 */
	public void getFromLdap(ActionEvent event)
	{
		try
    	{
			getBusinessRegistry().getRemoteUserBusinessSrv().reloadDetails(getUserDetails());

			Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_DATA_LOADED));
    	}
		catch(final BusinessStackError validationErrors)
		{
			for(final String msg: validationErrors.getI18NMessages())
			{
				Utils.addErrorMessage(msg);
			}
		}
	}

	/**
	 * Refresh user data with ldap values
	 *
	 * @param event
	 */
	public void removeAvatar(ActionEvent event)
	{
		try
    	{
			final UserDetailsBusinessSrv userDetailsBusinessSrv = getBusinessRegistry().getUserDetailsBusinessSrv();

			userDetailsBusinessSrv.removeAvatar(getUserDetails().getNodeRef());
			getUserDetails().setAvatar(userDetailsBusinessSrv.getAvatar(getUserDetails().getNodeRef()));

			Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_AVATAR_DELETED));
    	}
		catch(final BusinessStackError validationErrors)
		{
			for(final String msg: validationErrors.getI18NMessages())
			{
				Utils.addErrorMessage(msg);
			}
		}
	}

	/**
	 * Refresh user data with ldap values
	 *
	 * @param event
	 */
	public void launchUpdateAvatarDialog(ActionEvent event)
	{
		final UpdateAvatarDialog dialog = (UpdateAvatarDialog) Beans.getBean(UpdateAvatarDialog.BEAN_NAME);
		dialog.start(event);
	}


	/**
	 * @return		if the circabc installation use a remote user manager (ldap)
	 */
	public boolean isReloadbuttonAvailable()
	{
		return getBusinessRegistry().getRemoteUserBusinessSrv().isRemoteManagementAvailable();
	}

	/**
	 * @param contentFilterLanguage the contentFilterLanguage to set
	 */
	public final void setContentFilterLanguage(String contentFilterLanguage)
	{
		if(contentFilterLanguage == null || contentFilterLanguage.equalsIgnoreCase(UserPreferencesBean.MSG_CONTENTALLLANGUAGES))
		{
			getUserDetails().setContentFilterLanguage(null);
		}
		else
		{
			getUserDetails().setContentFilterLanguage(I18NUtil.parseLocale(contentFilterLanguage));
		}

	}

	/**
	 * @param contentFilterLanguage the contentFilterLanguage to set
	 */
	public final String getContentFilterLanguage()
	{
		final Locale contentFilterLanguage = getUserDetails().getContentFilterLanguage();
		if(contentFilterLanguage == null)
		{
			return null;
		}
		else
		{
			return contentFilterLanguage.getLanguage();
		}
	}

	public String getBrowserTitle()
	{
		return translate("edit_user_details_header");
	}

	public String getPageIconAltText()
	{
		return translate("edit_user_details_icon_tooltip");
	}

	@Override
	public String getFinishButtonLabel()
	{
		return translate("save");
	}
}
