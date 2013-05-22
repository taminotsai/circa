/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.tag;

import javax.faces.component.UIComponent;

import org.alfresco.web.ui.common.tag.HtmlComponentTag;

/**
 * @author yanick Pignot
 */
public class SearchDynamicPropertiesTag extends HtmlComponentTag
{
   /**
    * @see javax.faces.webapp.UIComponentTag#getComponentType()
    */
   public String getComponentType()
   {
	   return "eu.cec.digit.circabc.faces.AdvancedSearch";
   }

   /**
    * @see javax.faces.webapp.UIComponentTag#getRendererType()
    */
   public String getRendererType()
   {
      // self rendering component
      return null;
   }

   /**
    * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
    */
   protected void setProperties(UIComponent component)
   {
      super.setProperties(component);

      setStringProperty(component, "bean", this.bean);
      setStringProperty(component, "var", this.var);
   }

   /**
    * @see org.alfresco.web.ui.common.tag.HtmlComponentTag#release()
    */
   public void release()
   {
      super.release();

      this.bean = null;
      this.var = null;
   }

   /**
    * Set the bean reference
    *
    * @param bean     the bean reference
    */
   public void setBean(String bean)
   {
      this.bean = bean;
   }

   /**
    * Set the var
    *
    * @param var     the var
    */
   public void setVar(String var)
   {
      this.var = var;
   }


   /** the bean reference */
   private String bean;

   /** the variable Map reference */
   private String var;
}
