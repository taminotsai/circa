<#--  Copyright European Community 2006 - Licensed under the EUPL V.1.0  -->
<#--  			http://ec.europa.eu/idabc/en/document/6523 -->
<#compress>

${formatMessage("mails_common_dear_user", fullName(person))}

<br /><br />

${formatMessage("invite_circabc_user_template_mail_body",
		fullName(me),
        titleOrName(interestGroup),
		accessUrl(interestGroup),
		titleOrName(category),
		accessUrl(category))
	}

<br /><br />

<u><i>${formatMessage("invite_circabc_user_template_username")}</i></u>: ${person.properties.userName}
<br />
<u><i>${formatMessage("invite_circabc_user_template_profile")}</i></u>: ${profile}<br />

<br />

${formatMessage("mails_common_signature")}

</#compress>
