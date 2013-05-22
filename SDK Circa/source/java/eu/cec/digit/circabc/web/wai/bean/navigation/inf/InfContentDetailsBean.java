/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.bean.navigation.inf;

import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.wai.bean.navigation.library.ContentDetailsBean;
import eu.cec.digit.circabc.web.wai.manager.ActionsListWrapper;

/**
 * Bean that backs the navigation inside the content details in the Library Service
 *
 * @author yanick pignot
 */
public class InfContentDetailsBean extends ContentDetailsBean
{
	/** */
	private static final long serialVersionUID = -1967666575499663894L;

	public static final String BEAN_NAME = "InfContentDetailsBean";

	public NavigableNodeType getManagedNodeType()
	{
		return NavigableNodeType.INFORMATION_CONTENT;
	}

	public String getRelatedJsp()
	{
		return NAVIGATION_JSP_FOLDER + "inf/" + JSP_NAME;
	}

	public String getPageDescription()
	{
		return translate(MSG_PAGE_DESCRIPTION, getCurrentNode().getName());
	}

	@Override
	public ActionsListWrapper getActionList()
	{
		if(!getDocument().isLocked())
		{
			return new ActionsListWrapper(getDocument(), "inf_doc_details_actions_wai");
		}
		else
		{
			return null;
		}
	}
}
