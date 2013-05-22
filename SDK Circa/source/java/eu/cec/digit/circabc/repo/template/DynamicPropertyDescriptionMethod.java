/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.alfresco.repo.template.BaseTemplateProcessorExtension;
import org.alfresco.repo.template.TemplateNode;
import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.service.dynamic.property.DynamicProperty;
import eu.cec.digit.circabc.service.dynamic.property.DynamicPropertyService;
import freemarker.ext.beans.BeanModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class DynamicPropertyDescriptionMethod extends BaseTemplateProcessorExtension implements TemplateMethodModelEx
{
	private DynamicPropertyService dynamicPropertiesService;
	
	public Object exec(List args) throws TemplateModelException
	{
		List<String> result = Collections.emptyList();
    	if (args.size() == 1)
        {
            // arg 0 must be a wrapped TemplateNode object
            final BeanModel arg0 = (BeanModel)args.get(0);

            NodeRef ref = null;
            if(arg0.getWrappedObject() instanceof NodeRef)
            {
            	ref = (NodeRef) arg0.getWrappedObject();
            }
            else if (arg0.getWrappedObject() instanceof TemplateNode)
            {
            	final TemplateNode templateNode = (TemplateNode)arg0.getWrappedObject();

            	ref = templateNode.getNodeRef();
            }

            if(ref != null)
            {
            	result = getResult(ref);
            }
        }

    	return result;
	}

	private List<String> getResult(NodeRef ref)
	{
		List<String>  result = new ArrayList<String>(); 
		final List<DynamicProperty> dynamicProperties = dynamicPropertiesService.getDynamicProperties(ref);
		for (DynamicProperty dynamicProperty : dynamicProperties)
		{
			result.add(dynamicProperty.getLabel().getDefaultValue());
		}
		return result;
	}

	public void setDynamicPropertiesService(DynamicPropertyService dynamicPropertiesService)
	{
		this.dynamicPropertiesService = dynamicPropertiesService;
	}

	public DynamicPropertyService getDynamicPropertiesService()
	{
		return dynamicPropertiesService;
	}




}
