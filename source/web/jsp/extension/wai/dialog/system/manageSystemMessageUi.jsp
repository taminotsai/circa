<%--+
    |     Copyright European Community 2011 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false"%>

<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/scripts/tiny_mce/tiny_mce.js">&#160;</script>
<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/scripts/ajax/common.js">&#160;</script>
<script language="javascript">
	<%-- Init the Tiny MCE in-line HTML editor --%>
	tinyMCE.init(
	{
		plugins : "safari,spellchecker,pagebreak,style,layer,table,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template",
		theme : "advanced",
		mode : "textareas",
		relative_urls: true,
		verify_html : false,
		language : "<%=request.getLocale().getLanguage()%>",

		theme_advanced_buttons1 : "fontsizeselect,separator,bold,italic,underline,strikethrough,separator,forecolor,separator,link,unlink",
		theme_advanced_buttons2 : "",
		theme_advanced_buttons3 : "",
		theme_advanced_buttons4 : "",
		theme_advanced_toolbar_location : "top",
		theme_advanced_toolbar_align : "left",
		theme_advanced_path : false,
		theme_advanced_resizing : false,
		theme_advanced_disable: "styleselect",
		theme_advanced_styles : "Code=codeStyle;Quote=quoteStyle",
		font_size_style_values : "10pt,12pt,14pt,18pt,24pt",

		// get a list of common used urls
		external_link_list_url : "${pageContext.request.contextPath}/faces/jsp/extension/wai/dialog/content/edit/links.jsp",
		// get a list of available images
		external_image_list_url : "${pageContext.request.contextPath}/faces/jsp/extension/wai/dialog/content/edit/images.jsp",
		// get a list of available media
		media_external_list_url : "${pageContext.request.contextPath}/faces/jsp/extension/wai/dialog/content/edit/medias.jsp",

		extended_valid_elements : "a[href|target|name],font[face|size|color|style],span[class|align|style]"
	});
</script>

<circabc:panel id="contentMainFormManageSystemMessageUi" styleClass="contentMainForm">

	<circabc:panel id="formRow1" styleClass="formRow">
		<circabc:panel id="col1Row1" styleClass="formColumnNarrow">
			<h:outputLabel for="txtMessage">
				<h:outputText value="#{cmsg.manage_system_message_label_message}" />
			</h:outputLabel>
		</circabc:panel>
		<circabc:panel id="col2Row1" styleClass="formColumn">
			<h:inputTextarea id="txtMessage" value="#{DialogManager.bean.message}" styleClass="txtArea" />
		</circabc:panel>
	</circabc:panel>
	
	<circabc:panel id="formRow2" styleClass="formRow">
		<circabc:panel id="col1Row2" styleClass="formColumnNarrow">
			<h:outputText value=" " />
		</circabc:panel>
		<circabc:panel id="col2VRow2" styleClass="formColumn">
			<h:selectBooleanCheckbox id="cbxShowMessage" value="#{DialogManager.bean.showMessage}" />
			<h:outputLabel for="cbxShowMessage">
				<h:outputText value="#{cmsg.manage_system_message_label_showMessage}" />
			</h:outputLabel>
		</circabc:panel>
	</circabc:panel>
</circabc:panel>
