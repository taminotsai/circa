/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile;


/**
 * Exception used by the IGServiceProfileManager
 * @author Philippe Dubois
 *
 */
public class IGRootProfileException extends RuntimeException {

	private static final long serialVersionUID = 4627788862738776094L;

   String profileName;
   String explanation;

   public IGRootProfileException(String profileName, String explain)
   {
      this.profileName = profileName;
      this.explanation = explain;
   }

   public String getProfileName()
   {
      return profileName;
   }

   public String getExplanation()
   {
      return explanation;
   }

}
