/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.common.renderer.data;

import java.io.IOException;

import javax.faces.context.FacesContext;

import eu.cec.digit.circabc.web.ui.common.component.data.UIColumn;
import eu.cec.digit.circabc.web.ui.common.component.data.UIRichList;

/**
 * Contract for implementations capable of rendering the columns for a Rich List
 * component.
 *
 * @author Guillaume
 */
public interface IRichListRenderer extends org.alfresco.web.ui.common.renderer.data.IRichListRenderer
{
   /**
    * Callback executed by the RichList component to render any adornments before
    * the main list rows are rendered. This is generally used to output header items.
    *
    * @param context       FacesContext
    * @param richList      The parent RichList component
    * @param columns       Array of columns to be shown
    *
    * @throws IOException
    */
   public void renderListBefore(FacesContext context, UIRichList richList, UIColumn[] columns)
      throws IOException;

   /**
    * Callback executed by the RichList component once per row of data to be rendered.
    * The bean used as the current row data is provided, but generally rendering of the
    * column data will be performed by recursively encoding Column child components.
    *
    * @param context       FacesContext
    * @param richList      The parent RichList component
    * @param columns       Array of columns to be shown
    * @param row           The data bean for the current row
    *
    * @throws IOException
    */
   public void renderListRow(FacesContext context, UIRichList richList, UIColumn[] columns, Object row)
      throws IOException;

   /**
    * Callback executed by the RichList component to render any adornments after
    * the main list rows are rendered. This is generally used to output footer items.
    *
    * @param context       FacesContext
    * @param richList      The parent RichList component
    * @param columns       Array of columns to be shown
    *
    * @throws IOException
    */
   public void renderListAfter(FacesContext context, UIRichList richList, UIColumn[] columns)
      throws IOException;

   /**
    * Return the unique view mode identifier that this renderer is responsible for.
    *
    * @return Unique view mode identifier for this renderer e.g. "icons" or "details"
    */
   public String getViewModeID();
}
