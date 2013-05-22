/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.business.api.nav;

import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.business.acl.AclAwareWrapper;
import eu.cec.digit.circabc.business.api.props.PropertyItem;

/**
 * Represent a node in the business layer. Must encapsulate and hide all target implementation details.
 *
 * <p>
 * 		The code for this class has been shamelessly stolen from Alfresco,
 *  	but we want to remove our dependency to the alfresco-webclient.jar.
 *
 *  	@see org.alfresco.web.bean.repository.MapNode
 * </p>
 *
 * @author Yanick Pignot
 *
 * TODO to decide how to use and implement this class.
 * 				Do we need a single NavNode for ALL puposes, or generalize it to reflect each circabc node types.
 * 					1.		One class by type (Circabc, Library, NewsPost, ...).
 * 					2.		With perhaps common super interface-class for similar (but not equals type)
 * 							- A IGServiceNode --> NewsServiceNode, LibraryServiceNode.
 * 							- A TopLevelNode  --> RootNode, CatNode, IGNode
 * 							- A ContentNode   --> LibDocument, InfDocument, LibURL, LibPost, NewsPost, ...
 */
public interface NavNode extends Map<String, PropertyItem>, AclAwareWrapper
{

	/**
	 * Get the node reference identifier.
	 *
	 * @return					The node reference
	 */
	public abstract NodeRef getNodeRef();
}
