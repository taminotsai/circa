<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>
<%@ page buffer="32kb" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false"%>

<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />
	
<circabc:panel id="contentMainFormEditNodeDetails" styleClass="contentMainForm">
	<circabc:propertySheetGrid id="${DialogManager.bean.idPrefix}" value="#{DialogManager.bean.editableNode}"
                        var="spaceProps" columns="1" labelStyleClass="propertiesLabel"
                        externalConfig="true" cellpadding="2" cellspacing="2" />
    <h:outputText value="<br/>" escape="false" />
    <h:outputText style="font-style:italic; margin:8px;" value="#{DialogManager.bean.translationNote}" escape="false" />
</circabc:panel>


