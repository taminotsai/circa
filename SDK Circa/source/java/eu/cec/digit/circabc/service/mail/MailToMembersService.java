package eu.cec.digit.circabc.service.mail;

import javax.mail.MessagingException;

import org.alfresco.service.cmr.repository.NodeRef;

public interface MailToMembersService
{

	public abstract boolean sendToAllMembers(final NodeRef nodeRef, final String from, final String to, final String subject,
			final String body, final boolean html) throws MessagingException;

}