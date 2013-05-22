/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.api.mail;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Business service to sent a content using email.
 *
 * @author patrice.coppens@trasys.lu
 */
public interface MailMeContentBusinessSrv
{
	/**
	 * Send a nodeRef in email.
	 *
	 * @param contentRef		NodeRef is any node.
	 * @return 					true if the node is successfully sent
	 */
	public boolean send(final NodeRef anyRef);

}
