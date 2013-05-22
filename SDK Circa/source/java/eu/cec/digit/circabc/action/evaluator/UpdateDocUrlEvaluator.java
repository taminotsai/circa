package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.web.action.evaluator.UpdateDocEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.model.DocumentModel;

/**
 * UpdateDocURL Evaluator - Extends the update evaluation for an URL content.
 *
 * @author David Ferraz
 */
public class UpdateDocUrlEvaluator extends UpdateDocEvaluator
{
   private static final long serialVersionUID = 6040963610213633893L;

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
