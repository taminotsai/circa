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
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/noscript.js" ></script>
<circabc:panel id="selectSpaceDialogMainForm" styleClass="contentMainForm">

	<%-- Include the the modify recurrence selection --%>
	<%@ include file="/jsp/extension/wai/dialog/event/modify-recurrence.jsp" %>

	<circabc:panel id="select-space-meeting-wz-section" styleClass="signup_rub_title">
		<h:outputText value="#{cmsg.event_create_meetings_wizard_step2_details}" escape="false" />
	</circabc:panel>
	<f:verbatim><br /></f:verbatim>
	<circabc:nodeSelector id="space-selector"
				rootNode="#{WaiDialogManager.bean.libraryId}"
				initialSelection="#{WaiDialogManager.bean.libraryId}"
				label="#{msg.select_destination_prompt}"
				value="#{WaiDialogManager.bean.appointment.librarySection}"
				pathLabel="#{cmsg.path_label}"
				pathErrorMessage="#{cmsg.path_error_message}"
				styleClass="selector" />

</circabc:panel>
