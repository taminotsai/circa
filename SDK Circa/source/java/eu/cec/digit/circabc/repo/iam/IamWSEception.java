package eu.cec.digit.circabc.repo.iam;


/**
 * @author Slobodan Filipovic 
 * Exception when calling iam web service
 *
 */
public class IamWSEception extends RuntimeException {

	public IamWSEception(String message) {
		super(message);
	}

	public IamWSEception(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6313277738682278125L;

}
