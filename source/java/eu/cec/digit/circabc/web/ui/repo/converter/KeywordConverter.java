/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;

import eu.cec.digit.circabc.service.keyword.Keyword;
import eu.cec.digit.circabc.web.Services;

/**
 * Converter for a keyword. The list of keywords is displayed as as list of their names.
 *
 * @author yanick pignot
 */
public class KeywordConverter implements Converter
{

	/**
	 * <p>The standard converter id for this converter.</p>
	 */
	public static final String CONVERTER_ID = "eu.cec.digit.circabc.faces.KeywordConverter";

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
	{
		return value;
	}

	@SuppressWarnings("unchecked")
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
	{

		if(value instanceof List)
		{
			return getDisplayString(context, (List) value);
		}
		else
		{
			return "";
		}


	}

	public static String getDisplayString(FacesContext context, List keywords)
	{
		if(keywords == null || keywords.size() < 1)
		{
			return "";
		}
		
		final StringBuffer buff = new StringBuffer("");

		final NodeService nodeService = Services.getAlfrescoServiceRegistry(context).getNodeService();

		boolean first = true;
		NodeRef nodeRef = null;

		for (Object keyword : keywords)
		{
			if(keyword != null)
			{
				if(keyword instanceof NodeRef)
				{
					nodeRef = (NodeRef) keyword;
				}
				else if(keyword instanceof Keyword)
				{
					nodeRef = ((Keyword) keyword).getId();
				}

				if(nodeRef == null)
				{
					throw new IllegalArgumentException("The list of keywords accepted must be either a list of NodeRef either a list of Keyword");
				}

				if(first)
				{
					first = false;
				}
				else
				{
					buff.append(", ");
				}

				buff.append(nodeService.getProperty(nodeRef, ContentModel.PROP_TITLE));
			}
		}

		return buff.toString();
	}


}
