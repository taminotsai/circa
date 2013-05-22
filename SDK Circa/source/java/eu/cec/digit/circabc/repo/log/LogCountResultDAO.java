/**
 * 
 */
package eu.cec.digit.circabc.repo.log;


/**
 * @author beaurpi
 *
 */
public class LogCountResultDAO {
					
	private Integer hourPeriod;
	private Integer numberOfActions;
	
	public LogCountResultDAO() {
	}

	/**
	 * @return the date
	 */
	public Integer getHourPeriod() {
		return hourPeriod;
	}

	/**
	 * @param date the date to set
	 */
	public void setHourPeriod(Integer hourPeriod) {
		this.hourPeriod = hourPeriod;
	}

	/**
	 * @return the numberOfActions
	 */
	public Integer getNumberOfActions() {
		return numberOfActions;
	}

	/**
	 * @param numberOfActions the numberOfActions to set
	 */
	public void setNumberOfActions(Integer numberOfActions) {
		this.numberOfActions = numberOfActions;
	}

}
