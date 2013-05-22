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


<%@ page isELIgnored="false" %>

<circabc:panel id="contentMainFormEditOthersNotification" styleClass="contentMainForm">

	<f:verbatim>
		<br />
	</f:verbatim>

	<h:outputText id="edit-other-notif-confirmation" value="#{DialogManager.bean.confirmation}" styleClass="wai_dialog_more_action_left_desc" escape="false"/>

	<h:selectOneMenu id="edit-other-notif-value" value="#{DialogManager.bean.notificationStatus}" styleClass="wai_dialog_more_action" >
		<f:selectItems id="edit-other-notif-values" value="#{DialogManager.bean.statuses}" />
	</h:selectOneMenu>

</circabc:panel>