/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.notification;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.model.UserModel;


/**
 *	Bean that backs the "Edit Own Notification" WAI page.
 *
 * @author Yanick Pignot
 */
public class EditOwnNotificationDialog extends EditAuthorityNotificationDialog
{

	public static final String BEAN_NAME = "EditOwnNotificationDialog";

    /** */
    private static final long serialVersionUID = -298735948907020207L;

	private String globalStatus;
    @Override
    public void init(Map<String, String> parameters)
    {

        //prevent null pointer in restaure time
        if(parameters != null)
        {

        	parameters.put(PARAM_AUTHORITY, getNavigator().getCurrentUser().getUserName());
        	parameters.put(PARAM_STATUS, "");

        	super.init(parameters);
        }

        // init the panel.
        getNotificationStatusPanel().isPanelDisplayed();
        this.globalStatus = null;
        notificationStatus = getNotificationStatusPanel().getUserNotificationStatus();
   }

    @Override
	public String getContainerTitle()
	{
		return translate("notification_edit_own_dialog_title", getActionNode().getName());
	}

    public String getBrowserTitle()
    {
        return translate("notification_edit_own_dialog_browser_title");
    }

    public String getPageIconAltText()
    {
        return translate("notification_edit_own_dialog_icon_tooltip");
    }


    @Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
    	if(this.globalStatus != null && this.globalStatus.length() > 0)
    	{
    		final Boolean statusBool = Boolean.valueOf(this.globalStatus);

    		if(statusBool.equals(getCurrentGlobalStatus()) == false)
    		{
    			final NodeRef person = getNavigator().getCurrentUser().getPerson();
    			getNodeService().setProperty(person, UserModel.PROP_GLOBAL_NOTIFICATION, statusBool);
    		}
    	}

    	return super.finishImpl(context, outcome);
	}

    public String getGlobalNotificationStatus()
    {
    	if(globalStatus == null)
    	{
    		 this.globalStatus = getCurrentGlobalStatus().toString();
    	}
    	return globalStatus;
    }

	private Boolean getCurrentGlobalStatus()
	{
		return getNotificationStatusPanel().getNotificationReport().getGlobalNotificationStatus().toBoolean();
	}

    public void setGlobalNotificationStatus(final String status)
    {
    	this.globalStatus = status;
    }

}