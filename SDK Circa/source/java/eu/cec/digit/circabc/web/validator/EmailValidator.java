/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.validator;

import javax.mail.internet.AddressException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.user.UserService;


/**
 * email validator based on RFC 2822.
 *
 * original code by: http://www.leshazlewood.com/ "Les"
 *
 * @author patrice.coppens@trasys.lu
 */
public class EmailValidator
{
	/** The message if the email is invalidg */
	protected static final String err_invalid = "err_invalid";

	/** The message if the email is duplicate */
	public static final String err_duplicate = "err_duplicate";

	/** Logger */
	private static final Log logger = LogFactory.getLog(EmailValidator.class);

	private EmailValidator()
	{

	}

	/**
	 * Check if the object contains a valid email address
	 *
	 * @param userEnteredEmailString The string to test
	 * @throws Exception Launch an exception with the corresponding message
	 */
	public static void evaluate(String userEnteredEmailString) throws Exception
	{
		// Test the minimum length
    	if (!isValid(userEnteredEmailString))
    	{
    		throw new Exception(err_invalid);
    	}

    	// All is good
    	if (logger.isInfoEnabled()) {
    		logger.info("All clear");
    	}
    }

	/**
	 * Check if the email is unique before its creation
	 *
	 * @param UserService	the circabc user service reference
	 * @param userEnteredEmailString				the email that shoulb be unique
	 * @throws Exception Launch an exception with the corresponding message
	 */
	public static void evaluateUnicity(UserService userService, String userEnteredEmailString) throws Exception
	{
		// Test the minimum length
    	if (isEmailExists(userService, userEnteredEmailString))
    	{
    		throw new Exception(err_duplicate);
    	}

    	// All is good
    	if (logger.isInfoEnabled()) {
    		logger.info("All clear");
    	}
    }

	public static boolean isEmailExists(UserService userService, String newEmail)
	{
		return userService.isEmailExists(newEmail, false);
	}

	public static boolean isValid( String userEnteredEmailString ) {
		try {
			new javax.mail.internet.InternetAddress(userEnteredEmailString.trim()).validate();
			return true;
		} catch (AddressException e) {
			return false;
		}
	}

	public static String getErrorInvalid()
	{
		return err_invalid;
	}

	public static String getErrorDuplicate()
	{
		return err_duplicate;
	}


}