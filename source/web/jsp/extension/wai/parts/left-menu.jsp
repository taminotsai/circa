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
<f:loadBundle basename="alfresco.extension.config.circabc-settings" var="settings" />


<%-- Menu START --%>
<div id="sidebar">

	<!--  Top Interest Group menu if required -->
	<circabc:displayer id="left-menu-displayer-ig" rendered="#{WaiLeftMenuBean.interestGroupDisplay}">
		<div id="tabMenuIGH">
			<!--  The title of this menu group (the name of the current ig) -->
			<div id="tabMenuIGHBlue">
				<circabc:actionLink id="left-menu-ig-link" value="#{NavigationBean.currentIGRoot.bestTitle}" actionListener="#{BrowseBean.clickWai}" tooltip="#{NavigationBean.currentIGRoot.name}" onclick="showWaitProgress();">
					<circabc:param id="param-id-left-menu-ig-link" name="id" value="#{NavigationBean.currentIGRoot.id}" />
				</circabc:actionLink>
				<img src="${currentContextPath}/images/extension/menu.gif" alt="${cmsg.library_main_options_are_listed_below}" />
			</div>

			<!-- The list of available services -->
		   <div id="tabMenuIGHSub" class="tabMenuIGHSub">

				<!--  The information service -->
				<circabc:displayer id="left-menu-displayer-information" rendered="#{WaiLeftMenuBean.informationDisplay}">

					<c:choose>
						<c:when test="${WaiLeftMenuBean.informationActivated}">
							<!--  Expanded -->
							<div id="tabMenuIGHInfExp">
								<img src="${currentContextPath}/images/extension/expanded.gif" alt="${cmsg.information_menu_tooltip}" />
								<circabc:actionLink id="left-menu-link-inf" value="#{cmsg.information_menu}" actionListener="#{BrowseBean.clickWai}" tooltip="#{cmsg.information_menu_link_tooltip}" onclick="showWaitProgress();" >
									<circabc:param id="param-id-left-menu-link-inf" name="id" value="#{NavigationBean.currentIGRoot.information.id}" />
								</circabc:actionLink>
							</div>
							<div id="tabMenuIGHInfSub">
								<c:forEach var="action" items="${InformationBean.actions}">
									<circabc:permissionEvaluator value="#{NavigationBean.currentNode}" allow="${action.permission.allow}" id="${action.permission.id}-${action.link.id}">
										<ul>
											<li>
												<c:if test="${action.onlyActionListener}">
													<circabc:actionLink value="${action.link.value}" actionListener="${action.link.actionListener}" tooltip="${action.link.tooltip}" id="LisI${action.link.id}"  >
														<circabc:param id="Param-ILista${action.parameter.id}-${action.link.id}" name="${action.parameter.name}" value="${action.parameter.value}" />
													</circabc:actionLink>
												</c:if>
												<c:if test="${action.notOnlyActionListener}">
													<circabc:actionLink value="${action.link.value}" action="${action.link.action}" actionListener="${action.link.actionListener}" tooltip="${action.link.tooltip}" id="I${action.link.id}"  >
														<circabc:param id="Param-IListb${action.parameter.id}-${action.link.id}" name="${action.parameter.name}" value="${action.parameter.value}" />
													</circabc:actionLink>
												</c:if>
											</li>
										</ul>
									</circabc:permissionEvaluator>
								</c:forEach>
							</div>
						</c:when>
						<c:otherwise>
							<!--  Closed -->
							<div id="tabMenuIGHInf">
								<img src="${currentContextPath}/images/extension/collapsed.gif" alt="${cmsg.information_menu_tooltip}" />
								<circabc:actionLink id="left-menu-link-inf-closed" value="#{cmsg.information_menu}" actionListener="#{BrowseBean.clickWai}" tooltip="#{cmsg.information_menu_link_tooltip}" onclick="showWaitProgress();">
									<circabc:param id="param-id-left-menu-inf-lib-closed" name="id" value="#{NavigationBean.currentIGRoot.information.id}" />
								</circabc:actionLink>
							</div>
						</c:otherwise>
					</c:choose>
				</circabc:displayer>

		   		<!--  The library service -->
				<circabc:displayer id="left-menu-displayer-library" rendered="#{WaiLeftMenuBean.libraryDisplay}">

					<c:choose>
						<c:when test="${WaiLeftMenuBean.libraryActivated}">
							<!--  Expanded -->
							<div id="tabMenuIGHLibExp">
								<img src="${currentContextPath}/images/extension/expanded.gif" alt="${cmsg.library_menu_tooltip}" />
								<circabc:actionLink id="left-menu-link-lib" value="#{cmsg.library_menu}" actionListener="#{BrowseBean.clickWai}" tooltip="#{cmsg.library_menu_link_tooltip}" onclick="showWaitProgress();">
									<circabc:param id="param-id-left-menu-link-lib" name="id" value="#{NavigationBean.currentIGRoot.library.id}" />
								</circabc:actionLink>
							</div>
							<div id="tabMenuIGHLibSub">
								<c:forEach var="action" items="${LibraryBean.actions}">
									<circabc:permissionEvaluator value="#{NavigationBean.currentNode}" allow="${action.permission.allow}" id="${action.permission.id}-${action.link.id}">
										<ul>
											<li>
												<c:if test="${action.onlyActionListener}">
													<circabc:actionLink value="${action.link.value}" actionListener="${action.link.actionListener}" tooltip="${action.link.tooltip}" id="Lis${action.link.id}"  >
														<circabc:param id="Param-ALista${action.parameter.id}-${action.link.id}" name="${action.parameter.name}" value="${action.parameter.value}" />
													</circabc:actionLink>
												</c:if>
												<c:if test="${action.notOnlyActionListener}">
													<circabc:actionLink value="${action.link.value}" action="${action.link.action}" actionListener="${action.link.actionListener}" tooltip="${action.link.tooltip}" id="C${action.link.id}"  >
														<circabc:param id="Param-AListb${action.parameter.id}-${action.link.id}" name="${action.parameter.name}" value="${action.parameter.value}" />
													</circabc:actionLink>
												</c:if>
											</li>
										</ul>
									</circabc:permissionEvaluator>
								</c:forEach>
							</div>
						</c:when>
						<c:otherwise>
							<!--  Closed -->
							<div id="tabMenuIGHLib">
								<img src="${currentContextPath}/images/extension/collapsed.gif" alt="${cmsg.igroot_library_tooltip}" />
								<circabc:actionLink id="left-menu-link-lib-closed" value="#{cmsg.library_menu}" actionListener="#{BrowseBean.clickWai}" tooltip="#{cmsg.library_menu_link_tooltip}" onclick="showWaitProgress();">
									<circabc:param id="param-id-left-menu-link-lib-closed" name="id" value="#{NavigationBean.currentIGRoot.library.id}" />
								</circabc:actionLink>
							</div>
						</c:otherwise>
					</c:choose>
				</circabc:displayer>

		   		<!--  The members service -->
				<circabc:displayer id="left-menu-displayer-member" rendered="#{WaiLeftMenuBean.directoryDisplay}" >
					<c:choose>
						<c:when test="${WaiLeftMenuBean.directoryActivated}">
							<!--  Expanded -->
							<div id="tabMenuIGHMemExp">
								<img src="${currentContextPath}/images/extension/expanded.gif" alt="${cmsg.igroot_members_tooltip}" />
								<circabc:actionLink  id="left-menu-link-members" value="#{cmsg.igroot_members}" actionListener="#{BrowseBean.clickWai}" tooltip="#{cmsg.igroot_members_tooltip}" onclick="showWaitProgress();">
									<circabc:param id="param-id-left-menu-link-members" name="id" value="#{NavigationBean.currentIGRoot.directory.id}" />
								</circabc:actionLink>
							</div>
						</c:when>
						<c:otherwise>
							<!--  Closed -->
							<div id="tabMenuIGHMem">
								<img src="${currentContextPath}/images/extension/collapsed.gif" alt="${cmsg.igroot_members_tooltip}" />
								<circabc:actionLink id="left-menu-link-members-closed" value="#{cmsg.igroot_members}" actionListener="#{BrowseBean.clickWai}" tooltip="#{cmsg.igroot_members_tooltip}" onclick="showWaitProgress();">
									<circabc:param id="param-id-left-menu-link-members-closed" name="id" value="#{NavigationBean.currentIGRoot.directory.id}" />
								</circabc:actionLink>
							</div>
						</c:otherwise>
					</c:choose>
				</circabc:displayer>

				<!--  The event service -->
				<circabc:displayer id="left-menu-displayer-event" rendered="#{WaiLeftMenuBean.eventDisplay}">

					<c:choose>
						<c:when test="${WaiLeftMenuBean.eventActivated}">
							<!--  Expanded -->
							<div id="tabMenuIGHEventExp">
								<img src="${currentContextPath}/images/extension/expanded.gif" alt="${cmsg.event_menu_tooltip}" />
								<circabc:actionLink id="left-menu-link-event" value="#{cmsg.event_menu}" actionListener="#{BrowseBean.clickWai}" tooltip="#{cmsg.event_menu_link_tooltip}" onclick="showWaitProgress();">
									<circabc:param id="param-id-left-menu-link-event" name="id" value="#{NavigationBean.currentIGRoot.event.id}" />
								</circabc:actionLink>
							</div>
							<div id="tabMenuIGHEventSub">
								<c:forEach var="action" items="${EventBean.actions}">
									<circabc:permissionEvaluator value="#{NavigationBean.currentNode}" allow="${action.permission.allow}" id="${action.permission.id}-${action.link.id}">
										<ul>
											<li>
												<c:if test="${action.onlyActionListener}">
													<circabc:actionLink value="${action.link.value}" actionListener="${action.link.actionListener}" tooltip="${action.link.tooltip}" id="LisE${action.link.id}"  >
														<circabc:param id="Param-EVLista${action.parameter.id}-${action.link.id}" name="${action.parameter.name}" value="${action.parameter.value}" />
													</circabc:actionLink>
												</c:if>
												<c:if test="${action.notOnlyActionListener}">
													<circabc:actionLink value="${action.link.value}" action="${action.link.action}" actionListener="${action.link.actionListener}" tooltip="${action.link.tooltip}" id="E${action.link.id}"  >
														<circabc:param id="Param-EVListb${action.parameter.id}-${action.link.id}" name="${action.parameter.name}" value="${action.parameter.value}" />
													</circabc:actionLink>
												</c:if>
											</li>
										</ul>
									</circabc:permissionEvaluator>
								</c:forEach>
							</div>
						</c:when>
						<c:otherwise>
							<!--  Closed -->
							<div id="tabMenuIGHEvent">
								<img src="${currentContextPath}/images/extension/collapsed.gif" alt="${cmsg.event_menu_tooltip}" />
								<circabc:actionLink id="left-menu-link-event-closed" value="#{cmsg.event_menu}" actionListener="#{BrowseBean.clickWai}" tooltip="#{cmsg.event_menu_link_tooltip}" onclick="showWaitProgress();">
									<circabc:param id="param-id-left-menu-inf-event-closed" name="id" value="#{NavigationBean.currentIGRoot.event.id}" />
								</circabc:actionLink>
							</div>
						</c:otherwise>
					</c:choose>
				</circabc:displayer>

		   		<!--  The newsgroup service -->
				<circabc:displayer id="left-menu-displayer-newsgroup" rendered="#{WaiLeftMenuBean.newsgroupDisplay}">
					<c:choose>
						<c:when test="${WaiLeftMenuBean.newsgroupActivated}">
							<!--  Expanded -->
							<div id="tabMenuIGHNewExp"><img src="${currentContextPath}/images/extension/collapsed.gif" alt="${cmsg.igroot_newsgroups_tooltip}" />
								<circabc:actionLink id="left-menu-link-news" value="#{cmsg.igroot_newsgroups}" actionListener="#{BrowseBean.clickWai}" tooltip="#{cmsg.igroot_newsgroups_tooltip}" onclick="showWaitProgress();">
									<circabc:param id="param-id-left-menu-link-news" name="id" value="#{NavigationBean.currentIGRoot.newsgroup.id}" />
								</circabc:actionLink>
							</div>
							<div id="tabMenuIGHNewSub">
								<c:forEach var="action" items="${NewsGroupBean.actions}">
									<circabc:permissionEvaluator value="#{NavigationBean.currentNode}" allow="${action.permission.allow}" id="${action.permission.id}">
										<ul>
											<li>
												<c:if test="${action.onlyActionListener}">
													<circabc:actionLink value="${action.link.value}" actionListener="${action.link.actionListener}" tooltip="${action.link.tooltip}" id="Lis${action.link.id}"  >
														<circabc:param id="Param-NewLista${action.parameter.id}-${action.link.id}" name="${action.parameter.name}" value="${action.parameter.value}" />
													</circabc:actionLink>
												</c:if>
												<c:if test="${action.notOnlyActionListener}">
													<circabc:actionLink value="${action.link.value}" action="${action.link.action}" actionListener="${action.link.actionListener}" tooltip="${action.link.tooltip}" id="C${action.link.id}"  >
														<circabc:param id="Param-NewListb${action.parameter.id}-${action.link.id}" name="${action.parameter.name}" value="${action.parameter.value}" />
													</circabc:actionLink>
												</c:if>
											</li>
										</ul>
									</circabc:permissionEvaluator>
								</c:forEach>
							</div>
						</c:when>
						<c:otherwise>
							<!--  Closed -->
							<div id="tabMenuIGHNew">
								<img src="${currentContextPath}/images/extension/collapsed.gif" alt="${cmsg.igroot_newsgroups_tooltip}" />
								<circabc:actionLink id="left-menu-link-news-closed" value="#{cmsg.igroot_newsgroups}" actionListener="#{BrowseBean.clickWai}" tooltip="#{cmsg.igroot_newsgroups_tooltip}" onclick="showWaitProgress();">
									<circabc:param id="param-id-left-menu-link-news-closed" name="id" value="#{NavigationBean.currentIGRoot.newsgroup.id}" />
								</circabc:actionLink>
							</div>
						</c:otherwise>
					</c:choose>
				</circabc:displayer>

		   		<!--  The survey service -->
				<circabc:displayer id="left-menu-displayer-survey" rendered="#{WaiLeftMenuBean.surveyDisplay}">
					<c:choose>
						<c:when test="${WaiLeftMenuBean.surveyActivated}">
							<!--  Expanded -->
							<div id="tabMenuIGHSurExp"><img src="${currentContextPath}/images/extension/expanded.gif" alt="${cmsg.igroot_surveys_tooltip}" />
								<circabc:actionLink id="left-menu-link-surveys" value="#{cmsg.igroot_surveys}" actionListener="#{BrowseBean.clickWai}" tooltip="#{cmsg.igroot_surveys_tooltip}" onclick="showWaitProgress();">
									<circabc:param id="param-id-left-menu-link-surveys" name="id" value="#{NavigationBean.currentIGRoot.survey.id}" />
								</circabc:actionLink>
							</div>
						</c:when>
						<c:otherwise>
							<!--  Closed -->
							<div id="tabMenuIGHSur">
								<img src="${currentContextPath}/images/extension/collapsed.gif" alt="${cmsg.igroot_surveys_tooltip}" />
								<circabc:actionLink id="left-menu-link-surveys-closed" value="#{cmsg.igroot_surveys}" actionListener="#{BrowseBean.clickWai}" tooltip="#{cmsg.igroot_surveys_tooltip}" onclick="showWaitProgress();">
									<circabc:param id="param-id-left-menu-link-surveys-closed" name="id" value="#{NavigationBean.currentIGRoot.survey.id}" />
								</circabc:actionLink>
							</div>
						</c:otherwise>
					</c:choose>
				</circabc:displayer>

		   		<!--  The administration service -->
				<circabc:displayer id="left-menu-displayer-admin" rendered="#{WaiLeftMenuBean.adminView}">
					<div id="tabMenuIGHAdm"><img src="${currentContextPath}/images/extension/collapsed.gif" alt="${cmsg.igroot_administration_tooltip}" />
						<circabc:actionLink id="left-menu-link-admin" value="#{cmsg.igroot_administration}" action="wai:dialog:adminConsoleWai" actionListener="#{WaiDialogManager.setupParameters}" tooltip="#{cmsg.igroot_administration}" >
							<circabc:param id="param-id-left-menu-link-admin" name="id" value="#{NavigationBean.currentNode.id}" />
						</circabc:actionLink>
					</div>
				</circabc:displayer>
			</div>

			<!-- The search field  -->
			<circabc:simpleSearch id="search" label="#{cmsg.search_box_label}" labelAltText="#{cmsg.search_box_label_tooltip}" button="#{cmsg.search_box_button}" buttonAltText="#{cmsg.search_box_button_tooltip}" actionListener="#{BrowseBean.searchWai}" rendered="#{NavigationBean.currentIGRoot != null}" styleClass="searchSimple" />

			<!-- The clibpboard panel  -->
			<circabc:clipboardShelfItem id="clipboard" collections="#{ClipboardBean.items}" pasteActionListener="#{ClipboardBean.pasteItem}" downloadAllActionListener="#{ClipboardBean.downloadAllItem}" styleClass="clipboard" rendered="#{WaiLeftMenuBean.pastEnableHere}" />
		</div>
	</circabc:displayer>

	<c:catch var="ExceptionRecupUser">
		<c:choose>
			<c:when test="${NavigationBean.isGuest == false}"><%@ include file="self_auth.jsp" %></c:when>
			<c:otherwise><%@ include file="self_guest.jsp" %></c:otherwise>
		</c:choose>
	</c:catch>
	<c:if test="${ExceptionRecupUser != null}">
		<%@ include file="self_guest.jsp" %>
	</c:if>

	<div id="Powered">
		<p class="powered"><h:outputText value="#{cmsg.welcome_powered_alfresco}" escape="false" /></p>
		<p class="powered"><h:outputText value="#{settings.version}" escape="false" /></p>
	</div>
	<div id="ServerName">
		<p class="server">
			<h:outputText value="#{cmsg.server_name}" /><a:outputText id="request_localName_value" value="<%=request.getLocalName()%>"/><br />
			<h:outputText value="#{cmsg.host_name}" /><a:outputText id="request_hostName_value" value="#{WaiLeftMenuBean.hostName}"/>
		</p>
	</div>
</div>




