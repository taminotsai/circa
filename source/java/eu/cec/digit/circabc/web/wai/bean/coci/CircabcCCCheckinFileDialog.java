/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.bean.coci;

import java.io.File;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.web.scripts.FileTypeImageUtils;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.web.WebClientHelper;
import eu.cec.digit.circabc.web.WebClientHelper.ExtendedURLMode;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.bean.override.CircabcBrowseBean;

/**
 * @author Yanick Pignot
 */
public class CircabcCCCheckinFileDialog extends CircabcCCUpdateFileDialog
{
	private static final Log logger = LogFactory.getLog(CircabcCCCheckinFileDialog.class);
	
	private static final String MSG_ERROR_UNEXPECTED = "checkin_unexpected_error";
	private static final String VALUE_DEFAULT_COPY_LOCATION = "__default_copy_location";
	private static final String VALUE_NEW_COPY_LOCATION = "__new_copy_location";

	private static final long serialVersionUID = 3909601469295466806L;

    public static final String BEAN_NAME = "CircabcCCCheckinFileDialog";

    private static final String DIALOG_CALL = CircabcNavigationHandler.WAI_DIALOG_PREFIX + CircabcNavigationHandler.OUTCOME_SEPARATOR +  "checkInContentWai";

    private static final String CLOSE_AND_LAUNCH_DIALOG = DIALOG_CALL
    					+ CircabcNavigationHandler.OUTCOME_SEPARATOR
    					+ CircabcBrowseBean.PREFIXED_WAI_BROWSE_OUTCOME;

    /** Transient state for checkin process */
    private int checkinStep = 1;

    private String versionNote;
    private boolean minorChange;
    private boolean keepCheckedOut;
    private String copyLocation;

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

			this.checkinStep = 1;
			this.versionNote = null;
	    	this.minorChange = true;
	    	this.keepCheckedOut = false;
	    	this.copyLocation = VALUE_DEFAULT_COPY_LOCATION;

			String url = WebClientHelper.getGeneratedWaiFullUrl(getActionNode(), ExtendedURLMode.HTTP_WAI_BROWSE);

	        getActionNode().getProperties().put("url", url);
	        getActionNode().getProperties().put("workingCopy", getActionNode().hasAspect(ContentModel.ASPECT_WORKING_COPY));
	        getActionNode().getProperties().put("fileType32", FileTypeImageUtils.getFileTypeImage(getActionNode().getName(), false));
		}
	}

    @Override
    protected String finishImpl(FacesContext context, String outcome) throws Exception
    {
        try
        {
        	if(VALUE_NEW_COPY_LOCATION.equals(copyLocation) && isFileUploaded() == false)
            {
            	checkinStep ++;

            	isFinished = false;

            	return CLOSE_AND_LAUNCH_DIALOG;
            }
            else
            {
            	final NodeRef workingCopy = getActionNode().getNodeRef();
            	final NodeRef currentRef = getNavigator().getCurrentNode().getNodeRef();
            	final boolean wasAContent = getBusinessRegistry().getNavigationBusinessSrv().isContent(currentRef);

            	final File file =  getUploadedFile() != null ? getUploadedFile().getFile() : null;

            	final NodeRef original;
            	if(file == null)
            	{
            		original = getCociContentBusinessSrv().checkIn(workingCopy, minorChange, versionNote, keepCheckedOut);
            	}
            	else
            	{
            		original = getCociContentBusinessSrv().checkIn(workingCopy, minorChange, versionNote, keepCheckedOut, file, getUploadedFile().isNotificationDisabled(),getUploadedFile().getFileName());
            	}

				if(keepCheckedOut == false
            			&& original != null
            			&& wasAContent)
                {
                    ((CircabcBrowseBean) getBrowseBean()).clickWai(original);
                }

                return CLOSE_AND_BROWSE_OUTCOME;
            }
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

    public String getBrowserTitle()
    {
        return translate("checkin_browser_title");
    }

    public String getPageIconAltText()
    {
        return translate("checkin_browser_icon_tooltip");
    }

    /**
     * Check if we just upload a file and need to go to last step Return always true
     *
     * @return true if first step for the checkin
     */
    public boolean getCheckforLastStep()
    {
        // Look for file uploaded
        if (isFileUploaded())
        {
            // a File has been upload -> last step
            this.checkinStep = 3;
        }

        return true;
    }

    /**
     * True if first step for the checkin
     *
     * @return true if first step for the checkin
     */
    public boolean getCheckinFirstStep()
    {
        return (this.checkinStep == 1);
    }

    /**
     * True if second step for the checkin
     *
     * @return true if second step for the checkin
     */
    public boolean getCheckinSecondStep()
    {
        return (this.checkinStep == 2);
    }

    /**
     * True if last step for the checkin
     *
     * @return true if last step for the checkin
     */
    public boolean getCheckinLastStep()
    {
        return (this.checkinStep == 3);
    }

	/**
	 * @return the copyLocation
	 */
	public final String getCopyLocation()
	{
		return copyLocation;
	}

	/**
	 * @param copyLocation the copyLocation to set
	 */
	public final void setCopyLocation(String copyLocation)
	{
		this.copyLocation = copyLocation;
	}

	/**
	 * @return the keepCheckedOut
	 */
	public final boolean isKeepCheckedOut()
	{
		return keepCheckedOut;
	}

	/**
	 * @param keepCheckedOut the keepCheckedOut to set
	 */
	public final void setKeepCheckedOut(boolean keepCheckedOut)
	{
		this.keepCheckedOut = keepCheckedOut;
	}

	/**
	 * @return the minorChange
	 */
	public final boolean isMinorChange()
	{
		return minorChange;
	}

	/**
	 * @param minorChange the minorChange to set
	 */
	public final void setMinorChange(boolean minorChange)
	{
		this.minorChange = minorChange;
	}

	/**
	 * @return the versionNote
	 */
	public final String getVersionNote()
	{
		return versionNote;
	}

	/**
	 * @param versionNote the versionNote to set
	 */
	public final void setVersionNote(String versionNote)
	{
		this.versionNote = versionNote;
	}

}
