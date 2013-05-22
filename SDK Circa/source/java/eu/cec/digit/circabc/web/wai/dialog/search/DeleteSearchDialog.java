package eu.cec.digit.circabc.web.wai.dialog.search;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.AccessStatus;
import org.alfresco.web.ui.common.Utils;

import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.service.profile.permissions.DirectoryPermissions;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;
import eu.cec.digit.circabc.web.wai.manager.ActionsListWrapper;

public class DeleteSearchDialog extends BaseWaiDialog  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4693521428836073592L;
	
	static final String MSG_DELETE_SEACH_DIALOG_PERMISSION = "delete_seach_dialog_permission";
	
	
	@Override
	public void init(final Map<String, String> parameters)
	{
		super.init(parameters);
	}
	
	@Override
    protected String finishImpl(FacesContext context, String outcome) throws Exception
    {
	 
	String locationSearch = (String) getNodeService().getProperty(getActionNode().getNodeRef(), CircabcModel.PROP_LOCATION);
	NodeRef igNodeRef = new NodeRef(locationSearch);
	if(getPermissionService().hasPermission(igNodeRef, DirectoryPermissions.DIRADMIN.toString()) == AccessStatus.ALLOWED)
    {
		 AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>()
			        {
			            public Object doWork()
			            {
			            	getNodeService().deleteNode(getActionNode().getNodeRef());
			                return null;
			            }
			        }, AuthenticationUtil.getSystemUserName());
		 return  CircabcNavigationHandler.CLOSE_WAI_DIALOG_OUTCOME;
    }
	else
	{
		Utils.addErrorMessage(translate(MSG_DELETE_SEACH_DIALOG_PERMISSION));
		return null;
	}
    }

	@Override
    public boolean getFinishButtonDisabled()
    {
        return false;
    }

    public ActionsListWrapper getActionList()
    {
        return null;
    }


    public boolean isCancelButtonVisible()
    {
        return true;
    }

    public boolean isFormProvided()
    {
        return false;
    }
    

    public String getBrowserTitle()
	{
		return translate("delete_search_dialog_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("delete_search_dialog_icon_tooltip");
	}
    /**
     * Returns the confirmation to display to the user before deleting the content.
     *
     * @return The formatted message to display
     */
    public String getConfirmMessage()
    {
          return translate("delete_seach_dialog_confirmation", getActionNode().getName());
     }

}
