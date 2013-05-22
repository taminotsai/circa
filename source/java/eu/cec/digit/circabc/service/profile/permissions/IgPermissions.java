/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile.permissions;

import java.util.HashSet;

/**
 * Enumeration representing the permissions associated to the interest group service
 * Enumeration are used to decribe all the existing permissions in the interste group
 *
 * @author Stephane Clinckart
 */
public enum IgPermissions
{
    IGDELETE("IgDelete"), IGCREATE("IgCreate");

    static HashSet<IgPermissions> igPermissions = null;

    String igPermissionString;

    protected static void init()
    {
        igPermissions = new HashSet<IgPermissions>();
        for (IgPermissions permission : IgPermissions.values())
        {
            igPermissions.add(permission);
        }

    }

    public static IgPermissions withPermissionString(String permiString)
	{
    	IgPermissions match = null;

		for(IgPermissions permission: getPermissions())
		{
			if(permission.igPermissionString.equals(permiString))
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

    IgPermissions(final String permission)
    {
        igPermissionString = permission;
    }

    /**
     * Return the string value associated to the permission
     *
     * @return
     */
    public String toString()
    {
        if (igPermissions == null)
            init();
        return igPermissionString;
    }

    /**
     * return an Set representing the permission list.
     *
     * @return Set of IgPermissions
     */
    public static HashSet<IgPermissions> getPermissions()
    {
        if (igPermissions == null)
            init();
        return (HashSet<IgPermissions>) igPermissions.clone();
    }

}
