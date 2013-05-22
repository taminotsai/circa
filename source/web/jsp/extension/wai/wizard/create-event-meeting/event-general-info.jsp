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

<circabc:panel id="eventInfoWizardMainForm" styleClass="contentFullPage">

		<circabc:panel id="event-info-wz-section" styleClass="signup_rub_title">
			<h:outputText value="#{cmsg.event_create_event_wizard_step1_sectionTitle}" escape="false" />
		</circabc:panel>

		<h:panelGrid columns="3" cellpadding="3" cellspacing="3" border="0" >
			<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
			<h:outputText value="#{cmsg.event_create_meetings_wizard_step1_language}: "/>
			<h:selectOneMenu id="event-language" value="#{WaiWizardManager.bean.appointment.language}">
				<f:selectItems value="#{WaiWizardManager.bean.languages}"/>
			</h:selectOneMenu>

			<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
			<h:outputText value="#{cmsg.event_create_meetings_wizard_step1_meeting_title}: "/>
			<h:inputText id="event-title" value="#{WaiWizardManager.bean.appointment.title}" maxlength="1024" size="35" immediate="false"/>

			<h:outputText value="" />
			<h:outputText value="#{cmsg.event_create_meetings_wizard_step1_abstract}: "/>
			<h:inputTextarea id="event-abstract" value="#{WaiWizardManager.bean.appointment.eventAbstract}" rows="3" cols="40" readonly="false"/>

			<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
			<h:outputText value="#{cmsg.event_create_event_wizard_step1_type}: "/>
			<h:selectOneMenu id="event-type" value="#{WaiWizardManager.bean.appointment.eventType}" converter="eu.cec.digit.circabc.faces.EnumConverter">
				<f:selectItems value="#{WaiWizardManager.bean.eventTypes}"/>
			</h:selectOneMenu>

			<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
			<h:outputText value="#{cmsg.event_create_event_wizard_step1_priority}: "/>
			<h:selectOneMenu id="event-priority" value="#{WaiWizardManager.bean.appointment.priority}" converter="eu.cec.digit.circabc.faces.EnumConverter">
				<f:selectItems value="#{WaiWizardManager.bean.priorities}"/>
			</h:selectOneMenu>

			<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
			<h:outputText value="#{cmsg.event_create_meetings_wizard_step1_date}: "/>
			<circabc:inputDatePicker id="event-date" value="#{WaiWizardManager.bean.appointment.startDateAsDate}" yearCount="#{DatePickerGenerator.yearCount}" startYear="#{CircabcDatePickerGenerator.thisYear}" initialiseIfNull="true" />

			<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
			<h:outputText value="#{cmsg.event_create_meetings_wizard_step1_occurs}: "/>
			<h:panelGrid columns="2" cellpadding="3" cellspacing="3" border="0" >

				<h:selectOneRadio id="event-main-occurence" layout="pageDirection" value="#{WaiWizardManager.bean.appointment.occurenceRate.mainOccurence}" converter="eu.cec.digit.circabc.faces.EnumConverter">
					<f:selectItems value="#{WaiWizardManager.bean.mainOccurences}"/>
				</h:selectOneRadio>
				<circabc:panel id="occurence-specification">
					<h:outputText value="#{cmsg.event_create_meetings_wizard_step1_occurs_once}" />
					<f:verbatim><br /></f:verbatim>

					<h:selectOneMenu id="event-time-occurence" value="#{WaiWizardManager.bean.appointment.occurenceRate.timesOccurence}" converter="eu.cec.digit.circabc.faces.EnumConverter">
						<f:selectItems value="#{WaiWizardManager.bean.timesOccurences}"/>
					</h:selectOneMenu>
					<h:outputText value="&nbsp;#{cmsg.event_create_meetings_wizard_step1_occurs_for}&nbsp;" escape="false"/>
					<h:inputText id="event-times-one" size="3" value="#{WaiWizardManager.bean.timesOneAsString}" immediate="false" />
					<h:outputText value="&nbsp;#{cmsg.event_create_meetings_wizard_step1_occurs_times}" escape="false"/>
					<f:verbatim><br /></f:verbatim>

					<h:outputText value="#{cmsg.event_create_meetings_wizard_step1_occurs_every}&nbsp;" escape="false"/>
					<h:inputText  id="event-every" size="3" value="#{WaiWizardManager.bean.everyAsString}" immediate="false" />
					<h:outputText value="&nbsp;" escape="false"/>
					<h:selectOneMenu id="event-every-time-occurence" value="#{WaiWizardManager.bean.appointment.occurenceRate.everyTimesOccurence}" converter="eu.cec.digit.circabc.faces.EnumConverter">
						<f:selectItems value="#{WaiWizardManager.bean.everyTimesOccurences}"/>
					</h:selectOneMenu>
					<h:outputText value="&nbsp;#{cmsg.event_create_meetings_wizard_step1_occurs_for}&nbsp;" escape="false"/>
					<h:inputText id="event-times-two" size="3" value="#{WaiWizardManager.bean.timesTwoAsString}" immediate="false" />
					<h:outputText value="&nbsp;#{cmsg.event_create_meetings_wizard_step1_occurs_times}" escape="false"/>
				</circabc:panel>
			</h:panelGrid>

			<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
			<h:outputText value="#{cmsg.event_create_meetings_wizard_step1_start_time}: "/>
			<circabc:inputDatePicker id="event-start-date" showTime="true" showDate="false" timeAsList="true" value="#{WaiWizardManager.bean.appointment.startTimeAsDate}" startYear="#{CircabcDatePickerGenerator.thisYear}" initialiseIfNull="true" />

			<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
			<h:outputText value="#{cmsg.event_create_meetings_wizard_step1_end_time}: "/>
			<circabc:inputDatePicker id="event-end-date" showTime="true" showDate="false" timeAsList="true" value="#{WaiWizardManager.bean.appointment.endTimeAsDate}" startYear="#{CircabcDatePickerGenerator.thisYear}" initialiseIfNull="true" />


			<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
			<h:outputText value="#{cmsg.event_create_meetings_wizard_step1_time_zone}: "/>
			<h:selectOneMenu id="event-timezone" value="#{WaiWizardManager.bean.appointment.timeZoneId}"  >
				<f:selectItems value="#{WaiWizardManager.bean.timeZones}"/>
			</h:selectOneMenu>

			<h:outputText value="" />
			<h:outputText value="#{cmsg.event_create_meetings_wizard_step1_location}: "/>
			<h:inputText id="event-location" value="#{WaiWizardManager.bean.appointment.location}" maxlength="1024" size="40" immediate="false"/>

			<h:outputText value="" />
			<h:outputText value="#{cmsg.event_create_meetings_wizard_step1_message}: "/>
			<h:inputTextarea id="event-message" value="#{WaiWizardManager.bean.appointment.invitationMessage}" rows="3" cols="40" readonly="false"/>

		</h:panelGrid>
</circabc:panel>
