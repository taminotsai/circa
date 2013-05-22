/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile.permissions;

import java.util.HashSet;

/**
 * Enumeration representing the permissions associated to the directory service
 * Enumeration are used to decribe all the existing permissions in the directory
 *
 * @author Philippe Dubois
 */
public enum DirectoryPermissions
{
    DIRADMIN("DirAdmin"), DIRMANAGEMEMBERS("DirManageMembers"), DIRACCESS(
            "DirAccess"), DIRNOACCESS("DirNoAccess");

    static HashSet<DirectoryPermissions> dirPermissions = null;

    String dirPermissionString;

    public static DirectoryPermissions withPermissionString(String permiString)
	{
    	DirectoryPermissions match = null;

		for(DirectoryPermissions permission: getPermissions())
		{
			if(permission.dirPermissionString.equals(permiString))
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

    protected static void init()
    {
        dirPermissions = new HashSet<DirectoryPermissions>();
        for (DirectoryPermissions permission : DirectoryPermissions.values())
        {
            dirPermissions.add(permission);
        }

    }

    DirectoryPermissions(String permission)
    {
        dirPermissionString = permission;
    }

    /**
     * Return the string value associated to the permission
     *
     * @return
     */
    public String toString()
    {
        if (dirPermissions == null)
            init();
        return dirPermissionString;
    }

    /**
     * return an Set representing the permission list.
     *
     * @return Set of LibraryPermissions
     */
    public static HashSet<DirectoryPermissions> getPermissions()
    {
        if (dirPermissions == null)
            init();
        return (HashSet<DirectoryPermissions>) dirPermissions.clone();
    }

    public static DirectoryPermissions[] minimalValues()
	{
		return new DirectoryPermissions[]{
				DIRACCESS, DIRNOACCESS
		};
	}

}
