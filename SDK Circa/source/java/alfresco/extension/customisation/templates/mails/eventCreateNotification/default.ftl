<#--  Copyright European Community 2006 - Licensed under the EUPL V.1.0  -->
<#--  			http://ec.europa.eu/idabc/en/document/6523				 -->


<#compress>

${formatMessage("mails_common_dear_genereric")}

<br /><br />


${formatMessage("create_event_audience_notification_body",
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

<table width="510" border="1" summary="${formatMessage('create_event_audience_table_summary')}">
  <tr>
    <th colspan="2" scope="col"><b>${appointment.title}</b></th>
  </tr>
  <tr>
    <td width="132"><b>${formatMessage('event_create_event_wizard_step1_type')}</b></td>
    <td width="362">${formatMessage(concatStr('event_create_event_wizard_step1_type_', appointment.eventType)?lower_case)}</td>
  </tr>
  <tr>
    <td><b>${formatMessage('event_create_meetings_wizard_step1_date')}</b></td>
    <td>${appointment.startDateAsDate?string('dd-MM-yyyy')}</td>
  </tr>
  <tr>
    <td><b>${formatMessage('event_create_meetings_wizard_step1_start_time')}</b></td>
    <td>${appointment.startTimeAsDate?string('hh:mm')}</td>
  </tr>
  <tr>
    <td><b>${formatMessage('event_create_meetings_wizard_step1_end_time')}</b></td>
    <td>${appointment.endTimeAsDate?string('hh:mm')}</td>
  </tr>
  <tr>
    <td><b>${formatMessage('event_create_meetings_wizard_step1_time_zone')}</b></td>
    <td>${appointment.timeZoneId}</td>
  </tr>
  <tr>
    <td><b>${formatMessage('event_create_meetings_wizard_step1_location')}</b></td>
    <td>${appointment.location}</td>
  </tr>
  <tr>
    <td colspan="2">
    	<b>${formatMessage('event_create_meetings_wizard_step3_title')}:&nbsp;</b>${formatMessage(concatStr('event_create_meetings_wizard_step1_audience_status_', appointment.audienceStatus)?lower_case)}   	
    	
    	<#if concatStr(appointment.audienceStatus) = 'Closed'>
	   		<br />${formatMessage('create_event_audience_audience_closed')}
	   		<br />${formatMessage('create_event_audience_audience_users')}:<br />
			<#list appointment.invitedUsers as user>
                                                                ${fullName(user, true)}<#if user_has_next>,&nbsp;</#if>
			</#list>
                </#if>	
    </td>
  </tr>
  <tr>
    <td colspan="2">
    	<b>${formatMessage('event_create_meetings_wizard_step1_abstract')}:&nbsp;</b><br />${appointment.eventAbstract}
    </td>
  </tr>
  <tr>
    <td colspan="2">
		<b>${formatMessage('event_create_meetings_wizard_step1_message')}:&nbsp;</b><br />${appointment.invitationMessage}
    </td>
  </tr>
</table>


<br /><br />

${formatMessage("mails_common_signature")}

</#compress>
