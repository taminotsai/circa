/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.permissions;

import static eu.cec.digit.circabc.web.PermissionUtils.AUTH_TYPE_VALUE_GROUP;
import static eu.cec.digit.circabc.web.PermissionUtils.KEY_AUTHORITY;
import static eu.cec.digit.circabc.web.PermissionUtils.KEY_AUTHORITY_TYPE;
import static eu.cec.digit.circabc.web.PermissionUtils.KEY_DISPLAY_NAME;
import static eu.cec.digit.circabc.web.PermissionUtils.KEY_PERMISSION;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import net.sf.ehcache.CacheManager;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.profile.CircabcRootProfileManagerService;
import eu.cec.digit.circabc.web.PermissionUtils;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * Bean that backs edit user permissions dialog (for any kind of node).
 *
 * @author Yanick Pignot
 */
public class EditPermissionDialog extends BaseWaiDialog
{
	private static final long serialVersionUID = 777770868117140631L;

	private static final Log logger = LogFactory.getLog(EditPermissionDialog.class);

	public static final String BEAN_NAME = "EditPermissionDialog";
	private static final String WARN_RIGHTS = "warning_space_permissions";

	private String authority = null;
	private String authType = null;
	private String displayName = null;
	private String permission = null;

	private CacheManager dynamicAuthorithiesCacheManager;

	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null )
		{
			authority = parameters.get(KEY_AUTHORITY);
			authType = parameters.get(KEY_AUTHORITY_TYPE);
			displayName = parameters.get(KEY_DISPLAY_NAME);
			permission = parameters.get(KEY_PERMISSION);

			if(getActionNode() == null)
			{
				throw new IllegalArgumentException("The node id is a mandatory parameter");
			}

			if(authority == null || authType == null || displayName == null || permission == null)
			{
				throw new IllegalArgumentException("authority, authType, displayName and permission are mandatory parameters");
			}
			
			logRecord.setService(super.getServiceFromActionNode());
			logRecord.setActivity("Edit permission");
			updateLogDocument(getActionNode().getNodeRef(), logRecord);
		}
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
        try
        {
        	final List<String> guestsPermissions  =  Collections.unmodifiableList(Arrays.asList("LibNoAccess", "LibAccess","NwsAccess","NwsNoAccess", "SurAccess","SurNoAccess","InfAccess","InfNoAccess"));
        	final List<String> regiteredPermissions  =  Collections.unmodifiableList(Arrays.asList("LibNoAccess", "LibAccess","NwsPost","NwsAccess","NwsNoAccess","SurEncode", "SurAccess","SurNoAccess","InfAccess","InfNoAccess"));
            if(authority.equalsIgnoreCase("guest") &&  !guestsPermissions.contains(permission))
            {
            	Utils.addStatusMessage(FacesMessage.SEVERITY_ERROR, translate(WARN_RIGHTS, displayName, permission));
            	return null;
            }
        	
            if(authority.equalsIgnoreCase(CircabcRootProfileManagerService.ALL_CIRCA_USERS_AUTHORITY) && !regiteredPermissions.contains(permission))
            {
            	Utils.addStatusMessage(FacesMessage.SEVERITY_ERROR, translate(WARN_RIGHTS, displayName, permission));
            	return null;
            }
            
        	// clear the currently set permissions for this user
            // and add each of the new permissions in turn
            NodeRef nodeRef = getActionNode().getNodeRef();
            getPermissionService().clearPermission(nodeRef, this.authority);
            getPermissionService().setPermission(
                    nodeRef,
                    this.authority,
                    permission,
                    true);

            final String logType;
            if(authType.equals(AUTH_TYPE_VALUE_GROUP))
            {
            	logType = "profile";
            }
            else
            {
            	logType = "user";
            }

            logRecord.setInfo("Set permission " + permission + " to the " + logType + " " + displayName);
            PermissionUtils.resetCache(getActionNode(), dynamicAuthorithiesCacheManager, logger);

            if(logger.isDebugEnabled())
            {
            	logger.debug("Permission " + permission + " successfully setted to " + displayName + " in the space " + getActionNode().getPath());
            }
        }
        catch (Exception err)
        {
        	outcome = null;

        	logger.error("Impossible to update the permission " + permission + " to authority " + authority, err);

        	Utils.addErrorMessage(translate(Repository.ERROR_GENERIC, err.getMessage()), err );
        }

		return outcome;
	}

	public String getPersonPermission()
	{
		return permission;
	}

	public void setPersonPermission(String permission)
	{
		this.permission = permission;
	}

	public List<SelectItem> getPermissions()
    {
    	return PermissionUtils.getPermissions(getActionNode(), logger);
    }

	@Override
	public String getContainerTitle()
	{
	     return translate("modify_user_roles") + " " + displayName;
	}

	@Override
	public String getContainerDescription()
	{
	   return translate("modify_user_roles_description");
	}

	public String getBrowserTitle()
	{
		return translate("edit_user_permission_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("edit_user_permission_icon_tooltip");
	}

	/**
	 * @param dynamicAuthorithiesCacheManager the dynamicAuthorithiesCacheManager to set
	 */
	public final void setDynamicAuthorithiesCacheManager(CacheManager dynamicAuthorithiesCacheManager)
	{
		this.dynamicAuthorithiesCacheManager = dynamicAuthorithiesCacheManager;
	}

}
