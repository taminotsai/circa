package eu.cec.digit.circabc.service.log;

import java.io.Serializable;
import java.util.Date;



public class LogRecord implements Serializable

{
	/** */
	private static final long serialVersionUID = 7838345879030553825L;

	private Long igID;
	private String igName="";
	private Long documentID;
	private String user;
	private String activity;
	private StringBuffer info = new StringBuffer() ;
	private String path;
	private String service;
	private boolean isOK;
	private Date date;

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
	 * @param documentID the documentID to set
	 */
	public void setDocumentID(Long documentID)
	{
		this.documentID = documentID;
	}
	/**
	 * @return the documentID
	 */
	public Long getDocumentID()
	{
		return documentID;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user)
	{
		this.user = user;
	}
	/**
	 * @return the user
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(String service)
	{
		this.service = service;
	}
	/**
	 * @return the service
	 */
	public String getService()
	{
		return service;
	}
	/**
	 * @param action the action to set
	 */
	public void setActivity(String actitivty)
	{
		this.activity = actitivty;
	}
	/**
	 * @return the action
	 */
	public String getActivity()
	{
		return activity;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(String info)
	{
		this.info.delete(0, this.info.length());
		this.info.append(info);
	}
	/**
	 * @return the info
	 */
	public String getInfo()
	{
		return info.toString();
	}

	/**
	 * @param info the info to set
	 */
	public void addInfo(String info)
	{
		this.info.append(" ");
		this.info.append(info);
	}
	/**
	 * @param isOK the isOK to set
	 */
	public void setOK(boolean isOK)
	{
		this.isOK = isOK;
	}
	/**
	 * @return the isOK
	 */
	public boolean isOK()
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
	/**
	 * @param igName the igName to set
	 */
	public void setIgName(String igName)
	{
		this.igName = igName;
	}
	/**
	 * @return the igName
	 */
	public String getIgName()
	{
		return igName;
	}
	/**
	 * @return the date
	 */
	public final Date getDate()
	{
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public final void setDate(Date date)
	{
		this.date = date;
	}
	@Override
	public String toString()
	{
		return "LogRecord [igID=" + this.igID + ", igName=" + this.igName + ", documentID=" + this.documentID + ", user=" + this.user + ", activity=" + this.activity + ", info=" + this.info + ", path=" + this.path + ", service=" + this.service + ", isOK=" + this.isOK + ", date=" + this.date + "]";
	}
	
}
