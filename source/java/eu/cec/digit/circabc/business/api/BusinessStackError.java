/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.business.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.springframework.extensions.surf.util.I18NUtil;

/**
 * Keep in memory a list of business error. Usefull to display to user the complete list of missing / invalid
 * data in the form.
 *
 * Using this class prevent the webclient validation. It is the responsability of the Busisess stateless layer.
 *
 * @author Yanick Pignot
 */
public class BusinessStackError extends RuntimeException
{
	/** */
	private static final long serialVersionUID = 324195749694518785L;

	private final List<BusinessRuntimeExpection> errors;

	public BusinessStackError(final List<BusinessRuntimeExpection> errors)
	{
		this.errors = errors;
	}

	public BusinessStackError(final BusinessRuntimeExpection error)
	{
		this();
		this.errors.add(error);
	}

	public BusinessStackError()
	{
		this(new ArrayList<BusinessRuntimeExpection>(5));
	}

	/**
	 * @return if validation messages are added to the stack
	 */
	public boolean hasError()
	{
		return errors.size() > 0;
	}

	/**
	 * Append an validation error to the stack
	 *
	 * @param error
	 */
	public BusinessStackError append(final BusinessValidationError error)
	{
		this.errors.add(error);
		return this;
	}

	/**
	 * @return	All validation messages translated in the current locale
	 */
	public List<String> getI18NMessages()
	{
		return getI18NMessages(I18NUtil.getLocale());
	}

	/**
	 * @return	All validation messages translated in the given locale
	 */
	public List<String> getI18NMessages(final Locale locale)
	{
		final List<String> messages = new ArrayList<String>(this.errors.size());
		for(final BusinessRuntimeExpection error: errors)
		{
			messages.add(error.getLocalizedMessage(locale));
		}

		return messages;
	}

	/**
	 * @return	All I18N validation message keys
	 */
	public List<String> getMessageKeys()
	{
		final List<String> keys = new ArrayList<String>(this.errors.size());
		for(final BusinessRuntimeExpection error: errors)
		{
			keys.add(error.getMessageKey());
		}

		return keys;
	}

	/**
	 * Call this method when the validation is completed.
	 *
	 * @throws BusinessStackError			If at least one error is found.
	 */
	public void finish() throws BusinessStackError
	{
		finish(null);
	}

	/**
	 * Call this method when the validation is completed.
	 *
	 * @param logger								Debug all error messages messages
	 * @throws BusinessStackError			If at least one error is found.
	 */
	public void finish(final Log logger) throws BusinessStackError
	{
		final boolean debugEnable = logger != null && logger.isDebugEnabled();

		if(hasError())
		{
			if(debugEnable)
			{
				for(final String message: getI18NMessages(Locale.ENGLISH))
				{
					logger.debug(message);
				}
			}

			throw this;
		}
		else if (debugEnable)
		{
			logger.debug("All user filled information are valid.");
		}
	}

	@Override
	public String getLocalizedMessage()
	{
		return getI18NMessages().toString();
	}

	@Override
	public String getMessage()
	{
		return getMessageKeys().toString();
	}

}
