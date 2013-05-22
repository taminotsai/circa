<#--  Copyright European Community 2006 - Licensed under the EUPL V.1.0  -->
<#--  http://ec.europa.eu/idabc/en/document/6523                         -->

<#compress>

${formatMessage("mails_common_dear_user", fullName(person))}

<br /><br />

${formatMessage("mail_me_content_body",
		location.name,
		downloadUrl(location),
                                titleOrName(interestGroup),
		accessUrl(interestGroup)
                                titleOrName(category),
		accessUrl(category))
	}


<br /><br /><br />

<#include companyhome.childByNamePath["Data Dictionary/CircaBC/templates/mails/includes/propertyList.ftl"].nodeRef>


<br /><br />

${formatMessage("mails_common_signature")}

</#compress>

