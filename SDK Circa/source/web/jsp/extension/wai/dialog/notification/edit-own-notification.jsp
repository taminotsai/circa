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

<f:loadBundle basename="alfresco.messages.webclient" var="msg" />
<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />


<h:panelGrid columns="2" cellpadding="3" cellspacing="3" border="0" rendered="#{NotificationStatusPanel.profile == null}">
    <h:outputText id="edit-own-notification-global" value="#{cmsg.notification_panel_global_status}:" styleClass="propertiesLabelTiny" />
	<h:selectOneMenu id="edit-own-notification-global-value" value="#{DialogManager.bean.globalNotificationStatus}" styleClass="wai_dialog_more_action_fixed" >
		<f:selectItems id="edit-own-notification-global-values" value="#{DialogManager.bean.globalStatuses}" />
	</h:selectOneMenu>

    <h:outputFormat id="edit-own-notification-user" value="#{cmsg.notification_panel_user_status}:" styleClass="propertiesLabelTiny">
		<circabc:param value="#{NotificationStatusPanel.userDisplayName}" />
    </h:outputFormat>
	<h:selectOneMenu id="edit-own-notification-user-value" value="#{DialogManager.bean.notificationStatus}" styleClass="wai_dialog_more_action_fixed" >
		<f:selectItems id="edit-other-notif-values" value="#{DialogManager.bean.statuses}" />
	</h:selectOneMenu>
</h:panelGrid>

<h:panelGrid columns="2" cellpadding="3" cellspacing="3" border="0" rendered="#{NotificationStatusPanel.profile != null}">
    <h:outputText id="edit-own-notification-global-2" value="#{cmsg.notification_panel_global_status}:" styleClass="propertiesLabelTiny" />
	<h:selectOneMenu id="edit-own-notification-global-value-2" value="#{DialogManager.bean.globalNotificationStatus}" styleClass="wai_dialog_more_action_fixed" >
		<f:selectItems id="edit-own-notification-global-values-2" value="#{DialogManager.bean.globalStatuses}" />
	</h:selectOneMenu>

    <h:outputFormat id="edit-own-notification-user-2" value="#{cmsg.notification_panel_user_status}:" styleClass="propertiesLabelTiny">
		<circabc:param value="#{NotificationStatusPanel.userDisplayName}" />
    </h:outputFormat>
	<h:selectOneMenu id="edit-own-notification-user-value-2" value="#{DialogManager.bean.notificationStatus}" styleClass="wai_dialog_more_action_fixed" >
		<f:selectItems id="edit-other-notif-values-2" value="#{DialogManager.bean.statuses}" />
	</h:selectOneMenu>

	<h:outputFormat id="edit-own-notification-profile-2" value="#{cmsg.notification_panel_profile_status}:" styleClass="propertiesLabelTiny">
		<circabc:param value="#{NotificationStatusPanel.profile}" />
    </h:outputFormat>
 		<h:outputText id="edit-own-notification-profile-value-2" value="#{NotificationStatusPanel.profileNotificationStatus}" />
</h:panelGrid>

<f:verbatim><br /></f:verbatim>

<circabc:panel id="edit-own-notification-warning" styleClass="infoPanel" styleClassLabel="infoContent" >
	<h:graphicImage id="edit-own-notification-image-warning" value="/images/icons/warning.gif" title="#{cmsg.message_warn_tooltip}" alt="#{cmsg.message_warn_tooltip}"  />
	<h:outputText id="edit-own-notification-text-warning-spaces" value="&nbsp;&nbsp;" escape="false" />
	<h:outputText id="edit-own-notification-text-warning-result" value="#{NotificationStatusPanel.notificationResult}" escape="false" />
</circabc:panel>

<f:verbatim><br /></f:verbatim>


