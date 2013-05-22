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

<%@ page pageEncoding='UTF8' %>


<circabc:view>
	<%-- load a bundle of properties I18N strings here --%>
	<f:loadBundle basename="alfresco.messages.webclient" var="msg" />
	<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />


	<%@ page isELIgnored="false"%>

  	<c:set var="currentTitle" value="${cmsg.title_add_content}" />

	<%@ include file="/jsp/extension/wai/parts/header.jsp" %>
    <%@ include file="/jsp/extension/wai/parts/banner.jsp" %>

<h:form  id="FormPrincipal">
	<%@ include file="/jsp/extension/wai/parts/left-menu.jsp" %>
</h:form>

<div id="maincontent">
	
	<%-- Content START --%>
	<div id="ContentHeader">
		<circabc:panel id="contentHeaderUrlsPanel" styleClass="ContentHeaderUrls" >
			<f:verbatim><br /></f:verbatim>
			<circabc:displayer rendered="#{InterestGroupLogoBean.dialogDisplay}">
				<h:graphicImage binding="#{InterestGroupLogoBean.otherPagesIconBinding}" id="addcontent-ig-logo" />
			</circabc:displayer>
		</circabc:panel>
		<div class="ContentHeaderNavigationLibrary">
		<h:form acceptcharset="UTF-8" id="FormPrincipal2"><circabc:navigationList id="navigationLibrary" value="#{WaiNavigationManager.navigation}" actionListener="#{BrowseBean.clickWai}" separatorFirst="false" styleClass="navigationLibrary" /></h:form></div>
		<div>
			<div class="ContentHeaderIcone"><h:graphicImage url="/images/icons/add_content_large.gif" alt="#{cmsg.add_content_icon_tooltip}" title="#{cmsg.add_content_icon_tooltip}"></h:graphicImage></div>
			<div class="ContentHeaderText">
				<span class="ContentHeaderTitle"><h:outputText value="#{cmsg.add_content_title}" /></span><br />
				<span class="ContentHeaderSubTitle"><h:outputText value="#{cmsg.add_content_title_desc}" /></span>
			</div>
		</div>
	</div>
	<div id="ContentMain">
		
		<div class="ContentMainFormFull">

		<h:form acceptcharset="UTF-8" id="FormRemoveFile" enctype="multipart/form-data">
		
		<circabc:errors styleClass="messageGen" infoClass="messageInfo" warnClass="messageWarn" errorClass="messageError" escape="false"/>

			<div class="formStep">
			
				<br/>
				
				<img alt="" src="${currentContextPath}/images/extension/icons/uploadBigBlue.png" style="margin-top:20px; margin-left:5%; margin-right:5%; float:left; width:110px;">


					<div class="rightWarning">
						<img src="${currentContextPath}/images/extension/icons/warning.png" alt="warning" title="warning" class="iconWarning"/>
						<br/>
						<h:outputText value="#{cmsg.add_content_warning_limit}"/>
						<h:outputText value="#{CircabcAddContentBean.fileUploadLimit.maxSizeInMegaBytes} Mb"/>
					</div>
 
					<%-- Locate the content --%>
					<h:outputText id="text1" value="1. #{cmsg.add_content_locate_content}" styleClass="signup_subrub_title"/><br />
					<br />
					<t:inputFileUpload id="alfFileInput" size="50" value="#{CircabcAddContentBean.submittedFile}" storage="file" validator="#{CircabcAddContentBean.valiteFileSize}">
					
					</t:inputFileUpload>
					<br style="margin-bottom:15px;"/>
					
					<h:selectBooleanCheckbox value="#{CircabcAddContentBean.isMultiLingualDocument }" ></h:selectBooleanCheckbox>&nbsp;
					<h:outputText value="#{cmsg.add_content_to_multilingual_document }"></h:outputText>&nbsp;
					<h:selectOneMenu id="language" value="#{CircabcAddContentBean.submittedLanguageDocument }" style="width:150px;">
						<f:selectItems value="#{CircabcAddContentBean.filterLanguages}"/>
					</h:selectOneMenu>
	
					<%-- Set options file --%>
					<%-- Currently disabled for the new upload file form --%>
					<h:outputText id="text2" value="2. #{cmsg.library_add_content_other_options}" styleClass="signup_subrub_title" rendered="false"/><br />
					<h:panelGrid columns="2" cellpadding="3" cellspacing="3" border="0" rendered="false">
						<h:selectBooleanCheckbox id="edit-properties" value="#{CircabcAddContentBean.showOtherProperties}" readonly="#{CircabcAddContentBean.uploadedFileCount > 0}"/>
						<h:outputText id="text-modif-props" value="#{cmsg.library_add_content_modify_props}"/>
						<h:selectBooleanCheckbox id="check-disable-notif" value="#{CircabcAddContentBean.disableNotification}" title="#{cmsg.notification_disable_mechanism_tooltip}" />
						<h:outputText id="text-notif-disabled" value="#{cmsg.notification_disable_mechanism}"/>
					</h:panelGrid>
	
					<%-- Upload the file --%>
					<h:outputText id="text3" value="2. #{cmsg.add_content_click_upload}" styleClass="signup_subrub_title"/><br />
	
					<h:commandButton value="Upload" action="wai:dialog:addContentWai"  actionListener="#{CircabcAddContentBean.uploadFile} " onclick="showWaitProgress();"></h:commandButton>

				<br/><br/>
			</div>

			
			
			<div class="ContentMainButton">
				<div class="divButtonDialogHighLighted">
	
						<h:commandButton id="finish-button" styleClass="dialogButton" value="#{cmsg.finish}" action="#{CircabcAddContentBean.finish}"  disabled="#{CircabcAddContentBean.uploadedFileCount == 0}" onclick="showWaitProgress();"/>
	                	<h:commandButton id="cancel-button" styleClass="dialogButton" value="#{msg.cancel}" action="#{CircabcAddContentBean.cancel}" />
	
				</div>
			</div>
			
				<br/><br/>

				<circabc:panel id="upload-uploadedfiles-section" styleClass="signup_rub_title" tooltip="#{cmsg.library_add_content_uploaded_files}">
					<h:outputText value="#{cmsg.library_add_content_uploaded_files}" escape="false" />
				</circabc:panel>

				<f:verbatim><br /></f:verbatim>

				<h:dataTable id="all-uploaded-files" value="#{CircabcAddContentBean.uploadedFilesDataModel}" var="row"
					cellspacing="0" cellpadding="0"
					style="width:100%;"
					rendered="#{CircabcAddContentBean.uploadedFileCount > 0}" columnClasses="col_ids, col_files" styleClass="uploads-table">
					
					<h:column id="column-id">
						
						<h:outputText value="#{CircabcAddContentBean.uploadedFilesDataModel.rowIndex+1}" title="#{CircabcAddContentBean.uploadedFilesDataModel.rowIndex+1}"/>
					
					</h:column>
					
					<h:column id="column-file" >

						
						<f:verbatim><div class="formArea"></f:verbatim>
						
						
							<f:verbatim><div class="miniHeader"></f:verbatim>
								<h:panelGroup style="font-weight:bold; width:60%; display:inline-block;"><h:outputText value="#{row.fileName}"/></h:panelGroup>
								<h:panelGroup style="text-align:right; width:40%; display:inline-block;">
									<h:outputText value="#{row.resolveFileSize}" escape="false"/><h:outputText value="&nbsp&nbsp|&nbsp&nbsp" escape="false"/>
									<h:selectOneMenu  value="#{CircabcAddContentBean.uploadedFiles[CircabcAddContentBean.uploadedFilesDataModel.rowIndex].submitedProperties['check-disable-notif']}">
										<f:selectItem itemLabel="#{cmsg.do_not_notify }" itemValue="true"></f:selectItem>
										<f:selectItem itemLabel="#{cmsg.do_notify }"  itemValue="false"></f:selectItem>
									</h:selectOneMenu>
									<h:outputText value="&nbsp&nbsp|&nbsp&nbsp" escape="false"/>
									<h:commandLink id="remove-uploaded" actionListener="#{CircabcAddContentBean.removeSelection}"
									 value="#{cmsg.profile_remove_title}"
									  />
								</h:panelGroup>
							<f:verbatim></div><br/></f:verbatim>
							
							<h:panelGroup>
							
								<h:graphicImage alt="" title="" styleClass="fileExtension" value="#{CircabcAddContentBean.uploadedFiles[CircabcAddContentBean.uploadedFilesDataModel.rowIndex].preparedExtension}"></h:graphicImage>
							
								<h:outputLabel value="#{cmsg.title }: " for="title"></h:outputLabel>
								<h:inputText styleClass="textNormalWidth" id="title" value="#{CircabcAddContentBean.uploadedFiles[CircabcAddContentBean.uploadedFilesDataModel.rowIndex].submitedProperties['title']}"></h:inputText>
								<h:outputLabel styleClass="secondLabel" value="#{cmsg.status }: " for="status"></h:outputLabel>
								<h:selectOneMenu styleClass="textTinyWidth" id="status" value="#{CircabcAddContentBean.uploadedFiles[CircabcAddContentBean.uploadedFilesDataModel.rowIndex].submitedProperties['status']}">
									<f:selectItems value="#{CircabcAddContentBean.possibleStatus }"/>
								</h:selectOneMenu>
								
								<f:verbatim><br/></f:verbatim>
								<h:outputLabel value="#{cmsg.description }: " for="description"></h:outputLabel>
								<h:inputTextarea styleClass="textLongWidth textLongHeight" id="description" value="#{CircabcAddContentBean.uploadedFiles[CircabcAddContentBean.uploadedFilesDataModel.rowIndex].submitedProperties['description']}"></h:inputTextarea>
								<f:verbatim><br/></f:verbatim>
								<h:outputLabel value="#{cmsg.author }: " for="author"></h:outputLabel>
								<h:inputText styleClass="textNormalWidth" id="author" value="#{CircabcAddContentBean.uploadedFiles[CircabcAddContentBean.uploadedFilesDataModel.rowIndex].submitedProperties['author']}"></h:inputText>
							</h:panelGroup>
							
							<f:verbatim><br/></f:verbatim>
							<f:verbatim><div class="optionalPart"></f:verbatim>
							
							<!-- WHEN IT IS PIVOT DOCUMENT -->
							<h:panelGroup rendered="#{row.isPivotMultilingual == true and row.isTranslation == false}">
								<h:outputText style="margin-left:50px; padding:5px;" value="#{cmsg.add_content_is_pivot_multilingual_title }: "></h:outputText>
								<h:outputText value="#{row.languageTitle}" style="font-weight:bold;"></h:outputText>
								<h:commandLink id="remove-multilingual" actionListener="#{CircabcAddContentBean.removeMultilinguism}"
									 value="#{cmsg.add_content_cancel_pivot_multilingual_title}"
									  style="margin-left:100px;"/>
							</h:panelGroup>
							
							<!-- WHEN IT IS TRANSLATED DOCUMENT -->
							<h:panelGroup rendered="#{row.isPivotMultilingual == false and row.isTranslation == true}">
								<h:outputText style="margin-left:50px; padding:5px;" value="#{cmsg.add_content_is_translation_title } "></h:outputText>
								<h:outputText value="#{row.isTranslationOfDocument} " style="font-weight:bold;"></h:outputText>
								<h:outputText value="#{cmsg.add_content_in} "></h:outputText>
								<h:outputText value="#{row.languageTitle}" style="font-weight:bold;"></h:outputText>
								<h:commandLink id="remove_translation" actionListener="#{CircabcAddContentBean.removeTranslation}"
									 value="#{cmsg.add_content_cancel_translation_title}"
									  style="margin-left:100px;"/>
							</h:panelGroup>
							
							<!-- WHEN IT IS A SIMPLE DOCUMENT -->
							<h:panelGroup rendered="#{row.isPivotMultilingual == false and row.isTranslation == false and CircabcAddContentBean.hasPivotDocuments == true}">
							
								<h:commandButton id="add_translation" actionListener="#{CircabcAddContentBean.addTranslation}"
									 value="#{cmsg.add_content_make_translation_title}"
									  style="margin-left:50px;"/>
								<h:outputText style="margin-left:10px;" value="#{cmsg.add_content_make_translation_description } "></h:outputText>
								
								<h:selectOneMenu id="translation-of-document" value="#{CircabcAddContentBean.uploadedFiles[CircabcAddContentBean.uploadedFilesDataModel.rowIndex].submitedProperties['isTranslationOf'] }" style="width:200px; margin-left:5px; margin-right:5px;">
									<f:selectItems value="#{CircabcAddContentBean.pivotDocuments}"/>
								</h:selectOneMenu>
								
								<h:outputText value="#{cmsg.add_content_in } "></h:outputText>
								
								<h:selectOneMenu id="translation-language" value="#{CircabcAddContentBean.uploadedFiles[CircabcAddContentBean.uploadedFilesDataModel.rowIndex].submitedProperties['isPivot_mainLanguage'] }" style="width:200px; margin-left:5px; margin-right:5px;">
									<f:selectItems value="#{CircabcAddContentBean.filterLanguages}"/>
								</h:selectOneMenu>
								
							</h:panelGroup>
							<f:verbatim></div></f:verbatim>
						<f:verbatim></div></f:verbatim>
						
						<f:attribute name="width" value="85%"/>
					</h:column>
					
				</h:dataTable>

				<h:outputText id="no-files" value="#{cmsg.library_add_content_no_uploaded_files}" escape="false" rendered="#{CircabcAddContentBean.uploadedFileCount == 0}" styleClass="noItem" />
				<br/>	<br/>

						
				
		</h:form>




		</div>
	</div>
<%-- Content END --%>
</div>
<%@ include file="/jsp/extension/wai/parts/footer.jsp" %>
</circabc:view>
