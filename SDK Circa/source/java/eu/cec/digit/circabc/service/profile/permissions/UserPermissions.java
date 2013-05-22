/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile.permissions;

import java.util.HashSet;

/**
 * Enumeration representing the permissions associated to the user information
 * Enumeration where used to decribe all the existing permissions in the CircaUserAspect
 *
 * @author Clinckart Stephane
 *
 */
public enum UserPermissions
{
	PERSONINFOREAD("PersonInfoRead");

	protected String permissionString;

	static HashSet<UserPermissions> circaUserPermissions = null;

	/**
	 * initialise the list of permissions
	 *
	 */
	protected static void init()
	{
		circaUserPermissions = new HashSet<UserPermissions>();
		for (final UserPermissions permission : UserPermissions.values())
		{
			circaUserPermissions.add(permission);
		}

	}

	/**
	 * Constructor initialising the string value of the permission. The String
	 * values will be defined in the file permissionDefinitions.xml
	 *
	 * @param value
	 *            string value associated to the enumeration value.
	 */
	UserPermissions(String value)
	{
		permissionString = value;
	}

	/**
	 * Return the string value associated to the permission
	 *
	 * @return
	 */
	public String toString()
	{
		if (circaUserPermissions == null)
			init();
		return permissionString;
	}

	/**
	 * return an Set representing the permission list.
	 *
	 * @return Set of CircabcPermissions
	 */
	@SuppressWarnings("unchecked")
	public static HashSet<UserPermissions> getPermissions()
	{
		if (circaUserPermissions == null)
			init();
		return (HashSet<UserPermissions>) circaUserPermissions.clone();

	}

}
