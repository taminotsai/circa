<%@page import="eu.cec.digit.circabc.service.cmr.security.CircabcConstant"%>
<%@page import="org.apache.commons.logging.LogFactory"%>
<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>

<%@ page import="org.apache.commons.logging.LogFactory"%>
<%@ page import="eu.cec.digit.circabc.web.wai.bean.LoginBean"%>
<%@ page import="eu.cec.digit.circabc.web.Services"%>
<%@ page import="org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback"%>
<%@ page import="org.alfresco.repo.transaction.RetryingTransactionHelper"%>
<%@ page import="eu.cec.digit.circabc.web.app.CircabcNavigationHandler"%>
<%@ page import="org.alfresco.web.app.servlet.AuthenticationHelper"%>
<%@ page import="javax.servlet.http.Cookie"%>
<%@ page import="javax.faces.context.FacesContext" %>
<%@ page import="org.alfresco.web.app.servlet.FacesHelper" %>
<%@ page import="org.alfresco.service.cmr.security.AuthenticationService" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>

<%@ page import="org.alfresco.service.transaction.TransactionService" %>
<%@ page import="org.alfresco.service.cmr.security.PermissionService" %>
<%@ page import="org.alfresco.service.cmr.security.PersonService" %>
<%@ page import="org.alfresco.service.cmr.repository.NodeRef" %>
<%@ page import="org.alfresco.web.bean.repository.User" %>


<%@ page isELIgnored="false"%>

<%
	// ensure construction of the FacesContext before attemping a service call
	FacesContext fc = FacesHelper.getFacesContext(request, response, application);
	fc.getViewRoot().setViewId(CircabcNavigationHandler.WAI_NAVIGATION_CONTAINER_PAGE);
%>

<%
	final WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
	final HttpSession finalSession = session ;
	Cookie authCookie = AuthenticationHelper.getAuthCookie(request);
	// remove the cookie
	if (authCookie != null)
	{
    	authCookie.setMaxAge(0);
        response.addCookie(authCookie);
	}

	// expired ticket
        AuthenticationService unpAuth = (AuthenticationService)context.getBean("authenticationService");
        unpAuth.invalidateTicket(unpAuth.getCurrentTicket());
        unpAuth.clearCurrentSecurityContext();

        TransactionService transactionService = Services.getAlfrescoServiceRegistry(context.getServletContext()).getTransactionService();

	final RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
	final RetryingTransactionHelper.RetryingTransactionCallback<Object> callback = new RetryingTransactionCallback<Object>()
	{
		public Object execute() throws Throwable
		{
			AuthenticationService authService = (AuthenticationService)context.getBean("AuthenticationService");
			authService.authenticateAsGuest();

			PersonService personService = (PersonService)context.getBean("personService");
	        NodeRef guestRef = personService.getPerson(CircabcConstant.GUEST_AUTHORITY);

	        User user = new User(authService.getCurrentUserName(), authService.getCurrentTicket(), guestRef);
	        finalSession.setAttribute(AuthenticationHelper.AUTHENTICATION_USER, user);

	        finalSession.removeAttribute(AuthenticationHelper.SESSION_INVALIDATED);

			return null;
		}
	};
	try
	{
		txnHelper.doInTransaction(callback, false, true);

	}
	catch (Throwable e)
	{
		LogFactory.getLog(LoginBean.class).error("Error during a logout: ", e);
	}

%>

<%-- redirect to the web application's appropriate start page --%>
<%
	String redirectUrl = request.getContextPath() + "/faces/jsp/extension/welcome.jsp";

	session.invalidate();

	response.sendRedirect(redirectUrl);
%>