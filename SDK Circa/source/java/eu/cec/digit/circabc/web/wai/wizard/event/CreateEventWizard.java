/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.wizard.event;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.repo.event.EventImpl;
import eu.cec.digit.circabc.service.event.Event;
import eu.cec.digit.circabc.service.event.EventPriority;
import eu.cec.digit.circabc.service.event.EventType;
import eu.cec.digit.circabc.web.ui.repo.converter.EnumConverter;
import eu.cec.digit.circabc.web.wai.bean.navigation.event.AppointmentWebUtils;

/**
 * Bean that backs the Create event wizard
 *
 * @author yanick pignot
 */
public class CreateEventWizard extends CreateMeetingWizard
{
	private static final long serialVersionUID = 625966444708662690L;

	@Override
    public void init(Map<String, String> parameters)
    {
        super.init(parameters);
        this.logRecord.setService("Event");
        this.logRecord.setActivity("Create event");
        this.isLoggingEnabled = true;
    }

    @Override
    protected String finishImpl(FacesContext context, String outcome) throws Exception
    {
    	// the contact info are setted in the last step.
    	validateContact(getAppointment());
    	applyUserSelection();
    	validateOccurenceRate();

    	// if the fields are not valid, stay in the current step
    	if(FacesContext.getCurrentInstance().getMessages().hasNext())
    	{
    		isFinished = false;
    		return null;
    	}
    	else
    	{
    		NodeRef nodeRef = getEventService().createEvent(getActionNode().getNodeRef(), (Event) getAppointment());
			logRecord.setDocumentID((Long) getNodeService().getProperty(nodeRef, ContentModel.PROP_NODE_DBID));
    	}

    	return outcome;
    }

	public String getBrowserTitle()
	{
		return translate("event_create_event_wizard_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("event_create_event_wizard_icon_tooltip");
	}

	/**
     * @return the available types
     */
    public List<SelectItem> getEventTypes()
    {
    	return EnumConverter.convertEnumToSelectItem(
					FacesContext.getCurrentInstance(),
					EventType.values(),
					AppointmentWebUtils.MSG_PREFIX_EVENT_TYPE);
    }

    /**
     * @return the available Priorities
     */
    public List<SelectItem> getPriorities()
    {
    	return EnumConverter.convertEnumToSelectItem(
					FacesContext.getCurrentInstance(),
					EventPriority.values(),
					AppointmentWebUtils.MSG_PREFIX_PRIORITY);
    }

     protected void reset()
    {
        setAppointment(new EventImpl());
        resetFileds();
        resetAppointment(getAppointment());
    }
}
