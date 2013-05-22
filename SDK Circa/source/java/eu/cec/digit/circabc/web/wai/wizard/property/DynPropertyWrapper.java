/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.wizard.property;

import java.io.Serializable;
import java.util.Locale;

/**
 * Light weight object that represent a single dynamic property
 *
 * @author Yanick pignot
 */
public class DynPropertyWrapper implements Serializable
{
	private static final long serialVersionUID = -3433352998092218189L;

	private String value;
	private Locale locale;

	/**
	 * Instanciate a Dynamic property
	 *
	 * @param id
	 * @param value
	 * @param locale
	 */
	public DynPropertyWrapper(String value, Locale locale)
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