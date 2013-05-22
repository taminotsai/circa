<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>

<%@ page buffer="32kb" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<circabc:panel id="contentMainFormCustomiseCategoryNodeDetails"
	styleClass="contentMainForm">

	
		<circabc:panel id="panel_form_customisation">
		<br/>
		<h:outputFormat id="panel_form_customisation_title" value="#{cmsg.category_customise_dialog_manage_look_and_feel_title}" escape="false" styleClass="panel_header_little" >		</h:outputFormat>
		<br/>
		<h:outputLabel for="banner_customisation_title" value="#{cmsg.category_customise_dialog_manage_navigation_list_title}" ></h:outputLabel>
		<h:selectOneRadio id="banner_customisation_title" value="#{WaiDialogManager.bean.selectedRenderChoice}"> <!--  -->
			<f:selectItems value="#{WaiDialogManager.bean.selectRenderChoices}"/>
		</h:selectOneRadio>
		<h:outputText value="#{cmsg.category_customise_dialog_manage_navigation_list_description }"></h:outputText>
		<br/><br/>
		</circabc:panel>
		


</circabc:panel>