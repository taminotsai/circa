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
<%--circabc:displayer rendered="#{LoginBean.init == true}"/ --%>
<circabc:view>
	<%-- load a bundle of properties I18N strings here --%>
	<f:loadBundle basename="alfresco.messages.webclient" var="msg" />
	<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

	<%@ page import="org.alfresco.web.app.servlet.AuthenticationHelper"%>
	<%@ page import="javax.servlet.http.Cookie"%>

	<%@ page isELIgnored="false"%>

 	<c:set var="currentTitle" value="${cmsg.title_login}" />

	<%@ include file="/jsp/extension/wai/parts/header.jsp" %>
    <%@ include file="/jsp/extension/wai/parts/banner.jsp" %>

<h:form acceptcharset="UTF-8" id="FormPrincipal">

<%@ include file="/jsp/extension/wai/parts/left-menu.jsp" %>

<div id="maincontent">
<%-- Content START --%>
<div id="ContentHeader">
	<span class="ContentHeaderTitle"><h:outputText value="#{cmsg.activate_account_page_title}" /></span><br />
	<span class="ContentHeaderSubTitle"><h:outputText value="#{cmsg.activate_account_page_subtitle}" /></span>
</div>

<circabc:displayer rendered="#{LoginBean.initActivation == true}"/>
<circabc:errors styleClass="messageGen" infoClass="messageInfo" warnClass="messageWarn" errorClass="messageError" />

<div id="ContentMain">

		<p><h:outputText value="#{cmsg.self_registration_welcome}" escape="false"/></p>
		<div id="LoginForm" class="loginForm">
			<div class="LoginFormSub">
					<label for="FormPrincipal:user-name" class="textLogin"><h:outputText value="#{cmsg.username}" /> :</label>
					<h:inputText id="user-name" value="#{LoginBean.username}"	validator="#{LoginBean.validateUsername}" styleClass="logon" disabled="#{LoginBean.badParameters}"/>
					<br />
					<label for="FormPrincipal:user-password" class="textLogin"><h:outputText value="#{cmsg.password}" /> :</label>
					<h:inputSecret id="user-password" value="#{LoginBean.password}"	validator="#{LoginBean.validatePassword}" styleClass="logon" disabled="#{LoginBean.badParameters}"/>
					<br />
			</div>
			<h:commandButton id="submit2" action="#{LoginBean.activateAndLoginCirca}" value="#{cmsg.login}" styleClass="logon2" disabled="#{LoginBean.badParameters}"/>
		</div>
		<br />
		<p>
			<h:outputText value="#{cmsg.self_registration_howto_activate}" escape="false"/>
		</p>

<script type="text/javascript" language="JavaScript">
if (document.getElementById("FormPrincipal:user-name").value.length == 0) {
	document.getElementById("FormPrincipal:user-name").focus();
} else {
	document.getElementById("FormPrincipal:user-password").focus();
}
</script>
</div>
<%-- Content END --%>
</div>
</h:form>
	<%@ include file="/jsp/extension/wai/parts/footer.jsp" %>
</circabc:view>
