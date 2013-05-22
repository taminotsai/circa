/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile;

/**
 * Exception used by the ProfileManagerService
 *
 * @author Clinckart Stephane
 *
 */
public class ProfileException extends RuntimeException
{

	/**
	 *
	 */
	private static final long serialVersionUID = 9088824582171521166L;

	String profileName;

	String explanation;

	public ProfileException(String profileName, String explain)
	{
		super(profileName + ": " + explain);

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
