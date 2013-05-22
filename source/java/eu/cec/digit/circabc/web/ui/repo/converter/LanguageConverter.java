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

/**
 * Extends the oiginal langage converter to prevent null pointer exception when the value is setted as null.
 *
 * @author yanick pignot
 */
public class LanguageConverter extends org.alfresco.web.ui.repo.converter.LanguageConverter
{
	private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value)
	{
		if(value == null)
		{
			return DEFAULT_LOCALE.getLanguage();
		}
		else
		{
			return super.getAsObject(context, component, value);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value)
	{
		if(value == null)
		{
			return "";
		}
		else
		{
			return super.getAsString(context, component, value);
		}
	}

}
