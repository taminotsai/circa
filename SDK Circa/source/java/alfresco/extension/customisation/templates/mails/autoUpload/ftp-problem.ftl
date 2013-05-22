<#--  Copyright European Community 2006 - Licensed under the EUPL V.1.0  -->
<#--  			http://ec.europa.eu/idabc/en/document/6523				 -->


<#compress>

${formatMessage("mails_common_dear_genereric")}

<br /><br />

${formatMessage("auto_upload_mail_template_ftp_problem_message",
		document.name,
		accessUrl(document),
		titleOrName(interestGroup),
		accessUrl(interestGroup),
		titleOrName(category),
		accessUrl(category))
	}

<br /><br />

${formatMessage("mails_common_signature")}

</#compress>
