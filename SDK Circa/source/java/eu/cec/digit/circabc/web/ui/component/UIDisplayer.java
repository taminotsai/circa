/*--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--*/
package eu.cec.digit.circabc.web.ui.component;

import javax.faces.component.UIComponentBase;
import javax.faces.el.ValueBinding;

/**
 * @author Guillaume
 */
public class UIDisplayer extends UIComponentBase
{
   // ------------------------------------------------------------------------------
   // Component Impl

   /**
    * Default constructor
    */
   public UIDisplayer()
   {
      setRendererType(null);
   }

   /**
    * @see javax.faces.component.UIComponent#getFamily()
    */
   public String getFamily()
   {
      return "eu.cec.digit.circabc.faces.Controls";
   }

   /**
    * Force the get of the value
    */
   @Override
   public boolean isRendered()
   {
       ValueBinding vb = getValueBinding("rendered");
       Boolean v = vb != null ? (Boolean)vb.getValue(getFacesContext()) : null;
       return v != null ? v.booleanValue() : true;
   }
}
