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

<circabc:view>

	<%-- load a bundle of properties I18N strings here --%>
	<f:loadBundle basename="alfresco.messages.webclient" var="msg" />
	<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />


	<%@ page isELIgnored="false"%>

 	<c:set var="currentTitle" value="${cmsg.ecas_redirection_page_maintitle}" />

<%@ include file="/jsp/extension/wai/parts/header.jsp" %>
<%@ include file="/jsp/extension/wai/parts/banner.jsp" %>

	<!-- script to to the welcome page  -->
    <script type="text/javascript">
        setTimeout('redirect();', 1000);

        function redirect() {
          document.getElementById('FormPrincipal:goto-circabc').click();
        }
    </script>

            <h:form acceptcharset="UTF-8" id="FormPrincipal">


 		           <%@ include file="/jsp/extension/wai/parts/left-menu.jsp" %>

 		           <%-- Content START --%>
	               <div id="maincontent">

		               <div id="ContentHeader">
		       	             <span class="ContentHeaderTitle"><h:outputText value="#{cmsg.ecas_redirection_page_title}" /></span><br />
			                 <span class="ContentHeaderSubTitle"><h:outputText value="#{cmsg.ecas_redirection_page_subtitle}" /></span>
		               </div>

		               <circabc:errors styleClass="messageGen" infoClass="messageInfo" warnClass="messageWarn" errorClass="messageError" />

		               <div id="ContentMain">

			               <p><h:outputText value="#{cmsg.ecas_redirection_page_introduction}:" escape="false"/></p>

			               <div id="ecasRedirectionForm" class="ecasRedirect">
			                   <h:commandButton id="goto-circabc" action="#{LoginBean.ecasLogin}" value="#{cmsg.ecas_redirection_page_button_caption}" styleClass="logon2" />
			               </div>
	               </div>
                   <%-- Content END --%>
              </div>
           </h:form>
      <%@ include file="/jsp/extension/wai/parts/footer.jsp" %>
</circabc:view>