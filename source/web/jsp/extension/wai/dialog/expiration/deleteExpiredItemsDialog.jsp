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

<%-- load a bundle of properties with I18N strings --%>
<f:loadBundle basename="alfresco.messages.webclient" var="msg" />
<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<circabc:panel id="contentMainForm" styleClass="contentMainForm">

	<circabc:panel id="panelMessage" styleClass="panelMessage">
		<h:outputText value="#{cmsg.delete_expired_items_dialog_warning}" />
	</circabc:panel>

	<h:dataTable value="#{WaiDialogManager.bean.confirmationItems}" var="item" 
		binding="#{WaiDialogManager.bean.confirmationTable}" styleClass="selectedItems">
		<h:column>
			<f:facet name="header">
				<h:outputText value="#{cmsg.name}"/>
			</f:facet>    
             <h:outputText value="#{item.name}"></h:outputText>
		</h:column>
		<h:column>
			<f:facet name="header" >
				<h:outputText value="#{cmsg.expiration_date}"/>
			</f:facet>
			<h:outputText value="#{item.expirationDate}"></h:outputText>
		</h:column>
		<h:column>
			<f:facet name="header" >
				<h:outputText value="#{cmsg.author}"/>
			</f:facet>    
			<h:outputText value="#{item.author}"></h:outputText>
		</h:column>
	</h:dataTable>

</circabc:panel>