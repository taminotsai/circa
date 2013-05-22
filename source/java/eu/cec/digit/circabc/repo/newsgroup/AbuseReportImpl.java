/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.newsgroup;

import java.io.Serializable;
import java.util.Date;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.util.ParameterCheck;

import eu.cec.digit.circabc.service.newsgroup.AbuseReport;

/**
 * Concrete implementation of an abuse in a moderation process.
 *
 * @author Yanick Pignot
 */
/*package*/ class AbuseReportImpl implements AbuseReport, Serializable
{
	/** */
	private static final long serialVersionUID = -3614767880321596490L;

	private final Date reportDate;
	private final String reporter;
	private final String message;

	/**
	 * @param reportDate
	 * @param reporter
	 * @param message
	 */
	/*package*/ AbuseReportImpl(Date reportDate, String reporter, String message)
	{
		super();
		ParameterCheck.mandatory("The date", reportDate);
		ParameterCheck.mandatoryString("The reporter", reporter);

		this.reportDate = reportDate;
		this.reporter = reporter;
		this.message = message == null ? "" : message;


	}

	/**
	 * @param message
	 */
	/*package*/ AbuseReportImpl(final String message)
	{
		this(new Date(), AuthenticationUtil.getFullyAuthenticatedUser(), message);
	}

	public final String getMessage()
	{
		return message;
	}

	public final Date getReportDate()
	{
		return reportDate;
	}

	public final String getReporter()
	{
		return reporter;
	}

	@Override
	public String toString()
	{
		return "Abuse Report: [Date:" + reportDate + ", Reporter:" + reporter + ", message:" + message + "]";
	}

	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((message == null) ? 0 : message.hashCode());
		result = PRIME * result + ((reportDate == null) ? 0 : reportDate.hashCode());
		result = PRIME * result + ((reporter == null) ? 0 : reporter.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AbuseReportImpl other = (AbuseReportImpl) obj;
		if (message == null)
		{
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (reportDate == null)
		{
			if (other.reportDate != null)
				return false;
		} else if (!reportDate.equals(other.reportDate))
			return false;
		if (reporter == null)
		{
			if (other.reporter != null)
				return false;
		} else if (!reporter.equals(other.reporter))
			return false;
		return true;
	}
}
