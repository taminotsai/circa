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

<%--  script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/tinymce_3_2_1_1/tiny_mce.js"></script> --%>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/tiny_mce/tiny_mce.js"></script>
<script language="javascript" type="text/javascript">


	<%-- Init the Tiny MCE in-line HTML editor --%>
	tinyMCE.init(
	{
		plugins : "safari,spellchecker,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,imagemanager,filemanager",
		theme : "advanced",
		mode : "exact",
		relative_urls: false,
		elements : "editor",
		save_callback : "saveContent",

		theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,formatselect,fontselect,fontsizeselect",
		theme_advanced_buttons2 : "cut,copy,paste,pastetext,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,forecolor,backcolor",
		theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub, sup, emotions,ltr,rtl,fullscreen",
		theme_advanced_toolbar_location : "top",
		theme_advanced_toolbar_align : "left",
		theme_advanced_statusbar_location : "bottom",
		theme_advanced_resizing : false,

		theme_advanced_disable: "styleselect",


		extended_valid_elements : "a[href|target|name],font[face|size|color|style],span[class|align|style]"
	});

	function saveContent(id, content)
	{
		document.getElementById("FormPrincipal:editorOutput").value=content;
	}

</script>

<circabc:panel id="contentMainFormEditHtml" styleClass="contentMainForm">

	<circabc:panel id="edit-html--warning" styleClass="infoPanel" styleClassLabel="infoContent"  >
		<h:graphicImage id="edit-html-image-warning" value="/images/icons/warning.gif" title="#{cmsg.message_warn_tooltip}" alt="#{cmsg.message_warn_tooltip}"  />
		<h:outputText id="edit--text-warning-spaces" value="&nbsp;&nbsp;#{cmsg.edit_inline_html_warning}&nbsp;" escape="false" />
		<circabc:actionLink id="edit-html-html-doc" image="/images/icons/edit_online.gif" tooltip="#{cmsg.edit_inline_html_simplest_tooltip}" value="#{cmsg.edit_inline_html_simplest_action}" padding="2" showLink="true" action="wai:dialog:close:wai:dialog:editTextDocumentInlineWai" actionListener="#{WaiDialogManager.setupParameters}" >
			<circabc:param id="id-edit" name="id" value="#{WaiDialogManager.bean.actionNode.id}" />
		</circabc:actionLink>
	</circabc:panel>

	<f:verbatim><br /><br /><div id="editor" class="onlineEditor" ></f:verbatim>
		<h:outputText value="#{WaiDialogManager.bean.documentContent}" escape="false" />
	<f:verbatim></div ></f:verbatim>

	<h:inputHidden id="editorOutput" value="#{WaiDialogManager.bean.editorOutput}" />

</circabc:panel>




