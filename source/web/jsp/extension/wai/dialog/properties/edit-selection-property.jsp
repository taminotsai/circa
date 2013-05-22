<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a"%>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r"%>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>



<%-- load a bundle of properties with I18N strings --%>
<f:loadBundle basename="alfresco.messages.webclient" var="msg" />
<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<circabc:panel id="edit-selection-property-section">



	<circabc:panel id="edit-selection-definition">

		<f:verbatim>
			<br />
		</f:verbatim>
		<h:outputText value="#{cmsg.edit_selection_list_of_values}" escape="false" />
		<f:verbatim>
			<br />
			<br />
		</f:verbatim>
		<h:inputText id="add-text" value="#{DialogManager.bean.addText}"></h:inputText>
		<h:commandButton id="add-button" value="#{cmsg.edit_selection_add_value}"
			actionListener="#{DialogManager.bean.add}"></h:commandButton>
		<f:verbatim>
			<br /><br />
		</f:verbatim>
		<h:selectOneListbox styleClass="standardSelect"
			id="list-of-valid-values" value="#{DialogManager.bean.currentValue}">
			<f:selectItems value="#{DialogManager.bean.listOfValidValues}" />
		</h:selectOneListbox>
		<circabc:panel id="edit-selection-definition-move-action"
			styleClass="leftFloatingActions">
			<h:commandButton id="up-button" value="#{cmsg.edit_selection_move_value_up}"
				actionListener="#{DialogManager.bean.up}"></h:commandButton>
			<h:commandButton id="down-button" value="#{cmsg.edit_selection_move_value_down}"
				actionListener="#{DialogManager.bean.down}"></h:commandButton>
<f:verbatim>
				<hr class="tinyLineSpacer" />
			</f:verbatim>
			<h:commandButton id="select-button" value="#{cmsg.edit_selection_select_value}"
				actionListener="#{DialogManager.bean.select}"></h:commandButton>
			<h:inputText id="update-text"
				value="#{DialogManager.bean.updateText}"></h:inputText>
			<h:commandButton id="update-button" value="#{cmsg.edit_selection_update_value}"
				actionListener="#{DialogManager.bean.update}"></h:commandButton>
			<f:verbatim>
				<hr class="tinyLineSpacer" />
			</f:verbatim>
			<h:commandButton id="remove-button" value="#{cmsg.edit_selection_remove_value}"
				actionListener="#{DialogManager.bean.remove}"></h:commandButton>
		</circabc:panel>
		
		
	</circabc:panel>

	<f:verbatim>
		<br />
		<br />
	</f:verbatim>

	<h:outputLabel id="update-existing-properties-text"
		value="#{cmsg.edit_selection_update_existing_values}"></h:outputLabel>
	<h:selectBooleanCheckbox id="update-existing-properties"
		value="#{DialogManager.bean.updateExistingProperties}"></h:selectBooleanCheckbox>

</circabc:panel>



