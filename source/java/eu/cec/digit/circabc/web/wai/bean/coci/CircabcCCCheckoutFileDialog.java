/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.bean.coci;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.web.scripts.FileTypeImageUtils;
import org.alfresco.web.ui.common.Utils;
import org.alfresco.web.ui.common.component.UIActionLink;

import eu.cec.digit.circabc.business.api.BusinessStackError;
import eu.cec.digit.circabc.business.api.content.CociContentBusinessSrv;
import eu.cec.digit.circabc.web.WebClientHelper;
import eu.cec.digit.circabc.web.WebClientHelper.ExtendedURLMode;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * @author Yanick Pignot
 */
public class CircabcCCCheckoutFileDialog extends BaseWaiDialog
{
	private static final long serialVersionUID = 3909601469295466806L;

	public static final String BEAN_NAME = "CircabcCCCheckoutFileDialog";

	private static final String MSG_SUCCESS = "checkout_action_success";
	private static final String MSG_FAILURE = "checkout_action_failure";


	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null)
		{
			if(getActionNode() == null)
			{
				throw new IllegalArgumentException("The node id is a madatory parameter that should be passed either via the Wizard/DialogManager.setupParameters(ActionEvent event). Use the actionListener tag of the action component.");
			}

			String url = WebClientHelper.getGeneratedWaiFullUrl(getActionNode(), ExtendedURLMode.HTTP_WAI_BROWSE);

	        getActionNode().getProperties().put("url", url);
	        getActionNode().getProperties().put("workingCopy", getActionNode().hasAspect(ContentModel.ASPECT_WORKING_COPY));
	        getActionNode().getProperties().put("fileType32", FileTypeImageUtils.getFileTypeImage(getActionNode().getName(), false));
		}
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		return null;
	}

	public String getBrowserTitle()
	{
		return null;
	}

	public String getPageIconAltText()
	{
		return null;
	}

	/**
	 * Action called upon completion of the Check Out file page without going into dialog
	 *
	 * @param event The action event
	 *
	 * @return outcome Depends on calling page
	 */
	public void checkoutFileDirect(ActionEvent event)
	{
		try
		{
			// setup the action
			init(((UIActionLink)event.getComponent()).getParameterMap());

			getCociContentBusinessSrv().checkOut(getActionNode().getNodeRef());

			// refresh and click
			getBrowseBean().refreshBrowsing();

			Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_SUCCESS, getActionNode().getName()));
		}
		catch(final BusinessStackError e)
		{
			for(final String err: e.getI18NMessages())
			{
				Utils.addErrorMessage(err);	
			}			
		}
		catch(final Throwable t)
		{
			Utils.addErrorMessage(translate(MSG_FAILURE, getActionNode().getName(), t.getMessage()));
		}
	}


	/**
	 * @return the cociContentBusinessSrv
	 */
	protected final CociContentBusinessSrv getCociContentBusinessSrv()
	{
		return getBusinessRegistry().getCociContentBusinessSrv();
	}
}
