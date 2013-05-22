/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.content.edit;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Bean responsible for the edit document online dialog
 *
 * @author Yanick Pignot
 */
public class EditOnlineDocumentDialog extends CreateContentBaseDialog
{

	private static final String DIALOG_NAME = "editDocumentInlineWai";

	private static final long serialVersionUID = -194979676833262094L;

	public static final String BEAN_NAME = "EditOnlineDocumentDialog";


	/** A logger for the class */
	private static final Log logger = LogFactory.getLog(EditOnlineDocumentDialog.class);

	public static final String MSG_ERROR_UPDATE = "error_update";


	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(getActionNode() == null)
		{
			throw new IllegalArgumentException("The node id is a mandatory parameter");
		}
	}

	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
	    final String selectedContent = getSelectedContent();
		if (getActionNode() != null && selectedContent != null)
	    {
	       try
	       {
	          if (logger.isDebugEnabled())
	          {
	             logger.debug("Trying to update content node Id: " + getActionNode().getId());
	          }

	          // get an updating writer that we can use to modify the content on the current node
	          ContentWriter writer = getContentService().getWriter(getActionNode().getNodeRef(), ContentModel.PROP_CONTENT, true);
	          writer.putContent(selectedContent);

	          attachFiles(getActionNode().getNodeRef());
	       }
	       catch (Throwable err)
	       {
	          Utils.addErrorMessage(translate(MSG_ERROR_UPDATE) + err.getMessage());
	          outcome = null;
	       }
	    }
	    else
	    {
	       logger.warn("WARNING: editInlineOK called without a current Document!");
	    }

	    return outcome;
	}

	@Override
	protected String getDialogName()
	{
		return DIALOG_NAME;
	}

	@Override
	public boolean isAttachementAllowed()
    {
        return true;
    }

	@Override
	protected NodeRef getEditablePost()
	{
		return getActionNode().getNodeRef();
	}

	@Override
	public String getDefaultMode()
	{
		// retrieve the content reader for this node
        final ContentReader reader = getContentService().getReader(getActionNode().getNodeRef(), ContentModel.PROP_CONTENT);

        final String mimetype = reader.getMimetype();

        // calculate which editor screen to display
        if (MimetypeMap.MIMETYPE_TEXT_PLAIN.equals(mimetype) ||
             MimetypeMap.MIMETYPE_XML.equals(mimetype) ||
             MimetypeMap.MIMETYPE_TEXT_CSS.equals(mimetype) ||
             MimetypeMap.MIMETYPE_JAVASCRIPT.equals(mimetype))
        {
        	return MODE_TEXT;
        }
        else
        {
    	   return MODE_HTML;
        }
	}

	public String getBrowserTitle()
	{
		return translate("edit_inline_action_dialog_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("edit_inline_action_dialog_icon_tooltip");
	}
}
