/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.app.servlet.FacesHelper;
import org.alfresco.web.bean.generator.BaseComponentGenerator;
import org.alfresco.web.bean.generator.TextAreaGenerator;
import org.alfresco.web.bean.generator.TextFieldGenerator;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.ui.repo.component.property.PropertySheetItem;
import org.alfresco.web.ui.repo.component.property.UIPropertySheet;
import org.springframework.extensions.surf.util.I18NUtil;

import eu.cec.digit.circabc.model.DocumentModel;
import eu.cec.digit.circabc.service.dynamic.property.DynamicProperty;
import eu.cec.digit.circabc.service.dynamic.property.DynamicPropertyService;
import eu.cec.digit.circabc.service.struct.ManagementService;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.Services;

/**
 * Generates a specify keyword on a document generator.
 *
 * @author Yanick Pignot
 */
public class DynamicPropertyGenerator extends BaseComponentGenerator
{
	protected static final String PROPERTY_PREFIX;

	static
	{
		final String propName = DocumentModel.PROP_DYN_ATTR_1.getLocalName();
		PROPERTY_PREFIX = propName.substring(0, propName.length() - 1);
	}

	private DynamicProperty dynamicProperty;

	@Override
	public UIComponent generateAndAdd(FacesContext context, UIPropertySheet propertySheet, PropertySheetItem item)
	{
		final Node node = propertySheet.getNode();
		final NodeRef igRoot = getManagementService(context).getCurrentInterestGroup(node.getNodeRef());
		if (igRoot == null )
		{
			final PropertySheetItem child = getPropertySheetItemByName(propertySheet, item.getName());
			child.setRendered(false);
		}
		else
		{
			dynamicProperty = getDynPropertyByName(context, item.getName(), igRoot);

			final PropertySheetItem child = getPropertySheetItemByName(propertySheet, item.getName());

			if(dynamicProperty == null)
			{
				child.setRendered(false);
			}
			else
			{
				final Locale currentLocale = I18NUtil.getLocale();

				String label = null;
				if  (dynamicProperty.getLabel() !=null)
				{
					label = dynamicProperty.getLabel().getClosestValue(currentLocale);
				}

				if(label == null)
				{
					label = dynamicProperty.getName();
				}

				final Object titleCompenent = (child.getChildren() != null && child.getChildren().size() > 0) ? child.getChildren().get(0) : null;

				if(titleCompenent != null && titleCompenent instanceof UIOutput)
				{
					((UIOutput)titleCompenent).setValue(label + ":") ;
				}
			}
		}

		return super.generateAndAdd(context, propertySheet, item);
	}

	@SuppressWarnings("unchecked")
	public UIComponent generate(FacesContext context, String id)
	{
		UIComponent component = null;

		if(dynamicProperty != null)
		{
			switch (dynamicProperty.getType())
			{
				case DATE_FIELD:
					component = getDatePickerGenerator().generate(context, id);
					break;
				case SELECTION:
					 component = context.getApplication().createComponent(UISelectOne.COMPONENT_TYPE);
					 FacesHelper.setupComponentId(context, component, id);

					 // create the list of choices
					 UISelectItems itemsComponent = (UISelectItems)context.getApplication().
		             createComponent("javax.faces.SelectItems");

					 itemsComponent.setValue(getMultiValues(dynamicProperty));

					 // add the items as a child component
					 component.getChildren().add(itemsComponent);

					break;
				case TEXT_AREA:
					component = ((TextAreaGenerator) Beans.getBean("TextAreaGenerator")).generate(context, id);
					break;
			}
		}


		if(component == null)
		{
			component = ((TextFieldGenerator) Beans.getBean("TextFieldGenerator")).generate(context, id);
		}

		return component;
	}


	protected PropertySheetItem getPropertySheetItemByName(final UIPropertySheet propertySheet, final String name)
	{
		PropertySheetItem toReturn = null;

		for(final Object child : propertySheet.getChildren())
		{
			if(child instanceof PropertySheetItem)
			{
				PropertySheetItem item = (PropertySheetItem) child;
				if(item.getName().equals(name))
				{
					toReturn = item;
					break;
				}
			}
		}

		return toReturn;

	}

	protected DatePickerGenerator getDatePickerGenerator()
    {
		return (DatePickerGenerator) Beans.getBean("CircabcDatePickerGenerator");
    }

  	public static List<SelectItem> getMultiValues(DynamicProperty property)
  	{
		final String values = property.getValidValues();

		List<SelectItem> items = null;

		if(values != null && values.length() > 0)
		{
			final StringTokenizer tokens = new StringTokenizer(values, String.valueOf(DynamicPropertyService.MULTI_VALUES_SEPARATOR), false);

			items = new ArrayList<SelectItem>(tokens.countTokens());

			while (tokens.hasMoreTokens())
			{
				items.add(new SelectItem(tokens.nextToken()));
			}
		}
		else
		{
			items = Collections.<SelectItem> emptyList();
		}

		return items;
  	}

	protected DynamicProperty getDynPropertyByName(final FacesContext context, final String name, final NodeRef ref)
	{
		final String pref = name.substring(0, name.indexOf(':') + 1);
		final String indexStr = name.substring(pref.length() + PROPERTY_PREFIX.length());

		final Long index = Long.parseLong(indexStr);

		final DynamicPropertyService propertyService = getDynamicPropertyService(context);
		final NodeRef igRoot = getManagementService(context).getCurrentInterestGroup(ref);
		final List<DynamicProperty> properties = propertyService.getDynamicProperties(igRoot);

		DynamicProperty property = null;

		for (final DynamicProperty prop : properties)
		{
			if((prop != null) && (prop.getIndex() != null) )
			{
				if(prop.getIndex().equals(index))
				{
					property = prop;
					break;
				}
			}
		}

		return property;
	}

	protected DynamicPropertyService getDynamicPropertyService(final FacesContext context)
	{
		return Services.getCircabcServiceRegistry(context).getDynamicPropertieService();
	}

	protected ManagementService getManagementService(final FacesContext context)
	{
		return Services.getCircabcServiceRegistry(context).getManagementService();
	}
}
