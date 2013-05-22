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

<circabc:panel id="contentMainFormEditUserPerm" styleClass="contentMainForm">


	<circabc:panel id="manage-permission-main-section" styleClass="signup_rub_title">
		<h:outputText value="#{msg.change_user_roles}" escape="false" />
	</circabc:panel>

	<f:verbatim><br /></f:verbatim>

	<h:outputText value="#{cmsg.select_permission}:&nbsp;&nbsp;&nbsp;" escape="false" />

	<h:selectOneMenu id="roles" style="width:250px" value="#{WaiDialogManager.bean.personPermission}" >
		<f:selectItems value="#{WaiDialogManager.bean.permissions}" />
	</h:selectOneMenu>

	<f:verbatim><br /><br /></f:verbatim>

</circabc:panel>
