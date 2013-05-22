/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.converter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.model.SelectItem;

import org.alfresco.web.app.Application;

/**
 * Convertor for any enumeration.
 *
 * @author yanick pignot
 */
public class EnumConverter implements Converter
{

	/**
	 * <p>The standard converter id for this converter.</p>
	 */
	public static final String CONVERTER_ID = "eu.cec.digit.circabc.faces.EnumConverter";

	@SuppressWarnings("unchecked")
	public Object getAsObject(final FacesContext context, final UIComponent comp, final String value) throws ConverterException
	{
		final Class enumType = comp.getValueBinding("value").getType(context);
		return Enum.valueOf(enumType, value);
	}

	public String getAsString(final FacesContext context, final UIComponent component, final Object object) throws ConverterException {

		if (object == null)
		{
			return null;
		}

		// allow to hard code the name of the enumeration
		if(object instanceof String)
		{
			return (String) object;
		}

		final Enum type = (Enum) object;
		return type.toString();
	}

	/**
	 * Util method that convert a list of enumeration in a translated list of selectable item.
	 * <b>
	 * 		Don't forget to set the converter to the component:
	 * 		<code>
	 * 			<h:selectOneMenu value="#{Bean.myEnumeration}" converter="eu.cec.digit.circabc.faces.EnumConverter">
	 * 				<f:selectItems value="#{Bean.myEnumerations}"/>
	 *          </h:selectOneMenu>
	 * 		</code>
	 * </b>
	 *
	 * @param context			The faces context
	 * @param enums				All enumeration to convert into SelectItem Enum.values()
	 * @param messagePrefix		The I18N message prefix. Can be empty. If null no message will appears to screen
	 * @return					The list of Select item redy to be inserted to a Select Item JSF Component.
	 */
	public static List<SelectItem> convertEnumToSelectItem(final FacesContext context, final Enum[] enums, final String messagePrefix)
	{
        final List<SelectItem> items = new ArrayList<SelectItem>(enums.length);
        String message = null;

        for(Enum enumItem : enums)
        {
        	if(messagePrefix == null)
        	{
        		message =  "" ;
        	}
        	else
        	{
        		message =  Application.getMessage(context, messagePrefix + enumItem.name().toLowerCase());
        	}

        	items.add(new SelectItem(enumItem, message));
        }

        return items;
	}

}
