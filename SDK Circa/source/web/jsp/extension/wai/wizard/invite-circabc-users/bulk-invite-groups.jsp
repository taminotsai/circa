<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a"%>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r"%>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>
<%@ taglib uri="/WEB-INF/tomahawk.tld" prefix="t"%>

<%@ page isELIgnored="false"%>

<%-- load a bundle of properties with I18N strings --%>
<f:loadBundle basename="alfresco.messages.webclient" var="msg" />
<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<div class="formArea">

	<h:panelGroup rendered="#{BulkInviteCircabcUsersWizard.ecasDepartmentNumberEnabled == false}">

	<h:outputText styleClass="signup_subrub_title" value="#{cmsg.bulk_invite_department_number_disabled_title }"></h:outputText>
	
	<h:outputText escape="false" value="<br/><br/>#{cmsg.bulk_invite_department_number_disabled }"></h:outputText>

	</h:panelGroup>
	
	<h:panelGroup rendered="#{BulkInviteCircabcUsersWizard.ecasDepartmentNumberEnabled == true}">

	<h:outputText styleClass="signup_subrub_title" value="#{cmsg.bulk_invite_department_number_title }"></h:outputText>

	</h:panelGroup>
	
</div>