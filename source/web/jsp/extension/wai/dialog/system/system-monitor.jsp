<%--+
    |     Copyright European Community 2011 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false"%>

<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<circabc:panel id="contentMainFormSystemMonitorUi" styleClass="contentMainForm">

	<p class="monitorText">
		<h:outputText value="#{cmsg.system_monitor_max_file_upload}"/>
		<h:inputText value="#{WaiDialogManager.bean.maxFileSize}" styleClass="monitorCounter"  />
	</p>
		
	<p class="monitorText">
		<h:outputText value="#{cmsg.system_monitor_import_max_file_upload}"/>
		<h:inputText value="#{WaiDialogManager.bean.importMaxFileSize}" styleClass="monitorCounter"  />
	</p>
	
	<p class="monitorText">
		<h:outputText value="#{cmsg.system_monitor_text1}" />
		<h:outputText value="#{WaiDialogManager.bean.userCount}" styleClass="monitorCounter"  />
		<h:outputText value="#{cmsg.system_monitor_text3}" />
	</p>
	
	<circabc:richList id="userList" viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" 
		value="#{WaiDialogManager.bean.users}" var="usr" pageSize="100">
		<circabc:column id="userListColUserName">
			<f:facet name="header">
				<h:outputText value="#{cmsg.system_monitor_user_list_col_username}" />
			</f:facet>
			<h:outputText value="#{usr.userName}"/>
		</circabc:column>
		<circabc:column id="userListColFullName">
			<f:facet name="header">
				<h:outputText value="#{cmsg.system_monitor_user_list_col_fullname}" />
			</f:facet>
			<h:outputText value="#{usr.fullName}"/>
		</circabc:column>
		<circabc:column id="userListColEmail">
			<f:facet name="header">
				<h:outputText value="#{cmsg.system_monitor_user_list_col_email}" />
			</f:facet>
			<h:outputText value="#{usr.email}"/>
		</circabc:column>
		<circabc:dataPager id="system-monitor-pager" styleClass="pagerCirca" />
	</circabc:richList>
	
</circabc:panel>
