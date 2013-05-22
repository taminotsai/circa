/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.notification;

import java.util.List;
import java.util.Set;

import org.alfresco.service.Auditable;
import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.service.customisation.mail.MailTemplate;
/**
 * @author filips
 * @author Yanick Pignot
 * @author beaurpi
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * Commented the key parameter of the @Auditable annotation.
 * Commented the deprecated @PublicService annotation. 
 */
//@PublicService
public interface NotificationService
{

	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = {"nodeRef", "users"})
	void notify(NodeRef nodeRef, Set<NotifiableUser>  users) throws Exception;
	
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = {"nodeRef", "users", "notificationType"})
	void notify(NodeRef nodeRef, Set<NotifiableUser>  users, NotificationType notificationType) throws Exception;
	
	/***
	 * This method use only emails, there is not any locale feature for translation -> only in english so
	 * @param nodeRef
	 * @param mails
	 * @param templateType
	 * @throws Exception
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = {"nodeRef", "mails", "templateType"})
	void notify(NodeRef nodeRef, List<String>  mails, MailTemplate templateType) throws Exception;

}
