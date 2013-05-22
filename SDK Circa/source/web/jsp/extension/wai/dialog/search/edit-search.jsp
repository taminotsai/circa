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

<circabc:panel id="contentMainSaveSearch" styleClass="contentMainForm">

	<circabc:panel id="save-search--warning" styleClass="infoPanel" styleClassLabel="infoContent" rendered="true" >
		<h:graphicImage id="save-search-image-warning" value="/images/icons/warning.gif" title="#{cmsg.message_warn_tooltip}" alt="#{cmsg.message_warn_tooltip}"  />
		<h:outputText id="save-search-text-warning-spaces" value="&nbsp;&nbsp;" escape="false" />
	</circabc:panel>

	<h:panelGrid columns="3" cellpadding="3" cellspacing="5" border="0"  >
			<h:graphicImage value="/images/icons/required_field.gif" alt="#{msg.required_field}" />
			<h:outputText value="#{msg.name}:&nbsp;" escape="false"/>
			<h:inputText id="name" value="#{CircabcSearchProperties.searchName}" size="35" maxlength="1024"/>

			<h:outputText value=" " />
			<h:outputText value="#{msg.description}:&nbsp;" escape="false"/>
			<h:inputText value="#{CircabcSearchProperties.searchDescription}"size="35" maxlength="1024" />
			
			<h:selectBooleanCheckbox value="#{CircabcSearchProperties.searchSaveGlobal}" disabled="true" />
			<h:outputText value="#{msg.save_search_global}" />
	</h:panelGrid>
</circabc:panel>

