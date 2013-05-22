<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ page import="javax.faces.context.FacesContext" %>
<%@ page import="org.alfresco.web.app.servlet.FacesHelper" %>

<%@ page isELIgnored="false"%>

<%-- redirect to the web application's appropriate start page --%>
<%
	// ensure construction of the FacesContext before attemping a service call
	FacesContext fc = FacesHelper.getFacesContext(request, response, application);
	response.sendRedirect(request.getContextPath() + "/faces/jsp/extension/welcome.jsp");
%>
