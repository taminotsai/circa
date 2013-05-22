package eu.cec.digit.circabc.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.app.Application;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.ui.common.SortableSelectItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.cmr.security.CircabcConstant;
import eu.cec.digit.circabc.service.profile.CategoryProfileManagerService;
import eu.cec.digit.circabc.service.profile.CircabcRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.IGRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.Profile;
import eu.cec.digit.circabc.service.profile.ProfileManagerService;
import eu.cec.digit.circabc.service.struct.ManagementService;
import eu.cec.digit.circabc.web.bean.navigation.AspectResolver;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;

public class ProfileUtils extends AbstractSearchUtils
{
	private static final String PROFILE_NAME = "profileName:";
	private static final Log logger = LogFactory.getLog(ProfileUtils.class);
	/**
	 * Build a Jsf select item of all assignable profiles in the given node
	 *
	 *  <b>Assignable</b> means that Guest / Registred and imported profiles will <b>NOT</b> be added.
	 *
	 * @param node
	 * @param logger
	 * @return
	 */
	public static final List<SortableSelectItem> buildAssignableProfileItems(final Node node, final Log logger)
	{
		return generateProfilesSelectItems(node, false, false, false, false, null, null, logger);
	}

	/**
	 * Build a Jsf select item of all profiles in the given node
	 *
	 *  <b>ALL</b> means that Guest / Registred and imported profiles will be added.
	 *
	 * @param node
	 * @param logger
	 * @return
	 */
	public static final List<SortableSelectItem> buildAllProfileItems(final Node node, final Log logger)
	{
		return buildAllProfileItems(node, null, null, logger);
	}

	/**
	 * Build a Jsf select item of all profiles in the given node according given filters.
	 *
	 *  <b>ALL</b> means that Guest / Registred and imported profiles will be added.
	 *
	 * @param node
	 * @param match							filter of the profile name
	 * @param alreadyInvitedAuthority		filter of some profile authority to remove
	 * @param logger
	 * @return
	 */
	public static final List<SortableSelectItem> buildAllProfileItems(final Node node, final String match, final List<String> alreadyInvitedAuthority, final Log logger)
	{
		return generateProfilesSelectItems(node, false, true, true, true, match, alreadyInvitedAuthority, logger);
	}

	/**
	 * Build a Jsf select item of all mailable profiles in the given node.
	 *
	 *  <b>Mailable</b> means means that Guest and Registred  will <b>NOT</b> be added but imported profiles <b>YES</b>.
	 *
	 * @param node
	 * @param logger
	 * @return
	 */
	public static final List<SortableSelectItem> buildMailableProfileItems(final Node node, final Log logger)
	{
		return buildMailableProfileItems(node, null, null, logger);
	}

	/**
	 * Build a Jsf select item of all mailable profiles in the given node according given filters.
	 *
	 *  <b>Mailable</b> means means that Guest and Registred  will <b>NOT</b> be added but imported profiles <b>YES</b>.
	 *
	 * @param node
	 * @param match							filter of the profile name
	 * @param alreadyInvitedAuthority		filter of some profile authority to remove
	 * @param logger
	 * @return
	 */
	public static final List<SortableSelectItem> buildMailableProfileItems(final Node node, final String match, final List<String> alreadyInvitedAuthority, final Log logger)
	{
		return generateProfilesSelectItems(node, false, false, false, true, match, alreadyInvitedAuthority, logger);
	}


    public static String getProfilename(final NodeRef where, final String authority, IGRootProfileManagerService profileManager, ManagementService managementService)
    {
    	if(profileManager == null)
    	{
    		profileManager = getProfileManagerServiceFactory().getIGRootProfileManagerService();
    	}

    	if(managementService == null)
    	{
    		managementService = getManagementService();
    	}

    	final NodeRef igRoot = managementService.getCurrentInterestGroup(where);

		if(igRoot == null)
		{
			throw new IllegalStateException("The node received has not Interest Group");
		}

		final Profile profile = profileManager.getProfileFromGroup(igRoot, authority);

		return profile.getProfileDisplayName();
    }

    /**
     * Return the list of default profiles of an Interest Group
     * @return
     */
    public static List<String> getIGDefaultProfiles()
	{
    	final List<String> defaultProfiles = new ArrayList<String>();
    	defaultProfiles.add(IGRootProfileManagerService.Profiles.IGLEADER);
    	defaultProfiles.add(IGRootProfileManagerService.Profiles.SECRETARY);
    	defaultProfiles.add(IGRootProfileManagerService.Profiles.AUTHOR);
    	defaultProfiles.add(IGRootProfileManagerService.Profiles.CONTRIBUTOR);
    	defaultProfiles.add(IGRootProfileManagerService.Profiles.REVIEWER);
    	defaultProfiles.add(IGRootProfileManagerService.Profiles.ACCESS);
    	return defaultProfiles;
	}

    /**
     * Return the list of default profiles of an Interest Group
     * @return
     */
    public static List<String> getCategoryDefaultProfiles()
	{
    	final List<String> defaultProfiles = new ArrayList<String>();
    	defaultProfiles.add(CategoryProfileManagerService.Profiles.CIRCA_CATEGORY_ADMIN);
    	return defaultProfiles;
	}

    /**
     * Return the list of default profiles of an Interest Group
     * @return
     */
    public static List<String> getCircabcDefaultProfiles()
	{
    	final List<String> defaultProfiles = new ArrayList<String>();
    	defaultProfiles.add(CircabcRootProfileManagerService.Profiles.CIRCABC_ADMIN);
    	return defaultProfiles;
	}


    //-- Private helpers

    private static List<SortableSelectItem> generateProfilesSelectItems(final Node node, final boolean addAllProfileChoice, final boolean addGuest, final boolean addRegistred, final boolean addImported, final String match, final List<String> alreadyInvitedAuthority, final Log logger)
    {
    	long startTime = 0;

    	if(logger.isDebugEnabled())
		{
    		startTime = System.currentTimeMillis();
			logger.debug("Start to build the profile list items...");
		}

    	final Collection<Profile> profiles = getProfiles(node.getNodeRef(), logger);
    	final List<SortableSelectItem> selectItems = new ArrayList<SortableSelectItem>(profiles.size() + 2);
    	final FacesContext fc = FacesContext.getCurrentInstance();

    	final Set<String> filteredAuthority = new HashSet<String>();
    	final boolean needNameFilter;


    	if(alreadyInvitedAuthority != null && alreadyInvitedAuthority.size() > 0)
    	{
    		filteredAuthority.addAll(alreadyInvitedAuthority);
    	}
    	if(addGuest == false)
    	{
    		filteredAuthority.add(CircabcConstant.GUEST_AUTHORITY);
    	}
    	if(addRegistred == false)
    	{
    		filteredAuthority.add(CircabcRootProfileManagerService.ALL_CIRCA_USERS_PROFILE_NAME);
    	}

    	if(match == null || match.matches(STAR_WILDCARD_REGEX))
    	{
    		needNameFilter = false;

    		if (logger.isDebugEnabled())
            {
    			logger.debug("  --  No name filter activated");
            }
    	}
    	else
    	{
    		needNameFilter = true;

    		if (logger.isDebugEnabled())
            {
    			logger.debug("  --  Name filter activated. Must match " + match);
            }
    	}

    	if(addAllProfileChoice)
	    {
	        selectItems.add(new SortableSelectItem(ALL_PROFILE, Application.getMessage(fc, ALL_PROFILE), ALL_PROFILE));

	        if (logger.isDebugEnabled())
            {
                logger.debug("  --  Select case: 'All profile' added");
            }
	    }

    	for (final Profile profile : profiles)
	    {
	    	if(filteredAuthority.contains(profile.getProfileName()))
	    	{
	    		if(logger.isDebugEnabled())
	    		{
	    			logger.debug("  --  Profile " + profile.getProfileName() + " removed from the list because it is in the filter list");
	    		}
	    	}
	    	else if(profile.isImported() && addImported == false)
	    	{
	    		if(logger.isDebugEnabled())
	    		{
	    			logger.debug("  --  Profile " + profile.getProfileName() + " removed from the list because it's an imported one");
	    		}
	    	}
	    	else if(needNameFilter == true && profileMatch(profile, match, logger) == false)
	    	{
	    		if(logger.isDebugEnabled())
	    		{
	    			logger.debug("  --  Profile " + profile.getProfileName() + " removed from the list because it doesn't matches filter");
	    		}
	    	}
	    	else
	    	{
	    		selectItems.add(buildProfileItem(profile, logger));

	    		if(logger.isDebugEnabled())
	    		{
	    			logger.debug("  --  Profile " + profile.getProfileName() + " successfully added to the list.");
	    		}
	    	}
	    }

    	if(logger.isDebugEnabled())
		{
			logger.debug(selectItems.size() + " Profiles found in " + (System.currentTimeMillis() - startTime) + "ms");
		}

	    return selectItems;
    }

    private static boolean profileMatch(final Profile profile, final String match, final Log logger)
    {
    	if(profile.getProfileName().contains(match))
    	{
    		if(logger.isDebugEnabled())
    		{
    			logger.debug("  --  Profile " + profile.getProfileName() + " matches with its profile name.");
    		}
    		return true;
    	}
    	else if(profile.getProfileDisplayName().contains(match))
    	{
    		if(logger.isDebugEnabled())
    		{
    			logger.debug("  --  Profile " + profile.getProfileName() + " matches with its current title.");
    		}
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }

    private static List<Profile> getProfiles(final NodeRef where, final Log logger)
    {
    	final NavigableNodeType type = AspectResolver.resolveType(where);
    	final NodeRef rootNode;
		if (type == null)
		{
			throw new IllegalStateException("Impossible to perform a Circabc invitation in an non Circabc node.");
		}

		if (type.isStrictlyUnder(NavigableNodeType.IG_ROOT))
		{
			rootNode = getManagementService().getCurrentInterestGroup(where);
		}
		else
		{
			rootNode = where;
		}

		final ProfileManagerService profileManager = getProfileManagerServiceFactory().getProfileManagerService(rootNode);

		final List<Profile> profiles = profileManager.getProfiles(rootNode);

		if(profiles.size() > 0 && logger.isDebugEnabled())
		{
			logger.debug("  --  " + profiles.size() + " profiles successfully found for Node " + rootNode);
		}
		if(profiles.size() == 0 && logger.isWarnEnabled())
		{
			logger.warn("  --  No profile found for Node " + rootNode);
		}

		return profiles;
    }

    private static SortableSelectItem buildProfileItem(final Profile profile, final Log logger)
    {
    	final String profileName = profile.getPrefixedAlfrescoGroupName();
		final String displayName = profile.getProfileDisplayName();
		final String description = profile.getProfileDescription();

		 if (logger.isDebugEnabled())
         {
                logger.debug("Profile Name:" + profileName + " in use with title " + displayName + " and description " + description);
         }

		return new SortableSelectItem(profileName, displayName, description);
    }
}
