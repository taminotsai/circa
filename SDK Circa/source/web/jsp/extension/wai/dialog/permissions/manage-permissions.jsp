<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |     http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a" %>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r" %>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>


<%@ page isELIgnored="false" %>
<%@ page import="org.alfresco.web.ui.common.PanelGenerator" %>

<script language="javascript">
    function updateInheritance(){
        document.getElementById("FormPrincipal:submit-change-inherited").click();
    }
</script>

<circabc:panel id="contentMainFormManageNodePermission" styleClass="contentMainForm">

	<circabc:panel id="manage-permissions--info" styleClass="infoPanel" styleClassLabel="infoContent" rendered="#{DialogManager.bean.owner != null}">
		<h:graphicImage id="manage-permissions-image-info" value="/images/extension/icons/info.gif" title="#{cmsg.message_info_tooltip}" alt="#{cmsg.message_info_tooltip}"  />
		<h:outputText id="manage-permissions-text-permissions-spaces" value="&nbsp;&nbsp;" escape="false" />
		<h:outputFormat id="manage-permissions-text-owner" value="#{cmsg.manage_space_permission_page_owner_desc}" escape="false"  >
			<circabc:param value="#{DialogManager.bean.owner}" />
		</h:outputFormat>
	</circabc:panel>

	<r:permissionEvaluator value="#{DialogManager.bean.actionNode}" allow="ChangePermissions">
		<!--  Action add a new keyword -->
		<circabc:panel id="manage-permission-more-action-section" styleClass="wai_dialog_more_action">
			<h:graphicImage value="/images/icons/invite.gif" title="#{cmsg.manage_space_permission_page_action_grant_tooltip}" alt="#{cmsg.manage_space_permission_page_action_grant_tooltip}" />
			<h:outputText id="manage-permission-set-perm-space" value="&nbsp;" escape="false" />
			<circabc:actionLink id="manage-permission-act-set" value="#{cmsg.manage_space_permission_page_action_grant}" tooltip="#{cmsg.manage_space_permission_page_action_grant_tooltip}" action="wai:wizard:changeCircabcAccessRight" actionListener="#{WaiWizardManager.setupParameters}" >
				<circabc:param id="manage-permission-act-id" name="id" value="#{DialogManager.bean.actionNode.id}" />
			</circabc:actionLink>
		</circabc:panel>
	</r:permissionEvaluator>

	<f:verbatim>
		<br /><br />
	</f:verbatim>

	<circabc:panel id="manage-permission-main-section" styleClass="signup_rub_title">
		<h:outputText value="#{cmsg.manage_space_permission_page_list_title}" escape="false" />
	</circabc:panel>

	<f:verbatim>
		<br />
	</f:verbatim>

	<circabc:richList id="users-list-perm"
			viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt"
			value="#{DialogManager.bean.users}" var="r" initialSortColumn="userName">

		<%-- Primary column with full name --%>
		<circabc:column id="manage-perm-col-name" primary="true" >
			<f:facet name="header">
				<circabc:sortLink id="manage-perm-link-name" label="#{msg.name}" value="fullName" mode="case-insensitive" styleClass="header" tooltipAscending="#{cmsg.generic_sort_asc}" tooltipDescending="#{cmsg.generic_sort_desc}"/>
			</f:facet>
			<f:facet name="small-icon">
				<h:graphicImage id="manage-perm-col-image" url="#{r.icon}" />
			</f:facet>
			<h:outputText id="manage-perm-val-name" value="#{r.displayName}" />
		</circabc:column>

		<%-- Username column --%>
		<circabc:column id="manage-perm-col-authority" >
			<f:facet name="header">
				<circabc:sortLink id="manage-perm-sort-authority" label="#{msg.authority}" value="userName" styleClass="header" tooltipAscending="#{cmsg.generic_sort_asc}" tooltipDescending="#{cmsg.generic_sort_desc}"/>
			</f:facet>
			<h:outputText id="manage-perm-val-authority" value="#{r.userFullName}" />
		</circabc:column>

		<%-- Roles column --%>
		<circabc:column id="manage-perm-col-role">
			<f:facet name="header">
				<circabc:sortLink id="manage-perm-role-link" label="#{msg.roles}" value="permission" styleClass="header" tooltipAscending="#{cmsg.generic_sort_asc}" tooltipDescending="#{cmsg.generic_sort_desc}"/>
			</f:facet>
			<h:outputText id="manage-perm-role-value" value="#{r.permission}" />
		</circabc:column>

		<%-- Actions column --%>
		<circabc:column id="manage-perm-col-action" actions="true" >
			<f:facet name="header">
				<circabc:sortLink id="manage-perm-actions-title" label="#{msg.actions}" value="#{msg.actions}" tooltipAscending="#{cmsg.generic_sort_asc}" tooltipDescending="#{cmsg.generic_sort_desc}"/>
			</f:facet>
			<circabc:actionLink id="manage-perm-col-actions-edit" value="#{msg.change_roles}" tooltip="#{msg.change_roles}" image="/images/icons/edituser.gif" showLink="false" action="wai:dialog:editPermissionWai" actionListener="#{WaiDialogManager.setupParameters}">
				<circabc:param id="param-change-role-id-lib"   name="id" value="#{WaiDialogManager.bean.actionNode.id}"/>
				<circabc:param id="param-change-role-username" name="authority" value="#{r.authority}" />
				<circabc:param id="param-change-role-authtype" name="authType" value="#{r.authType}" />
				<circabc:param id="param-change-role-fullname" name="displayName" value="#{r.displayName}" />
				<circabc:param id="param-change-role-permission" name="permission" value="#{r.permission}" />
		        <circabc:param id="param-change-role-imageName" name="imageName" value="change_role_#{r.displayName}" />
			</circabc:actionLink>
			<circabc:actionLink noDisplay="#{r.inheritedPermission}" id="manage-perm-col-actions-remove" value="#{msg.remove}" tooltip="#{msg.remove}" image="/images/icons/delete_person.gif" showLink="false" action="wai:dialog:removePermissionWai" actionListener="#{WaiDialogManager.setupParameters}">
				<circabc:param id="param-remove-id-lib"   name="id" value="#{WaiDialogManager.bean.actionNode.id}"/>
				<circabc:param id="param-remove-username" name="authority" value="#{r.authority}" />
				<circabc:param id="param-remove-authtype" name="authType" value="#{r.authType}" />
				<circabc:param id="param-remove-fullname" name="displayName" value="#{r.displayName}" />
		        <circabc:param id="param-remove-imageName" name="imageName" value="remove_role_#{r.displayName}" />
			</circabc:actionLink>
		</circabc:column>

		<f:facet name="empty">
			<h:outputFormat id="manage-permissions-list-container" value="#{cmsg.no_list_items}" escape="false" />
		</f:facet>
	</circabc:richList>

	<f:verbatim>
		<br />
	</f:verbatim>

	<h:selectBooleanCheckbox id="chkPermissions" value="#{DialogManager.bean.inheritPermissions}"
					onchange="updateInheritance()"
					disabled="#{!DialogManager.bean.hasChangePermissions}" />
	<h:outputText value="#{msg.inherit_permissions}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" escape="false" />

	<h:commandButton id="submit-change-inherited" styleClass="" value="#{cmsg.manage_permission_dialog_apply_inherit}" action="wai:dialog:close:wai:dialog:managePermissionsWai" actionListener="#{DialogManager.bean.inheritPermissionsValueChanged}" rendered="true" title="#{cmsg.manage_permission_dialog_apply_inherit_tooltip}" />

</circabc:panel>
