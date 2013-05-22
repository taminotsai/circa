<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>
<%@ page buffer="32kb" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false"%>

<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/scripts/tiny_mce/tiny_mce.js">&#160;</script>
<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/scripts/ajax/common.js">&#160;</script>
<script language="javascript" type="text/javascript">
tinyMCE.init(
{
	// general options
	theme : "advanced",
	mode : "textareas",
	plugins : "safari,spellchecker,pagebreak,style,layer,table,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template",
	convert_urls: false,
	relative_urls: false,
	verify_html : false,
	language : "<%=request.getLocale().getLanguage()%>",
	font_size_style_values : "10pt,12pt,14pt,18pt,24pt",

	// theme options
	theme_advanced_buttons1 : "fontsizeselect,separator,bold,italic,underline,strikethrough,separator,forecolor,separator,link,unlink,separator,cut,copy,paste,pastetext,pasteword",
	theme_advanced_buttons2 : "",
    theme_advanced_buttons3 : "",
	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	theme_advanced_path : false,
	theme_advanced_resizing : true,
	theme_advanced_statusbar_location : "bottom",
	theme_advanced_disable: "styleselect",
	theme_advanced_styles : "Code=codeStyle;Quote=quoteStyle",

	// Drop lists for link/image/media/template dialogs
	external_link_list_url : "${pageContext.request.contextPath}/faces/jsp/extension/wai/dialog/content/edit/links.jsp",
	external_image_list_url : "${pageContext.request.contextPath}/faces/jsp/extension/wai/dialog/content/edit/images.jsp",
	media_external_list_url : "${pageContext.request.contextPath}/faces/jsp/extension/wai/dialog/content/edit/medias.jsp",
	extended_valid_elements : "a[href|target|name],font[face|size|color|style],span[class|align|style]"
});
</script>

<p><img src="/circabc/images/extension/help.png" alt="help" style="float:left;width:100px;"/><br/><h:outputText value="#{cmsg.contact_dialog_request_help_text }" escape="false"></h:outputText></p>

<fieldset class="formArea">
	<legend><h:outputText value="#{cmsg.contact_dialog_request_legend }"></h:outputText></legend>
	<br/>
	<h:outputText value="#{ContactFormDialog.organisationHelpdesk }" escape="false">
	</h:outputText><br/><br/>
	<h:outputLabel for="contact" value="#{cmsg.contact_dialog_contact }"></h:outputLabel>
	<h:selectOneMenu id="contact" value="#{ContactFormDialog.selectedContact }">
		<f:selectItems value="#{ContactFormDialog.contactAsItems }"/>
	</h:selectOneMenu>
	<br/>
	<h:outputText value="#{ContactFormDialog.guessedHelpdesk }" styleClass="inputHelper"/>
	<br/><br/>
	<h:outputLabel for="mail" value="#{cmsg.contact_dialog_mail }" rendered="#{NavigationBean.isGuest == true}"></h:outputLabel>
	<h:inputText id="mail" value="#{ContactFormDialog.mail }" styleClass="textLongWidth" rendered="#{NavigationBean.isGuest == true}"></h:inputText>
	<h:outputText value="<br/>" escape="false" rendered="#{NavigationBean.isGuest == true}"></h:outputText>
	<br/>
	<h:outputLabel for="subject" value="#{cmsg.contact_dialog_subject }"></h:outputLabel>
	<h:inputText id="subject" value="#{ContactFormDialog.subject }" styleClass="textLongWidth"></h:inputText>
	<br/>
	<h:outputLabel for="type" value="#{cmsg.contact_dialog_type }"></h:outputLabel>
	<h:selectOneMenu id="type" value="#{ContactFormDialog.selectedType }">
		<f:selectItems value="#{ContactFormDialog.types }"/>
	</h:selectOneMenu>
	<br/>
	<br/>
	<br/>
	<h:outputLabel for="description" value="#{cmsg.contact_dialog_description }"></h:outputLabel>
	<h:inputTextarea id="description" value="#{ContactFormDialog.description }" styleClass="textLongWidth"></h:inputTextarea>
	<br/>
</fieldset>

