package eu.cec.digit.circabc.web.wai.dialog.ig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.node.archive.NodeArchiveService;
import org.alfresco.service.cmr.lock.NodeLockedException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.rule.RuleService;
import org.alfresco.web.app.Application;
import org.alfresco.web.app.servlet.FacesHelper;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.business.api.link.LinksBusinessSrv;
import eu.cec.digit.circabc.business.api.link.ShareSpaceItem;
import eu.cec.digit.circabc.service.profile.Profile;
import eu.cec.digit.circabc.service.profile.ProfileManagerService;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.bean.override.CircabcBrowseBean;
import eu.cec.digit.circabc.web.wai.dialog.generic.DeleteNodeDialog;



/**
 *	Bean that backs the "Delete Interest Group" WAI Dialog.
 *
 * @author Stephane Clinckart
 */
public class DeleteIgNodeDialog extends DeleteNodeDialog {

	/** */
    private static final long serialVersionUID = -3366182849266786067L;

    private static final Log logger = LogFactory.getLog(DeleteIgNodeDialog.class);

    /** Public JSF Bean name */

    public static final String BEAN_NAME = "DeleteIgNodeDialog";

    private static final String MSG_IG_CONTAINS_LOCKED_DOCUMENTS = "ig_contains_locked_documents";
    private static final String MSG_SUCCESS = "delete_ig_node_dialog_success";

    final Map<Profile, List<NodeRef>> profilesExportedUsedByIG = new HashMap<Profile, List<NodeRef>>();

	private List<ShareSpaceItem> sharedSpaces = new ArrayList<ShareSpaceItem>();

	private transient RuleService ruleService;
	
	/** NodeArchiveService bean reference */
    transient private NodeArchiveService nodeArchiveService;

	private NodeRef archivedNode;
	private boolean deleteLog ;
	private boolean purgeData ;
	
	

    @Override
    public void init(final Map<String, String> parameters) {
    	try {
	        super.init(parameters);
	        deleteLog = true;
	        purgeData = true;
	        final NodeRef currentNode = getActionNode().getNodeRef();
	        final ProfileManagerService profileManagerService = getProfileManagerServiceFactory().getProfileManagerService(currentNode);
	        final List<Profile> profiles = profileManagerService.getProfiles(currentNode);
	        profilesExportedUsedByIG.clear();
	        List<NodeRef> igs;
	        for(final Profile profile : profiles) {
	            if(profile.isExported()) {
	            	igs = new ArrayList<NodeRef>(profileManagerService.getRefInExportedProfile(currentNode, profile.getProfileName()));
	            	if(igs.size() > 0)
	            	{
	            		profilesExportedUsedByIG.put(profile, igs);
	            	}
	            }
	        }

	        sharedSpaces.clear();
	        final List<ShareSpaceItem> spItems = getLinksBusinessSrv().findSharedSpaces(currentNode);
	        if(spItems != null)
	        {
	        	sharedSpaces.addAll(spItems);
	        }
    	}
    	catch(final Exception ex)
    	{
    		if(logger.isErrorEnabled()) {
    			logger.error("init failed:" + ex.getMessage(), ex);
    		}
    	}


    }

    public boolean getDeletionAllowed() {
    	return getProfilesExportedUsedByIGIsEmpty() && getSharedSpacesIsEmpty() ;
    }

	/**
	 * @return
	 */
    public boolean getSharedSpacesIsEmpty()
	{
		return sharedSpaces.isEmpty();
	}

	/**
	 * @return
	 */
	public boolean getProfilesExportedUsedByIGIsEmpty()
	{
		return (profilesExportedUsedByIG.size() == 0);
	}


	@Override
	protected String doPostCommitProcessing(FacesContext context, String outcome)
	{
		if (purgeData)
		{
			if (archivedNode != null)
			{
				if (this.getNodeService().exists(archivedNode))
				{ 	
					this.getNodeArchiveService().purgeArchivedNode(archivedNode);
				}
				else
				{
					if (logger.isWarnEnabled() )
					{
						logger.warn("Unable to purge after deleting interest group:");
						logger.warn("Archive node " +archivedNode.toString()+ " does not exists");
					}
				}
			}
		}
		return super.doPostCommitProcessing(context, outcome);
		
	}

    public List<ProfilesListWrapper> getProfiles()
    {
    	final List<ProfilesListWrapper> profiles = new ArrayList<ProfilesListWrapper>();
    	try
    	{
	    	String profileName;
	    	for(final Entry<Profile, List<NodeRef>> entry : profilesExportedUsedByIG.entrySet())
	    	{
	    		final Profile profile = entry.getKey();
	    		profileName = profile.getProfileDisplayName();
	    		if(logger.isErrorEnabled()) {
	    			logger.error("Exported Profile:" + profileName + " used in:");
	    		}

	    		final List<NodeRef> igs = entry.getValue();
	    		final List<String> igsList = new ArrayList<String>(igs.size());
	    		String title;
	    		for(final NodeRef nodeRef : igs)
	    		{
	    			title = getBestTitle(nodeRef);
	    			if(logger.isErrorEnabled()) {
	        			logger.error("IG:" + title);
	        		}
	    			igsList.add(title);
	    		}

	    		profiles.add(new ProfilesListWrapper(profileName, igsList));
	    	}
    	}
    	catch(final Exception ex)
    	{
    		if(logger.isErrorEnabled()) {
    			logger.error("getProfiles:" + ex.getMessage(), ex);
    		}
    	}
    	return profiles;
    }

    @Override
    public boolean getFinishButtonDisabled()
    {
    	return ! getDeletionAllowed();
    }

    @Override
    protected String finishImpl(final FacesContext context, final String outcome) throws Exception
    {
    	try
    	{
	        NodeRef parent = null;
	        archivedNode = null;

	        if(getActionNode() != null && actionNodeType != null) {
	        	final String currentName = getActionNode().getName();
	            final NodeRef currentNode = getActionNode().getNodeRef();
	            if (logger.isDebugEnabled()) {

	                final String name = (String) getNodeService().getProperty(currentNode, ContentModel.PROP_NAME);
	                logger.debug("Trying to delete Interest Group: " + name + " with ID:" + getActionNode().getId());
	            }
	            parent = getNodeService().getPrimaryParent(currentNode).getParentRef();
	            long igID = (Long) this.getNodeService().getProperty(currentNode, ContentModel.PROP_NODE_DBID);

	            //delete the node
	            try {
	            	// A  rule was trying to add LibraryAspect in an archived node,
	            	// where a non-owner hasn't right to perform AddAspect.
	            	getRuleService().disableRules();
	            	if (purgeData)
	            	{
	            		this.getNodeService().addAspect(currentNode, ContentModel.ASPECT_TEMPORARY, null);
	            	}
	                this.getNodeService().deleteNode(currentNode);
	                Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_SUCCESS, currentName));
	            }
	            catch(final NodeLockedException nle) {
	                Utils.addErrorMessage(Application.getMessage(context, MSG_IG_CONTAINS_LOCKED_DOCUMENTS));
	                return null;
	            }
	            finally
	            {
	            	getRuleService().enableRules();
	            }
	            
	            if (deleteLog)
	            {
	            	this.getLogService().deleteInterestgroupLog(igID);
	            }
	            
	            archivedNode = this.getNodeArchiveService().getArchivedNode(currentNode);
				
	            
	            
	        } else {
	            if(logger.isWarnEnabled()) {
	                logger.warn("Delete called without a current ID!");
	            }
	        }

	        if(parent != null)
	        {
	            getBrowseBean().clickWai(parent);
	        }
    	}
    	catch(final Exception ex)
    	{
    		if(logger.isErrorEnabled()) {
    			logger.error("finishImpl:" + ex.getMessage(), ex);
    		}
    	}

    	
    	
	    return CircabcNavigationHandler.CLOSE_WAI_DIALOG_OUTCOME
                    + CircabcNavigationHandler.OUTCOME_SEPARATOR
                    + CircabcBrowseBean.PREFIXED_WAI_BROWSE_OUTCOME;
    }

	/**
	 * @return the linksBusinessSrv
	 */
	protected LinksBusinessSrv getLinksBusinessSrv()
	{
		return getBusinessRegistry().getLinksBusinessSrv();
	}


	/**
	 * @return the sharedSpaces
	 */
	public List<ShareSpaceItem> getSharedSpaces()
	{
		return sharedSpaces;
	}

	public final RuleService getRuleService()
	{
		if(ruleService == null)
		{
			ruleService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getRuleService();
		}
		return ruleService;
	}

	public final void setRuleService(RuleService ruleService)
	{
		this.ruleService = ruleService;
	}
	
	 /**
     *@return nodeArchiveService
     */
    public NodeArchiveService getNodeArchiveService()
    {
       //check for null for cluster environment
        if (nodeArchiveService == null)
        {
           nodeArchiveService = (NodeArchiveService) FacesHelper.getManagedBean(FacesContext.getCurrentInstance(), "nodeArchiveService");
        }
        return nodeArchiveService;
    }

	public void setDeleteLog(boolean deleteLog)
	{
		this.deleteLog = deleteLog;
	}

	public boolean isDeleteLog()
	{
		return deleteLog;
	}

	public void setPurgeData(boolean purgeData)
	{
		this.purgeData = purgeData;
	}

	public boolean isPurgeData()
	{
		return purgeData;
	}

}
