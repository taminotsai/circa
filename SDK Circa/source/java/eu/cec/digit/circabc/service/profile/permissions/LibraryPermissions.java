/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile.permissions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;



/**
 * Enumeration representing the permissions associated to the library service
 * Enumeration where used to decribe all the existing permissions in the library
 * @author Philippe Dubois
 *
 */
public enum LibraryPermissions   {
   LIBADMIN("LibAdmin"),LIBFULLEDIT("LibFullEdit"),LIBEDITONLY("LibEditOnly"),LIBMANAGEOWN("LibManageOwn"),
   LIBACCESS("LibAccess"),LIBNOACCESS("LibNoAccess");

   protected String libraryPermissionString;
   static HashSet<LibraryPermissions> libPermissions = null;

   protected static  final List<String> orderedLibraryPermissions  =  Collections.unmodifiableList(Arrays.asList("LibNoAccess", "LibAccess", "LibManageOwn", "LibEditOnly", "LibFullEdit", "LibAdmin"));


   /**
    * initialise the list of permissions
    *
    */
   protected static void init()
    {

	   libPermissions = new HashSet<LibraryPermissions>();
       for (LibraryPermissions permission : LibraryPermissions.values())
       {
    	   libPermissions.add(permission);
       }
    }

   public static LibraryPermissions withPermissionString(String permiString)
	{
	   LibraryPermissions match = null;

		for(LibraryPermissions permission: getPermissions())
		{
			if(permission.libraryPermissionString.equals(permiString))
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
    * Constructor initialising the string value of the permission.
    * The String values will be defined in the file permissionDefinitions.xml
    * @param value string value associated to the enumeration value.
    */
   LibraryPermissions(String value)
   {
      libraryPermissionString = value;
   }

   /**
    * Return the string value associated to the permission
    * @return
    */
   public String toString()
   {
      if (libPermissions == null) init();
      return libraryPermissionString;
   }

   /**
    * return an List representing the permission list.
    * @return List of LibraryPermissions
    */
   public static HashSet<LibraryPermissions> getPermissions()
   {
      if (libPermissions == null) init();
      return (HashSet<LibraryPermissions>)libPermissions.clone();
   }

   /**
    * return an List representing the permission list.
    * @return List of LibraryPermissions
    */
   public static List<String> getOrderedLibraryPermissions()
   {
      return orderedLibraryPermissions;
   }

   public static LibraryPermissions[] minimalValues()
	{
		return new LibraryPermissions[]{
				LIBACCESS, LIBNOACCESS
		};
	}
}

