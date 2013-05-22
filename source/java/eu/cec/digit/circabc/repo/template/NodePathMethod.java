/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.template;

import javax.jcr.PathNotFoundException;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.repo.struct.SimplePath;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class NodePathMethod extends NodeRefBaseTemplateProcessorExtension implements TemplateMethodModelEx
{

	@Override
	public String getResult(NodeRef nodeRef)throws TemplateModelException
	{
		try
		{
			SimplePath path = new SimplePath(getNodeService(), nodeRef);
			return path.toString();
		}
		catch (PathNotFoundException e)
		{
			throw new TemplateModelException(e);
		}

	}

}
