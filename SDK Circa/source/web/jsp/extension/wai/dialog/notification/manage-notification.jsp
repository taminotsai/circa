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

<circabc:panel id="contentMainFormManageNotification" styleClass="contentMainForm">

	<!--  Display a message that infor that only SUSCRIBED and USUSCRIBED will be displayed -->
	<circabc:panel id="manage-notif-panel-warning" styleClass="infoPanel" styleClassLabel="infoContent" >
		<h:graphicImage id="manage-notif-image-warning" value="/images/icons/warning.gif" title="#{cmsg.message_warn_tooltip}" alt="#{cmsg.message_warn_tooltip}"  />
		<h:outputText id="manage-notif-text-warning-spaces" value="&nbsp;&nbsp;" escape="false" />
		<h:outputText id="manage-notif-text-warning-for-ig" value="#{cmsg.notification_view_other_dialog_forig_info}" escape="false" rendered="#{DialogManager.bean.currentNodeInterestGroup == true}" />
		<h:outputText id="manage-notif-text-warning-not-ig" value="#{cmsg.notification_view_other_dialog_notig_info}" escape="false" rendered="#{DialogManager.bean.currentNodeInterestGroup == false}" />
	</circabc:panel>

	<f:verbatim>
		<br />
	</f:verbatim>

	<!--  Action define a new Notification status for any authority -->
	<circabc:panel id="manage-notif-define-authority-section" styleClass="wai_dialog_more_action">
		<h:graphicImage value="/images/icons/add_user.gif" alt="#{cmsg.notification_define_other_action_tooltip}" />
		<h:outputText id="manage-notif-define-new-space" value="&nbsp;" escape="false" />
		<circabc:actionLink id="manage-notif-act-define" value="#{cmsg.notification_define_other_action_title}" tooltip="#{cmsg.notification_define_other_action_tooltip}" action="wai:dialog:defineOthersNotificationWai" actionListener="#{WaiDialogManager.setupParameters}" >
			<circabc:param id="manage-notif-act-id" name="id" value="#{DialogManager.bean.currentNode.id}" />
			<circabc:param id="manage-notif-act-service" name="service" value="Administration" />
			<circabc:param id="manage-notif-act-activity" name="activity" value="Define notification status" />
		</circabc:actionLink>
	</circabc:panel>


	<f:verbatim>
		<br /><br />
	</f:verbatim>

	<!--  Display the Notification status -->
	<circabc:richList id="manage-notif-list" viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{DialogManager.bean.notifications}" var="n" initialSortColumn="type" pageSize="#{BrowseBean.listElementNumber}">

		<circabc:column id="manage-notif-list-type">
			<f:facet name="header">
				<circabc:sortLink id="manage-notif-list-type-sorter" label="#{cmsg.notification_view_other_dialog_col_type}" value="type" tooltipAscending="#{cmsg.notification_view_other_type_sort_asc}" tooltipDescending="#{cmsg.notification_view_other_type_sort_desc}"/>
			</f:facet>
			<h:outputText id="manage-notif-list-col-type" value="#{n.type}"/>
		</circabc:column>

		<circabc:column id="manage-notif-list-username">
			<f:facet name="header">
				<circabc:sortLink id="manage-notif-list-username-sorter" label="#{cmsg.notification_view_other_dialog_col_username}" value="username" tooltipAscending="#{cmsg.notification_view_other_name_sort_asc}" tooltipDescending="#{cmsg.notification_view_other_name_sort_desc}"/>
			</f:facet>
			<h:outputText id="manage-notif-list-col-username" value="#{n.username}"/>
		</circabc:column>

		<circabc:column id="manage-notif-list-status">
			<f:facet name="header">
				<circabc:sortLink id="manage-notif-list-status-sorter" label="#{cmsg.notification_view_other_dialog_col_status}" value="status" tooltipAscending="#{cmsg.notification_view_other_status_sort_asc}" tooltipDescending="#{cmsg.notification_view_other_status_sort_desc}"/>
			</f:facet>
			<h:outputText id="manage-notif-list-col-status" value="#{n.status}"/>
		</circabc:column>

		<circabc:column id="manage-notif-list-actions-col">
			<f:facet name="header">
				<h:outputText id="manage-notif-list-cont-act" value="#{cmsg.actions}" escape="false" />
			</f:facet>
			<circabc:actionLink image="/images/icons/edit_group.gif" id="manage-notif-act-modif-authority" tooltip="#{cmsg.notification_edit_other_action_tooltip}" value="#{cmsg.notification_edit_other_action_tooltip}" showLink="false" action="wai:dialog:editAuthorityNotificationDialogWai" actionListener="#{WaiDialogManager.setupParameters}" >
				<circabc:param id="param-notif-profile-authority" name="authority" value="#{n.authority}" />
				<circabc:param id="param-notif-profile-status" name="status" value="#{n.statusValueToString}" />
				<circabc:param id="param-notif-profile-node-id" name="id" value="#{n.nodeId}" />
				<circabc:param id="param-notif-profile-node-display" name="displayName" value="#{n.username}" />
				<circabc:param id="param-notif-profile-node-id-imageName" name="imageName" value="edit_notification_#{n.username}" />
				<circabc:param id="param-notif-profile-service" name="service" value="Administration" />
				<circabc:param id="param-notif-profile-activity" name="activity" value="Change notification status" />
			</circabc:actionLink>
		</circabc:column>

		<f:facet name="empty">
			<h:outputFormat id="manage-notif-list-container" value="#{cmsg.notification_view_other_no_list_items}" escape="false" />
		</f:facet>
		<circabc:dataPager id="manage-notifications-pager" styleClass="pagerCirca" />
	</circabc:richList>


</circabc:panel>