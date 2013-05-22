/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.bean.navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.alfresco.repo.security.authority.UnknownAuthorityException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.web.PermissionUtils;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;

/**
 * Bean that backs the navigation inside a Category
 *
 * @author yanick pignot
 */
public class CategoryBean extends BaseWaiNavigator
{
    /** */
    private static final long serialVersionUID = -6967164595499663893L;

    private static final Log logger = LogFactory.getLog(CategoryBean.class);

    public static final String JSP_NAME = "category-home.jsp";
    public static final String BEAN_NAME = "CategoryBean";

    public static final String MSG_PAGE_TITLE = "category_home_title";
    public static final String MSG_PAGE_DESCRIPTION = "category_home_title_desc";
    public static final String MSG_BROWSER_TITLE = "category_home_title_short";

    /** Lists of members interest group */
    private List<NavigableNode> memberIg = null;
    /** Lists of registered interest group */
    private List<NavigableNode> registredIg = null;
    /** Lists of public interest group */
    private List<NavigableNode> publicIg = null;

    public NavigableNodeType getManagedNodeType()
    {
        return NavigableNodeType.CATEGORY;
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
        return translate(MSG_PAGE_TITLE, getCurrentCategory().getProperties().get(NavigableNode.BEST_TITLE_RESOLVER_NAME));
    }

    @Override
    public String getBrowserTitle()
    {
        return translate(MSG_BROWSER_TITLE) + " " + getCurrentNode().getName();
    }

    public void init(Map<String, String> parameters)
    {
        this.memberIg = null;
        this.registredIg = null;
        this.publicIg = null;
    }

    /**
     * <blockquote>
     *    <b>Members access mode</b>: the Interest Groups for which you have Membership. This does not appear if you are not a member of any IG.
     * </blockquote>
     *
     * @return the list of interest groups having the member access mode
     */
    public List<NavigableNode> getMembersAccessIg()
    {
        if(this.memberIg == null)
        {
            initAndGroupIgRoot();
        }

        return this.memberIg;
    }

    /**
     * @return if the members access list is empty
     */
    public boolean isMembersListEmpty()
    {
        return getMembersAccessIg() == null || getMembersAccessIg().isEmpty();
    }
    
    /**
     * @return if the members access list is empty
     */
    public boolean getMembersListEmpty()
    {
        return isMembersListEmpty();
    }

    /**
     * 	<blockquote>
     *    <b>Registered access mode</b>: the Interest Groups of which you may become a Member and which are visible for authenticated users, only. This does not appear if there is no Interest Group available for this access mode.	 *
     *  </blockquote>
     *
     * @return the list of interest groups having the registred access mode
     */
    public List<NavigableNode> getRegisteredAccessIg()
    {
        if(this.registredIg == null)
        {
            initAndGroupIgRoot();
        }

        return this.registredIg;
    }

    /**
     * @return if the registred access list is empty
     */
    public boolean isRegisteredListEmpty()
    {
        return getRegisteredAccessIg().isEmpty();
    }

    /**
     * <blockquote>
     *    <b>Public access mode</b>: the Interest Groups which are accessible to everybody irrespective of authentication. This does not appear if there is no public Interest Group in this category.
     *  </blockquote>
     *
     * @return the list of interest groups having the public access mode
     */
    public List<NavigableNode> getPublicAccessIg()
    {
        if(this.publicIg == null)
        {
            initAndGroupIgRoot();
        }

        return this.publicIg;

    }

    /**
     * @return if the public access list is empty
     */
    public boolean isPublicListEmpty()
    {
        return getPublicAccessIg().isEmpty();
    }


    /**
     * Build and sort the interest group of the current category according theirs access modes.
     */
    private void initAndGroupIgRoot()
    {
        long startTime = 0;
        if (logger.isDebugEnabled())
        {
            startTime = System.currentTimeMillis();
        }


        this.memberIg = new ArrayList<NavigableNode>();
        this.registredIg = new ArrayList<NavigableNode>();
        this.publicIg = new ArrayList<NavigableNode>();

        final String currentUser = getNavigator().getCurrentUser().getUserName();
        final List<NavigableNode> igs = getNavigator().getCurrentCategory().getNavigableChilds();


        for (NavigableNode node : igs)
        {
            try {

                node.put("canJoin",  Boolean.valueOf(PermissionUtils.isUserCanJoinNode(currentUser, node.getNodeRef(), getNodeService(), getPermissionService())));

                if (getProfileManagerServiceFactory().getIGRootProfileManagerService().getInvitedUsers(node.getNodeRef()).contains(currentUser))
                {
                    // User is invited to the node: he's member of this node
                    this.memberIg.add(node);
                }
                else if (!getManagementService().hasGuestVisibility(node.getNodeRef()))
                {
                    // User is NOT invited to the node but is invited on another igroot
                    this.registredIg.add(node);
                }
                else
                {
                    // User is NOT invited to the node and is NOT invited on another igroot in the same category
                    this.publicIg.add(node);
                }

            } catch(UnknownAuthorityException uae) {
                //@TODO Remove this stuff when Alfresco will correct his clustering issue http://issues.alfresco.com/browse/AR-2035
            	if(logger.isErrorEnabled()) {
            		logger.error("THIS IS A WORK ARROUND, THERE ERROR STILL THERE BUT HAS NO IMPACT ON THE APPLICATION");
            		logger.error("THE PROBLEM RESOLVE HIMSELF WHEN CLUSTER ELEMENTS FINNISH SYNCHRONISATION");
            		logger.error("CLUSTERING PROBLEM: The current NodeRef. Open ISSUE by ALFRESCO: http://issues.alfresco.com/browse/AR-2035", uae);
            	}
            }
        }


        if (logger.isDebugEnabled())
        {
            long endTime = System.currentTimeMillis();
            logger.debug("Time to query sort and build the list of IgRoot (" +  memberIg.size() + " member ig(s) / " + registredIg.size() + " registred ig(s) / " + publicIg.size() + " public ig(s)) : " + (endTime - startTime) + "ms");
        }
    }
}
