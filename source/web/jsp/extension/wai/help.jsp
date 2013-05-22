<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a"%>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r"%>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/extension/treemenu.js" ></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/extension/definitionList.js" ></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/extension/help.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/extension/treeview.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/extension/d-commission.css" type="text/css">

<circabc:view>

	<%-- load a bundle of properties I18N strings here --%>
	<f:loadBundle basename="alfresco.messages.webclient" var="msg" />
	<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

	<%@ page isELIgnored="false"%>

  	<c:set var="currentTitle" value="${cmsg.title_welcome}" />

	<%@ include file="/jsp/extension/wai/parts/header.jsp" %>
    <%@ include file="/jsp/extension/wai/parts/banner.jsp" %>

			<h:form acceptcharset="UTF-8" id="FormPrincipal">

			    <%@ include file="/jsp/extension/wai/parts/left-menu.jsp" %>

				<div id="maincontent">
					<c:choose>
						<c:when test="${param['page'] != null}">
							<jsp:include page='<%="/html/help/en/" + request.getParameter("page")%>' />
						</c:when>
						<c:otherwise>
							<jsp:include page='<%="/html/help/en/help_toc.html"%>' />
						</c:otherwise>
					</c:choose>
				<%-- Content END --%>
				</div>
			</h:form>
	<%@ include file="/jsp/extension/wai/parts/footer.jsp" %>

	<script type="text/javascript">
		if(document.getElementById('treemenu') != null){
			ddtreemenu.createTree('treemenu', false, 0, getContextPath());
		}

		/** Calculates and returns the context path for the current page */
		function getContextPath()
		{
			if (_alfContextPath == null)
			{
				var path = window.location.pathname;
				var idx = path.indexOf("/", 1);
				if (idx != -1)
				{
					_alfContextPath = path.substring(0, idx);
				}
				else
				{
					_alfContextPath = "";
				}
			}

			return _alfContextPath;
		}

	</script>


</circabc:view>

