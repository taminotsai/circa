/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.converter;

import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.extensions.surf.util.I18NUtil;

/**
 * Convertor for any locale.
 *
 * @author yanick pignot
 */
public class LocaleConverter implements Converter
{

	/**
	 * <p>The standard converter id for this converter.</p>
	 */
	public static final String CONVERTER_ID = "eu.cec.digit.circabc.faces.LocaleConverter";


	public Object getAsObject(final FacesContext context, final UIComponent comp, final String value) throws ConverterException
	{
		return I18NUtil.parseLocale(value);
	}

	public String getAsString(final FacesContext context, final UIComponent component, final Object object) throws ConverterException {

		if (object == null)
		{
			return null;
		}
		else if(object instanceof String)
		{
			// allow to hard code the locale
			return (String) object;
		}
		else if(object instanceof Locale)
		{
			return ((Locale) object).getLanguage();
		}
		else
		{
			return object.toString();
		}
	}
}
