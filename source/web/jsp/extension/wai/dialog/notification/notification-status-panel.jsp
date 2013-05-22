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

<circabc:displayer id="notification-status-panel-displayer" rendered="#{NotificationStatusPanel.panelDisplayed}">

     <circabc:panel id="notification-status-panel-properties" label="#{cmsg.notification_panel_title}" styleClass="panelDocumentDetailsGlobal" styleClassLabel="panelDocumentDetailsLabel" tooltip="#{cmsg.notification_panel_tooltip}">

     	<h:panelGrid columns="2" cellpadding="3" cellspacing="3" border="0"  rendered="#{NotificationStatusPanel.profile == null}"  >
       	<h:outputText id="notification-status-panel-global" value="#{cmsg.notification_panel_global_status}:" styleClass="propertiesLabelTiny"/>
       	<h:outputText id="notification-status-panel-global-value" value="#{NotificationStatusPanel.globalNotificationStatus}" />

       	<h:outputFormat id="notification-status-panel-user" value="#{cmsg.notification_panel_user_status}:" styleClass="propertiesLabelTiny">
			<circabc:param value="#{NotificationStatusPanel.userDisplayName}" />
       	</h:outputFormat>
       	<h:outputText id="notification-status-panel-user-value" value="#{NotificationStatusPanel.userNotificationStatus}" />
	</h:panelGrid>

	<h:panelGrid columns="2" cellpadding="3" cellspacing="3" border="0" rendered="#{NotificationStatusPanel.profile != null}">
       	<h:outputText id="notification-status-panel-global-2" value="#{cmsg.notification_panel_global_status}:" styleClass="propertiesLabelTiny"/>
       	<h:outputText id="notification-status-panel-global-value-2" value="#{NotificationStatusPanel.globalNotificationStatus}" />

       	<h:outputFormat id="notification-status-panel-user-2" value="#{cmsg.notification_panel_user_status}:" styleClass="propertiesLabelTiny">
			<circabc:param value="#{NotificationStatusPanel.userDisplayName}" />
       	</h:outputFormat>
       	<h:outputText id="notification-status-panel-user-value-2" value="#{NotificationStatusPanel.userNotificationStatus}" />

		<h:outputFormat id="notification-status-panel-profile" value="#{cmsg.notification_panel_profile_status}:" styleClass="propertiesLabelTiny">
			<circabc:param value="#{NotificationStatusPanel.profile}" />
		</h:outputFormat>
    	 <h:outputText id="notification-status-panel-profile-value" value="#{NotificationStatusPanel.profileNotificationStatus}" />
	</h:panelGrid>

	<f:verbatim><br /></f:verbatim>

	<circabc:panel id="notification-status-panel-warning" styleClass="infoPanel" styleClassLabel="infoContent" >
		<h:graphicImage id="notification-status-panel-image-warning" value="/images/icons/warning.gif" title="#{cmsg.message_warn_tooltip}" alt="#{cmsg.message_warn_tooltip}"  />
		<h:outputText id="notification-status-panel-text-warning-spaces" value="&nbsp;&nbsp;" escape="false" />
		<h:outputText id="notification-status-panel-text-warning-result" value="#{NotificationStatusPanel.notificationResult}" escape="false" />
	</circabc:panel>

      	<f:verbatim><br /></f:verbatim>
     </circabc:panel>

     <circabc:panel id="topOfPageAnchonotification-status-panel" styleClass="topOfPageAnchor"  >
         <%-- Display the "back to top icon first and display the text after." --%>
         <circabc:actionLink id="notification-status-panel-anchor-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
         <circabc:actionLink id="notification-status-panel-anchor-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
     </circabc:panel>

</circabc:displayer>

