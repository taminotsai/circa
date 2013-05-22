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

<%@ page isELIgnored="false"%>



<circabc:panel id="contentMainFormAdvancedSearch"
	styleClass="contentMainForm">

	<circabc:richList value="#{ViewUserSavedSearchDialog.savedSearches}"
		viewMode="circa" styleClass="recordSet"
		headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow"
		altRowStyleClass="recordSetRowAlt" initialSortDescending="false"
		initialSortColumn="label" var="u">
		<circabc:column id="label-column">
			<f:facet name="header">
				<h:outputText id="label-header" value="#{msg.name}"
					styleClass="header" />
			</f:facet>
			<h:outputText id="label-text" value="#{u.label}" />
		</circabc:column>
		<circabc:column id="description-column">
			<f:facet name="header">
				<h:outputText id="description-header" value="#{msg.description}"
					styleClass="header" />
			</f:facet>
			<h:outputText id="description-text" value="#{u.description}" />
		</circabc:column>
		<circabc:column id="delete-search-action-column">
			<circabc:actionLink id="delete-search-action-remove"
				value="delete search" tooltip="delete search"
				image="/images/icons/delete.gif" showLink="false"
				action="wai:dialog:deleteSearchWai"
				actionListener="#{WaiDialogManager.setupParameters}">
				<circabc:param id="delete-search-action-edit-par-id" name="id"
					value="#{u.value}" />
				<circabc:param id="delete-search-action-edit-par-service"
					name="service" value="Administration" />
				<circabc:param id="delete-search-action-edit-par-activity"
					name="activity" value="Delete search" />
			</circabc:actionLink>
		</circabc:column>

		<f:facet name="empty">
			<h:outputFormat id="no-items-category-results"
				value="no saved search" />
		</f:facet>
	</circabc:richList>
</circabc:panel>




