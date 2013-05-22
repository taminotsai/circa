/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.manager;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.model.ForumModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.app.context.UIContextService;
import org.alfresco.web.bean.repository.Node;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.struct.ManagementService;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.repository.InterestGroupNode;
import eu.cec.digit.circabc.web.wai.bean.navigation.InformationBean;
import eu.cec.digit.circabc.web.wai.bean.navigation.LibraryBean;
import eu.cec.digit.circabc.web.wai.bean.navigation.NewsGroupBean;
import eu.cec.digit.circabc.web.wai.bean.navigation.WaiNavigator;
import eu.cec.digit.circabc.web.wai.bean.navigation.WelcomeBean;

/**
 * Bean that manage the navigation beans. These beans must be an instance of WaiNavigator
 *
 * @see eu.cec.digit.circabc.web.wai.bean.navigation.WaiNavigator
 * @author yanick pignot
 */
public class NavigationManager implements Serializable
{
	private static final long serialVersionUID = -7002395459540564631L;

	public static final String BEAN_NAME = "WaiNavigationManager";

	private static final Log logger = LogFactory.getLog(NavigationManager.class);

	//private WaiNavigator currentBean;
	private NavigationState state;

	private List<Node> navigationList = null;
	
	private String renderPropertyNameFromBean;

	/**
	 * Init the current navigation.
	 *
	 * @param navigationBeanName 					the bean name that backs the navigation.
	 */
	public void initNavigation(final String navigationBeanName)
	{
		if(navigationBeanName == null || navigationBeanName.length() < 1)
		{
			throw new IllegalArgumentException("The navigation bean name is a mandatory parameter.");
		}

		// set the bean that will back the navigation
		final WaiNavigator currentBean  = Beans.getWaiBrosableBean(navigationBeanName);
		final Node currentNode = Beans.getWaiNavigator().getCurrentNode();
		if(logger.isDebugEnabled())
		{
			logger.debug("currentBean:" + currentBean + "\ncurrentNode:" + currentNode);			
		}

		state = new NavigationState(currentBean, currentNode);
		navigationList = null;
		
		if(currentBean instanceof LibraryBean)
		{
			renderPropertyNameFromBean = ((LibraryBean) currentBean).getRenderPropertyNameNavigationPreference();
		}
		else if(currentBean instanceof NewsGroupBean)
		{
			renderPropertyNameFromBean = ((NewsGroupBean) currentBean).getRenderPropertyNameNavigationPreference();
		}
		else if(currentBean instanceof InformationBean)
		{
			renderPropertyNameFromBean = ((InformationBean) currentBean).getRenderPropertyNameNavigationPreference();
		}
		else{
			renderPropertyNameFromBean = null;
		}

		initNavigationList();

		if(currentBean == null)
		{
			throw new IllegalArgumentException("The navigation manager can't handle this navigation. The WaiNavigator " + navigationBeanName + " must be declared as a managed bean.");
		}
	}

	public void setParamsToApply(final Map<String, String> parameters)
	{
		state.getBean().setParamsToApply(parameters);
	}

	public static boolean areStatesEquals(final NavigationState state, final Object obj)
	{
		if(state == null)
		{
			return false;
		}
		else
		{
			return state.equals(obj);
		}
	}


	/**
	 * @return the current navigation bean
	 */
	public WaiNavigator getBean()
	{
		return getState().getBean();
	}


	public boolean isIconVisible()
	{
		return getState().getBean().getPageIcon() != null;
	}

	public boolean isNavigationVisible()
	{
		return getNavigation() != null;
	}

	/**
	 * @return the current jsp Page that handle this navigation
	 */
	public String getPage()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("jsp page: " + getState().getBean().getRelatedJsp());
		}
		return getState().getBean().getRelatedJsp();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.web.wai.bean.navigation.WaiNavigator#getPageDescription()
	 */
	public String getDescription()
	{
		return getState().getBean().getPageDescription();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.web.wai.bean.navigation.WaiNavigator#getPageIcon()
	 */
	public String getIcon()
	{
		return getState().getBean().getPageIcon();
	}


	/**
	 * @return
	 * @see eu.cec.digit.circabc.web.wai.bean.navigation.WaiNavigator#getPageIconAltText()
	 */
	public String getIconAlt()
	{
		return getState().getBean().getPageIconAltText();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.web.wai.bean.navigation.WaiNavigator#getPageTitle()
	 */
	public String getTitle()
	{
		return getState().getBean().getPageTitle();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.web.wai.bean.navigation.WaiNavigator#getHttpUrl()
	 */
	public String getHttpUrl()
	{
		return getState().getBean().getHttpUrl();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.web.wai.bean.navigation.WaiNavigator#getPreviousHttpUrl()
	 */
	public String getPreviousHttpUrl()
	{
		return getState().getBean().getPreviousHttpUrl();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.web.wai.bean.navigation.WaiNavigator#getWebdavUrl()
	 */
	public String getWebdavUrl()
	{
		return getState().getBean().getWebdavUrl();
	}


	/**
	 * @return
	 */
	public List<Node> getNavigation()
	{
		return navigationList;
	}

	public String getBrowserTitle()
	{
		return getState().getBean().getBrowserTitle();
	}

	public NavigationState getState()
	{
		if(state == null)
		{
			// the user has probably called the wai browse page directly via an url.

			if(Beans.getWaiNavigator().getCurrentNodeType() == null)
			{
				if(logger.isWarnEnabled()) 
				{
					logger.warn("Redirect to the welcome page");
				}
				// the user come from an non circabc node. Or it is its first navigation request.
				initNavigation(WelcomeBean.BEAN_NAME);
			}
			else
			{
				if(logger.isWarnEnabled()) 
				{
					logger.warn("Redirect to the latest requested page:" + Beans.getWaiNavigator().getCurrentNodeType().getBeanName());
				}
				
				// return to the last requested page
				initNavigation(Beans.getWaiNavigator().getCurrentNodeType().getBeanName());
			}

		}
		return this.state;
	}

	public void restoreState(final NavigationState state)
	{
		this.state = state;

		if(!state.getNode().getId().equals(Beans.getWaiNavigator().getCurrentNodeId()))
		{
			Beans.getWaiNavigator().setCurrentNodeId(state.getNode().getId());

			// add the notification because the space is changed !!!
			UIContextService.getInstance(FacesContext.getCurrentInstance()).spaceChanged();
		}

		getBean().restored();
	}

	@Override
	public String toString()
	{
		return "The navigation being managed: \n\tPage: " + getPage() + "\n\tBean: " + getState().getBean().getManagedNodeType().getBeanName() + "\n\tNode Type: " +  getBean().getManagedNodeType().getComparatorQName();
	}

	private void initNavigationList()
	{
		//	we don't need navigation list on IGroot or above
		if(Beans.getWaiNavigator().getCurrentIGService() != null)
		{
			navigationList = new LinkedList<Node>();

			final NavigableNodeType type2 = getState().getBean().getManagedNodeType();
			final NavigableNodeType type = type2.getRelatedIgService();

			if(type != null)
			{
				if(NavigableNodeType.DIRECTORY.equals(type))
				{
					navigationList.add(Beans.getWaiNavigator().getCurrentIGService());
				}
				else if(NavigableNodeType.EVENT.equals(type))
				{
					navigationList.add((Node) Beans.getWaiNavigator().getCurrentIGRoot().get(InterestGroupNode.EVENT_SERVICE));
				}
				else
				{
					final ManagementService managementService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getManagementService();

					final List<NodeRef> nav = managementService.getAllParents(
									getState().getNode().getNodeRef(),
									true,
									type.getComparatorQName(),
									true);

					for(final NodeRef ref : nav)
					{
						final Node node = new Node(ref);
						// don't add the Post name.
						if(node.getType().equals(ForumModel.TYPE_POST) == false)
						{
							((LinkedList<Node>)navigationList).addLast(node);
						}
					}

					if(NavigableNodeType.NEWSGROUP_POST.equals(type) || NavigableNodeType.LIBRARY_POST.equals(type))
					{
						navigationList.remove(navigationList.size() - 1);
					}
				}
			}
		}
		else if(Beans.getWaiNavigator().getCurrentIGRoot() != null)
		{
			navigationList = new LinkedList<Node>();
			navigationList.add(null);
		}
	}

	/**
	 * @return the renderPropertyNameFromBean
	 */
	public String getRenderPropertyNameFromBean() {
		return renderPropertyNameFromBean;
	}

	/**
	 * @param renderPropertyNameFromBean the renderPropertyNameFromBean to set
	 */
	public void setRenderPropertyNameFromBean(String renderPropertyNameFromBean) {
		this.renderPropertyNameFromBean = renderPropertyNameFromBean;
	}

	
}
