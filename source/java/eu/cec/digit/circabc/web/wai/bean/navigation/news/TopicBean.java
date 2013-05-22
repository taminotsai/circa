/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.bean.navigation.news;

import org.alfresco.model.ContentModel;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.wai.bean.navigation.NewsGroupBean;

/**
 * Bean that backs the navigation inside the topics of the newsgroup service
 *
 * @author yanick pignot
 */
public class TopicBean extends NewsGroupBean
{
	/** */
	private static final long serialVersionUID = -6967164595499987493L;

	public static final String JSP_NAME  = "topic.jsp";
	public static final String BEAN_NAME = "NewsTopicBean";

	public static final String MSG_PAGE_DESCRIPTION = "newsgroups_topic_title_desc";
	public static final String MSG_PAGE_ICON_ALT = "newsgroups_topic_icon_tooltip";


	public NavigableNodeType getManagedNodeType()
	{
		return NavigableNodeType.NEWSGROUP_TOPIC;
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
		return "/images/icons/" + getCurrentNode().getProperties().get("icon") + ".gif";
	}

	public String getPageIconAltText()
	{
		return translate(MSG_PAGE_ICON_ALT);
	}

	public String getTopicTitle()
	{
		final Node current = getCurrentNode();
		final String title = (String) current.getProperties().get(ContentModel.PROP_TITLE.toString());

		final String toCrop;
		if(title == null || title.length() < 1)
		{
			toCrop = current.getName();
		}
		else
		{
			toCrop = title;
		}

		if(toCrop.length() > 80)
		{
			return toCrop.substring(0, 80) + "...";
		}
		else
		{
			return toCrop;
		}
	}

}
