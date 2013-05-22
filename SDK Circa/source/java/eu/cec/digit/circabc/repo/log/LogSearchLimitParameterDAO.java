package eu.cec.digit.circabc.repo.log;

public class LogSearchLimitParameterDAO extends LogSearchParameterDAO
{

	private int startRecord;
	private int pageSize;
	/**
	 * @param start the start to set
	 */
	public void setStartRecord(int startRecord)
	{
		this.startRecord = startRecord;
	}
	/**
	 * @return the start
	 */
	public int getStartRecord()
	{
		return startRecord;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}
	/**
	 * @return the pageSize
	 */
	public int getPageSize()
	{
		return pageSize;
	}


}
