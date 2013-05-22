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



<circabc:panel id="contentMainCircabcHomeMembership"
	styleClass="contentMain" rendered="#{WelcomeBean.registered == true}">

	<circabc:panel id="panelWelcome"
	label="#{cmsg.browse_circabc_categories}"
	styleClass="panelWelcomeGlobal" styleClassLabel="panelWelcomeLabel">
	<circabc:richList id="spacesList" viewMode="circa"
		styleClass="recordSet" headerStyleClass="recordSetHeader"
		rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt"
		value="#{WelcomeBean.categoryHeaders}" var="r"
		initialSortDescending="false" initialSortColumn="name">
		<%-- Primary column for details view mode --%>
		<circabc:column id="col1" primary="true">
			<f:facet name="header">
				<h:outputText value="#{cmsg.category_name}" escape="false" />
			</f:facet>
			<circabc:actionLink value="#{r.name}" tooltip="#{r.description}"
				actionListener="#{BrowseBean.clickWai}" anchor="#{r.id}"
				onclick="showWaitProgress();">
				<circabc:param name="id" value="#{r.id}" />
			</circabc:actionLink>
		</circabc:column>

		<%-- component to display if the list is empty --%>
		<f:facet name="empty">
			<h:outputFormat id="no-list-items" value="#{cmsg.no_list_items}"
				escape="false" />
		</f:facet>
	</circabc:richList>
</circabc:panel>

	<circabc:panel id="contentMainCircabcHomeMembershipList"
		label="#{cmsg.view_user_membership_header}"
		styleClass="panelWelcomeGlobal" styleClassLabel="panelWelcomeLabel">

		<f:verbatim>
			<br />
		</f:verbatim>


		<circabc:richList id="categoryRoles" viewMode="circa"
			styleClass="recordSet" headerStyleClass="recordSetHeader"
			rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt"
			value="#{WelcomeBean.categoryRoles}" var="u">
			<%-- category column --%>
			<circabc:column id="category-category">
				<f:facet name="header">
					<h:outputText id="category-category-header"
						value="#{cmsg.category_column_header}" styleClass="header" />
				</f:facet>
				<circabc:actionLink value="#{u.category}" tooltip="#{u.category}"
					actionListener="#{BrowseBean.clickWai}"
					onclick="showWaitProgress();">
					<circabc:param name="id" value="#{u.categoryNodeId}" />
				</circabc:actionLink>
			</circabc:column>
			<circabc:column id="category-profile">
				<f:facet name="header">
					<h:outputText id="category-profile-header"
						value="#{cmsg.profile_column_header}" styleClass="header"/>
				</f:facet>
				<h:outputText id="category-profile-text" value="#{u.profile}" />
			</circabc:column>
			<f:facet name="empty">
				<h:outputFormat id="no-items-category-results"
					value="#{cmsg.view_user_membership_no_list_items_category}" />
			</f:facet>
		</circabc:richList>

		<circabc:richList id="igRoles" viewMode="circa" styleClass="recordSet"
			headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow"
			altRowStyleClass="recordSetRowAlt" value="#{WelcomeBean.igRoles}"
			var="u">
			<circabc:column id="interest-group-column">
				<f:facet name="header">
					<h:outputText id="interest-group-column-header"
						value="#{cmsg.interest_group_column_header}" styleClass="header" />
				</f:facet>
				<h:outputText id="interest-group-text" value="" />
				<circabc:actionLink value="#{u.interestGroupTitle}"
					tooltip="#{u.interestGroupTitle}"
					actionListener="#{BrowseBean.clickWai}"
					onclick="showWaitProgress();">
					<circabc:param name="id" value="#{u.interestGroupNodeId}" />
				</circabc:actionLink>
			</circabc:column>
			<circabc:column id="profile-column">
				<f:facet name="header">
					<h:outputText id="profile-column-header"
						value="#{cmsg.profile_column_header}" styleClass="header" />
				</f:facet>
				<h:outputText id="profile-text" value="#{u.profileTitle}"  />
			</circabc:column>
			<f:facet name="empty">
				<h:outputFormat id="no-items-interest-group-results"
					value="#{cmsg.view_user_membership_no_list_items_ig}" />
			</f:facet>
		</circabc:richList>
	</circabc:panel>
</circabc:panel>

<circabc:panel id="contentMainCircabcHomeText" styleClass="contentMain"
	rendered="#{WelcomeBean.registered == false}">
	<f:verbatim>
		<p>
	</f:verbatim>
	<h:outputText value="#{cmsg.welcome_text_1}<br />" escape="false" />
	<h:outputText value="#{cmsg.welcome_text_2}<br />" escape="false" />
	<h:outputText value="#{cmsg.welcome_text_3}<br />" escape="false" />
	<f:verbatim>
		</p>
	</f:verbatim>

	<h:outputText value="#{cmsg.welcome_text_4}" escape="false" />

	<f:verbatim>
		<ul>
	</f:verbatim>
	<h:outputText value="<li>#{cmsg.welcome_text_5}</li>" escape="false" />
	<h:outputText value="<li>#{cmsg.welcome_text_6}</li>" escape="false" />
	<h:outputText value="<li>#{cmsg.welcome_text_7}</li>" escape="false" />
	<h:outputText value="<li>#{cmsg.welcome_text_8}</li>" escape="false" />
	<f:verbatim>
		</ul>
	</f:verbatim>

	<f:verbatim>
		<p>
	</f:verbatim>
	<circabc:actionLink value="#{cmsg.welcome_text_9}"
		tooltip="#{cmsg.welcome_text_9}"
		actionListener="#{BrowseBean.clickWai}"
		rendered="#{NavigationBean.circabcHomeNode.rootCategoryHeader != null}"
		onclick="showWaitProgress();">
		<circabc:param name="id"
			value="#{NavigationBean.circabcHomeNode.rootCategoryHeader.id}" />
	</circabc:actionLink>
	<f:verbatim>
		</p>
	</f:verbatim>

	<h:outputText value="#{cmsg.welcome_text_10}&nbsp;" escape="false" />
	<circabc:actionLink value="#{cmsg.welcome_help}&nbsp;"
		href="/faces/jsp/extension/wai/help.jsp?page=help_toc.html"
		tooltip="#{cmsg.welcome_help_tooltip}" immediate="true"
		target="_blank" />
	<h:outputText value="#{cmsg.welcome_text_11}&nbsp;" escape="false" />
	<circabc:actionLink value="#{cmsg.welcome_user_guide}"
		tooltip="#{cmsg.welcome_user_guide_tooltip}"
		href="/docs/help/en/User_Guide.pdf" target="_blank" />
	<h:outputText value="<br/>#{cmsg.welcome_text_12}" escape="false" />
	<circabc:actionLink value="#{cmsg.welcome_help_link_problem}"
		tooltip="#{cmsg.welcome_help_link_problem_tooltip}"
		href="/faces/jsp/extension/wai/help.jsp?page=help_carrefour_09.html#Link_From_Other_Application"
		target="_blank" />


	<f:verbatim>
		<br />
	</f:verbatim>
	<f:verbatim>
		<br />
	</f:verbatim>
</circabc:panel>



<circabc:panel id="topOfPageAnchorRootHome" styleClass="topOfPageAnchor">
	<%-- Display the "back to top icon first and display the text after." --%>
	<circabc:actionLink id="topOfPageAnchorRootHome-1"
		value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}"
		href="#top" styleClass="topOfPageAnchor" showLink="true" />
	<circabc:actionLink id="topOfPageAnchorRootHome-2"
		value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top"
		styleClass="topOfPageAnchor" showLink="false"
		image="#{currentContextPath}/images/extension/top_ns.gif" />
</circabc:panel>

