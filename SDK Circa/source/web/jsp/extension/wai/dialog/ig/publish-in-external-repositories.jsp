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
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r"%>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>

<%@ page isELIgnored="false"%>

<%-- load a bundle of properties with I18N strings --%>
<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<circabc:panel id="publishInExternalRepository" styleClass="contentMainForm">
	
	<f:verbatim><br/><br/></f:verbatim>
	
	<h:outputText id="subject-mess" 
		value="&nbsp;#{cmsg.publish_in_external_repositories_dialog_subject}" 
		escape="false" />
	
	<h:inputText value="#{PublishInExternalRepositoriesDialog.subject}" size="50"/>
	
	<f:verbatim><br/><br/></f:verbatim>
	
	<h:outputText id="comment-mess" 
		value="&nbsp;#{cmsg.publish_in_external_repositories_dialog_comment}" 
		escape="false" />
	
	<h:inputText value="#{PublishInExternalRepositoriesDialog.comment}" size="100"/>
	
	<f:verbatim><br/><br/></f:verbatim>
	
	<h:outputText id="select-mailtype-mess" 
		value="&nbsp;#{cmsg.publish_in_external_repositories_dialog_mail_type}" 
		escape="false" />
	
	<h:selectOneMenu id="list-available-columns" title="#{cmsg.publish_in_external_repositories_dialog_mail_type}" value="#{PublishInExternalRepositoriesDialog.selectedMailType}" >
		<f:selectItems id="values-available-columns" value="#{PublishInExternalRepositoriesDialog.mailTypes}" />
	</h:selectOneMenu>
	
	<f:verbatim><br/><br/></f:verbatim>
	
<!-- 	<circabc:panel id="select-recipient-section-1" styleClass="signup_rub_title" > -->
		<h:outputText id="select-recipient-mess" 
			value="&nbsp;#{cmsg.publish_in_external_repositories_dialog_select_external_recipient}" 
			escape="false" />
<!-- 	</circabc:panel> -->
	
	<a:genericPicker id="picker" showFilter="false" 
			filters="#{PublishInExternalRepositoriesDialog.filters}" 
			showAddButton="false" multiSelect="false" 
			queryCallback="#{PublishInExternalRepositoriesDialog.pickerCallback}" />
	
	<h:commandButton id="AddToList" value="#{msg.add_to_list_button}"
   	              actionListener="#{PublishInExternalRepositoriesDialog.addSelectedUser}"
		          styleClass="wizardButton" />
<!-- 		          disabled="#{PublishInExternalRepositoriesDialog.selectedUserDataModel.rowCount != 0}"  -->
	
	<f:verbatim><br/><br/></f:verbatim>
	
  	<h:panelGroup>
		
		<h:dataTable value="#{PublishInExternalRepositoriesDialog.selectedUserDataModel}" var="row"
			rowClasses="selectedItemsRow,selectedItemsRowAlt"
			styleClass="selectedItems" headerClass="selectedItemsHeader"
			cellspacing="0" cellpadding="4"
			rendered="#{PublishInExternalRepositoriesDialog.selectedUserDataModel.rowCount != 0}">
			<h:column>
				<f:facet name="header">
					<h:outputText value="#{msg.name}" />
				</f:facet>
				<h:outputText value="#{row.authority}" />
			</h:column>
			<h:column>
				<circabc:actionLink actionListener="#{PublishInExternalRepositoriesDialog.removeSelection}"
					image="/images/icons/delete.gif" tooltip="#{msg.remove}" value="#{msg.remove}" showLink="false"
					styleClass="pad6Left" />
			</h:column>
		</h:dataTable>
		<a:panel id="no-items"
			rendered="#{PublishInExternalRepositoriesDialog.selectedUserDataModel.rowCount == 0}">
			<br/>
			<table cellspacing='0' cellpadding='2' border='0' class='selectedItems'>
				<tr>
					<td colspan='2' class='selectedItemsHeader'><h:outputText
						id="no-items-name" value="#{msg.name}:" /></td>
				</tr>
				<tr>
					<td class='selectedItemsRow'>
						<h:outputText id="no-items-msg" value="#{msg.no_selected_items}" />
					</td>
				</tr>
			</table>
		</a:panel>
		
  	</h:panelGroup>
	
</circabc:panel>
