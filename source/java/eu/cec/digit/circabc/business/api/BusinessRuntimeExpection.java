/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.business.api;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.extensions.surf.util.I18NUtil;

/**
 * @author Yanick Pignot
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * I18NUtil was moved to Spring.
 * This class seems to be developed for CircaBC
 */
public class BusinessRuntimeExpection extends RuntimeException
{
	private final Object[] parameters;

	/** */
	private static final long serialVersionUID = -6603382649957220698L;

	/**
	 *
	 */
	public BusinessRuntimeExpection()
	{
		super();
		this.parameters = null;
	}

	/**
	 * @param message						The message key
	 * @param parameters					The optional paramaters of the I18N message key
	 */
	public BusinessRuntimeExpection(final String messageKey, final Object ... parameters)
	{
		this(messageKey, null, parameters);
	}

	/**
	 * @param message						The message key
	 * @param cause							The previous cause of the error
	 * @param parameters					The optional paramaters of the I18N message key
	 */
	public BusinessRuntimeExpection(final String messageKey, final Throwable cause, final Object ... parameters)
	{
		super(messageKey, cause);
		this.parameters = parameters;
	}

	/**
	 * @return	The I18N message key
	 */
	public String getMessageKey()
	{
		return getMessage();
	}

	/**
	 * @return	The validation message translated in the given locale
	 */
	public String getLocalizedMessage(final Locale locale)
	{
		final String message = I18NUtil.getMessage(getMessageKey(), locale);

		if(parameters == null)
		{
			return message;
		}
		else
		{
			return MessageFormat.format(message, parameters);
		}
	}

	@Override
	public String getLocalizedMessage()
	{
		return getLocalizedMessage(I18NUtil.getLocale());
	}

}
