/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.template;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import freemarker.template.TemplateMethodModelEx;

/**
 * Freemarker method to display the title of a node or its name if no title
 *
 * @author Yanick Pignot
 */
public class TitleOrNameMethod extends NodeRefBaseTemplateProcessorExtension implements TemplateMethodModelEx
{

	@Override
	public String getResult(final NodeRef nodeRef)
	{

		final Map<QName, Serializable> props = getNodeService().getProperties(nodeRef);

		final String title = (String) props.get(ContentModel.PROP_TITLE);

		if(title == null || title.length() < 1)
		{
			return (String) props.get(ContentModel.PROP_NAME);
		}
		else
		{
			return title;
		}
	}


}