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
<%@ page buffer="32kb" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false"%>

<circabc:panel id="contentMainForm" styleClass="contentMainFormWithBorder">
	<h:outputFormat value="<p>" escape="false" />
		<h:outputFormat value="#{cmsg.self_registration_congratulation_page_content_1}" escape="false">
			<circabc:param value="#{SelfRegistrationDialog.email}" />
		</h:outputFormat>

		<h:outputFormat value="<br /><br />" escape="false" />

		<h:outputFormat value="#{cmsg.self_registration_congratulation_page_content_2}" escape="false">
			<circabc:param value="#{SelfRegistrationDialog.expirationTime}" />
		</h:outputFormat>

		<h:outputFormat value="<br /><br />" escape="false" />
	<h:outputFormat value="</p>" escape="false" />
</circabc:panel>

