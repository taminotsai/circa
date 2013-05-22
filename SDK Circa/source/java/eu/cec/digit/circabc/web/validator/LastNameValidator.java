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
 * Validator for the lastName
 *
 * @author Guillaume
 */
public class LastNameValidator
{
	/** Logger */
	private static final Log logger = LogFactory.getLog(LastNameValidator.class);

	/** The minimum permit length (not tested if value equals -1) */
	protected static final int minLength = 1;

	/** The maximum permit length (not tested if value equals -1) */
	protected static final int maxLength = 50;

	/** The message if the minimum length is wrong */
	protected static final String err_minimum_length = "err_minimum_length";

	/** The message if the maximum length is wrong */
	protected static final String err_maximum_length = "err_maximum_length";

	private LastNameValidator()
	{

	}

	/**
	 * Check if the object contains a valid phone number
	 *
	 * @param lastName The string to test
	 * @throws Exception Launch an exception with the corresponding message
	 */
	public static void evaluate(String lastName) throws Exception
	{
		lastName = lastName.trim();

    	// Test the minimum length
    	if ((minLength > -1) && (lastName.length() < minLength))
    	{
    		throw new Exception(err_minimum_length);
    	}

    	// Test the minimum length
    	if ((maxLength > -1) && (lastName.length() > maxLength))
    	{
    		throw new Exception(err_maximum_length);
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

	public static String getErrorMaximumLength()
	{
		return err_maximum_length;
	}

	/**
	 * @return the maxLength
	 */
	public static int getMaxLength()
	{
		return maxLength;
	}

	/**
	 * @return the minLength
	 */
	public static int getMinLength()
	{
		return minLength;
	}
}