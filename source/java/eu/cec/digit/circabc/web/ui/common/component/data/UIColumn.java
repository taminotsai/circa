/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.common.component.data;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * @author Guillaume
 */
public class UIColumn extends UIComponentBase 
{
   // ------------------------------------------------------------------------------
   // Component implementation

   /**
    * @see javax.faces.component.UIComponent#getFamily()
    */
   public String getFamily()
   {	  
      return "eu.cec.digit.circabc.faces.Data";
   }

   /**
    * Return the UI Component to be used as the header for this column
    *
    * @return UIComponent
    */
   public UIComponent getHeader()
   {
      return getFacet("header");
   }

   /**
    * Return the UI Component to be used as the footer for this column
    *
    * @return UIComponent
    */
   public UIComponent getFooter()
   {
      return getFacet("footer");
   }

   /**
    * Return the UI Component to be used as the large icon for this column
    *
    * @return UIComponent
    */
   public UIComponent getLargeIcon()
   {
      return getFacet("large-icon");
   }


   /**
    * Return the UI Component to be used as the small icon for this column
    *
    * @return UIComponent
    */
   public UIComponent getSmallIcon()
   {
      return getFacet("small-icon");
   }

   /**
    * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
    */
   public void restoreState(FacesContext context, Object state)
   {
      Object values[] = (Object[])state;
      // standard component attributes are restored by the super class
      super.restoreState(context, values[0]);
      this.primary = ((Boolean)values[1]).booleanValue();
      this.actions = ((Boolean)values[2]).booleanValue();
   }

   /**
    * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
    */
   public Object saveState(FacesContext context)
   {
      Object values[] = new Object[3];
      // standard component attributes are saved by the super class
      values[0] = super.saveState(context);
      values[1] = (this.primary ? Boolean.TRUE : Boolean.FALSE);
      values[2] = (this.actions ? Boolean.TRUE : Boolean.FALSE);
      return (values);
   }


   // ------------------------------------------------------------------------------
   // Strongly typed component property accessors

   /**
    * @return true if this is the primary column
    */
   public boolean getPrimary()
   {
      ValueBinding vb = getValueBinding("primary");
      if (vb != null)
      {
         this.primary = (Boolean)vb.getValue(getFacesContext());
      }
      return this.primary;
   }

   /**
    * @param primary    True if this is the primary column, false otherwise
    */
   public void setPrimary(boolean primary)
   {
      this.primary = primary;
   }

   /**
    * @return true if this is the column containing actions for the current row
    */
   public boolean getActions()
   {
      ValueBinding vb = getValueBinding("actions");
      if (vb != null)
      {
         this.actions = (Boolean)vb.getValue(getFacesContext());
      }
      return this.actions;
   }

   /**
    * @param actions    True if this is the column containing actions for the current row
    */
   public void setActions(boolean actions)
   {
      this.actions = actions;
   }


   // ------------------------------------------------------------------------------
   // Private data

   private boolean primary = false;
   private boolean actions = false;
}
