/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.mail;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.alfresco.service.Auditable;
import org.alfresco.service.NotAuditable;
import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.service.event.Meeting;
import eu.cec.digit.circabc.service.event.UpdateMode;



/**
 * Interface for Mail Service. Each mail should be sent via this service. An email is always sent in a non-blocking process.
 * It means that if any error appears, it is will be never throwed.
 *
 * @author Yanick Pignot
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * Commented the key parameter of the @Auditable annotation.
 * Commented the deprecated @PublicService annotation. 
 */
//@PublicService
public interface MailService
{

	/**
	 * @return the no-reply address defined for circabc.
	 */
	@NotAuditable
	public String getNoReplyEmailAddress();

	/**
	 * @return the support team address defined for circabc.
	 */
	@NotAuditable
	public String getSupportEmailAddress();

	/**
	 * @return	the developer team email address defined for circabc
	 */
	@NotAuditable
	public String getDevTeamEmailAddress();
	
	/**
	 * @return	the developer team email address defined for circabc
	 */
	@NotAuditable
	public String getHelpdeskAddress();

	/**
	 * Sent an email as text or html email to a person.
	 *
	 * @param from				The email that send the email
	 * @param to				The email that receive the email
	 * @param subject			The subject of the email
	 * @param body				The body of the email
	 * @param html				True is the email is in HTML format
	 * @return					If whethever the mail is sent or not.
	 */
	@Auditable(/*key =  Auditable.Key.NO_KEY, */parameters = {"from", "to", "replyTo", "subject", "body" })
	public boolean send(String from, String to, String replyTo, String subject, String body, boolean html) throws MessagingException ;

	/**
	 * Sent an email as text or html email to each specified person
	 *
	 * @param from				The email that send the email
	 * @param to				The emails that receive the email
	 * @param subject			The subject of the email
	 * @param body				The body of the email
	 * @param html				True is the email is in HTML format
	 * @return					If whethever the mail is sent or not.
	 */
	@Auditable(/*key =  Auditable.Key.NO_KEY, */parameters = {"from", "to", "replyTo", "subject", "body"})
	public boolean send(String from, List<String> to, String replyTo, String subject, String body, boolean html) throws MessagingException ;

	/**
	 * Sent an email as text or html email to a person with the given node attached
	 *
	 * @param from				The person that send the email
	 * @param to				The person that receive the email
	 * @param subject			The subject of the email
	 * @param body				The body of the email
	 * @param html				True is the email is in HTML format
	 * @return					If whethever the mail is sent or not.
	 */
	@Auditable(/*key =  Auditable.Key.ARG_0, */parameters = {"from", "to", "replyTo", "subject", "body"})
	public boolean sendNode(NodeRef content, String from, String to, String replyTo, String subject, String body, boolean html) throws MessagingException;


	/**
	 * Sent an email as text or html email to each specified person with the given node attached
	 *
	 * @param from				The person that send the email
	 * @param to				The persons that receive the email
	 * @param subject			The subject of the email
	 * @param body				The body of the email
	 * @param html				True is the email is in HTML format
	 * @return					If whethever the mail is sent or not.
	 */
	@Auditable(/*key =  Auditable.Key.ARG_0, */parameters = {"content", "from", "to", "replyTo" , "subject", "body"})
	public boolean sendNode(NodeRef content, String from, List<String> to, String replyTo, String subject, String body, boolean html) throws MessagingException;


	@Auditable(/*key =  Auditable.Key.NO_KEY, */parameters = {"subject", "from", "to", "replyTo", "meeting", "oldMeeting",  "mode"})
	public boolean sendMeetingRequest( String from, List<String> to, String replyTo, Meeting meeting, Meeting  oldMeeting,  UpdateMode mode) throws Exception ;

	@Auditable(/*key =  Auditable.Key.NO_KEY, */parameters = {"subject", "from", "to", "replyTo" , "meeting", "eventDate","mode"})
	public boolean cancelMeeting( String from, List<String> to, String replyTo, Meeting meeting, Date eventDate,UpdateMode mode ) throws Exception ;

}

