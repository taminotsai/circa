package eu.cec.digit.circabc.web.ui.tag;

import org.joda.time.DateTimeFieldType;
import org.joda.time.TimeOfDay;

import com.google.ical.values.DateValue;


/**
 * Wrapper that is used to display an event unit in the Calendar Component
 *
 * @author Yanick Pignot
 */
public class AppointmentUnit
{
	private DateValue startDay;

	private TimeOfDay start;
	private TimeOfDay end;
	private String id;
	private String title;

	private String type;

	/**
	 * @return the end
	 */
	public TimeOfDay getEnd()
	{
		return end;
	}

	/**
	 * @return the end as String
	 */
	public String getEndAsString()
	{
		return getAsString(end) ;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(TimeOfDay end)
	{
		this.end = end;
	}
	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the start
	 */
	public TimeOfDay getStart()
	{
		return start;
	}

	/**
	 * @return the start
	 */
	public String getStartAsString()
	{
		return getAsString(start) ;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(TimeOfDay start)
	{
		this.start = start;
	}
	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the type
	 */
	public final String getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public final void setType(String type)
	{
		this.type = type;
	}

	public boolean isInQuarter(TimeOfDay from)
	{
		return from.equals(start) || (from.isAfter(start) && from.isBefore(end));

	}

	/**
	 * @return the startDay
	 */
	public final DateValue getStartDay()
	{
		return startDay;
	}

	/**
	 * @param startDay the startDay to set
	 */
	public final void setStartDay(DateValue startDay)
	{
		this.startDay = startDay;
	}

	private String getAsString(TimeOfDay timeOfDay)
	{
		return paddInt(timeOfDay.get(DateTimeFieldType.hourOfDay())) + ':' + paddInt(timeOfDay.get(DateTimeFieldType.minuteOfHour())) ;
	}

	private String paddInt(int i)
	{
		return ((i < 10) ? "0" : "") + i;

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AppointmentUnit other = (AppointmentUnit) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
