/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.template;

import java.util.List;

import org.alfresco.repo.template.BaseTemplateProcessorExtension;
import org.alfresco.repo.template.TemplateNode;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;

import freemarker.ext.beans.BeanModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public abstract class NodeRefBaseTemplateProcessorExtension extends BaseTemplateProcessorExtension implements TemplateMethodModelEx
{
	private NodeService nodeService;
	/**
	 * @return the nodeService
	 */
	protected final NodeService getNodeService()
	{
		return nodeService;
	}

	/**
	 * @param nodeService the nodeService to set
	 */
	public final void setNodeService(NodeService services)
	{
		this.nodeService = services;
	}

	public Object exec(List args) throws TemplateModelException
	{
		String result = "";

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

	public abstract String getResult(NodeRef nodeRef) throws TemplateModelException;

}
