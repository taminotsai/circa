/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.api.content;

import java.io.File;
import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Business service manage the attachements. An attachement can be added to any kind of node (usually a topic, but not required).
 *
 * @author Yanick Pignot
 */
public interface AttachementBusinessSrv
{

	/**
	 * Add a hidden attachement in a temporarary space. Used to get the attachement nodeRef reference before the creation of the parent 
	 *
	 * @see eu.cec.digit.circabc.business.impl.props.PropertiesBusinessSrv#computeValidName(String)
	 * @see eu.cec.digit.circabc.business.impl.props.PropertiesBusinessSrv#computeValidUniqueName(NodeRef, String)
	 *
	 * @param name									A filename name (not null)
	 * @param file									An existing file on the fs
	 * @return
	 */
	public NodeRef addTempAttachement(final String name, final File file);
	
	/**
	 * Add a hidden attachement to the given node.
	 *
	 * @see eu.cec.digit.circabc.business.impl.props.PropertiesBusinessSrv#computeValidName(String)
	 * @see eu.cec.digit.circabc.business.impl.props.PropertiesBusinessSrv#computeValidUniqueName(NodeRef, String)
	 *
	 * @param referer								An existing parent
	 * @param name									A filename name (not null)
	 * @param file									An existing file on the fs
	 * @return
	 */
	public NodeRef addAttachement(final NodeRef referer, final String name, final File file);

	/**
	 * Add a linked attachement to the given node.
	 *
	 * @param referer								An existing parent
	 * @param refered								The link to reference
	 * @return
	 */
	public NodeRef addAttachement(final NodeRef referer, final NodeRef refered);


	/**
	 * Get all attachements of any ref. An empty list if no attacheùent fond.
	 *
	 * @param referer									The node to query
	 * @return
	 */
	public List<Attachement> getAttachements(final NodeRef referer);

	/**
	 * Remove a given attachement from its referer.
	 *
	 * @param referer								An existing parent
	 * @param refered								The link to reference
	 * @return
	 */
	public void removeAttachement(final NodeRef referer, final NodeRef refered);

}
