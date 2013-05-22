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

<circabc:panel id="deleteAppointmentDialogMainForm" styleClass="contentMainForm">

	<f:verbatim><br /></f:verbatim>
	<h:outputText value="#{WaiDialogManager.bean.confirmation} "/>

	<f:verbatim><br /></f:verbatim>

	<h:selectOneRadio value="#{WaiDialogManager.bean.occurenceChoice}" rendered="#{WaiDialogManager.bean.recurrent}" layout="pageDirection" >
		<f:selectItem itemValue="Single" itemLabel="#{cmsg.event_delete_meeting_dialog_confirmation_del_this}" />
		<f:selectItem itemValue="FuturOccurences" itemLabel="#{cmsg.event_delete_meeting_dialog_confirmation_del_futur}" />
		<f:selectItem itemValue="AllOccurences" itemLabel="#{cmsg.event_delete_meeting_dialog_confirmation_del_series}" />
	</h:selectOneRadio>

	<f:verbatim><br /><i></f:verbatim>
		<h:outputText value="#{WaiDialogManager.bean.mailInformation}"/>
	<f:verbatim><i></f:verbatim>

</circabc:panel>