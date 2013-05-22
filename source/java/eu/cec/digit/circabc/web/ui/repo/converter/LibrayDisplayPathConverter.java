/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.converter;

import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.web.ui.repo.converter.DisplayPathConverter;

import eu.cec.digit.circabc.service.struct.ManagementService;
import eu.cec.digit.circabc.web.Services;

/**
 * The display path of a file link or a folder link must start from the libray
 *
 * @author yanick pignot
 */
public class LibrayDisplayPathConverter extends DisplayPathConverter
{

	/**
	 * <p>The standard converter id for this converter.</p>
	 */
	public static final String CONVERTER_ID = "eu.cec.digit.circabc.faces.LibraryDisplayPathConverter";

	private String circabcRootPath = null;

	public LibrayDisplayPathConverter()
	{
		super();
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
		String path = super.getAsString(context, component, value);

		if(path != null && path.startsWith(getCircabcRootPath(context)))
		{
			final StringTokenizer tokens = new StringTokenizer(path, "/", false);

			int tokenCount = tokens.countTokens();

			if(tokenCount > 4)
			{
				// the token count should be upper that 4 ..
				// remove the 4 first element that should be: /Company Home/CircaBC/Category/InterestGroup/

				final StringBuffer buff = new StringBuffer(path.length());
				String token = null;

				for(int c = 0; c < tokenCount; ++c)
				{
					token = tokens.nextToken();
					if(c >= 4)
					{
						buff
							.append('/')
							.append(token);

					}
				}

				path = buff.toString();
			}

		}

		return path;
    }


	private String getCircabcRootPath(FacesContext context)
	{
		if(circabcRootPath == null)
		{
			final ManagementService managementService = Services.getCircabcServiceRegistry(context).getManagementService();
			final NodeService nodeService = Services.getAlfrescoServiceRegistry(context).getNodeService();

			final StringBuffer buff = new StringBuffer();
			buff
				.append('/')
				.append(nodeService.getProperty(managementService.getCompanyHomeNodeRef(), ContentModel.PROP_NAME))
				.append('/')
				.append(nodeService.getProperty(managementService.getCircabcNodeRef(), ContentModel.PROP_NAME))
				.append('/');

			this.circabcRootPath = buff.toString();

		}
		return circabcRootPath;
	}
}
