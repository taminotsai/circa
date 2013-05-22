/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile.permissions;

import java.util.HashSet;

/**
 * Enumeration representing the permissions associated to the library service
 * Enumeration where used to decribe all the existing permissions in the CircaBC
 *
 * @author Clinckart Stephane
 *
 */
public enum CircabcRootPermissions
{
	CIRCABCADMIN("CircaBCAdmin"), CIRCABCMANAGEMEMBERS("CircaBCManageMembers"), CIRCABCACCESS(
			"CircaBCAccess"), CIRCABCNOACCESS("CircaBCNoAccess");

	protected String permissionString;

	static HashSet<CircabcRootPermissions> circaBCPermissions = null;

	public static CircabcRootPermissions withPermissionString(String permiString)
	{
		CircabcRootPermissions match = null;

		for(CircabcRootPermissions permission: getPermissions())
		{
			if(permission.permissionString.equals(permiString))
			{
				match = permission;
				break;
			}
		}
		if(match == null)
		{
			throw new IllegalArgumentException("No enum const class with permission string " + permiString);
		}
		else
		{
			return match;
		}
	}

	/**
	 * initialise the list of permissions
	 *
	 */
	protected static void init()
	{
		circaBCPermissions = new HashSet<CircabcRootPermissions>();
		for (CircabcRootPermissions permission : CircabcRootPermissions.values())
		{
			circaBCPermissions.add(permission);
		}

	}

	/**
	 * Constructor initialising the string value of the permission. The String
	 * values will be defined in the file permissionDefinitions.xml
	 *
	 * @param value
	 *            string value associated to the enumeration value.
	 */
	CircabcRootPermissions(String value)
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
		if (circaBCPermissions == null)
			init();
		return permissionString;
	}

	/**
	 * return an Set representing the permission list.
	 *
	 * @return Set of CircabcPermissions
	 */
	@SuppressWarnings("unchecked")
	public static HashSet<CircabcRootPermissions> getPermissions()
	{
		if (circaBCPermissions == null)
			init();
		return (HashSet<CircabcRootPermissions>) circaBCPermissions.clone();

	}

}
