/**
 *
 */
package eu.cec.digit.circabc.repo.admin.debug;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.extensions.surf.util.I18NUtil;

/**
 * @author Yanick Pignot
 */
public abstract class ReportUtils
{
	private ReportUtils()
	{

	}
	private static final ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return DateFormat.getDateTimeInstance(
					DateFormat.SHORT,
					DateFormat.SHORT,
					I18NUtil.getLocale());
		}
	};


	public static final String DEFAULT_GROUP_NAME = "DEFAULT";


	public static String getDisplayName(String group, String name)
	{
		if(DEFAULT_GROUP_NAME.equals(group))
		{
			return name;
		}
		else
		{
			return group + "." + name;
		}
	}

	public static String getSecuredString(Object object)
	{
		return getSecuredString(object, "N/A");
	}

	public static String getSecuredString(Object object, String replacementString)
	{
		if(object == null)
		{
			return replacementString;
		}
		else if(object instanceof Date)
		{
			Date date = (Date) object;

			return dateFormat.get().format(date);
		}
		else
		{
			return object.toString();
		}
	}

}
