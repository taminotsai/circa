<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a" %>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r" %>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>

<%@ page isELIgnored="false"%>

	<circabc:panel id="make-multilingual-panel-label" styleClass="signup_rub_title">
		<h:outputText value="#{cmsg.make_multilingual_panel_label}" escape="false" />
	</circabc:panel>

	<f:verbatim>
		<br />
	</f:verbatim>

	<h:panelGrid columns="3" cellpadding="3" cellspacing="3" border="0">
		<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
		<h:outputText value="&nbsp;#{msg.author}:&nbsp;&nbsp;" escape="false"/>
		<h:inputText id="author" value="#{DialogManager.bean.author}"  maxlength="1024" size="35" immediate="false"/>

		<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
		<h:outputText value="&nbsp;#{msg.language}:&nbsp;&nbsp;" escape="false"/>
		<h:selectOneMenu id="language" value="#{DialogManager.bean.language}" immediate="false" >
			<f:selectItem  itemLabel="#{msg.select_language}"  itemValue="null"/>
			<f:selectItems value="#{DialogManager.bean.filterLanguages}"/>
		</h:selectOneMenu>
	</h:panelGrid>

	<f:verbatim>
		<br />
	</f:verbatim>


	<circabc:panel id="make-multilingual-other-options" styleClass="signup_rub_title">
		<h:outputText value="#{cmsg.make_multilingual_other_options}" escape="false" />
	</circabc:panel>

	<f:verbatim>
		<br />
	</f:verbatim>


	<h:panelGrid columns="2" cellpadding="3" cellspacing="3" border="0">
		<h:selectBooleanCheckbox id="add_translation" value="#{DialogManager.bean.addTranslationAfter}" />
		<h:outputText value="#{cmsg.make_multilingual_add_trans_when_diag_close}" />
	</h:panelGrid>




