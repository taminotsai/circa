package eu.cec.digit.circabc.repo.log;

import java.util.Date;

/**
 * @author Slobodan Filipovic
 *
 */
public class LogSearchParameterDAO
{
	private Long igID;
	private Date fromDate;
	private Date toDate;
	private String activityDescription;
	private String serviceDescription;
	private String userName;
	/**
	 * @param igID the igID to set
	 */
	public void setIgID(Long igID)
	{
		this.igID = igID;
	}
	/**
	 * @return the igID
	 */
	public Long getIgID()
	{
		return igID;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date dateFrom)
	{
		this.fromDate = dateFrom;
	}
	/**
	 * @return the fromDate
	 */
	public Date getFromDate()
	{
		return fromDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date dateTo)
	{
		this.toDate = dateTo;
	}
	/**
	 * @return the toDate
	 */
	public Date getToDate()
	{
		return toDate;
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
}
