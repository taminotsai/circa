package eu.cec.digit.circabc.web.app.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.alfresco.web.app.Application;
import org.alfresco.web.app.servlet.AuthenticationHelper;
import org.alfresco.web.app.servlet.AuthenticationStatus;
import org.alfresco.web.app.servlet.BaseServlet;
import org.alfresco.web.bean.LoginOutcomeBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.cmr.security.CircabcConstant;
import eu.cec.digit.circabc.web.ui.common.renderer.ErrorsRenderer;

public class AuthenticationFilter extends
	org.alfresco.web.app.servlet.AuthenticationFilter {

	private static final Log logger = LogFactory
			.getLog(AuthenticationFilter.class);

	/** forcing guess access is available on most servlets */
	public static final String ARG_GUEST = "guest";

	/**
	 * an existing Ticket can be passed to most servlet for non-session based
	 * authentication
	 */
	public static final String ARG_TICKET = "ticket";

	public static final String MSG_AUTH_AS_GUEST = "accessing_as_guest";

	/** list of valid JSPs for redirect after a clean login */
	// TODO: make this list configurable
	private static final Set<String> circabcValidRedirectJSPs = new HashSet<String>();
	static {
		circabcValidRedirectJSPs.add("/jsp/extension/welcome.jsp");
	}

	private static final String FACES_JSP_EXTENSION_SESSION_EXPIRED_JSP = "/faces/jsp/extension/session_expired.jsp";

	private static final String SESSION_EXPIRED_JSP = "session_expired.jsp";

	@Override
	public void doFilter(ServletContext context, ServletRequest req,
			ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		
		if (logger.isInfoEnabled()) {
			logger.info("**** AuthenticationFilter Filter Called ****");
		}
		final HttpServletRequest httpReq = (HttpServletRequest) req;
		final HttpServletResponse httpRes = (HttpServletResponse) res;

		final String requestURI = httpReq.getRequestURI();
//		 allow session expire page
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



		httpReq.setCharacterEncoding("UTF-8");
		httpRes.setContentType("text/html; charset=UTF-8");

		// allow the login page to proceed
		if (httpReq.getRequestURI().endsWith(getLoginPage(context)) == false) {
			AuthenticationStatus status;

			// see if a ticket or a force Guest parameter has been supplied
			final String ticket = req.getParameter(ARG_TICKET);
			boolean wasNotGuest = false;

			if (ticket != null && ticket.length() != 0) {
				status = AuthenticationHelper.authenticate(context,
						httpReq, httpRes, ticket);
			} else {
				final Cookie cookie = AuthenticationHelper
						.getAuthCookie(httpReq);

				if (cookie != null && cookie.getValue() != null) {
					wasNotGuest = !cookie.getValue().equals(
							CircabcConstant.GUEST_AUTHORITY);
				}

				boolean forceGuest = false;
				final String guest = req
						.getParameter(AuthenticationFilter.ARG_GUEST);
				if (guest != null) {
					forceGuest = Boolean.parseBoolean(guest);
				}

				status = AuthenticationHelper.authenticate(context,
						httpReq, httpRes, forceGuest);
			}

			if (status == AuthenticationStatus.Failure) {
				// authentication failed - now need to display the login page to
				// the user, if asked to
				status = AuthenticationHelper.authenticate(context,
						httpReq, httpRes, true);

				if (wasNotGuest) {
					final String authAsGuest = Application.getBundle(
							httpReq.getSession()).getString(MSG_AUTH_AS_GUEST);
					ErrorsRenderer.addForcedMessage(new FacesMessage(
							authAsGuest));
				}
			}
		}

		// continue filter chaining
		chain.doFilter(req, res);
	}

	/**
	 * @return The login page url
	 */
	private String getLoginPage(ServletContext context) {
		if (this.loginPage == null) {
			this.loginPage = Application.getLoginPage(context);
		}

		return this.loginPage;
	}

	private String loginPage = null;

	/**
	 * Redirect to the Login page - saving the current URL which can be
	 * redirected back later once the user has successfully completed the
	 * authentication process.
	 */
	public static void redirectToLoginPage(final HttpServletRequest req,
			final HttpServletResponse res, final ServletContext sc)
			throws IOException {
		if (logger.isInfoEnabled()) {
			logger.info("**** redirectToLoginPage is Called ****");
		}
		// authentication failed - so end servlet execution and redirect to
		// login page
		res.sendRedirect(req.getContextPath() + BaseServlet.FACES_SERVLET
				+ Application.getLoginPage(sc));

		// save the full requested URL so the login page knows where to redirect
		// too later
		final String uri = req.getRequestURI();
		String url = uri;
		if (req.getQueryString() != null && req.getQueryString().length() != 0) {
			url += "?" + req.getQueryString();
		}
		if (uri.indexOf(req.getContextPath() + BaseServlet.FACES_SERVLET) != -1) {
			// if we find a JSF servlet reference in the URI then we need to
			// check if the rest of the
			// JSP specified is valid for a redirect operation after Login has
			// occured.
			int jspIndex = uri.indexOf(BaseServlet.FACES_SERVLET)
					+ BaseServlet.FACES_SERVLET.length();

			if (uri.length() > jspIndex
					&& (BaseServlet.validRedirectJSP(uri.substring(jspIndex)) || circabcValidRedirectJSPs
							.contains(uri.substring(jspIndex)))) {
				req.getSession()
						.setAttribute(LoginOutcomeBean.PARAM_REDIRECT_URL, url);
			}
		} else {
			req.getSession().setAttribute(LoginOutcomeBean.PARAM_REDIRECT_URL, url);
		}
	}
}
