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

<circabc:panel id="contentMainFormDefineOthersNotification" styleClass="contentMainForm">

	<f:verbatim>
		<br />
	</f:verbatim>

	<circabc:panel id="define-notif-section-title" styleClass="signup_rub_title">
		<h:outputText value="#{cmsg.notification_define_other_dialog_section_title}" escape="false" />
	</circabc:panel>

	<f:verbatim>
		<br />
	</f:verbatim>

	<a:genericPicker id="define-notif-picker" showAddButton="false"
		filters="#{DialogManager.bean.filters}"
		queryCallback="#{DialogManager.bean.pickerCallback}"
		width="300" />
	<h:outputText value="" />

	<f:verbatim>
		<br />
	</f:verbatim>

	<h:outputText id="define-notif-select-status" value="#{cmsg.notification_define_other_dialog_select}" />

	<f:verbatim>
		<br /><br />
	</f:verbatim>

	<h:selectOneListbox id="define-notif-statuses" size="3" >
		<f:selectItems id="define-notif-statuses-list" value="#{DialogManager.bean.statuses}" />
	</h:selectOneListbox>

	<f:verbatim>
		<br /><br />
	</f:verbatim>

	<h:commandButton id="define-notif-add-to-list" value="#{msg.add_to_list_button}"
		actionListener="#{DialogManager.bean.addSelection}"
		styleClass="wizardButton" />

	<f:verbatim>
		<br /><br />
	</f:verbatim>

	<h:dataTable id="define-notif-data-table" value="#{DialogManager.bean.userNotificationDataModel}" var="row"
		rowClasses="selectedItemsRow,selectedItemsRowAlt"
		styleClass="selectedItems" headerClass="selectedItemsHeader"
		cellspacing="0" cellpadding="4"
		rendered="#{DialogManager.bean.userNotificationDataModel.rowCount != 0}">

		<h:column>
			<f:facet name="header">
				<h:outputText id="define-notif-col-1-header" value="#{cmsg.notification_view_other_dialog_col_type}" />
			</f:facet>
			<h:outputText id="define-notif-col-1-value" value="#{row.type}" />
		</h:column>
		<h:column>
			<f:facet name="header">
				<h:outputText id="define-notif-col-2-header"value="#{cmsg.notification_view_other_dialog_col_username}" />
			</f:facet>
			<h:outputText id="define-notif-col-2-value" value="#{row.username}" />
		</h:column>
		<h:column>
			<f:facet name="header">
				<h:outputText id="define-notif-col-3-header" value="#{cmsg.notification_view_other_dialog_col_status}" />
			</f:facet>
			<h:outputText id="define-notif-col-3-value" value="#{row.status}" />
		</h:column>
		<h:column>
			<circabc:actionLink tooltip="#{msg.remove}" id="define-notif-col-4-value"
				actionListener="#{DialogManager.bean.removeSelection}"
				image="/images/icons/delete.gif" value="#{msg.remove}" showLink="false"
				styleClass="pad6Left" />
		</h:column>
	</h:dataTable>

	<a:panel id="no-items"
		rendered="#{DialogManager.bean.userNotificationDataModel.rowCount == 0}">
		<table cellspacing='0' cellpadding='2' border='0' class='selectedItems'>
			<tr>
				<td class='selectedItemsRow'>
					<h:outputText id="no-items-msg" value="#{msg.no_selected_items}" />
				</td>
			</tr>
		</table>
	</a:panel>


</circabc:panel>