<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>

    <%@ page isELIgnored="false"%>

	<div id="tabMenu">
		<div id="tabMenuBlue">${cmsg.self_main_menu}</div>
		<div id="tabMenuSub">
			<ul>
				<li>
					<circabc:actionLink value="#{cmsg.self_browse_categories}" tooltip="#{cmsg.self_browse_categories_tooltip}"  actionListener="#{BrowseBean.clickWai}" rendered="#{NavigationBean.circabcHomeNode.rootCategoryHeader != null}" immediate="true"  onclick="showWaitProgress();">
						<circabc:param name="id" value="#{NavigationBean.circabcHomeNode.rootCategoryHeader.id}" />
					</circabc:actionLink>
				</li>
				<li> <circabc:actionLink value="#{cmsg.self_logout} (#{NavigationBean.currentUserName})" action="#{LoginBean.logout}" tooltip="#{cmsg.self_logout_tooltip}" immediate="true" /></li>
				<li> <circabc:actionLink value="#{cmsg.self_help}" href="/faces/jsp/extension/wai/help.jsp?page=help_toc.html" tooltip="#{cmsg.self_help_tooltip}" immediate="true" /></li>
				<c:if test="${WaiLeftMenuBean.interestGroupDisplay == false}">
					<li>
						<circabc:actionLink value="#{cmsg.self_admininitration}" action="wai:dialog:adminConsoleWai" actionListener="#{WaiDialogManager.setupParameters}" tooltip="#{cmsg.self_admininitration_tooltip}">
							<circabc:param name="id" value="#{NavigationBean.currentNode.id}" />
						</circabc:actionLink>
					</li>
				</c:if>
			</ul>
		</div>
	</div>
	<div id="tabLogo">
      <p><a href="http://ec.europa.eu/isa/" title="${cmsg.self_isa_1_title}" ><img src="${currentContextPath}/images/extension/isa_logo2.png" alt="${cmsg.self_isa_2_alt}" width="200"/></a></p>
	  <%--  Should test if the current dialog is WAI or Not --%>
      <c:if test="${true}">
      	<p><a href="http://www.w3.org/WAI" title="${cmsg.self_w3c_wai_1_title}" tooltip="${cmsg.self_w3c_wai_1_tooltip}" > <img src="${currentContextPath}/images/extension/w3c2.gif" alt="${cmsg.self_w3c_wai_1_alt}" /></a></p>
      </c:if>
	</div>
