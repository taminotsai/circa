package eu.cec.digit.circabc.repo.log;

/**
 * @author Slobodan Filipovic
 *
 */
public class LogActivityDAO
{
	private Integer id;
	private String activityDescription;
	private String serviceDescription;
	public LogActivityDAO()
	{

	}
	public LogActivityDAO(Integer id, String service, String activity)
	{
		this.id =  id;
		this.serviceDescription = service;
		this.activityDescription = activity;
	}

	public LogActivityDAO(String service, String activity)
	{
		this.serviceDescription = service;
		this.activityDescription = activity;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
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
}
