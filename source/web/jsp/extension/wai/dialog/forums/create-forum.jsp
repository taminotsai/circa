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

<circabc:panel id="contentMainFormCreateForum" styleClass="contentMainForm">
	<circabc:panel id="panelCreateForum" label="#{cmsg.newsgroups_forum_create_forum_properties}" styleClass="panelCreateReplyGlobal" styleClassLabel="panelCreateReplyLabel" tooltip="#{cmsg.newsgroups_forum_create_forum_properties}">

		<h:panelGrid columns="3" cellpadding="3" cellspacing="3" border="0" >
			<%--  The name --%>
			<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
			<h:outputText value="#{msg.name}: " />
			<h:inputText id="forum_name" value="#{WaiDialogManager.bean.name}" maxlength="200" size="35" immediate="false"/>

			<%--  The description --%>
			<h:outputText value="" />
			<h:outputText value="#{msg.description}: "/>
			<h:inputTextarea id="forum_description" value="#{WaiDialogManager.bean.description}" rows="3" cols="55" readonly="false" />

			<%--  Moderate --%>
			<h:outputText value="" />
			<h:outputText value="#{cmsg.newsgroups_forum_create_forum_apply_moderation}: " />
			<h:selectBooleanCheckbox id="moderate" value="#{WaiDialogManager.bean.moderated}" />

		</h:panelGrid>


	</circabc:panel>
</circabc:panel>
