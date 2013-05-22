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

<circabc:panel id="contentMainNewsHome" styleClass="contentMain">

	<circabc:panel id="panelNewsGroupsForums" label="#{cmsg.newsgroups_forums_label}" styleClass="panelNewsGroupsForumsGlobal" styleClassLabel="panelNewsGroupsForumsLabel" tooltip="#{cmsg.newsgroups_forums_label_tooltip}">
		<circabc:customList id="newsGroupsForumsList"  styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{NewsGroupBean.forums}" configuration="#{NewsGroupBean.forumNavigationPreference}" />
	</circabc:panel>

	<circabc:panel id="topOfPageAnchorNewsHome" styleClass="topOfPageAnchor" rendered="#{NavigationBean.currentIGRoot.library != null}" >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorNewsHome-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorNewsHome-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>
</circabc:panel>
