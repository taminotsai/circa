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

<circabc:panel id="contentMainPost" styleClass="contentMain">

	<circabc:panel id="contentMainButton" styleClass="contentMainButton">
		<circabc:panel id="divButtonDialog" styleClass="divButtonDialog">
			<h:commandButton id="close-button" styleClass="dialogButton" value="#{cmsg.close}" action="wai:browse-wai:close" />
		</circabc:panel>
	</circabc:panel>

	<circabc:panel id="contentMainForm" styleClass="contentMainForm">

		<f:verbatim><br /></f:verbatim>

		<h:outputFormat id="post-details" value="#{cmsg.newsgroups_post_created_by_on}" escape="false" styleClass="noItem">
			<circabc:param value="#{WaiNavigationManager.bean.creator}" />
			<circabc:param value="#{WaiNavigationManager.bean.created}" />
		</h:outputFormat>

		<circabc:displayer rendered="#{WaiNavigationManager.bean.reply}">
			<circabc:actionLink value="" tooltip="#{cmsg.newsgroups_post_on_reply_of_tooltip}"  actionListener="#{BrowseBean.clickWai}" onclick="showWaitProgress();" styleClass="noItem">
				<h:outputFormat id="post-related-post" value="&nbsp;#{cmsg.newsgroups_post_on_reply_of}" escape="false">
					<circabc:param value="#{WaiNavigationManager.bean.referenceCreator}" />
					<circabc:param value="#{WaiNavigationManager.bean.referenceDate}" />
				</h:outputFormat>
				<circabc:param name="id" value="#{WaiNavigationManager.bean.referenceId}" > </circabc:param>
			</circabc:actionLink>
		</circabc:displayer>

		<f:verbatim><br /><br /></f:verbatim>

		<circabc:panel id="panelNewsGroupsPost" label="#{cmsg.newsgroups_post_label}" styleClass="panelNewsGroupsPostGlobal" styleClassLabel="panelNewsGroupsPostLabel" tooltip="#{cmsg.newsgroups_post_label_tooltip}">
			<h:outputText value="#{WaiNavigationManager.bean.message}" escape="false"></h:outputText>
		</circabc:panel>

		<circabc:panel id="topOfPageAnchorPost" styleClass="topOfPageAnchor" >
			<%-- Display the "back to top icon first and display the text after." --%>
			<circabc:actionLink id="topOfPageAnchorPost-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
			<circabc:actionLink id="topOfPageAnchorPost-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
		</circabc:panel>


		<circabc:displayer rendered="#{WaiNavigationManager.bean.attachementAvailable}">
			<circabc:panel id="panelAttachements" label="#{cmsg.newsgroups_post_related_elements}" styleClass="panelNewsGroupsPostGlobal" styleClassLabel="panelNewsGroupsPostLabel" tooltip="#{cmsg.newsgroups_post_related_elements}">
				<c:set var="count" value="0" />
				<f:verbatim><table cellpadding='4' ><tr></f:verbatim>
				<c:forEach var="attach" items="${WaiNavigationManager.bean.attachements}">
					<c:choose>
						<c:when test="${count == 4}">
	      					<f:verbatim></tr><tr></f:verbatim>
	      					<c:set var="count" value="1" />
	      				</c:when>
						<c:otherwise>
	      					<c:set var="count" value="${count + 1}" />
	      		 		</c:otherwise>
		      	    </c:choose>
		      	    <f:verbatim><td></f:verbatim>
					<c:choose>
						<c:when test="${attach.isContainer == true}">
	      					<circabc:actionLink value=" ${attach.name}" tooltip="${attach.name}" image="/images/icons/${attach.smallIcon}.gif"  actionListener="#{BrowseBean.clickWai}"  onclick="showWaitProgress();">
								<circabc:param name="id" value="${attach.id}" />
							</circabc:actionLink>
	      				</c:when>
						<c:otherwise>
							<circabc:actionLink value=" ${attach.name}" tooltip="${attach.name}" image="${attach.fileType16}" href="${attach.downloadUrl}" target="new" />
	      		 		</c:otherwise>
		      	    </c:choose>
		      	    <f:verbatim></td></f:verbatim>
				</c:forEach>
				<f:verbatim></tr></table></f:verbatim>

			</circabc:panel>
			<circabc:panel id="topOfPageAnchorAttachement" styleClass="topOfPageAnchor" >
				<%-- Display the "back to top icon first and display the text after." --%>
				<circabc:actionLink id="topOfPageAnchorAttach-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
				<circabc:actionLink id="topOfPageAnchorAttach-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
			</circabc:panel>
		</circabc:displayer>

	</circabc:panel>
</circabc:panel>

