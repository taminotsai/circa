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

<circabc:panel id="contentMainLibraryDossier" styleClass="contentMain">
	<circabc:panel id="panelLibraryContainer" label="#{cmsg.library_panel_container_label}" styleClass="panelLibraryContainerGlobal" styleClassLabel="panelLibraryContainerLabel" tooltip="#{cmsg.library_panel_container_tooltip}">
		<circabc:customList id="libraryDossierContainerList" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{DossierBean.containers}" configuration="#{DossierBean.containerNavigationPreference}" />
	</circabc:panel>

	<circabc:panel id="topOfPageAnchorLibDossierContainer" styleClass="topOfPageAnchor" rendered="#{NavigationBean.currentIGRoot.library != null}" >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorLibDossierContainer-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorLibDossierContainer-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>

	<circabc:panel id="panelLibraryContent" label="#{cmsg.library_panel_content_label}" styleClass="panelLibraryContentGlobal" styleClassLabel="panelLibraryContentLabel" tooltip="#{cmsg.library_panel_content_tooltip}">
		<circabc:customList id="libraryDossierContentList"  styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{DossierBean.contents}" configuration="#{DossierBean.contentNavigationPreference}" />
	</circabc:panel>

	<circabc:panel id="topOfPageAnchorLibDossierContent" styleClass="topOfPageAnchor" rendered="#{NavigationBean.currentIGRoot.library != null}" >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorLibDossierContent-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorLibDossierContent-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>

</circabc:panel>
