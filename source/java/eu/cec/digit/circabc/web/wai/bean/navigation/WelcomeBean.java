/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.bean.navigation;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.alfresco.repo.node.MLPropertyInterceptor;

import eu.cec.digit.circabc.service.user.UserCategoryMembershipRecord;
import eu.cec.digit.circabc.service.user.UserIGMembershipRecord;
import eu.cec.digit.circabc.service.user.UserService;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.repository.CircabcRootNode;

/**
 * Bean that backs the navigation inside the root node of Circabc
 *
 * @author yanick pignot
 */
public class WelcomeBean extends BaseWaiNavigator
{
	private static final String GUEST = "guest";

	/** */
	private static final long serialVersionUID = -6967164595499663893L;

	public static final String JSP_NAME = "circabc-home.jsp";
	public static final String BEAN_NAME = "WelcomeBean";

	public static final String MSG_PAGE_TITLE = "welcome_title";
	public static final String MSG_PAGE_DESCRIPTION = "welcome_title_desc";
	public static final String MSG_BROWSER_TITLE = "title_welcome";
	
	// membeers for 
	
	private UserService userService;

	public NavigableNodeType getManagedNodeType()
	{
		return NavigableNodeType.CIRCABC_ROOT;
	}

	public String getRelatedJsp()
	{
		return NAVIGATION_JSP_FOLDER + JSP_NAME;
	}

	public String getPageDescription()
	{
		return translate(MSG_PAGE_DESCRIPTION);
	}

	public String getPageTitle()
	{
		return translate(MSG_PAGE_TITLE);
	}

	public void init(final Map<String, String> parameters)
    {
		// Ensure that the mlText interceptor is running to avoid a classcast exception
		// Bug fix: DIGIT-CIRCABC-658
		MLPropertyInterceptor.setMLAware(false);
    }

	public List<NavigableNode> getCategoryHeaders()
    {
		final CircabcRootNode circabcNode = getNavigator().getCircabcHomeNode();

    	if(circabcNode != null && circabcNode.getNavigableChilds() != null)
    	{
    		return circabcNode.getNavigableChilds();
    	}
    	else
    	{
    		return Collections.<NavigableNode> emptyList();
    	}
    }

	@Override
	public String getBrowserTitle()
	{
		return translate(MSG_BROWSER_TITLE);
	}

	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

	public UserService getUserService()
	{
		return userService;
	}


	
	public boolean isRegistered()
	{
		final String userName = getNavigator().getCurrentAlfrescoUserName();
		return  ((userName != null && !userName.equalsIgnoreCase(GUEST)));
	}
	
	
	public List<UserIGMembershipRecord> getIgRoles()
	{
	
		final String userName = getNavigator().getCurrentAlfrescoUserName();
		if (userName != null && !userName.equalsIgnoreCase(GUEST))
		{
			return getUserService().getInterestGroups(userName);
		}
    	else
    	{
    		return Collections.<UserIGMembershipRecord> emptyList();
    	}

	}


	public List<UserCategoryMembershipRecord> getCategoryRoles()
	{
		final String userName = getNavigator().getCurrentAlfrescoUserName();
		if (userName != null && !userName.equalsIgnoreCase(GUEST))
		{
			return getUserService().getCategories(userName);
		}
		else
    	{
    		return Collections.<UserCategoryMembershipRecord> emptyList();
    	}
	}
}
