/**
 * 
 */
package eu.cec.digit.circabc.service.config.auto.upload;

/**
 * @author beaurpi
 *
 */
public enum AutoUploadJobResult {
	JOB_OK(1), JOB_NOTHING_TO_DO(0), JOB_ERROR(-1), JOB_REMOTE_FTP_PROBLEM(-2);
	
	private final Integer result;
	
	AutoUploadJobResult(Integer result){
		this.result=result;
	}

	/**
	 * @return the result
	 */
	public Integer getResult() {
		return result;
	}

}
