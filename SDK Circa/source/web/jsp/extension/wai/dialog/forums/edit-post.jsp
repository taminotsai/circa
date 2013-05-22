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


	<circabc:panel id="edit-post--warning" styleClass="infoPanel" styleClassLabel="infoContent" rendered="#{DialogManager.bean.rejected}" >
		<h:graphicImage id="edit-post-image-warning" value="/images/icons/warning.gif" title="#{cmsg.message_warn_tooltip}" alt="#{cmsg.message_warn_tooltip}"  />
		<h:outputFormat id="edit-post-text-moderator" value="&nbsp;&nbsp;#{cmsg.edit_post_dialog_refused_by}&nbsp;" escape="false"  >
			<circabc:param value="<b>#{DialogManager.bean.rejectModerator}</b>" />
			<circabc:param value="<b>#{DialogManager.bean.rejectDate}</b>" />
		</h:outputFormat>
		<h:outputFormat id="edit-post-text-reason" value="#{cmsg.edit_post_dialog_refused_reason}" escape="false" rendered="#{DialogManager.bean.rejectReasonAvailable}" >
			<circabc:param value="<i>#{DialogManager.bean.rejectMessage}</i>" />
		</h:outputFormat>

		<f:verbatim><br /><br /></f:verbatim>

		<h:outputFormat id="edit-post-otherversions" value="#{cmsg.edit_post_dialog_refused_restore}" escape="false"  />

		<f:verbatim><br /><br /></f:verbatim>

		<h:selectOneMenu id="edit-post-version" value="#{DialogManager.bean.selectedVersion}"  >
			<f:selectItems id="edit-post-versions" value="#{DialogManager.bean.olderVersions}" />
		</h:selectOneMenu>

		<f:verbatim>&nbsp;&nbsp;</f:verbatim>

		<h:commandButton id="submit-select-version" styleClass="" value="#{cmsg.edit_post_dialog_select}" action="wai:dialog:close:wai:dialog:editPostWai" rendered="true" title="#{cmsg.edit_post_dialog_select_tooltip}" />
	</circabc:panel>



	<f:verbatim><br /></f:verbatim>

	<%@ include file="/jsp/extension/wai/dialog/content/edit/edit-online.jsp" %>

</circabc:panel>
