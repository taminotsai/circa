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
<%@ page buffer="32kb" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false"%>

<circabc:panel id="contentMainForm" styleClass="contentMainFormWithBorder">
		<f:verbatim><br /></f:verbatim>
			<h:outputFormat value="#{cmsg.delete_user_text}">
				<circabc:param value="#{WaiDialogManager.bean.personInfo}" />
			</h:outputFormat>
		<f:verbatim><br /><br /></f:verbatim>
</circabc:panel>
<circabc:panel id="panelInterestGroup" label="#{cmsg.delete_user_interest_group_header}" tooltip="#{cmsg.delete_user_interest_group_header_tooltip}" styleClass="panelMembersResultsGlobal" styleClassLabel="panelMembersResultsLabel">
	<circabc:richList id="igRoles" viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{WaiDialogManager.bean.igRoles}" var="u"  pageSize="10">
		<%-- category column --%>
		<circabc:column id="category-column">
			<f:facet name="header">
		         <h:outputText id="category-column-header" value="#{cmsg.category_column_header}" styleClass="header"/>
			</f:facet>
			<h:outputText id="category-text" value="#{u.category}"/>
		</circabc:column>
		<circabc:column id="interest-group-column">
			<f:facet name="header">
		         <h:outputText id="interest-group-column-header" value="#{cmsg.interest_group_column_header}" styleClass="header"/>
			</f:facet>
			<h:outputText id="interest-group-text" value="#{u.interestGroup}"/>
		</circabc:column>
		<circabc:column id="profile-column">
			<f:facet name="header">
		         <h:outputText id="profile-column-header" value="#{cmsg.profile_column_header}" styleClass="header"/>
			</f:facet>
			<h:outputText id="profile-text" value="#{u.profile}"/>
		</circabc:column>
		<f:facet name="empty">
			<h:outputFormat id="no-items-interest-group-results" value="#{cmsg.delete_user_interest_group_no_list_items}" />
		</f:facet>
		<circabc:dataPager id="pagerInterestGroupResults" styleClass="pagerCirca" />
	</circabc:richList>
</circabc:panel>
<circabc:panel id="panelCategory" label="#{cmsg.delete_user_category_header}" tooltip="#{cmsg.delete_user_category_header_tooltip}" styleClass="panelMembersResultsGlobal" styleClassLabel="panelMembersResultsLabel">
	<circabc:richList id="idCategoryRoles" viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{WaiDialogManager.bean.categoryRoles}" var="u"  pageSize="10">
		<%-- category column --%>
		<circabc:column id="category-category-column">
			<f:facet name="header">
		         <h:outputText id="category-category-column-header" value="#{cmsg.category_column_header}" styleClass="header"/>
			</f:facet>
			<h:outputText id="category-category-text" value="#{u.category}"/>
		</circabc:column>
		<circabc:column id="category-profile-column">
			<f:facet name="header">
		         <h:outputText id="category-profile-column-header" value="#{cmsg.profile_column_header}" styleClass="header"/>
			</f:facet>
			<h:outputText id="category-profile-text" value="#{u.profile}"/>
		</circabc:column>
		<f:facet name="empty">
			<h:outputFormat id="no-items-category-results" value="#{cmsg.delete_user_category_no_list_items}" />
		</f:facet>
		<circabc:dataPager id="pagerCategoryResults" styleClass="pagerCirca" />
	</circabc:richList>
</circabc:panel>


