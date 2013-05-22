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

<circabc:panel id="contentMainFormManageNavCustomization" styleClass="contentMainForm">

	<circabc:panel id="manage-navigation-main-section" styleClass="signup_rub_title">
		<h:outputText value="#{cmsg.manage_navigation_dialog_section_title}" escape="false" />
	</circabc:panel>

	<f:verbatim>
		<br />
	</f:verbatim>

	<circabc:richList id="manage-nav-list" viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{DialogManager.bean.preferences}" var="pref" initialSortColumn="service" >

		<circabc:column id="manage-nav-col-serv">
			<f:facet name="header">
				<circabc:sortLink id="manage-nav-service-sorter" label="#{cmsg.manage_navigation_dialog_service}" value="service" tooltipAscending="#{cmsg.generic_sort_asc}" tooltipDescending="#{cmsg.generic_sort_desc}"/>
			</f:facet>
			<h:outputText id="manage-nav-service-value" value="#{pref.service}" escape="false" />
		</circabc:column>

		<circabc:column id="manage-nav-col-type">
			<f:facet name="header">
				<circabc:sortLink id="manage-nav-type-sorter" label="#{cmsg.manage_navigation_dialog_type}" value="type" tooltipAscending="#{cmsg.generic_sort_asc}" tooltipDescending="#{cmsg.generic_sort_desc}"/>
			</f:facet>
			<h:outputText id="manage-nav-service-type" value="#{pref.type}" escape="false" />
		</circabc:column>

		<circabc:column id="manage-nav-col-inhertied">
			<f:facet name="header">
				<circabc:sortLink id="manage-nav-inhertied-sorter" label="#{cmsg.manage_navigation_dialog_inherited}" value="customizedOnPath" tooltipAscending="#{cmsg.generic_sort_asc}" tooltipDescending="#{cmsg.generic_sort_desc}"/>
			</f:facet>
			<h:outputText id="manage-nav-inherited-value" value="#{pref.customizedOnPath}" escape="false" />
		</circabc:column>

		<circabc:column id="manage-nav-col-actions" actions="true">
			<f:facet name="header">
	            <a:outputText id="manage-nav-actions" value="#{msg.actions}"/>
			</f:facet>

			<%-- Edit action --%>
			<circabc:displayer rendered="#{pref.editable}">
				<circabc:actionLink image="/images/icons/edit_form.gif" id="edit-nav" tooltip="#{cmsg.edit_navigation_action_tooltip}" value="#{cmsg.edit_navigation_action_tooltip}" showLink="false" action="wai:dialog:editNavigationPreferenceDialog" actionListener="#{WaiDialogManager.setupParameters}" >
	            	<circabc:param id="edit-nav-param-id" name="id" value="#{DialogManager.bean.actionNode.id}" />
		            <circabc:param id="edit-nav-param-service" name="prefService" value="#{pref.serviceName}" />
		            <circabc:param id="edit-nav-param-type" name="prefType" value="#{pref.typeName}" />
	    	    </circabc:actionLink>
	    		<h:outputText id="edit-nav-space" value="&nbsp;" escape="false"/>
			</circabc:displayer>
			<%-- Remove action --%>
			<circabc:displayer rendered="#{pref.removable}">
				<circabc:actionLink image="/images/icons/cancel_editing.gif" id="remove-nav" tooltip="#{cmsg.remove_navigation_action_tooltip}" value="#{cmsg.remove_navigation_action_tooltip}" showLink="false" action="wai:dialog:removeNavigationPreferenceDialog" actionListener="#{WaiDialogManager.setupParameters}" >
	            	<circabc:param id="remove-nav-param-id" name="id" value="#{DialogManager.bean.actionNode.id}" />
		            <circabc:param id="remove-nav-param-service" name="prefService" value="#{pref.serviceName}" />
		            <circabc:param id="remove-nav-param-type" name="prefType" value="#{pref.typeName}" />
	    	    </circabc:actionLink>
			</circabc:displayer>

		</circabc:column>

		<f:facet name="empty">
			<h:outputFormat id="manage-nav-nolist" value="#{cmsg.no_list_items}" escape="false" styleClass="noItem"/>
		</f:facet>

	</circabc:richList>

</circabc:panel>