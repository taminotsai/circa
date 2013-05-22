package eu.cec.digit.circabc.web.wai.dialog.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.repo.event.AppointmentUtils;
import eu.cec.digit.circabc.service.event.EventFilter;
import eu.cec.digit.circabc.service.event.EventItem;
import eu.cec.digit.circabc.service.event.EventService;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * Bean that back appoitments (Meetings and Events) search and display page.
 *
 * @author Yanick Pignot
 */
public class ViewAppointments extends BaseWaiDialog{

	private static final String PARAM_IG_SELECT = "igSelection";

	private static final String SELECT_CHOICE_IG_THIS = "ig";
	private static final String SELECT_CHOICE_IG_ALL = "all";

	private static final String SELECT_CHOICE_DATE_EXACT = "exact";
	private static final String SELECT_CHOICE_DATE_PREVIOUS = "previous";
	private static final String SELECT_CHOICE_DATE_FUTURE = "future";

	/** */
	private static final long serialVersionUID = -2599920262997080980L;

	public static final String BEAN_NAME = "ViewAppointments";

	/** The name of this dialog */
	public static final String DIALOG_NAME = "viewEventsMeetingWai";

	transient private EventService eventService;

	private Date exactDate= new Date();
	private String periodSelection = SELECT_CHOICE_DATE_FUTURE;
	private String interestGroupSelection = SELECT_CHOICE_IG_ALL;
	transient private  List<EventItem> appointments ;
	private String userName=getNavigator().getCurrentAlfrescoUserName();




	public String getBrowserTitle()
	{
		return translate("event_view_appointments_dialog_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("event_view_appointments_dialog_icon_tooltip");
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

	public boolean isCurrentIgDisabled()
	{
		return getNavigator().getCurrentIGRoot() == null;
	}



	/**
	 * Search appointments for current user,
	 *  between two dates, for one or all interest group
	 *
	 */
	public void search()
	{
		if (this.periodSelection == null)
		{
			init(null);
		}

		EventFilter filter ;
		// this.exactDate
		if (this.periodSelection.equalsIgnoreCase(SELECT_CHOICE_DATE_FUTURE))
		{
			filter = EventFilter.Future;
		}
		else if (this.periodSelection.equalsIgnoreCase(SELECT_CHOICE_DATE_PREVIOUS))
		{
			filter = EventFilter.Previous;

		}
		else if (this.periodSelection.equalsIgnoreCase(SELECT_CHOICE_DATE_EXACT))
		{
			filter = EventFilter.Exact;

		}
		else
		{
			throw new IllegalStateException("Unknown filter : " +  this.periodSelection);

		}

		NodeRef eventRoot = null;
		if (this.interestGroupSelection.equals(SELECT_CHOICE_IG_ALL) || getActionNode() == null)
		{
			eventRoot = null;
		}
		else if (this.interestGroupSelection.equalsIgnoreCase(SELECT_CHOICE_IG_THIS))
		{
			eventRoot = getActionNode().getNodeRef();
		}
		else
		{
			throw new IllegalStateException("Unknown interest group selector : " +  this.interestGroupSelection);
		}
		this.appointments = getEventService().getAppointments(filter, eventRoot, userName,AppointmentUtils.convertDateToDateValue(exactDate) );

	}


	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		String igPreSelect = null;

		if(parameters != null)
		{
			igPreSelect = parameters.get(PARAM_IG_SELECT);

			// user comme the left menu action
			this.userName = null;
			this.exactDate = null;
			this.periodSelection = null;
			this.interestGroupSelection = null;
			this.appointments= null;
		}
		else
		{
			// the user click on search
		}

		// user comme the left menu action
		if(userName == null)
		{
			this.userName = getNavigator().getCurrentAlfrescoUserName();
		}
		if(exactDate == null)
		{
			this.exactDate = new Date();
		}
		if(periodSelection == null)
		{
			this.periodSelection = SELECT_CHOICE_DATE_FUTURE;
		}
		if(interestGroupSelection == null)
		{
			if(igPreSelect == null)
			{
				this.interestGroupSelection = (isCurrentIgDisabled()) ? SELECT_CHOICE_IG_ALL : SELECT_CHOICE_IG_THIS ;
			}
			else
			{
				this.interestGroupSelection = igPreSelect;
			}
		}
		if(exactDate == null)
		{
			this.appointments= new ArrayList<EventItem>();
		}


	}

	/**
	 * @param exactDate the exactDate to set
	 */
	public void setExactDate(Date exactDate) {
		this.exactDate = exactDate;
	}

	/**
	 * @return the exactDate
	 */
	public Date getExactDate() {
		return exactDate;
	}

	/**
	 * @param periodSelection the periodSelection to set
	 */
	public void setPeriodSelection(String periodSelection) {
		this.periodSelection = periodSelection;
	}

	/**
	 * @return the periodSelection
	 */
	public String getPeriodSelection() {
		return periodSelection;
	}

	/**
	 * @param interestGroupSelection the interestGroupSelection to set
	 */
	public void setInterestGroupSelection(String interestGroupSelection) {
		this.interestGroupSelection = interestGroupSelection;
	}

	/**
	 * @return the interestGroupSelection
	 */
	public String getInterestGroupSelection() {
		return interestGroupSelection;
	}

	/**
	 * @param appointments the appointments to set
	 */
	public void setAppointments(List<EventItem> appointments) {
		this.appointments = appointments;
	}

	/**
	 * @return the appointments
	 */
	public List<EventItem> getAppointments() {
		search();
		return appointments;
	}
	@Override
	public String getCancelButtonLabel()
	{
	   return translate("close");
	}
	@Override
	public String getFinishButtonLabel()
	{
	   return translate("view_appointments_search_button_label");
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{

		// stay in the same page
		return CircabcNavigationHandler.CLOSE_WAI_DIALOG_OUTCOME +
				CircabcNavigationHandler.OUTCOME_SEPARATOR +
				CircabcNavigationHandler.WAI_DIALOG_PREFIX + DIALOG_NAME;

	}



}
