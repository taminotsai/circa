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

<%@ page isELIgnored="false"%>

<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<circabc:panel id="contentMainGlobalStatisticsWai" styleClass="contentMainForm" >

		<h:outputText value="#{ cmsg.global_statistics_dialog_list_of_files}"></h:outputText>
		<h:commandButton value="Run global statistics" action="wai:dialog:GlobalStatisticsDialog" actionListener="#{GlobalStatisticsDialog.fireme }" ></h:commandButton>
		<f:verbatim><br/></f:verbatim>
		<h:outputText value="#{ cmsg.global_statistics_dialog_list_of_files}"></h:outputText>
		<h:commandButton value="Force cleaning" action="wai:dialog:GlobalStatisticsDialog" actionListener="#{GlobalStatisticsDialog.fireme2 }" ></h:commandButton>
		<f:verbatim><br/></f:verbatim><f:verbatim><br/></f:verbatim>
		<h:outputText value="#{ cmsg.global_statistics_dialog_search_user_last_login}"></h:outputText>
		<h:commandButton value="Search" action="wai:dialog:GlobalStatisticsDialog" actionListener="#{GlobalStatisticsDialog.searchLastLogin }"></h:commandButton>
		<f:verbatim><br/></f:verbatim>
		<h:outputText value="#{ cmsg.global_statistics_dialog_search_userid}"></h:outputText>
		<h:inputText  value="#{GlobalStatisticsDialog.userid}"></h:inputText>
		<f:verbatim><br/></f:verbatim>
		<h:outputText value="#{ cmsg.global_statistics_dialog_search_result}"></h:outputText>
		<h:outputText value="#{GlobalStatisticsDialog.result}" style="font-size:120%; font-weight:bold;"></h:outputText>
		<f:verbatim><br/></f:verbatim><f:verbatim><br/></f:verbatim>
		
		<circabc:panel id="panelContainer" label="#{cmsg.library_panel_content_label}" styleClass="panelLibraryContainerGlobal" styleClassLabel="panelLibraryContainerLabel" tooltip="#{cmsg.library_panel_container_tooltip}">
			
			<h:dataTable id="reports" styleClass="recordSet" value="#{GlobalStatisticsDialog.reports}" var="wn" >
				<%-- Primary column for details view mode --%>
				<h:column id="content-title">
					<f:facet name="header">
						<h:outputText id="ig-home-wnew-col-name-content-title" value="#{cmsg.title}" escape="false" />
					</f:facet>
					<circabc:actionLink id="ig-home-wnew-content-icon"  value="#{wn.name}" href="#{wn.downloadUrl}" target="new" tooltip="#{cmsg.igroot_home_whats_new_link_tooltip}"/>
				</h:column>
				
				<h:column id="content-size">
					<f:facet name="header">
						<h:outputText id="ig-home-wnew-col-name-content-size" value="#{cmsg.size}" escape="false" />
					</f:facet>
					<h:outputText id="size" value="#{wn.sizeAsString}"/>
					
				</h:column>
				
				<h:column id="content-modified">
					<f:facet name="header">
						<h:outputText id="ig-home-wnew-col-name-content-modified" value="#{cmsg.modified}" escape="false" />
					</f:facet>
					<h:outputText id="modified" value="#{wn.fileInfo.modifiedDate}"/>
					
				</h:column>
				
				<%-- component to display if the list is empty - normally not seen --%>
				<f:facet name="empty">
					<h:outputFormat id="no-list-items-whatsnews" value="#{cmsg.no_list_items}" escape="false" />
				</f:facet>
			</h:dataTable>
		</circabc:panel>
	


</circabc:panel>