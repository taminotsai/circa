package eu.cec.digit.circabc.repo.webservice.authentication;

import java.rmi.RemoteException;

import org.alfresco.repo.security.authentication.AuthenticationComponent;
import org.alfresco.repo.security.authentication.AuthenticationException;
import org.alfresco.repo.webservice.Utils;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.repo.web.scripts.bean.TicketValidator;
import eu.cec.digit.ecas.client.validation.AuthenticationSuccess;


public class CircabcAuthenticationWebService implements CircabcAuthenticationServiceSoapPort 
{
	private static Log logger = LogFactory.getLog(CircabcAuthenticationWebService.class);

    private AuthenticationService authenticationService;
    
    private AuthenticationComponent authenticationComponent;

    /**
     * Sets the AuthenticationService instance to use
     * 
     * @param authenticationSvc
     *            The AuthenticationService
     */
    public void setAuthenticationService(AuthenticationService authenticationSvc)
    {
        this.authenticationService = authenticationSvc;
    }
    
    /**
     * Set the atuthentication component
     * 
     * @param authenticationComponent
     */
    public void setAuthenticationComponent(AuthenticationComponent authenticationComponent) 
    {
		this.authenticationComponent = authenticationComponent;
	}

    
    private TicketValidator ticketValidator; 
    
    /**
     * @see org.alfresco.repo.webservice.authentication.AuthenticationServiceSoapPort#startSession(java.lang.String,
     *      java.lang.String)
     */

	public AuthenticationResult startSession(String username, String ecasProxyTicket) throws RemoteException, AuthenticationFault
	{
		 try
	        {
			 	AuthenticationSuccess authenticationSuccess = ticketValidator.validateTicket(ecasProxyTicket);
  	       
	        	if (authenticationSuccess != null && authenticationSuccess.isSuccessful())
	        	{
	        		final String user = authenticationSuccess.getUser();
					if (user.equalsIgnoreCase(username))
	        		{
						this.authenticationComponent.setCurrentUser(username);
	        			String ticket = this.authenticationService.getCurrentTicket();

			            if (logger.isDebugEnabled())
			            {
			                logger.debug("Issued ticket '" + ticket + "' for '" + username + "'");
			            }
	            
			            return new AuthenticationResult(username, ticket, Utils.getSessionId());
	        		}
	        		else
	        		{
	        			if (logger.isErrorEnabled())
	    	        	{
	    	        		logger.error("Can not start session: ecas proxy ticket '" + ecasProxyTicket + "' is valid but user is invalid");
	    	        	}
	    	            throw new AuthenticationFault(100, "Invalid userName and ecasProxyTicket");
	        		}
	        	}
	        	else
	        	{
	        		if (logger.isErrorEnabled())
		        	{
		        		logger.error("Can not start session ecas proxy ticket '" + ecasProxyTicket + "' for '" + username + "'");
		        	}
		            throw new AuthenticationFault(100, "Invalid userName and ecasProxyTicket");
	        	}
	        }
	        catch (AuthenticationException ae)
	        {
	        	if (logger.isErrorEnabled())
	        	{
	        		logger.error("Can not start session ecas proxy ticket '" + ecasProxyTicket + "' for '" + username + "'" , ae);
	        	}
	            throw new AuthenticationFault(100, ae.getMessage());
	        } 
	        catch (Throwable e)
	        {
	        	if (logger.isErrorEnabled())
	        	{
	        		logger.error("Can not start session ecas proxy ticket '" + ecasProxyTicket + "' for '" + username + "'" , e);
	        	}
	            throw new AuthenticationFault(0, e.getMessage());
	        }

	}

	public void setTicketValidator(TicketValidator ticketValidator)
	{
		this.ticketValidator = ticketValidator;
	}

	public TicketValidator getTicketValidator()
	{
		return ticketValidator;
	}

}
