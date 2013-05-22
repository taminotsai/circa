/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.customisation;

/**
 * @author Yanick Pignot
 */
public class CustomizationException extends Exception
{

	/** */
	private static final long serialVersionUID = 733672122793854579L;

	/**
	 * @param message
	 */
	public CustomizationException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public CustomizationException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CustomizationException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
