/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.notification;

import java.util.Set;

import org.alfresco.service.Auditable;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Low level service that manage
 * 
 * @author yanick pignot
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * Commented the key parameter of the @Auditable annotation.
 * Commented the deprecated @PublicService annotation. 
 */
//@PublicService
public interface NotificationSubscriptionService
{

	/**
	 * Get the AuthorityNotification that is granted/denied to the given
	 * authority
	 * 
	 * @param nodeRef
	 *            - the reference to the node
	 * @param authority
	 *            - the authority that match the permission
	 * @throws InvalidNodeRefException
	 *             error if the given node is invalid or not under an
	 *             InterestGroup
	 * @return notification status
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "nodeRef", "authority" })
	public AuthorityNotification getAuthorityNotificationStatus(final NodeRef nodeRef, final String authority) throws InvalidNodeRefException;

	/**
	 * Get all the notifiable users. The process compute the notification status
	 * from the given node to its interest group.
	 * 
	 * @param nodeRef
	 *            the from from which the status will be computed (can be any
	 *            kind of node - space, content, topic, ....)
	 * @throws InvalidNodeRefException
	 *             error if the given node is invalid or not under an
	 *             InterestGroup
	 * @return the set of users to notify
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "nodeRef" })
	public Set<NotifiableUser> getNotifiableUsers(final NodeRef nodeRef) throws InvalidNodeRefException;

	/**
	 * Get all the AuthorityNotification that are granted/denied to the current
	 * authentication for the given node
	 * 
	 * @param nodeRef
	 *            - the reference to the node
	 * @return the set of notification status
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "nodeRef" })
	public Set<AuthorityNotification> getNotifications(final NodeRef nodeRef);

	/**
	 * Get the notification report of a given user authority for a given node
	 * 
	 * @param nodeRef
	 *            - the reference to the node
	 * @param authority
	 *            - the authority that match the permission
	 * @return the full report
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "nodeRef", "userAuthority" })
	public UserNotificationReport getUserNotificationReport(final NodeRef nodeRef, final String userAuthority);

	/**
	 * Remove the notification settings of the given authority on the current
	 * node.
	 * 
	 * @param nodeRef
	 *            where the notification status settings will removed
	 * @param authority
	 *            who losts the notification status setting
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "nodeRef", "authority" })
	public void removeNotification(final NodeRef nodeRef, final String authority);

	/**
	 * Set a specific notification status on a node.
	 * 
	 * @param nodeRef
	 * @param authority
	 * @param permission
	 * @param allow
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "nodeRef", "authority", "status" })
	public void setNotificationStatus(final NodeRef nodeRef, final String authority, final NotificationStatus status);
}
