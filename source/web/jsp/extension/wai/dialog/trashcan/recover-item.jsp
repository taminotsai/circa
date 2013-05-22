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
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/noscript.js" ></script>

<%@ page buffer="32kb" contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false"%>

<%-- load a bundle of properties with I18N strings --%>
<f:loadBundle basename="alfresco.messages.webclient" var="msg" />
<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<circabc:panel id="contentMainForm" styleClass="contentMainForm">
	<circabc:panel id="panelRecoverItem">
		<circabc:panel id="panelRecoverItemConfirm">
			<h:outputFormat value="#{msg.recover_item_confirm}">
				<f:param value="#{CircabcTrashcanDialogProperty.item.name}" />
			</h:outputFormat>
		</circabc:panel>
		<circabc:panel id="panelRecoverItemMessage" styleClass="infoPanel" styleClassLabel="infoContent">
			<h:outputText value="#{cmsg.recover_item_message}">
			</h:outputText>
		</circabc:panel>
		<circabc:panel  id="panelRecoverItemSpaceSelector">
			<h:outputText value="#{msg.destination}" /><f:verbatim>:&nbsp; </f:verbatim>
			<circabc:nodeSelector  id="space-selector"
				rootNode="#{CircabcTrashcanDialog.currentSpaceNode}"
				initialSelection="#{CircabcTrashcanDialog.currentSpaceNode}"
				label="#{msg.select_destination_prompt}"
				value="#{CircabcTrashcanDialogProperty.destination}"
				pathLabel="#{cmsg.path_label}"
				pathErrorMessage="#{cmsg.path_error_message}"
				styleClass="selector" />
		</circabc:panel>
	</circabc:panel>
</circabc:panel>




