/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.ui.repo.tag.evaluator;

import javax.faces.component.UIComponent;
import javax.servlet.jsp.JspException;

import org.alfresco.web.ui.common.tag.evaluator.GenericEvaluatorTag;

import eu.cec.digit.circabc.web.ui.repo.component.evaluator.PermissionEvaluator;

/**
 * @author Clinckart Stephane
 */
public class PermissionEvaluatorTag extends GenericEvaluatorTag
{
   /**
    * @see javax.faces.webapp.UIComponentTag#getComponentType()
    */
   public String getComponentType()
   {
      return "eu.cec.digit.circabc.faces.PermissionEvaluator";
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
      if (component instanceof PermissionEvaluator)
      {
         if (((PermissionEvaluator)component).evaluate() == true)
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

   /**
    * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
    */
   protected void setProperties(UIComponent component)
   {
      super.setProperties(component);
      setStringProperty(component, "allow", this.allow);
      setStringProperty(component, "deny", this.deny);
   }

   /**
    * @see javax.servlet.jsp.tagext.Tag#release()
    */
   public void release()
   {
      super.release();
      this.allow = null;
      this.deny = null;
   }

   /**
    * Set the allow permissions
    *
    * @param allow     the allow permissions
    */
   public void setAllow(String allow)
   {
      this.allow = allow;
   }

   /**
    * Set the deny permissions
    *
    * @param deny     the deny permissions
    */
   public void setDeny(String deny)
   {
      this.deny = deny;
   }


   /** the allow permissions */
   private String allow;

   /** the deny permissions */
   private String deny;
}
