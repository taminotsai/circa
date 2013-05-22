/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.bean.navigation.inf;

import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.wai.bean.navigation.library.FileLinkDetailsBean;
import eu.cec.digit.circabc.web.wai.manager.ActionsListWrapper;

/**
 * Bean that backs the navigation inside the file link details in the Information Service
 *
 * @author yanick pignot
 */
public class InfFileLinkDetailsBean extends FileLinkDetailsBean
{
	/** */
	private static final long serialVersionUID = -2222116457591646394L;

	public static final String BEAN_NAME = "InfFileLinkDetailsBean";
	public static final String JSP_NAME  = "file-link-details.jsp";

	@Override
	public NavigableNodeType getManagedNodeType()
	{
		return NavigableNodeType.LIBRARY_FILE_LINK;
	}

	@Override
	public String getRelatedJsp()
	{
		return NAVIGATION_JSP_FOLDER + "inf/" + JSP_NAME;
	}

	public ActionsListWrapper getActionList()
	{
		return new ActionsListWrapper(getDocument(), "inf_filelink_details_actions_wai");
	}
}
