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

<circabc:panel id="contentMainCatHome" styleClass="contentMain">

		<f:verbatim><p></f:verbatim>
		<h:outputText value="#{cmsg.category_home_text_list_desc}:" escape="false" />
				<f:verbatim></p></f:verbatim>

		<circabc:actionLink id="publicLink" href="#publicAnchor" value="<ul><li><b>#{cmsg.category_home_text_members}</b>:&nbsp;" tooltip="#{cmsg.category_home_text_members_tooltip}" />
		<h:outputText value="#{cmsg.category_home_text_members_desc}</li>" escape="false" />

		<circabc:actionLink id="registeredLink" href="#registeredAnchor" value="<li><b>#{cmsg.category_home_text_registred}</b>:&nbsp;" tooltip="#{cmsg.category_home_text_registred_tooltip}" />
		<h:outputText value="#{cmsg.category_home_text_registred_desc}</li>" escape="false" />

		<circabc:actionLink id="memberLink" href="#memberAnchor" value="<li><b>#{cmsg.category_home_text_public}</b>:&nbsp;" tooltip="#{cmsg.category_home_text_public_tooltip}" />
		<h:outputText value="#{cmsg.category_home_text_public_desc}</li> " escape="false" />

		<f:verbatim></ul><p></f:verbatim>
			<h:outputText value="#{cmsg.category_home_text_11}&nbsp;" escape="false" />
			<circabc:actionLink value="#{cmsg.category_home_text_12}" href="/faces/jsp/extension/wai/help.jsp?page=help_toc.html" tooltip="#{cmsg.category_home_text_12}" immediate="true" />
		<f:verbatim></p></f:verbatim>

	<f:verbatim><br /></f:verbatim>


	<%-- The member nodes panel --%>
	<circabc:panel id="panelCategoryMember" label="#{cmsg.category_home_member_access}" styleClass="panelCatHomeGlobal" styleClassLabel="panelCatHomeLabel" rendered="#{!CategoryBean.membersListEmpty}" tooltip="#{cmsg.category_home_member_access_tooltip}">
		<a name="memberAnchor"></a>
		<circabc:richList id="memberList" viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{CategoryBean.membersAccessIg}" var="m" initialSortColumn="bestTitle">
			<%-- Primary column for details view mode --%>
			<circabc:column primary="true" id="col-member-name" styleClass="columnIgTitle">
				<f:facet name="header">
					<circabc:sortLink label="#{cmsg.category_home_name}" value="bestTitle" tooltipAscending="#{cmsg.category_home_sort_asc}" tooltipDescending="#{cmsg.category_home_sort_desc}"/>
				</f:facet>
				<circabc:actionLink value="#{m.bestTitle}" tooltip="#{m.name}" actionListener="#{BrowseBean.clickWai}" onclick="showWaitProgress();">
					<circabc:param name="id" value="#{m.id}" />
				</circabc:actionLink>
			</circabc:column>
			<circabc:column id="col-member-des" styleClass="columnIgDescription">
				<f:facet name="header">
					<h:outputText value="#{cmsg.category_home_description}" escape="false" />
				</f:facet>
				<h:outputText value="<div class='descContainer'>" escape="false"></h:outputText>
				
				<h:outputText value="<div class='descCollapsed'>" rendered="#{m.needMoreDetails == true }" escape="false"></h:outputText>
				
				<h:outputText value="#{m.noHtmlDescription}" escape="false"/>
				
				<h:outputText value="</div><div  class='moreDetails'>" rendered="#{m.needMoreDetails == true }" escape="false"></h:outputText>
				
				<h:graphicImage alt="show hide" value="/images/extension/icons/navigation-270.png" rendered="#{m.needMoreDetails == true }" ></h:graphicImage>
				
				<h:outputText value="</div>" rendered="#{m.needMoreDetails == true }" escape="false"></h:outputText>
				
				<h:outputText value="</div>" escape="false"></h:outputText>
			</circabc:column>
			<%-- component to display if the list is empty - normally not seen --%>
			<f:facet name="empty">
				<h:outputFormat id="no-list-items-memeber" value="#{cmsg.no_list_items}" escape="false" />
			</f:facet>
		</circabc:richList>
	</circabc:panel>

	<circabc:panel id="topOfPageAnchorCatHomeMembers" styleClass="topOfPageAnchor" rendered="#{!CategoryBean.membersListEmpty}">
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorCatHomeMembers-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorCatHomeMembers-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>

	<%-- The registerd nodes panel --%>
	<circabc:panel id="panelCategoryRegistred" label="#{cmsg.category_home_registered_access}" styleClass="panelCatHomeGlobal" styleClassLabel="panelCatHomeLabel" rendered="#{!CategoryBean.registeredListEmpty}" tooltip="#{cmsg.category_home_registered_access_tooltip}">
		<a name="registeredAnchor"></a>
		<circabc:richList id="registredList" viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{CategoryBean.registeredAccessIg}" var="reg" initialSortColumn="bestTitle">
			<%-- Primary column for details view mode --%>
			<circabc:column primary="true" id="col-registred-name" styleClass="columnIgTitle">
				<f:facet name="header">
					<circabc:sortLink label="#{cmsg.category_home_name}" value="bestTitle" tooltipAscending="#{cmsg.category_home_sort_asc}" tooltipDescending="#{cmsg.category_home_sort_desc}"/>
				</f:facet>
				<circabc:actionLink value="#{reg.bestTitle}" tooltip="#{reg.name}" actionListener="#{BrowseBean.clickWai}" onclick="showWaitProgress();">
					<circabc:param name="id" value="#{reg.id}" />
				</circabc:actionLink>
			</circabc:column>
			<circabc:column id="col-regitred-descr" styleClass="columnIgDescription">
				<f:facet name="header">
					<h:outputText value="#{cmsg.category_home_description}" escape="false" />
				</f:facet>
				
				<h:outputText value="<div class='descContainer'>" escape="false"></h:outputText>
				
				<h:outputText value="<div class='descCollapsed'>" rendered="#{reg.needMoreDetails == true }" escape="false"></h:outputText>
				
				<h:outputText value="#{reg.noHtmlDescription}" escape="false"/>
				
				<h:outputText value="</div><div  class='moreDetails'>" rendered="#{reg.needMoreDetails == true }" escape="false"></h:outputText>
				
				<h:graphicImage alt="show hide" value="/images/extension/icons/navigation-270.png" rendered="#{reg.needMoreDetails == true }" ></h:graphicImage>
				
				<h:outputText value="</div>" rendered="#{reg.needMoreDetails == true }" escape="false"></h:outputText>
				
				<h:outputText value="</div>" escape="false"></h:outputText>
			</circabc:column>
			<circabc:column styleClass="columnAction" id="col-registred-action">
				<f:facet name="header">
					<h:outputText value="#{cmsg.category_home_actions}" escape="false" />
				</f:facet>
				<circabc:actionLink value="#{cmsg.category_home_join_this_group}" tooltip="#{cmsg.category_home_join_this_group_tooltip}" action="wai:dialog:applyForMembershipWai" actionListener="#{WaiDialogManager.setupParameters}"  rendered="#{reg.canJoin}" >
					<circabc:param name="id" value="#{reg.id}" />
					<circabc:param name="service" value="Administration" />
					<circabc:param name="activity" value="Apply for membership" />
				</circabc:actionLink>
				<h:outputText value=" " escape="false" rendered="#{!reg.canJoin}" />
			</circabc:column>
			<%-- component to display if the list is empty - normally not seen --%>
			<f:facet name="empty">
				<h:outputFormat id="no-list-items-registred" value="#{cmsg.no_list_items}" escape="false" rendered="#{CategoryBean.registeredListEmpty}" />
			</f:facet>
		</circabc:richList>
	</circabc:panel>

	<circabc:panel id="topOfPageAnchorCatHomeRegistred" styleClass="topOfPageAnchor" rendered="#{!CategoryBean.registeredListEmpty}">
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorCatHomeRegistred-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorCatHomeRegistred-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>

	<%-- The public nodes panel --%>
	<circabc:panel id="panelCategoryPublic" label="#{cmsg.category_home_public_access}" styleClass="panelCatHomeGlobal" styleClassLabel="panelCatHomeLabel" tooltip="#{cmsg.category_home_public_access_tooltip}">
		<a name="publicAnchor"></a>
		<circabc:richList id="publicList" viewMode="circa" styleClass="recordSet" headerStyleClass="recordSetHeader" rowStyleClass="recordSetRow" altRowStyleClass="recordSetRowAlt" value="#{CategoryBean.publicAccessIg}" var="p" initialSortColumn="bestTitle">
			<%-- Primary column for details view mode --%>
			<circabc:column primary="true" id="col-public-name"  styleClass="columnIgTitle">
				<f:facet name="header">
					<circabc:sortLink label="#{cmsg.category_home_name}" value="bestTitle" tooltipAscending="#{cmsg.category_home_sort_asc}" tooltipDescending="#{cmsg.category_home_sort_desc}"/>
				</f:facet>
				<circabc:actionLink value="#{p.bestTitle}" tooltip="#{p.name}" actionListener="#{BrowseBean.clickWai}" onclick="showWaitProgress();">
					<circabc:param name="id" value="#{p.id}" />
				</circabc:actionLink>
			</circabc:column>
			<circabc:column id="col-public-descr" styleClass="columnIgDescription">
				<f:facet name="header" >
					<h:outputText value="#{cmsg.category_home_description}" escape="false" />
				</f:facet>
					
				<h:outputText value="<div class='descContainer'>" escape="false"></h:outputText>
				
				<h:outputText value="<div class='descCollapsed'>" rendered="#{p.needMoreDetails == true }" escape="false"></h:outputText>
				
				<h:outputText value="#{p.noHtmlDescription}" escape="false"/>
				
				<h:outputText value="</div><div  class='moreDetails'>" rendered="#{p.needMoreDetails == true }" escape="false"></h:outputText>
				
				<h:graphicImage alt="show hide" value="/images/extension/icons/navigation-270.png" rendered="#{p.needMoreDetails == true }" ></h:graphicImage>
				
				<h:outputText value="</div>" rendered="#{p.needMoreDetails == true }" escape="false"></h:outputText>
				
				<h:outputText value="</div>" escape="false"></h:outputText>
				
			</circabc:column>
			<circabc:column styleClass="columnAction" id="col-public-actions">
				<f:facet name="header">
					<h:outputText value="#{cmsg.category_home_actions}" escape="false" />
				</f:facet>
				<circabc:actionLink value="#{cmsg.category_home_join_this_group}" tooltip="#{cmsg.category_home_join_this_group_tooltip}" action="wai:dialog:applyForMembershipWai" actionListener="#{WaiDialogManager.setupParameters}"  rendered="#{p.canJoin}" >
					<circabc:param name="id" value="#{p.id}" />
					<circabc:param name="service" value="Administration" />
					<circabc:param name="activity" value="Apply for membership" />
				</circabc:actionLink>
				<h:outputText value=" " escape="false" rendered="#{!p.canJoin}" />
			</circabc:column>
			<%-- component to display if the list is empty --%>
			<f:facet name="empty">
				<h:outputFormat id="no-list-items-public" value="#{cmsg.no_list_items}" escape="false" rendered="#{CategoryBean.publicListEmpty}" />
			</f:facet>
		</circabc:richList>
	</circabc:panel>

	<circabc:panel id="topOfPageAnchorCatHomeRegistredEnd" styleClass="topOfPageAnchor">
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorCatHomeRegistredEnd-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorCatHomeRegistredEnd-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>
</circabc:panel>



<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript" >
jQuery(document).ready(function(){
	jQuery("div.descContainer").click(function(){
	
		if(jQuery(this).children("div.descCollapsed").css("height")=="120px")
		{
			jQuery(this).children("div.descCollapsed").css("height","40px");
			jQuery(this).children("div.moreDetails").css("top","-25px");
			
			jQuery(this).children("div.moreDetails").children("img").attr("src","<%=request.getContextPath()%>/images/extension/icons/navigation-270.png");
		}
		else
		{
			jQuery(this).children("div.descCollapsed").css("height","120px");
			jQuery(this).children("div.moreDetails").css("top","0px");
			
			jQuery(this).children("div.moreDetails").children("img").attr("src","<%=request.getContextPath()%>/images/extension/icons/navigation-090.png");
		}
	});
});
</script>