/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.component;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.model.SelectItem;

import org.alfresco.web.app.Application;
import org.alfresco.web.ui.common.ComponentConstants;
import org.alfresco.web.ui.common.Utils;

import eu.cec.digit.circabc.service.dynamic.property.DynamicProperty;
import eu.cec.digit.circabc.web.wai.dialog.search.SearchProperties;
import eu.cec.digit.circabc.web.wai.generator.DynamicPropertyGenerator;

/**
 * @author Pignot Yanick
 */
public class UISearchDynamicProperties extends UIComponentBase
{
	public static final String CIRCABC_FACES_DATE_PICKER_RENDERER = "eu.cec.digit.circabc.faces.DatePickerGeneratorRenderer";

	public static final String SET_PROPERTY_METHOD_PREFIX = "dynamicProperty";

	public static final String PREFIX_DATE_TO    = "to_";
	public static final String PREFIX_DATE_FROM  = "from_";
	public static final String PREFIX_LOV_ITEM   = "item_";

	private static final String VALUE = "value";

	private static final String MSG_NO_PROP = "advanced_search_dialog_no_dynamic_properties_defined";

   // ------------------------------------------------------------------------------
   // Component implementation

   public UISearchDynamicProperties()
   {
	   	//	 set the default renderer for a property sheet
	    setRendererType(ComponentConstants.JAVAX_FACES_GRID);
   }

   /**
    * @see javax.faces.component.UIComponent#getFamily()
    */
   public String getFamily()
   {
      return "eu.cec.digit.circabc.faces.AdvancedSearch";
   }

   /**
    * @see javax.faces.component.UIComponentBase#encodeBegin(javax.faces.context.FacesContext)
    */
   @SuppressWarnings("unchecked")
   public void encodeBegin(FacesContext context) throws IOException
   {
      if (isRendered() == false)
      {
         return;
      }

      if (getChildCount() == 0)
      {
    	  createComponents(context, getProperties(context));
      }

      //super.encodeBegin(context);

      final List<UIComponent> children = getChildren();

      final ResponseWriter out = context.getResponseWriter();

      out.write("<table border=\"0\" cellpadding=\"3\" cellspacing=\"3\"><tbody>");


	  boolean odd = true;

	  for (final UIComponent component : children)
      {
    	  if(odd)
    	  {
    		 out.write("<tr>");
    	  }
    	  out.write("<td>");

    	  Utils.encodeRecursive(context, component);

    	  out.write("</td>");
    	  if(!odd)
    	  {
    		 out.write("</tr>");
    	  }

    	  odd = odd == false;
     }

	  out.write("</tbody></table>");
   }

   @SuppressWarnings("unchecked")
   protected void createComponents(FacesContext context, List<DynamicProperty> dynamicProperties) throws IOException
   {
	   if(dynamicProperties != null && dynamicProperties.size() > 0)
	   {
		   for (DynamicProperty property : dynamicProperties)
		   {
			   createComponent(context, property);
		   }
	   }
	   else
	   {
		   getChildren().add(generateLabel(context, Application.getMessage(context, MSG_NO_PROP)));
	   }
   }

   @SuppressWarnings("unchecked")
   protected void createComponent(FacesContext context, DynamicProperty property) throws IOException
   {
	   if(property == null)
	   {
		   return;
	   }

	   final String propertyBinding = getPropetyBinding(property);

	   super.getChildren().add( generateLabel(context, property.getLabel().getDefaultValue() + ": ") );

	   switch (property.getType())
	   {
	   		case DATE_FIELD:

	   			getChildren().add(generateDatePicker(context, property, propertyBinding));
	   			break;
	   		case SELECTION:
	   			getChildren().add(generateSelection(context, property, propertyBinding));
	   			break;
	   		case TEXT_AREA:
	   		case TEXT_FIELD:
	   			getChildren().add(generateInput(context, property, propertyBinding));
	   			break;
	   		default:
	   			throw new IllegalStateException("This kind of dynamic property is not managed for the search screen");
	}

   }


   private List<DynamicProperty> getProperties(FacesContext context)
   {
	   final String beanBinding = "#{" + (String) getAttributes().get("bean") + '.' + (String)getAttributes().get("var") + '}';
	   final javax.faces.application.Application facesApp = context.getApplication();
	   final MethodBinding binding = facesApp.createMethodBinding(beanBinding, null);

	   final Object dynamicPropertiesObj = binding.invoke(context, null);

	   if(dynamicPropertiesObj == null)
	   {
		   // probably no dynamic property for this interest group
		   return null;
	   }

	   final List<DynamicProperty> properties = new ArrayList<DynamicProperty>(5);

	   if(dynamicPropertiesObj instanceof DynamicProperty)
	   {
		   properties.add((DynamicProperty) dynamicPropertiesObj);
	   }
	   else if(dynamicPropertiesObj instanceof List)
	   {
		    for(Object obj : (List) dynamicPropertiesObj)
		    {
		    	if(!(obj instanceof DynamicProperty))
		    	{
		    		throw new IllegalArgumentException("The list can only contain DynamicProperty.");
		    	}

		    	properties.add((DynamicProperty) obj);
		    }
	   }

	   return properties;
   }

   /**
    * Generates a JSF OutputText component/renderer
    *
    * @param context JSF context
    * @param displayLabel The display label text
    *
    * @return UIComponent
    */
   private UIComponent generateLabel(FacesContext context, String displayLabel)
   {
      UIOutput label = (UIOutput)context.getApplication().createComponent(ComponentConstants.JAVAX_FACES_OUTPUT);
      label.setId(context.getViewRoot().createUniqueId());
      label.setRendererType(ComponentConstants.JAVAX_FACES_TEXT);
      label.setValue(displayLabel);
      return label;
   }

   private String getPropetyBinding(DynamicProperty property)
   {
	   final StringBuffer buff = new StringBuffer("");

	   buff.append('#')
	   		.append('{')
	   		.append(SearchProperties.BEAN_NAME)
	   		.append('.')
	   		.append(SET_PROPERTY_METHOD_PREFIX)
	   		.append(property.getIndex())
	   		.append('}');

	   return buff.toString();
   }

   @SuppressWarnings("unchecked")
   private UIComponent generateDatePicker(FacesContext context, DynamicProperty property, String propertyBinding)
   {
	   final javax.faces.application.Application facesApp = context.getApplication();

	   // create value bindings for the start year and year count attributes
       final ValueBinding startYearBind = facesApp.createValueBinding("#{DatePickerGenerator.startYear}");
       final ValueBinding yearCountBind = facesApp.createValueBinding("#{DatePickerGenerator.yearCount}");


       UIComponent component = context.getApplication().createComponent(ComponentConstants.JAVAX_FACES_INPUT);

       component.setRendererType(CIRCABC_FACES_DATE_PICKER_RENDERER);
       component.getAttributes().put("startYear", startYearBind);
       component.getAttributes().put("yearCount", yearCountBind);
       component.getAttributes().put("initialiseIfNull",  Boolean.FALSE);
       component.getAttributes().put("style", "margin-right: 7px;");

       component.setValueBinding(VALUE, facesApp.createValueBinding(propertyBinding));

       return component;
   }

   private UIComponent generateInput(FacesContext context, DynamicProperty property, String propertyBinding)
   {
	   final javax.faces.application.Application facesApp = context.getApplication();

	   UIInput component = (UIInput)facesApp.createComponent(ComponentConstants.JAVAX_FACES_INPUT);
	   component.setRendererType(ComponentConstants.JAVAX_FACES_TEXT);
	   component.setValueBinding("size", facesApp.createValueBinding("#{TextFieldGenerator.size}"));
	   component.setValueBinding("maxlength", facesApp.createValueBinding("#{TextFieldGenerator.maxLength}"));

	   component.setValueBinding(VALUE, facesApp.createValueBinding(propertyBinding));

       return component;
   }

   @SuppressWarnings("unchecked")
   private UIComponent generateSelection(FacesContext context, DynamicProperty property, String propertyBinding)
   {
	   final javax.faces.application.Application facesApp = context.getApplication();

	   final UISelectOne component = (UISelectOne)facesApp.createComponent(UISelectOne.COMPONENT_TYPE);
	   final UISelectItems itemsComponent = (UISelectItems)facesApp.createComponent(ComponentConstants.JAVAX_FACES_SELECT_ITEMS);

	   final List<SelectItem> values = DynamicPropertyGenerator.getMultiValues(property);
	   final List<SelectItem> items = new ArrayList<SelectItem>(values.size() + 1);

	   items.add(new SelectItem("", Application.getMessage(context, SearchProperties.ANY_MESSAGE_KEY)));
	   items.addAll(values);

       itemsComponent.setValue(items);

       // add the items as a child component
       component.getChildren().add(itemsComponent);
       component.setValueBinding(VALUE, facesApp.createValueBinding(propertyBinding));

       return component;
   }
}
