/*--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--*/
package eu.cec.digit.circabc.web.ui.repo.renderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.alfresco.web.app.Application;
import org.apache.myfaces.renderkit.html.HtmlTextRenderer;

/**
 * Renders a WAI multilingual text field.
 * <p>
 * Renders the default output followed by an icon
 * to represent multilingual properties.
 * </p>
 *
 * @author patrice.coppens@trasys.lu
 */
public class MultilingualTextRenderer extends HtmlTextRenderer
{
	   @Override
	   public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException
	   {
	      super.encodeEnd(facesContext, component);

	      String tooltip = Application.getMessage(facesContext, "marker_tooltip");
	      ResponseWriter out = facesContext.getResponseWriter();
	      out.write("<img src='");
	      out.write(facesContext.getExternalContext().getRequestContextPath());
	      out.write("/images/icons/multilingual_marker.gif' title='");
	      out.write(tooltip);
	      out.write("' alt='");
	      out.write(tooltip);
	      out.write("' style='margin-left: 6px; vertical-align: -4px; _vertical-align: -2px;' />");
	   }
	}