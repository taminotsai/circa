/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.business.ecas;

import org.alfresco.repo.security.authentication.AuthenticationException;
import org.alfresco.service.Auditable;
import org.alfresco.service.PublicService;

/**
 * This services is an adapter to the ECAS service authenticator. It
 * is used by the EcasLoginBean to get the Alfresco Ticket
 *
 * @author atadian
 */
@PublicService
public interface EcasAdapterService
{
    /**
     * Gets the ticket related to the user who is loggin
     * @param pUserName the userName
     */
	@Auditable( parameters = {"pUserName"})
    public String getCurrentTicket(final String pUserName);

    /**
     * Fake authenticate method that creates
     * @param pUserName the userName
     */
	@Auditable( parameters = {"pUserName"})
    public void authenticate(final String pUserName) throws AuthenticationException;
}
