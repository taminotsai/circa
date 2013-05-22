package eu.cec.digit.circabc.web.wai.wizard.sharespace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.business.api.BusinessStackError;
import eu.cec.digit.circabc.business.api.link.InterestGroupItem;
import eu.cec.digit.circabc.business.api.link.LinksBusinessSrv;
import eu.cec.digit.circabc.service.customisation.mail.MailTemplate;
import eu.cec.digit.circabc.service.profile.permissions.LibraryPermissions;
import eu.cec.digit.circabc.web.PermissionUtils;
import eu.cec.digit.circabc.web.repository.IGServicesNode;
import eu.cec.digit.circabc.web.repository.InterestGroupNode;
import eu.cec.digit.circabc.web.wai.AbstractMailToUsersBean;

public class SharingSpaceWizard extends AbstractMailToUsersBean
{
    private static final String FIRSTNAME_REGEX = "<USER_FIRST_NAME>";
    private static final String LASTTNAME_REGEX = "<USER_LAST_NAME>";

    private static final long serialVersionUID = 6122281106190028588L;

    private static final Log logger = LogFactory.getLog(SharingSpaceWizard.class);

    private static final String KEY_PROFILE = "profile";
    private static final String KEY_YOUR_IG = "yourinterestGroup";

    private NodeRef shareSpace;
    private String interestGroup;
    private String libraryPermission;
    private List<SelectItem> availableInterestGroups ;
    private List<SelectItem> orderedLibraryPermissions;

    private String notify = "yes";


    @Override
    public void init(final Map<String, String> parameters)
    {
        super.init(parameters);

        notify= "yes";

        // ensure to not erase values when it is not required
        if (parameters != null)
        {
            if(getActionNode() == null)
            {
                throw new IllegalArgumentException("Node id is a mandatory parameter");
            }

            this.shareSpace= getActionNode().getNodeRef();
    		this.interestGroup ="";
    		this.availableInterestGroups = new ArrayList<SelectItem>();
    		this.orderedLibraryPermissions = new ArrayList<SelectItem>();

            fillItems();
        }
    }

	private void fillItems()
	{
		final List<InterestGroupItem> interestGroups = getLinksBusinessSrv().getInterestGroupForSharing(shareSpace);
		for (final InterestGroupItem item : interestGroups)
		{
		    availableInterestGroups.add(new SelectItem(item.getNodeRef().toString(),  item.getTitle()));
		    interestGroup = item.getNodeRef().toString();
		}

		final List<String> perms = LibraryPermissions.getOrderedLibraryPermissions();
		for (final String perm : perms)
		{
		    this.orderedLibraryPermissions.add(new SelectItem(perm, PermissionUtils.getPermissionLabel(perm)));
		}
		this.libraryPermission = LibraryPermissions.LIBACCESS.toString();
	}


    @Override
    public boolean getNextButtonDisabled() {
        if(availableInterestGroups == null || availableInterestGroups.size() == 0) {
            return true;
        } else {
            return super.getNextButtonDisabled();
        }
    }


    @Override
    protected String finishImpl(FacesContext context, String outcome)
    {
    	final NodeRef targetIgRef = new NodeRef(interestGroup);
    	boolean notifyLeaders = notify.equals("yes");
    	logRecordSetInfo(targetIgRef);

    	try
    	{
            final LibraryPermissions libPerm = LibraryPermissions.withPermissionString(libraryPermission);
			getLinksBusinessSrv().applySharing(shareSpace,targetIgRef, libPerm);
    	}
    	catch(final BusinessStackError err)
    	{
    		for(final String msg: err.getI18NMessages())
    		{
    			Utils.addErrorMessage(msg);
    		}
    		this.isFinished = false;
    		return null;
    	}

        if(logger.isDebugEnabled())
        {
            logger.debug(getNodeService().getProperty(targetIgRef, ContentModel.PROP_NAME) + " successfully invited to the space " + getActionNode().getName());
        }

        if (notifyLeaders)
        {
            AuthenticationUtil.runAs(new RunAsWork<Object>()
            {
                public Object doWork() throws Exception
                {
                    final IGServicesNode library = new InterestGroupNode(targetIgRef).getLibrary();
                    final Set<String> libAdmins = getUserService().getUsersWithPermission(library.getNodeRef(), LibraryPermissions.LIBADMIN.toString());

                    final Map<String, Object> extraModelParams = buildModelParam();

                    for (final String libAdmin : libAdmins)
                    {
                        final NodeRef person = getPersonService().getPerson(libAdmin);

                        mailToUser(person, getActionNode().getNodeRef(), null, extraModelParams, buildBodyParams(person));

                        if (logger.isDebugEnabled())
                        {
                            logger.debug("Invitation successufully send to " + getNodeService().getProperty(targetIgRef, ContentModel.PROP_NAME) + " lib admin: " + libAdmin);
                        }
                    }
                    return null;
                }
            }, AuthenticationUtil.getSystemUserName());

        }

        return outcome;
    }


    private void logRecordSetInfo(final NodeRef targetIgRef)
    {
        try {
            AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>()
            {
                    public Object doWork()
                    {
                        final String igName = (String) getNodeService().getProperty(targetIgRef, ContentModel.PROP_NAME);
                        final String spaceName = (String) getNodeService().getProperty(shareSpace, ContentModel.PROP_NAME);
                        final String info = "Invited interest group " + igName  + " to space " + spaceName ;
                        logRecord.setInfo(info);
                           return null;
                     }
                 }, AuthenticationUtil.getAdminUserName());
        } catch (final Exception e)
        {
            logger.error("Error during logging share space wizard", e);
        }
    }


    /**
     * @return
     */
    private Map<String, Object> buildModelParam()
    {
        final Map<String, Object> params = new HashMap<String, Object>(2);
        if(interestGroup != null) {
            params.put(KEY_YOUR_IG, new NodeRef(interestGroup));
        }
        params.put(KEY_PROFILE, libraryPermission);
        return params;
    }

     @Override
     protected  Map<String, Object> getDisplayModelToAdd()
     {
         return buildModelParam();
     }

    /**
     * @return
     */
    private Map<String, String> buildBodyParams(final NodeRef person)
    {
        final Map<QName, Serializable> personProperties = getNodeService().getProperties(person);
        final Map<String, String> params = new HashMap<String, String>(2);
        params.put(FIRSTNAME_REGEX, (String) personProperties.get(ContentModel.PROP_FIRSTNAME));
        params.put(LASTTNAME_REGEX, (String) personProperties.get(ContentModel.PROP_LASTNAME));

        return params;
    }

    public String getBrowserTitle()
    {
        return translate("sharing_space_title");
    }

    public String getPageIconAltText()
    {
        return translate("sharing_space_icon");
    }

    /**
     * @param shareSpace the shareSpace to set
     */
    public void setShareSpace(NodeRef shareSpace)
    {
        this.shareSpace = shareSpace;
    }

    /**
     * @return the shareSpace
     */
    public NodeRef getShareSpace()
    {
        return shareSpace;
    }

    /**
     * @param interestGroup the interestGroup to set
     */
    public void setInterestGroup( String interestGroup)
    {
        this.interestGroup = interestGroup;
    }

    /**
     * @return the interestGroup
     */
    public String getInterestGroup()
    {
        return interestGroup;
    }

    /**
     * @param libraryPermission the libraryPermission to set
     */
    public void setLibraryPermission(String libraryPermission)
    {
        this.libraryPermission = libraryPermission;
    }

    /**
     * @return the libraryPermission
     */
    public String getLibraryPermission()
    {
        return libraryPermission;
    }

    /**
     * @param availableInterestGroups the availableInterestGroups to set
     */
    public void setAvailableInterestGroups(List<SelectItem> availableInterestGroups)
    {
        this.availableInterestGroups = availableInterestGroups;
    }

    /**
     * @return the availableInterestGroups
     */
    public List<SelectItem> getAvailableInterestGroups()
    {
        return availableInterestGroups;
    }

    /**
     * @param orderedLibraryPermissions the orderedLibraryPermissions to set
     */
    public void setOrderedLibraryPermissions(List<SelectItem> orderedLibraryPermissions)
    {
        this.orderedLibraryPermissions = orderedLibraryPermissions;
    }

    /**
     * @return the orderedLibraryPermissions
     */
    public List<SelectItem> getOrderedLibraryPermissions()
    {
        return orderedLibraryPermissions;
    }
    public String getNotify()
    {
        return this.notify;
    }

    public void setNotify(final String notify)
    {
        this.notify = notify;
    }

    @Override
    protected MailTemplate getMailTemplateDefinition()
    {
        return MailTemplate.SHARE_SPACE_NOTIFICATION;
    }

    /**
	 * @return the linksBusinessSrv
	 */
	protected LinksBusinessSrv getLinksBusinessSrv()
	{
		return getBusinessRegistry().getLinksBusinessSrv();
	}
}


