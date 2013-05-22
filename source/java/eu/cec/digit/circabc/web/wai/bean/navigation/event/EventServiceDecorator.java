/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.bean.navigation.event;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.ical.values.DateValue;

import eu.cec.digit.circabc.repo.event.AppointmentUtils;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.ui.tag.AppointmentUnit;
import eu.cec.digit.circabc.web.ui.tag.JSFCalendarDecorator;
import eu.cec.digit.circabc.web.wai.bean.navigation.EventBean;

/**
 * @author James Smith
 * @see org.calendartag.tags.CalendarTag org.calendartag.decorators.CalendarDecorator
 * The default implementation of <code>CalendarDecorator</code> used when a decorator is not defined.
 * @since Aug 23, 2004
 */
public class EventServiceDecorator extends JSFCalendarDecorator
{
	private EventBean eventBean;
	private Map<DateValue, List<AppointmentUnit>> appointmentsOfRange;


	public List<AppointmentUnit> getAppointments()
    {
		if(appointmentsOfRange == null)
		{
			appointmentsOfRange = getEventBean().getAppointmentUnitByDay(start, end);
		}

		List<AppointmentUnit> appointments = appointmentsOfRange.get(AppointmentUtils.convertDateToDateValue(calendar.getTime()));

		// return the appointments of the day.
		return appointments == null ? Collections.<AppointmentUnit>emptyList() : appointments;
	}

	private EventBean getEventBean()
	{
		if(eventBean == null)
		{
			eventBean = (EventBean) Beans.getBean(EventBean.BEAN_NAME);
		}

		return eventBean;
	}

	public void initializeCalendar()
    {
		// release cache the events of the current period
		appointmentsOfRange = null;
    }

}

