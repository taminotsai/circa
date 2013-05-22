/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.newsgroup;

import java.util.Date;

/**
 * Representation of an abuse in a moderation process.
 *
 * @author Yanick Pignot
 */
public interface AbuseReport
{
	/**
	 * @return			the date of the reported abuse
	 */
	public abstract Date getReportDate();

	/**
	 * @return			the user that are report the abuse
	 */
	public abstract String getReporter();

	/**
	 * @return			the message (reason of the abuse) filled by the user
	 */
	public abstract String getMessage();
}
