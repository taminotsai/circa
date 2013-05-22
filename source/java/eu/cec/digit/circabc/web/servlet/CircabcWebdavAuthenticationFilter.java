package eu.cec.digit.circabc.web.servlet;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.SessionUser;
import org.alfresco.repo.security.authentication.AuthenticationException;
import org.alfresco.repo.web.filter.beans.DependencyInjectedFilter;
import org.alfresco.repo.webdav.auth.BaseAuthenticationFilter;
import org.alfresco.repo.webdav.auth.WebDAVUser;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.NoSuchPersonException;
import org.alfresco.web.app.servlet.AuthenticationHelper;
import org.alfresco.web.app.servlet.AuthenticationStatus;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * WebDAV Authentication Filter Class
 *
 * @author
 */
public class CircabcWebdavAuthenticationFilter extends BaseAuthenticationFilter implements DependencyInjectedFilter
{
    private static final String DOT_PPT = ".ppt";
    
	// Debug logging
    
    private static Log logger = LogFactory.getLog(CircabcWebdavAuthenticationFilter.class);
    
    // Authenticated user session object name
    
    public final static String AUTHENTICATION_USER = "_alfDAVAuthTicket";
    
    // Allow an authentication ticket to be passed as part of a request to bypass authentication
    
    private static final String ARG_TICKET = "ticket";
    
	/**	
     * Run the authentication filter
     * 
     * @param context
     * @param req ServletRequest
     * @param resp ServletResponse
     * @param chain FilterChain
     * @exception ServletException
     * @exception IOException
     */
	public void doFilter(ServletContext context, ServletRequest req,
			ServletResponse resp, FilterChain chain) throws IOException,
			ServletException {
    	
    	// Assume it's an HTTP request
    	
    	final HttpServletRequest httpReq = (HttpServletRequest) req;
    	final HttpServletResponse httpResp = (HttpServletResponse) resp;
    	
        // Get the user details object from the session
        SessionUser user = checkHttpUser(context, httpReq, httpResp);
        
        if (user == null)
        {
            // Get the authorization header
        	
        	final String authHdr = httpReq.getHeader("Authorization");
        	
            if (authHdr != null && authHdr.length() > 5 && authHdr.substring(0,5).equalsIgnoreCase("BASIC"))
            {
                // Basic authentication details present
            	
            	final String basicAuth = new String(Base64.decodeBase64(authHdr.substring(5).getBytes()));
            	
                // Split the username and password
            	
                String username = null;
                String password = null;
                
                int pos = basicAuth.indexOf(':');
                if ( pos != -1)
                {
                    username = basicAuth.substring(0, pos);
                    password = basicAuth.substring(pos + 1);
                }
                else
                {
                    username = basicAuth;
                    password = "";
                }
                
                try
                {
                    // Authenticate the user
                	authenticationService.authenticate(username, password.toCharArray());
                	
                    // Setup User object and Home space ID etc.
                	user = createUserEnvironment(httpReq.getSession(), authenticationService.getCurrentUserName(), authenticationService.getCurrentTicket(), false);                    
                }
                catch (final AuthenticationException ex)
                {
                    // Do nothing, user object will be null
                }
                catch (final NoSuchPersonException e)
                {
                    // Do nothing, user object will be null
                }
            }
            else
            {
            	// Check if the request includes an authentication ticket
            	
            	String ticket = req.getParameter(ARG_TICKET);
            	
            	if(ticket != null &&  ticket.length() > 0)
            	{
            		// PowerPoint bug fix
            		if (ticket.endsWith(DOT_PPT))
            		{
            			ticket = ticket.substring(0, ticket.length() - DOT_PPT.length());
            		}
            		
                	// Debug
            		
                    if (logger.isDebugEnabled()) {
                        logger.debug("Logon via ticket from " + req.getRemoteHost() + " (" +
                                req.getRemoteAddr() + ":" + req.getRemotePort() + ")" + " ticket=" + ticket);
                    }
        	    	// Validate the ticket
                    
        	    	authenticationService.validate(ticket);
        	    	
        	    	// Need to create the User instance if not already available
        	    	
        	    	final String currentUsername = authenticationService.getCurrentUserName();
        	    	
                    user = createUserEnvironment(httpReq.getSession(), currentUsername, ticket, false);
            	}
            }
            
            // Check if the user is authenticated, if not then prompt again
            
            if (user == null)
            {
                // No user/ticket, force the client to prompt for logon details
            	
                httpResp.setHeader("WWW-Authenticate", "BASIC realm=\"Alfresco DAV Server\"");
                httpResp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                
                httpResp.flushBuffer();
                return;
            }
        }
        
        // Chain other filters
        chain.doFilter(req, resp);
    }
	
    /**
     * Cleanup filter resources
     */
    public void destroy()
    {
        // Nothing to do
    }

	/**
	 * @param httpReq
	 * @param user
	 * @param status
	 * @return
	 */
	private WebDAVUser checkHttpUser(final ServletContext servletContext, 
			final HttpServletRequest httpReq, final HttpServletResponse httpResp) {
		
//        SessionUser user = getSessionUser(context, httpReq, httpResp, false);
		
		// Get the user details object from the session
    	AuthenticationStatus status;
    	
		try {
			status = AuthenticationHelper.authenticate(servletContext, httpReq, httpResp, false, false);
		} 
		catch (final IOException e1) {
			//TODO Add some logs here
			return null;
		}
		
		// ECAS component stores the user principal
		final Principal userPrincipal = httpReq.getUserPrincipal();
		
        if ((userPrincipal != null) || (status == AuthenticationStatus.Success))
        {
        	UserTransaction tx = null;
    	    try
    	    {
    	    	// Validate the ticket
    	    	final String ticket = authenticationService.getCurrentTicket();
    	    	authenticationService.validate(ticket);

    	    	// Need to create the User instance if not already available
    	    	final String currentUsername = authenticationService.getCurrentUserName();

    	        // Start a transaction
  	            tx = transactionService.getUserTransaction();
    	        tx.begin();

    	        final NodeRef personRef = personService.getPerson(currentUsername);
    	        final NodeRef homeRef = (NodeRef) nodeService.getProperty(personRef, ContentModel.PROP_HOMEFOLDER);

    	        // Check that the home space node exists - else Login cannot proceed
    	        if (nodeService.exists(homeRef) == false)
    	        {
    	        	throw new InvalidNodeRefException(homeRef);
    	        }

    	        final WebDAVUser webDavUser = new WebDAVUser(currentUsername, authenticationService.getCurrentTicket(), homeRef);
    	        tx.commit();
    	        tx = null;

    	        // Store the User object in the Session - the authentication servlet will then proceed
    	        httpReq.getSession().setAttribute(AUTHENTICATION_USER, webDavUser);
    	        return webDavUser;
    	    }
        	catch (final AuthenticationException authErr)
        	{
        		// Clear the user object to signal authentication failure

        		//user = null;
        	}
        	catch (final Throwable e)
        	{
        		// Clear the user object to signal authentication failure

        		//user = null;
        	}
        	finally
        	{
        		try
        	    {
        			if (tx != null)
        	        {
        				tx.rollback();
       	        	}
        	    }
        	    catch (final Exception tex)
        	    {
        	    }
        	}
        }
        //userPrincipal == null) && status != AuthenticationStatus.Success
        // or Exception
		return null;
	}
    
	/**
     * @see org.alfresco.repo.webdav.auth.BaseAuthenticationFilter#getLogger()
     */
    protected Log getLogger() {
        return logger;
    }
}
