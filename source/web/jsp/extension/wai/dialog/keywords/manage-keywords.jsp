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


<script language="javascript">
    function updateList(){
        document.getElementById("FormPrincipal:submit-change-lang").click();
    }
</script>

<circabc:panel id="contentMainFormManageKeywords" styleClass="contentMainForm">

	<!--  Action add a new keyword -->
	<circabc:panel id="manage-keywords-add-keyword-section" styleClass="wai_dialog_more_action">
		<h:graphicImage value="/images/icons/add_category.gif" alt="#{cmsg.add_new_keyword_dialog_action_tooltip}" />
		<h:outputText id="manage-keywords-add-keyword-spaces" value="&nbsp;" escape="false" />
		<circabc:actionLink id="manage-keywords-act-add" value="#{cmsg.add_new_keyword_dialog_action_title}" tooltip="#{cmsg.add_new_keyword_dialog_action_tooltip}" action="wai:dialog:defineKeywordDialogWai" actionListener="#{WaiDialogManager.setupParameters}" >
			<circabc:param id="manage-keywords-act-id" name="id" value="#{DialogManager.bean.interestGroup.id}" />
			<circabc:param id="manage-keywords-service" name="service" value="Library" />
			<circabc:param id="manage-keywords-activity" name="activity" value="Add keyword" />
		</circabc:actionLink>
	</circabc:panel>

	<f:verbatim>
		<br /><br />
	</f:verbatim>

	<!--  Select language panel -->
	<circabc:panel id="manage-keywords-main-section" styleClass="signup_rub_title">
		<h:outputText value="#{cmsg.manage_keyword_dialog_section_title}" escape="false" />
	</circabc:panel>

	<f:verbatim>
		<br />
	</f:verbatim>

	<h:outputText id="manage-keywords-lang-text" value="#{cmsg.manage_keyword_dialog_language_filter}:&nbsp;" escape="false"/>
	<h:selectOneMenu id="manage-keywords-language" value="#{DialogManager.bean.selectedLanguage}" onchange="updateList()" valueChangeListener="#{ManageKeywordsDialog.updateList}" immediate="true"  >
		<f:selectItems id="manage-keywords-languages" value="#{DialogManager.bean.languages}" />
	</h:selectOneMenu>
	<h:outputText id="manage-keywords-lang-text-spaces-submit" value="&nbsp;&nbsp;" escape="false" />
	<h:commandButton id="submit-change-lang" styleClass="" value="#{cmsg.filter}" action="wai:dialog:close:wai:dialog:manageKeywordsDialogWai" rendered="true" immediate="true" title="#{cmsg.manage_keyword_dialog_apply_filter_tooltip}" />


	<f:verbatim>
		<br /><br />
	</f:verbatim>

	<!--  Display the keywords -->
	<circabc:richList id="manage-keywords-list" viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{DialogManager.bean.keywords}" var="kw" initialSortColumn="value" pageSize="#{BrowseBean.listElementNumber}">
		<circabc:column id="manage-keywords-list-name">
			<f:facet name="header">
				<circabc:sortLink id="manage-keywords-list-sorter" label="#{cmsg.manage_keyword_dialog_value}" value="value" tooltipAscending="#{cmsg.manage_keyword_dialog_sort_asc}" tooltipDescending="#{cmsg.manage_keyword_dialog_sort_desc}"/>
			</f:facet>
			<h:outputText id="manage-keywords-list-col-name" value="#{kw.value}"/>
		</circabc:column>
		<circabc:column id="manage-keywords-list-actions-col">
			<f:facet name="header">
				<h:outputText id="manage-keywords-list-cont-act" value="#{cmsg.actions}" escape="false" />
			</f:facet>

			<circabc:actionLink image="/images/icons/edit_category.gif" id="manage-keywords-act-modify-key" tooltip="#{cmsg.modify_keyword_dialog_action_tooltip}" value="#{cmsg.modify_keyword_dialog_action_tooltip}" showLink="false" action="wai:dialog:editKeywordDialogWai" actionListener="#{WaiDialogManager.setupParameters}" >
				<circabc:param id="param-modify-keyword-ig-id" name="id" value="#{DialogManager.bean.interestGroup.id}" />
				<circabc:param id="param-modify-keyword" name="keyword" value="#{kw.id}" />
				<circabc:param id="param-modify-keyword-imageName" name="imageName" value="modify_keyword_#{kw.value}" />
				<circabc:param id="param-modify-service" name="service" value="Library" />
				<circabc:param id="param-modify-activity" name="activity" value="Edit keyword" />
			</circabc:actionLink>
			<circabc:actionLink image="/images/icons/delete_category.gif" id="manage-keywords-act-delete-key" tooltip="#{cmsg.delete_keyword_dialog_action_tooltip}" value="#{cmsg.delete_keyword_dialog_action_tooltip}" showLink="false" action="wai:dialog:deleteKeywordDialogWai" actionListener="#{WaiDialogManager.setupParameters}" >
				<circabc:param id="param-delete-keyword-ig-id" name="id" value="#{DialogManager.bean.interestGroup.id}" />
				<circabc:param id="param-delete-keyword" name="keyword" value="#{kw.id}" />
				<circabc:param id="param-delete-keyword-imageName" name="imageName" value="delete_keyword_#{kw.value}" />
				<circabc:param id="param-delete-service" name="service" value="Library" />
				<circabc:param id="param-delete-activity" name="activity" value="Delete keyword" />
			</circabc:actionLink>
		</circabc:column>
		<f:facet name="empty">
			<h:outputFormat id="manage-keywords-list-container" value="#{cmsg.manage_keyword_dialog_no_list_items}" escape="false" />
		</f:facet>
		<circabc:dataPager id="manage-keywords-pager" styleClass="pagerCirca" />
	</circabc:richList>

	<!--  h:commandButton value="" id="submit-change-lang" immediate="true" action=""  /-->

</circabc:panel>