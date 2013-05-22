/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.converter;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.alfresco.web.app.Application;

/**
 * Convertor for a String property that the value can be translated via the message bundle.
 *
 * @author yanick pignot
 */
public class I18NTextFieldConverter implements Converter
{

	/**
	 * <p>The standard converter id for this converter.</p>
	 */
	public static final String CONVERTER_ID = "eu.cec.digit.circabc.faces.I18NTextFieldConverter";

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
	{
		return value;
	}

	@SuppressWarnings("unchecked")
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
	{
		if(value instanceof String)
		{
			 String result = (String) value;

			 if(!(component instanceof UISelectOne))
			 {
				 final String translation = Application.getMessage(context, ((String) value).toLowerCase());
				 if(translation != null && !translation.startsWith("$$"))
				 {
					 result = translation;
				 }
			 }

			 return result;
		}
		else
		{
			return "";
		}
	}
}
