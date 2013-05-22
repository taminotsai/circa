/**
 *
 */
package eu.cec.digit.circabc.repo.event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.TimeOfDay;

import com.google.ical.values.DateValue;
import com.google.ical.values.DateValueImpl;


/**
 */
public abstract class AppointmentUtils
{
	private static final String GMT = "GMT";
	private static final String DATE_FORMAT = "yyyyMMdd'T'HHmmss";

	private AppointmentUtils()
	{

	}

	public static Date convertTimeOfDayToDate(TimeOfDay timeOfDay) {
		 // from Joda to JDK
	    DateTime dt = new DateTime();
	    dt =  dt.withTime(timeOfDay.getHourOfDay(),timeOfDay.getMinuteOfHour() , timeOfDay.getSecondOfMinute(), 0);

	    return  dt.toDate();
	}

	public static Date convertDateValueToDate(DateValue dateValue) {
		DateTime d =  new DateTime();
		d =  d.withDate(dateValue.year(),dateValue.month(),dateValue.day())  ;

		return d.toDate();
	}

	public static TimeOfDay convertDateToTimeOfDay( Date date) {
   	 DateTime dt = new DateTime(date);
   	 return dt.toTimeOfDay();


	}

	public static DateValue  convertDateToDateValue(Date date) {
	   DateTime dt = new DateTime(date);
	   return new DateValueImpl( dt.getYear(), dt.getMonthOfYear(),	   dt.getDayOfMonth());
	}


	public static Date convertTimeOfDayDateValueToDate(TimeOfDay timeOfDay,DateValue dateValue) {
		DateTime d =  new DateTime();
		d =  d.withDate(dateValue.year(),dateValue.month(),dateValue.day())  ;
		d =  d.withTime(timeOfDay.getHourOfDay(),timeOfDay.getMinuteOfHour() , timeOfDay.getSecondOfMinute(), 0);

	    return d.toDate();
	}

	public static String convertTimeOfDayDateValueTimezoneToGMTString(TimeOfDay timeOfDay,DateValue dateValue,String timezoneID) {


		GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone(timezoneID));

		gregorianCalendar.set(Calendar.YEAR, dateValue.year() );
		gregorianCalendar.set(Calendar.MONTH, dateValue.month() - 1 );
		gregorianCalendar.set(Calendar.DAY_OF_MONTH,dateValue.day() );
		gregorianCalendar.set(Calendar.HOUR_OF_DAY,timeOfDay.getHourOfDay());
		gregorianCalendar.set(Calendar.MINUTE,timeOfDay.getMinuteOfHour() );
		gregorianCalendar.set(Calendar.SECOND,timeOfDay.getSecondOfMinute());
		GregorianCalendar gmtCalendar = new GregorianCalendar(TimeZone.getTimeZone(GMT));
		gmtCalendar.setTimeInMillis(gregorianCalendar.getTimeInMillis());
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone(GMT));
		return sdf.format(gmtCalendar.getTime());

	}

	public static String convertTimeOfDayDateTimezoneToGMTString(TimeOfDay timeOfDay,Date date,String timezoneID) {


		GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone(timezoneID));
		gregorianCalendar.setTime(date);
		gregorianCalendar.set(Calendar.HOUR_OF_DAY,timeOfDay.getHourOfDay());
		gregorianCalendar.set(Calendar.MINUTE,timeOfDay.getMinuteOfHour() );
		gregorianCalendar.set(Calendar.SECOND,timeOfDay.getSecondOfMinute());
		GregorianCalendar gmtCalendar = new GregorianCalendar(TimeZone.getTimeZone(GMT));
		gmtCalendar.setTimeInMillis(gregorianCalendar.getTimeInMillis());
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone(GMT));
		return sdf.format(gmtCalendar.getTime());

	}


}
