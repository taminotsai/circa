/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.keywords;

import java.io.Serializable;
import java.util.Locale;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Light weight object that represent a single keyword in the User Interface
 *
 * @author Yanick pignot
 */
public class KeywordWrapper implements Serializable
{
	private static final long serialVersionUID = -3491052998092218189L;

	private NodeRef id;
	private String value;
	private Locale locale;

	/**
	 * Instanciate a non multilingual keyword
	 *
	 * @param id
	 * @param value
	 */
	public KeywordWrapper(NodeRef id, String value)
	{
		this(id, value, null);
	}

	/**
	 * Instanciate a multilingual keyword
	 *
	 * @param id
	 * @param value
	 * @param locale
	 */
	public KeywordWrapper(NodeRef id, String value, Locale locale)
	{
		super();
		this.id = id;
		this.value = value;
		this.locale = locale;
	}

	/**
	 * @return the id
	 */
	public NodeRef getId()
	{
		return id;
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