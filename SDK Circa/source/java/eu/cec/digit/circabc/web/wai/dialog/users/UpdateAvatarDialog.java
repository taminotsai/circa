/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.users;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.business.api.BusinessStackError;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.ui.common.component.UIActionLink;
import eu.cec.digit.circabc.web.ui.common.renderer.ErrorsRenderer;
import eu.cec.digit.circabc.web.wai.bean.content.CircabcUploadedFile;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * @author Yanick Pignot
 *
 */
public class UpdateAvatarDialog extends BaseWaiDialog
{
	private static final Log logger = LogFactory.getLog(UpdateAvatarDialog.class);

	private static final long serialVersionUID = -9047838883338266252L;

	private static final String MSG_SUCCESS = "edit_user_details_avatar_success";
	private static final String MSG_ERROR_UNEXPECTED = "update_doc_unexpected_error";
	private static final String MSG_WRONG_FORMAT = "edit_user_details_avatar_wrong_format";

	public static final String BEAN_NAME = "UpdateAvatarDialog";

	private static final String CLOSE_AND_EDIT_USER = CircabcNavigationHandler.CLOSE_DIALOG_OUTCOME
    				+ CircabcNavigationHandler.OUTCOME_SEPARATOR
    				+ CircabcNavigationHandler.WAI_DIALOG_PREFIX
    				+ EditUserDetailsDialog.DIALOG_NAME;

	private static final double MAX_SIZE = 64.0;
	
    private CircabcUploadedFile uploadedFile;

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

			reset();
		}
	}

	@Override
    protected String finishImpl(FacesContext context, String outcome) throws Exception
    {
        try
        {
        	final NodeRef person = getActionNode().getNodeRef();
        	final NodeRef avatarRef = getBusinessRegistry().getUserDetailsBusinessSrv().updateAvatar(person, uploadedFile.getFileName(), uploadedFile.getFile());

        	Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_SUCCESS));
 
        	final EditUserDetailsDialog userDetailsDialog = (EditUserDetailsDialog) Beans.getBean(EditUserDetailsDialog.BEAN_NAME);
        	if(userDetailsDialog.getUserDetails() != null)
        	{
        		userDetailsDialog.getUserDetails().setAvatar(avatarRef);
        	}
        	
        	
            return CLOSE_AND_EDIT_USER;
        }
		catch(final BusinessStackError validationErrors)
		{
			for(final String msg: validationErrors.getI18NMessages())
			{
				Utils.addErrorMessage(msg);
			}
			this.isFinished = false;
			return null;
		}
		catch(Throwable t)
        {
			if(logger.isErrorEnabled()) {
				logger.error("Error", t);
			}
        	isFinished = false;
        	Utils.addErrorMessage(translate(MSG_ERROR_UNEXPECTED, getActionNode().getName(), t.getMessage()));
        	return null;
        }
    }

	public void start(final ActionEvent event)
    {
        // NOTE: this is a temporary solution to allow us to use the new dialog
        // framework beans outside of the dialog framework, we need to do
        // this because the uploading requires a separate non-JSF form, this
        // approach can not be used in the current dialog framework. Until
        // we have a pure JSF upload solution we need this initialisation

    	final UIComponent component = event.getComponent();
    	final Map<String, String> params = ((UIActionLink)component).getParameterMap();

    	init(params);
    }

    public boolean isFileUploaded ()
	{
    	return uploadedFile != null && uploadedFile.getFile() != null;
	}

    /**
	 * @return the uploadedFile
	 */
	protected final CircabcUploadedFile getUploadedFile()
	{
		return uploadedFile;
	}

    protected void reset()
	{
    	if(this.uploadedFile != null && this.uploadedFile.getFile() != null)
    	{
    		try
    		{
    			this.uploadedFile.getFile().delete();
    		}
    		catch(Throwable ignore){}
    	 }

    	uploadedFile = null;
	}

    public void addFile(CircabcUploadedFile fileBean) throws IOException
    {
    	if (fileBean != null && fileBean.getFile() != null)
    	{
    	  	this.uploadedFile = fileBean;
    	  	String newFileName = this.uploadedFile.getFileName() + ".png";
    	  	this.uploadedFile.setFileName(newFileName);
    	  	
    	    //load image
    	    BufferedImage img = null;  
            img = ImageIO.read(this.uploadedFile.getFile());  
            boolean written = false;
            if(img != null)
            {
	            if(img.getWidth() > MAX_SIZE || img.getHeight() > MAX_SIZE)
	            {
	            	// image needs to be scaled down, find scale factor
	            	double scaleFactor = 1.0;
	            	if(img.getWidth() > img.getHeight())
	            		scaleFactor = (double)img.getWidth() / MAX_SIZE;
	            	else
	            		scaleFactor = (double)img.getHeight() / MAX_SIZE;
	            	
	            	// scale it down
	            	int newWidth = (int)((double)img.getWidth() / scaleFactor);
	            	int newHeight = (int)((double)img.getHeight() / scaleFactor);
	            	Image i = img.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
	            	BufferedImage scaledImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
	    			Graphics2D graphic = scaledImg.createGraphics();
	    			graphic.drawImage(i, 0, 0, null);
	            	
	            	// store the scaled image as png
	            	written = ImageIO.write(scaledImg, "png", this.uploadedFile.getFile());
	            }
	            else
	            {
	            	// store the original image as png
	            	written = ImageIO.write(img, "png", this.uploadedFile.getFile());
	            }
	            if(!written)
	            { 
	            	String message = translate("edit_user_details_avatar_save_failed");
	            	ErrorsRenderer.addForcedMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
	            }
            }
            else
            {
            	// no image format
            	reset();
            	String message = translate(MSG_WRONG_FORMAT);
            	ErrorsRenderer.addForcedMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
            }
    	}
    }

    @Override
    public String cancel()
    {
    	reset();
    	return super.cancel();
    }

    @Override
	protected String doPostCommitProcessing(final FacesContext context, final String outcome)
	{
    	reset();
    	return super.doPostCommitProcessing(context, outcome);
	}

	public String getBrowserTitle()
	{
		return null;
	}

	public String getPageIconAltText()
	{
		return null;
	}

}
