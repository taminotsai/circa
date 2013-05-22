package eu.cec.digit.circabc.web.wai.dialog.event;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.web.ui.common.Utils;

import eu.cec.digit.circabc.service.event.Appointment;
import eu.cec.digit.circabc.service.event.AppointmentUpdateInfo;

/**
 * Bean that back the general info edition dialog for an appointement (as well an event that a meeting)
 *
 * @author Yanick Pignot
 */
public class ModifyAppointmentInfoDialog extends AppointmentDialogBase {

	/** */
	private static final long serialVersionUID = 1122213930081721315L;

	public static final String BEAN_NAME = "ModifyAppointmentInfoDialog";

	private static final String MSG_DIALOG_TITLE_MEETING = "event_modif_information_dialog_title";
	private static final String MSG_DIALOG_TITLE_EVENT = "event_modif_information_dialog_event_title";
	private static final String MSG_DIALOG_DESC_MEETING = "event_modif_information_dialog_description";
	private static final String MSG_DIALOG_DESC_EVENT = "event_modif_information_dialog_event_description";
	private static final String MSG_DIALOG_BROWSER_TITLE = "event_modif_information_dialog_browser_title";
	private static final String MSG_DIALOG_ICON_TOOLTIP = "event_modif_information_dialog_icon_tooltip";

	@Override
    public void init(Map<String, String> parameters)
	{
		super.init(parameters);
		if(parameters != null)
		{
			setupAppointement();
		}
        this.logRecord.setService("Event");
        this.logRecord.setActivity("Modify appointment info");
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		this.validateDetails(getAppointment());

    	// if the fields are not valid, stay in the current step
    	if(FacesContext.getCurrentInstance().getMessages().hasNext())
    	{
    		isFinished = false;
    		return null;
    	}
    	else
    	{
    		getEventService().updateAppointment(getActionNode().getNodeRef(),getAppointment(), getUpdateMode(),AppointmentUpdateInfo.GeneralInformation);
    	}

    	return outcome;
	}
	@Override
	protected void validateDetails(Appointment appointment)
    {
        final String title = appointment.getTitle();

        if(title == null || title.trim().length() < 1)
        {
            Utils.addErrorMessage(translate(MSG_ERROR_TITLE_MISSING));
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
		return isMeeting() ? translate(MSG_DIALOG_TITLE_MEETING)  : translate(MSG_DIALOG_TITLE_EVENT);
	}

	public String getContainerDescription()
	{
		return isMeeting() ? translate(MSG_DIALOG_DESC_MEETING, getAppointment().getTitle())  : translate(MSG_DIALOG_DESC_EVENT, getAppointment().getTitle());
	}
}
