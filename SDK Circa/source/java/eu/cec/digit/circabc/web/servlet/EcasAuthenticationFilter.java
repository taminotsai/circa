/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.servlet;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.alfresco.web.app.Application;
import org.alfresco.web.app.servlet.AuthenticationHelper;
import org.alfresco.web.app.servlet.AuthenticationStatus;
import org.alfresco.web.app.servlet.FacesHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.web.ui.common.renderer.ErrorsRenderer;

/**
 * Servlet filter responsible for redirecting to the login page for the Web
 * Client if the user does not have a valid ticket.
 * <p>
 * The current ticker is validated for each page request and the login page is
 * shown if the ticker has expired.
 * <p>
 *
 * It overrides the AuthenticationFilter by letting ecaslogin.jsp pass the
 * filter
 * 
 * Save to delete on OSS? Not being used!
 * 
 * @author atadian
 */
public class EcasAuthenticationFilter extends eu.cec.digit.circabc.web.app.servlet.AuthenticationFilter
{
     private static final String FACES_JSP_EXTENSION_SESSION_EXPIRED_JSP = "/faces/jsp/extension/session_expired.jsp";

	private static final String SESSION_EXPIRED_JSP = "session_expired.jsp";

	/** The ecas login page URL * */
     private String ecasLoginPage = null;

     /** The filter config to read some parameters of the filter* */
     private FilterConfig filterConfig;

     /** The logger class* */
     private static final Log logger = LogFactory
                .getLog(EcasAuthenticationFilter.class);

     @Override
	public void doFilter(ServletContext context, ServletRequest req,
			ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
    	 
    	 final HttpServletRequest httpReq = (HttpServletRequest) req;
          final HttpServletResponse httpRes = (HttpServletResponse)res;
          final String requestURI = httpReq.getRequestURI();


          // allow session expire page
          if (StringUtils.contains(requestURI, SESSION_EXPIRED_JSP))
          {
        	  chain.doFilter(req, res);
              return ;
          }
          else
          {
        	  // only get session do not create
        	  final HttpSession session = httpReq.getSession(false);
	          if  (session == null)
	          {
	        	  final boolean isSessionInvalid = (httpReq.getRequestedSessionId() != null)&& !httpReq.isRequestedSessionIdValid();
	        	  if (isSessionInvalid)
	        	  {
	        		  httpRes.sendRedirect(httpReq.getContextPath() + FACES_JSP_EXTENSION_SESSION_EXPIRED_JSP);
	        		  return ;
	        	  }
	          }
          }

		if(logger.isInfoEnabled())
          {
        	  logger.info("filtering " + requestURI);
          }

          // allow the login page to proceed (and the ECAS Login page also) 777
          if (requestURI.endsWith("login.jsp") == false && requestURI.endsWith(getEcasLoginPage()) == false)
          {

              // The services called after needs to have a configured FacesContext. Ensure its construction.
          	  if(FacesContext.getCurrentInstance() == null)
              {
                  FacesHelper.getFacesContext(req, res, context);
              }

          	  final boolean forceGuest = AuthenticationHelper.getUser((ServletContext) context,httpReq, httpRes) == null;

             final AuthenticationStatus status =
                   AuthenticationHelper.authenticate(context, httpReq, httpRes, forceGuest);

             if (status == AuthenticationStatus.Success || status == AuthenticationStatus.Guest)
             {
                // continue filter chaining
                chain.doFilter(req, res);
             }
             else
             {
                // authentication failed - so end servlet execution and redirect to login page
                // also save the requested URL so the login page knows where to redirect too later
            	AuthenticationHelper.authenticate(context, httpReq, httpRes, true);

            	 final String authAsGuest = Application.getBundle(httpReq.getSession()).getString(MSG_AUTH_AS_GUEST);
		    	 ErrorsRenderer.addForcedMessage(new FacesMessage(authAsGuest));
             }
          } else
          {
                // continue filter chaining
                chain.doFilter(req, res);
          }
     }

     /**
       * @return The login ecas login page url defined in the parameters of the
       *         filter
       * @return the ecas login page URL
       */
     private String getEcasLoginPage()
     {
          if (this.ecasLoginPage == null)
          {
              this.ecasLoginPage = filterConfig.getInitParameter("ecasloginpage");
              logger.info("Reading parameter ecasloginpage:" + this.ecasLoginPage);
          }

          return this.ecasLoginPage;
     }

}
