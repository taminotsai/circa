/*--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--*/
package eu.cec.digit.circabc.web.ui.repo.renderer.property;


import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.alfresco.web.app.Application;
import org.alfresco.web.ui.common.Utils;

import eu.cec.digit.circabc.web.ui.common.UtilsCircabc;

/**
 * WAI-A Renderer for a PropertySheetItem component.
 * @author patrice.coppens@trasys.lu
 */
public class PropertySheetItemRenderer extends org.alfresco.web.ui.repo.renderer.property.PropertySheetItemRenderer{

	public static final String MANDATORY_MARKER= "/images/icons/required_field.gif";

	   /**
	    * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	    */
	   @SuppressWarnings("unchecked")
	   @Override
	   public void encodeChildren(FacesContext context, UIComponent component) throws IOException
	   {
	      if (! component.isRendered())
	      {
	         return;
	      }

	      ResponseWriter out = context.getResponseWriter();

	      // make sure there are 2 or 3 child components
	      int count = component.getChildCount();

	      if (count == 2 || count == 3)
	      {
	         // get the label and the control
	         List<UIComponent> children = component.getChildren();
	         UIComponent label = children.get(0);
	         UIComponent control = children.get(1);


	         // place a style class on the label column if necessary
	         String labelStylceClass = (String)component.getParent().getAttributes().get("labelStyleClass");
	         out.write("<th ");
	         if (labelStylceClass != null)
	         {
	            outputAttribute(out, labelStylceClass, "class");
	         }

	         // close the <td>
	         out.write(">");

	         // encode the mandatory marker component if present
	         if (count == 3)
	         {
	        	ResourceBundle bundle = (ResourceBundle)Application.getBundle(context);

	        	out.write(UtilsCircabc.buildImageTag(context, MANDATORY_MARKER, bundle.getString("mandatory")));
	        	out.write(" ");
	         }

	         // encode the label
	         Utils.encodeRecursive(context, label);
	         // encode the control
	         out.write("</th><td align=\"left\" >");
	         Utils.encodeRecursive(context, control);

	         // NOTE: we'll allow the property sheet's grid renderer close off the last <td>
	      }
	   }
}
