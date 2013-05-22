<%--+
    |     Copyright European Community 2012 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a"%>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r"%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/noscript.js" ></script>

<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />
	
<%@ page isELIgnored="false"%>

<circabc:panel id="contentMainFormManageExternalRepositories" styleClass="contentMainForm">
	
	<fieldset class="formArea">
		
		<circabc:panel id="manage-external-repositories-configured" styleClass="signup_rub_title">
			<h:outputText value="#{cmsg.manage_external_repositories_configured_repositories}" escape="false" />
		</circabc:panel>
		
		<br/>
		
		<circabc:richList id="manage-configured-repositories-list"
				viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt"
				pageSize="#{ManageExternalRepositoriesDialog.pageSize}"
				value="#{DialogManager.bean.configuredRepositories}" var="r" initialSortColumn="name">
			
			<%-- Primary column with repository name --%>
			<circabc:column id="manage-external-repositories-col-name" primary="true" >
				<f:facet name="header">
					<circabc:sortLink id="manage-external-repositories-link-name" label="#{cmsg.manage_external_repositories_repository_name}" value="name" mode="case-insensitive" styleClass="header" tooltipAscending="#{cmsg.generic_sort_asc}" tooltipDescending="#{cmsg.generic_sort_desc}"/>
				</f:facet>
				<f:facet name="small-icon">
					<h:graphicImage id="manage-external-repositories-col-image" url="#{r.icon}" />
				</f:facet>
				<h:outputText id="manage-external-repositories-val-name" value="#{r.name}" />
			</circabc:column>
			
			<circabc:column id="manage-external-repositories-col-date" >
				<f:facet name="header">
					<circabc:sortLink id="manage-external-repositories-link-date" label="#{cmsg.manage_external_repositories_repository_date}" value="date" mode="case-insensitive" styleClass="header" tooltipAscending="#{cmsg.generic_sort_asc}" tooltipDescending="#{cmsg.generic_sort_desc}"/>
				</f:facet>
				<h:outputText id="manage-external-repositories-val-date" value="#{r.date}" />
			</circabc:column>
			
			<circabc:column id="manage-external-repositories-col-actions" actions="true" >
				<f:facet name="header">
					<h:outputText id="manage-external-repositories-actions-link" value="#{cmsg.actions}" />
				</f:facet>
				<circabc:actionLink id="manage-external-repositories-col-actions-remove" value="#{cmsg.manage_external_repositories_remove_repository}" tooltip="#{cmsg.manage_external_repositories_remove_repository}" image="/images/extension/help/delete.gif" showLink="false" action="#{ManageExternalRepositoriesDialog.removeRepository}" actionListener="#{WaiDialogManager.setupParameters}">
					<circabc:param id="manage-external-repositories-col-actions-remove-par-id" name="id" value="#{DialogManager.bean.actionNode.id}" />
					<circabc:param id="manage-external-repositories-col-actions-remove-par-name" name="removeName" value="#{r.name}" />
					<circabc:param id="manage-external-repositories-col-actions-remove-par-service" name="service" value="Administration" />
					<circabc:param id="manage-external-repositories-col-actions-remove-par-activity" name="activity" value="#{cmsg.manage_external_repositories_remove_repository}" />
				</circabc:actionLink>
			</circabc:column>
			
			<f:facet name="empty">
				<h:outputFormat id="manage-external-repositories-list-container" value="#{cmsg.no_list_items}" escape="false" />
			</f:facet>
			
			<circabc:dataPager id="manage-external-repositories-pager" styleClass="pagerCirca" />
			
		</circabc:richList>
		
		<circabc:panel id="manage-external-repositories-avaulable" styleClass="signup_rub_title">
			<h:outputText value="#{cmsg.manage_external_repositories_available_repositories}" escape="false" />
		</circabc:panel>
		
		<br/>
		
		<h:outputText value="#{cmsg.manage_external_repositories_add_repository}:" />
		<c:if test="${!(empty ManageExternalRepositoriesDialog.availableRepositories)}">
			<h:selectOneMenu value="#{ManageExternalRepositoriesDialog.selectedAvailableRepository}" id="select_repository">
				<f:selectItems value="#{ManageExternalRepositoriesDialog.availableRepositories}"/>
			</h:selectOneMenu>
			<h:commandButton id="submit_add" value="#{cmsg.manage_external_repositories_add}" action="#{ManageExternalRepositoriesDialog.addRepository}" title="#{cmsg.manage_external_repositories_add}"></h:commandButton>
		</c:if>
		<c:if test="${empty ManageExternalRepositoriesDialog.availableRepositories}">
			<h:outputText value="#{cmsg.manage_external_repositories_all}" />
		</c:if>
		
	</fieldset>
	
	<br/>
	
</circabc:panel>