<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a" %>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r" %>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>

<circabc:panel id="add-empty-translation-section1" styleClass="signup_rub_title" tooltip="#{cmsg.add_translation_label_tooltip}">
	<h:outputText value="#{cmsg.manage_multilingual_details_properties}" escape="false" />
</circabc:panel>

<f:verbatim>
	<br />
</f:verbatim>

<h:panelGrid columns="3" cellpadding="3" cellspacing="3" border="0" >
	<h:outputText value="" />
	<h:outputText value="#{msg.title}: " />
	<h:inputText id="translation_title" value="#{CircabcAddTranslationWithoutContentDialog.title}" maxlength="1024" size="35" immediate="false"/>

	<h:outputText value="" />
	<h:outputText value="#{msg.description}: "/>
	<h:inputTextarea id="translation_description" value="#{CircabcAddTranslationWithoutContentDialog.description}" rows="3" cols="55" readonly="false"/>

	<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
	<h:outputText value="#{msg.author}: "/>
	<h:inputText id="translation_author" value="#{CircabcAddTranslationWithoutContentDialog.author}" maxlength="1024" size="35" immediate="false"/>

	<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
	<h:outputText value="#{msg.language}: "/>
	<h:selectOneMenu id="translation_language" value="#{CircabcAddTranslationWithoutContentDialog.language}">
		<f:selectItem  itemLabel="#{msg.select_language}" itemValue="null"/>
		<f:selectItems value="#{CircabcAddTranslationWithoutContentDialog.unusedLanguages}"/>
	</h:selectOneMenu>
</h:panelGrid>

<f:verbatim>
	<br /><br />
</f:verbatim>

<circabc:panel id="add-empty-translation-section2" styleClass="signup_rub_title" tooltip="#{cmsg.add_translation_label_tooltip}">
	<h:outputText value="#{msg.other_properties}" escape="false" />
</circabc:panel>

<h:panelGrid columns="2" cellpadding="3" cellspacing="3" border="0">
		<h:selectBooleanCheckbox id="check_modify" value="#{CircabcAddTranslationWithoutContentDialog.showOtherProperties}" />
		<h:outputText id="translation_modify" value="#{msg.modify_props_when_page_closes}"/><br /><br />
</h:panelGrid>