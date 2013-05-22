<#--  Copyright European Community 2006 - Licensed under the EUPL V.1.0  -->
<#--  			http://ec.europa.eu/idabc/en/document/6523				 -->

<#compress>

${formatMessage("mails_common_dear_user", fullName(person))}

<br /><br />

${formatMessage("share_space_body",
		fullName(me),
		location.name,
		accessUrl(location),
		titleOrName(interestGroup),
		accessUrl(interestGroup),
		titleOrName(category),
		accessUrl(category))
	}

<br /><br /><br />

<u><i>${formatMessage("share_space_your_ig")}</i></u>: ${formatMessage("mails_common_href", titleOrName(yourinterestGroup), accessUrl(yourinterestGroup))} ${formatMessage("share_space_your_ig_perm")}<br />
<u><i>${formatMessage("share_space_permission")}</i></u>: ${profile}<br />
<u><i>${formatMessage("share_space_location")}</i></u>: ${formatMessage("mails_common_href", circabcPath(location), accessUrl(location))}<br />
<u><i>${formatMessage("share_space_access")}</i></u>: ${formatMessage("mails_common_href", accessUrl(location), accessUrl(location))}<br />

<br /><br />

${formatMessage("mails_common_signature")}

</#compress>