package eu.cec.digit.circabc.web.wai.dialog.search;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.web.app.Application;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.bean.repository.Repository;

import eu.cec.digit.circabc.web.wai.dialog.WaiDialog;
import eu.cec.digit.circabc.web.wai.manager.ActionsListWrapper;

public class EditSearchDialog  extends org.alfresco.web.bean.search.EditSearchDialog implements WaiDialog
{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3586954733856359034L;
	
	@Override
    public void init(Map<String, String> parameters) {
    	// TODO Auto-generated method stub
    	super.init(parameters);
    	
    	properties.setSearchDescription(null);
        properties.setSearchName(null);
        properties.setEditSearchName(null);

        // load previously selected search for overwrite
        try
        {
           NodeRef searchRef = new NodeRef(Repository.getStoreRef(), properties.getSavedSearch());
           Node searchNode = new Node(searchRef);
           if (getNodeService().exists(searchRef) && searchNode.hasPermission(PermissionService.WRITE))
           {
              Node node = new Node(searchRef);
              properties.setSearchName(node.getName());
              properties.setEditSearchName(properties.getSearchName());
              properties.setSearchDescription((String)node.getProperties().get(ContentModel.PROP_DESCRIPTION));
           }
           else
           {
              // unable to overwrite existing saved search
              properties.setSavedSearch(null);
           }
        }
        catch (Throwable err)
        {
           // unable to overwrite existing saved search for some other reason
           properties.setSavedSearch(null);
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

    public String getBrowserTitle()
    {
        return Application.getMessage(FacesContext.getCurrentInstance(), "save_search_dialog_browser_title");
    }

    public String getPageIconAltText()
    {
    	return Application.getMessage(FacesContext.getCurrentInstance(), "save_search_dialog_icon_tooltip");
    }

    public boolean isCancelButtonVisible()
    {
        return true;
    }

    public boolean isFormProvided()
    {
        return false;
    }

	
}
	 