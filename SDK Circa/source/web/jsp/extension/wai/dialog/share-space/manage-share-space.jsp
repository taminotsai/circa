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

<circabc:panel id="contentMainFormManageShareSpace" styleClass="contentMainForm">

	<!--  Action to invite new ig to share space -->
	<circabc:panel id="manage-share-space-add-ig-section" styleClass="wai_dialog_more_action" >
		<h:graphicImage value="/images/extension/icons/new_rule_small.gif" alt="#{cmsg.add_new_ig_dialog_action_tooltip}" />
		<h:outputText id="manage-share-space-add-ig" value="&nbsp;" escape="false" />
		<circabc:actionLink id="manage-share-space-act-add" tooltip="#{cmsg.add_new_ig_dialog_action_tooltip}" value="#{cmsg.add_new_ig_dialog_action_title}" showLink="false" action="wai:wizard:sharingSpace" actionListener="#{WaiWizardManager.setupParameters}" >
			<circabc:param id="id" name="id" value="#{DialogManager.bean.actionNode.nodeRef.id}" />
			<circabc:param id="manage-share-space-service" name="service" value="Library" />
			<circabc:param id="manage-share-space-activity" name="activity" value="Add shared space" />
		</circabc:actionLink>
	</circabc:panel>

	<f:verbatim>
		<br /><br />
	</f:verbatim>

	<!--  Display the dynamic properties -->
	<circabc:richList id="manage-share-space-list" viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{DialogManager.bean.invitedInterestGroups}" var="ig" initialSortColumn="name" pageSize="#{BrowseBean.listElementNumber}">
		<circabc:column id="manage-share-space-list-name">
			<f:facet name="header">
				<h:outputText id="manage-share-space-list-header-name" value="#{cmsg.interest_group_name}" escape="false" />
			</f:facet>
			<h:outputText id="manage-share-space-list-col-name" value="#{ig.igTitle}"/>
		</circabc:column>
		<circabc:column id="manage-share-space-list-type">
			<f:facet name="header">
				<h:outputText id="manage-share-space-list-header-type" value="#{cmsg.interest_group_permission}" escape="false" />
			</f:facet>
			<h:outputText id="manage-share-space-list-col-type" value="#{ig.permissionTitle}"/>
		</circabc:column>
		<circabc:column id="manage-share-space-list-actions-col">
			<f:facet name="header">
				<h:outputText id="manage-share-space-list-container-action" value="#{cmsg.actions}" escape="false" />
			</f:facet>
			<circabc:actionLink image="/images/icons/delete.gif" id="manage-share-space-delete-ig" tooltip="#{cmsg.delete_share_space_ig_action_tooltip}" value="#{cmsg.delete_share_space_ig_action_tooltip}" showLink="false" action="wai:dialog:removeIGShareSpaceDialog" actionListener="#{WaiDialogManager.setupParameters}" >
				<circabc:param id="param-delete-ig-id" name="interestGroupID" value="#{ig.id}" />
				<circabc:param id="param-delete-share-space-id" name="shareSpaceID" value="#{DialogManager.bean.actionNode.nodeRef}" />
				<circabc:param id="param-delete-ig-name" name="interestGroupName" value="#{ig.igTitle}" />
				<circabc:param id="param-delete-ig-imageName" name="imageName" value="remove_#{ig.name}" />
				<circabc:param id="param-delete-share-space-service" name="service" value="Library" />
				<circabc:param id="param-delete-share-space-activity" name="activity" value="Delete Shared Space" />
			</circabc:actionLink>
		</circabc:column>
		<f:facet name="empty">
			<h:outputFormat id="manage-interest-group-list-container" value="#{cmsg.manage_share_space_dialog_no_list_items}" escape="false" />
		</f:facet>
		<circabc:dataPager id="manage-share-space-pager" styleClass="pagerCirca" />
	</circabc:richList>
</circabc:panel>