package eu.cec.digit.circabc.web.wai.dialog.system;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.alfresco.web.ui.common.Utils;

import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

public class ManageSystemMessageBean extends BaseWaiDialog
{
	private static final long serialVersionUID = 1L;
	private String originalMessage = null;
	private boolean originalShowMessage = false;
	
	private final String MSG_OFF = "manage_system_message_off";
	private final String MSG_ON = "manage_system_message_on";
	private final String MSG_CHANGED = "manage_system_message_changed";

	//***********************************************************************
	//                                                              OVERRIDES
	//***********************************************************************
	
	@Override
	public void init(final Map<String, String> parameters)
	{
		super.init(parameters);
		
		//update the bean properties with the node
		
		originalShowMessage = getSystemMessageBean().isShowMessage();
		this.setShowMessage(originalShowMessage);
		
		originalMessage = getSystemMessageBean().getMessage();
		this.setMessage(originalMessage);
	}
	
	@Override
	protected String finishImpl(FacesContext context, String outcome)
			throws Exception 
	{
		// update the node
		getSystemMessageBean().updateProperties(
				this.isShowMessage(), 
				this.getMessage());
		
		// inform the user
		String status = "";
		if(originalShowMessage != this.isShowMessage())
		{
			if(this.isShowMessage())
			{
				// system message has been turned on
				status += translate(MSG_ON);
			}
			else
			{
				// system message has been turned off
				status += translate(MSG_OFF);
			}
		}
		if(originalMessage.equals(this.getMessage())==false)
		{
			// system message has been changed
			status += translate(MSG_CHANGED);
		}
		
		if(!status.equals(""))
			Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, status);
		
		return CircabcNavigationHandler.CLOSE_WAI_DIALOG_OUTCOME;
	}
	
	public String getPageIconAltText() {
		return null;
	}


	public String getBrowserTitle() {
		return null;
	}
	
	//***********************************************************************
	//                                                         PRIVATE HELPER
	//***********************************************************************
	
	private SystemMessageBean systemMessageBean;
	private SystemMessageBean getSystemMessageBean()
	{	if(systemMessageBean == null)
		{
			@SuppressWarnings("rawtypes")
			Map application = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
			systemMessageBean = (SystemMessageBean)application.get("SystemMessageBean");
		}
		return systemMessageBean;
	}
	
	private boolean showMessage;
	public void setShowMessage(boolean showMessage) {
		this.showMessage = showMessage;
	}
	public boolean isShowMessage() {
		return showMessage;
	}
	
	private String message;
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}


}
