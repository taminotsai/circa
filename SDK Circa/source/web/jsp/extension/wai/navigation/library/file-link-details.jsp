<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a"%>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r"%>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>

<%@ page isELIgnored="false"%>

<circabc:panel id="contentMainContentDetails" styleClass="contentMain">

    <%-- the right menu --%>
    <circabc:panel id="fileLinkContentMainButton" styleClass="contentMainButton">
        <%--  The close button --%>
        <circabc:panel id="fileLinkDivButtonDialog" styleClass="divButtonDialog">
            <h:commandButton id="close-button" styleClass="dialogButton" value="#{cmsg.close}" action="wai:browse-wai:close" />
        </circabc:panel>
        <f:verbatim><br /><br /><br /></f:verbatim>
        <%--  The action list --%>
		<circabc:panel id="fileLink-id-divspacer10px" styleClass="divspacer10px" />
        <circabc:panel id="fileLinkPanelActions" label="#{cmsg.actions}" tooltip="#{cmsg.actions_tooltip}" styleClass="panelActionsGlobal" styleClassLabel="panelActionsLabel" >
            <circabc:actions id="actions_doc" value="filelink_details_actions_wai" context="#{LibFileLinkDetailsBean.document}" vertical="true"/>
        </circabc:panel>
    </circabc:panel>

    <%-- the main content --%>
    <circabc:panel id="fileLinkcontentMainForm" styleClass="contentMainForm">

        <%-- Display the properties  --%>
        <circabc:panel id="fileLinkPanelDocumentDetails" label="#{msg.properties}" styleClass="panelDocumentDetailsGlobal" styleClassLabel="panelDocumentDetailsLabel" tooltip="#{cmsg.document_details_panel_detail_tooltip}" >
        	<circabc:panel id="fileLink-det-padd-list" styleClass="pad8px" />
            <circabc:panel id="fileLinkcontentHeaderSubPanelDocdet">
                <circabc:panel id="cfileLinkontentHeaderIconDocdet" styleClass="contentHeaderIcon" >
                    <circabc:actionLink id="fileLinkdoc-logo1" value="#{LibFileLinkDetailsBean.document.name}" tooltip="#{cmsg.library_content_link_tooltip}: #{LibFileLinkDetailsBean.document.name}" href="#{LibFileLinkDetailsBean.document.properties.url}" target="new" image="#{LibFileLinkDetailsBean.document.properties.fileType32}" showLink="false" />
                </circabc:panel>
                <circabc:panel id="fileLinkcontentHeaderTextDocdet">
                    <circabc:propertySheetGrid id="fileLink-document-props" value="#{LibFileLinkDetailsBean.document}" var="documentProps" columns="1"
                            mode="view"  labelStyleClass="propertiesLabelTiny" cellpadding="2" cellspacing="2" externalConfig="true" />
                    <h:message id="fileLinkmsg1" for="fileLink-document-props" styleClass="statusMessage" />
                </circabc:panel>
            </circabc:panel>
        </circabc:panel>

        <circabc:panel id="topOfPageAnchorFileLinkDetails" styleClass="topOfPageAnchor"  >
            <%-- Display the "back to top icon first and display the text after." --%>
            <circabc:actionLink id="act-link-fileLink-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
            <circabc:actionLink id="act-link-fileLink-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
        </circabc:panel>

       	<%-- Include the notification status panel --%>
		<%@ include file="/jsp/extension/wai/dialog/notification/notification-status-panel.jsp" %>

	</circabc:panel>

</circabc:panel>

