<#--  Copyright European Community 2006 - Licensed under the EUPL V.1.0  -->
<#--  			http://ec.europa.eu/idabc/en/document/6523				 -->


<#compress>

${formatMessage("mails_common_dear_user", fullName(person))}

<br /><br />

${formatMessage("signal_abuse_mail_body",
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

${formatMessage("signal_abuse_mail_when",
		abuseDate?string("dd-MM-yyyy HH:mm '('zzz')' "),
		me.properties.email,
		fullName(person)
		)
	}

<br />
<br />
<hr />

        <u><i>${formatMessage("signal_abuse_mail_user_comment", fullName(me))}</i></u>:
        <br /><br />

        ${abuseReason?replace("\n","<br />")}

<br /><hr />

		<#if document?exists>
		    <u><i>${formatMessage("signal_abuse_mail_post_content", document.properties.creator, document.properties.created?string("dd-MM-yyyy"))}</i></u>:
	       	<br /><br />

			${document.content?replace("\n","<br />")}
		<#else>
			No message found!
		</#if>

<br /><hr />

<br /><br />

${formatMessage("mails_common_signature")}

</#compress>