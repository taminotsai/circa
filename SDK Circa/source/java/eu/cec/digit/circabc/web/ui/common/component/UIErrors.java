/*--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--*/
package eu.cec.digit.circabc.web.ui.common.component;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Class for setter and getter
 *
 * @author Guillaume
 */
public class UIErrors extends UIComponentBase
{
   // ------------------------------------------------------------------------------
   // Construction

   /**
    * Default Constructor
    */
   public UIErrors()
   {
      setRendererType("eu.cec.digit.circabc.faces.ErrorsRenderer");
   }


   // ------------------------------------------------------------------------------
   // Component implementation

   /**
    * @see javax.faces.component.UIComponent#getFamily()
    */
   public String getFamily()
   {
      return "eu.cec.digit.circabc.faces.Errors";
   }

   /**
    * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
    */
   public void restoreState(FacesContext context, Object state)
   {
      Object values[] = (Object[])state;
      // standard component attributes are restored by the super class
      super.restoreState(context, values[0]);
      this.styleClass = (String)values[1];
      this.errorClass = (String)values[2];
      this.infoClass = (String)values[3];
      this.warnClass = (String)values[4];
      this.escape = (Boolean)values[5];
   }

   /**
    * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
    */
   public Object saveState(FacesContext context)
   {
      Object values[] = new Object[6];
      // standard component attributes are saved by the super class
      values[0] = super.saveState(context);
      values[1] = this.styleClass;
      values[2] = this.errorClass;
      values[3] = this.infoClass;
      values[4] = this.warnClass;
      values[5] = this.escape;

      return (values);
   }


   // ------------------------------------------------------------------------------
   // Strongly typed component property accessors

   /**
    * Get the CSS class to use for general html composant
    *
    * @return The CSS class to use for general html composant
    */
   public String getStyleClass()
   {
      ValueBinding vb = getValueBinding("styleClass");
      if (vb != null)
      {
         this.styleClass = (String)vb.getValue(getFacesContext());
      }

      return this.styleClass;
   }

   /**
    * Set the CSS class to use for general html composant
    *
    * @param styleClass The CSS class to use for general html composant
    */
   public void setStyleClass(String styleClass)
   {
      this.styleClass = styleClass;
   }


   /**
    * Get the CSS class to use for ERROR and FATAL messages
    *
    * @return The CSS class to use for ERROR and FATAL messages.
    */
   public String getErrorClass()
   {
      ValueBinding vb = getValueBinding("errorClass");
      if (vb != null)
      {
         this.errorClass = (String)vb.getValue(getFacesContext());
      }

      return this.errorClass;
   }

   /**
    * Set the CSS class to use for error and fatal messages
    *
    * @param errorClass The CSS class to use for error and fatal messages
    */
   public void setErrorClass(String errorClass)
   {
      this.errorClass = errorClass;
   }

   /**
    * Get the CSS class to use for INFO messages
    *
    * @return The CSS class to use for INFO messages.
    */
   public String getInfoClass()
   {
      ValueBinding vb = getValueBinding("infoClass");
      if (vb != null)
      {
         this.infoClass = (String)vb.getValue(getFacesContext());
      }

      return this.infoClass;
   }

   /**
    * Set the CSS class to use for INFO messages
    *
    * @param infoClass The CSS class to use for info messages
    */
   public void setInfoClass(String infoClass)
   {
      this.infoClass = infoClass;
   }

   /**
    * Get the CSS class to use for WARN messages
    *
    * @return The CSS class to use for WARN messages.
    */
   public String getWarnClass()
   {
      ValueBinding vb = getValueBinding("warnClass");
      if (vb != null)
      {
         this.warnClass = (String)vb.getValue(getFacesContext());
      }

      return this.warnClass;
   }

   /**
    * Set the CSS class to use for WARN messages
    *
    * @param warnClass The CSS class to use for warn messages
    */
   public void setWarnClass(String warnClass)
   {
      this.warnClass = warnClass;
   }

   // ------------------------------------------------------------------------------
   // Private data


   /**
 * @return the escape
 */
public Boolean getEscape() {
	
	ValueBinding vb = getValueBinding("escape");
    if (vb != null)
    {
       this.escape = (Boolean) vb.getValue(getFacesContext());
    }

    return this.escape;
}


/**
 * @param escape the escape to set
 */
public void setEscape(Boolean escape) {
	this.escape = escape;
}

/** The CSS class to use for general html composant */
   private String styleClass = null;

   /** The CSS class to use for ERROR and FATAL messages */
   private String errorClass = null;

   /** The CSS class to use for INFO messages */
   private String infoClass = null;

   /** The CSS class to use for WARN messages */
   private String warnClass = null;
   
   /** The CSS class to use for WARN messages */
   private Boolean escape = null;

}
