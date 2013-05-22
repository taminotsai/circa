package eu.cec.digit.circabc.web.wai.dialog.event;

import java.util.Map;


/**
 * Bean that back the general info edition dialog for an appointement (as well an event that a meeting)
 *
 * @Deprecated validate the functional changes before delete.
 *
 * @author Yanick Pignot
 */
@Deprecated
public class ModifyReccurenceDialog extends AppointmentDialogBase {

	/** */
	private static final long serialVersionUID = 1169143930081731955L;

	public static final String BEAN_NAME = "ModifyReccurenceDialog";

	private static final String MSG_DIALOG_TITLE_MEETING = "event_edit_recurrent_meeting_dialog_title";
	private static final String MSG_DIALOG_TITLE_EVENT = "event_edit_recurrent_event_dialog_title";
	private static final String MSG_DIALOG_DESC_MEETING = "event_edit_recurrent_meeting_dialog_description";
	private static final String MSG_DIALOG_DESC_EVENT = "event_edit_recurrent_event_dialog_description";
	private static final String MSG_DIALOG_BROWSER_TITLE = "event_edit_recurrent_meeting_dialog_browser_title";
	private static final String MSG_DIALOG_ICON_TOOLTIP = "event_edit_recurrent_meeting_dialog_icon_tooltip";

	@Override
    public void init(Map<String, String> parameters)
	{
		super.init(parameters);
        this.logRecord.setService("Event");
        this.logRecord.setActivity("Modify reccurence");
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
		return isMeeting() ? translate(MSG_DIALOG_TITLE_MEETING)  : translate(MSG_DIALOG_TITLE_EVENT);
	}

	public String getContainerDescription()
	{
		return isMeeting() ? translate(MSG_DIALOG_DESC_MEETING)  : translate(MSG_DIALOG_DESC_EVENT);
	}
}
