package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.web.action.evaluator.CheckoutDocEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.model.DocumentModel;

/**
 * CheckoutDocURL Evaluator - Extends the check-out evaluation for an URL content.
 *
 * @author David Ferraz
 */
public class CheckoutDocUrlEvaluator extends CheckoutDocEvaluator
{
   private static final long serialVersionUID = 6050963610213633893L;

   /**
    * @see org.alfresco.web.action.ActionEvaluator#evaluate(org.alfresco.web.bean.repository.Node)
    */
   public boolean evaluate(Node node)
   {
	   if(super.evaluate(node))
	   {
		   return !node.hasAspect(DocumentModel.ASPECT_URLABLE);
	   }
	   else
	   {
		   return false;
	   }
   }
}
