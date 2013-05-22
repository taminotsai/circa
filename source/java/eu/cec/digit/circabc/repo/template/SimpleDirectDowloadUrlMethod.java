/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.repo.template;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.web.servlet.ExternalAccessServlet;
import freemarker.template.TemplateMethodModelEx;

/**
 * Freemarker method to return the direct download url of a content.
 * THis to render url without facescontext (DirectAccessUrlMethod does not work in cron job as it is tied to facescontext)
 *
 * @author Pierre Beauregard
 */
public class SimpleDirectDowloadUrlMethod extends NodeRefBaseTemplateProcessorExtension implements TemplateMethodModelEx
{

	@Override
	public String getResult(NodeRef nodeRef)
	{
		return ExternalAccessServlet.getServerContext()+"/d/d/workspace/SpacesStore/"+nodeRef.getId()+"/"+super.getNodeService().getProperty(nodeRef, ContentModel.PROP_NAME).toString();

	}
}