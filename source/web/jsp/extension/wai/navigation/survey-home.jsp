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

<circabc:panel id="contentMainSurveyHome" styleClass="contentMain">

	<circabc:panel id="panelSurveysContainer" label="#{cmsg.surveys_panel_container_label}"  tooltip="#{cmsg.surveys_panel_container_label_tooltip}" styleClass="panelSurveysContainerGlobal" styleClassLabel="panelSurveysContainerLabel">
		<circabc:richList id="surveysContainerList" viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{SurveyHomeBean.containerNodes}" var="s" initialSortColumn="name" pageSize="#{SurveyHomeBean.listPageSize}">
			<circabc:column>
				<f:facet name="header">
					<circabc:sortLink label="#{cmsg.surveys_container_name}" value="name" tooltipAscending="#{cmsg.surveys_container_sort_asc}" tooltipDescending="#{cmsg.surveys_container_sort_desc}"/>
				</f:facet>
				<f:facet name="small-icon">
					<circabc:actionLink value="#{s.name}" image="/images/icons/#{s.smallIcon}.gif" actionListener="#{BrowseBean.clickWai}" showLink="false" tooltip="#{cmsg.surveys_container_icon_tooltip}">
						<circabc:param name="id" value="#{s.id}" />
					</circabc:actionLink>
				</f:facet>
				<circabc:actionLink value="#{s.name}" actionListener="#{BrowseBean.clickWai}" tooltip="#{cmsg.surveys_container_icon_tooltip} #{s.name}">
					<circabc:param name="id" value="#{s.id}" />
				</circabc:actionLink>
			</circabc:column>
			<circabc:column>
				<f:facet name="header">
					<h:outputText value="#{cmsg.surveys_container_description}" escape="false" />
				</f:facet>
				<h:outputText value="#{s.description}" escape="false" />
			</circabc:column>
			<circabc:column>
				<f:facet name="header">
					<h:outputText value="#{cmsg.surveys_container_created}" escape="false" />
				</f:facet>
				<h:outputText value="#{s.created}" escape="false">
					<a:convertXMLDate type="both" pattern="#{msg.date_time_pattern}" />
				</h:outputText>
			</circabc:column>
			<circabc:column>
				<f:facet name="header">
					<h:outputText value="#{cmsg.surveys_container_modified}" escape="false" />
				</f:facet>
				<h:outputText value="#{s.modified}" escape="false">
					<a:convertXMLDate type="both" pattern="#{msg.date_time_pattern}" />
				</h:outputText>
			</circabc:column>
			<circabc:column>
				<f:facet name="header">
					<h:outputText value="#{cmsg.surveys_container_action}" escape="false" />
				</f:facet>
				<circabc:actions id="surveysContainerActions" value="space_browse_surveys_wai" context="#{s}" showLink="false" />
			</circabc:column>
			<f:facet name="empty">
				<h:outputFormat id="no-list-items-surveys-container" value="#{cmsg.survey_no_item}" escape="false" />
			</f:facet>
			<circabc:dataPager id="pagerSurveysContainer" styleClass="pagerCirca" />
		</circabc:richList>
	</circabc:panel>

	<circabc:panel id="topOfPageAnchorSurveyFirst" styleClass="topOfPageAnchor"  >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorSurveyFirst-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorSurveyFirst-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>

	<circabc:panel id="panelSurveysReal" label="#{cmsg.surveys_panel_real_label}"  tooltip="#{cmsg.surveys_panel_real_label_tooltip}" styleClass="panelSurveysRealGlobal" styleClassLabel="panelSurveysRealLabel">
		<circabc:richList id="surveysRealList" viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{SurveyHomeBean.surveys}" var="r" initialSortColumn="name" pageSize="#{SurveyHomeBean.listPageSize}">
			<circabc:column>
				<f:facet name="header">
					<circabc:sortLink label="#{cmsg.survey_questionnaire}" value="name" tooltipAscending="#{cmsg.surveys_container_sort_asc}" tooltipDescending="#{cmsg.surveys_container_sort_desc}"/>
				</f:facet>
				<f:facet name="small-icon">
					<h:graphicImage title="#{r.name}" url="/images/filetypes/_default.gif" />
				</f:facet>
				<h:outputText value="#{r.name}" />
			</circabc:column>
			<circabc:column>
				<f:facet name="header">
					<h:outputText value="#{cmsg.survey_subject}" escape="false" />
				</f:facet>
				<h:outputText value="#{r.subject}" />
			</circabc:column>
			<circabc:column>
				<f:facet name="header">
					<h:outputText value="#{cmsg.survey_status}" escape="false" />
				</f:facet>
				<h:outputText value="#{r.status}" />
			</circabc:column>
			<circabc:column>
				<f:facet name="header">
					<h:outputText value="#{cmsg.survey_start_date}" escape="false" />
				</f:facet>
				<h:outputText value="#{r.startDate}" escape="false">
					<a:convertXMLDate type="both" pattern="#{msg.date_time_pattern}" />
				</h:outputText>
			</circabc:column>
			<circabc:column>
				<f:facet name="header">
					<h:outputText value="#{cmsg.survey_close_date}" escape="false" />
				</f:facet>
				<h:outputText value="#{r.closeDate}" escape="false">
					<a:convertXMLDate type="both" pattern="#{msg.date_time_pattern}" />
				</h:outputText>
			</circabc:column>
			<circabc:column>
				<f:facet name="header">
					<h:outputText value="#{cmsg.survey_translations}" escape="false" />
				</f:facet>
				<circabc:surveyLangs value="#{r}" wai="true" />
			</circabc:column>

			<f:facet name="empty">
				<h:outputFormat id="no-list-items-surveys-real" value="#{cmsg.survey_no_survey}" escape="false" />
			</f:facet>
			<circabc:dataPager id="pagerSurveysReal" styleClass="pagerCirca" />
		</circabc:richList>
	</circabc:panel>

	<circabc:panel id="topOfPageAnchorSurveyLast" styleClass="topOfPageAnchor">
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorSurveyLast-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorSurveyLast-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>
</circabc:panel>

