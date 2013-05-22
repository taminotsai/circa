/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.bean.navigation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.alfresco.web.ui.common.Utils;
import org.calendartag.util.CalendarTagUtil;

import com.google.ical.values.DateValue;

import eu.cec.digit.circabc.model.EventModel;
import eu.cec.digit.circabc.repo.event.AppointmentUtils;
import eu.cec.digit.circabc.service.event.AppointmentType;
import eu.cec.digit.circabc.service.event.EventItem;
import eu.cec.digit.circabc.service.event.EventService;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.repository.InterestGroupNode;
import eu.cec.digit.circabc.web.ui.component.UICalendar;
import eu.cec.digit.circabc.web.ui.tag.AppointmentUnit;
import eu.cec.digit.circabc.web.wai.bean.navigation.event.AppointmentUnitComparator;
import eu.cec.digit.circabc.web.wai.menu.ActionWrapper;

/**
 * Bean that backs the navigation inside the event service
 *
 * @author yanick pignot
 */
public class EventBean extends InterestGroupBean
{
    /** */
    private static final long serialVersionUID = -6967164595499111193L;

    /** Logger */
    //private static final Log logger = LogFactory.getLog(EventBean.class);

    public static final String JSP_NAME  = "event-home.jsp";
    public static final String BEAN_NAME = "EventBean";

    public static final String MSG_PAGE_TITLE = "event_home_title";
    public static final String MSG_PAGE_DESCRIPTION = "event_home_title_desc";
    public static final String MSG_PAGE_ICON_ALT = "event_home_icon_tooltip";

    public static final String MSG_PARSE_ERROR = "event_home_parse_error";

    private static final String CREATE_CHILDREN = "CreateChildren";

    private static final String ID = "id";


    private static final String EVENT_VIEW_EVENT_MEETING = "event_view_event_meeting";
    private static final String EVENT_VIEW_EVENT_MEETING_TOOLTIP = "event_view_event_meeting_tooltip";
    private static final String WAI_DIALOG_VIEW_EVENTS_MEETINGS_WAI = "wai:dialog:viewEventsMeetingWai";

    private static final String EVENT_CREATE_EVENT = "event_create_event";
    private static final String EVENT_CREATE_EVENT_TOOLTIP= "event_create_event_tooltip";
    private static final String WAI_WIZARD_CREATE_EVENT_WAI = "wai:wizard:createEventWai";

    private static final String EVENT_CREATE_MEETING = "event_create_meeting";
    private static final String EVENT_CREATE_MEETING_TOOLTIP = "event_create_meeting_tooltip";

    private static final String WAI_WIZARD_CREATE_MEETING_WAI = "wai:wizard:createMeetingWai";
    private static final String DIALOG_MANAGER_SETUP_PARAMETERS = "DialogManager.setupParameters";
    private static final String WIZARD_MANAGER_SETUP_PARAMETERS = "WizardManager.setupParameters";

    private static final String MSG_PREFIX_MODE = "event_home_view_mode_";


    private transient EventService eventService;

    private Date browseDate;
    private String viewMode;
    private String weekStart;

    /** The action to be displayed */
    private List<ActionWrapper> actions = null;

    public NavigableNodeType getManagedNodeType()
    {
        return NavigableNodeType.EVENT;
    }

    public String getRelatedJsp()
    {
        return NAVIGATION_JSP_FOLDER + JSP_NAME;
    }

    public String getPageDescription()
    {
        return translate(MSG_PAGE_DESCRIPTION);
    }

    public String getPageTitle()
    {
        return translate(MSG_PAGE_TITLE);
    }

    public String getPageIcon()
    {
        return IMAGES_ICONS + getCurrentNode().getProperties().get(ICON) + GIF;
    }

    public String getPageIconAltText()
    {
        return translate(MSG_PAGE_ICON_ALT);
    }

    @Override
    public String getBrowserTitle()
    {
        return translate(MSG_PAGE_TITLE);
    }

    @Override
    public String getWebdavUrl()
	{
    	// not relevant for calendar
		return null;
	}

    public void init(Map<String, String> parameters)
    {
        this.actions = null;
        this.browseDate = null;
        this.viewMode = null;
        this.weekStart = null;

        if(parameters != null)
        {
            String browseDateStr = parameters.get(UICalendar.PARAM_BROWSE_DATE);

            if(browseDateStr != null)
            {
                try
                {
                    browseDate = UICalendar.convert(browseDateStr);
                }
                catch (ParseException e)
                {

                }
            }
            viewMode = parameters.get(UICalendar.PARAM_VIEW_MODE);
        }

        weekStart = (String) getCurrentNode().getProperties().get(EventModel.PROP_WEEK_START_DAY.toString());

        if(weekStart == null)
        {
        	weekStart = EventModel.WEEK_START_DAY_CONSTRAINT_VALUES.get(1);
        }

        if(browseDate == null)
        {
            browseDate = GregorianCalendar.getInstance().getTime();
        }
        if(viewMode == null)
        {
        	viewMode = UICalendar.VIEW_MODE_MONTH;
        }

    }

    public Date getStartDate()
    {
        return browseDate;
    }

    public void setStartDate(Date date)
    {
        this.browseDate = date;

    }

    public String getStartDateAsString()
    {
        return UICalendar.convert(browseDate);
    }

    public void setStartDateAsString(String dateString)
    {

    	try
		{
			browseDate = UICalendar.convert(dateString);
		}
        catch (ParseException e)
		{
        	browseDate = new Date();

			Utils.addErrorMessage(translate(MSG_PARSE_ERROR, dateString));
		}
    }

    public List<SelectItem> getViewModes()
    {
    	final List<SelectItem> viewModes = new ArrayList<SelectItem>(6);

    	viewModes.add(new SelectItem(UICalendar.VIEW_MODE_DAY, translate(MSG_PREFIX_MODE + UICalendar.VIEW_MODE_DAY)));
    	viewModes.add(new SelectItem(UICalendar.VIEW_MODE_WEEK, translate(MSG_PREFIX_MODE + UICalendar.VIEW_MODE_WEEK)));
    	viewModes.add(new SelectItem(UICalendar.VIEW_MODE_MONTH, translate(MSG_PREFIX_MODE + UICalendar.VIEW_MODE_MONTH)));
    	viewModes.add(new SelectItem(UICalendar.VIEW_MODE_TRIMESTER, translate(MSG_PREFIX_MODE + UICalendar.VIEW_MODE_TRIMESTER)));
    	//viewModes.add(new SelectItem(UICalendar.VIEW_MODE_WORKWEEK, translate(MSG_PREFIX_MODE + UICalendar.VIEW_MODE_MONTH)));
    	// viewModes.add(new SelectItem(UICalendar.VIEW_MODE_AGENDA, translate(MSG_PREFIX_MODE + UICalendar.VIEW_MODE_AGENDA)));

        return viewModes;
    }

    public String getViewMode()
    {
        return this.viewMode;
    }

    public void setViewMode(String mode)
    {
        this.viewMode = mode;
    }

    public String getUserLang()
    {
    	// Workaround DIGIT-CIRCABC-1257
    	// return I18NUtil.getLocale().getLanguage();
    	return Locale.ENGLISH.getLanguage();
    }

    public String getWeekStart()
    {
    	return weekStart;
    }

    public String getPopupIcon()
    {
    	final FacesContext context = FacesContext.getCurrentInstance();
    	final String contextPath = context.getExternalContext().getRequestContextPath();

    	return contextPath + "/images/extension/icons/calendar.gif";
    }


    public Map<DateValue, List<AppointmentUnit>> getAppointmentUnitByDay(final Calendar from, final Calendar to)
	{
    	final DateValue fromDateValue = AppointmentUtils.convertDateToDateValue(from.getTime());
    	final DateValue toDateValue = AppointmentUtils.convertDateToDateValue(to.getTime());

		final List<EventItem> appointements = getEventService().getEventsBetweenDates(getCurrentNode().getNodeRef(), fromDateValue, toDateValue);

		int dateDiff = CalendarTagUtil.differenceInDays(from, to);
    	final Map<DateValue, List<AppointmentUnit>> appointmentMap = new HashMap<DateValue, List<AppointmentUnit>>(dateDiff + 1);

    	AppointmentUnit unit;
		DateValue startDay;
		List<AppointmentUnit> appointments;
		for (final EventItem item : appointements)
		{
			unit = new AppointmentUnit();

			startDay = AppointmentUtils.convertDateToDateValue(item.getDate());

			unit.setId(item.getEventNodeRef().getId());
			unit.setTitle(item.getTitle());
			unit.setType(item.getEventType().equals(AppointmentType.Event) ? "E" : "M");
			unit.setStart(item.getStartTime());
			unit.setEnd(item.getEndTime());
			unit.setStartDay(startDay);

			if(appointmentMap.containsKey(startDay))
			{
				appointmentMap.get(startDay).add(unit);
			}
			else
			{
				appointments = new ArrayList<AppointmentUnit>();
				appointments.add(unit);
				appointmentMap.put(startDay, appointments);
			}
		}

		for(Map.Entry<DateValue, List<AppointmentUnit>> entry : appointmentMap.entrySet())
		{
			Collections.sort(entry.getValue(), new AppointmentUnitComparator());
		}

		return appointmentMap;
	}


    /**
     * @return the list of available action for the current node that should be a Event root or child
     */
    public List<ActionWrapper> getActions()
    {
        if(!FacesContext.getCurrentInstance().getViewRoot().getViewId().equals(CircabcNavigationHandler.WAI_NAVIGATION_CONTAINER_PAGE))
        {
            // don't display actions when a dialog or a wizard is launched
            return null;
        }

        //return Collections.<ActionWrapper>emptyList();

        actions = new ArrayList<ActionWrapper>(3);

        final NavigableNodeType type = getNavigator().getCurrentNodeType();

        if(type.equals(NavigableNodeType.EVENT)
                || type.equals(NavigableNodeType.EVENT_CHILD))
        {

        	NavigableNode enventRoot = (NavigableNode) getNavigator().getCurrentIGRoot().get(InterestGroupNode.EVENT_SERVICE);

            actions.add(
                    new ActionWrapper(CREATE_CHILDREN,
                            translate(EVENT_VIEW_EVENT_MEETING),
                            WAI_DIALOG_VIEW_EVENTS_MEETINGS_WAI,
                            DIALOG_MANAGER_SETUP_PARAMETERS,
                            translate(EVENT_VIEW_EVENT_MEETING_TOOLTIP),
                            ID ,
                            enventRoot.getId()
                            )
                    );
            actions.add(
                    new ActionWrapper(CREATE_CHILDREN,
                            translate(EVENT_CREATE_EVENT),
                            WAI_WIZARD_CREATE_EVENT_WAI,
                            WIZARD_MANAGER_SETUP_PARAMETERS,
                            translate(EVENT_CREATE_EVENT_TOOLTIP),
                            ID ,
                            enventRoot.getId()
                            )
                    );
            actions.add(
                    new ActionWrapper(CREATE_CHILDREN,
                            translate(EVENT_CREATE_MEETING),
                            WAI_WIZARD_CREATE_MEETING_WAI,
                            WIZARD_MANAGER_SETUP_PARAMETERS,
                            translate(EVENT_CREATE_MEETING_TOOLTIP),
                            ID ,
                            enventRoot.getId()
                            )
                    );
        }

        else
        {
            actions = null;
        }
        return actions;
    }

    /**
	 * @return the eventService
	 */
	protected final EventService getEventService()
	{
		if(eventService == null)
		{
			eventService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getEventService();
		}
		return eventService;
	}

	/**
	 * @param eventService the eventService to set
	 */
	public final void setEventService(EventService eventService)
	{
		this.eventService = eventService;
	}
}
