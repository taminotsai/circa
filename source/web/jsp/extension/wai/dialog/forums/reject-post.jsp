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

<circabc:panel id="contentMainRejectPost" styleClass="contentMainForm">

		<f:verbatim><br /></f:verbatim>

		<!--  Fill the message -->

		<circabc:panel id="reject-post-section-reason" styleClass="signup_rub_title">
			<h:outputText value="#{cmsg.moderation_reject_post_dialog_section_message}" escape="false" />
		</circabc:panel>

		<f:verbatim><br /></f:verbatim>

		<h:inputTextarea id="reject-reason" value="#{WaiDialogManager.bean.message}" rows="5" cols="50"/>

		<!--  Details of the post-->

		<f:verbatim><br /><br /></f:verbatim>

		<circabc:panel id="reject-post-section-post-details" styleClass="signup_rub_title">
			<h:outputText value="#{cmsg.moderation_reject_post_dialog_section_details}" escape="false" />
		</circabc:panel>

		<f:verbatim><br /></f:verbatim>

		<h:outputFormat id="post-details" value="#{cmsg.newsgroups_post_created_by_on}" escape="false">
			<circabc:param value="#{WaiDialogManager.bean.creator}" />
			<circabc:param value="#{WaiDialogManager.bean.created}"  />
		</h:outputFormat>

		<f:verbatim><br /><br /></f:verbatim>

		<circabc:panel id="panelNewsGroupsPost" label="#{cmsg.newsgroups_post_label}" styleClass="panelNewsGroupsPostGlobal" styleClassLabel="panelNewsGroupsPostLabel" tooltip="#{cmsg.newsgroups_post_label_tooltip}">
		<h:outputText value="#{WaiDialogManager.bean.content}" escape="false"  />
		</circabc:panel>



		<circabc:panel id="topOfPageAnchorPost" styleClass="topOfPageAnchor" >
			<%-- Display the "back to top icon first and display the text after." --%>
			<circabc:actionLink id="topOfPageAnchorPost-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
			<circabc:actionLink id="topOfPageAnchorPost-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
		</circabc:panel>

</circabc:panel>
