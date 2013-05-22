/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile.permissions;

import java.util.HashSet;

/**
 * Enumeration representing the permissions associated to the Information service
 * Enumeration where used to decribe all the existing permissions in the CircaBC
 *
 * @author Yanick Pignot
 *
 */
public enum InformationPermissions
{
	INFADMIN("InfAdmin"),
	INFMANAGE("InfManage"),
	INFACCESS("InfAccess"),
	INFNOACCESS("InfNoAccess");

	protected String permissionString;

	static HashSet<InformationPermissions> informationPermissions = null;

	public static InformationPermissions withPermissionString(String permiString)
	{
		InformationPermissions match = null;

		for(InformationPermissions permission: getPermissions())
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
		informationPermissions = new HashSet<InformationPermissions>();
		for (InformationPermissions permission : InformationPermissions
				.values())
		{
			informationPermissions.add(permission);
		}

	}

	/**
	 * Constructor initialising the string value of the permission. The String
	 * values will be defined in the file permissionDefinitions.xml
	 *
	 * @param value
	 *            string value associated to the enumeration value.
	 */
	InformationPermissions(String value)
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
		if (informationPermissions == null)
			init();
		return permissionString;
	}

	/**
	 * return an Set representing the permission list.
	 *
	 * @return Set of LibraryPermissions
	 */
	public static HashSet<InformationPermissions> getPermissions()
	{
		if (informationPermissions == null)
			init();
		return (HashSet<InformationPermissions>) informationPermissions
				.clone();
	}

	public static InformationPermissions[] minimalValues()
	{
		return new InformationPermissions[]{
				INFACCESS, INFNOACCESS
		};
	}
}
