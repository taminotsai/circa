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

<circabc:panel id="declineRecurMeetingDialogMainForm" styleClass="contentFullPage">

	<circabc:displayer rendered="#{WaiDialogManager.bean.recurrent}">
	<
	</circabc:displayer>

	<h:outputText value="#{cmsg.event_decline_recurrent_meeting_dialog_message} "/>

	<f:verbatim><br /></f:verbatim>

	<h:selectOneRadio value="#{WaiDialogManager.bean.occurenceChoice}" layout="pageDirection" >
		<f:selectItem itemValue="Single" itemLabel="#{cmsg.event_decline_recurrent_meeting_dialog_decline_this}" />
		<f:selectItem itemValue="FuturOccurences" itemLabel="#{cmsg.event_decline_recurrent_meeting_dialog_decline_furtur}" />
		<f:selectItem itemValue="AllOccurences" itemLabel="#{cmsg.event_decline_recurrent_meeting_dialog_decline_all}" />
	</h:selectOneRadio>

</circabc:panel>