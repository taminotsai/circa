/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.common.tag.data;

import javax.faces.component.UIComponent;

import org.springframework.extensions.webscripts.ui.common.tag.BaseComponentTag;


/**
 * @author Yanick Pignot
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * BaseComponentTag was moved to Spring Surf.
 * This class seems to be developed for CircaBC
 */
public class CustomListTag extends BaseComponentTag
{
   // ------------------------------------------------------------------------------
   // Component methods

   /**
    * @see javax.faces.webapp.UIComponentTag#getComponentType()
    */
   public String getComponentType()
   {
      return "eu.cec.digit.circabc.faces.CustomList";
   }

   /**
    * @see javax.faces.webapp.UIComponentTag#getRendererType()
    */
   public String getRendererType()
   {
      return "eu.cec.digit.circabc.faces.CustomListRenderer";
   }

   /**
    * @see javax.servlet.jsp.tagext.Tag#release()
    */
   public void release()
   {
      super.release();
      this.value = null;
      this.styleClass = null;
      this.rowStyleClass = null;
      this.altRowStyleClass = null;
      this.headerStyleClass = null;
      this.configuration = null;
   }

   /**
    * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
    */
   protected void setProperties(UIComponent component)
   {
      super.setProperties(component);
      setStringBindingProperty(component, "value", this.value);
      setStringProperty(component, "styleClass", this.styleClass);
      setStringProperty(component, "rowStyleClass", this.rowStyleClass);
      setStringProperty(component, "altRowStyleClass", this.altRowStyleClass);
      setStringProperty(component, "headerStyleClass", this.headerStyleClass);
      setStringBindingProperty(component, "configuration", this.configuration);
   }


   // ------------------------------------------------------------------------------
   // Bean implementation

   /**
    * Set the value
    *
    * @param value     the value
    */
   public void setValue(String value)
   {
      this.value = value;
   }

   /**
    * Set the styleClass
    *
    * @param styleClass     the styleClass
    */
   public void setStyleClass(String styleClass)
   {
      this.styleClass = styleClass;
   }

   /**
    * Set the the row CSS Class
    *
    * @param rowStyleClass     the the row CSS Class
    */
   public void setRowStyleClass(String rowStyleClass)
   {
      this.rowStyleClass = rowStyleClass;
   }

   /**
    * Set the alternate row CSS Class
    *
    * @param altRowStyleClass     the alternate row CSS Class
    */
   public void setAltRowStyleClass(String altRowStyleClass)
   {
      this.altRowStyleClass = altRowStyleClass;
   }

   /**
    * Set the header row CSS Class
    *
    * @param headerStyleClass     the header row CSS Class
    */
   public void setHeaderStyleClass(String headerStyleClass)
   {
      this.headerStyleClass = headerStyleClass;
   }

   /**
	* @return the configuration
	*/
   public final String getConfiguration()
   {
	   return configuration;
   }

   /**
	* @param configuration the configuration to set
	*/
   public final void setConfiguration(String configuration)
   {
	   this.configuration = configuration;
   }

// ------------------------------------------------------------------------------
   // Private data

   /** the header row CSS Class */
   private String headerStyleClass;

   /** the row CSS Class */
   private String rowStyleClass;

   /** the alternate row CSS Class */
   private String altRowStyleClass;

   /** the styleClass */
   private String styleClass;

   /** the value */
   private String value;

   /** the navigation preference */
   private String configuration;
}
