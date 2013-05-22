/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.bean.navigation.news;

import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.wai.bean.navigation.NewsGroupBean;

/**
 * Bean that backs the navigation inside the forums of the newsgroup service
 *
 * @author yanick pignot
 */
public class ForumBean extends NewsGroupBean
{

	/** */
	private static final long serialVersionUID = -6967164595499987493L;

	public static final String JSP_NAME  = "forum.jsp";
	public static final String BEAN_NAME = "NewsForumBean";

	public static final String MSG_PAGE_DESCRIPTION = "newsgroups_forum_title_desc";
	public static final String MSG_PAGE_ICON_ALT = "newsgroups_forum_icon_tooltip";

	public NavigableNodeType getManagedNodeType()
	{
		return NavigableNodeType.NEWSGROUP_FORUM;
	}

	public String getRelatedJsp()
	{
		return NAVIGATION_JSP_FOLDER + "news/" + JSP_NAME;
	}

	public String getPageDescription()
	{
		return translate(MSG_PAGE_DESCRIPTION);
	}

	public String getPageTitle()
	{
		return getCurrentNode().getName();
	}

	public String getPageIcon()
	{
		return "/images/icons/forum.gif";
	}

	public String getPageIconAltText()
	{
		return translate(MSG_PAGE_ICON_ALT);
	}
}
