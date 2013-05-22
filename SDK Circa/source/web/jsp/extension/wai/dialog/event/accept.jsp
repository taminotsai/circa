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

<circabc:panel id="acceptRecurMeetingDialogMainForm" styleClass="contentMainForm">

	<h:outputText value="#{cmsg.event_accept_recurrent_meeting_dialog_message} "/>

	<f:verbatim><br /></f:verbatim>

	<h:selectOneRadio value="#{WaiDialogManager.bean.occurenceChoice}" layout="pageDirection" >
		<f:selectItem itemValue="Single" itemLabel="#{cmsg.event_accept_recurrent_meeting_dialog_accept_this}" />
		<f:selectItem itemValue="FuturOccurences" itemLabel="#{cmsg.event_accept_recurrent_meeting_dialog_accept_furtur}" />
		<f:selectItem itemValue="AllOccurences" itemLabel="#{cmsg.event_accept_recurrent_meeting_dialog_accept_all}" />
	</h:selectOneRadio>

</circabc:panel>