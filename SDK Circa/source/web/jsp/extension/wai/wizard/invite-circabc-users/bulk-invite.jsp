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
<%@ taglib uri="/WEB-INF/tomahawk.tld" prefix="t"%>
	
	<script type="text/javascript">
	
		function displayLoader()
		{
			document.getElementById("loaderDiv").style.display='block';
		}
	
	</script>
	
	
		<div class="formArea">
		
		<div id="loaderDiv" style="display:none; width:100%; height:75px; text-align:center; line-height:25px;">
			Loading users, please wait.
			<br/>
			<h:graphicImage value="/images/extension/arrows-loader.gif" alt="loading..."/>
		</div>


	<h4><h:outputText styleClass="signup_subrub_title" value="#{cmsg.bulk_invite_select_user_from_group_title }"></h:outputText></h4>
	<h:outputLabel value="#{cmsg.bulk_invite_select_category}:" for="category"></h:outputLabel>
	<h:selectOneMenu id="category" value="#{BulkInviteCircabcUsersWizard.selectedCategory }" styleClass="textNormalWidth" onchange="submit()" valueChangeListener="#{BulkInviteCircabcUsersWizard.refreshAvailableInterestGroups }">
		<f:selectItems value="#{BulkInviteCircabcUsersWizard.availableCategories }"/>
	</h:selectOneMenu>
	<br/>
	<br/>
	<h:outputLabel value="#{cmsg.bulk_invite_select_interest_group}:" for="ig"></h:outputLabel>
	
	<h:selectManyListbox value="#{BulkInviteCircabcUsersWizard.selectedAvailableInterestGroups }" style="width:30%;height:150px;float:left;">
		<f:selectItems value="#{BulkInviteCircabcUsersWizard.availableInterestGroups }"/>
	</h:selectManyListbox>
	
	<div style="float:left; padding:15px;">
	<h:commandButton value="" actionListener="#{BulkInviteCircabcUsersWizard.addSelection }" image="/images/extension/icons/move-right.png" style="margin-top:10px;" onclick="displayLoader()"></h:commandButton>
	<br/>
	<h:commandButton value="" actionListener="#{BulkInviteCircabcUsersWizard.removeSelection }" image="/images/extension/icons/move-left.png" style="margin-top:35px;" onclick="displayLoader()"></h:commandButton>
	</div>
	
	<h:selectManyListbox value="#{BulkInviteCircabcUsersWizard.selectedChosedInterestGroups }" style="width:30%;height:150px;float:left;">
		<f:selectItems value="#{BulkInviteCircabcUsersWizard.chosenConvertedGroups }"/>
	</h:selectManyListbox>
	
	<br style="clear:both;"/>

	
	<h:graphicImage alt="" value="/images/extension/icons/cursor.png" style="float:right; margin-right:10%; margin-top:50px;" width="100"></h:graphicImage>
	
	<h4><h:outputText styleClass="signup_subrub_title" value="#{cmsg.bulk_invite_profile_helper_title }"></h:outputText></h4>
	<h:selectBooleanCheckbox  style="margin-left:200px;" id="createProfileHelper" value="#{BulkInviteCircabcUsersWizard.createIgProfileHelper }" valueChangeListener="#{BulkInviteCircabcUsersWizard.refreshIgProfileHelper}" onchange="submit()"></h:selectBooleanCheckbox><h:outputText value="#{cmsg.bulk_invite_profile_helper_description }"></h:outputText>
	<br/><br/>
	
	<h4><h:outputText styleClass="signup_subrub_title" value="#{cmsg.bulk_invite_upload_file }"></h:outputText></h4>
	
	<h:outputLabel value="#{cmsg.file}:" for="file"></h:outputLabel>
	<t:inputFileUpload id="file" value="#{BulkInviteCircabcUsersWizard.submittedFile}" size="40" storage="file"></t:inputFileUpload>
	<br/>
	<h:commandButton style="margin-left:200px;" value="#{cmsg.add_content_upload }" actionListener="#{BulkInviteCircabcUsersWizard.uploadTemplate }" onclick="displayLoader()"></h:commandButton>
	
		<circabc:actionLink value="#{cmsg.bulk_invite_upload_file_template_link}"
		tooltip="#{cmsg.bulk_invite_upload_file_template_link}"
		href="/docs/bulk/user/import/bulk-user-import-template.xls" target="_blank" />
		
	<br/><br/>
	
	<h:dataTable var="file" value="#{BulkInviteCircabcUsersWizard.uploadedTemplates }" rowClasses="recordSetRow,recordSetRowAlt"
			styleClass="selectedItems" headerClass="selectedItemsHeader" footerClass="selectedItemsHeader" width="100%" rendered="#{BulkInviteCircabcUsersWizard.uploadedTemplatesSize > 0 }">
		<h:column>
		  <f:facet name="header">
		  <h:outputText value="#{cmsg.file }" />
		  </f:facet> 
		   <h:outputText value="#{file.name}"></h:outputText>
		</h:column>
		
		<h:column>
		  <f:facet name="header">
		  <h:outputText value="#{cmsg.action }" />
		  </f:facet>
		  <circabc:actionLink id="removeFile-link" value="#{cmsg.bulk_invite_remove_template_file}" tooltip="#{cmsg.bulk_invite_remove_template_file}" actionListener="#{BulkInviteCircabcUsersWizard.removeTemplate}">
				<circabc:param id="idFileName" name="fileName" value="#{file.name}" />
		  </circabc:actionLink>
		</h:column>
		
		<f:facet name="empty">
			<h:outputFormat id="empty-template-list" value="#{cmsg.bulk_invite_empty_template_file_list}" />
		</f:facet>
		
	</h:dataTable>
	
	<h4><h:outputText styleClass="signup_subrub_title" value="#{cmsg.bulk_invite_profile_backup_title }"></h:outputText></h4>
	<h:commandButton style="margin-left:200px;" value="#{cmsg.bulk_invite_profile_backup_action_title }" actionListener="#{BulkInviteCircabcUsersWizard.saveWorkTemplate }"></h:commandButton>
	<br/><br/>
	
	

</div>

<script type="text/javascript" language="javascript">
var formTag = document.getElementById("FormPrincipal");
formTag.setAttribute("enctype","multipart/form-data");

</script>
