/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.tag;

import java.util.Calendar;
import java.util.List;

import javax.faces.context.FacesContext;

import org.alfresco.web.app.Application;
import org.apache.myfaces.shared_impl.renderkit.html.HTML;
import org.calendartag.decorator.DefaultCalendarDecorator;

/**
 * @author James Smith
 * @see org.calendartag.tags.CalendarTag org.calendartag.decorators.CalendarDecorator
 * The default implementation of <code>CalendarDecorator</code> used when a decorator is not defined.
 * @since Aug 23, 2004
 */
public class JSFCalendarDecorator extends DefaultCalendarDecorator
{
	protected FacesContext fc;

	/**
     *
     * @see org.calendartag.decorator.CalendarDecorator#initializeCalendar()
     */
    public  JSFCalendarDecorator() {
    	 days =  new String []  {"", "sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
    	 months = new String [] {"january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"};
    }

	/**
     * Returns empty content
     * @see org.calendartag.decorator.CalendarDecorator#getEmptyDay()
     */
    public String getEmptyDay() {
        return getDay(null);
    }

    /**
     * Returns the calendar's title using the month names and years.  It uses the protected months
     * array to generate text for each month indexed from 0 to 11
     * @see org.calendartag.decorator.CalendarDecorator#getCalendarTitle()
     */
    public String getCalendarTitle() {

        if (start.get(Calendar.MONTH) == end.get(Calendar.MONTH) &&
                start.get(Calendar.YEAR) == end.get(Calendar.YEAR)) {
            return Application.getMessage(fc, months[calendar.get(Calendar.MONTH)]) + " " + calendar.get(Calendar.YEAR);
        } else {
            if (start.get(Calendar.YEAR) == end.get(Calendar.YEAR)) {
                return Application.getMessage(fc, months[start.get(Calendar.MONTH)]) + " - " +
                		Application.getMessage(fc, months[end.get(Calendar.MONTH)]) + " " +
                        calendar.get(Calendar.YEAR);
            } else {
                return Application.getMessage(fc, months[start.get(Calendar.MONTH)]) + " " +
                        start.get(Calendar.YEAR) + " - " +
                        Application.getMessage(fc, months[end.get(Calendar.MONTH)]) + " " +
                        calendar.get(Calendar.YEAR);
            }
        }

    }

    /**
     * Returns a string representing the day of the month.  If the calendar's start and end range span
     * multiple months it also returns the month of the year.  The date is a hyperlink using the url passed
     * @see org.calendartag.decorator.CalendarDecorator#getDay(java.lang.String)
     */
    public String getDay(String url) {
        if (calendar.get(Calendar.DATE) != 1 ||
                (start.get(Calendar.MONTH) == end.get(Calendar.MONTH) &&
                start.get(Calendar.YEAR) == end.get(Calendar.YEAR))) {
            return "" + calendar.get(Calendar.DATE) ;
        } else {
            return "" + calendar.get(Calendar.DATE) + ""
            		+ HTML.NBSP_ENTITY + "<i>" + Application.getMessage(fc, months[calendar.get(Calendar.MONTH)]) + "</i>";
        }
    }

    public String getWeekdayTitle(int day) {
	       return Application.getMessage(fc, days[day]);
	}

    public List<AppointmentUnit> getAppointments()
    {
    	return null;
    }

	/**
	 * @param faceContext the faceContext to set
	 */
	public void setFacesContext(FacesContext facesContext)
	{
		this.fc = facesContext;
	}
}

