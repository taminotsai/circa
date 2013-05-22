/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.generator;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.alfresco.web.app.Application;
import org.alfresco.web.bean.generator.TextFieldGenerator;
import org.alfresco.web.ui.repo.component.property.PropertySheetItem;
import org.alfresco.web.ui.repo.component.property.UIPropertySheet;

/**
 * Generates a specify keyword on a document generator.
 *
 * @author Yanick Pignot
 */
public class I18NTextFieldGenerator extends TextFieldGenerator
{
	 @SuppressWarnings("unchecked")
	 @Override
	 protected UIComponent createComponent(FacesContext context, UIPropertySheet propertySheet, PropertySheetItem item)
	 {
		 final UIComponent component = super.createComponent(context, propertySheet, item);

		 if(component instanceof UISelectOne)
		 {
			 final UISelectOne select = (UISelectOne) component;
			 final UISelectItems items = (UISelectItems) select.getChildren().get(0);
			 final List<SelectItem> selectItems = (List<SelectItem>) items.getValue();
			 String translation = null;
			 for (SelectItem selectItem : selectItems)
			 {
				 translation = Application.getMessage(context, selectItem.getLabel().toLowerCase());
				 if(translation != null && !translation.startsWith("$$"))
				 {
					 selectItem.setLabel(translation);
				}
			 }
		 }

		 return component;
	 }
}
