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

<%-- load a bundle of properties I18N strings here --%>
<f:loadBundle basename="alfresco.messages.webclient" var="msg" />
<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<%@ page buffer="32kb" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false"%>


<circabc:panel id="contentMainForm" styleClass="contentMainFormWithBorder">
	<h:outputText value="<p>#{cmsg.edit_other_user_details_introduction}<br /></p>" escape="false"/>

	<circabc:panel id="errorPanel" rendered="#{WaiDialogManager.bean.hasError}" >
		<h:outputText id="TextError" value="#{WaiDialogManager.bean.error}" styleClass="editUserDetailsError" escape="false" />
	</circabc:panel>

	<circabc:panel id="identityPart" styleClass="editUserDetailsPart">
		<h:outputText value="#{cmsg.edit_user_details_identity}"/>
	</circabc:panel>

	<h:outputText value="<br /><table><tr><td>" escape="false"/>

	<h:outputText value="#{cmsg.edit_user_details_firstname}: "/>
	<h:outputText value="</td><td>" escape="false"/>
	<h:inputText value="#{WaiDialogManager.bean.firstName}" size="70"  />
	<h:outputText value="</td></tr><tr><td>" escape="false"/>

	<h:outputText value="#{cmsg.edit_user_details_lastname}: " />
	<h:outputText value="</td><td>" escape="false"/>
	<h:inputText value="#{WaiDialogManager.bean.lastName}" size="70"  />
	<h:outputText value="</td></tr><tr><td>" escape="false"/>

	<h:outputText value="#{cmsg.edit_user_details_email}: " />
	<h:outputText value="</td><td>" escape="false"/>
	<h:inputText value="#{WaiDialogManager.bean.email}" size="70"  />

	<h:outputText value="</td></tr></table>" escape="false"/>

	<circabc:panel id="topOfPageAnchorIdentity" styleClass="topOfPageAnchor"  >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>
	<circabc:panel id="space1" styleClass="divspacer3px" />

	<circabc:panel id="contactInfoPart" styleClass="editUserDetailsPart">
		<h:outputText value="#{cmsg.edit_user_details_contact_information}"/>
	</circabc:panel>

	<h:outputText value="<br /><table><tr><td>" escape="false"/>

	<h:outputText value="#{cmsg.edit_user_details_title_prop}: " />
	<h:outputText value="</td><td>" escape="false"/>
	<h:inputText value="#{WaiDialogManager.bean.title}" size="70"  />
	<h:outputText value="</td></tr><tr><td>" escape="false"/>

	<h:outputText value="#{cmsg.edit_user_details_organisation}: " />
	<h:outputText value="</td><td>" escape="false"/>
	<h:inputText value="#{WaiDialogManager.bean.organisation}" size="70" />
	<h:outputText value="</td></tr><tr><td>" escape="false"/>

	<h:outputText value="#{cmsg.edit_user_details_phone}: " />
	<h:outputText value="</td><td>" escape="false"/>
	<h:inputText value="#{WaiDialogManager.bean.phone}" size="70"  />
	<h:outputText value="</td></tr><tr><td>" escape="false"/>

	<h:outputText value="#{cmsg.edit_user_details_postal_address}: " />
	<h:outputText value="</td><td>" escape="false"/>
	<h:inputText value="#{WaiDialogManager.bean.postalAddress}" size="70" />
	<h:outputText value="</td></tr><tr><td>" escape="false"/>

	<h:outputText value="#{cmsg.edit_user_details_description}: " />
	<h:outputText value="</td><td>" escape="false"/>
	<h:inputText value="#{WaiDialogManager.bean.description}" size="70" />
	<h:outputText value="</td></tr><tr><td>" escape="false"/>

	<h:outputText value="#{cmsg.edit_user_details_fax}: " />
	<h:outputText value="</td><td>" escape="false"/>
	<h:inputText value="#{WaiDialogManager.bean.fax}" size="70" />
	<h:outputText value="</td></tr><tr><td>" escape="false"/>

	<h:outputText value="#{cmsg.edit_user_details_url_address}: " />
	<h:outputText value="</td><td>" escape="false"/>
	<h:inputText value="#{WaiDialogManager.bean.url}" size="70" />

	<h:outputText value="</td></tr></table>" escape="false"/>

	<circabc:panel id="topOfPageAnchorContactInfo" styleClass="topOfPageAnchor" >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>
	<circabc:panel id="space2" styleClass="divspacer3px" />

	<circabc:panel id="userOptionInfoPart" styleClass="editUserDetailsPart">
		<h:outputText value="#{cmsg.edit_user_details_user_options}"/>
	</circabc:panel>

	<h:outputText value="<br /><table><tr><td>" escape="false"/>

	<h:outputText value="#{cmsg.edit_user_details_content_language_filter}: " />
	<h:outputText value="</td><td>" escape="false"/>
	<h:selectOneMenu value="#{WaiDialogManager.bean.contentFilterLanguage}">
		<f:selectItems value="#{WaiDialogManager.bean.contentFilterLanguages}" />
	</h:selectOneMenu>
	<h:outputText value="</td></tr><tr><td>" escape="false"/>

	<h:outputText value="#{msg.language}" />
	<h:outputText value="</td><td>" escape="false"/>
	<h:selectOneMenu id="interface-filter-language" value="#{WaiDialogManager.bean.userInterfaceLanguage}" >
	   	<f:selectItems value="#{WaiDialogManager.bean.languages}" />
	</h:selectOneMenu>
	<h:outputText value="</td></tr><tr><td>" escape="false"/>

	<h:outputText value="#{cmsg.edit_user_details_global_notification}: " />
	<h:outputText value="</td><td>" escape="false"/>
	<h:selectOneMenu value="#{WaiDialogManager.bean.globalNotification}">
		<f:selectItem itemValue="true" itemLabel="#{cmsg.edit_user_details_global_notification_active}" />
		<f:selectItem itemValue="false" itemLabel="#{cmsg.edit_user_details_global_notification_nonactive}" />
	</h:selectOneMenu>
	<h:outputText value="</td></tr><tr><td>" escape="false"/>

	<h:outputText value="#{cmsg.edit_user_details_visibility}: " />
	<h:outputText value="</td><td>" escape="false"/>
	<h:selectOneMenu value="#{WaiDialogManager.bean.visibility}">
		<f:selectItem itemValue="true" itemLabel="#{cmsg.edit_user_details_visibility_active}" />
		<f:selectItem itemValue="false" itemLabel="#{cmsg.edit_user_details_visibility_nonactive}" />
	</h:selectOneMenu>
	<h:outputText value="</td></tr></table>" escape="false"/>

	<circabc:panel id="topOfPageAnchorUserOpt" styleClass="topOfPageAnchor" >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>
	<circabc:panel id="space3" styleClass="divspacer3px" />

	<circabc:panel id="privacyInfoPart" styleClass="editUserDetailsPart">
		<h:outputText value="#{cmsg.self_registration_privacy_statement_title}"/>
	</circabc:panel>

	<f:verbatim><br /></f:verbatim>
	<circabc:actionLink value="#{cmsg.self_registration_privacy_statement_link}" tooltip="#{cmsg.self_registration_privacy_statement_link_tooltip}" href="/html/PrivacyStatement.htm" />

	<circabc:panel id="topOfPageAnchorLast" styleClass="topOfPageAnchor" >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>
</circabc:panel>

