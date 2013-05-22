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
	<circabc:panel id="panelCreateReply" label="#{cmsg.create_reply_label}" styleClass="panelCreateReplyGlobal" styleClassLabel="panelCreateReplyLabel" tooltip="#{cmsg.create_reply_label_tooltip}">
		<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
		<h:outputText value=" #{cmsg.create_reply_message}:"/>
		<f:verbatim><br /></f:verbatim>
		<h:inputTextarea id="message" value="#{WaiDialogManager.bean.content}" rows="6" cols="70" />
		<f:verbatim><br /></f:verbatim>
		<f:verbatim><br /></f:verbatim>
		<h:outputText value=" #{cmsg.create_reply_posted}:"/>
		<f:verbatim><br /></f:verbatim>
		<h:inputTextarea id="previous" value="#{WaiDialogManager.bean.previous}" rows="6" cols="70" disabled="true" />
		<f:verbatim><br /><br /></f:verbatim>
	</circabc:panel>
</circabc:panel>
