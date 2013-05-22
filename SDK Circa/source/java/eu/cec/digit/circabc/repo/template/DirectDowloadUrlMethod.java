/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.repo.template;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.web.WebClientHelper;
import eu.cec.digit.circabc.web.WebClientHelper.ExtendedURLMode;
import freemarker.template.TemplateMethodModelEx;

/**
 * Freemarker method to return the direct download url of a content.
 *
 * @author Yanick Pignot
 */
public class DirectDowloadUrlMethod extends NodeRefBaseTemplateProcessorExtension implements TemplateMethodModelEx
{

	@Override
	public String getResult(NodeRef nodeRef)
	{
		return WebClientHelper.getGeneratedWaiFullUrl(nodeRef, ExtendedURLMode.HTTP_DOWNLOAD);
	}
}