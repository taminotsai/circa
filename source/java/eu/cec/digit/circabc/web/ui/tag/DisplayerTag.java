/*--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--*/
package eu.cec.digit.circabc.web.ui.tag;

import javax.faces.component.UIComponent;
import javax.servlet.jsp.JspException;

import org.springframework.extensions.webscripts.ui.common.tag.BaseComponentTag;

import eu.cec.digit.circabc.web.ui.component.UIDisplayer;

/**
 * @author Guillaume
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * BaseComponentTag was moved to Spring Surf.
 * This class seems to be developed for CircaBC
 */
public class DisplayerTag extends BaseComponentTag
{
   /**
    * @see javax.faces.webapp.UIComponentTag#getComponentType()
    */
   public String getComponentType()
   {
      return "eu.cec.digit.circabc.faces.Displayer";
   }

   /**
    * @see javax.faces.webapp.UIComponentTag#getRendererType()
    */
   public String getRendererType()
   {
      // the component is self renderering
      return null;
   }

   /**
    * Override this to allow the displayer component to control whether child components
    * are rendered by the JSP tag framework. This is a nasty solution as it requires
    * a reference to the UIDisplayer instance and also specific knowledge of the component
    * type that is created by the framework for this tag.
    *
    * The reason for this solution is to allow any child content (including HTML tags)
    * to be displayed inside the UIDisplayer component without having to resort to the
    * awful JSF Component getRendersChildren() mechanism - as this would force the use
    * of the verbatim tags for ALL non-JSF child content!
    */
   protected int getDoStartValue() throws JspException
   {
      UIComponent component = getComponentInstance();
      if (component instanceof UIDisplayer)
      {
         if (component.isRendered() == true)
         {
            return EVAL_BODY_INCLUDE;
         }
         else
         {
            return SKIP_BODY;
         }
      }
      return EVAL_BODY_INCLUDE;
   }
}
