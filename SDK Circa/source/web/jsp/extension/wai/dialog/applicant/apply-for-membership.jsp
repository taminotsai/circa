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

<%-- the main content --%>
<circabc:panel id="contentMainForm" styleClass="contentFullPage">

	<circabc:displayer rendered="#{WaiDialogManager.bean.guest == false}">
			<h:outputText value="<p>#{WaiDialogManager.bean.interestGroupDesc}</p>" escape="false" />
	</circabc:displayer>

	<circabc:displayer rendered="#{WaiDialogManager.bean.guest == true}">
		<h:outputText value="<p>#{cmsg.apply_for_membership_no_log_page_explaination_1}<br />" escape="false" />
		<h:outputText value="#{cmsg.apply_for_membership_no_log_page_explaination_2}</p>" escape="false" />
		<h:outputText value="<p>#{cmsg.apply_for_membership_no_log_page_explaination_3}</p>" escape="false" />
		<h:outputText value="<p>#{cmsg.apply_for_membership_no_log_page_explaination_4}</p>" escape="false" />
		<f:verbatim><br /></f:verbatim>
	</circabc:displayer>

	<%-- Content START --%>
	<circabc:panel id="contact_info" label="#{cmsg.igroot_home_contact_information}" styleClass="panelIGHomeContact" styleClassLabel="panelIGHomeContactLabel" tooltip="#{cmsg.igroot_home_contact_information_tooltip}">
		<h:outputText value="#{WaiDialogManager.bean.contactInfo}" escape="false" />
	</circabc:panel>

	<f:verbatim><br /></f:verbatim>

	<circabc:panel id="panel-application-form" label="#{cmsg.application_form_membership}" styleClass="panelApplyMembershipApplication" styleClassLabel="panelApplyMembershipApplicationLabel" rendered="#{WaiDialogManager.bean.guest == false}" tooltip="#{cmsg.application_form_membership_tooltip}">

		<h:outputText value="#{cmsg.introduce_your_application}<br /><br />" escape="false" />
		<h:outputText value="#{cmsg.information_forwarded_to_diradmins}<br /><br />" escape="false" />
		<circabc:panel id="applicationFormCenter" styleClass="applicationFormCenter">
			<h:inputTextarea id="application-message" value="#{WaiDialogManager.bean.message}" cols="80" rows="10" />
			<f:verbatim><br /></f:verbatim>
			<h:commandButton id="submit" action="#{WaiDialogManager.finish}" value="#{msg.submit}" />
		</circabc:panel>
	</circabc:panel>

     <circabc:panel id="topOfPageAnchorDocDetails" styleClass="topOfPageAnchor"  >
         <%-- Display the "back to top icon first and display the text after." --%>
         <circabc:actionLink value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
         <circabc:actionLink value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
     </circabc:panel>

</circabc:panel>
