/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.converter;

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.util.ISO8601DateFormat;
import org.alfresco.web.app.Application;
import org.alfresco.web.ui.common.converter.XMLDateConverter;
import org.alfresco.web.ui.repo.RepoConstants;

import eu.cec.digit.circabc.web.WebClientHelper;

/**
 * Convertor for dynamic properties.
 *
 * @author yanick pignot
 */
public class DynamicPropertyConverter implements Converter
{

	/**
	 * <p>The standard converter id for this converter.</p>
	 */
	public static final String CONVERTER_ID = "eu.cec.digit.circabc.faces.DynamicPropertyConverter";

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
	{
		return value;
	}

	@SuppressWarnings("unchecked")
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
	{
		boolean isDate = false;
		if(value instanceof Date)
		{
			isDate = true;
		}
		else if(value instanceof String)
		{
			try
			{
				ISO8601DateFormat.parse((String) value);
				isDate = true;
			}
			catch(AlfrescoRuntimeException ex)
			{
				isDate = false;
			}
		}

		if(isDate)
		{
			return getDateConverter(context).getAsString(context, component, value.toString());
		}
		else
		{
			return value == null ? "" : value.toString();
		}
	}

   /**
    * Retrieves the default converter for the date component
    *
    * @param context FacesContext
    * @return XMLDateConverter
    */
   protected Converter getDateConverter(FacesContext context)
   {
      XMLDateConverter converter = (XMLDateConverter)context.getApplication().
            createConverter(RepoConstants.ALFRESCO_FACES_XMLDATE_CONVERTER);
      converter.setType("date");
      converter.setPattern(Application.getMessage(context, WebClientHelper.MSG_DATE_PATTERN));
      return converter;
   }
}
