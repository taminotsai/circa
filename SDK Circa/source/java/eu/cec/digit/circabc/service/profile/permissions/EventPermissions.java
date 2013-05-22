/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile.permissions;

import java.util.HashSet;

/**
 * Enumeration representing the permissions associated to the Event service
 * Enumeration where used to decribe all the existing permissions in the CircaBC
 *
 * @author Pignot Yanick
 *
 */
public enum EventPermissions
{
	EVEADMIN("EveAdmin"),
	EVEACCESS("EveAccess"),
	EVENOACCESS("EveNoAccess");

	protected String permissionString;

	static HashSet<EventPermissions>eventPermissions = null;

	public static EventPermissions withPermissionString(String permiString)
	{
		EventPermissions match = null;

		for(EventPermissions permission: getPermissions())
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
		eventPermissions = new HashSet<EventPermissions>();
		for (EventPermissions permission : EventPermissions
				.values())
		{
			eventPermissions.add(permission);
		}

	}

	/**
	 * Constructor initialising the string value of the permission. The String
	 * values will be defined in the file permissionDefinitions.xml
	 *
	 * @param value
	 *            string value associated to the enumeration value.
	 */
	EventPermissions(String value)
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
		if (eventPermissions == null)
			init();
		return permissionString;
	}

	/**
	 * return an Set representing the permission list.
	 *
	 * @return Set of LibraryPermissions
	 */
	public static HashSet<EventPermissions> getPermissions()
	{
		if (eventPermissions == null)
			init();
		return (HashSet<EventPermissions>) eventPermissions
				.clone();
	}

	public static EventPermissions[] minimalValues()
	{
		return new EventPermissions[]{
				EVEACCESS, EVENOACCESS
		};
	}
}
