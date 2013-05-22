/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.api.props;

import java.io.Serializable;

import eu.cec.digit.circabc.business.api.nav.NavNode;

/**
 * Simple interface used to implement small classes capable of calculating dynamic property values
 * for Nodes at runtime. This allows bean responsible for building large lists of Nodes to
 * encapsulate the code needed to retrieve non-standard Node properties. The values are then
 * calculated on demand by the property resolver.
 *
 * When a node is reset() the standard and other props are cleared. If property resolvers are used
 * then the non-standard props will be restored automatically as well.
 *
 * <p>
 * 	 	The code for this class has been shamelessly stolen from Alfresco,
 *  	but we want to remove our dependency to the alfresco-webclient.jar.
 *
 *  	@see org.alfresco.web.bean.repository.NodePropertyResolver
 * </p>
 *
 * @author Yanick Pignot
 */
public interface RuntimePropertyResolver
{

	/**
	 * Get the property value for this resolver
	 *
	 * @param node       Node this property is for
	 *
	 * @return property value
	 */
	 public Serializable get(NavNode node);

}
