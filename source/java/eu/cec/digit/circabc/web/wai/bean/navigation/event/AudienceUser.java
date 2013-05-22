package eu.cec.digit.circabc.web.wai.bean.navigation.event;

import org.apache.commons.lang.StringUtils;


/**
 * Wrapper that is used to display an single user of the audience with its status
 *
 * @author Yanick Pignot
 */
public class AudienceUser
{
	private String userFirstName;
	private String userLastName;
	private String email;
	private String status;

	/**
	 * @param userFirstName
	 * @param userLastName
	 * @param status
	 */
	public AudienceUser(String userFirstName, String userLastName, String email, String status)
	{
		super();
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.setEmail(email);
		this.status = status;
	}

	public final String getShortDisplayName()
    {
		String userName = StringUtils.isBlank(userFirstName) ? "" : userFirstName  + " ";
		userName += StringUtils.isBlank(userLastName) ? "" : userLastName;
		String email = StringUtils.isBlank(this.email) ? "" : " (" + this.email  + ")";
        return StringUtils.isBlank(userName) ? this.email : userName + email;
    }

	/**
	 * @return the status
	 */
	public final String getStatus()
	{
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public final void setStatus(String status)
	{
		this.status = status;
	}
	/**
	 * @return the userFirstName
	 */
	public final String getUserFirstName()
	{
		return userFirstName;
	}
	/**
	 * @param userFirstName the userFirstName to set
	 */
	public final void setUserFirstName(String userFirstName)
	{
		this.userFirstName = userFirstName;
	}
	/**
	 * @return the userLastName
	 */
	public final String getUserLastName()
	{
		return userLastName;
	}
	/**
	 * @param userLastName the userLastName to set
	 */
	public final void setUserLastName(String userLastName)
	{
		this.userLastName = userLastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

}
