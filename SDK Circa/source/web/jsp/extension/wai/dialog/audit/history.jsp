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

<f:verbatim>
	<br />
</f:verbatim>

<circabc:panel id="history-panel-label" styleClass="signup_rub_title" tooltip="#{cmsg.history_description}">
	<h:outputText value="#{cmsg.history}" escape="false" />
</circabc:panel>

<f:verbatim>
	<br />
</f:verbatim>

<%-- Audit list --%>
<a:richList id="auditRichList" viewMode="details"
	pageSize="#{BrowseBean.listElementNumber}" styleClass="recordSet"
	headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow"
	altRowStyleClass="recordSetRowAlt" width="100%"
	value="#{DialogManager.bean.auditList}" var="r"
	rendered="#{DialogManager.bean.auditList != null}"
	initialSortColumn="logDate" initialSortDescending="true">

	<%-- Component to display if the list is empty --%>
	<f:facet name="empty">
		<h:outputText
			value="<i>#{cmsg.no_audit_entry_found}</i><br /><br /><br />"
			escape="false" />
	</f:facet>
	<%-- Timestamp column --%>
	<a:column id="audit-date"
		style="text-align:left; white-space:nowrap; padding-right:5px">
		<f:facet name="header">
			<h:outputText id="audit-date-label" value="#{msg.date}"
				styleClass="header" />
		</f:facet>
		<h:outputText id="audit-date-txt" value="#{r.logDate}">
			<a:convertXMLDate type="both" pattern="#{msg.date_time_pattern}" />
		</h:outputText>
	</a:column>

	<%-- User name column --%>
	<a:column id="audit-username"
		style="text-align:left; padding-right:5px">
		<f:facet name="header">
			<h:outputText id="audit-username-label" value="#{msg.username}"
				styleClass="header" />
		</f:facet>
		<h:outputText id="audit-username-txt" value="#{r.userName}" />
	</a:column>

	<%-- Service column --%>
	<a:column id="audit-service"
		style="text-align:left; padding-right:5px">
		<f:facet name="header">
			<h:outputText id="audit-service-label" value="#{cmsg.service}"
				styleClass="header" />
		</f:facet>
		<h:outputText id="audit-service-txt" value="#{r.serviceDescription}" />
	</a:column>

	<%-- Activity column --%>
	<a:column id="audit-method"
		style="text-align:left; padding-right:5px">
		<f:facet name="header">
			<h:outputText id="audit-method-label" value="#{cmsg.activity}"
				styleClass="header" />
		</f:facet>
		<h:outputText id="audit-method-txt" value="#{r.activityDescription}" />
	</a:column>

	<%-- Failed column --%>
	<a:column id="audit-status"
		style="text-align:left; padding-right:5px">
		<f:facet name="header">
			<h:outputText id="audit-status-label" value="#{msg.status}"
				styleClass="header" />
		</f:facet>
		<h:outputText id="audit-status-txt" value="#{r.status}" />

	</a:column>
	<circabc:dataPager id="history-pager" styleClass="pagerCirca" />
</a:richList>
