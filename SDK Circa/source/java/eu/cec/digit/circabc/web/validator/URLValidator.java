/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.validator;

import java.net.MalformedURLException;
import java.net.URL;

import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Validator for the URL property
 *
 * @author David Ferraz
 * @author Slobodan Filipovic
 */
public class URLValidator
{
	/** Logger */
	@SuppressWarnings("unused")
	private static Log logger = LogFactory.getLog(URLValidator.class);



	/**
	 * Check if the object contains a valid URL
	 *
	 * @param url The string to test
	 * @throws Exception Launch an exception with the corresponding message
	 */
	public static void evaluate(String url) throws ValidatorException
	{

		url = url.trim();

		if (url==null || url.length()<1)
		{
			throw new ValidatorException(new FacesMessage("library_add_url_url_empty"));
		}
		// try to create URL object from string if fail throw exception
		try
		{
			@SuppressWarnings("unused")
			final URL dummyUrl  = new URL( url );
		} catch (MalformedURLException e)
		{
			if (logger.isErrorEnabled())
			{
				logger.error("Error validationg: " + url,e);
			}
			throw new ValidatorException(new FacesMessage("library_add_url_url_invalid"));
		}


    }
}