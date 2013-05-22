<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>

<%@ page isELIgnored="false"%>

<circabc:panel id="contentMainFormUndoCheckOut" styleClass="contentMainForm">
	<circabc:panel id="undo-checkout-confirmation" styleClass="panelUndoCheckOutYellow">
		<h:graphicImage url="/images/icons/info_icon.gif" width="16" height="16"/>
		<h:outputText value="#{msg.undo_checkout_info}" />
	</circabc:panel>
</circabc:panel>
