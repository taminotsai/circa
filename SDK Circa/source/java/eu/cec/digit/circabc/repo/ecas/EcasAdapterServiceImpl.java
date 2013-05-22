/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.ecas;

import org.alfresco.repo.security.authentication.AuthenticationComponent;
import org.alfresco.repo.security.authentication.AuthenticationException;
import org.alfresco.repo.security.authentication.TicketComponent;
import org.alfresco.service.cmr.security.NoSuchPersonException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.business.ecas.EcasAdapterService;

/**
 * This services is an adapter to the ECAS service authenticator. It
 * is used by the EcasLoginBean to get the Alfresco Ticket
 *
 * @author atadian
 */
public class EcasAdapterServiceImpl implements EcasAdapterService
{

    /** Ticket component **/
    private TicketComponent ticketComponent;

    /** The authentication component. In this case is a EcasAuthenticationComponent */
    private AuthenticationComponent authenticationComponent;

    /** The logger */
    private static final Log logger = LogFactory.getLog(EcasAdapterServiceImpl.class);

    public EcasAdapterServiceImpl()
    {
        super();
    }

    public void setTicketComponent(final TicketComponent ticketComponent)
    {
    	if(logger.isInfoEnabled())
    		logger.info("setTicketComponent " + ticketComponent) ;
        this.ticketComponent = ticketComponent;
    }
    public void setAuthenticationComponent(final AuthenticationComponent authenticationComponent)
    {
    	if(logger.isInfoEnabled())
    		logger.info("setAuthenticationComponent " + authenticationComponent) ;
        this.authenticationComponent = authenticationComponent;
    }

    public void invalidateUserSession(final String userName) throws AuthenticationException
    {
        ticketComponent.invalidateTicketByUser(userName);
    }

    public void invalidateTicket(final String ticket) throws AuthenticationException
    {
        ticketComponent.invalidateTicketById(ticket);
    }

    /**
     * Gets the ticket related to the user who is loggin
     * @param pUserName the userName
     */
    public String getCurrentTicket(final String pUserName)
    {
        return ticketComponent.getCurrentTicket(pUserName ,false);
    }

    /**
     * Fake authenticate method that creates
     * @param pUserName the userName
     */
    public void authenticate(final String pUserName) throws AuthenticationException, NoSuchPersonException
    {
        authenticationComponent.authenticate(pUserName, new char[0]);
    }
}
