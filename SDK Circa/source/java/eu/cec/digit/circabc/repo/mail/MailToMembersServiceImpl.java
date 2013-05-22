/**
 *
 */
package eu.cec.digit.circabc.repo.mail;

import java.util.Set;

import javax.mail.MessagingException;

import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.mail.MailService;
import eu.cec.digit.circabc.service.mail.MailToMembersService;
import eu.cec.digit.circabc.service.profile.ProfileManagerService;
import eu.cec.digit.circabc.service.profile.ProfileManagerServiceFactory;

/**
 * @author clincst
 *
 */
public class MailToMembersServiceImpl implements MailToMembersService {

	private static final Log logger = LogFactory.getLog(MailToMembersServiceImpl.class);

	private MailService mailService;

	private ProfileManagerServiceFactory profileManagerServiceFactory;


	public boolean sendToAllMembers(final NodeRef nodeRef, final String from, final String to, final String subject, final String body, final boolean html) throws MessagingException {
		
		String noReply = mailService.getNoReplyEmailAddress();
		
		final ProfileManagerService profileManagerService = profileManagerServiceFactory.getProfileManagerService(nodeRef);
		if(profileManagerService != null) {
			final Set<String> users = profileManagerService.getMasterUsers(nodeRef);
			for(final String user : users) {
				logger.debug("User:" + user);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Send email to: " + to
						+ "\n...with subject:\n" + subject
						+ "\n...with body:\n" + body);
			}

			return mailService.send(noReply, to, from,subject, body, html);
		} else {
			return false;
		}
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(final MailService mailService) {
		this.mailService = mailService;
	}

	public ProfileManagerServiceFactory getProfileManagerServiceFactory() {
		return profileManagerServiceFactory;
	}

	public void setProfileManagerServiceFactory(final ProfileManagerServiceFactory profileManagerServiceFactory) {
		this.profileManagerServiceFactory = profileManagerServiceFactory;
	}
}
