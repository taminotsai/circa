/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.bean.navigation.library;

import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.wai.manager.ActionsListWrapper;

/**
 * Bean that backs the navigation inside the file link details in the Library Service
 *
 * @author yanick pignot
 */
public class FileLinkDetailsBean extends ContentDetailsBean
{
	/** */
	private static final long serialVersionUID = -1967164575499663894L;

	public static final String BEAN_NAME = "LibFileLinkDetailsBean";
	public static final String JSP_NAME  = "file-link-details.jsp";

	@Override
	public NavigableNodeType getManagedNodeType()
	{
		return NavigableNodeType.LIBRARY_FILE_LINK;
	}

	@Override
	public String getRelatedJsp()
	{
		return NAVIGATION_JSP_FOLDER + "library/" + JSP_NAME;
	}

	public ActionsListWrapper getActionList()
	{
		return new ActionsListWrapper(getDocument(), "filelink_details_actions_wai");
	}
}
