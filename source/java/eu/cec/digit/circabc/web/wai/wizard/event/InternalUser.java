/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.wizard.event;

/**
 * Client side Internal user representation
 *
 * @author Yanick Pignot
 */
public class InternalUser
{
    private String authority;
    private String firstName;
    private String lastName;
    private String email;

    public InternalUser(final String authority, final String firstName, final String lastName, final String email)
    {
        this.authority = authority;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public InternalUser(final String authority, final String firstName)
    {
        this(authority, firstName, null, null);
    }

    public final String getAuthority()
    {
        return authority;
    }

    public final String getLongDisplayName()
    {
        return getShortDisplayName() + ((email == null) ? "" : " (" + email + ")");
    }

    public final String getShortDisplayName()
    {
        return ((firstName == null) ? "" : firstName + " ") + ((lastName == null) ? "" : lastName) ;
    }


	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}
}