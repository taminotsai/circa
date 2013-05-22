/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.dialog.users;

import java.util.Locale;

import javax.faces.context.FacesContext;

import org.alfresco.web.bean.repository.User;
import org.springframework.extensions.surf.util.I18NUtil;

/**
 * Dialog bean to view user profile for the WAI
 *
 * @author Yanick Pignot
 */
public class ViewUserDetailsDialog extends BaseUserDetailsBean
{
	private static final long serialVersionUID = -1151251711114914639L;

	public static final String BEAN_NAME = "CircabcViewUserDetailsDialog";
	public static final String DIALOG_NAME = "viewUserDetailsWai";


	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		// nothing to do
		return outcome;
	}

	public String getBrowserTitle()
	{
		return getContainerDescription();
	}

	public String getPageIconAltText()
	{
		return translate("view_user_details_icon_tooltip");
	}

	@Override
	public String getContainerDescription()
	{
		if(isDataSuccessfullyLoaded())
		{
			return translate("view_user_details_title_desc", getUserDetails().getDisplayId());
		}
		else
		{
			return translate("view_user_details_title_desc", "ERROR");
		}

	}
	
	public boolean isEditDetailsAllowed()
	{
		if(isDataSuccessfullyLoaded() && getUserDetails().isUserCreated())
		{
			final User currentUser = getNavigator().getCurrentUser();
			return currentUser != null && getUserDetails().getUserName().equalsIgnoreCase(currentUser.getUserName());
		}
		else
		{
			return false; 
		}
	}

	public String getInterfaceLanguageStr()
	{
		return translateLang(getUserDetails().getUserInterfaceLanguage());
	}

	public String getContentLanguageStr()
	{
		return translateLang(getUserDetails().getContentFilterLanguage());
	}

	public String getVisibilityStr()
	{
		if(getUserDetails().getVisibility())
		{
			return translate(MSG_VISIBILITY_ACTIVE);
		}
		else
		{
			return translate(MSG_VISIBILITY_NONACTIVE);
		}
	}

	public String getNotificationStr()
	{
		if(getUserDetails().getGlobalNotification())
		{
			return translate(MSG_GLOBAL_NOTIFICATION_ACTIVE);
		}
		else
		{
			return translate(MSG_GLOBAL_NOTIFICATION_NONACTIVE);
		}
	}

	@Override
	public boolean isCancelButtonVisible()
	{
		return false;
	}

	private String translateLang(final String lang)
	{
		return translateLang(I18NUtil.parseLocale(lang));
	}

	private String translateLang(final Locale locale)
	{
		return getBusinessRegistry().getPropertiesBusinessSrv().computeLanguageTranslation(locale);
	}
}
