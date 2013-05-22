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

<circabc:panel id="contentMainFormEditUserProfile" styleClass="contentMainForm">

	<circabc:panel id="manage-edit-profile-main-section" styleClass="signup_rub_title">
		<h:outputText value="#{cmsg.edit_user_profile_section}" escape="false" />
	</circabc:panel>

	<h:outputFormat value="#{cmsg.title_edit_users_profile}:&nbsp;&nbsp;&nbsp;" id="msg12" escape="false">
		<circabc:param value="#{WaiDialogManager.bean.fullName}" id="username-param" />
	</h:outputFormat>

	<h:selectOneMenu id="roles" style="width:250px" value="#{WaiDialogManager.bean.userProfile}" >
		<f:selectItems value="#{WaiDialogManager.bean.profiles}" />
	</h:selectOneMenu>

	<f:verbatim><br /><br /></f:verbatim>

</circabc:panel>