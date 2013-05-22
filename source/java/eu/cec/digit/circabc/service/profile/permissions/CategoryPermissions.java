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
public enum CategoryPermissions
{
	CIRCACATEGORYADMIN("CircaCategoryAdmin"), CIRCACATEGORYMANAGEMEMBERS(
			"CircaCategoryManageMembers"), CIRCACATEGORYACCESS(
			"CircaCategoryAccess"), CIRCACATEGORYNOACCESS(
			"CircaCategoryNoAccess");

	protected String permissionString;

	public static CategoryPermissions withPermissionString(String permiString)
	{
		CategoryPermissions match = null;

		for(CategoryPermissions permission: getPermissions())
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

	static HashSet<CategoryPermissions> circaCategoryPermissions = null;

	/**
	 * initialise the list of permissions
	 *
	 */
	protected static void init()
	{
		circaCategoryPermissions = new HashSet<CategoryPermissions>();
		for (CategoryPermissions permission : CategoryPermissions
				.values())
		{
			circaCategoryPermissions.add(permission);
		}

	}

	/**
	 * Constructor initialising the string value of the permission. The String
	 * values will be defined in the file permissionDefinitions.xml
	 *
	 * @param value
	 *            string value associated to the enumeration value.
	 */
	CategoryPermissions(String value)
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
		if (circaCategoryPermissions == null)
			init();
		return permissionString;
	}

	/**
	 * return an Set representing the permission list.
	 *
	 * @return Set of LibraryPermissions
	 */
	public static HashSet<CategoryPermissions> getPermissions()
	{
		if (circaCategoryPermissions == null)
			init();
		return (HashSet<CategoryPermissions>) circaCategoryPermissions
				.clone();
	}
}
