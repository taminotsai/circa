/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.api.user;

import java.io.File;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Business service to manage user details (properties and preferences).
 *
 * @author Yanick Pignot
 */
public interface UserDetailsBusinessSrv
{

	/**
	 * Get all details of the given person.
	 *
	 * @param person						The person to query
	 * @return								A person POJO
	 */
	public UserDetails getUserDetails(final NodeRef person);

	/**
	 * Get all details of the given person identified by its user name.
	 *
	 * @param username						The username to query
	 * @return								A person POJO
	 */
	public UserDetails getUserDetails(final String username);

	/**
	 * Get all details of the current authenticated user.
	 *
	 * @return								A person POJO
	 */
	public UserDetails getMyDetails();

	/**
	 * Upload an image file and set it as avatar for the given person.
	 *
	 * @param person						The person to update
	 * @param avatarFileName				The name of the avatar file
	 * @param avatarFile					The avatar to upload
	 * @return								The created avatar node reference
	 */
	public abstract NodeRef updateAvatar(final NodeRef person, final String avatarFileName, final File avatarFile);

	/**
	 * Update the avatar of a person with an existing repository file.
	 *
	 * @param person						The person to update
	 * @param imageRef						The existing repository file
	 */
	public abstract void updateAvatar(final NodeRef person, final NodeRef imageRef);

	/**
	 * Remove the user defined avatar to use the default one.
	 *
	 * @param person						The person to update
	 * @return							.
	 */
	public abstract void removeAvatar(final NodeRef person);

	/**
	 * Return the avatar of the given person.
	 *
	 * @param person					The person to query
	 * @return							The configured or default avatar node reference
	 */
	public abstract NodeRef getAvatar(final NodeRef person);

	/**
	 * Return the default avatar.
	 *
	 * @return							The default avatar node reference
	 */
	public abstract NodeRef getDefaultAvatar();

	/**
	 * Update modified user details (prefernces and properties).
	 * <b>Warning:</b>
	 * <p>
	 * 		This method doen'st support avatar changes. Use updateAvatar and removeAvatar methods of this same class.	 *
	 * </p>
	 *
	 * @see eu.cec.digit.circabc.business.api.user.UserDetailsBusinessSrv#updateAvatar(NodeRef, String, File)
	 * @see eu.cec.digit.circabc.business.api.user.UserDetailsBusinessSrv#updateAvatar(NodeRef, NodeRef)
	 * @see eu.cec.digit.circabc.business.api.user.UserDetailsBusinessSrv#removeAvatar(NodeRef)
	 *
	 * TODO remove the first argument (personRef) when a <i>AclAwareWrapper</i> aware Acl voters will be implemented
	 *
	 * @param personRef							The person node reference (Only used for security check, MUST be equals to userDetails.getNodeRef())
	 * @param userDetails						The details of the person to update.
	 */
	public void updateUserDetails(final NodeRef personRef, final UserDetails userDetails);
}
