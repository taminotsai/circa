/*--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--*/
package eu.cec.digit.circabc.web.bean.override;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.web.bean.repository.Preferences;
import org.alfresco.web.bean.repository.PreferencesService;
import org.alfresco.web.bean.users.UserPreferencesBean;
import org.springframework.extensions.surf.util.I18NUtil;


/**
 * @author guillaume, makz
 */
public class CircabcUserPreferencesBean extends UserPreferencesBean
{
	private static final long serialVersionUID = -7861458606474224433L;

	public static final String BEAN_NAME = "UserPreferencesBean";

	private static final String PREF_USER_INTERFACE_LANGUAGE = UserPreferencesBean.PREF_INTERFACELANGUAGE;

	final private Map<String, Integer> userRichListPreference = new HashMap<String, Integer>(10);

	/**
	 * Purpose : set the language of the interface without changing the UserProfilePreference
	 * -> No more done by alfresco
	 *
	 * @param language The language selection to set.
	 */
	public void setLanguage(String language) 
	{
		try 
		{
			if (language != null)
			{
				super.setLanguage(language);

				Locale locale = I18NUtil.parseLocale(language);
				
				String currentUser = AuthenticationUtil.getRunAsUser();
				if (currentUser != null)
				{
					String guestUser = AuthenticationUtil.getGuestUserName();
					if  (!currentUser.equalsIgnoreCase(guestUser))
					{
						// it's not a guest user, so store the language in the user settings
						Preferences preferences = PreferencesService.getPreferences();
						if (preferences != null)
						{
							preferences.setValue(PREF_USER_INTERFACE_LANGUAGE, locale.getLanguage());
						}
					}
					else
					{
						// for guest users only change the JSF language
						FacesContext fc = FacesContext.getCurrentInstance();
						fc.getViewRoot().setLocale(locale);
					}
		 		 }
			}
		} 
		catch (Exception e) 
		{
			// TODO: log error
		}
	}

	/**
	 * @return 	If no user is logged in it returns the JSF language 
	 * 			otherwise it returns the language that is set in the user profile.
	 */
	public String getLanguage() 
	{
		String result = "en";
 		try
 		{
 			// use the JSF language if the current user is guest
 			FacesContext fc = FacesContext.getCurrentInstance();
 			result = fc.getViewRoot().getLocale().getLanguage();
 		
 			final String currentUser = AuthenticationUtil.getRunAsUser();
 			if (currentUser != null)
 			{
 				final String guestUser = AuthenticationUtil.getGuestUserName();
	 			if  (!currentUser.equalsIgnoreCase(guestUser))
	 			{
	 				// it is not the guest user, read the language from the user settings
 					final Preferences preferences = PreferencesService.getPreferences();
 					if (preferences != null)
 					{
						final Serializable userInterfaceLanguage = PreferencesService.getPreferences().getValue(PREF_USER_INTERFACE_LANGUAGE);
						if(userInterfaceLanguage != null)
						{
							if (userInterfaceLanguage instanceof Locale)
							{
								Locale local =  (Locale) userInterfaceLanguage;
								result = local.getLanguage();
							}
							else if  (userInterfaceLanguage instanceof String )
							{
								result = (String)userInterfaceLanguage;
							}
						}
 					}
	 			}
 			}
 		}
 		catch (Exception ex) 
 		{
 			// TODO: log error
 		}
 		return result;
	}

	public Map<String, Integer> getListElementPreference()
	{
		return userRichListPreference;
	}

}