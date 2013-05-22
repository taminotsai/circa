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
<%@ page isELIgnored="false" %>

<circabc:panel id="contentMainFormDeleteUserProfile" styleClass="contentMainForm">

	<f:verbatim>
		<br />
	</f:verbatim>

	<h:outputText id="delete-user-profile-tabulation" value="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" escape="false"/>
	<h:outputText id="delete-user-profile-confirmation" value="#{WaiDialogManager.bean.confirmationMessage}" styleClass="mainSubTitle" escape="false"/>

	<f:verbatim>
		<br /><br />
	</f:verbatim>

</circabc:panel>