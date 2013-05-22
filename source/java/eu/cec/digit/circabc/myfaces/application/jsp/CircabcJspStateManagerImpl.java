/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.myfaces.application.jsp;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.alfresco.web.app.AlfrescoNavigationHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.application.jsp.JspStateManagerImpl;

import eu.cec.digit.circabc.service.struct.ManagementService;
import eu.cec.digit.circabc.web.Services;

/**
 * Circabc StateManager implementation for use when views are defined via tags in JSP pages.
 * We need other action when a state is not found in session
 *
 * @author Guillaume
 */
public class CircabcJspStateManagerImpl extends JspStateManagerImpl
{
	private static final Log logger = LogFactory.getLog(CircabcJspStateManagerImpl.class);

	/** The circabc management service */
	private ManagementService managementService;

	public CircabcJspStateManagerImpl()
	{
		if (logger.isTraceEnabled())
		{
			logger.trace("New CircabcJspStateManagerImpl instance created");
		}
	}

	@Override
	public UIViewRoot restoreView(final FacesContext facescontext, final String viewId, final String renderKitId)
	{
		if (logger.isTraceEnabled())
		{
			logger.trace("Entering restoreView");
		}

		Object obj = facescontext.getExternalContext().getSessionMap().get(AlfrescoNavigationHandler.EXTERNAL_CONTAINER_SESSION);

	    if (obj != null && obj instanceof Boolean && ((Boolean)obj).booleanValue())
	    {
	    	facescontext.getExternalContext().getSessionMap().put(
	    			AlfrescoNavigationHandler.EXTERNAL_CONTAINER_SESSION, Boolean.FALSE);
	    	return null;
	    }
		UIViewRoot uiViewRoot = restoreTreeStructure(facescontext, viewId, renderKitId);

		if (uiViewRoot != null)
		{
			uiViewRoot.setViewId(viewId);
			restoreComponentState(facescontext, uiViewRoot, renderKitId);
			final String restoredViewId = uiViewRoot.getViewId();
			if (restoredViewId == null || !(restoredViewId.equals(viewId)))
			{
				if (logger.isTraceEnabled())
				{
					logger.trace("Exiting restoreView - restored view is null.");
				}
				return null;
			}
		}
		else
		{
			//We permit only one other outter access - Original login page for alfresco user
			if (logger.isInfoEnabled())
			{
				logger.info("Alfresco login page wanted");
			}
			return null;
		}

		if (logger.isTraceEnabled())
		{
			logger.trace("Exiting restoreView");
		}

		return uiViewRoot;
	}


	/**
	 * @return the managementService
	 */
	public ManagementService getManagementService()
	{
		if (managementService == null)
		{
			managementService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getManagementService();
		}

		return managementService;
	}

}
