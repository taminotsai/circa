/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.bean.navigation.event;

import eu.cec.digit.circabc.service.event.AudienceStatus;
import eu.cec.digit.circabc.service.event.EventPriority;
import eu.cec.digit.circabc.service.event.EventType;
import eu.cec.digit.circabc.service.event.EveryTimesOccurence;
import eu.cec.digit.circabc.service.event.MeetingAvailability;
import eu.cec.digit.circabc.service.event.MeetingRequestStatus;
import eu.cec.digit.circabc.service.event.MeetingType;
import eu.cec.digit.circabc.service.event.TimesOccurence;
import eu.cec.digit.circabc.web.WebClientHelper;


/**
 * Utilitary methods for Appointments
 *
 * @author yanick pignot
 */
public class AppointmentWebUtils
{
	public static final String MSG_PREFIX_MEETING_TYPE = "event_create_meetings_wizard_step1_type_";
	public static final String MSG_PREFIX_AVAILABILITY = "event_create_meetings_wizard_step1_availability_";
	public static final String MSG_PREFIX_AUDIENCE_STATUS ="event_create_meetings_wizard_step1_audience_status_";
	public static final String MSG_PREFIX_TIMES_OCCURENCE = "event_create_meetings_wizard_step1_occurs_";
	public static final String MSG_PREFIX_EVERY_TIMES_OCCURENCE = MSG_PREFIX_TIMES_OCCURENCE;
	public static final String MSG_PREFIX_REQUEST_STATUS = "event_view_meetings_details_dialog_";

	public static final String MSG_PREFIX_EVENT_TYPE = "event_create_event_wizard_step1_type_";
	public static final String MSG_PREFIX_PRIORITY = "event_create_event_wizard_step1_priority_";

	private AppointmentWebUtils(){}

	public static String translate(final EventType eventType)
	{
		return translate(eventType, MSG_PREFIX_EVENT_TYPE);
	}

	public static String translate(final MeetingType meetingType)
	{
		return translate(meetingType, MSG_PREFIX_MEETING_TYPE);
	}

	public static String translate(final MeetingAvailability  meetingAvailability)
	{
		return translate(meetingAvailability, MSG_PREFIX_AVAILABILITY);
	}

	public static String translate(final EventPriority eventPriority)
	{
		return translate(eventPriority, MSG_PREFIX_PRIORITY);
	}

	public static String translate(final AudienceStatus audienceStatus)
	{
		return translate(audienceStatus, MSG_PREFIX_AUDIENCE_STATUS);
	}

	public static String translate(final TimesOccurence timesOccurence)
	{
		return translate(timesOccurence, MSG_PREFIX_TIMES_OCCURENCE);
	}

	public static String translate(final EveryTimesOccurence everyTimesOccurence)
	{
		return translate(everyTimesOccurence, MSG_PREFIX_EVERY_TIMES_OCCURENCE);
	}

	public static String translate(final MeetingRequestStatus meetingRequestStatus)
	{
		return translate(meetingRequestStatus, MSG_PREFIX_REQUEST_STATUS);
	}


	private  static String translate(final Enum enumItem, String messagePrefix)
	{
		if(enumItem == null)
		{
			return "";
		}
		return WebClientHelper.translate(messagePrefix + enumItem.name().toLowerCase());
	}


}
