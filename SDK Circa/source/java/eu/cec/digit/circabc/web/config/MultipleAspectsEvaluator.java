/*--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--*/
package eu.cec.digit.circabc.web.config;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.alfresco.service.namespace.QName;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.bean.repository.Repository;
import org.springframework.extensions.config.evaluator.Evaluator;

/**
 * Evaluator that determines whether a given object has ALL particular aspects applied.
 *
 * @author patrice.coppens@trasys.lu
 */
public class MultipleAspectsEvaluator implements Evaluator {
	/** condition separator.*/
	public static final String SEPARATOR = ";" ;


	/** "not" prefixe.*/
	public static final String NOT = "!";

	/**
	 * Determines whether the given aspects are applied to the given object.
	 * @see org.alfresco.config.evaluator.Evaluator#applies(java.lang.Object, java.lang.String)
	 * @param obj to evaluate.
	 * @param condition all aspects separate with ';'
	 * @return boolean true if ALL aspect are applied
	 */
	public boolean applies(Object obj, String condition)
	{
		  List<String> aspectCondition= Arrays.asList(condition.split(SEPARATOR)) ;

	      if (obj instanceof Node)
	      {
	         Set aspects = ((Node)obj).getAspects();
	         if (aspects != null)
	         {
	        	 for (String aspect : aspectCondition)
	        	 {

	        		 // check if ! is present
	        		 if(aspect.startsWith(NOT))
	        		 {
	        			//must not be present
	        			final String as= aspect.substring(1);
	        			final QName spaceQName = Repository.resolveToQName(as);

	        			//return false if at least one aspect with '!" is present.
	 	        		if(aspects.contains(spaceQName))
	 	        		{
	 	        			return false;
	 	        		}
	        		 }

	        		 //must be present
	        		 else
	        		 {
	        			 final QName spaceQName = Repository.resolveToQName(aspect);

	        			 //return false if at least one aspect is not present.
	 	        		 if(! aspects.contains(spaceQName))
	 	 	             {
	 	        			return false;
	 	        		 }
	        		 }
				}
	        	 //all aspects are present.
		         return true;
	         }
	      }
	      //object has no aspect
	      return false;
	}

}
