/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.notification;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.util.ParameterCheck;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.notification.NotificationStatus;
import eu.cec.digit.circabc.service.notification.NotificationSubscriptionService;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.wai.bean.notification.NotificationStatusPanel;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;


/**
 *	Bean that backs the "Edit Authority Notification" WAI page.
 *
 * @author Yanick Pignot
 */
public class EditAuthorityNotificationDialog extends BaseWaiDialog
{
	/** */
	private static final long serialVersionUID = -216135948938570207L;

	/** Logger */
	private static final Log logger = LogFactory.getLog(EditAuthorityNotificationDialog.class);

	protected final static String PARAM_AUTHORITY = "authority";
	protected final static String PARAM_STATUS    = "status";
	protected final static String PARAM_DISPLAY_NAME    = "displayName";

	private static final String MSG_CONFIRMATION_PROFILE = "notification_edit_other_dialog_desc_profile";
	private static final String MSG_CONFIRMATION_USER = "notification_edit_other_dialog_desc_user";

	private transient NotificationSubscriptionService notificationSubscriptionService;
	private NotificationStatusPanel notificationStatusPanel;

	protected String authority = null;
	private SelectItem[] notificationStatuses;
	private String displayName = null;

	protected String notificationStatus;
	private SelectItem[] globalNotificationStatuses;


	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		// prevent null pointer in restaure time
		if(parameters != null)
		{
			authority = parameters.get(PARAM_AUTHORITY);
			notificationStatus = parameters.get(PARAM_STATUS);
			displayName = parameters.get(PARAM_DISPLAY_NAME);
		}

		if(getActionNode() == null)
		{
			throw new IllegalArgumentException("The node id is a mandatory parameter");
		}

		if(authority == null)
		{
			throw new IllegalArgumentException("The modifiable autority is a mandatory parameter");
		}

		if(parameters != null)
		{
			notificationStatuses = null;
			globalNotificationStatuses = null;
		}

	}

	public SelectItem[] getStatuses()
	{
		if(notificationStatuses == null)
		{
			notificationStatuses = NotificationUtils.getStatusesAsSelectItem(getActionNode());
		}

		return notificationStatuses;
	}

	public SelectItem[] getGlobalStatuses()
	{
		if(globalNotificationStatuses == null)
		{
			globalNotificationStatuses = NotificationUtils.getGlobalStatusesAsSelectItem();
		}

		return globalNotificationStatuses;
	}

	/**
	 * @return the notificationStatus
	 */
	public final String getNotificationStatus()
	{
		return notificationStatus;
	}

	/**
	 * @param notificationStatus the notificationStatus to set
	 */
	public final void setNotificationStatus(String notificationStatus)
	{
		this.notificationStatus = notificationStatus;
	}

	@Override
	public String getContainerTitle()
	{
		return translate("notification_edit_other_dialog_title", getActionNode().getName());
	}


	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("Trying to modify the authority notification ... ");
		}

		ParameterCheck.mandatory("The status as string", notificationStatus);
		ParameterCheck.mandatory("The authority", authority);

		final NotificationStatus status = NotificationStatus.valueOf(notificationStatus);

		if(status == null)
		{
			throw new IllegalStateException("The value of the status seems to be corrupted. Available String values are " + Arrays.toString( NotificationStatus.values()));
		}

		if(status.equals(NotificationStatus.INHERITED) && NavigableNodeType.IG_ROOT.isNodeFromType(getActionNode()))
		{
			logger.error("The web client must not propose INHERITED status for an Interest Group ... ");

			// let the service manage the error ...
		}

		String info = MessageFormat.format("status {0} for user {1}", new Object[]{status , authority });

		logRecord.setInfo(info );
		getNotificationSubscriptionService().setNotificationStatus(getActionNode().getNodeRef(), authority, status);

		if(logger.isDebugEnabled())
		{
			logger.debug("Authority notification (" + status + ") succesffully updated for authority " + authority + " on the node " + getActionNode().getName()) ;
		}
		return outcome;
	}

	@Override
	protected String doPostCommitProcessing(FacesContext context, String outcome)
	{
		// refresh the notification report
    	getNotificationStatusPanel().reset();
		return super.doPostCommitProcessing(context, outcome);
	}

	public String getConfirmation()
	{

		switch (AuthorityType.getAuthorityType(authority))
		{
			case USER:
				return translate(MSG_CONFIRMATION_USER, displayName);

			case GROUP:
				return translate(MSG_CONFIRMATION_PROFILE, displayName);

			default :
				return null;
		}
	}

	public String getBrowserTitle()
	{
		return translate("notification_define_other_dialog_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("notification_define_other_dialog_icon_tooltip");
	}

	/**
	 * @return the notificationSubscriptionService
	 */
	protected final NotificationSubscriptionService getNotificationSubscriptionService()
	{
		if(notificationSubscriptionService == null)
		{
			notificationSubscriptionService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getNotificationSubscriptionService();
		}
		return notificationSubscriptionService;
	}

	/**
	 * @param notificationSubscriptionService the notificationSubscriptionService to set
	 */
	public final void setNotificationSubscriptionService(NotificationSubscriptionService notificationSubscriptionService)
	{
		this.notificationSubscriptionService = notificationSubscriptionService;
	}

	 /**
     * @param notificationStatusPanel the notificationStatusPanel to set
     */
    public final void setNotificationStatusPanel(NotificationStatusPanel notificationStatusPanel)
    {
        this.notificationStatusPanel = notificationStatusPanel;
    }

    /**
     * @return the notificationStatusPanel
     */
    protected final NotificationStatusPanel getNotificationStatusPanel()
    {
        if(notificationStatusPanel == null)
        {
        	notificationStatusPanel = (NotificationStatusPanel) Beans.getBean(NotificationStatusPanel.BEAN_NAME);
        }
        return notificationStatusPanel;
    }

}
