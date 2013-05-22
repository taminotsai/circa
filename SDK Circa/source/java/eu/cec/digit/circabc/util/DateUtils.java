package eu.cec.digit.circabc.util;

import java.util.Date;

public class DateUtils {

	private DateUtils()
	{

	}
	public static boolean isDateOlderThan(final Date date, final long milliseconds) {
		boolean older = false;
		final Date now = new Date();

		if((now.getTime() - date.getTime()) <= milliseconds) {
			older = true;
		}
		return older;
	}
}
