/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.wizard.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.alfresco.web.app.Application;
import org.alfresco.web.ui.common.SortableSelectItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.profile.permissions.LibraryPermissions;
import eu.cec.digit.circabc.web.PermissionUtils;
import eu.cec.digit.circabc.web.ProfileUtils;
/**
 * @creator Clinckart Stephane
 * @author  Yanick Pignot
 */
public class ChangeCircabcAccessRightWizard extends InviteCircabcUsersWizard
{
    private static final long serialVersionUID = 3196287985384763157L;

	private static final Log logger = LogFactory.getLog(ChangeCircabcAccessRightWizard.class);

    /** Index of the ROLES search filter index */
    public static final int IGROOT_INVITED_USERS_IDX = 0;

    /** Index of the PROFILES search filter index */
    public static final int PROFILES_FILTER_IDX = 1;

    protected static final String MSG_PROFILES = "profile";
    protected static final String MSG_USERS = "user";


    @Override
    public SelectItem[] getFilters()
    {
       final ResourceBundle bundle = Application.getBundle(FacesContext
                .getCurrentInstance());

        return new SelectItem[]
        {
              new SelectItem("" + IGROOT_INVITED_USERS_IDX, bundle.getString(MSG_USERS))
              // TODO allow the invitation of profile at the Library Space level.
              ,new SelectItem("" + PROFILES_FILTER_IDX, bundle.getString(MSG_PROFILES))
        };
    }

    public List<SelectItem> getPermissions()
    {
    	return PermissionUtils.getPermissions(getActionNode(), logger);
    }


    @Override
	public SelectItem[] pickerCallback(final int filterIndex, final String contains)
	{
    	List<SortableSelectItem> result = null;

    	final List<Map> authorities = PermissionUtils.getInterestGroupAuthorities(getActionNode().getNodeRef());
    	final List<String> alreadyInvitedProfiles = buildInvitedList(authorities);

		if(filterIndex == PROFILES_FILTER_IDX)
		{
			result = ProfileUtils.buildAllProfileItems(getActionNode(), contains, alreadyInvitedProfiles, logger);
			if(logger.isDebugEnabled())
        	{
				logger.debug("The Profile search is performed successfully and return " + result + ". Filter Index: " + filterIndex + ". Expression: " + contains + "." );
        	}

		}
		else if(filterIndex == IGROOT_INVITED_USERS_IDX)
		{
			result = PermissionUtils.buildInvitedUserItems(getActionNode(), contains, false, alreadyInvitedProfiles, logger);

			if(logger.isDebugEnabled())
        	{
				logger.debug("The User invited in the current IG search is performed successfully and return " + result + ". Filter Index: " + filterIndex + ". Expression: " + contains + "." );
        	}

		}
		else
		{
			logger.error("The picker is called with an invalid index parameter " + filterIndex  + ". This last is not taken in account yet.");

			result =  Collections.<SortableSelectItem>emptyList();
		}
		return result.toArray(new SelectItem[result.size()]);
	}

    private List<String> buildInvitedList(final List<Map> cachedPersonNodes)
	{
    	final List<String> invitedList = new ArrayList<String> (cachedPersonNodes.size());

    	for (final Map userMap : cachedPersonNodes)
		{
    		if(userMap.get("authType").equals("group"))
    		{
    			invitedList.add((String) userMap.get("fullName"));
    		}
    		else
    		{
    			invitedList.add((String) userMap.get("userName"));
    		}
		}

    	return invitedList;
	}

    @Override
    public List<SortableSelectItem> getProfiles()
    {
    	final Set<LibraryPermissions> permissions = LibraryPermissions.getPermissions();

    	final List<SortableSelectItem> permAsList = new ArrayList<SortableSelectItem>(permissions.size());

    	String permAsString;
    	for (final LibraryPermissions perm : permissions)
		{
    		permAsString = perm.toString();
			permAsList.add(new SortableSelectItem(permAsString, permAsString, permAsString));
		}

		if(logger.isDebugEnabled())
    	{
			logger.debug("The permissions found are " + permAsList );
    	}

    	return permAsList;
    }
}
