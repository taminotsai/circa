<#--  Copyright European Community 2006 - Licensed under the EUPL V.1.0  -->
<#--  			http://ec.europa.eu/idabc/en/document/6523				 -->


<#compress>

${formatMessage("mails_common_dear_user", fullName(person))}

<br /><br />

${formatMessage("reject_post_mail_body",
		titleOrName(space),
		accessUrl(space),
		titleOrName(interestGroup),
		accessUrl(interestGroup),
		titleOrName(category),
		accessUrl(category)
		)
	}
<br />
<br />

${formatMessage("reject_post_mail_when",
		rejectDate?string("dd-MM-yyyy HH:mm '('zzz')' "),
		me.properties.email,
		fullName(person)
		)
	}

<br />
<br />
<hr />

        <u><i>${formatMessage("reject_post_mail_moderator_comment")}</i></u>:
        <br /><br />

        ${rejectReason?replace("\n","<br />")}

<br /><hr />

       <u><i>${formatMessage("reject_post_mail_you_wrote")}</i></u>:
       <br /><br />

		${rejectedContent?replace("\n","<br />")}

<br /><hr />

<br /><br />

${formatMessage("mails_common_signature")}

</#compress>