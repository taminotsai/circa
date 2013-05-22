/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.template;

import java.util.List;

import org.alfresco.repo.template.BaseTemplateProcessorExtension;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * Freemarker method to concat any object as a String
 *
 * @author Yanick Pignot
 */
public class ConcatAsStringMethod extends BaseTemplateProcessorExtension implements TemplateMethodModelEx
{
    /**
     * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
     */
    @SuppressWarnings("unchecked")
	public Object exec(List args) throws TemplateModelException
    {
    	StringBuffer result = new StringBuffer("");

        for(Object obj : args)
        {
        	if(obj != null)
        	{
        		result.append(obj.toString());
        	}
        }

        return result.toString() ;
    }
}