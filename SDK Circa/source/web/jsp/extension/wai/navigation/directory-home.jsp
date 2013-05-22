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

<circabc:panel id="contentMainDirectoryHome" styleClass="contentMain">

	<f:verbatim><br /></f:verbatim>
	<p>
		<h:outputText id="directory-home-intro" escape="false" value="#{cmsg.members_home_text_1}"/>
	</p>
	<f:verbatim><br /><br /></f:verbatim>

	<circabc:panel id="panelMembersSearch" label="#{cmsg.members_home_search_label}" tooltip="#{cmsg.members_home_search_label_tooltip}" styleClass="panelMembersSearchGlobal" styleClassLabel="panelMembersSearchLabel">
		<f:verbatim><br /></f:verbatim>
		<h:outputText id="directory-home-name-label" value="#{cmsg.members_home_name}&nbsp;" escape="false" />
		<h:inputText  id="directory-home-name" value="#{DirectoryBean.name}" />
		<h:outputText id="directory-home-txt-page-size" value="&nbsp;&nbsp;#{cmsg.members_home_results_per_page}&nbsp;" escape="false" />
		<h:selectOneMenu id="directory-home-sel-page-size" value="#{DirectoryBean.pageSize}">
			<f:selectItems value="#{DirectoryBean.pageSizes}" />
		</h:selectOneMenu>
		<f:verbatim><br /><br /></f:verbatim>
		<h:outputText id="directory-home-txt-filterby" value="#{cmsg.members_home_filter_by}&nbsp;" escape="false" />
		<h:selectOneMenu id="directory-home-sel-member" value="#{DirectoryBean.member}" rendered="#{DirectoryBean.advanced}" onchange="checkProfileFilter();">
			<f:selectItem id="directory-home-item-member" itemValue="members" itemLabel="#{cmsg.members_home_members_only}" />
			<f:selectItem id="directory-home-item-all-member" itemValue="allcircabcuser" itemLabel="#{cmsg.members_home_all_members}" />
		</h:selectOneMenu>			
		<h:outputText id="directory-home-space" value="&nbsp;" escape="false"/>
		<h:selectOneMenu id="directory-home-sel-domain" value="#{DirectoryBean.domain}">
			<f:selectItems value="#{DirectoryBean.filters}" />
		</h:selectOneMenu>
		<h:outputText value="&nbsp;" escape="false"/>
		<h:selectOneMenu id="directory-home-sel-profile" value="#{DirectoryBean.profile}" >
			<f:selectItem id="directory-home-item-empty" itemValue="" itemLabel="#{cmsg.members_home_disable_profile_filter}" />
			<f:selectItems value="#{DirectoryBean.profiles}" />
		</h:selectOneMenu>
		<h:outputText value="&nbsp;" escape="false"/>
		<h:outputText id="directory-home-space2" value="&nbsp;" escape="false"/>
		<h:commandButton id="directory-home-search-action" action="#{DirectoryBean.search}" value="#{cmsg.members_home_search}" />
		<f:verbatim><br /><br /></f:verbatim>		
	</circabc:panel>
	
	<f:verbatim><br /></f:verbatim>

	<circabc:displayer id="displayer-dir-home-noguest" rendered="#{NavigationBean.isGuest == false}">
		<circabc:panel id="panelMembersResultsNoguest" label="#{cmsg.members_home_results_label}" tooltip="#{cmsg.members_home_results_label_tooltip}" styleClass="panelMembersResultsGlobal" styleClassLabel="panelMembersResultsLabel">
			<circabc:richList id="membersResultsListNoguest" viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{DirectoryBean.users}" var="u" initialSortColumn="lastName" pageSize="#{DirectoryBean.pageSize}">
				<circabc:column id="directory-home-noguest-col-name-surname">
					<f:facet name="header">
						<circabc:sortLink id="directory-home-noguest-col-surname" label="#{cmsg.members_home_surname}" value="lastName" tooltipAscending="#{cmsg.members_home_sort_asc}" tooltipDescending="#{cmsg.members_home_sort_desc}"/>
					</f:facet>
					<circabc:actionLink id="directory-home-noguest-lastname" value="#{u.lastName}" action="wai:dialog:viewUserDetailsWai" actionListener="#{WaiDialogManager.setupParameters}" tooltip="#{cmsg.members_home_view_details_tooltip}">
						<circabc:param name="id" value="#{u.id}" />
					</circabc:actionLink>
				</circabc:column>
				<circabc:column id="directory-home-noguest-col-first">
					<f:facet name="header">
						<h:outputText id="directory-home-noguest-col-name-first" value="#{cmsg.members_home_firstname}" />
					</f:facet>
					<circabc:actionLink id="directory-home-noguest-first" value="#{u.firstName}" action="wai:dialog:viewUserDetailsWai" actionListener="#{WaiDialogManager.setupParameters}" tooltip="#{cmsg.members_home_view_details_tooltip}">
						<circabc:param name="id" value="#{u.id}" />
					</circabc:actionLink>
				</circabc:column>
				<circabc:column id="directory-home-noguest-col-profile">
					<f:facet name="header">
						<h:outputText id="directory-home-noguest-col-name-profile" value="#{cmsg.members_home_access_profile}" />
					</f:facet>
					<h:outputText id="directory-home-noguest-profile" value="#{u.profile}" />
				</circabc:column>
				<circabc:column id="directory-home-noguest-col-actions" >
					<f:facet name="header">
						<h:outputText id="directory-home-noguest-col-name-actions" value="#{cmsg.members_home_action}" />
					</f:facet>
					<circabc:actions id="membersResultsViewActionsNoguest" value="members_results_view_wai" context="#{u}" showLink="false" />
				</circabc:column>
				<f:facet name="empty">
					<h:outputFormat id="no-list-items-members-results" value="#{cmsg.members_home_no_list_items}" />
				</f:facet>
				<circabc:dataPager id="pagerMembersResults" styleClass="pagerCirca" />
			</circabc:richList>
		</circabc:panel>
	</circabc:displayer>

	<circabc:displayer id="displayer-dir-home-guest" rendered="#{NavigationBean.isGuest == true}">
		<circabc:panel id="panelMembersResultsGuest" label="#{cmsg.members_home_results_label}" tooltip="#{cmsg.members_home_results_label_tooltip}" styleClass="panelMembersResultsGlobal" styleClassLabel="panelMembersResultsLabel">
			<circabc:richList id="membersResultsListGuest" viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{DirectoryBean.users}" var="u" initialSortColumn="name" pageSize="#{DirectoryBean.pageSize}">
				<circabc:column id="directory-home-guest-col-surname">
					<f:facet name="header">
						<circabc:sortLink id="directory-home-guest-col-name-surname" label="#{cmsg.members_home_surname}" value="surname" tooltipAscending="#{cmsg.members_home_sort_asc}" tooltipDescending="#{cmsg.members_home_sort_desc}"/>
					</f:facet>
					<h:outputText id="directory-home-guest-last" value="#{u.lastName}" />
				</circabc:column>
				<circabc:column id="directory-home-guest-col-first">
					<f:facet name="header">
						<h:outputText id="directory-home-guest-col-name-first" value="#{cmsg.members_home_firstname}" />
					</f:facet>
					<h:outputText id="directory-home-guest-first" value="#{u.firstName}" />
				</circabc:column>
				<circabc:column id="directory-home-guest-col-profile">
					<f:facet name="header">
						<h:outputText id="directory-home-guest-col-name-profile" value="#{cmsg.members_home_access_profile}" />
					</f:facet>
					<h:outputText id="directory-home-guest-profile" value="#{u.profile}" />
				</circabc:column>
				<f:facet name="empty">
					<h:outputFormat id="no-list-items-members-results-guest" value="#{cmsg.members_home_no_list_items}" />
				</f:facet>
				<circabc:dataPager id="pagerMembersResults-Guest" styleClass="pagerCirca" />
			</circabc:richList>
		</circabc:panel>
	</circabc:displayer>
	
	<f:verbatim><br /></f:verbatim>	
	
	<circabc:panel id="panelMembersExport" label="#{cmsg.members_home_export_label}" tooltip="#{cmsg.members_home_export_label}" styleClass="panelMembersSearchGlobal" styleClassLabel="panelMembersSearchLabel">
		<f:verbatim><br /></f:verbatim>
		<h:selectOneMenu id="directory-home-export-type" value="#{DirectoryBean.exportType}" immediate="true"  >
			<f:selectItems id="directory-home-export-type-options" value="#{DirectoryBean.exportTypes}" />
		</h:selectOneMenu>
		<h:outputText id="directory-home-space3" value="&nbsp;&nbsp;" escape="false"/>
		<h:commandButton id="directory-home-export-button" action="#{DirectoryBean.export}" value="#{cmsg.members_home_export}" />
		<f:verbatim><br /><br /></f:verbatim>
	</circabc:panel>

	<circabc:panel id="topOfPageAnchorDirHome" styleClass="topOfPageAnchor" rendered="#{NavigationBean.currentIGRoot.library != null}" >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorDirHome-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorDirHome-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>

	<circabc:displayer id="displayer-dir-home-advanced" rendered="#{DirectoryBean.advanced}">
		<%-- We have the right because it's an admin and administrative tasks require javacript --%>
		<script type="text/javascript">
			function checkProfileFilter()
			{
				if (document.getElementById("FormPrincipal:directory-home-sel-member").value == "allcircabcuser" )
				{
					document.getElementById("FormPrincipal:directory-home-sel-profile").disabled = true;
				}
				else
				{
					document.getElementById("FormPrincipal:directory-home-sel-profile").disabled = false;
				}
			}
		</script>
	</circabc:displayer>

</circabc:panel>
