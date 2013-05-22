/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.bean.navigation.library;

import java.util.List;

import eu.cec.digit.circabc.service.profile.permissions.LibraryPermissions;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.wai.bean.navigation.LibraryBean;
import eu.cec.digit.circabc.web.wai.bean.navigation.news.TopicBean;
import eu.cec.digit.circabc.web.wai.menu.ActionWrapper;

/**
 * Bean that backs the navigation inside the topics of the library service
 *
 * @author yanick pignot
 */
public class DiscussionTopicBean extends TopicBean
{

	private static final long serialVersionUID = -5167911581120985898L;

	public static final String BEAN_NAME = "LibTopicBean";

	private DiscussionForumBean libForumBean;
	private LibraryBean libraryBean;

	public NavigableNodeType getManagedNodeType()
	{
		return NavigableNodeType.LIBRARY_TOPIC;
	}

	/**
	 * Get the list of post nodes for the current topic <
	 *
	 * @return List of post nodes for the current topic
	 */
	public List<NavigableNode> getPosts()
	{
		return super.getPosts();
	}

	@Override
	public List<ActionWrapper> getActions()
	{
		return getLibraryBean().getActions();
	}

	@Override
	protected boolean isUserModerator()
	{
		return getCurrentNode().hasPermission(LibraryPermissions.LIBADMIN.toString());
	}
	/**
	 * @return the libForumBean
	 */
	protected final DiscussionForumBean getLibForumBean()
	{
		if(libForumBean == null)
		{
			libForumBean = (DiscussionForumBean) Beans.getWaiBrosableBean(DiscussionForumBean.BEAN_NAME);
		}
		return libForumBean;
	}

	/**
	 * @param libForumBean the libForumBean to set
	 */
	public final void setLibForumBean(DiscussionForumBean libForumBean)
	{
		this.libForumBean = libForumBean;
	}

	/**
	 * @return the libraryBean
	 */
	protected final LibraryBean getLibraryBean()
	{
		if(libraryBean == null)
		{
			libraryBean = (LibraryBean) Beans.getWaiBrosableBean(LibraryBean.BEAN_NAME);
		}
		return libraryBean;
	}

	/**
	 * @param libraryBean the libraryBean to set
	 */
	public final void setLibraryBean(LibraryBean libraryBean)
	{
		this.libraryBean = libraryBean;
	}
}
