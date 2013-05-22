/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile.permissions;

import java.util.HashSet;



/**
 * Enumeration representing the permissions associated to the newsgroup service
 * Enumeration where used to decribe all the existing permissions in the newsgroup
 * @author Clinckart Stephane
 *
 */
public enum NewsGroupPermissions   {
   NWSADMIN("NwsAdmin"), NWSMODERATE("NwsModerate"), NWSPOST("NwsPost"),
   NWSACCESS("NwsAccess"), NWSNOACCESS("NwsNoAccess");

   protected String newsgroupPermissionString;
   static HashSet<NewsGroupPermissions> newsgroupPermissions = null;

   public static NewsGroupPermissions withPermissionString(String permiString)
	{
	   NewsGroupPermissions match = null;

		for(NewsGroupPermissions permission: getPermissions())
		{
			if(permission.newsgroupPermissionString.equals(permiString))
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
      newsgroupPermissions = new HashSet<NewsGroupPermissions>();
       for (NewsGroupPermissions permission : NewsGroupPermissions.values())
    {
         newsgroupPermissions.add(permission);
    }

    }

   /**
    * Constructor initialising the string value of the permission.
    * The String values will be defined in the file permissionDefinitions.xml
    * @param value string value associated to the enumeration value.
    */
   NewsGroupPermissions(String value)
   {
      newsgroupPermissionString = value;
   }

   /**
    * Return the string value associated to the permission
    * @return
    */
   public String toString()
   {
      if (newsgroupPermissions == null) init();
      return newsgroupPermissionString;
   }

   /**
    * return an List representing the permission list.
    * @return List of LibraryPermissions
    */
   public static HashSet<NewsGroupPermissions> getPermissions()
   {
      if (newsgroupPermissions == null) init();
      return (HashSet<NewsGroupPermissions>)newsgroupPermissions.clone();
   }

   public static NewsGroupPermissions[] minimalValues()
	{
		return new NewsGroupPermissions[]{
				NWSPOST ,NWSACCESS, NWSNOACCESS
		};
	}

}

