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

<%@ page buffer="32kb" contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false"%>

<%-- load a bundle of properties with I18N strings --%>
<f:loadBundle basename="alfresco.messages.webclient" var="msg" />
<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<circabc:panel id="contentMainForm" styleClass="contentMainForm">

	<circabc:panel id="confirmationPanel" styleClass="descIntroText" >
		<h:outputText value="#{cmsg.delete_category_confirmation_admin}" rendered="#{DeleteCategoryDialogBean.deleteCategoryNode}" />
		<h:outputText value="#{cmsg.delete_category_confirmation}" rendered="#{not empty DeleteCategoryDialogBean.nodesForDeletion}"/>
		<h:outputText value="#{cmsg.delete_category_nothing}" rendered="#{empty DeleteCategoryDialogBean.nodesForDeletion}"/>
	</circabc:panel>
	
	<circabc:panel id="confirmationListPanel" rendered="#{!DeleteCategoryDialogBean.deleteCategoryNode}">
		<circabc:richList id="confirmationList" 
			viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" 
			value="#{DeleteCategoryDialogBean.nodesForDeletion}" var="node" initialSortColumn="name"
			rendered="#{not empty DeleteCategoryDialogBean.nodesForDeletion}">
			
			<circabc:column>
				<f:facet name="header">
					<h:outputText value="#{msg.name}" />
				</f:facet>
				<h:outputText value="#{node.name}" />
			</circabc:column>
			
			<circabc:column>
				<f:facet name="header">
					<h:outputText value="#{msg.path}" />
				</f:facet>
				<h:outputText value="#{node.path}" />
			</circabc:column>
			
			<circabc:column>
				<f:facet name="header">
					<h:outputText value="#{msg.author}" />
				</f:facet>
				<h:outputText value="#{node.author}" />
			</circabc:column>
			
			<circabc:dataPager id="deleteCategory-pager" styleClass="pagerCirca" />
		</circabc:richList>
	</circabc:panel>
</circabc:panel>