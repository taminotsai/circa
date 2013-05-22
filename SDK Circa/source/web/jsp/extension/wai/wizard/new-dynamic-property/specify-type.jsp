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

<%-- load a bundle of properties with I18N strings --%>
<f:loadBundle basename="alfresco.messages.webclient" var="msg"/>
<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<h:outputText value="1.&nbsp;#{cmsg.new_dynamic_property_section_1}"  escape="false"/>
<f:verbatim>
	<br /><br />
</f:verbatim>
<h:selectOneMenu id="PropertyType" value="#{WizardManager.bean.type}">
	<f:selectItems value="#{WizardManager.bean.types}" />
</h:selectOneMenu>
<f:verbatim>
	<br /><br />
</f:verbatim>
<h:outputText value="2.&nbsp;#{cmsg.new_dynamic_property_section_2}" escape="false" />
<f:verbatim>
	<br /><br />
</f:verbatim>

<h:panelGrid columns="2" cellpadding="3" cellspacing="3" border="0">
	<h:inputTextarea  id="PropertyValue" value="#{WizardManager.bean.validValues}"rows="5" cols="50" />
	<h:outputText value="#{cmsg.new_dynamic_property_section_separetd_value}" escape="false" style="font-style: italic;"/>
</h:panelGrid>
