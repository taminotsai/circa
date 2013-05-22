package eu.cec.digit.circabc.repo.log;

import java.util.Date;


/**
 * @author Slobodan Filipovic
 *
 */
public class LogSearchResultDAO
{

	private Date logDate;
	private String activityDescription;
	private String serviceDescription;
	private String userName;
	private String info;
	private String path;
	private int isOK;
	/**
	 * @param logDate the logDate to set
	 */
	public void setLogDate(Date date)
	{
		this.logDate = date;
	}
	/**
	 * @return the logDate
	 */
	public Date getLogDate()
	{
		return logDate;
	}
	/**
	 * @param activityDescription the activityDescription to set
	 */
	public void setActivityDescription(String activityDescription)
	{
		this.activityDescription = activityDescription;
	}
	/**
	 * @return the activityDescription
	 */
	public String getActivityDescription()
	{
		return activityDescription;
	}
	/**
	 * @param serviceDescription the serviceDescription to set
	 */
	public void setServiceDescription(String serviceDescription)
	{
		this.serviceDescription = serviceDescription;
	}
	/**
	 * @return the serviceDescription
	 */
	public String getServiceDescription()
	{
		return serviceDescription;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	/**
	 * @return the userName
	 */
	public String getUserName()
	{
		return userName;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(String info)
	{
		this.info = info;
	}
	/**
	 * @return the info
	 */
	public String getInfo()
	{
		return info;
	}
	/**
	 * @param isOK the isOK to set
	 */
	public void setIsOK(int isOK)
	{
		this.isOK = isOK;
	}
	/**
	 * @return the isOK
	 */
	public int getIsOK()
	{
		return isOK;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path)
	{
		this.path = path;
	}
	/**
	 * @return the path
	 */
	public String getPath()
	{
		return path;
	}

	public String getStatus()
	{
		final String result;
		if (isOK == 1)
		{
			result = "OK";
		} else
		{
			result = "Error";
		}
		return result;
	}

}
