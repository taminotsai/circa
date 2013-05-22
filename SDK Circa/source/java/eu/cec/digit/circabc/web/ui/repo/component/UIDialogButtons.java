/*--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--*/
package eu.cec.digit.circabc.web.ui.repo.component;


import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.alfresco.web.ui.common.Utils;

/**
 * WAI.
 * Component that displays the buttons for a dialog.
 * <p>
 * The standard <code>OK</code> and <code>Cancel</code> buttons
 * are always generated. Any additional buttons, either configured
 * or generated dynamically by the dialog, are generated in between
 * the standard buttons.
 *
 * @author patrice.coppens@trasys.lu
 */
public class UIDialogButtons extends
		org.alfresco.web.ui.repo.component.UIDialogButtons {


	   @Override
	   public void encodeBegin(FacesContext context) throws IOException
	   {
	      if (!isRendered()) return;

	      if (this.getChildCount() == 0)
	      {
	         // generate all the required buttons the first time
	         generateButtons(context);
	      }

	      ResponseWriter out = context.getResponseWriter();
	      out.write("<div class=\"tablediv\">");
	   }

	   @Override
	   public void encodeChildren(FacesContext context) throws IOException
	   {
	      if (!isRendered()) return;

	      ResponseWriter out = context.getResponseWriter();

	      // render the buttons
	      for (Iterator i = getChildren().iterator(); i.hasNext(); /**/)
	      {
	         out.write("<div class=\"rowdiv\" ><div class=\"celldivfullcenter\" >");

	         UIComponent child = (UIComponent)i.next();
	         Utils.encodeRecursive(context, child);

	         out.write("</div></div>");
	      }
	   }

	   @Override
	   public void encodeEnd(FacesContext context) throws IOException
	   {
	      if (!isRendered()) return;

	      ResponseWriter out = context.getResponseWriter();
	      out.write("</div>");
	   }

}
