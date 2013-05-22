<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ page import="javax.faces.context.FacesContext" %>
<%@ page import="org.alfresco.web.app.servlet.FacesHelper" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a"%>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r"%>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>

<%
	// ensure construction of the FacesContext before attemping a service call
	FacesContext fc = FacesHelper.getFacesContext(request, response, application);
%>

<circabc:view>

	<%-- load a bundle of properties I18N strings here --%>
	<f:loadBundle basename="alfresco.messages.webclient" var="msg" />
	<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />


	<%@ page isELIgnored="false"%>

 	<c:set var="currentTitle" value="${cmsg.circabc_uncatched_error_page_main_title}" />
 
	<%@ include file="/jsp/extension/wai/parts/header.jsp" %>
    <%@ include file="/jsp/extension/wai/parts/banner-simple.jsp" %>


    <!-- real content START -->
	<table width="100%" border="0" align="center" cellspacing="0" cellpadding="0" id="content" summary="${cmsg.banner_content_page}" >
	<tr>
		<td>
			<h:form acceptcharset="UTF-8" id="FormPrincipal" styleClass="FormPrincipal"  >
               	   <%-- Content START --%>
				<circabc:panel id="maincontent" styleClass="errorMainContent" >
					<%-- No menu to display --%>

		               <circabc:panel id="contentHeader" styleClass="contentHeaderError">
								<h:outputText value="<br />" styleClass="contentHeaderSubTitle" escape="false"/>
								<h:outputText value="#{cmsg.circabc_uncatched_error_page_title}" styleClass="contentHeaderTitle" escape="false"/>
								<h:outputText value="<br /><br />" styleClass="contentHeaderSubTitle" escape="false"/>
			           </circabc:panel>

		               <circabc:panel id="contentMainErrorPage" styleClass="contentMain">
						   <h:outputText value="<br /><br />" escape="false"/>
						   <circabc:panel id="panelBackToCircabc" styleClass="errorBackToCircabc">
						   		<h:outputText value="#{NavigationBean.circabcUrl}" escape="false"/>
						   </circabc:panel>

						   <h:outputText value="<br /><br />" escape="false"/>
						   <h:outputText value="#{cmsg.circabc_uncatched_error_page_back_apologize}" escape="false"/>
						   <h:outputText value="<br />" escape="false"/>
						   <h:outputText value="#{cmsg.circabc_uncatched_error_page_back_apologize2}" escape="false"/>
						   <h:outputText value="<br />" escape="false"/>
						   <h:outputText value="#{cmsg.circabc_uncatched_error_page_back_apologize3}" escape="false"/>
						   <h:outputText value="<br /><br />" escape="false"/>
						   <h:outputText value="#{cmsg.contact_local_help}" escape="false"/>
						   <h:outputText value="<br />" escape="false"/>
						   <h:outputText value="#{cmsg.we_apologize_for_incovenience}" escape="false"/>
						   <h:outputText value="<br />" escape="false"/>
						   <h:outputText value="#{cmsg.the_circabc_team}" escape="false"/>
		               </circabc:panel>
					</circabc:panel>
                   <%-- Content END --%>

           </h:form>
      <%@ include file="/jsp/extension/wai/parts/footer.jsp" %>
</circabc:view>
