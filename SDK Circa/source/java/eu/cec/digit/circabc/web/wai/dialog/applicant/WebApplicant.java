/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.applicant;

import java.io.Serializable;
import java.util.Date;

import eu.cec.digit.circabc.repo.applicant.Applicant;
import eu.cec.digit.circabc.web.PermissionUtils;

/**
 * Web side wrapper for Applicant object encapsulation
 *
 * @author Yanick Pignot
 */
public class WebApplicant implements Serializable
{
	/** */
	private static final long serialVersionUID = -9120647048820773893L;

	private final Applicant applicant;
	private final String login;

	/*package*/ WebApplicant(final Applicant applicant)
	{
		this.applicant = applicant;
		login = PermissionUtils.computeUserLogin(applicant.getUserName());
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.repo.applicant.Applicant#getDate()
	 */
	public Date getDate()
	{
		return applicant.getDate();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.repo.applicant.Applicant#getDisplayName()
	 */
	public String getDisplayName()
	{
		return applicant.getDisplayName();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.repo.applicant.Applicant#getFirstName()
	 */
	public String getFirstName()
	{
		return applicant.getFirstName();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.repo.applicant.Applicant#getLastName()
	 */
	public String getLastName()
	{
		return applicant.getLastName();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.repo.applicant.Applicant#getMessage()
	 */
	public String getMessage()
	{
		return applicant.getMessage();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.repo.applicant.Applicant#getUserName()
	 */
	public String getUserName()
	{
		return applicant.getUserName();
	}

	/**
	 * @return the login
	 */
	public final String getLogin()
	{
		return login;
	}

	/**
	 * @return the applicant
	 */
	public final Applicant getApplicant()
	{
		return applicant;
	}
}
