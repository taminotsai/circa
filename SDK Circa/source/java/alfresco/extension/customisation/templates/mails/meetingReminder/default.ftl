<#--  Copyright European Community 2006 - Licensed under the EUPL V.1.0  -->
<#--  			http://ec.europa.eu/idabc/en/document/6523				 -->

<#compress>

${formatMessage("mails_common_dear_user", fullName(person))}

<br /><br />

${formatMessage("meeting_author_reminder_body",
		appointment.title,
		accessUrl(appointmentFirstOccurence)
	)}

<br /><br />
<i>	
${formatMessage("create_event_visit_service",
		accessUrl(eventService),
		titleOrName(interestGroup),
		accessUrl(interestGroup),
		titleOrName(category),
		accessUrl(category)
	)}
</i>
<br /><br />

 <#if concatStr(appointment.audienceStatus) == 'Closed'>
	<br />${formatMessage('meeting_author_reminder_closed')}<br />
                      <br /><i><u>${formatMessage('create_event_audience_audience_users')}:</u></i><br />
	<ul>                               
                    <#list appointment.invitedUsers as user>
                        <li>&nbsp;${fullName(user, true)}</li>
                    </#list>
                </ul> 
  </#if>
	
<br /><br />

${formatMessage("mails_common_signature")}

</#compress>


