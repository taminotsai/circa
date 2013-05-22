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



<circabc:panel id="contentMainForm" styleClass="contentMainForm">
	<circabc:panel id="panelAddUrl" label="#{cmsg.library_add_url_properties}" styleClass="panelCreateReplyGlobal" styleClassLabel="panelCreateReplyLabel" tooltip="#{cmsg.library_add_url_description}">
		<h:panelGrid columns="3" cellpadding="3" cellspacing="3" border="0" >
			<%--  The name --%>
			<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
			<h:outputText value="#{msg.title}: "/>
			<h:inputText id="url_name" value="#{WaiDialogManager.bean.title}" maxlength="200" size="35" immediate="false"/>

			<%--  The URL --%>
			<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
			<h:outputText value="URL: " />
			<h:inputText id="url" value="#{WaiDialogManager.bean.url}" maxlength="200" size="35" immediate="false"/>
		</h:panelGrid>
	</circabc:panel>
</circabc:panel>