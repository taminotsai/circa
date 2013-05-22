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
 * This class seems to be developed for CircaBC
 */
public class MultiListTag extends BaseComponentTag
{
   // ------------------------------------------------------------------------------
   // Component methods

   /**
    * @see javax.faces.webapp.UIComponentTag#getComponentType()
    */
   public String getComponentType()
   {
      return "eu.cec.digit.circabc.faces.MultiList";
   }

   /**
    * @see javax.faces.webapp.UIComponentTag#getRendererType()
    */
   public String getRendererType()
   {
      return "eu.cec.digit.circabc.faces.MultiListRenderer";
   }

   /**
    * @see javax.servlet.jsp.tagext.Tag#release()
    */
   public void release()
   {
      super.release();
      this.value = null;
      this.var = null;
      this.initialSortColumn = null;
      this.initialSortDescending = null;
      this.listConfig = null;
      this.numColumn = null;
      this.styleClass = null;
      this.rowStyleClass = null;
      this.altRowStyleClass = null;
      this.headerStyleClass = null;
      this.pageSize = null;
   }

   /**
    * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
    */
   protected void setProperties(UIComponent component)
   {
      super.setProperties(component);
      setStringBindingProperty(component, "value", this.value);
      setStringStaticProperty(component, "var", this.var);
      setStringProperty(component, "initialSortColumn", this.initialSortColumn);
      setBooleanProperty(component, "initialSortDescending", this.initialSortDescending);
      setStringProperty(component, "listConfig", this.listConfig);
      setIntProperty(component, "numColumn", this.numColumn);
      setStringProperty(component, "styleClass", this.styleClass);
      setStringProperty(component, "rowStyleClass", this.rowStyleClass);
      setStringProperty(component, "altRowStyleClass", this.altRowStyleClass);
      setStringProperty(component, "headerStyleClass", this.headerStyleClass);
      setIntProperty(component, "pageSize", this.pageSize);
   }


   // ------------------------------------------------------------------------------
   // Bean implementation

   /**
    * Set the number of column by row
    *
    * @param numColumn     the number of column by row
    */
   public void setNumColumn(final String numColumn)
   {
      this.numColumn = numColumn;
   }

   /**
    * Set the pageSize
    *
    * @param pageSize     the pageSize
    */
   public void setPageSize(final String pageSize)
   {
      this.pageSize = pageSize;
   }

   /**
    * Set the initialSortColumn
    *
    * @param initialSortColumn     the initialSortColumn
    */
   public void setInitialSortColumn(final String initialSortColumn)
   {
      this.initialSortColumn = initialSortColumn;
   }

   /**
    * Set the initialSortDescending
    *
    * @param initialSortDescending     the initialSortDescending
    */
   public void setInitialSortDescending(final String initialSortDescending)
   {
      this.initialSortDescending = initialSortDescending;
   }

   /**
    * Set the listConfig
    *
    * @param listConfig     the listConfig
    */
   public void setListConfig(final String listConfig)
   {
      this.listConfig = listConfig;
   }

   /**
    * Set the value
    *
    * @param value     the value
    */
   public void setValue(final String value)
   {
      this.value = value;
   }

   /**
    * Set the var
    *
    * @param var     the var
    */
   public void setVar(final String var)
   {
      this.var = var;
   }

   /**
    * Set the styleClass
    *
    * @param styleClass     the styleClass
    */
   public void setStyleClass(final String styleClass)
   {
      this.styleClass = styleClass;
   }

   /**
    * Set the row CSS Class
    *
    * @param rowStyleClass     the the row CSS Class
    */
   public void setRowStyleClass(final String rowStyleClass)
   {
      this.rowStyleClass = rowStyleClass;
   }

   /**
    * Set the alternate row CSS Class
    *
    * @param altRowStyleClass     the alternate row CSS Class
    */
   public void setAltRowStyleClass(final String altRowStyleClass)
   {
      this.altRowStyleClass = altRowStyleClass;
   }

   /**
    * Set the header row CSS Class
    *
    * @param headerStyleClass     the header row CSS Class
    */
   public void setHeaderStyleClass(final String headerStyleClass)
   {
      this.headerStyleClass = headerStyleClass;
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

   /** the var */
   private String var;

   /** the numColumn */
   private String numColumn;

   /** the pageSize */
   private String pageSize;

   /** the initialSortColumn */
   private String initialSortColumn;

   /** the initialSortDescending */
   private String initialSortDescending;

   /** the listConfig */
   private String listConfig;
}
