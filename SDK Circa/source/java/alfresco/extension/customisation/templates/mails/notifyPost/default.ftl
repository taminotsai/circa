<#--  Copyright European Community 2006 - Licensed under the EUPL V.1.0  -->
<#--  			http://ec.europa.eu/idabc/en/document/6523				 -->

<#compress>

${formatMessage("mails_common_dear_user", fullName(person))}

<br /><br />

${formatMessage("notification_message_post_header",
                                accessUrl(location),
		titleOrName(location.parent),
		accessUrl(location.parent),
		titleOrName(interestGroup),
		accessUrl(interestGroup),
		titleOrName(category),
		accessUrl(category))
	}

<br /><br /><hr />

<i><u>
	${formatMessage("notification_message_post_haswritten",
		document.properties['cm:creator'])
	}:
</u></i>

<br /><br />

<#if document?exists>
	${document.content?replace("\n","<br />")}
<#else>
	No message found!
</#if>

<br /><hr />

<br /><br />

${formatMessage("mails_common_signature")}

</#compress>
