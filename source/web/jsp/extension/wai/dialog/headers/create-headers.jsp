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

<circabc:panel id="contentMainFormCreateCatHeaders" styleClass="contentMainForm">
	<circabc:panel id="panelCreateCatHeaders" label="#{cmsg.create_cat_header_dialog_cat_propeties}" styleClass="panelCreateReplyGlobal" styleClassLabel="panelCreateReplyLabel" tooltip="#{cmsg.create_cat_header_dialog_cat_propeties}">

		<h:panelGrid columns="3" cellpadding="3" cellspacing="3" border="0" >
			<%--  The name --%>
			<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
			<h:outputText value="#{msg.name}: " />
			<h:inputText id="cat_header_name" value="#{WaiDialogManager.bean.name}" maxlength="200" size="35" immediate="false"/>

			<%--  The description --%>
			<h:outputText value="" />
			<h:outputText value="#{msg.description}: "/>
			<h:inputTextarea id="cat_header_description" value="#{WaiDialogManager.bean.description}" rows="3" cols="55" readonly="false" />
		</h:panelGrid>

	</circabc:panel>
</circabc:panel>
