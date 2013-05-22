/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.app;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.alfresco.web.app.servlet.FacesHelper;

import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.wai.manager.DialogManager;
import eu.cec.digit.circabc.web.wai.manager.NavigationManager;
import eu.cec.digit.circabc.web.wai.manager.WizardManager;

/**
 * Util class to manage the WAI web client.
 *
 * @see eu.cec.digit.circabc.web.wai.manager.NavigationManager
 * @see eu.cec.digit.circabc.web.wai.bean.navigation.WaiNavigator
 * @author yanick pignot
 */
public class WaiApplication implements Serializable
{

	/** */
	private static final long serialVersionUID = -3400082766466164726L;

	public static NavigationManager getNavigationManager()
	{
		return (NavigationManager)FacesHelper.getManagedBean(FacesContext.getCurrentInstance(), NavigationManager.BEAN_NAME);
	}

	public static void setNavigationManager(NavigableNodeType type)
	{
		setNavigationManager(type.getBeanName());
	}

	public static void setNavigationManager(String beanName)
	{
		getNavigationManager().initNavigation(beanName);
	}

	public static DialogManager getDialogManager()
	{
		return (DialogManager)FacesHelper.getManagedBean(FacesContext.getCurrentInstance(), DialogManager.BEAN_NAME);
	}

	public static WizardManager getWizardManager()
	{
		return (WizardManager)FacesHelper.getManagedBean(FacesContext.getCurrentInstance(), WizardManager.BEAN_NAME);
	}

}
