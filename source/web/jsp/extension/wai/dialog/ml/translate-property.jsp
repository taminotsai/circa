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

<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/scripts/tiny_mce/tiny_mce.js">&#160;</script>
<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/scripts/ajax/common.js">&#160;</script>
<script language="javascript">
	<%-- Init the Tiny MCE in-line HTML editor --%>
	tinyMCE.init(
	{
		plugins : "safari,spellchecker,pagebreak,style,layer,table,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template",
		theme : "advanced",
		mode : "<h:outputText rendered="#{DialogManager.bean.displayRichText == true }" escape="false" value="textareas" /><h:outputText rendered="#{DialogManager.bean.displayRichText == false }" escape="false" value="none" />",
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

<circabc:panel id="contentMainFormTranslateProperty" styleClass="contentMainForm">

	<!--  New keyword panel -->
	<circabc:panel id="translate-property-first-section" styleClass="signup_rub_title">
		<h:outputText value="1.&nbsp;#{cmsg.translate_property_dialog_section_specify}" escape="false" />
	</circabc:panel>

	<f:verbatim>
		<br />
	</f:verbatim>

	<h:selectOneMenu id="translate-property-language" value="#{DialogManager.bean.language}" >
		<f:selectItems id="translate-property-languages" value="#{DialogManager.bean.languages}" />
	</h:selectOneMenu>

	<circabc:displayer rendered="#{DialogManager.bean.displayAsArea == false}">
		<h:inputText id="translate-property-value-input" value="#{DialogManager.bean.value}" />
	</circabc:displayer>
	<circabc:displayer rendered="#{DialogManager.bean.displayAsArea == true}">
		<f:verbatim><br /><br /></f:verbatim>
		<h:inputTextarea id="translate-property-value-area" rows="3" cols="33" value="#{DialogManager.bean.value}" />
	</circabc:displayer>

	<f:verbatim>
		<br />
	</f:verbatim>
	
	<h:outputText value="#{cmsg.lightDescription_no_html }" rendered="#{DialogManager.bean.displayRichText == false }" style="color:#CCC;"></h:outputText>
	<f:verbatim>
		<br />
	</f:verbatim>
	<h:outputText value="#{cmsg.lightDescription_limit_500 }" rendered="#{DialogManager.bean.displayRichText == false }" style="color:#CCC;"></h:outputText>


	<f:verbatim>
		<br /><br /><br />
	</f:verbatim>

	<circabc:panel id="translate-property_dialog_section_add" styleClass="signup_rub_title">
		<h:outputText value="2.&nbsp;#{cmsg.translate_property_dialog_section_add}" escape="false" />
	</circabc:panel>

	<f:verbatim>
		<br />
	</f:verbatim>

	<h:commandButton id="AddToList" value="#{msg.add_to_list_button}"
		actionListener="#{DialogManager.bean.addSelection}"
		styleClass="wizardButton" />

	<f:verbatim>
		<br /><br />
	</f:verbatim>

	<h:dataTable value="#{DialogManager.bean.translationDataModel}" var="row"
		rowClasses="selectedItemsRow,selectedItemsRowAlt"
		styleClass="selectedItems" headerClass="selectedItemsHeader"
		cellspacing="0" cellpadding="4"
		rendered="#{DialogManager.bean.translationDataModel.rowCount != 0}">
		<h:column>
			<f:facet name="header">
				<h:outputText value="#{msg.language}" />
			</f:facet>
			<h:outputText id="col-lang" value="#{row.language}" />
		</h:column>
		<h:column>
			<f:facet name="header">
				<h:outputText id="col-name" value="#{msg.name}" />
			</f:facet>
			<h:outputText value="#{row.value}" escape="false" />
		</h:column>
		<h:column>
			<circabc:actionLink tooltip="#{msg.remove}" actionListener="#{DialogManager.bean.removeSelection}"
				image="/images/icons/delete.gif" value="#{msg.remove}" showLink="false"
				styleClass="pad6Left" />
		</h:column>
	</h:dataTable>
	<a:panel id="no-items"
		rendered="#{DialogManager.bean.translationDataModel.rowCount == 0}">
		<table cellspacing='0' cellpadding='2' border='0' class='selectedItems'>
			<tr>
				<td class='selectedItemsRow'>
					<h:outputText id="no-items-msg" value="#{msg.no_selected_items}" />
				</td>
			</tr>
		</table>
	</a:panel>

</circabc:panel>
