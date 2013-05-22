package eu.cec.digit.circabc.web.wai.dialog.coci;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.app.context.UIContextService;
import org.alfresco.web.bean.coci.CCProperties;
import org.alfresco.web.bean.coci.CCUndoCheckoutFileDialog;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.bean.override.CircabcBrowseBean;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * Bean responsible for the undo checkout dialog
 *
 * @author yanick pignot
 */
public class UndoCheckoutDialog extends BaseWaiDialog
{
	private static final long serialVersionUID = 8166875319281692712L;

	public static String BEAN_NAME = "UndoCheckoutDialog";

	private CCUndoCheckoutFileDialog undoCheckoutFileDialog;

	private CCProperties ccProperties;

	private Node lastCurrentNode;


	@Override
	public void init(Map<String, String> parameters)
	{

		super.init(parameters);

		// in the restaure mode, the parameters can be null
		if(parameters != null)
		{
			String id  = parameters.get(NODE_ID_PARAMETER);
			// test if the application is in right state (id should be equalt to the current node)
			lastCurrentNode = getNavigator().getCurrentNode();
			if(!lastCurrentNode.getId().equals(id))
			{
				// the verfication of id parameter will be perfom here
				getNavigator().setCurrentNodeId(id);
				UIContextService.getInstance(FacesContext.getCurrentInstance()).spaceChanged();
			}

			ccProperties = new CCProperties();
	        ccProperties.setDocument(getActionNode());

			getUndoCheckoutFileDialog().setProperty(ccProperties);

			logRecord.setService(super.getServiceFromActionNode());
		}
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		if (getActionNode() != null)
		{
			NodeRef locked = null;

			if(getNavigator().getCurrentNodeType().equals(NavigableNodeType.LIBRARY_CONTENT))
			{
				// the dialog has to be called via the document details page
				locked = getLockedDocument();
			}

			final String resultOutcome = undoCheckoutFileDialog.undoCheckoutFile(FacesContext.getCurrentInstance(), "");

			// Handle error
			if (resultOutcome == null)
				return null;

			if(wasCurrentnodeAContainer())
			{
				// return the the original space
				getBrowseBean().clickWai(lastCurrentNode.getId());
			}
			else if(locked != null)
			{
				// don't return to the current node, it is dropped.
				// But to the original one.
				getBrowseBean().clickWai(locked);
			}
		}

		return CircabcNavigationHandler.CLOSE_WAI_DIALOG_OUTCOME
						+ CircabcNavigationHandler.OUTCOME_SEPARATOR
						+ CircabcBrowseBean.PREFIXED_WAI_BROWSE_OUTCOME;
	}

	public String getBrowserTitle()
	{
		return translate("title_undocheckout");
	}

	public String getPageIconAltText()
	{
		return translate("undocheckout_icon_tooltip");
	}

	@Override
	public String getFinishButtonLabel()
	{
		return translate("undo_checkout");
	}

	@Override
	public String getContainerDescription()
	{
		return translate("undocheckout_title_desc");
	}

	@Override
	public String getContainerTitle()
	{
		return translate("undocheckout_title", getActionNode().getName());
	}

	private NodeRef getLockedDocument()
	{
		return (NodeRef) getActionNode().getProperties().get(ContentModel.PROP_COPY_REFERENCE);
	}


	public final CCUndoCheckoutFileDialog getUndoCheckoutFileDialog()
	{
		return undoCheckoutFileDialog;
	}

	public final void setUndoCheckoutFileDialog(CCUndoCheckoutFileDialog undoCheckoutFileDialog)
	{
		if(undoCheckoutFileDialog == null)
		{
			undoCheckoutFileDialog = Beans.getCCUndoCheckoutFileDialog();
		}
		this.undoCheckoutFileDialog = undoCheckoutFileDialog;
	}

	public boolean wasCurrentnodeAContainer()
	{
		final QName modelType = lastCurrentNode.getType();

		// look for Space or File nodes
        if ((ContentModel.TYPE_FOLDER.equals(modelType) || getDictionaryService().isSubClass(modelType, ContentModel.TYPE_CONTAINER)) )
        {
        	return true;
        }
        else
        {
        	return false;
        }
	}
}
