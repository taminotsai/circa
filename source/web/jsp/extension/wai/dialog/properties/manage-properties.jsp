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

<circabc:panel id="contentMainFormManageProperties" styleClass="contentMainForm">

	<!--  Action add a new dynamic property -->
	<circabc:panel id="manage-properties-add-property-section" styleClass="wai_dialog_more_action" rendered="#{DialogManager.bean.addNewAvailable == true}">
		<h:graphicImage value="/images/icons/add_category.gif" alt="#{cmsg.add_new_property_dialog_action_tooltip}" />
		<h:outputText id="manage-properties-add-property-spaces" value="&nbsp;" escape="false" />
		<circabc:actionLink id="manage-properties-act-add" tooltip="#{cmsg.add_new_property_dialog_action_tooltip}" value="#{cmsg.add_new_property_dialog_action_title}" showLink="false" action="wai:wizard:defineNewPropertyWizard" actionListener="#{WaiWizardManager.setupParameters}" >
			<circabc:param id="id" name="id" value="#{DialogManager.bean.interestGroup.id}" />
			<circabc:param id="manage-properties-add-property-service" name="service" value="Administration" />
			<circabc:param id="manage-properties-add-property-activity" name="activity" value="Define new property" />
		</circabc:actionLink>
	</circabc:panel>

	<circabc:panel id="manage-properties--warning" styleClass="infoPanel" styleClassLabel="infoContent" rendered="#{DialogManager.bean.addNewAvailable == false}" >
		<h:graphicImage id="manage-properties-image-warning" value="/images/icons/warning.gif" title="#{cmsg.message_warn_tooltip}" alt="#{cmsg.message_warn_tooltip}"  />
		<h:outputText id="manage-properties-text-warning-spaces" value="&nbsp;&nbsp;" escape="false" />
		<h:outputFormat id="manage-properties-text-no-more" value="#{cmsg.manage_properties_dialog_no_more_poperty}" escape="false"  >
			<circabc:param value="#{DialogManager.bean.maxProperty}" />
		</h:outputFormat>
	</circabc:panel>


	<f:verbatim>
		<br /><br />
	</f:verbatim>

	<!--  Display the dynamic properties -->
	<!--  TODO read value from webclient.properties -->
	<circabc:richList id="manage-properties-list" viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{DialogManager.bean.properties}" var="dp" initialSortColumn="index" pageSize="#{BrowseBean.listElementNumber}">
		<circabc:column id="manage-properties-list-name">
			<f:facet name="header">
				<h:outputText id="manage-properties-list-header-name" value="#{cmsg.dynamic_property_name}" escape="false" />
			</f:facet>
			<h:outputText id="manage-properties-list-col-name" value="#{dp.name}"/>
		</circabc:column>
		<circabc:column id="manage-properties-list-type">
			<f:facet name="header">
				<h:outputText id="manage-properties-list-header-type" value="#{cmsg.dynamic_property_type}" escape="false" />
			</f:facet>
			<h:outputText id="manage-properties-list-col-type" value="#{dp.type}"/>
		</circabc:column>
		<circabc:column id="manage-properties-list-languages">
			<f:facet name="header">
				<h:outputText id="manage-properties-list-header-languages" value="#{cmsg.dynamic_property_languages}" escape="false" />
			</f:facet>
			<h:outputText id="manage-properties-list-col-languages" value="#{dp.languages}"/>
		</circabc:column>
		<circabc:column id="manage-properties-list-valid-values">
			<f:facet name="header">
				<h:outputText id="manage-properties-list-header-valid-values" value="#{cmsg.dynamic_property_validvalues}" escape="false" />
			</f:facet>
			<h:outputText id="manage-properties-list-col-valid-values" value="#{dp.displayValidValues}"/>
		</circabc:column>
		<circabc:column id="manage-properties-list-actions-col">
			<f:facet name="header">
				<h:outputText id="manage-properties-list-cont-act" value="#{cmsg.actions}" escape="false" />
			</f:facet>
			<circabc:actionLink image="/images/icons/edit_category.gif" id="manage-properties-act-modify-key" tooltip="#{cmsg.edit_property_dialog_action_tooltip}" value="#{cmsg.edit_property_dialog_action_tooltip}" showLink="false" action="wai:dialog:editPropertyDialogWai" actionListener="#{WaiDialogManager.setupParameters}" >
				<circabc:param id="param-modify-property-ig-id" name="id" value="#{DialogManager.bean.interestGroup.id}" />
				<circabc:param id="param-modify-property" name="property" value="#{dp.id}" />
				<circabc:param id="param-modify-property-imageName" name="imageName" value="edit_#{dp.name}" />
				<circabc:param id="param-modify-property-service" name="service" value="Administration" />
				<circabc:param id="param-modify-property-activity" name="activity" value="Edit property" />
			</circabc:actionLink>
			<circabc:actionLink image="/images/icons/delete_category.gif" id="manage-properties-act-delete-key" tooltip="#{cmsg.delete_property_dialog_action_tooltip}" value="#{cmsg.delete_property_dialog_action_tooltip}" showLink="false" action="wai:dialog:deletePropertyDialogWai" actionListener="#{WaiDialogManager.setupParameters}" >
				<circabc:param id="param-delete-property-ig-id" name="id" value="#{DialogManager.bean.interestGroup.id}" />
				<circabc:param id="param-delete-property" name="property" value="#{dp.id}" />
				<circabc:param id="param-delete-property-imageName" name="imageName" value="remove_#{dp.name}" />
				<circabc:param id="param-delete-property-service" name="service" value="Administration" />
				<circabc:param id="param-delete-property-activity" name="activity" value="Delete property" />
			</circabc:actionLink>
			<circabc:actionLink image="/faces/images/extension/icons/add_categories.gif" id="manage-selection-properties-act-modify-key" tooltip="#{cmsg.edit_values_property_dialog_action_tooltip}" value="#{cmsg.edit_values_property_dialog_action_tooltip}" showLink="false" action="wai:dialog:editSelectionPropertyDialogWai" actionListener="#{WaiDialogManager.setupParameters}" noDisplay="#{dp.selectionType == false }"  >
				<circabc:param id="param-modify-selection-property-ig-id" name="id" value="#{DialogManager.bean.interestGroup.id}" />
				<circabc:param id="param-modify-selection-property" name="property" value="#{dp.id}" />
				<circabc:param id="param-modify-selection-property-imageName" name="imageName" value="add_#{dp.name}" />
				<circabc:param id="param-modify-selection-property-service" name="service" value="Administration" />
				<circabc:param id="param-modify-selection-property-activity" name="activity" value="Edit property" />
			</circabc:actionLink>
		</circabc:column>
		<f:facet name="empty">
			<h:outputFormat id="manage-propertys-list-container" value="#{cmsg.manage_properties_dialog_no_list_items}" escape="false" />
		</f:facet>
		<circabc:dataPager id="manage-properties-pager" styleClass="pagerCirca" />
	</circabc:richList>

</circabc:panel>