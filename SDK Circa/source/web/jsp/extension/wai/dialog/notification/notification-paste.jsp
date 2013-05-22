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


<%@ page isELIgnored="false" %>

<f:loadBundle basename="alfresco.messages.webclient" var="msg" />
<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<circabc:panel id="contentMainNotificationOnCopyMove" styleClass="contentMainForm">
	<h:outputText id="notify-paste-all-text" value="#{cmsg.notify_paste_all}" styleClass="mainSubTitle"  />
	<h:selectBooleanCheckbox id="notify-paste-all" value="#{WaiDialogManager.bean.notifyPasteAll}" />
	<f:verbatim><br /></f:verbatim>
	<h:outputText id="notify-paste-text" value="#{cmsg.notify_paste}" styleClass="mainSubTitle" />
	<h:selectBooleanCheckbox id="notify-on-move" value="#{WaiDialogManager.bean.notifyPaste}" />
</circabc:panel>


