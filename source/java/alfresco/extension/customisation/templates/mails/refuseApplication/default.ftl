<#--  Copyright European Community 2006 - Licensed under the EUPL V.1.0  -->
<#--  			http://ec.europa.eu/idabc/en/document/6523				 -->


<#compress>

${formatMessage("mails_common_dear_user", fullName(person))}

<br /><br />

${formatMessage("reject_application_mail_template_body",
		titleOrName(interestGroup),
		accessUrl(interestGroup),
		titleOrName(category),
		accessUrl(category),
		person.properties.userName
		)
	}

<br />
<br />
<hr />

        <u><i>${formatMessage("reject_application_mail_template_comment", date?string("dd-MM-yyyy"))}</i></u>: 
        <br /><br />

        ${reason?replace("\n","<br />")}

<br /><hr />

<br /><br />

${formatMessage("mails_common_signature")}

</#compress>