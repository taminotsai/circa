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

<circabc:panel id="contentMainForum" styleClass="contentMain">

	<circabc:panel id="panelNewsGroupsSubForums" label="#{cmsg.newsgroups_forums_label}" styleClass="panelNewsGroupsForumsGlobal" styleClassLabel="panelNewsGroupsForumsLabel" tooltip="#{cmsg.newsgroups_forums_label_tooltip}" rendered="#{WaiNavigationManager.bean.subForumAllowed}" >
		<circabc:customList id="newsGroupsSubForumsList"  styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{WaiNavigationManager.bean.forums}" configuration="#{WaiNavigationManager.bean.forumNavigationPreference}" />
	</circabc:panel>

	<circabc:panel id="topOfPageAnchorNewsHomeSub" styleClass="topOfPageAnchor" rendered="#{WaiNavigationManager.bean.subForumAllowed}" >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorNewsHomeSub-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorNewsHomeSub-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>


	<circabc:panel id="panelNewsGroupsForum" label="#{cmsg.newsgroups_forum_label}" styleClass="panelNewsGroupsForumGlobal" styleClassLabel="panelNewsGroupsForumLabel" tooltip="#{cmsg.newsgroups_forum_label_tooltip}">
		<circabc:customList id="newsGroupsTopicList"  styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{WaiNavigationManager.bean.topics}" configuration="#{WaiNavigationManager.bean.topicNavigationPreference}" />
	</circabc:panel>

	<circabc:panel id="topOfPageAnchorForum" styleClass="topOfPageAnchor"  >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorForum-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorForum-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>
</circabc:panel>
