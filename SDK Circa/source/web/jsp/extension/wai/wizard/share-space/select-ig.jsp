<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a" %>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r" %>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>

<%@ page isELIgnored="false"%>

<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />
<f:loadBundle basename="alfresco.messages.webclient" var="msg" />

<h:panelGrid columns="2" cellpadding="3" cellspacing="3" border="0">
	
	<h:outputLabel value="#{cmsg.select_ig_col_ig}:" styleClass="propertiesLabelTiny"  />
	<h:selectOneMenu id="InterestGroups" value="#{WizardManager.bean.interestGroup}" >
		<f:selectItems value="#{WizardManager.bean.availableInterestGroups}" />
	</h:selectOneMenu>
	
	<h:outputLabel value="#{cmsg.select_ig_col_perm}:" styleClass="propertiesLabelTiny"  />
	<h:selectOneMenu id="MaximumPermision" value="#{WizardManager.bean.libraryPermission}" >
		<f:selectItems value="#{WizardManager.bean.orderedLibraryPermissions}" />
	</h:selectOneMenu>	
	
</h:panelGrid>


