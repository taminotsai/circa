<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>
<%@ page buffer="32kb" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false"%>

<circabc:panel id="contentMainFormDeleteNodeDetails" styleClass="contentMainForm">

	<f:verbatim><br /></f:verbatim>
	<h:outputText id="delete-node-confirmation" value="#{WaiDialogManager.bean.confirmMessage}" styleClass="mainSubTitle" />

</circabc:panel>


