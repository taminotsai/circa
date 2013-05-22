/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.business.ecas;

import org.alfresco.repo.security.authentication.AbstractAuthenticationComponent;
import org.alfresco.repo.security.authentication.AuthenticationException;

/**
 * The ECAS AuthenticationComponent used by the EcasAdapterService
 *
 * @author atadian
 */
public class EcasAuthenticationComponent extends AbstractAuthenticationComponent
{
    public EcasAuthenticationComponent()
    {
        super();
    }

    /**
     * Just set the current User. Authentication was already done by ECAS
     * @param userName the ECAS userName
     * @param pass will always be null. The authentication was already done by ECAS. It is here
     * to comply with the AbstractAuthenticationComponent requirements
     */
    public void authenticate(final String userName, final char pass[]) throws AuthenticationException
    {
        try
        {
            setCurrentUser(userName);

        }
        catch (final net.sf.acegisecurity.AuthenticationException ae)
        {
            throw new AuthenticationException(ae.getMessage(), ae);
        }
    }



    @Override
    protected boolean implementationAllowsGuestLogin()
    {
        return false;
    }


}
