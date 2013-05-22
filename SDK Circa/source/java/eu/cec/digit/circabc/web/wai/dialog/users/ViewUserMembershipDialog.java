/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.dialog.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;

import eu.cec.digit.circabc.service.user.UserCategoryMembershipRecord;
import eu.cec.digit.circabc.service.user.UserIGMembershipRecord;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * Dialog bean to edit user profile for the WAI
 *
 * @author Slobodan Filipovic, Markus Kölzer
 */
public final class ViewUserMembershipDialog extends BaseWaiDialog
{
	private static final long serialVersionUID = 395069528441074404L;
	public static final String BEAN_NAME = "ViewUserMembershipDialog";

	/** Logger */
	//private static final Log logger = LogFactory.getLog(ViewUserMembershipDialog.class);

	private String currentUserName;
	private String profileUserName;
	private boolean allowedToView;;

	//***********************************************************************
	//                                                              OVERRIDES
	//***********************************************************************

	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);
		
		this.profileUserName = parameters.get("profileUserName");
		this.currentUserName = this.getNavigator().getCurrentUser().getUserName();
		this.allowedToView = this.isAllowedToViewMemberships();
	}
	
	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		return outcome;
	}

	public String getContainerTitle()
	{
		return translate("view_user_membership_title", this.profileUserName);
	}
	
	public String getBrowserTitle()
	{
		return translate("view_user_membership_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("view_user_membership_icon_tooltip");
	}

	@Override
	public String getCancelButtonLabel()
	{
	   return translate("close");
	}

	//***********************************************************************
	//                                                      GETTER AND SETTER
	//***********************************************************************
	
	public boolean isAllowedToView() 
	{
		return allowedToView;
	}
	
	public boolean isMyProfile()
	{
		return this.profileUserName.equals(this.currentUserName);
	}
	
	public String getUserName() 
	{
		return this.profileUserName;
	}

	public String getUserFullName() 
	{
		return getUserService().getUserFullName(this.profileUserName);
	}

	public List<UserIGMembershipRecord> getIgRoles()
	{
		if(this.isMyProfile())
		{
			// you are always allowed to view all of your own IG memberships
			return getUserService().getInterestGroups(this.profileUserName);
		}
		else
		{
			// if it is not your profile we must apply some filters here because you are probably not allowed to see everything.
			
			List<NodeRef> filterCategories = new ArrayList<NodeRef>();
			List<UserCategoryMembershipRecord> catRolesOfCurrentUser = getUserService().getCategories(this.currentUserName);
			if(catRolesOfCurrentUser.size() > 0)
			{
				// if you are category admin, so you are only allowed to view the IGs that are under cour category
				for(UserCategoryMembershipRecord catMembership : catRolesOfCurrentUser)
				{
					filterCategories.add(new NodeRef(
							StoreRef.STORE_REF_WORKSPACE_SPACESSTORE,
							catMembership.getCategoryNodeId()));
				}
			}
			
			return getUserService().getInterestGroups(this.profileUserName, filterCategories);
		}
	}
	
	public List<UserCategoryMembershipRecord> getCategoryRoles()
	{
		return getUserService().getCategories(this.profileUserName);
	}
	
	//***********************************************************************
	//                                                         PRIVATE HELPER
	//***********************************************************************
	
	private boolean isAllowedToViewMemberships()
	{
		boolean result = false;
		
		if(this.profileUserName.equals(this.currentUserName))
		{
			//current user is watching own memberships
			return true;
		}
		
		// check all categories of current user
		List<UserCategoryMembershipRecord> catRolesOfCurrentUser = getUserService().getCategories(this.currentUserName);
		for(UserCategoryMembershipRecord catRoleOfCurrentUser: catRolesOfCurrentUser)
		{
			if(catRoleOfCurrentUser.getProfile().equals("CircaCategoryAdmin"))
			{
				// the current user is leader of this category, 
				// check if the profile user is a member of this category
				if(isUserMemberOfCategory(this.profileUserName, catRoleOfCurrentUser.getCategoryNodeId()))
				{
					return true;
				}
			}
		}
		
		// check all IGs of current user
		List<UserIGMembershipRecord> igRolesOfCurrentUser = getUserService().getInterestGroups(this.currentUserName);
		for(UserIGMembershipRecord igRoleOfCurrentUser: igRolesOfCurrentUser)
		{
			String profile = igRoleOfCurrentUser.getProfile();
			if(profile.equals("IGLeader") || profile.equals("Secretary"))
			{
				// the current user is leader or secretary of this IG, 
				// check if the profile user is a member of this IG
				if(isUserMemberOfInterestGroup(this.profileUserName, igRoleOfCurrentUser.getInterestGroupNodeId()))
				{
					return true;
				}
			}
		}

		return result;
	}
	
	private boolean isUserMemberOfCategory(String userName, String catNodeId)
	{
		List<UserCategoryMembershipRecord> catRoles = getUserService().getCategories(userName);
		for(UserCategoryMembershipRecord catRole: catRoles)
		{
			if(catRole.getCategoryNodeId().equals(catNodeId))
			{
				return true;
			}
		}
		return false;
	}
	
	private boolean isUserMemberOfInterestGroup(String userName, String igNodeId)
	{
		List<UserIGMembershipRecord> igRolesOfUser = getUserService().getInterestGroups(userName);
		for(UserIGMembershipRecord igRole: igRolesOfUser)
		{
			if(igRole.getInterestGroupNodeId().equals(igNodeId))
			{
				return true;
			}
		}
		return false;
	}
}
