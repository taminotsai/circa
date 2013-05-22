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
	
	<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

	<%@ page isELIgnored="false"%>
	
	<f:verbatim>
	<div>
	</f:verbatim>
		<f:verbatim>
		<h3>
		</f:verbatim><h:outputText value="#{cmsg.configure_auto_upload_dialog_status_title }"></h:outputText>
		<f:verbatim>
		</h3>
		</f:verbatim>
		<h:outputText value="#{cmsg.configure_auto_upload_dialog_status_enabled }" rendered="#{ConfigureAutoUploadDialog.statusBoolean == true }" escape="false"/>
		<h:outputText value="#{cmsg.configure_auto_upload_dialog_status_disabled}" rendered="#{ConfigureAutoUploadDialog.statusBoolean == false }" escape="false"/>
		
		<h:graphicImage value="/images/extension/icons/check-64.png" rendered="#{ConfigureAutoUploadDialog.statusBoolean == true }" styleClass="inlineIcon64" ></h:graphicImage>
		<h:graphicImage value="/images/extension/icons/cross-64.png"  rendered="#{ConfigureAutoUploadDialog.statusBoolean == false }" styleClass="inlineIcon64"></h:graphicImage>
	
		<f:verbatim>	
		<br/>
		<br/>
		</f:verbatim>
		<h:outputLabel value="#{cmsg.configure_auto_upload_dialog_status_title }" for="auto-upload-status"></h:outputLabel>
		<f:verbatim>
		<br/>
		</f:verbatim>
		<h:selectBooleanCheckbox styleClass="textNormalWidth" id="auto-upload-status" value="#{ConfigureAutoUploadDialog.statusBoolean}"></h:selectBooleanCheckbox>
		<h:outputText value="#{cmsg.configure_auto_upload_dialog_status_helper}" escape="false" styleClass="inputHelper"/>
	<f:verbatim>	
	</div>
	</f:verbatim>
	<f:verbatim>
	<br/>

	<fieldset class="formArea">
		<legend>
	</f:verbatim>
		<h:outputText value="#{cmsg.configure_auto_upload_dialog_fieldset_legend }"></h:outputText>
	<f:verbatim></legend>
	</f:verbatim>	
		<h:inputHidden value="#{ConfigureAutoUploadDialog.idConfiguration }" id="idConfigRef"></h:inputHidden>
	
		<h:outputLabel value="#{cmsg.configure_auto_upload_dialog_ftp_host }" for="host"/>
		<h:inputText styleClass="textNormalWidth" id="host" value="#{ConfigureAutoUploadDialog.host }"></h:inputText><f:verbatim><br/></f:verbatim>
		
		<h:outputLabel value="#{cmsg.configure_auto_upload_dialog_ftp_port }" for="port"/>
		<h:inputText styleClass="textNormalWidth" id="port" value="#{ConfigureAutoUploadDialog.port }"></h:inputText><f:verbatim><br/></f:verbatim>
		<f:verbatim>		
		<br/>
		</f:verbatim>
		<h:outputLabel value="#{cmsg.configure_auto_upload_dialog_ftp_path }" for="path"/>
		<h:inputText styleClass="textLongWidth" id="path" value="#{ConfigureAutoUploadDialog.path }"></h:inputText><f:verbatim><br/></f:verbatim>
		<f:verbatim>
		<br/>
		</f:verbatim>
		<h:outputLabel value="#{cmsg.configure_auto_upload_dialog_ftp_username }" for="username"/>
		<h:inputText styleClass="textNormalWidth" id="username" value="#{ConfigureAutoUploadDialog.username }"></h:inputText><f:verbatim><br/></f:verbatim>
		
		<h:outputLabel value="#{cmsg.configure_auto_upload_dialog_ftp_password }" for="password"/>
		<h:inputSecret styleClass="textNormalWidth" id="password" value="#{ConfigureAutoUploadDialog.password }" redisplay="true"></h:inputSecret><f:verbatim><br/></f:verbatim>
		
		<h:outputLabel value=""/>
		<h:commandButton id="testConnection" action="wai:dialog:ConfigureAutoUploadDialog" actionListener="#{ConfigureAutoUploadDialog.testConnection}" value="#{cmsg.configure_auto_upload_dialog_test_connection }"></h:commandButton>
		<h:outputText escape="false" value="#{cmsg.configure_auto_upload_dialog_test_connection_failed }" rendered="#{ConfigureAutoUploadDialog.testResult == -2 }"></h:outputText>
		<h:outputText escape="false" value="#{cmsg.configure_auto_upload_dialog_test_connection_wrong_path }" rendered="#{ConfigureAutoUploadDialog.testResult == -1 }"></h:outputText>
		<h:outputText escape="false"value="#{cmsg.configure_auto_upload_dialog_test_connection_file_not_exist }" rendered="#{ConfigureAutoUploadDialog.testResult == 0 }"></h:outputText>
		<h:outputText escape="false"value="#{cmsg.configure_auto_upload_dialog_test_connection_success }" rendered="#{ConfigureAutoUploadDialog.testResult == 1 }"></h:outputText>
		<f:verbatim>
		<br/>
		
		<br/>
		</f:verbatim>
		
		<h:outputLabel value="#{cmsg.configure_auto_upload_dialog_frequency_title }"></h:outputLabel>
		
		<h:outputText value="#{cmsg.configure_auto_upload_dialog_frequency_day }" />
		<h:selectOneMenu value="#{ConfigureAutoUploadDialog.selectedDayChoice }" id="day_frequency" >
			<f:selectItems value="#{ConfigureAutoUploadDialog.availableDayChoices }"/>
		</h:selectOneMenu>
		<f:verbatim>
		&nbsp;
		</f:verbatim>
		<h:outputText value="#{cmsg.configure_auto_upload_dialog_frequency_at }"/>
		<f:verbatim>
		&nbsp;
		</f:verbatim>
		<h:outputText value="#{cmsg.configure_auto_upload_dialog_frequency_hour }"/>
		<h:selectOneMenu value="#{ConfigureAutoUploadDialog.selectedHourChoice }" id="hour_frequency">
			<f:selectItems value="#{ConfigureAutoUploadDialog.availableHourOptions }"/>
		</h:selectOneMenu>
		<f:verbatim>
		<br/>
		<br/>
		</f:verbatim>
		<h:outputLabel value="#{cmsg.configure_auto_upload_dialog_auto_extract }" for="auto_extract"></h:outputLabel>
		<h:selectBooleanCheckbox styleClass="textNormalWidth" id="auto_extract" value="#{ConfigureAutoUploadDialog.autoExtract}"></h:selectBooleanCheckbox>
		<h:outputText value="#{cmsg.configure_auto_upload_dialog_auto_extract_helper}" escape="false"/>
		<f:verbatim>
		<br/>
		</f:verbatim>
		<h:outputLabel value="#{cmsg.configure_auto_upload_dialog_job_notification }" for="job_notification"></h:outputLabel>
		<h:selectBooleanCheckbox styleClass="textNormalWidth" id="job_notification" value="#{ConfigureAutoUploadDialog.jobNotifications}"></h:selectBooleanCheckbox>
		<h:outputText value="#{cmsg.configure_auto_upload_dialog_status_helper}" escape="false"/>
		<f:verbatim>
		<br/>
		</f:verbatim>
		<h:outputLabel value="#{cmsg.configure_auto_upload_dialog_emails }" for="emails"/>
		<h:inputTextarea styleClass="textNormalWidth" id="emails" value="#{ConfigureAutoUploadDialog.emails }"></h:inputTextarea>
		<h:outputText value="#{cmsg.configure_auto_upload_dialog_emails_helped}" escape="false" styleClass="inputHelper"/>
	<f:verbatim>		
		<br/>
	</fieldset>
	</f:verbatim>