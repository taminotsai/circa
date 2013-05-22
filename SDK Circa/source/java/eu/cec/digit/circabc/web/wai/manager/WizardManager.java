/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.manager;

import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import org.alfresco.web.app.Application;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.bean.wizard.IWizardBean;
import org.alfresco.web.bean.wizard.WizardState;
import org.alfresco.web.config.WizardsConfigElement.WizardConfig;
import org.alfresco.web.ui.common.component.UIListItem;

import eu.cec.digit.circabc.web.wai.app.WaiApplication;
import eu.cec.digit.circabc.web.wai.wizard.WaiWizard;

/**
 * Util class to manage a WAI wizard inside the wizard framework. It extends the original WizardManager from Alfresco.
 *
 * @see org.alfresco.web.bean.dialog.DialogManager
 *
 * @author yanick pignot
 */
public class WizardManager
{
	public static final String BEAN_NAME = "WaiWizardManager";

	public static final String CANCEL_FROM_ACTION = "#{" + BEAN_NAME + ".cancel}";

	private org.alfresco.web.bean.wizard.WizardManager nativeManager = null;

	public WizardManager()
	{
		nativeManager = Application.getWizardManager();;
	}

	/**
	 * @return the casted wizard dialog bean
	 */
	public WaiWizard getWaiBean()
	{
		return (WaiWizard) nativeManager.getBean();
	}

	/**
	 * @return
	 **/
	public String getIconAlt()
	{
		return getWaiBean().getPageIconAltText();
	}

	/**
	 * @return
	 */
	public String getBrowserTitle()
	{
		return getWaiBean().getBrowserTitle();
	}

	public boolean isVisibleActions()
    {
        return getActionsList() != null;
    }

    public ActionsListWrapper getActionsList()
    {
        return getWaiBean().getActionList();
    }

    public boolean isNavigationVisible()
    {
        return getNavigation() != null;
    }

    public List<Node> getNavigation()
    {
        return WaiApplication.getNavigationManager().getNavigation();
    }


    public boolean isIconVisible()
    {
        return getIcon() != null;
    }

	/**
     * @return
     * @see org.alfresco.web.bean.wizard.WizardManager#finish()
     */
    public String finish(ActionEvent action)
    {
        return this.finish();
    }


    public static boolean areStatesEquals(WizardState state, Object obj)
	{
		if(state == null || obj == null || !(obj instanceof WizardState))
		{
			return false;
		}

		WizardState state2 = (WizardState) obj;

		return state.getConfig().getName().equals(state2.getConfig().getName());
	}



	////// Delegate methods

	/**
	 *
	 * @see org.alfresco.web.bean.wizard.WizardManager#back()
	 */
	public void back()
	{
		nativeManager.back();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#cancel()
	 */
	public String cancel()
	{
		return nativeManager.cancel();
	}

	
	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#finish()
	 */
	@SuppressWarnings("unchecked")
	public String finish()
	{
		final String finishOutcome = nativeManager.finish();

        return finishOutcome;
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getBackButtonDisabled()
	 */
	public boolean getBackButtonDisabled()
	{
		return nativeManager.getBackButtonDisabled();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getBackButtonLabel()
	 */
	public String getBackButtonLabel()
	{
		return nativeManager.getBackButtonLabel();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getBean()
	 */
	public IWizardBean getBean()
	{
		return nativeManager.getBean();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getCancelButtonLabel()
	 */
	public String getCancelButtonLabel()
	{
		return nativeManager.getCancelButtonLabel();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getCurrentStep()
	 */
	public int getCurrentStep()
	{
		return nativeManager.getCurrentStep();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getCurrentStepAsString()
	 */
	public String getCurrentStepAsString()
	{
		return nativeManager.getCurrentStepAsString();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getCurrentStepName()
	 */
	public String getCurrentStepName()
	{
		return nativeManager.getCurrentStepName();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getCurrentWizard()
	 */
	public WizardConfig getCurrentWizard()
	{
		return nativeManager.getCurrentWizard();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getDescription()
	 */
	public String getDescription()
	{
		return nativeManager.getDescription();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getErrorMessage()
	 */
	public String getErrorMessage()
	{
		return nativeManager.getErrorMessage();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getFinishButtonDisabled()
	 */
	public boolean getFinishButtonDisabled()
	{
		return nativeManager.getFinishButtonDisabled();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getFinishButtonLabel()
	 */
	public String getFinishButtonLabel()
	{
		return nativeManager.getFinishButtonLabel();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getIcon()
	 */
	public String getIcon()
	{
		return nativeManager.getIcon();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getNextButtonDisabled()
	 */
	public boolean getNextButtonDisabled()
	{
		return nativeManager.getNextButtonDisabled();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getNextButtonLabel()
	 */
	public String getNextButtonLabel()
	{
		return nativeManager.getNextButtonLabel();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getPage()
	 */
	public String getPage()
	{
		return nativeManager.getPage();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getState()
	 */
	public WizardState getState()
	{
		return nativeManager.getState();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getStepDescription()
	 */
	public String getStepDescription()
	{
		return nativeManager.getStepDescription();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getStepInstructions()
	 */
	public String getStepInstructions()
	{
		return nativeManager.getStepInstructions();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getStepItems()
	 */
	public List<UIListItem> getStepItems()
	{
		return nativeManager.getStepItems();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getStepTitle()
	 */
	public String getStepTitle()
	{
		return nativeManager.getStepTitle();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.wizard.WizardManager#getTitle()
	 */
	public String getTitle()
	{
		return nativeManager.getTitle();
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return nativeManager.hashCode();
	}

	/**
	 *
	 * @see org.alfresco.web.bean.wizard.WizardManager#next()
	 */
	public void next()
	{
		nativeManager.next();
	}

	/**
	 * @param state
	 * @see org.alfresco.web.bean.wizard.WizardManager#restoreState(org.alfresco.web.bean.wizard.WizardState)
	 */
	public void restoreState(WizardState state)
	{
		nativeManager.restoreState(state);
	}

	/**
	 * @param config
	 * @see org.alfresco.web.bean.wizard.WizardManager#setCurrentWizard(org.alfresco.web.config.WizardsConfigElement.WizardConfig)
	 */
	public void setCurrentWizard(WizardConfig config)
	{
		nativeManager.setCurrentWizard(config);
	}

	/**
	 * @param event
	 * @see org.alfresco.web.bean.wizard.WizardManager#setupParameters(javax.faces.event.ActionEvent)
	 */
	public void setupParameters(ActionEvent event)
	{
		nativeManager.setupParameters(event);
	}

	/**
	 * @param arg0
	 * @see org.alfresco.web.bean.wizard.WizardManager#setupParameters(java.util.Map)
	 */
	public void setupParameters(Map<String, String> arg0)
	{
		nativeManager.setupParameters(arg0);
	}

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return nativeManager.toString();
	}



}
