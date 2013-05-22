<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |     http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a" %>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r" %>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>

<%@ page isELIgnored="false"%>

<circabc:panel id="versionned-properties-section1" styleClass="signup_rub_title" tooltip="#{cmsg.document_details_panel_detail_tooltip}">
	<h:outputText value="#{cmsg.properties}" escape="false" />
</circabc:panel>

<f:verbatim>
	<br />
</f:verbatim>

<circabc:panel id="ml-det-padd-list" styleClass="pad8px" >
	<circabc:panel id="contentHeaderSubPanelDocdet">
   		<circabc:panel id="contentHeaderIconDocdet" styleClass="contentHeaderIcon" >
      		<circabc:actionLink id="doc-logo1" value="#{DialogManager.bean.name}" tooltip="#{DialogManager.bean.name}" href="#{DialogManager.bean.url}" target="new" image="#{DialogManager.bean.fileType32}" showLink="false" />
   		</circabc:panel>
   		<circabc:panel id="contentHeaderTextDocdet">
      		<circabc:propertySheetGrid id="document-props" value="#{DialogManager.bean.frozenStateDocument}" var="documentProps" columns="1"
                   mode="view"  labelStyleClass="propertiesLabelTiny" cellpadding="2" cellspacing="2" externalConfig="true" />
   		</circabc:panel>
   </circabc:panel>
</circabc:panel>
