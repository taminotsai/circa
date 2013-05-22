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

<circabc:panel id="contentMainForm" styleClass="contentMainForm">
	<%-- Properties details --%>
	<circabc:panel id="panelMMDPropertiesDetails" label="#{cmsg.manage_multilingual_details_properties}" tooltip="#{cmsg.manage_multilingual_details_properties_tooltip}" styleClass="panelMMDPropertiesDetailsGlobal" styleClassLabel="panelMMDPropertiesDetailsLabel">
          	<circabc:propertySheetGrid id="ml-container-props-sheet" value="#{WaiDialogManager.bean.documentMlContainer}"
				var="mlContainerProps" columns="1" labelStyleClass="propertiesLabelTiny"
				externalConfig="true" cellpadding="2" cellspacing="2" mode="view"/>
	</circabc:panel>

    <circabc:panel id="topOfPageAnchorMLPropDetails" styleClass="topOfPageAnchor"  >
        <%-- Display the "back to top icon first and display the text after." --%>
        <circabc:actionLink id="act-link-prop-det-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
        <circabc:actionLink id="act-link-prop-det-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
    </circabc:panel>

	<%-- Translation details --%>
	<circabc:panel id="panelMMDTranslationDetails" label="#{cmsg.manage_multilingual_details_translations}" tooltip="#{cmsg.manage_multilingual_details_translations_tooltip}" styleClass="panelDocumentDetailsGlobal" styleClassLabel="panelDocumentDetailsLabel">
		<%-- list of translations --%>
		<circabc:richList id="TranslationList" viewMode="circa" value="#{WaiDialogManager.bean.translations}" var="r" styleClass="recordSet" headerStyleClass="recordSetHeader"	rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" initialSortColumn="name" initialSortDescending="false">

			<%-- Name and icon columns --%>
			<circabc:column>
				<f:facet name="small-icon">
					<h:graphicImage url="/images/filetypes/_default.gif" width="16" height="16" alt=" "/>
				</f:facet>
				<f:facet name="header">
					<circabc:sortLink label="#{msg.name}" value="name" mode="case-insensitive" styleClass="header" tooltipAscending="${tooltipAscending}" tooltipDescending="${tooltipDescending}"/>
				</f:facet>
				<circabc:actionLink id="view-name" value="#{r.name}" tooltip="#{r.name}" href="#{r.versionUrl}" target="new" />
			</circabc:column>

			<%-- Language columns --%>
			<circabc:column>
				<f:facet name="header">
					<circabc:sortLink label="#{msg.language}" value="language" mode="case-insensitive" styleClass="header" tooltipAscending="${tooltipAscending}" tooltipDescending="${tooltipDescending}"/>
				</f:facet>
				<circabc:actionLink id="view-language" value="#{r.versionLanguage != null? r.versionLanguage : '-'}" tooltip="#{r.versionLanguage}" href="#{r.versionUrl}" target="new" />
			</circabc:column>

			<%-- view actions --%>
			<circabc:column id="col25" >
				<f:facet name="header">
					<h:outputText value="#{msg.actions}"/>
				</f:facet>
				<%-- detail basic --%>
				<circabc:actionLink id="view-link" value="#{msg.view}" tooltip="#{msg.view}" href="#{r.versionUrl}" target="new" />
			</circabc:column>

			<circabc:dataPager styleClass="pager" />

		</circabc:richList>
	</circabc:panel>

    <circabc:panel id="topOfPageAnchorLast" styleClass="topOfPageAnchor"  >
        <%-- Display the "back to top icon first and display the text after." --%>
        <circabc:actionLink id="act-link-ver-hist-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
        <circabc:actionLink id="act-link-ver-hist-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
    </circabc:panel>
</circabc:panel>

