/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile.permissions;

import java.util.HashSet;

/**
 * Enumeration representing the permissions associated to the Visibility permission
 * Enumeration are used to decribe all the existing Visibility permissions
 *
 * @author clinckart
 */
public enum VisibilityPermissions
{

	VISIBILITY("Visibility"), NOVISIBILITY("NoVisibility");

    static HashSet<VisibilityPermissions> visibilityPermissions = null;

    String visibilityPermissionString;

    protected static void init()
    {
        visibilityPermissions = new HashSet<VisibilityPermissions>();
        for (VisibilityPermissions permission : VisibilityPermissions.values())
        {
            visibilityPermissions.add(permission);
        }
    }

    public static VisibilityPermissions withPermissionString(String permiString)
	{
    	VisibilityPermissions match = null;

		for(VisibilityPermissions permission: getPermissions())
		{
			if(permission.visibilityPermissionString.equals(permiString))
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


    VisibilityPermissions(String permission)
    {
        visibilityPermissionString = permission;
    }

    /**
     * Return the string value associated to the permission
     *
     * @return
     */
    public String toString()
    {
        if (visibilityPermissions == null)
            init();
        return visibilityPermissionString;
    }

    /**
     * return an List representing the permission list.
     *
     * @return List of JoinPermissions
     */
    public static HashSet<VisibilityPermissions> getPermissions()
    {
        if (visibilityPermissions == null)
            init();
        return (HashSet<VisibilityPermissions>) visibilityPermissions.clone();
    }

}
