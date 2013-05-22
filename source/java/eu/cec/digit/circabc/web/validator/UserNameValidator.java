/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.validator;

import org.alfresco.service.cmr.security.PersonService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * email validator based on RFC 2822.
 *
 * original code by: http://www.leshazlewood.com/ "Les"
 *
 * @author patrice.coppens@trasys.lu
 */
public class UserNameValidator
{
	/** Logger */
	private final static Log logger = LogFactory.getLog(UserNameValidator.class);

	/** The minimum permit length (not tested if value equals -1) */
	protected static final int minLength = 5;

	/** The maximum permit length (not tested if value equals -1) */
	protected static final int maxLength = 32;

	/** The message if the minimum length is wrong */
	protected static final String err_minimum_length = "err_minimum_length";

	/** The message if the maximum length is wrong */
	protected static final String err_maximum_length = "err_maximum_length";

	/** The message if the username is duplicate */
	public static final String err_duplicate = "err_duplicate";

	/** The message if the username characters are not valid */
	public static final String err_bad_chars = "err_bad_chars";

	private UserNameValidator()
	{

	}

	/**
	 * Check if the object contains a valid email address
	 *
	 * @param userEnteredEmailString The string to test
	 * @throws Exception Launch an exception with the corresponding message
	 */
	public static void evaluate(String userEnteredUsernameString) throws Exception
	{
		userEnteredUsernameString = userEnteredUsernameString.trim();

    	// Test the minimum length
    	if ((minLength > -1) && (userEnteredUsernameString.length() < minLength))
    	{
    		throw new Exception(err_minimum_length);
    	}

    	// Test the minimum length
    	if ((maxLength > -1) && (userEnteredUsernameString.length() > maxLength))
    	{
    		throw new Exception(err_maximum_length);
    	}


    	for (int i = 0; i < userEnteredUsernameString.length(); i++)
        {
        	if (Character.isLetterOrDigit(userEnteredUsernameString.charAt(i)) == false)
            {
        		throw new Exception(err_bad_chars);
            }
    	}

    	// All is good
    	if (logger.isInfoEnabled())
    	{
    		logger.info("All clear");
    	}
	}

	/**
	 * Check if the email is unique before its creation
	 *
	 * @param userEnteredEmailString				the email that shoulb be unique
	 * @throws Exception Launch an exception with the corresponding message
	 */
	public static void evaluateUnicity(PersonService personService, String newUser) throws Exception
	{
		// Test the minimum length
    	if (isUsernameExists(personService, newUser))
    	{
    		throw new Exception(err_duplicate);
    	}

    	// All is good
    	if (logger.isInfoEnabled()) {
    		logger.info("All clear");
    	}
    }

	/**
	 * Check if the user is exists before resending password
	 *
	 * @param personService 	the circabc user service reference
	 * @param oldUser			the email that shoulb be unique
	 * @throws Exception Launch an exception with the corresponding message
	 */

	public static void evaluateUserExists(PersonService personService, String oldUser) throws Exception
	{
		// Test if user exists
		// User must exists
    	if (! isUsernameExists(personService, oldUser))
    	{
    		throw new Exception(err_duplicate);
    	}

    	// All is good
    	if (logger.isInfoEnabled()) {
    		logger.info("All clear");
    	}
    }

	public static boolean isUsernameExists(PersonService personService, String newUser)
	{
		return personService.personExists(newUser);
	}

	public static String getErrorMinimumLength()
	{
		return err_minimum_length;
	}

	public static String getErrorMaximumLength()
	{
		return err_maximum_length;
	}

	public static String getErrorDuplicate()
	{
		return err_duplicate;
	}

	public static String getErrorBadCharacters()
	{
		return err_bad_chars;
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