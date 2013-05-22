/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.service.Auditable;
import org.alfresco.service.NotAuditable;
import org.alfresco.service.cmr.repository.MLText;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import eu.cec.digit.circabc.repo.applicant.Applicant;

/**
 * This service provides operations to manage node with aspect CircaCategory
 * 
 * @author Clinckart Stephane
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * Commented the key parameter of the @Auditable annotation.
 * Commented the deprecated @PublicService annotation.
 */
//@PublicService
public interface CategoryProfileManagerService extends ProfileManagerService
{

	public interface Profiles
	{

		// This type of profile allows everything on the library but is
		// sensitive to
		// inheritense cuting. Can admin directory.
		public static final String CIRCA_CATEGORY_ADMIN = "CircaCategoryAdmin";

		public static final String CIRCA_CATEGORY_MANAGE_MEMBERS = "CircaCategoryManageMembers";
	}
	public interface Roles
	{
	}

	public static String SERVICE_NAME = "categoryProfileManagerService";

	public static String PROXIED_SERVICE_NAME = "CategoryProfileManagerService";

	/**
	 * Due to a bug of CGLib that will be corrected in version 1.2 the heritance
	 * of interface doesn't work correctly.
	 * 
	 * @TODO Remove duplicate declaration when new version will be used.
	 */

	/**
	 * Add a user to the list of applicants with additional details and wait the
	 * approval of an admin.
	 * 
	 * @param rootNode
	 *            the root node to which the user wants to apply to begin a
	 *            member
	 * @param userID
	 *            the applicant user username
	 * @param message
	 *            the message posted by the applicant
	 * @param firstName
	 *            the first name of the applicant
	 * @param lastName
	 *            the last name of the applicant
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "rootNode", "userID", "message", "firstName", "lastName" })
	public void addApplicantPerson(final NodeRef rootNode, final String userID, final String message, final String firstName,
			final String lastName);

	/**
	 * Add person to a profile in the rootNode. (Where Profile are stored) An
	 * exception is thrown if the person is already member of the rootNode. When
	 * a person is added, a profile must be specidied.
	 * 
	 * @param nodeRef
	 * @param userID
	 * @param profileName
	 * @throws Exception
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "nodeRef", "userID", "profileName" })
	public void addPersonToProfile(final NodeRef nodeRef, final String userID, final String profileName) throws ProfileException;

	/**
	 * Add a new profile called profileName, on node. Role for services are
	 * given with profVal. Exception are thrown when profileName already exist *
	 * When a permission doesn't exist for a given service.
	 * 
	 * @param profileName
	 *            profile name
	 * @param nodeRef
	 *            root node
	 * @param servicesPermissions
	 *            HashMap containing the coples (service1,permission in
	 *            service),(service2, permission in service)
	 * @throws Exception
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "nodeRef", "profileName", "servicesPermissions" })
	public void addProfile(final NodeRef nodeRef, final String profileName, final Map<String, Set<String>> servicesPermissions)
			throws ProfileException;

	/**
	 * @param nodeRef
	 * @param profileName
	 * @param newDescriptions
	 */
	abstract public void addProfileDescriptions(final NodeRef nodeRef, final String profileName, final MLText newDescriptions);

	/**
	 * @param nodeRef
	 * @param profileName
	 * @param newTitles
	 */
	abstract public void addProfileTitles(final NodeRef nodeRef, final String profileName, final MLText newTitles);

	/**
	 * Move a person. The person must already be in a profile in the group.
	 * Profile must exist in the rootNode. (Where Profile are stored)
	 * 
	 * @param userID
	 *            userID of the moved user
	 * @param profileName
	 *            profile name the user is moved to
	 * @rootNodeRef root of the Profile
	 * @throws Exception
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "nodeRef", "userID", "profileName" })
	public void changePersonProfile(final NodeRef nodeRef, final String userID, final String profileName) throws ProfileException;

	@NotAuditable
	public String createInvitedUsersGroup(final NodeRef nodeRef);

	@NotAuditable
	public String createMasterGroup(final NodeRef nodeRef);

	@NotAuditable
	public String createSubsGroup(final NodeRef nodeRef);

	/**
	 * Delete an existing profile. It throws an exception if profileName doesn't
	 * exist or if profile still contain users,
	 * 
	 * @param profileName
	 *            profile name being deleted
	 * @param rootNodeRef
	 *            rootNode the profile belongs to
	 * @throws Exception
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "nodeRef", "profileName" })
	public void deleteProfile(final NodeRef nodeRef, final String profileName) throws ProfileException;

	/**
	 * @param nodeRef
	 * @param profileName
	 * @param export
	 *            export or not
	 */
	abstract public void exportProfile(final NodeRef nodeRef, final String profileName, boolean export);

	/**
	 * Return a Set with users that are applicant to this node
	 * 
	 * @param rootNode
	 * @return
	 */
	@NotAuditable
	public Map<String, Applicant> getApplicantUsers(final NodeRef rootNode);

	/**
	 * Return the name of the ApplicantUsersMap associated to the nodeRef.
	 * 
	 * @return
	 */
	@NotAuditable
	public String getApplicantUsersMapName(final NodeRef nodeRef);

	/**
	 * return the CircaHome node of the given node
	 * 
	 * @param nodeRef
	 * @return
	 */
	@NotAuditable
	public NodeRef getCircaHome(final NodeRef nodeRef);

	/**
	 * return the root node of the given node who has aspect defined by the
	 * QName
	 * 
	 * @param nodeRef
	 * @return
	 */
	@NotAuditable
	public NodeRef getCurrentAspectRoot(final NodeRef nodeRef);

	/**
	 * Get all exported profile (recusively)
	 * 
	 * @param nodeRef
	 * @return
	 */
	abstract public List<Profile> getExportedProfiles(final NodeRef nodeRef);

	/**
	 * Return a Set with users that are invited to this node
	 * 
	 * @param rootNode
	 * @return
	 */
	@NotAuditable
	public Set<String> getInvitedUsers(final NodeRef rootNode);
	
	@NotAuditable
	public Map<String,Profile> getInvitedUsersProfiles(final NodeRef rootNode);
	
	/**
	 * Return all user that are invited in a subService
	 *
	 * @param nodeRef
	 * @return
	 */
	@NotAuditable
	public Set<String> getAllSubUsers(final NodeRef nodeRef);

	/**
	 * Return the name of the InvitedUsersGroup associated to the nodeRef. The
	 * group name is not prefixed
	 * 
	 * @return
	 */
	@NotAuditable
	public String getInvitedUsersGroupName(final NodeRef nodeRef);

	@NotAuditable
	abstract public QName getInvitedUsersGroupQName();

	@NotAuditable
	abstract public QName getMasterGroupQName();

	/**
	 * Return the name of the MasterInvitedGroup associated to the nodeRef. The
	 * group name is not prefixed
	 * 
	 * @return
	 */
	@NotAuditable
	public String getMasterInvitedGroupName(final NodeRef nodeRef);

	/**
	 * Return a Set with all users that are binded this node
	 * 
	 * @param rootNode
	 * @return
	 */
	@NotAuditable
	public Set<String> getMasterUsers(final NodeRef rootNode);

	/**
	 * Return the person set belonging to that profile
	 * 
	 * @param profileName
	 * @param nodeRef
	 * @return
	 */
	@NotAuditable
	public Set<String> getPersonInProfile(final NodeRef nodeRef, final String profileName);

	/**
	 * Get the profile name the person belongs to. Return null if the person
	 * doesn't belong to the rootNodeRef.
	 * 
	 * @param nodeRef
	 * @param userID
	 *            user id of the person
	 * @return profile name profile name
	 * @throws Exception
	 */
	@NotAuditable
	public String getPersonProfile(final NodeRef nodeRef, final String userID);

	/**
	 * Fing the Profile associated
	 * 
	 * @param nodeRef
	 * @param profileName
	 * @return
	 */
	@NotAuditable
	public Profile getProfile(final NodeRef nodeRef, final String profileName);

	@NotAuditable
	abstract public Profile getProfileFromGroup(final NodeRef nodeRef, final String groupId);

	/**
	 * Get Profile binded to the current Node
	 * 
	 * @param nodeRef
	 * @param profileName
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "nodeRef" })
	public Map<String, Profile> getProfileMap(final NodeRef nodeRef);

	/**
	 * Return the list of Profiles defined on the current node
	 * 
	 * @param nodeRef
	 * @return
	 */
	@NotAuditable
	public List<Profile> getProfiles(final NodeRef nodeRef);

	/**
	 * Fing the Profiles recursivly
	 * 
	 * @param nodeRef
	 * @return
	 */
	@NotAuditable
	public List<Profile> getProfilesRecursivly(final NodeRef nodeRef);

	/**
	 * @param nodeRef
	 * @param profileName
	 * @return
	 */
	abstract public List<NodeRef> getRefInExportedProfile(final NodeRef nodeRef, final String profileName);

	@NotAuditable
	public String getRegistredGroupName(final NodeRef nodeRef);

	/**
	 * Return a Set with users that are registred to this node
	 * 
	 * @param rootNode
	 * @return
	 */
	@NotAuditable
	public Set<String> getRegistredUsers(final NodeRef rootNode);

	/**
	 * @return the services handled by this node
	 */
	@NotAuditable
	public abstract Set<String> getServices();

	@NotAuditable
	abstract public String getSubsGroupName(final NodeRef nodeRef);

	@NotAuditable
	abstract public QName getSubsGroupQName();

	@NotAuditable
	public boolean hasApplicantFeature();

	@NotAuditable
	public boolean hasMasterParentGroup();

	@NotAuditable
	public boolean hasParentSubsGroup();

	/**
	 * @param toIgNoderef
	 * @param fromIgNoderef
	 * @param fromProfileName
	 */
	abstract public void importProfile(final NodeRef toIgNoderef, final NodeRef fromIgNoderef, final String fromProfileName,
			final Map<String, Set<String>> servicesPermissions);

	/**
	 * @param nodeRef
	 * @param profileName
	 * @return
	 */
	abstract public boolean isProfileDeletable(final NodeRef nodeRef, final String profileName);

	/**
	 * Remove a user of the list of applicants of the givent root node
	 * 
	 * @param rootNode
	 *            the root node from which the user is reject for being a
	 *            membership
	 * @param userName
	 *            the rejected user username
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "rootNode", "userName" })
	public void removeApplicantPerson(final NodeRef rootNode, final String userName);

	/**
	 * Rename an existing profile, an exception is thrown when oldName or if
	 * oldname already exist. An exception is also thrown if rootNodeRef has not
	 * specified aspect
	 * 
	 * @param nodeRef
	 *            root node of the profile
	 * @param oldProfileName
	 *            old profile name
	 * @param newProfileName
	 *            new profile name
	 * @throws Exception
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "nodeRef", "oldProfileName", "newProfileName" })
	public void renameProfile(NodeRef nodeRef, final String oldProfileName, final String newProfileName) throws ProfileException;

	//@NotAuditable
	//public void setAuthenticationService(final AuthenticationService authenticationService);

	//@NotAuditable
	//public void setAuthorityService(final AuthorityService authService);

	//@NotAuditable
	//public void setFileFolderService(final FileFolderService fileFolderService);

	//@NotAuditable
	//public void setNodeService(final NodeService nodeService);

	//@NotAuditable
	//public void setOwnableService(final OwnableService ownableService);

	//@NotAuditable
	//public void setPermissionService(final PermissionService permissionService);

	//@NotAuditable
	//public void setServiceRegistry(final ServiceRegistry serviceRegistry);

	/**
	 * RemovePerson from the interest group. rootNodeRef must be the root (where
	 * Profile are stored). user must belong to the rootNodeRef. Clear all
	 * specific permission given on any root of the rootNodeRef for that person
	 * (recursive)
	 * 
	 * @param userID
	 * @throws Exception
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "nodeRef", "userID" })
	public void uninvitePerson(final NodeRef nodeRef, final String userID) throws ProfileException;

	/**
	 * Change the profile. HashMap contain the new profile definition. Exception
	 * is also thrown if rootNodeRef is not the rootNode. (Where Profile are
	 * stored) Exception is generated when the profile value doesn't exist
	 * 
	 * @param profileName
	 *            name of the profile to be changed
	 * @param profVal
	 *            profile values
	 * @param rootNodeRef
	 *            root og the Profile
	 * @throws Exception
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "nodeRef", "profileName", "profVal" })
	public Profile updateProfile(final NodeRef nodeRef, final String profileName, final Map<String, Set<String>> profVal,
			final boolean updateProfileMap) throws ProfileException;

}
