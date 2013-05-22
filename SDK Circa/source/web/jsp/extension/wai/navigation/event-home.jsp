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

<%@ taglib uri="http://comin.cz/tag/lib/jscalendar" prefix="jsc"%>

<%@ page isELIgnored="false"%>

<script language="javascript">
    function updateCalendar(){
        document.getElementById("FormPrincipal:apply").click();
    }
</script>


<circabc:panel id="contentMainEventHome" styleClass="contentMain">

		<%-- css For jscalendar popup --%>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/extension/jscalendar-1.0/calendar-blue.css" type="text/css" />
		<%-- css For jsf calendar --%>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/extension/calendartag/bigcalendar.css" type="text/css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/extension/calendartag/calendar.css" type="text/css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/extension/calendartag/minicalendar.css" type="text/css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/extension/calendartag/verticlecalendar.css" type="text/css" />

		<circabc:panel id="centerCalendar" styleClass="applicationFormCenter">

				<h:inputText id="date" value="#{EventBean.startDateAsString}"  title="#{cmsg.event_home_input_date_title}" size="8" />
 				<f:verbatim>&nbsp;</f:verbatim>
 			    <jsc:jscalendar for="FormPrincipal:date"
							showsTime="false"
							ifFormat="%d-%m-%Y"
							locale="#{EventBean.userLang}"
							icon="#{EventBean.popupIcon}"
 			    			title="#{cmsg.event_home_popup_button_title}"/>
				<f:verbatim>&nbsp;</f:verbatim>
				<h:selectOneMenu id="camendar-view-mode-language" value="#{EventBean.viewMode}" title="#{cmsg.event_home_input_mode_title}" >
					<f:selectItems value="#{EventBean.viewModes}"/>
				</h:selectOneMenu>
				<f:verbatim>&nbsp;</f:verbatim>
				<h:commandButton id="apply" actionListener="#{BrowseBean.clickWai}" value="#{cmsg.event_home_popup_action_apply}" title="#{cmsg.event_home_popup_action_title}" onclick="showWaitProgress();">
					<f:param name="id" value="#{NavigationBean.currentNode.id}" />
					<f:param name="browseDate" value="#{EventBean.startDateAsString}" />
					<f:param name="viewMode" value="#{EventBean.viewMode}" />
				</h:commandButton>


			<circabc:calendar date="#{EventBean.startDate}" weekStart="#{EventBean.weekStart}" decorator="eu.cec.digit.circabc.web.wai.bean.navigation.event.EventServiceDecorator" showPreviousNextLinks="true" beyond="true" actionListener="#{BrowseBean.clickWai}" viewMode="#{EventBean.viewMode}"  />
		</circabc:panel>

		<%-- Workaround to avoid a css conflict --%>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/extension/d-commission.css" type="text/css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/extension/circabc.css" type="text/css" />
	</circabc:panel>

