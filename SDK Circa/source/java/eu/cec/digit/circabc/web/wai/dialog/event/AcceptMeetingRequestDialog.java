package eu.cec.digit.circabc.web.wai.dialog.event;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.ui.common.Utils;

import eu.cec.digit.circabc.service.event.MeetingRequestStatus;
import eu.cec.digit.circabc.service.event.UpdateMode;
import eu.cec.digit.circabc.web.ui.common.component.UIActionLink;


/**
 * Bean that back the Accept Meeting Processes
 *
 * @author Yanick Pignot
 */
public class AcceptMeetingRequestDialog extends AppointmentDialogBase {

	/** */
	private static final long serialVersionUID = 1100813930081721315L;

	public static final String BEAN_NAME = "AcceptMeetingRequestDialog";

	private static final String MSG_DIALOG_TITLE = "event_accept_recurrent_meeting_dialog_title";
	private static final String MSG_DIALOG_DESC = "event_accept_recurrent_meeting_dialog_description";
	private static final String MSG_DIALOG_BROWSER_TITLE = "event_accept_recurrent_meeting_dialog_browser_title";
	private static final String MSG_DIALOG_ICON_TOOLTIP = "event_accept_recurrent_meeting_dialog_icon_tooltip";

	private static final String MSG_ACCEPT_CURRENT_STATUS = "event_accept_meeting_message_current";
	private static final String MSG_ACCEPT_SPECIFIC_STATUS = "event_accept_meeting_message_specific";
	private static final String MSG_ACCEPT_CURRENT_SERIES_STATUS = "event_accept_meeting_message_specific";
	private static final String MSG_ACCEPT_SPECIFIC_SERIES_STATUS = "event_accept_meeting_series_message_specific";

	public static final String DIALOG_OUTCOME = "wai:dialog:acceptMeetingRequestWai";

	@Override
    public void init(Map<String, String> parameters)
    {
    	super.init(parameters);
        this.logRecord.setService("Event");
        this.logRecord.setActivity("Accept meeting request");
    }

	/**
	 * Action called upon completion of the Accepted Single Meeting without going into dialog
	 *
	 * @param event The action event
	 */
	public void acceptMeetingPreTreatment(ActionEvent event)
	{
		final UIComponent component = event.getComponent();
    	final Map<String, String> parameters = ((UIActionLink)component).getParameterMap();

    	this.init(parameters);

    	if(!isMeeting())
    	{
    		throw new IllegalStateException("Impossible to call this action on an event.");
    	}
    	if(!isRecurrent())
    	{
    		// refresh and click
    		getBrowseBean().refreshBrowsing();
    	}
	}

	public String getOutcome()
	{
		if(isRecurrent())
		{
			return DIALOG_OUTCOME;
		}
		else
		{
			acceptSingleMeeting();
			return null;
		}
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		if(getUpdateMode().equals(UpdateMode.Single))
		{
			acceptSingleMeeting();
		}
		else
		{
			acceptMultipleMeeting();
		}
		return outcome;
	}

	private void acceptSingleMeeting()
	{
		final NodeRef appointementNodeRef = getActionNode().getNodeRef();
		final String userName = getNavigator().getCurrentUser().getUserName();

		getEventService().setMeetingRequestStatus(appointementNodeRef, userName, MeetingRequestStatus.Accepted, UpdateMode.Single);

		if(isCurrentNode(getActionNode()))
		{
			Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_ACCEPT_CURRENT_STATUS));
		}
		else
		{
			Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_ACCEPT_SPECIFIC_STATUS, getAppointment().getTitle()));
		}
	}

	private void acceptMultipleMeeting()
	{
		final NodeRef appointementNodeRef = getActionNode().getNodeRef();
		final String userName = getNavigator().getCurrentUser().getUserName();

		getEventService().setMeetingRequestStatus(appointementNodeRef, userName, MeetingRequestStatus.Accepted, getUpdateMode());

		if(isCurrentNode(getActionNode()))
		{
			Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_ACCEPT_CURRENT_SERIES_STATUS));
		}
		else
		{
			Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_ACCEPT_SPECIFIC_SERIES_STATUS, getAppointment().getTitle()));
		}
	}

	public String getBrowserTitle()
	{
		return translate(MSG_DIALOG_BROWSER_TITLE);
	}

	public String getPageIconAltText()
	{
		return translate(MSG_DIALOG_ICON_TOOLTIP);
	}

	public String getContainerTitle()
	{
		return translate(MSG_DIALOG_TITLE);
	}

	public String getContainerDescription()
	{
		return translate(MSG_DIALOG_DESC);
	}

	private boolean isCurrentNode(Node node)
	{
		return getNavigator().getCurrentNodeId().equals(node.getId());
	}
}
