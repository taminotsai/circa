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

<%@ page buffer="32kb" contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="org.alfresco.web.ui.common.PanelGenerator"%>

<%-- load a bundle of properties with I18N strings --%>
<f:loadBundle basename="alfresco.messages.webclient" var="msg" />
<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />
<circabc:panel id="contentMainForm" styleClass="contentMainForm">
	<circabc:panel id="panelDeleteItems">
		<circabc:panel id="panelDeleteItemConfirm">
			<h:outputText value="#{cmsg.delete_items_confirm}">
			</h:outputText>
		</circabc:panel>
		<circabc:panel id="panelDeleteItemsList" styleClass="contentMainForm">
			<h:outputText value="#{WaiDialogManager.bean.listedItemsTable}"
				escape="false" />
		</circabc:panel>
	</circabc:panel>
</circabc:panel>
