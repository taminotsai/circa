package eu.cec.digit.circabc.repo.web.scripts.bean;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.alfresco.repo.security.authentication.AuthenticationComponent;
import org.alfresco.repo.security.authentication.AuthenticationException;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import eu.cec.digit.ecas.client.validation.AuthenticationSuccess;

/**
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * DeclarativeWebScript was moved to Spring.
 * Status was moved to Spring.
 * WebScriptException was moved to Spring.
 * WebScriptRequest was moved to Spring.
 * WebScriptServletRequest was moved to Spring.
 * This class seems to be developed for CircaBC
 */
public class EcasLogin extends DeclarativeWebScript 
{

	/** A logger for the class */
	static final  Log logger = LogFactory.getLog(EcasLogin.class);
	// dependencies
    private AuthenticationService authenticationService;
	private AuthenticationComponent authenticationComponent;
	private TicketValidator ticketValidator;  
	public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
	public void setAuthenticationComponent(AuthenticationComponent authenticationComponent) {
		this.authenticationComponent = authenticationComponent;
	}

	/* (non-Javadoc)
     * @see org.alfresco.web.scripts.DeclarativeWebScript#executeImpl(org.alfresco.web.scripts.WebScriptRequest, org.alfresco.web.scripts.WebScriptResponse)
     */
    @Override
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status) {

        // extract username
        String username = req.getParameter("u");
        if (username == null || username.length() == 0) {
            throw new WebScriptException(HttpServletResponse.SC_BAD_REQUEST, "Username not specified");
        }
        // extract CAS ticket
        String ticket = req.getParameter("t");
        if (ticket == null) {
            throw new WebScriptException(HttpServletResponse.SC_BAD_REQUEST, "Ticket not specified");
        }

        try {
//        	WebScriptServletRequest request = (WebScriptServletRequest) req;
        	
        	AuthenticationSuccess authenticationSuccess = ticketValidator.validateTicket(ticket);
        	
        	if (authenticationSuccess != null && authenticationSuccess.isSuccessful()) {
        		
        		if (authenticationSuccess.getUser().equalsIgnoreCase(username)) {
        			
		            // add ticket to model for javascript and template access
		            Map<String, Object> model = new HashMap<String, Object>(7, 1.0f);
		            // authenticate our user
			    	authenticationComponent.setCurrentUser(username);
			    	// create a new alfresco ticket
			    	String alfticket = authenticationService.getCurrentTicket();
			    	model.put("ticket",  alfticket);
//			    	model.put("session", request.getHttpServletRequest().getSession().getId());
			    	return model;
        		}
        		else {
        			throw new WebScriptException(HttpServletResponse.SC_FORBIDDEN, "Login failed");
        		}
        	}
        	else {
        		throw new WebScriptException(HttpServletResponse.SC_FORBIDDEN, "Login failed");
        	}
        } 
        catch(AuthenticationException e) {
            throw new WebScriptException(HttpServletResponse.SC_FORBIDDEN, "Login failed");
        }  
        finally {
            authenticationService.clearCurrentSecurityContext();
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
