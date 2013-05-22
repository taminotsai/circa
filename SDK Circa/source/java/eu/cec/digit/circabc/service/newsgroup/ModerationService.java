/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.newsgroup;

import java.util.List;

import org.alfresco.service.Auditable;
import org.alfresco.service.NotAuditable;
import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Interface for the newsgroup moderation.
 * 
 * <pre>
 * 		Ideally containers are TYPE_FORUM or TYPE_TOPIC and contents are TYPE_POST. But it is not required.
 * </pre>
 * 
 * @author Yanick Pignot
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * Commented the key parameter of the @Auditable annotation.
 * Commented the deprecated @PublicService annotation. 
 */
//@PublicService
public interface ModerationService
{
	/**
	 * Set a content being accepted
	 * 
	 * @param content
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "content" })
	public abstract void accept(final NodeRef content);

	/**
	 * Set a container and all its subcontainer being moderated. The contents
	 * will be marked as wainting for approval IF the makeContentWaiting
	 * parameter is set as TRUE.
	 * 
	 * @param container
	 * @param makeContentWaiting
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "container" })
	public abstract void applyModeration(final NodeRef container, final boolean makeContentWaiting);

	/**
	 * Get the abuses signaled on a given node.
	 * 
	 * @param content
	 * @return An empty list if no abuse signaled.
	 */
	@NotAuditable
	public abstract List<AbuseReport> getAbuses(final NodeRef content);

	/**
	 * Return if the given node is accpected
	 * 
	 * @param content
	 * @return
	 */
	@NotAuditable
	public abstract boolean isApproved(final NodeRef content);

	/**
	 * Return if the given node is part of a moderation
	 * 
	 * @param container
	 * @return
	 */
	@NotAuditable
	public abstract boolean isContainerModerated(final NodeRef container);

	/**
	 * Return if the given node is rejected
	 * 
	 * @param content
	 * @return
	 */
	@NotAuditable
	public abstract boolean isRejected(final NodeRef content);

	/**
	 * Return if the given node is waiting for approval
	 * 
	 * @param content
	 * @return
	 */
	@NotAuditable
	public abstract boolean isWaitingForApproval(final NodeRef content);

	/**
	 * Set a content being rejected
	 * 
	 * @param content
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "content", "message" })
	public abstract void reject(final NodeRef content, final String message);

	/**
	 * Signal that an abuse is reported
	 * 
	 * @param content
	 * @param message
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "content", "message" })
	public abstract AbuseReport signalAbuse(final NodeRef content, final String message);

	/**
	 * Signal that an abuse has been dealed.
	 * 
	 * @param content
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "content" })
	public abstract void signalNotAbuse(final NodeRef content);

	/**
	 * Set a content being waiting for approval
	 * 
	 * @param content
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "content" })
	public abstract void waitForApproval(final NodeRef content);

}
