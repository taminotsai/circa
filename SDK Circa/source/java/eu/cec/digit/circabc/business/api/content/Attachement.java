/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.business.api.content;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.business.acl.AclAwareWrapper;

/**
 * @author Yanick Pignot
 */
public interface Attachement extends AclAwareWrapper
{

	/**
	 * Represent the kind of attachement.
	 *
	 * A attachement can be either
	 * -	a link in the repository.
	 * -	a content hidden under the node where it is attached.
	 *
	 * @author Yanick Pignot
	 */
	public enum AttachementType
	{
		REPO_LINK,

		HIDDEN_FILE
	}

	/**
	 * AclAware wrapper method contract to allow permission check on this wrapper
	 *
	 * @see eu.cec.digit.circabc.business.acl.AclAwareWrapper#getNodeRef()
	 */
	public NodeRef getNodeRef();

	/**
	 * Get the node on wich the node is attached
	 *
	 * @return
	 */
	public NodeRef getAttachedOn();

	/**
	 * Get the name of the attachement node
	 *
	 * @return
	 */
	public String getName();

	/**
	 * Get the title of the attachement node
	 *
	 * @return
	 */
	public String getTitle();

	/**
	 * Get the type of the attachement.
	 *
	 * @return
	 */
	public AttachementType geType();

}
