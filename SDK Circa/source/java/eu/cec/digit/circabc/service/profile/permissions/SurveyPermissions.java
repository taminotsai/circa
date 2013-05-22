/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile.permissions;

import java.util.HashSet;



/**
 * Enumeration representing the permissions associated to the survey service
 * Enumeration where used to decribe all the existing permissions in the survey
 *
 * @author Yanick Pignot
 */
public enum SurveyPermissions   {
   SURADMIN("SurAdmin"), SURENCODE("SurEncode"), SURACCESS("SurAccess"),
   SURNOACCESS("SurNoAccess");

   protected String surveyPermissionString;
   static HashSet<SurveyPermissions> surveyPermissions = null;

   /**
    * initialise the list of permissions
    *
    */
   protected static void init()
   {
	   surveyPermissions = new HashSet<SurveyPermissions>();
	   for (SurveyPermissions permission : SurveyPermissions.values())
	   {
		   surveyPermissions.add(permission);
	   }
   }

   public static SurveyPermissions withPermissionString(String permiString)
   {
	   SurveyPermissions match = null;

		for(SurveyPermissions permission: getPermissions())
		{
			if(permission.surveyPermissionString.equals(permiString))
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
   SurveyPermissions(String value)
   {
      surveyPermissionString = value;
   }

   /**
    * Return the string value associated to the permission
    * @return
    */
   public String toString()
   {
      if (surveyPermissions == null) init();
      return surveyPermissionString;
   }

   /**
    * return an List representing the permission list.
    * @return List of LibraryPermissions
    */
   public static HashSet<SurveyPermissions> getPermissions()
   {
      if (surveyPermissions == null) init();
      return (HashSet<SurveyPermissions>)surveyPermissions.clone();
   }

   public static SurveyPermissions[] minimalValues()
	{
		return new SurveyPermissions[]{
				SURACCESS, SURNOACCESS
		};
	}

}

