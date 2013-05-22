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
<%@ taglib uri="/WEB-INF/tomahawk.tld" prefix="t"%>

<%@ page isELIgnored="false"%>

<%-- load a bundle of properties with I18N strings --%>
<f:loadBundle basename="alfresco.messages.webclient" var="msg" />
<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<div class="formArea">

			

			<h4><h:outputText value="#{cmsg.bulk_invite_bulk_tools_title }"></h:outputText></h4>
			
			<div style="float:right;margin-right:15px;">
			
			 <h:outputLabel value="#{cmsg.bulk_invite_user_notify_title}"></h:outputLabel>
			 <h:selectBooleanCheckbox value="#{BulkInviteCircabcUsersWizard.notifyInvitations }"></h:selectBooleanCheckbox>
			
			</div>
	
			<h:selectOneMenu id="profileSelection" value="#{BulkInviteCircabcUsersWizard.selectedProfile }" >
		   		<f:selectItems value="#{BulkInviteCircabcUsersWizard.igProfilesAvailable }"/>
		   </h:selectOneMenu>
	
		   <h:outputLabel value="#{cmsg.bulk_invite_bulk_change_profile_title}"></h:outputLabel>
		   <h:commandButton value="#{cmsg.bulk_invite_bulk_update_title}" actionListener="#{BulkInviteCircabcUsersWizard.updateProfilesForSelectedUsers }"></h:commandButton>
		   <h:outputText value="#{cmsg.bulk_invite_bulk_selecte_users}"></h:outputText>
		   
		   <br/>
	
			<h:outputLabel value=""></h:outputLabel>
			<h:commandButton value="#{cmsg.bulk_invite_bulk_remove_title}" actionListener="#{BulkInviteCircabcUsersWizard.removeSelectedUsers }"></h:commandButton>
			<h:outputText value="#{cmsg.bulk_invite_bulk_selecte_users}"></h:outputText>
			
			<br/>
			
			
			<h4><h:outputText value="#{cmsg.bulk_invite_big_table_title }"></h:outputText></h4>
			
			<div style="float:right; text-align:right;margin-right:15px;">
				<h:outputText value="#{cmsg.bulk_invite_user_status_ok }" style="margin-right:5px;"> </h:outputText>
				<h:graphicImage alt="Ok" id="okInvitation" title="#{cmsg.bulk_invite_user_status_ok_tooltip }" value="/images/extension/icons/BulletGreen.png"></h:graphicImage>
				<br/>
				<h:outputText value="#{cmsg.bulk_invite_user_status_not_ok }" style="margin-right:5px;"> </h:outputText>
				<h:graphicImage alt="not Ok" id="notOkInvitation" title="#{cmsg.bulk_invite_user_status_not_ok_tooltip }" value="/images/extension/icons/BulletRed.png" ></h:graphicImage>
			</div>
			
			
			<circabc:actionLink value="" tooltip="" image="/images/extension/icons/checked.png" rendered="#{BulkInviteCircabcUsersWizard.selectedAllUsers == true}" actionListener="#{BulkInviteCircabcUsersWizard.unselectUser }">
		   		<circabc:param value="true" name="all"></circabc:param>
		   </circabc:actionLink>
		   
		   <circabc:actionLink value="" tooltip="" image="/images/extension/icons/unchecked.png" rendered="#{BulkInviteCircabcUsersWizard.selectedAllUsers == false}" actionListener="#{BulkInviteCircabcUsersWizard.selectUser }">
		   		<circabc:param value="true" name="all"></circabc:param>
		   </circabc:actionLink>
		   
		   <h:outputText value="#{cmsg.bulk_invite_select_unselect_label }" style="margin-left:5px;"> </h:outputText>
		   
		   <br/><br/>
	
	<h:dataTable var="row" value="#{BulkInviteCircabcUsersWizard.model  }" rowClasses="recordSetRow,recordSetRowAlt"
			styleClass="selectedItems" headerClass="selectedItemsHeader" footerClass="selectedItemsHeader" width="100%">
	
		<h:column>
		  <f:facet name="header">
		  <h:outputText value="" />
		  </f:facet> 
		   <circabc:actionLink value="" tooltip="" image="/images/extension/icons/checked.png" rendered="#{row.selected == true}" actionListener="#{BulkInviteCircabcUsersWizard.unselectUser }">
		   	<circabc:param value="#{row.user.email}" name="email"></circabc:param>
		   </circabc:actionLink>
		   
		   <circabc:actionLink value="" tooltip="" image="/images/extension/icons/unchecked.png" rendered="#{row.selected == false}" actionListener="#{BulkInviteCircabcUsersWizard.selectUser }">
		   	<circabc:param value="#{row.user.email}" name="email"></circabc:param>
		   </circabc:actionLink>
		</h:column>
	
		<h:column>
		  <f:facet name="header">
		  <h:outputText value="Ig" />
		  </f:facet> 
		   <h:outputText value="#{row.igName}" rendered="#{row.igName != null }"></h:outputText>
		</h:column>
		<h:column>
		  <f:facet name="header">
		  <h:outputText value="Group" />
		  </f:facet> 
		   <h:outputText value="#{row.departmentNumber}" rendered="#{row.departmentNumber != null }"></h:outputText>
		</h:column>
		<h:column>
		  <f:facet name="header">
		  <h:outputText value="File" />
		  </f:facet> 
		   <h:outputText value="#{row.fromFile}" rendered="#{row.fromFile != null }"></h:outputText>
		</h:column>
		<h:column>
		  <f:facet name="header">
		  <h:outputText value="Username" />
		  </f:facet> 
		   <h:outputText value="#{row.user.ecasUserName}"></h:outputText>
		</h:column>
		<h:column>
		  <f:facet name="header">
		  <h:outputText value="Email" />
		  </f:facet> 
		   <h:outputText value="#{row.user.email}"></h:outputText>
		</h:column>
		<h:column>
		  <f:facet name="header">
		  <h:outputText value="Profile" />
		  </f:facet> 
		   <h:outputText value="#{row.profile}"></h:outputText>		   
		</h:column>
		<h:column>
		  <f:facet name="header">
		  <h:outputText value="Status" />
		  </f:facet> 
		  <h:graphicImage alt="Ok" id="okInvitationTable" title="#{cmsg.bulk_invite_user_status_ok_tooltip }" value="/images/extension/icons/BulletGreen.png" rendered="#{row.status == 'ok'}"></h:graphicImage>
		  <h:graphicImage alt="Error" id="errorInvitationTable" title="#{cmsg.bulk_invite_user_status_not_ok_tooltip }" value="/images/extension/icons/BulletRed.png" rendered="#{row.status == 'ignore' || row.status == 'nok'}"></h:graphicImage>
		</h:column>
		<h:column>
		  <f:facet name="header">
		  <h:outputText value="Actions" />
		  </f:facet> 
		   
		   <circabc:actionLink value="" tooltip="#{cmsg.event_delete_meeting_action_title }" image="/images/extension/help/delete.gif" actionListener="#{BulkInviteCircabcUsersWizard.removeUserSelection }">
		   		<circabc:param id="mail" name="mail" value="#{row.user.email}" />
		   </circabc:actionLink>
		</h:column>
	
	</h:dataTable>
	
	<h4><h:outputText styleClass="signup_subrub_title" value="#{cmsg.bulk_invite_profile_backup_title }"></h:outputText></h4>
	<h:commandButton style="margin-left:200px;" value="#{cmsg.bulk_invite_profile_backup_action_title }" actionListener="#{BulkInviteCircabcUsersWizard.saveWorkTemplate }"></h:commandButton>
	<br/><br/>
	
</div>