/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.business.acl;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Since alfresco implementation of acl checks are performed on final objects (NodeRef, FileInfo, ... ), this class
 * simply helps to have one common interface for many kinds of wrappers.
 *
 * All wrappers that implement getNodeRef method can be a candidate of Acegi ACL checks.
 *
 * @see org.alfresco.repo.security.permissions.impl.acegi.ACLEntryAfterInvocationProvider
 * @see org.alfresco.repo.security.permissions.impl.acegi.ACLEntryVoter
 *
 * TODO override ACLEntryVoter and ACLEntryAfterInvocationProvider and use them in <b>circabc-business-security-context.xml</b>
 *
 * @author Yanick Pignot
 */
public interface AclAwareWrapper
{

	/**
	 * get the wrapper related node reference
	 *
	 * @return
	 */
	public NodeRef getNodeRef();

}
