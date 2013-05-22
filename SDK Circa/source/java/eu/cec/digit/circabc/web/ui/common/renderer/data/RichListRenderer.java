/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.common.renderer.data;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.alfresco.web.ui.common.Utils;
import org.alfresco.web.ui.common.renderer.BaseRenderer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.web.ui.common.component.data.UIColumn;
import eu.cec.digit.circabc.web.ui.common.component.data.UIRichList;

/**
 * @author Guillaume
 */
public class RichListRenderer extends BaseRenderer
{
   // ------------------------------------------------------------------------------
   // Renderer implemenation

   /**
    * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
    */
   public void encodeBegin(FacesContext context, UIComponent component)
         throws IOException
   {
      // always check for this flag - as per the spec
      if (component.isRendered() == true)
      {
    	 logger.info("RichList - encodeBegin");
         ResponseWriter out = context.getResponseWriter();
         Map attrs = component.getAttributes();
         out.write("<table");
         outputAttribute(out, attrs.get("styleClass"), "class");
         out.write(">");
      }
   }

   /**
    * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
    */
   public void encodeChildren(FacesContext context, UIComponent component)
         throws IOException
   {
      if (component.isRendered() == true)
      {
         // the RichList component we are working with
         UIRichList richList = (UIRichList)component;

         // prepare the component current row against the current page settings
         richList.bind();

         // collect child column components so they can be passed to the renderer
         List<UIColumn> columnList = new ArrayList<UIColumn>(8);
         for (Iterator i=richList.getChildren().iterator(); i.hasNext(); /**/)
         {
            UIComponent child = (UIComponent)i.next();
            if (child instanceof UIColumn)
            {
               columnList.add((UIColumn)child);
            }
         }

         UIColumn[] columns = new UIColumn[columnList.size()];
         columnList.toArray(columns);

         // get the renderer instance
         IRichListRenderer renderer = (IRichListRenderer)richList.getViewRenderer();
         if (renderer == null)
         {
            throw new IllegalStateException("IRichListRenderer must be available in UIRichList!");
         }

         // call render-before to output headers if required
         ResponseWriter out = context.getResponseWriter();
         out.write("<thead>");
         renderer.renderListBefore(context, richList, columns);
         out.write("</thead>");
         out.write("<tbody>");
         if (richList.isDataAvailable() == true)
         {
            while (richList.isDataAvailable() == true)
            {
               // render each row in turn
               renderer.renderListRow(context, richList, columns, richList.nextRow());
            }
         }
         else
         {
            // if no items present, render the facet with the "no items found" message
        	out.write("<tr><td");
        	String rowStyleClass = (String)richList.getAttributes().get("rowStyleClass");
        	if (rowStyleClass != null) {
        		out.write(" class=\"");
        		out.write(rowStyleClass);
        		out.write('"');
        	}
        	out.write('>');

            UIComponent emptyComponent = richList.getEmptyMessage();
            if (emptyComponent != null)
            {
               emptyComponent.encodeBegin(context);
               emptyComponent.encodeChildren(context);
               emptyComponent.encodeEnd(context);
            }
            out.write("</td></tr>");
         }
         // call render-after to output footers if required
         renderer.renderListAfter(context, richList, columns);
         out.write("</tbody>");
      }
   }

   /**
    * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
    */
   public void encodeEnd(FacesContext context, UIComponent component)
         throws IOException
   {
      // always check for this flag - as per the spec
      if (component.isRendered() == true)
      {
         ResponseWriter out = context.getResponseWriter();
         out.write("</table>");
         logger.info("RichList - encodeEnd");
      }
   }

   /**
    * @see javax.faces.render.Renderer#getRendersChildren()
    */
   public boolean getRendersChildren()
   {
      // we are responsible for rendering our child components
      // this renderer is a valid use of this mode - it can render the various
      // column descriptors as a number of different list view types e.g.
      // details, icons, list etc.
      return true;
   }


   // ------------------------------------------------------------------------------
   // Inner classes

   /**
    * Class to implement a Circa view for the RichList component
    *
    * @author Guillaume
    */
   public static class CircaViewRenderer implements IRichListRenderer
   {
      /** */
	private static final long serialVersionUID = -8613868143625579365L;

	public static final String VIEWMODEID = "circa";

      public String getViewModeID()
      {
         return VIEWMODEID;
      }

      public void renderListBefore(FacesContext context, UIRichList richList, UIColumn[] columns)
            throws IOException
      {
         ResponseWriter out = context.getResponseWriter();

         // render column headers as labels
         out.write("<tr>");
         String headerClass = (String)richList.getAttributes().get("headerStyleClass");
         for (int i=0; i<columns.length; i++)
         {
            UIColumn column = columns[i];

            if (column.isRendered() == true || column.getPrimary())
            {
            	// Increment the number of rendered columns
            	numColumnRender++;
               // render column header tag
               out.write("<th");
               String columnClass = (String)column.getAttributes().get("styleClass");
               outputAttribute(out, columnClass != null ? columnClass : headerClass, "class");
               out.write('>');

               // output the header facet if any
               UIComponent header = column.getHeader();
               if (header != null)
               {
                  header.encodeBegin(context);
                  if (header.getRendersChildren())
                  {
                     header.encodeChildren(context);
                  }
                  header.encodeEnd(context);
               }

               // we don't render child controls for the header row
               out.write("</th>");
            }
         }
         out.write("</tr>");

         this.rowIndex = 0;
      }

      public void renderListRow(FacesContext context, UIRichList richList, UIColumn[] columns, Object row)
            throws IOException
      {
         ResponseWriter out = context.getResponseWriter();

         // output row or alt style row if set
         out.write("<tr");
         String rowStyle = (String)richList.getAttributes().get("rowStyleClass");
         String altStyle = (String)richList.getAttributes().get("altRowStyleClass");
         if (altStyle != null && (this.rowIndex++ & 1) == 1)
         {
            rowStyle = altStyle;
         }
         outputAttribute(out, rowStyle, "class");
         out.write('>');

         // find the actions column if it exists
         UIColumn actionsColumn = null;
         for (int i=0; i<columns.length; i++)
         {
            if (columns[i].isRendered() == true && columns[i].getActions() == true)
            {
               actionsColumn = columns[i];
               break;
            }
         }

         // Boolean to tell if the icon has already been put
         boolean renderedFirst = false;
         // output each column in turn and render all children
         for (int i=0; i<columns.length; i++)
         {
            UIColumn column = columns[i];

            if (column.isRendered() == true)
            {
               out.write("<td");
               outputAttribute(out, column.getAttributes().get("styleClass"), "class");
               out.write('>');

               // for details view, we show the small column icon for the first column
               if (renderedFirst == false)
               {
                  UIComponent smallIcon = column.getSmallIcon();
                  if (smallIcon != null)
                  {
                     smallIcon.encodeBegin(context);
                     if (smallIcon.getRendersChildren())
                     {
                        smallIcon.encodeChildren(context);
                     }
                     smallIcon.encodeEnd(context);
                     out.write("&nbsp;");
                  }
                  renderedFirst = true;
               }

               // The Standard content
               if (column.getChildCount() != 0)
               {
                  if (column == actionsColumn)
                  {
                     out.write("<nobr><span class=\"actionContainer\">");
                  }

                  // allow child controls inside the columns to render themselves
                  Utils.encodeRecursive(context, column);

                  if (column == actionsColumn)
                  {
                     out.write("</span></nobr>");
                  }
               }

               out.write("</td>");
            }
         }
         out.write("</tr>");
      }

      public void renderListAfter(FacesContext context, UIRichList richList, UIColumn[] columns)
            throws IOException
      {
    	  // Children which are not UIClomun and have to be rendered
    	  ArrayList<UIComponent> childToRender = new ArrayList<UIComponent>();
    	  // check if there are components to render
    	  if (!richList.getChildren().isEmpty()) {
    		  for (Iterator i=richList.getChildren().iterator(); i.hasNext(); /**/)
 	         {
 	            UIComponent child = (UIComponent)i.next();
 	            if ((child instanceof UIColumn == false) && (child.isRendered()))
 	            {
 	            	childToRender.add(child);
 	            }
 	         }
    	  }
    	  if (!childToRender.isEmpty()) {
    		 //	output all remaining child components that are not UIColumn
    		 ResponseWriter out = context.getResponseWriter();

	         String nsColumn = numColumnRender > 1 ? "colspan=\""+numColumnRender+"\"" : "";
	         out.write("<tr><td "+nsColumn+">");

	         for (UIComponent component : childToRender) {
	        	 Utils.encodeRecursive(context, component);
	         }
	         out.write("</td></tr>");
    	  }
      }

      private int rowIndex = 0;

      /* The number of Column which are rendered */
      private int numColumnRender = 0;

		public void renderListAfter(FacesContext context, org.alfresco.web.ui.common.component.data.UIRichList richList, org.alfresco.web.ui.common.component.data.UIColumn[] columns) throws IOException
		{
			throw new IllegalStateException("Original Alfresco JSF client not managed");
		}

		public void renderListBefore(FacesContext context, org.alfresco.web.ui.common.component.data.UIRichList richList, org.alfresco.web.ui.common.component.data.UIColumn[] columns) throws IOException
		{
			throw new IllegalStateException("Original Alfresco JSF client not managed");
		}

		public void renderListRow(FacesContext context, org.alfresco.web.ui.common.component.data.UIRichList richList, org.alfresco.web.ui.common.component.data.UIColumn[] columns, Object row) throws IOException
		{
			throw new IllegalStateException("Original Alfresco JSF client not managed");
		}
   }

   private static final Log logger = LogFactory.getLog(RichListRenderer.class);
}
