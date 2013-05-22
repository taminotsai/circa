/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.bean.navigation.library;

import java.util.List;

import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.wai.bean.navigation.LibraryBean;
import eu.cec.digit.circabc.web.wai.bean.navigation.news.PostBean;
import eu.cec.digit.circabc.web.wai.menu.ActionWrapper;

/**
 * Bean that backs the navigation inside the posts of the library service
 *
 * @author yanick pignot
 */
public class DiscussionPostBean extends PostBean
{
	private static final long serialVersionUID = -5757464931958052246L;

	public static final String BEAN_NAME = "LibPostBean";

	private DiscussionForumBean libForumBean;
	private LibraryBean libraryBean;

	public NavigableNodeType getManagedNodeType()
	{
		return NavigableNodeType.LIBRARY_POST;
	}

	@Override
	public List<ActionWrapper> getActions()
	{
		return getLibraryBean().getActions();
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
