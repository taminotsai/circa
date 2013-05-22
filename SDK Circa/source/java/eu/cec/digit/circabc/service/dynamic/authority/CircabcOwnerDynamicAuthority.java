/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
 package eu.cec.digit.circabc.service.dynamic.authority;

import static eu.cec.digit.circabc.model.CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.repo.security.permissions.DynamicAuthority;
import org.alfresco.repo.security.permissions.PermissionReference;
import org.alfresco.service.cmr.ml.MultilingualContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.service.cmr.security.OwnableService;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.namespace.QName;
import org.alfresco.util.EqualsHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import eu.cec.digit.circabc.model.CircabcModel;

/**
 * The Circabc owner dynamic authority
 * similar like Alfresco original,
 * but if user is not member of ig , or category admin , or circabc admin
 * permission is denied    
 * @author andyh
 *
 */
public class CircabcOwnerDynamicAuthority implements DynamicAuthority, InitializingBean
{
    private OwnableService ownableService;
    private NodeService nodeService;
    private MultilingualContentService multilingualContentService;
    private AuthorityService authorityService;
    private static final Log logger = LogFactory.getLog(CircabcOwnerDynamicAuthority.class);
    

    public void setOwnableService(OwnableService ownableService)
    {
        this.ownableService = ownableService;
    }

    public void afterPropertiesSet() throws Exception
    {
        if (ownableService == null)
        {
            throw new IllegalArgumentException("There must be an ownable service");
        }
    }

    public boolean hasAuthority(final NodeRef nodeRef, final String userName)
    {
        return AuthenticationUtil.runAs(new RunAsWork<Boolean>(){

            public Boolean doWork() throws Exception
            {
                // TODO Auto-generated method stub
                final String owner = ownableService.getOwner(nodeRef);
				final boolean isOwner = EqualsHelper.nullSafeEquals(owner, userName);
				boolean isMangedByCircabc = nodeService.hasAspect(nodeRef, CircabcModel.ASPECT_CIRCABC_MANAGEMENT);
				if (isMangedByCircabc)
				{
					return  isOwner && isInvitedUser(nodeRef ,userName );
				}
				else
				{
					return  isOwner;
				}
				
            }

			private boolean isInvitedUser(NodeRef nodeRef, String userName) {
				final NodeRef container = getFirstContainer(nodeRef);
				Set<String>  invitedUsers = getInvitedUsers(container);
				return invitedUsers.contains(userName);
			}

			private Set<String> getInvitedUsers(NodeRef container) {
				Set<String> result = Collections.<String>emptySet();
				QName invitedUsersGroupQName = null;
				if (nodeService.hasAspect(container, CircabcModel.ASPECT_IGROOT))
				{
					invitedUsersGroupQName = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI,"circaIGRoot" + "InvitedUsersGroup");
				}
				else if ( 	nodeService.hasAspect(container, CircabcModel.ASPECT_CATEGORY) )
				{
					invitedUsersGroupQName = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI,"circaCategory" + "InvitedUsersGroup");
				}
				else if (	nodeService.hasAspect(container, CircabcModel.ASPECT_CIRCABC_ROOT))
				{
					invitedUsersGroupQName = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI,"circaBC" + "InvitedUsersGroup");
				}

				if (invitedUsersGroupQName != null)
				{
					Serializable property = nodeService.getProperty(container, invitedUsersGroupQName);
					if (property !=null)
					{
						final String invitedUsersGroupName = (String) property;
						final String prefixedUserGroupName = authorityService.getName(AuthorityType.GROUP, invitedUsersGroupName);
						if (authorityService.authorityExists(prefixedUserGroupName))
						{
							result = authorityService.getContainedAuthorities(AuthorityType.USER, prefixedUserGroupName, false);
						}
						else
						{
							if (logger.isErrorEnabled())
							{
								logger.error("Authority does bot exists: " + prefixedUserGroupName  );
							}
						}
						
						
					}
					else
					{
						if (logger.isErrorEnabled())
						{
							logger.error("Property " + invitedUsersGroupQName + " is null, for node " + container.toString());
						}
					}
				}
				return result;
			}

			private NodeRef getFirstContainer(NodeRef nodeRef) {
				NodeRef tempNodeRef  = nodeRef;
				for(;;)
				{
					if(tempNodeRef == null)
					{
						break;
					}

					if(nodeService.getType(tempNodeRef).equals(ContentModel.TYPE_MULTILINGUAL_CONTAINER))
					{
						tempNodeRef = multilingualContentService.getPivotTranslation(tempNodeRef);
					}

					if (nodeService.hasAspect(tempNodeRef, CircabcModel.ASPECT_IGROOT) || nodeService.hasAspect(tempNodeRef, CircabcModel.ASPECT_CATEGORY) || nodeService.hasAspect(tempNodeRef, CircabcModel.ASPECT_CIRCABC_ROOT) )
					{
						break;
					}
					else
					{
						tempNodeRef = nodeService.getPrimaryParent(tempNodeRef).getParentRef();
					}
				}
				return tempNodeRef;
			}
			}, AuthenticationUtil.getSystemUserName());
       
    }

    public String getAuthority()
    {
       return PermissionService.OWNER_AUTHORITY;
    }

    public Set<PermissionReference> requiredFor()
    {
        return null;
    }

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public NodeService getNodeService() {
		return nodeService;
	}

	public void setMultilingualContentService(MultilingualContentService multilingualContentService) {
		this.multilingualContentService = multilingualContentService;
	}

	public MultilingualContentService getMultilingualContentService() {
		return multilingualContentService;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public AuthorityService getAuthorityService() {
		return authorityService;
	}

}
