<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>
<%@ page buffer="32kb" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false"%>

<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<circabc:panel id="contentMainFormMoveIgDetails" styleClass="contentMainForm">
	<div style="margin:10px;">
		<p>
			<h:outputText value="#{cmsg.move_ig_dialog_lbl_source}" escape="false" />
		</p>
		<div style="float:left;margin-right:10px;">
			<h:outputText value="#{cmsg.move_ig_dialog_lbl_source_header}" escape="false" /><br/>
			<h:selectOneMenu id="selectionOfHeader" value="#{WaiDialogManager.bean.sourceHeader}" 
							 style="width:200px;" onchange="submit()">
				<f:selectItems value="#{WaiDialogManager.bean.allHeaders}" />
			</h:selectOneMenu>
		</div>
		<div style="float:left;margin-right:10px;">
			<h:outputText value="#{cmsg.move_ig_dialog_lbl_source_category}" escape="false" /><br/>
			<h:selectOneMenu id="selectionOfCategory" value="#{WaiDialogManager.bean.sourceCategory}" 
							 style="width:200px;" onchange="submit()">
				<f:selectItems value="#{WaiDialogManager.bean.sourceCategories}" />
			</h:selectOneMenu>
		</div>
		<div style="float:left">
			<h:outputText value="#{cmsg.move_ig_dialog_lbl_source_ig}" escape="false" /><br/>
			<h:selectOneMenu id="selectionOfIg" value="#{WaiDialogManager.bean.sourceIg}"
							 style="width:200px;">
				<f:selectItems value="#{WaiDialogManager.bean.sourceIgs}" />
			</h:selectOneMenu>
		</div>
		<div style="clear:both;"></div>
	</div>
	
	<div style="margin:20px 10px;">
		<p>
			<h:outputText value="#{cmsg.move_ig_dialog_lbl_target}" escape="false" />
		</p>
		<div style="float:left;margin-right:10px;">
			<h:outputText value="#{cmsg.move_ig_dialog_lbl_target_header}" escape="false" /><br/>
			<h:selectOneMenu id="selectionOfTargetHeader" value="#{WaiDialogManager.bean.targetHeader}" 
							 style="width:305px;" onchange="submit()">
				<f:selectItems value="#{WaiDialogManager.bean.allHeaders}" />
			</h:selectOneMenu>
		</div>
		<div style="float:left">
			<h:outputText value="#{cmsg.move_ig_dialog_lbl_target_category}" escape="false" /><br/>
			<h:selectOneMenu id="selectionOfTargetCategory" value="#{WaiDialogManager.bean.targetCategory}" 
							 style="width:305px">
				<f:selectItems value="#{WaiDialogManager.bean.targetCategories}" />
			</h:selectOneMenu>
		</div>
		<div style="clear:both;"></div>
	</div>
	
</circabc:panel>

<circabc:panel id="panelMembersExport" label="#{cmsg.members_home_export_label}" tooltip="#{cmsg.members_home_export_label}" styleClass="panelMembersSearchGlobal" styleClassLabel="panelMembersSearchLabel">
		<f:verbatim><br /></f:verbatim>
	
		<h:outputText id="move-ig-space1" value="&nbsp;&nbsp;" escape="false"/>
		<h:commandButton id="directory-home-export-button" action="#{WaiDialogManager.bean.export}" value="#{cmsg.members_home_export}" />
		<f:verbatim><br /><br /></f:verbatim>
	</circabc:panel>