/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.notification;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.service.notification.NotificationManagerService;

/**
 * @author filipsl
 *
 */
public class NotificationManagerServiceImpl implements NotificationManagerService
{
	private static final Log logger = LogFactory.getLog(NotificationManagerServiceImpl.class);

	// the services to inject
	private NodeService nodeService;

	/**
	 * @return the nodeService
	 */
	private final NodeService getNodeService()
	{
		return nodeService;
	}

	/**
	 * @param nodeService the nodeService to set
	 */
	public final void setNodeService(NodeService nodeService)
	{
		this.nodeService = nodeService;
	}

	public boolean isPasteAllNotificationEnabled(NodeRef igNodeRef)
	{
		validateNode(igNodeRef);
		return getNodeService().hasAspect(igNodeRef, CircabcModel.ASPECT_NOTIFY_PASTE_ALL);
	}

	public boolean isPasteNotificationEnabled(NodeRef igNodeRef)
	{
		validateNode(igNodeRef);
		return getNodeService().hasAspect(igNodeRef, CircabcModel.ASPECT_NOTIFY_PASTE);
	}

	public void setPasteAllNotificationEnabled(NodeRef igNodeRef, boolean value)
	{
		validateNode(igNodeRef);
		checkNotificationAspect(igNodeRef, value, CircabcModel.ASPECT_NOTIFY_PASTE_ALL);
		
	}

	/**
	 * @param igNodeRef
	 * @param value
	 * @param aspectName 
	 */
	private void checkNotificationAspect(NodeRef igNodeRef, boolean value, QName aspectName)
	{
		if (!getNodeService().hasAspect(igNodeRef, aspectName))
		{
			if(value){
			getNodeService().addAspect(igNodeRef, aspectName, null);
			}
		}
		else
		{
			if(!value){
				getNodeService().removeAspect(igNodeRef, aspectName);
			}
		}
	}

	public void setPasteNotificationEnabled(NodeRef igNodeRef, boolean value)
	{
		validateNode(igNodeRef);
		checkNotificationAspect(igNodeRef, value, CircabcModel.ASPECT_NOTIFY_PASTE);
	}

	/**
	 * @param igNodeRef
	 */
	private void validateNode(NodeRef igNodeRef)
	{

		if (!nodeService.hasAspect(igNodeRef, CircabcModel.ASPECT_IGROOT ))
		{
			throw new IllegalArgumentException("Node "  + igNodeRef + " does not have requied aspect " + CircabcModel.ASPECT_IGROOT  );
		}
	}

}
