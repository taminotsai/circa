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

<circabc:panel id="contactInfoDialogMainForm" styleClass="contentMainForm">

	<%-- Include the the modify recurrence selection --%>
	<%@ include file="/jsp/extension/wai/dialog/event/modify-recurrence.jsp" %>

	<circabc:panel id="meeting-audience-contactInfo-section" styleClass="signup_rub_title">
		<h:outputText value="#{cmsg.event_create_meetings_wizard_step4_properties}" escape="false" />
	</circabc:panel>

	<h:panelGrid columns="3" cellpadding="3" cellspacing="3" border="0" >
		<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
		<h:outputText value="#{cmsg.event_create_meetings_wizard_step4_name}: "/>
		<h:inputText id="meeting-contact-name" value="#{WaiDialogManager.bean.appointment.name}" maxlength="1024" size="35" immediate="false"/>

		<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
		<h:outputText value="#{cmsg.event_create_meetings_wizard_step4_phone}: "/>
		<h:inputText id="meeting-contact-phone" value="#{WaiDialogManager.bean.appointment.phone}" maxlength="1024" size="35" immediate="false"/>

		<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
		<h:outputText value="#{cmsg.event_create_meetings_wizard_step4_email}: "/>
		<h:inputText id="meeting-contact-email" value="#{WaiDialogManager.bean.appointment.email}" maxlength="1024" size="35" immediate="false"/>

		<h:outputText value="" />
		<h:outputText value="#{cmsg.event_create_meetings_wizard_step4_url}: "/>
		<h:inputText id="meeting-contact-url" value="#{WaiDialogManager.bean.appointment.url}" maxlength="1024" size="35" immediate="false"/>
	</h:panelGrid>


</circabc:panel>
