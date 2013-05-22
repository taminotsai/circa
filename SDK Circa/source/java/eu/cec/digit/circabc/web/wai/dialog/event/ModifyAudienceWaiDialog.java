package eu.cec.digit.circabc.web.wai.dialog.event;

import java.util.Map;

import javax.faces.context.FacesContext;

import eu.cec.digit.circabc.service.event.AppointmentUpdateInfo;
import eu.cec.digit.circabc.service.event.AudienceStatus;


/**
 * Bean that back the audience edition dialog for an appointement (as well an event that a meeting)
 *
 * @author Yanick Pignot
 */
public class ModifyAudienceWaiDialog extends AppointmentDialogBase {

    /** */
    private static final long serialVersionUID = 1333813930081721315L;

    public static final String BEAN_NAME = "ModifyAudienceWaiDialog";

    private static final String MSG_DIALOG_TITLE_MEETING = "event_modif_audience_dialog_title";
    private static final String MSG_DIALOG_TITLE_EVENT = "event_modif_audience_dialog_event_title";

    private static final String MSG_DIALOG_DESC_MEETING = "event_modif_audience_dialog_description";
    private static final String MSG_DIALOG_DESC_EVENT = "event_modif_audience_dialog_event_description";

    private static final String MSG_DIALOG_BROWSER_TITLE = "event_modif_audience_dialog_browser_title";
    private static final String MSG_DIALOG_ICON_TOOLTIP = "event_modif_audience_dialog_icon_tooltip";

    @Override
    public void init(Map<String, String> parameters)
    {
        super.init(parameters);
        if(parameters != null)
        {
            setupAppointement();
        }
        this.logRecord.setService("Event");
        this.logRecord.setActivity("Modify audience");
    }

    @Override
    protected String finishImpl(FacesContext context, String outcome) throws Exception
    {
        validateAudience(getAppointment());
        applyUserSelection();

        // if the fields are not valid, stay in the current step
        if(FacesContext.getCurrentInstance().getMessages().hasNext())
        {
            isFinished = false;
            return null;
        }
        else
        {
            getEventService().updateAppointment(getActionNode().getNodeRef(),getAppointment() , getUpdateMode(),AppointmentUpdateInfo.Audience);
        }

        return outcome;
    }

    public AudienceStatus getAudienceStatus()
    {
        return getAppointment().getAudienceStatus();
    }

    public void setAudienceStatus(AudienceStatus audienceStatus)
    {
		// jsf should not pass a null object in a submit. But can in a refresh of the page.
        if(audienceStatus != null)
        {
        	getAppointment().setAudienceStatus(audienceStatus);
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
