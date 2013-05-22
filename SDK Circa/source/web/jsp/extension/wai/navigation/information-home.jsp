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

<%@page import="eu.cec.digit.circabc.web.Beans"%>
<%@page import="eu.cec.digit.circabc.web.wai.bean.navigation.InformationBean"%>

<circabc:panel id="contentMainInformationHomeBrowse" styleClass="contentMain" rendered="#{InformationBean.editingView == false}">

	<circabc:displayer id="rendererIndexNotFound" rendered="#{InformationBean.indexFileFound == false}">
		<circabc:panel id="no-index-file--warning" styleClass="infoPanel" styleClassLabel="infoContent" >
			<h:graphicImage id="no-index-file-image-warning" value="/images/icons/warning.gif" title="#{cmsg.message_warn_tooltip}" alt="#{cmsg.message_warn_tooltip}"  />
			<h:outputText id="no-index-file-text-warning" value="&nbsp;&nbsp;#{cmsg.information_no_index_file}" escape="false" />
		</circabc:panel>
	</circabc:displayer>

	<circabc:displayer id="rendererIndexFound" rendered="#{InformationBean.indexFileFound == true}">
	<circabc:displayer id="renderNewWindow" rendered="#{InformationBean.renderNewWindow == true}">
		<f:verbatim>
			<script language="JavaScript">
				function open_mini_site()
				{
					new_window = window.open('<%=((InformationBean)Beans.getBean(InformationBean.BEAN_NAME)).getWebdavUrl(false)%>')
				}
				window.onLoad = open_mini_site();
			</script>
		</f:verbatim>
		<circabc:actionLink id="browseNewPage" value="#{cmsg.information_click_new_windows}" tooltip="#{cmsg.information_click_new_windows_tooltip}" immediate="true" showLink="false" href="#{InformationBean.webdavUrl}" target="new" />
	</circabc:displayer>

	<c:if test="${InformationBean.renderNewWindow == false}">
		<f:verbatim>
		<IFRAME src="<%=request.getContextPath()%><%=((InformationBean)Beans.getBean(InformationBean.BEAN_NAME)).getWebdavUrl()%>" frameborder="1" scrolling="auto" width="98%" height="600">
		</f:verbatim>
			<h:outputFormat id="no-iframe" value="#{cmsg.information_no_iframe}" escape="false">
				<circabc:param value="<%=request.getContextPath()%>" />
				<circabc:param value="<%=((InformationBean)Beans.getBean(InformationBean.BEAN_NAME)).getWebdavUrl()%>" />
			</h:outputFormat>
		<f:verbatim>
		</IFRAME>
		</f:verbatim>
	</c:if>
	</circabc:displayer>
</circabc:panel>


<circabc:panel id="contentMainInformationHomeEdit" styleClass="contentMain" rendered="#{InformationBean.editingView}">
	<!--  Action preview -->
	<circabc:panel id="edit-information-preview" styleClass="wai_dialog_more_action">
		<h:graphicImage value="/images/icons/comparetocurrent.png" alt="#{cmsg.information_preview_action_image_tooltip}" />
		<h:outputText id="edit-information-preview-spaces" value="&nbsp;" escape="false" />
		<circabc:actionLink id="previewNewPage" value="#{cmsg.information_preview_action_title}" tooltip="#{cmsg.information_preview_action_tooltip}" immediate="true" showLink="false" href="#{InformationBean.webdavUrl}" target="new"/>
	</circabc:panel>

	<f:verbatim>
		<br /><br />
	</f:verbatim>

	<circabc:panel id="panelInformationContainer" label="#{cmsg.library_panel_container_label}" styleClass="panelLibraryContainerGlobal" styleClassLabel="panelLibraryContainerLabel" tooltip="#{cmsg.library_panel_container_tooltip}">
		<circabc:customList id="InformationContainerList" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{InformationBean.containers}" configuration="#{InformationBean.containerNavigationPreference}" />
	</circabc:panel>

	<circabc:panel id="topOfPageAnchorInfHomeContainer" styleClass="topOfPageAnchor" rendered="#{NavigationBean.currentIGRoot.information != null}" >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorInfHomeContainer-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorInfHomeContainer-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>

	<circabc:panel id="panelInformationContent" label="#{cmsg.library_panel_content_label}" styleClass="panelLibraryContentGlobal" styleClassLabel="panelLibraryContentLabel" tooltip="#{cmsg.library_panel_content_tooltip}">
		<circabc:customList id="informationContentList"  styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{InformationBean.contents}" configuration="#{InformationBean.contentNavigationPreference}" />
	</circabc:panel>


	<circabc:panel id="topOfPageAnchorInfHomeContent" styleClass="topOfPageAnchor" rendered="#{NavigationBean.currentIGRoot.information != null}" >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorInfHomeContent-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorInfHomeContent-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>
</circabc:panel>