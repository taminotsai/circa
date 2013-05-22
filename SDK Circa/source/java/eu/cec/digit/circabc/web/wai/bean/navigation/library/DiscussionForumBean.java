/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.bean.navigation.library;

import java.util.List;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.ml.MultilingualContentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.customisation.nav.NavigationPreference;
import eu.cec.digit.circabc.service.customisation.nav.NavigationPreferencesService;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.wai.bean.navigation.LibraryBean;
import eu.cec.digit.circabc.web.wai.bean.navigation.news.ForumBean;
import eu.cec.digit.circabc.web.wai.menu.ActionWrapper;

/**
 * Bean that backs the navigation inside the forums of the library service
 *
 * @author yanick pignot
 */
public class DiscussionForumBean extends ForumBean
{
	private static final long serialVersionUID = -4789102671156849286L;

	@SuppressWarnings("unused")
	private final static Log logger = LogFactory.getLog(DiscussionForumBean.class);

	private transient MultilingualContentService multilingualContentService;
	private LibraryBean libraryBean;

	public static final String BEAN_NAME = "LibForumBean";

	public NavigableNodeType getManagedNodeType()
	{
		return NavigableNodeType.LIBRARY_FORUM;
	}

	@Override
	public List<ActionWrapper> getActions()
	{
		return getLibraryBean().getActions();
	}

	/**
	 * Get the list of topic nodes for the current forum
	 *
	 * @return List of topic nodes for the current forum
	 */
	@Override
	public List<NavigableNode> getTopics()
	{
		return super.getTopics();
	}

	@Override
	public NavigationPreference getTopicNavigationPreference()
	{
		return getNavigationPreferencesService().getServicePreference(
				getCurrentNode().getNodeRef(), NavigationPreferencesService.LIBRARY_SERVICE, NavigationPreferencesService.DISCUSSION_TYPE);
	}

	public boolean isSubForumAllowed()
	{
		return false;
	}

	/**
	 * @return the multilingualContentService
	 */
	protected final MultilingualContentService getMultilingualContentService()
	{
		if(multilingualContentService == null)
		{
			multilingualContentService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getMultilingualContentService();
		}
		return multilingualContentService;
	}

	/**
	 * @param multilingualContentService the multilingualContentService to set
	 */
	public final void setMultilingualContentService(MultilingualContentService multilingualContentService)
	{
		this.multilingualContentService = multilingualContentService;
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
