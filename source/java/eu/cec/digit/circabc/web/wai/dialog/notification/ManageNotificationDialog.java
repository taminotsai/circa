/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.web.bean.repository.Node;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.notification.AuthorityNotification;
import eu.cec.digit.circabc.service.notification.NotificationSubscriptionService;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;


/**
 *	Bean that backs the "Manage Notifications" WAI page.
 *
 * @author Yanick Pignot
 */
public class ManageNotificationDialog extends BaseWaiDialog
{
	private static final long serialVersionUID = 6677774407135361466L;

	/** Logger */
	private static final Log logger = LogFactory.getLog(ManageNotificationDialog.class);

	/** Public JSF Bean name */
	public static final String BEAN_NAME = "ManageNotificationDialog";

	private transient NotificationSubscriptionService notificationSubscriptionService;
	private Boolean interestGroup;


	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(getActionNode() == null)
		{
			throw new IllegalArgumentException("A node id is a mandatory parameter");
		}

		interestGroup = null;
	}

	public Node getCurrentNode()
	{
		return getActionNode();
	}

	public boolean isCurrentNodeInterestGroup()
	{
		if(interestGroup == null)
		{
			interestGroup = Boolean.valueOf(NavigableNodeType.IG_ROOT.isNodeFromType(getActionNode()));
		}
		return interestGroup.booleanValue();
	}


	public List<NotificationWrapper> getNotifications()
	{
		final Set<AuthorityNotification> notifications = getNotificationSubscriptionService().getNotifications(getActionNode().getNodeRef());
		final List<NotificationWrapper> wrappers = new ArrayList<NotificationWrapper>(notifications.size());

		NotificationWrapper wrapper = null;

		for (final AuthorityNotification notification : notifications)
		{
			wrapper = NotificationUtils.wrappNotification(notification, getActionNode().getNodeRef());

			if(wrapper == null)
			{
				logger.error("The repository is corrupeted. A notification has been setted with a non-managed Authotity Type. Only " + AuthorityType.GROUP + "  and " + AuthorityType.USER + " are allowed. "
						+ "\n\tAuthority found: " + notification.getAuthority()
						+ "\n\tFrom type:       " + notification.getAuthorityType()
						+ "\n\tWith the status: " + notification.getNotificationStatus()
						+ "\n\tAuthority found: " + notification.getAuthorityType()
						+ "\n\tOn node:         " + getActionNode());

			}

			wrappers.add(wrapper);
		}

		if(logger.isDebugEnabled())
		{
			logger.debug(wrappers.size() + " Notification status found for the node " + getActionNode().getId()
					+ "\n\tNotifications: " + wrappers
					+ "\n\tFor node:      " + getActionNode());
		}

		return wrappers;
	}

	@Override
	public void restored()
	{
		//interestGroup = null;
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		// nothing to do
		return outcome;
	}

	public String getBrowserTitle()
	{
		return translate("notification_view_other_dialog_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("notification_view_other_dialog_icon_tooltip");
	}

	@Override
	public String getContainerTitle()
	{
		return translate("notification_view_other_dialog_title", getBestTitle(getActionNode()));
	}

	@Override
	public String getCancelButtonLabel()
	{
	      return translate("close");
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
}
