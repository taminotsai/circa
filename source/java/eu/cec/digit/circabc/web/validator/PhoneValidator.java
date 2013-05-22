/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Validator for the phone
 *
 * @author Guillaume
 */
public class PhoneValidator
{
	/** Logger */
	private static final Log logger = LogFactory.getLog(PhoneValidator.class);

	/** The minimum permit length (not tested if value equals -1) */
	protected static final int minLength = 1;

	/** The message if the minimum length is wrong */
	protected static final String err_minimum_length = "err_minimum_length";

	private PhoneValidator()
	{

	}

	/**
	 * Check if the object contains a valid phone number
	 *
	 * @param phone The string to test
	 * @throws Exception Launch an exception with the corresponding message
	 */
	public static void evaluate(String phone) throws Exception
	{
		phone = phone.trim();

    	// Test the minimum length
    	if ((minLength > -1) && (phone.length() < minLength))
    	{
    		throw new Exception(err_minimum_length);
    	}

    	// All is good
    	if (logger.isInfoEnabled()) {
    		logger.info("All clear");
    	}
    }

	public static String getErrorMinimumLength()
	{
		return err_minimum_length;
	}
}