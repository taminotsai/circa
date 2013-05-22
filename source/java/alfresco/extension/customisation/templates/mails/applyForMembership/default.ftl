<#--  Copyright European Community 2006 - Licensed under the EUPL V.1.0  -->
<#--  			http://ec.europa.eu/idabc/en/document/6523				 -->

<#compress>

${formatMessage("mails_common_dear_user", fullName(person))}

<br /><br />

${formatMessage("apply_application_mail_template_body",
		titleOrName(interestGroup),
		accessUrl(interestGroup),
		titleOrName(category),
		accessUrl(category))
	}

<br /><br />

<u><i>${formatMessage("apply_application_mail_template_date")}</i></u>: ${applicationDate?string("dd-MM-yyyy HH:mm '('zzz')' ")}<br />
<u><i>${formatMessage("apply_application_mail_template_name")}</i></u>: ${fullName(me)}<br />
<u><i>${formatMessage("apply_application_mail_template_email")}</i></u>: ${me.properties.email}<br />
<u><i>${formatMessage("apply_application_mail_template_message")}</i></u>: <br />

<hr />

        ${applicationMessage?replace("\n","<br />")}

<br /><hr />

<br /><br />

${formatMessage("mails_common_signature")}

</#compress>