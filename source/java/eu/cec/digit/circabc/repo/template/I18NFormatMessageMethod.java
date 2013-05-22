/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.template;

import java.util.ArrayList;
import java.util.List;

import org.alfresco.repo.template.BaseTemplateProcessorExtension;
import org.springframework.extensions.surf.util.I18NUtil;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

/**
 * Freemarker method to translate a key and to apply some optional parameters
 *
 * @author Yanick Pignot
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * I18NUtil was moved to Spring.
 * This class seems to be developed for CircaBC
 */
public class I18NFormatMessageMethod extends BaseTemplateProcessorExtension implements TemplateMethodModelEx
{
	/**
     * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
     */
    @SuppressWarnings("unchecked")
	public Object exec(List args) throws TemplateModelException
    {
        String result = "";

        final int size = args.size();

        String key = null;
        List params = null;

        if(size > 0)
        {
        	boolean first = true;

        	for(Object arg : args)
        	{
    		   if (arg instanceof TemplateScalarModel)
               {
    			   if(first)
    			   {
    				   key = ((TemplateScalarModel)arg).getAsString();

    				   first = false;
    			   }
    			   else
    			   {
    				   if(params == null)
    				   {
    					   params = new ArrayList(size - 1);
    				   }
    				   params.add(((TemplateScalarModel)arg).getAsString());
    			   }
               }
        	}

        }

        if(key != null)
        {
        	if(params != null)
        	{
        		result = I18NUtil.getMessage(key, params.toArray());
        	}
        	else
        	{
        		result = I18NUtil.getMessage(key);
        	}
        }

        return result == null ? "" : result ;
    }

    public void setWebMessageBundle(String bundle)
    {
    	I18NUtil.registerResourceBundle(bundle);
    }
}