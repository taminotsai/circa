/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile.permissions;

import java.util.HashSet;

/**
 * Enumeration representing the permissions associated to the join permission
 * Enumeration are used to decribe all the existing join permissions
 *
 * @author Yanick Pignot
 */
public enum JoinPermissions
{

	JOIN("Join"), NOJOIN("NoJoin");

    static HashSet<JoinPermissions> joinPermissions = null;

    String joinPermissionString;

    protected static void init()
    {
        joinPermissions = new HashSet<JoinPermissions>();
        for (JoinPermissions permission : JoinPermissions.values())
        {
            joinPermissions.add(permission);
        }

    }

    public static JoinPermissions withPermissionString(String permiString)
	{
    	JoinPermissions match = null;

		for(JoinPermissions permission: getPermissions())
		{
			if(permission.joinPermissionString.equals(permiString))
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

    JoinPermissions(String permission)
    {
        joinPermissionString = permission;
    }

    /**
     * Return the string value associated to the permission
     *
     * @return
     */
    public String toString()
    {
        if (joinPermissions == null)
            init();
        return joinPermissionString;
    }

    /**
     * return an List representing the permission list.
     *
     * @return List of JoinPermissions
     */
    public static HashSet<JoinPermissions> getPermissions()
    {
        if (joinPermissions == null)
            init();
        return (HashSet<JoinPermissions>) joinPermissions.clone();
    }

}
