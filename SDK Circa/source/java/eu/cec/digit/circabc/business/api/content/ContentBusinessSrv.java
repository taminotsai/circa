/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.api.content;

import java.io.File;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Business service to upload a document.
 *
 * @author Yanick Pignot
 */
public interface ContentBusinessSrv
{




	/**
	 * Add a content node a given parent. The name will be unique and unique.
	 *
	 * @see eu.cec.digit.circabc.business.impl.props.PropertiesBusinessSrv#computeValidName(String)
	 * @see eu.cec.digit.circabc.business.impl.props.PropertiesBusinessSrv#computeValidUniqueName(NodeRef, String)
	 *
	 * @param parent								An existing parent
	 * @param name									A filename name (not null)
	 * @param file									An existing file on the fs
	 * @param disableNotification					if true, the notification mechanism will be skipped
	 * @return
	 */
	public NodeRef addContent(final NodeRef parent, final String name, final File file, final boolean disableNotification);

	/**
	 * Add a content node a given parent. The name will be unique and unique.
	 *
	 * @param parent								An existing parent
	 * @param name									A filename name (not null)
	 * @param file									An existing file on the fs
	 * @return
	 */
	public NodeRef addContent(final NodeRef parent, final String name, final File file);

}
