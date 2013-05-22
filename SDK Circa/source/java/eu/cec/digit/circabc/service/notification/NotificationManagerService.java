/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.notification;

import org.alfresco.service.Auditable;
import org.alfresco.service.cmr.repository.NodeRef;

/**
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * Commented the key parameter of the @Auditable annotation.
 * Commented the deprecated @PublicService annotation. 
 */
//@PublicService
public interface NotificationManagerService
{
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "igNodeRef"})
	boolean isPasteAllNotificationEnabled(NodeRef igNodeRef) ;
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "igNodeRef"})
	boolean isPasteNotificationEnabled(NodeRef ignodeRef) ;

	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "igNodeRef","value"})
	void setPasteAllNotificationEnabled(NodeRef igNodeRef, boolean value) ;
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "igNodeRef","value"})
	void setPasteNotificationEnabled(NodeRef igNodeRef, boolean value) ;
}
