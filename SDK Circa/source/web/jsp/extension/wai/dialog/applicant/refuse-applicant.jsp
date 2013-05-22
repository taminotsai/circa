<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |     http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a" %>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r" %>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>

<%@ page isELIgnored="false"%>

<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<circabc:panel id="refuse-applicant-section-1" styleClass="signup_rub_title" >
   <h:outputText value="&nbsp;#{cmsg.refusing_applicants}" escape="false" />
</circabc:panel>

<f:verbatim><br /></f:verbatim>

<h:outputText value="#{cmsg.refuse_applicant_users}" style="padding-top: 4px; padding-bottom: 4px; font-style: oblique;"/>

<h:dataTable value="#{DialogManager.bean.applicants}" var="row">
     <h:column>
      <h:graphicImage url="/images/icons/user_console.gif" />
     </h:column>
     <h:column>
        <h:outputText value="#{row.firstName}  #{row.lastName}  (#{row.login})" />
     </h:column>
 </h:dataTable>

<f:verbatim><br /></f:verbatim>

<circabc:panel id="refuse-applicant-section-2" styleClass="signup_rub_title" >
	<h:outputText value="#{cmsg.enter_optional_content}" style="padding-top: 4px; padding-bottom: 4px;"/>
</circabc:panel>

<f:verbatim><br /></f:verbatim>

<h:inputTextarea id="RefusalText" value="#{DialogManager.bean.message}" rows="10" cols="75" readonly="false"/>





