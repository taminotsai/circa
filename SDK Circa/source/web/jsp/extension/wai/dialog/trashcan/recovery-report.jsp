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



<circabc:panel id="contentMainFormManageDeletedItems"
	styleClass="contentMainForm">

<table cellpadding="2" cellspacing="2" border="0" width="100%">
<tr>
<td class="mainSubTitle">
<h:outputText value="#{msg.recovery_report_success}" />
</td>
</tr>
<tr>
<td>
<h:outputText value="#{CircabcTrashcanRecoveryReportDialog.successItemsTable}" escape="false" />
</td>
</tr>
<%-- show this panel if some items failed to recover --%>
<a:panel id="failure-panel" rendered="#{CircabcTrashcanDialogProperty.failureItemsCount != 0}">
<tr><td class="paddingRow"></td></tr>
<tr>
<td class="mainSubTitle">
<h:outputText value="#{msg.recovery_report_failed}" />
</td>
</tr>
<tr>
<td>
<h:outputText value="#{CircabcTrashcanRecoveryReportDialog.failureItemsTable}" escape="false" />
</td>
</tr>
</a:panel>
<tr><td class="paddingRow"></td></tr>
<tr>
<td>
<%-- Error Messages --%>
<%-- messages tag to show messages not handled by other specific message tags --%>
<h:messages globalOnly="true" styleClass="errorMessage" layout="table" />
<td>
</tr>
</table>
</circabc:panel>
