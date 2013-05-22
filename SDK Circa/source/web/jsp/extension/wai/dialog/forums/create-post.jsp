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

<circabc:panel id="contentMainForm" styleClass="contentMainForm">
	<circabc:panel id="panelCreatePost" label="#{cmsg.create_post_label}" styleClass="panelCreatePostGlobal" styleClassLabel="panelCreatePostLabel" tooltip="#{cmsg.create_post_label_tooltip}">
		<f:verbatim><br /></f:verbatim>
		<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
		<h:outputText value=" #{cmsg.create_post_message}:"/><br />
		<h:inputTextarea id="message" value="#{WaiDialogManager.bean.content}" rows="6" cols="70" />
		<f:verbatim><br /><br /></f:verbatim>
	</circabc:panel>
</circabc:panel>