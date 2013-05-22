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


<%@ page isELIgnored="false" %>
<circabc:panel id="contentMainFormRemoveIG" styleClass="contentMainForm">

		<h:outputText id="remove-ig-confirmation" value="#{cmsg.remove_ig_dialog_confirmation}:" styleClass="mainSubTitle"/>
		<f:verbatim>
			<br /><br />
		</f:verbatim>
		<h:outputText id="remove-ig-listing" value="#{WaiDialogManager.bean.interestGroupName}" styleClass="mainContentNote"/>

</circabc:panel>