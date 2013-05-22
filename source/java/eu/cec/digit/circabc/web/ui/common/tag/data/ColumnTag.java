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
 * @author Guillaume
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * BaseComponentTag was moved to Spring Surf.
 * Class implementation didn't change between Alfresco versions.
 */
public class ColumnTag extends BaseComponentTag
{
   /**
    * @see javax.faces.webapp.UIComponentTag#getComponentType()
    */
   public String getComponentType()
   {
      return "eu.cec.digit.circabc.faces.ListColumn";
   }

   /**
    * @see javax.faces.webapp.UIComponentTag#getRendererType()
    */
   public String getRendererType()
   {
      // the component is renderer by the parent
      return null;
   }

   /**
    * @see javax.servlet.jsp.tagext.Tag#release()
    */
   public void release()
   {
      super.release();
      this.primary = null;
      this.actions = null;
      this.styleClass = null;
   }

   /**
    * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
    */
   protected void setProperties(UIComponent component)
   {
      super.setProperties(component);

      setBooleanProperty(component, "primary", this.primary);
      setBooleanProperty(component, "actions", this.actions);
      setStringProperty(component, "styleClass", this.styleClass);
   }


   // ------------------------------------------------------------------------------
   // Tag properties

   /**
    * Set if this is the primary column
    *
    * @param primary     the primary if "true", otherwise false
    */
   public void setPrimary(String primary)
   {
      this.primary = primary;
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
    * Set if this is the actions column
    *
    * @param actions     the actions if "true", otherwise false
    */
   public void setActions(String actions)
   {
      this.actions = actions;
   }


   /** the actions */
   private String actions;

   /** the styleClass */
   private String styleClass;

   /** the primary */
   private String primary;
}
