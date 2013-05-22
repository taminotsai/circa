package eu.cec.digit.circabc.repo.log;

import java.util.Date;

/**
 * @author Slobodan Filipovic
 *
 */
public class LogRecordDAO
{
	private static final int MAX_PATH_SIZE_IN_BYTES = 4000;
	private static final int MAX_INFO_SIZE_IN_BYTES = 4000;
	private static final int MAX_USER_NAME_SIZE_IN_BYTES = 50;
	private static final int MAX_IG_NAME_SIZE_IN_BYTES = 256;
	private long id; // internal DB-generated sequence id
	private long igID;
	private String igName;
	private long documentID;
	private String user;
	private Date date;
	private int activityID;
	private String info;
	private String path;
	private int isOK;
	/**
	 * @param igID the igID to set
	 */
	public void setIgID(long igID)
	{
		this.igID = igID;
	}
	/**
	 * @return the igID
	 */
	public long getIgID()
	{
		return igID;
	}
	/**
	 * @param documentID the documentID to set
	 */
	public void setDocumentID(long documentID)
	{
		this.documentID = documentID;
	}
	/**
	 * @return the documentID
	 */
	public long getDocumentID()
	{
		return documentID;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user)
	{
		this.user = getTruncatedString( user,MAX_USER_NAME_SIZE_IN_BYTES) ;
	}
	/**
	 * @return the user
	 */
	public String getUser()
	{
		return user;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}
	/**
	 * @return the date
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * @param action the action to set
	 */
	public void setActivityID(int actitivty)
	{
		this.activityID = actitivty;
	}
	/**
	 * @return the action
	 */
	public int getActivityID()
	{
		return activityID;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(String info)
	{
		this.info = getTruncatedString(info, MAX_INFO_SIZE_IN_BYTES );
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
	 * @param id the id to set
	 */
	public void setId(long id)
	{
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path)
	{
		this.path = getTruncatedString(path,MAX_PATH_SIZE_IN_BYTES);
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
		this.igName = getTruncatedString(igName,MAX_IG_NAME_SIZE_IN_BYTES);
	}
	/**
	 * @return the igName
	 */
	public String getIgName()
	{
		return igName;
	}

	/**
	 * Truncate string to maxSizeInBytes bytes
	 * @param value String to truncate
	 * @param maxSizeInBytes
	 * @return
	 */
	private static  String getTruncatedString(String value ,int maxSizeInBytes)
	{
		if (value == null)
		{
			return null;
		}
		final String result ;
		final byte[] bytes = value.getBytes();
		if(bytes.length > maxSizeInBytes)
		{
			final byte[] destbytes = new byte [maxSizeInBytes];
			System.arraycopy(bytes, 0, destbytes, 0, maxSizeInBytes);
			result = new String(destbytes);
		}
		else
		{
		    result = value;
		}
		return result;
	}


}
