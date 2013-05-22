<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ page import="org.apache.commons.logging.Log"%>
<%@ page import="org.apache.commons.logging.LogFactory"%>
<%@ page import="javax.faces.context.FacesContext" %>
<%@ page import="org.alfresco.web.app.servlet.FacesHelper" %>
<%@ page import="org.alfresco.service.cmr.repository.NodeRef" %>
<%@ page import="eu.cec.digit.circabc.web.Services"%>
<%@ page import="org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback"%>
<%@ page import="org.alfresco.repo.transaction.RetryingTransactionHelper"%>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.alfresco.service.transaction.TransactionService" %>
<%@ page import="org.alfresco.service.cmr.security.PermissionService" %>
<%@ page import="org.alfresco.service.cmr.security.AuthenticationService" %>
<%@ page import="org.alfresco.service.cmr.security.PersonService" %>
<%@ page import="org.alfresco.web.app.servlet.AuthenticationHelper" %>
<%@ page import="org.alfresco.web.bean.repository.User" %>
<%@ page import="eu.cec.digit.circabc.web.bean.override.CircabcBrowseBean"%>
<%@ page import="eu.cec.digit.circabc.web.wai.bean.navigation.WelcomeBean"%>

<%@ page isELIgnored="false"%>

<%-- redirect to the web application's appropriate start page --%>
<%
	final WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());

	// ensure construction of the FacesContext before attemping a service call
	final FacesContext fc = FacesHelper.getFacesContext(request, response, application);
	final HttpSession finalSession = session ;

	TransactionService transactionService = Services.getAlfrescoServiceRegistry(context.getServletContext()).getTransactionService();
	final RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();

	final RetryingTransactionHelper.RetryingTransactionCallback<Object> callback1 = new RetryingTransactionCallback<Object>()
	{
		public Object execute() throws Throwable
		{

			User user = (User)finalSession.getAttribute(AuthenticationHelper.AUTHENTICATION_USER);
			if (user == null || finalSession.isNew())
			{
				AuthenticationService authService = (AuthenticationService)context.getBean("AuthenticationService");
			    authService.authenticateAsGuest();
		   		PersonService personService = (PersonService)context.getBean("personService");
		   		NodeRef guestRef = personService.getPerson(PermissionService.GUEST_AUTHORITY);
		   		user = new User(authService.getCurrentUserName(), authService.getCurrentTicket(), guestRef);
		   		finalSession.setAttribute(AuthenticationHelper.AUTHENTICATION_USER, user);

			}
			return null;
		}
	};
	try
	{
		txnHelper.doInTransaction(callback1, false, true);

	}
	catch (Throwable e)
	{
		final Log logger = LogFactory.getLog(WelcomeBean.class);
		if(logger.isErrorEnabled())
			logger.error("Error during the display of the welcome page: ", e);
	}

	final CircabcBrowseBean browseBean = (CircabcBrowseBean) FacesHelper.getManagedBean(fc, CircabcBrowseBean.BEAN_NAME);

	final RetryingTransactionHelper.RetryingTransactionCallback<Object> callback2 = new RetryingTransactionCallback<Object>()
	{
		public Object execute() throws Throwable
		{
			try
			{
				browseBean.clickCircabcHome(null);
			}
			catch(net.sf.acegisecurity.AuthenticationCredentialsNotFoundException ex)
			{
				User user = (User)finalSession.getAttribute(AuthenticationHelper.AUTHENTICATION_USER);
				// if the a secure context is not found, create an new one with the Guest Authority...
				AuthenticationService authService = (AuthenticationService)context.getBean("AuthenticationService");
			    authService.authenticateAsGuest();
		   		PersonService personService = (PersonService)context.getBean("personService");
		   		NodeRef guestRef = personService.getPerson(PermissionService.GUEST_AUTHORITY);
		   		user = new User(authService.getCurrentUserName(), authService.getCurrentTicket(), guestRef);
		   		finalSession.setAttribute(AuthenticationHelper.AUTHENTICATION_USER, user);
			}
			return null;
		}
	};
	try
	{
		txnHelper.doInTransaction(callback2, false);

	}
	catch (Throwable e)
	{
		LogFactory.getLog(WelcomeBean.class).error("Error during the display of the welcome page: ", e);
	}

	response.sendRedirect(request.getContextPath() + "/faces/jsp/extension/wai/navigation/container.jsp");
%>

