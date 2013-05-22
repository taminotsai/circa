<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<a href="<%=request.getContextPath()%>/faces/jsp/extension/welcome.jsp">Session expired.Please click here to go to circabc welcome page</a>
<%
	response.sendRedirect(request.getContextPath() + "/faces/jsp/extension/welcome.jsp");
%>