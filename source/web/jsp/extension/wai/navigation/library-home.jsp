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

<circabc:panel id="contentMainLibraryHome" styleClass="contentMain">

	<%-- the description of the interest group --%>
	<f:verbatim><br /></f:verbatim>

	<circabc:displayer rendered="#{InterestGroupLogoBean.mainPageDisplay}">
		<h:graphicImage binding="#{InterestGroupLogoBean.mainPageIconBinding}" id="ig-home-logo" />
	</circabc:displayer>
	<h:outputText style="margin-left:5px; margin-right:5px;" binding="#{InterestGroupLogoBean.mainPageDescBinding}" id="ig-home-description" value="#{LibraryBean.currentNodeDescription}" escape="false" />

	<h:outputText styleClass="inpage_information" id="library_elements" value="#{LibraryBean.spaceNumberOfElements}" escape="false" />
	<f:verbatim><br /></f:verbatim>
<f:verbatim><br /></f:verbatim>

	
	<circabc:panel id="panelLibraryContainer" label="#{cmsg.library_panel_container_label}" styleClass="panelLibraryContainerGlobal" styleClassLabel="panelLibraryContainerLabel" tooltip="#{cmsg.library_panel_container_tooltip}">
		<circabc:customList id="libraryContainerList" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{LibraryBean.containers}" configuration="#{LibraryBean.containerNavigationPreference}" />
	</circabc:panel>

	<circabc:panel id="topOfPageAnchorLibHomeContainer" styleClass="topOfPageAnchor" rendered="#{NavigationBean.currentIGRoot.library != null}" >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorLibHomeContainer-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorLibHomeContainer-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>

	<circabc:panel id="panelLibraryContent" label="#{cmsg.library_panel_content_label}" styleClass="panelLibraryContentGlobal" styleClassLabel="panelLibraryContentLabel" tooltip="#{cmsg.library_panel_content_tooltip}">
		<circabc:customList id="libraryContentList" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{LibraryBean.contents}" configuration="#{LibraryBean.contentNavigationPreference}" />
	</circabc:panel>


	<circabc:panel id="topOfPageAnchorLibHomeContent" styleClass="topOfPageAnchor" rendered="#{NavigationBean.currentIGRoot.library != null}" >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorLibHomeContent-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorLibHomeContent-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>
	
	<%--<circabc:richList id="users-list-perm"
			viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt"
			value="#{LibraryBean.contents}" var="r" >
			
			<circabc:column id="manage-perm-col-authority" >
				<f:facet name="header">
					<circabc:sortLink id="manage-perm-sort-authority" label="#{msg.authority}" value="bestTitle" styleClass="header" tooltipAscending="#{cmsg.generic_sort_asc}" tooltipDescending="#{cmsg.generic_sort_desc}"/>
				</f:facet>
				<h:outputText id="manage-perm-val-authority" value="#{r.bestTitle}" />
			</circabc:column>
			
			<circabc:column id="manage-perm-col-authority2" >
				<h:outputText value="Bulk operation" />	
				<h:selectBooleanCheckbox id="checkbox" value="#{r.selected}" title="click it to select or deselect"/>
  			</circabc:column>
	</circabc:richList>--%>		
</circabc:panel>
<%--
<h:form>
	<h:commandButton value="Update" action="refresh" />
	<h:dataTable value="#{LibraryBean.bulkContents}" var="r">
		<h:column id="manage-perm-col-authority" >
			<h:outputText id="manage-perm-val-authority" value="#{r.bestTitle}" />
		</h:column>
		
		<h:column id="manage-perm-col-authority2" >
			<h:outputText value="Bulk operation" />	
			<h:selectBooleanCheckbox id="checkbox" value="#{r.selected}" title="click it to select or deselect"/>
 		</h:column>
	</h:dataTable>
</h:form>

<h:form>
	<h:outputText value="Currently Selected : #{LibraryBean.selectedItems}" />

	<h:dataTable var="item" value="#{LibraryBean.items}">
		<h:column id="yx54" >
			<h:outputText value="#{item}" />
			<h:selectBooleanCheckbox value="#{LibraryBean.checkMap[item]}"/>
		</h:column>
	</h:dataTable>

	<h:commandButton value="Update2" action="refresh" />
</h:form>--%>