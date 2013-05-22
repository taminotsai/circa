/**
 * 
 */
package eu.cec.digit.circabc.repo.statistics.ig;

/**
 * @author beaurpi
 *
 */
public class StatData {
	
	private String dataName;
	private Object dataValue;

	/***
	 * Simple constructor
	 */
	public StatData() {
	}
	
	/***
	 * Complete constructor
	 */
	public StatData(String name, Object value) {
		this.dataName=name;
		this.dataValue=value;
	}

	/**
	 * @return the dataName
	 */
	public String getDataName() {
		return dataName;
	}

	/**
	 * @param dataName the dataName to set
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	/**
	 * @return the dataValue
	 */
	public Object getDataValue() {
		return dataValue;
	}

	/**
	 * @param dataValue the dataValue to set
	 */
	public void setDataValue(Object dataValue) {
		this.dataValue = dataValue;
	}

}
