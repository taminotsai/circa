<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a" %>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r" %>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>



<%-- load a bundle of properties with I18N strings --%>
<f:loadBundle basename="alfresco.messages.webclient" var="msg"/>
<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

	<h:selectOneMenu id="define-property-language" value="#{WizardManager.bean.language}">
		<f:selectItems value="#{WizardManager.bean.languages}" />
	</h:selectOneMenu>

	<h:inputText id="define-property-text" value="#{WizardManager.bean.text}" />
	<h:commandButton id="AddDescription" value="#{cmsg.edit_property_dialog_add_desc_button}" actionListener ="#{WizardManager.bean.addDescription}" />

	<f:verbatim>
		<br /><br />
	</f:verbatim>

	<h:outputText value="#{cmsg.edit_property_dialog_section_title}" />

	<f:verbatim>
		<br /><br />
	</f:verbatim>

	<h:dataTable id="define-dyn-prop-data-table" value="#{WizardManager.bean.translationDataModel}" var="row"
		rowClasses="selectedItemsRow,selectedItemsRowAlt"
		styleClass="selectedItems" headerClass="selectedItemsHeader"
		cellspacing="0" cellpadding="4"
		rendered="#{WizardManager.bean.translationDataModel.rowCount != 0}">

		<h:column>
			<f:facet name="header">
				<h:outputText id="define-dyn-prop-col-1-header" value="#{cmsg.define_property_dialog_language}" />
			</f:facet>
			<h:outputText id="define-dyn-prop-col-1-value" value="#{row.language}" />
		</h:column>
		<h:column>
			<f:facet name="header">
				<h:outputText id="define-dyn-prop-col-2-header"value="#{cmsg.define_property_dialog_translation}" />
			</f:facet>
			<h:outputText id="define-dyn-prop-col-2-value" value="#{row.value}" />
		</h:column>
		<h:column>
			<circabc:actionLink id="define-dyn-prop-col-3-value"
				actionListener="#{WizardManager.bean.removeSelection}"
				image="/images/icons/delete.gif" value="#{msg.remove}" tooltip="#{msg.remove}" showLink="false"
				styleClass="pad6Left" />
		</h:column>
	</h:dataTable>

	<a:panel id="no-items"
		rendered="#{WizardManager.bean.translationDataModel.rowCount == 0}">
		<table cellspacing='0' cellpadding='2' border='0' class='selectedItems'>
			<tr>
				<td class='selectedItemsRow'>
					<h:outputText id="define-dyn-prop-no-items-msg" value="#{cmsg.define_property_dialog_no_list_items}" />
				</td>
			</tr>
		</table>
	</a:panel>

