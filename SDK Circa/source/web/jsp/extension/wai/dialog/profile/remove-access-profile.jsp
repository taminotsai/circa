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
<%-- load a bundle of properties with I18N strings --%>
<f:loadBundle basename="alfresco.messages.webclient" var="msg" />
<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<r:permissionEvaluator value="#{DialogManager.bean.actionNode}" allow="DirAdmin">

<circabc:panel id="contentMainFormDeleteAccProfileDetails" styleClass="contentMainForm">

	<f:verbatim><br /></f:verbatim>
	<h:outputText id="delete-acc-prof-confirmation" value="#{WaiDialogManager.bean.confirmMessage}" styleClass="mainSubTitle" />

</circabc:panel>

</r:permissionEvaluator>



