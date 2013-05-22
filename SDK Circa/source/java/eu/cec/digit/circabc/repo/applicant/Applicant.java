/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.repo.applicant;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.context.FacesContext;

import eu.cec.digit.circabc.business.api.user.UserDetailsBusinessSrv;
import eu.cec.digit.circabc.web.Services;

/**
 * POJO that represents a applicant
 *
 * @author Yanick Pignot
 */
public class Applicant implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String lastName;
	private String firstName;
	private String userName;
	private Date date;
	private String message;

	/**
	 * Ininitialize an applicant with all parameters
	 *
	 * @param userName				the mandatory username of the applicant which resquest to be invited to the interest group
	 * @param date					the mandatory date of the application
	 * @param message				the message of the applicant sent to the interest groups dir admins
	 * @param firstName				the first name of the applicant
	 * @param lastName				the last name of the applicant
	 */
	public Applicant(final String userName, final Date date, final String message, final String firstName, final String lastName)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.date = date;
		this.message = message;
	}

	/**
	 * Ininitialize an applicant without the optional first name and last name
	 *
	 * @param userName				the mandatory username of the applicant which resquest to be invited to the interest group
	 * @param date					the mandatory date of the application
	 * @param name					the name of the applicant
	 */
	public Applicant(final String userName, final Date date, final String message)
	{
		this(userName, date, message, null, null);
	}

	/**
	 * Ininitialize an applicant without the optional names of the applicant and the optional message
	 *
	 * @param userName				the mandatory username of the applicant which resquest to be invited to the interest group
	 * @param date					the mandatory date of the application
	 */
	public Applicant(final String userName, final Date date)
	{
		this(userName, date, null);
	}


	/**
	 * @return the date of the application
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * @param date of the application the date to set
	 */
	public void setDate(final Date date)
	{
		this.date = date;
	}

	/**
	 * @return the message of the applicant sent to the ig dir admins
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message the message of the applicant sent to the ig dir admins to set
	 */
	public void setMessage(final String message)
	{
		this.message = message;
	}

	/**
	 * @return the name of the applicant
	 */
	public String getDisplayName()
	{
		String displayName = "";
		displayName += (this.firstName == null) ? "" : this.firstName + " ";
		displayName += (this.lastName == null)  ? "" : this.lastName;

		displayName = displayName.trim();

		// if it is impossible to set a display name, get the user name
		if(displayName.length() < 1)
		{
			displayName = userName;
		}

		return this.firstName + " " + this.lastName;
	}



	/**
	 * @return the userName of the applicant
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * @param userName the userName of the applicant to set
	 */
	public void setUserName(final String userName)
	{
		this.userName = userName;
	}
	
	public String getEmail()
	{
		final UserDetailsBusinessSrv userDetServ = Services.getBusinessRegistry(FacesContext.getCurrentInstance()).getUserDetailsBusinessSrv();
		return userDetServ.getUserDetails(this.userName).getEmail();
	}	
	

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(final String lastName)
	{
		this.lastName = lastName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(getDate());

		final StringBuffer sb = new StringBuffer();

		sb.append("Application details: ")
			.append("Date=")
			.append(cal.get(Calendar.YEAR))
			.append("/")
			.append(cal.get(Calendar.MONTH))
			.append("/")
			.append(cal.get(Calendar.DAY_OF_MONTH))
			.append("|User=")
			.append(getUserName())
			.append("|Message=")
			.append(getMessage());

		return sb.toString();
	}


}
