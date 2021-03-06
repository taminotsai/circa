/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.profile.newsgroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.service.cmr.repository.MLText;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.model.PermissionModel;
import eu.cec.digit.circabc.repo.profile.ProfileManagerServiceImpl;
import eu.cec.digit.circabc.service.profile.CircabcServices;
import eu.cec.digit.circabc.service.profile.NewsGroupProfileManagerService;
import eu.cec.digit.circabc.service.profile.Profile;
import eu.cec.digit.circabc.service.profile.ProfileException;
import eu.cec.digit.circabc.service.profile.ProfileManagerService;

public class NewsGroupProfileManagerServiceImpl extends
		ProfileManagerServiceImpl implements NewsGroupProfileManagerService
{
	private static final String PROFILE_PREFIX = "circaNewsGroup";

	private static final Set<String> services = new HashSet<String>();

	/** A logger for the class */
	private static final Log logger = LogFactory
			.getLog(NewsGroupProfileManagerServiceImpl.class);

	public NewsGroupProfileManagerServiceImpl() {
		services.add(CircabcServices.NEWSGROUP.toString());
	}

	/**
	 * @return the services
	 */
	public final Set<String> getServices()
	{
		return services;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.cec.digit.circabc.service.profile.ProfileManagerServiceImpl#deleteProfile(org.alfresco.service.cmr.repository.NodeRef,
	 *      java.lang.String)
	 */
	@Override
	public void deleteProfile(final NodeRef nodeRef, final String profileName)
			throws ProfileException
	{
		final String key = nodeRef.toString();
		profileMapCache.remove(key);

		final NodeRef parentNodeRef = nodeService.getPrimaryParent(nodeRef)
				.getParentRef();
		final ProfileManagerService profileManagerService = profileManagerServiceFactory
				.getProfileManagerService(parentNodeRef);
		profileManagerService.deleteProfile(nodeRef, profileName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.cec.digit.circabc.service.profile.ProfileManagerServiceImpl#getGroupNameFromProfile(org.alfresco.service.cmr.repository.NodeRef,
	 *      java.lang.String)
	 */
	@Override
	public Profile getProfile(final NodeRef nodeRef, final String profileName)
	{
		if (getProfilePrefix() == null)
		{
			throw new ProfileException(profileName,
					"This node has not Circabc Aspect");
		}

		// first check if rootNodeRef has specified aspect
		if (!nodeService.hasAspect(nodeRef, getNodeAspect()))
			throw new ProfileException(profileName,
					"Node has not the specified aspect");

		final NodeRef parentNodeRef = nodeService.getPrimaryParent(nodeRef)
				.getParentRef();
		final ProfileManagerService profileManagerService = profileManagerServiceFactory
				.getProfileManagerService(parentNodeRef);
		return profileManagerService.getProfile(parentNodeRef, profileName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.cec.digit.circabc.service.profile.ProfileManagerServiceImpl#renameProfile(org.alfresco.service.cmr.repository.NodeRef,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void renameProfile(final NodeRef nodeRef,
			final String oldProfileName, final String newProfileName)
			throws ProfileException
	{
		final String key = nodeRef.toString();
		profileMapCache.remove(key);

		if (getProfilePrefix() == null)
		{
			throw new ProfileException(oldProfileName,
					"This node has not Circabc Aspect");
		}

		// first check if rootNodeRef has specified aspect
		if (!nodeService.hasAspect(nodeRef, getNodeAspect()))
			throw new ProfileException(oldProfileName,
					"Node has not the specified aspect");

		final NodeRef parentNodeRef = nodeService.getPrimaryParent(nodeRef)
				.getParentRef();
		final ProfileManagerService profileManagerService = profileManagerServiceFactory
				.getProfileManagerService(parentNodeRef);
		profileManagerService.renameProfile(parentNodeRef, oldProfileName,
				newProfileName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.cec.digit.circabc.service.profile.ProfileManagerServiceImpl#uninvitePerson(org.alfresco.service.cmr.repository.NodeRef,
	 *      java.lang.String)
	 */
	@Override
	public void uninvitePerson(final NodeRef nodeRef, final String userID)
			throws ProfileException
	{
		if (getProfilePrefix() == null)
		{
			throw new ProfileException(userID, "This node has not Circabc Aspect");
		}

		// first check if rootNodeRef has specified aspect
		if (!nodeService.hasAspect(nodeRef, getNodeAspect()))
			throw new ProfileException(userID,
					"Node has not the specified aspect");

		final NodeRef parentNodeRef = nodeService.getPrimaryParent(nodeRef)
				.getParentRef();
		final ProfileManagerService profileManagerService = profileManagerServiceFactory
				.getProfileManagerService(parentNodeRef);
		profileManagerService.uninvitePerson(parentNodeRef, userID);
	}

	public Profile updateProfile(final NodeRef nodeRef, final String profileName,
			final Map<String, Set<String>> servicesPermissions, final boolean updateProfileMap)
	{
		final String key = nodeRef.toString();
		profileMapCache.remove(key);

		Profile profile = null;
		if (getProfilePrefix() == null)
		{
			throw new ProfileException(profileName,
					"This node has not Circabc Aspect");
		}

		// first check if rootNodeRef has specified aspect
		if (!nodeService.hasAspect(nodeRef, getNodeAspect()))
			throw new ProfileException(profileName,
					"Node has not the specified aspect");

		final NodeRef parentNodeRef = nodeService.getPrimaryParent(nodeRef)
				.getParentRef();

		final ProfileManagerService profileManagerService = profileManagerServiceFactory
				.getProfileManagerService(parentNodeRef);
		//profile = profileManagerService.updateProfile(parentNodeRef, profileName,
		//		servicesPermissions);

		//profile = super.updateProfile(parentNodeRef, profileName, servicesPermissions);

		//TODO REMOVE DUPLICATE CODE HERE

		if (!profileManagerService.getProfileMap(parentNodeRef).containsKey(profileName))
		{
			throw new ProfileException(profileName, "Profile not exist");
		} else
		{
			final Map<String, Set<String>> filteredServicesPermissions = filterServicesForNode(servicesPermissions);
			//
			// get the Profile
			profile = profileManagerService.getProfileMap(parentNodeRef).get(profileName);
			profile.clearNodeServicesPermissions(nodeRef, filteredServicesPermissions.keySet(), serviceRegistry);
			profile.setServicesPermissions(filteredServicesPermissions);
			profile.setNodeServicesPermissions(nodeRef, filteredServicesPermissions, serviceRegistry);

			if(updateProfileMap)
			{
				profileManagerService.updateProfileMap(parentNodeRef, profile);
			}
		}

		//profile.setNodeServicesPermissions(nodeRef, servicesPermissions, serviceRegistry);

		if (logger.isDebugEnabled())
		{
			logger.debug("updateProfile(profile:" + profileName + ", node:" + nodeRef + " && node:" + parentNodeRef + ")");
		}

		resetCache();

		return profile;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.cec.digit.circabc.service.profile.ProfileManagerServiceImpl#addProfile(org.alfresco.service.cmr.repository.NodeRef,
	 *      java.lang.String, java.util.HashMap)
	 */
	@Override
	public void addProfile(final NodeRef nodeRef, final String profileName, final String authorityGroupName,
			final Map<String, Set<String>> profilesPermissions)
			throws ProfileException
	{
		addProfile(nodeRef, profileName, authorityGroupName, null, null, profilesPermissions);
	}

	@Override
	public void addProfile(final NodeRef nodeRef, final String profileName, final String authorityGroupName,
			final MLText title, final MLText description, final Map<String, Set<String>> profilesPermissions)
			throws ProfileException
	{
		final String key = nodeRef.toString();
		profileMapCache.remove(key);

		if (getProfilePrefix() == null)
		{
			throw new ProfileException(profileName,
					"This node has not Circabc Aspect");
		}

		// first check if rootNodeRef has specified aspect
		if (!nodeService.hasAspect(nodeRef, getNodeAspect()))
			throw new ProfileException(profileName,
					"Node has not the specified aspect");

		final NodeRef parentNodeRef = nodeService.getPrimaryParent(nodeRef)
				.getParentRef();
		final ProfileManagerService profileManagerService = profileManagerServiceFactory
				.getProfileManagerService(parentNodeRef);
		profileManagerService.addProfile(parentNodeRef, profileName, authorityGroupName, title, description,
				profilesPermissions);
	}

	public List<QName> getQNameServiceRoles()
	{
		final List<QName> list = new ArrayList<QName>();
		list.add(PermissionModel.NEWSGROUP_PERMISSION);
		return list;
	}

	public Map<QName, String> getServiceRolesEnum()
	{
		final Map<QName, String> map = new HashMap<QName, String>();
		map.put(PermissionModel.NEWSGROUP_PERMISSION, CircabcServices.NEWSGROUP
				.toString());
		return map;
	}

	@Override
	public QName getNodeAspect()
	{
		return CircabcModel.ASPECT_NEWSGROUP;
	}

	@Override
	public QName getNodeRootAspect()
	{
		return CircabcModel.ASPECT_NEWSGROUP_ROOT;
	}

	public String getProfilePrefix()
	{
		return PROFILE_PREFIX;
	}

	public boolean hasParentSubsGroup()
	{
		return true;
	}

	public boolean hasMasterParentGroup()
	{
		return true;
	}

	public boolean hasApplicantFeature()
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.cec.digit.circabc.service.profile.ProfileManagerServiceImpl#addApplicantPerson(org.alfresco.service.cmr.repository.NodeRef,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void addApplicantPerson(final NodeRef nodeRef, final String userID,
			final String message, final String firstName, final String lastName)
			throws ProfileException
	{
		throw new IllegalStateException(
				"This method is not implemented yet for the aspect "
						+ getNodeAspect());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.cec.digit.circabc.service.profile.ProfileManagerServiceImpl#removeApplicantPerson(org.alfresco.service.cmr.repository.NodeRef,
	 *      java.lang.String)
	 */
	@Override
	public void removeApplicantPerson(final NodeRef nodeRef, final String userID)
			throws ProfileException
	{
		throw new IllegalStateException(
				"This method is not implemented yet for the aspect "
						+ getNodeAspect());
	}

	@Override
	public List<Profile> getProfilesRecursivly(final NodeRef nodeRef) {
		return Collections.<Profile>emptyList();
	}
}
