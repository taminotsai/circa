/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.template;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.Path;

import eu.cec.digit.circabc.util.PathUtils;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class LibraryNodePathMethod extends NodeRefBaseTemplateProcessorExtension implements TemplateMethodModelEx
{

	@Override
	public String getResult(NodeRef nodeRef)throws TemplateModelException
	{
			Path path = getNodeService().getPath(nodeRef);			    
			boolean includeFirstSlash = true;
			return PathUtils.getLibraryPath(path, includeFirstSlash);
	}

}
