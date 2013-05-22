package eu.cec.digit.circabc.web.wai.dialog.notification;

import java.util.Map;

import javax.faces.context.FacesContext;

import eu.cec.digit.circabc.service.notification.NotificationManagerService;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

public class NotificationPasteDialog extends BaseWaiDialog
{

	private NotificationManagerService notificationManagerService; 
	/**
	 * 
	 */
	private static final long serialVersionUID = -2922334756583014210L;

	private boolean notifyPasteAll;
	private boolean notifyPaste;
	
	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);
		setNotifyPasteAll(getNotificationManagerService().isPasteAllNotificationEnabled(getActionNode().getNodeRef()));
		setNotifyPaste(getNotificationManagerService().isPasteNotificationEnabled(getActionNode().getNodeRef()));
	}
	
	
	public String getPageIconAltText()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getBrowserTitle()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Throwable
	{
		getNotificationManagerService().setPasteAllNotificationEnabled(getActionNode().getNodeRef(),isNotifyPasteAll() );
		getNotificationManagerService().setPasteNotificationEnabled(getActionNode().getNodeRef(),isNotifyPaste() );
		return outcome;
	}

	public void setNotificationManagerService(NotificationManagerService notificationManagerService)
	{
		this.notificationManagerService = notificationManagerService;
	}

	public NotificationManagerService getNotificationManagerService()
	{
		return notificationManagerService;
	}


	public void setNotifyPasteAll(boolean notifyPasteAll)
	{
		this.notifyPasteAll = notifyPasteAll;
	}


	public boolean isNotifyPasteAll()
	{
		return notifyPasteAll;
	}


	public void setNotifyPaste(boolean notifyPaste)
	{
		this.notifyPaste = notifyPaste;
	}


	public boolean isNotifyPaste()
	{
		return notifyPaste;
	}


}
