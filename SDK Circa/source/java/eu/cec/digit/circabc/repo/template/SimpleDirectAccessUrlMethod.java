/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.repo.template;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.web.servlet.ExternalAccessServlet;
import freemarker.template.TemplateMethodModelEx;

/**
 * Freemarker method to return the direct access url of a node.
 * THis to render url without facescontext (DirectAccessUrlMethod does not work in cron job as it is tied to facescontext)
 *
 * @author Pierre Beauregard
 */
public class SimpleDirectAccessUrlMethod extends NodeRefBaseTemplateProcessorExtension implements TemplateMethodModelEx
{


	@Override
	public String getResult(NodeRef nodeRef)
	{
		return ExternalAccessServlet.getServerContext()+"/w/browse/"+nodeRef.getId();
	}
}