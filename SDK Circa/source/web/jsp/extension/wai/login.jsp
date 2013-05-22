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


<!-- real content START -->
	<table width="100%" border="0" align="center" cellspacing="0" cellpadding="0" id="content" summary="${cmsg.banner_content_page}">
	<tr>
		<td>
<h:form acceptcharset="UTF-8" id="FormPrincipal">

<%@ include file="/jsp/extension/wai/parts/left-menu.jsp" %>

<div id="maincontent">
<%-- Content START --%>
<div id="ContentHeader">
	<span class="ContentHeaderTitle"><h:outputText value="#{cmsg.login_title}" /></span><br />
	<span class="ContentHeaderSubTitle"><h:outputText value="#{cmsg.login_title_desc}" /></span>
</div>

<circabc:errors styleClass="messageGen" infoClass="messageInfo" warnClass="messageWarn" errorClass="messageError" />

<div id="ContentMain">
		<p><h:outputText value="#{cmsg.login_text_0}" /></p>
		<div id="LoginForm" class="loginForm">
			<div class="LoginFormSub">
					<label for="FormPrincipal:user-name" class="textLogin"><h:outputText value="#{cmsg.username}" /> :</label>
					<h:inputText id="user-name" value="#{LoginBean.username}"	validator="#{LoginBean.validateUsername}" styleClass="logon" />
					<br />
					<label for="FormPrincipal:user-password" class="textLogin"><h:outputText value="#{cmsg.password}" /> :</label>
					<h:inputSecret id="user-password" value="#{LoginBean.password}"	validator="#{LoginBean.validatePassword}" styleClass="logon" />
					<br />
			</div>
			<h:commandButton id="submit" action="#{LoginBean.loginCirca}" value="#{cmsg.login}" styleClass="logon2" /><img src="${currentContextPath}/images/extension/transparent.gif" alt="" class="logon3" />
		</div>
		<br />
		<p>
			<h:outputText value="#{cmsg.login_text_11}" escape="false" />
			<circabc:actionLink tooltip="#{cmsg.login_text_12_tooltip}" value="#{cmsg.login_text_12}" action="wai:dialog:resendOwnPasswordWai" immediate="true" >
				<circabc:param name="new" value="true" />
			</circabc:actionLink>
			<h:outputText value="#{cmsg.login_text_13}"/>
		</p>
		<p>
			<h:outputText value="#{cmsg.login_text_21}" escape="false" />
			<circabc:actionLink value="#{cmsg.self_sign_up}" action="wai:dialog:selfRegisterWai" actionListener="#{DialogManager.setupParameters}" tooltip="#{cmsg.self_sign_up_tooltip}" immediate="true">
				<circabc:param name="new" value="true" />
			</circabc:actionLink>
			<h:outputText value="#{cmsg.login_text_23}" />
		</p>


</div>
<%-- Content END --%>
</div>
</h:form>
<%@ include file="/jsp/extension/wai/parts/footer.jsp" %>
</circabc:view>
