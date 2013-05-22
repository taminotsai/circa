/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.api.user;


/**
 * Business service to manage remote management of users.
 * <p>
 * 		<b>The remote management can be disabled.<b> If not, it's usually a ldap server.
 * </p>
 * @see RemoteUserBusinessSrv#isRemoteManagementAvailable()
 *
 * @author Yanick Pignot
 */
public interface RemoteUserBusinessSrv
{

	/**
	 * Return if the current instanllation supports remote user managemet
	 *
	 * @return
	 */
	public abstract boolean isRemoteManagementAvailable();

	/**
	 * Reload user details from the remote user manager server.
	 *
	 * <b>Only the argument will be reloaded. To persist it, user UserDetailsBusinessSrv.updateUserDetails</b>
	 *
	 * @see eu.cec.digit.circabc.business.api.user.UserDetailsBusinessSrv#updateUserDetails
	 *
	 * @param userDetails
	 */
	public abstract void reloadDetails(final UserDetails userDetails);


	/**
	 * Return if the given username is found in the remote server manager
	 *
	 * @param userId							The user id of the user to query
	 * @return
	 */
	public abstract boolean userExists(final String userId);

}
