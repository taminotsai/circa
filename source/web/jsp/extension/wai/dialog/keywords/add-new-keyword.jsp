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

<circabc:panel id="contentMainFormAddKeyword" styleClass="contentMainForm">

	<!--  New keyword panel -->
	<circabc:panel id="add-keywords-first-section" styleClass="signup_rub_title">
		<h:outputText value="1.&nbsp;#{cmsg.add_new_keyword_dialog_section_specify}" escape="false" />
	</circabc:panel>

	<f:verbatim>
		<br />
	</f:verbatim>

	<h:selectOneMenu id="add-keyword-language" value="#{DialogManager.bean.language}" >
		<f:selectItems id="add-keywords-languages" value="#{DialogManager.bean.languages}" />
	</h:selectOneMenu>
	<h:inputText id="add-keyword-value" value="#{DialogManager.bean.value}" />

	<f:verbatim>
		<br /><br /><br />
	</f:verbatim>

	<circabc:panel id="add_new_keyword_dialog_section_add" styleClass="signup_rub_title">
		<h:outputText value="2.&nbsp;#{cmsg.add_new_keyword_dialog_section_add}" escape="false" />
	</circabc:panel>

	<f:verbatim>
		<br />
	</f:verbatim>

	<h:commandButton id="AddToList" value="#{msg.add_to_list_button}"
		actionListener="#{DialogManager.bean.addSelection}"
		styleClass="wizardButton" />

	<f:verbatim>
		<br /><br />
	</f:verbatim>

	<h:dataTable value="#{DialogManager.bean.translationDataModel}" var="row"
		rowClasses="selectedItemsRow,selectedItemsRowAlt"
		styleClass="selectedItems" headerClass="selectedItemsHeader"
		cellspacing="0" cellpadding="4"
		rendered="#{DialogManager.bean.translationDataModel.rowCount != 0}">
		<h:column>
			<f:facet name="header">
				<h:outputText value="#{msg.language}" />
			</f:facet>
			<h:outputText id="col-lang" value="#{row.language}" />
		</h:column>
		<h:column>
			<f:facet name="header">
				<h:outputText id="col-name" value="#{msg.name}" />
			</f:facet>
			<h:outputText value="#{row.value}" />
		</h:column>
		<h:column>
			<circabc:actionLink tooltip="#{msg.remove}" actionListener="#{DialogManager.bean.removeSelection}"
				image="/images/icons/delete.gif" value="#{msg.remove}" showLink="false"
				styleClass="pad6Left" />
		</h:column>
	</h:dataTable>
	<a:panel id="no-items"
		rendered="#{DialogManager.bean.translationDataModel.rowCount == 0}">
		<table cellspacing='0' cellpadding='2' border='0' class='selectedItems'>
			<tr>
				<td class='selectedItemsRow'>
					<h:outputText id="no-items-msg" value="#{msg.no_selected_items}" />
				</td>
			</tr>
		</table>
	</a:panel>

</circabc:panel>
