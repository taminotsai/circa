/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.ui.repo.converter;

import java.text.MessageFormat;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.business.api.user.UserDetails;
import eu.cec.digit.circabc.web.WebClientHelper;
import eu.cec.digit.circabc.web.WebClientHelper.ExtendedURLMode;

/**
 * @author Yanick Pignot
 *
 */
public class HTMLUseridConverter extends UseridConverter
{
	private static final String LINK_HTML = "<a href=\"{0}\" title=\"{1}\" >{2}</a>";
	private static final String MSG_TITLE = "view_user_details_url_tooltip_wai";

	public static final String CONVERTER_ID = "eu.cec.digit.circabc.faces.HTMLUseridConverter";


	@Override
	protected String getTextContent(final UserDetails userDetails)
	{
		if(userDetails.isUserCreated())
		{
			final NodeRef person = userDetails.getNodeRef();
			final String url = WebClientHelper.getGeneratedWaiFullUrl(person, ExtendedURLMode.HTTP_USERDETAILS);
			final String name = userDetails.getFullName();
			final String title = WebClientHelper.translate(MSG_TITLE, name);
			return MessageFormat.format(LINK_HTML, url, title, name);
		}
		else
		{
			return super.getTextContent(userDetails);
		}
	}

}
