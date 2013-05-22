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
<%@ page buffer="32kb" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false"%>


<circabc:panel id="contentMainFormEditTxtOnline" styleClass="contentMainForm">

	<circabc:panel id="edit-text--warning" styleClass="infoPanel" styleClassLabel="infoContent"  >
		<h:graphicImage id="edit-text-image-warning" value="/images/icons/warning.gif" title="#{cmsg.message_warn_tooltip}" alt="#{cmsg.message_warn_tooltip}"  />
		<h:outputText id="edit-text-text-warning-spaces" value="&nbsp;&nbsp;#{cmsg.edit_inline_text_warning}&nbsp;" escape="false" />
		<circabc:actionLink id="edit-text-simple-text" image="/images/icons/edit_online.gif" tooltip="#{cmsg.edit_inline_text_simplest_tooltip}" value="#{cmsg.edit_inline_text_simplest_action}" padding="2" showLink="true" action="wai:dialog:close:wai:dialog:editHtmlDocumentInlineWai" actionListener="#{WaiDialogManager.setupParameters}" >
			<circabc:param id="id-edit" name="id" value="#{WaiDialogManager.bean.actionNode.id}" />
		</circabc:actionLink>
	</circabc:panel>

	<f:verbatim><br /><br /></f:verbatim>

	<h:inputTextarea id="textArea" rows="24" cols="90" styleClass="onlineEditor" value="#{WaiDialogManager.bean.editorOutput}" />
</circabc:panel>
