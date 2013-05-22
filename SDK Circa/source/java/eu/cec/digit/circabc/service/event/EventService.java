package eu.cec.digit.circabc.service.event;

import java.util.List;

import org.alfresco.service.Auditable;
import org.alfresco.service.cmr.repository.NodeRef;

import com.google.ical.values.DateValue;

/**
 * @author Slobodan Filipovic
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * Commented the key parameter of the @Auditable annotation.
 * Commented the deprecated @PublicService annotation. 
 */
//@PublicService
public interface EventService
{

	/**
	 * Create circabc event
	 * 
	 * @param eventRoot
	 *            root event NodeRef of interest group
	 * @param event
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "eventRoot", "event" })
	public NodeRef createEvent(final NodeRef eventRoot, final Event event);

	/**
	 * Crete circabc event
	 * 
	 * @param eventRoot
	 *            root event NodeRef of interest group
	 * @param event
	 * @param enableMailSending
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "eventRoot", "event", "enableMailSending" })
	public NodeRef createEvent(final NodeRef eventRoot, final Event event, final boolean enableMailSending);

	/**
	 * Create circabc meeting
	 * 
	 * @param eventRoot
	 * @param meeting
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "eventRoot", "meeting" })
	public NodeRef createMeeting(final NodeRef eventRoot, final Meeting meeting);

	/**
	 * Crete circabc meeting
	 * 
	 * @param eventRoot
	 * @param meeting
	 * @param enableMailSending
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "eventRoot", "meeting", "enableMailSending" })
	public NodeRef createMeeting(final NodeRef eventRoot, final Meeting meeting, final boolean enableMailSending);

	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "eventRoot" })
	@Deprecated
	public void deleteAllEvents(final NodeRef eventRoot);

	/**
	 * Delete appintment(event or meeting) depending of mode parameter it can
	 * delete only one , future ,or all instances of appointmen in wich
	 * appointmentNodeRef bellongs
	 * 
	 * @param appointmentNodeRef
	 * @param mode
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "appointmentNodeRef", "mode" })
	public void deleteAppointment(final NodeRef appointmentNodeRef, final UpdateMode mode);

	/**
	 * Get all appointments for event root
	 * 
	 * @param eventRoot
	 *            root event NodeRef of interest group
	 * 
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "eventRoot" })
	public List<Appointment> getAllAppointments(final NodeRef eventRoot);

	/**
	 * @param filter
	 * @return
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "filter", "appointementNodeRef" })
	public List<EventItem> getAllOccurences(final NodeRef appointementNodeRef);

	/**
	 * @param eventItemId
	 * @return
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "eventItemId" })
	public Appointment getAppointmentByNodeRef(final NodeRef eventItemId);

	/**
	 * @param filter
	 * @param eventRoot
	 * @param userName
	 * @param date
	 * @return
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "filter", "eventRoot", "userName", "date" })
	public List<EventItem> getAppointments(final EventFilter filter, final NodeRef eventRoot, final String userName,
			final DateValue date);

	/**
	 * @param eventRoot
	 * @param date
	 * @return
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "eventRoot", "date" })
	public List<EventItem> getCalendarEventsOnDate(final NodeRef eventRoot, final DateValue date);

	/**
	 * @param eventRoot
	 * @param from
	 * @param to
	 * @return
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "eventRoot", "from", "to" })
	public List<EventItem> getEventsBetweenDates(final NodeRef eventRoot, final DateValue from, final DateValue to);

	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "meetingDefinitionNodeRef", "recurrenceId" })
	public NodeRef getMeetingNodeRef(final NodeRef meetingDefinitionNodeRef, final String recurrenceId);

	/**
	 * @param eventItemId
	 * @param userName
	 * @return
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "eventItemId", "userName" })
	public MeetingRequestStatus getMeetingStatus(final NodeRef eventItemId, final String userName);

	/**
	 * Set Meeting request status
	 * 
	 * @param meetingNodeRef
	 * @param userName
	 * @param meetingRequestStatus
	 * @param mode
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "meetingNodeRef", "userName", "meetingRequestStatus", "mode" })
	public void setMeetingRequestStatus(final NodeRef meetingNodeRef, final String userName,
			final MeetingRequestStatus meetingRequestStatus, final UpdateMode mode);

	/**
	 * @param appointmentNodeRef
	 * @param appointment
	 * @param mode
	 * @param updateInfo
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "appointmentNodeRef", "appointment", "mode", "updateInfo" })
	public void updateAppointment(final NodeRef appointmentNodeRef, final Appointment appointment, final UpdateMode mode,
			final AppointmentUpdateInfo updateInfo);

}