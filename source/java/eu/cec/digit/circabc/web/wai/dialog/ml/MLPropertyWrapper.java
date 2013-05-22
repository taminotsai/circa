/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.ml;

import java.io.Serializable;
import java.util.Locale;


/**
 * Light weight object that represent a single ml property in the User Interface
 *
 * @author Yanick pignot
 */
public class MLPropertyWrapper implements Serializable
{
	private static final long serialVersionUID = -3491052998092218918L;
	private String value;
	private Locale locale;

	/**
	 * Instanciate a MLPropertyWrapper
	 *
	 * @param value
	 * @param locale
	 */
	public MLPropertyWrapper(String value, Locale locale)
	{
		super();
		this.value = value;
		this.locale = locale;
	}

	/**
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @return the Locale
	 */
	public Locale getLocale()
	{
		return locale;
	}

	/**
	 * @return the Language
	 */
	public String getLanguage()
	{
		return locale == null ? "" : locale.getLanguage();
	}

	@Override
	public String toString()
	{
		return getValue();
	}
}