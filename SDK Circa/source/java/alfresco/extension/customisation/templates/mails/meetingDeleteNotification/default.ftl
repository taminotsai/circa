<#--  Copyright European Community 2006 - Licensed under the EUPL V.1.0  -->
<#--  			http://ec.europa.eu/idabc/en/document/6523				 -->

${formatMessage("meeting_invitation_body",
		appointment.title,
		accessUrl(appointmentFirstOccurence),
		accessUrl(circabc),
		interestGroup.name,
		accessUrl(interestGroup)
	)}

<#compress>
  <#if concatStr(appointment.audienceStatus) = 'Closed'>
	<br />${formatMessage('create_event_audience_audience_closed')}<br />
	<#list appointment.invitedUsers as user>
		&nbsp;&nbsp;&nbsp;${fullName(user, true)}<#if user_has_next><br/></#if>
	</#list>
  </#if>
</#compress>
