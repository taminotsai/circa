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

import java.util.Map;

import javax.faces.context.FacesContext;

import net.sf.ehcache.CacheManager;

import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.web.PermissionUtils;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * Bean that backs edit user permissions dialog (for any kind of node).
 *
 * @author Yanick Pignot
 */
public class RemovePermissionDialog extends BaseWaiDialog
{
	private static final long serialVersionUID = 777770868117140631L;

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(RemovePermissionDialog.class);

	public static final String BEAN_NAME = "RemovePermissionDialog";

	protected final static String MSG_REMOVE_PAGE_TITLE_USER = "remove_space_permission_page_title_user";
	protected final static String MSG_REMOVE_PAGE_TITLE_PROF = "remove_space_permission_page_title_access_profile";
	protected final static String MSG_REMOVE_PAGE_SUBTITLE_USER = "remove_space_permission_page_subtitle_user";
	protected final static String MSG_REMOVE_PAGE_SUBTITLE_PROF = "remove_space_permission_page_subtitle_access_profile";
	protected final static String MSG_REMOVE_PAGE_CONTENT_USER = "remove_space_permission_page_content_user";
	protected final static String MSG_REMOVE_PAGE_CONTENT_PROF = "remove_space_permission_page_content_access_profile";

	private static final String ERROR_DELETE = "error_remove_user";

	private String authority = null;
	private String authType = null;
	private String displayName = null;

	private CacheManager dynamicAuthorithiesCacheManager;

	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null )
		{
			authority = parameters.get(KEY_AUTHORITY);
			authType = parameters.get(KEY_AUTHORITY_TYPE);
			displayName = parameters.get(KEY_DISPLAY_NAME);

			if(getActionNode() == null)
			{
				throw new IllegalArgumentException("The node id is a mandatory parameter");
			}

			if(authority == null || authType == null || displayName == null)
			{
				throw new IllegalArgumentException("authority, authType, and displayName are mandatory parameters");
			}
		}
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		 try
	      {

	         if (authority != null)
	         {
	            // clear permissions for the specified Authority
	            this.getPermissionService().clearPermission(getActionNode().getNodeRef(), authority);
	         }

	         PermissionUtils.resetCache(getActionNode(), dynamicAuthorithiesCacheManager, logger);

	         if(logger.isDebugEnabled())
	         {
	           	logger.debug("Permissions successfully removed to " + displayName + " in the space " + getActionNode().getPath());
	         }

	      }
	      catch (Exception e)
	      {
	    	  outcome = null;

	          Utils.addErrorMessage(translate (ERROR_DELETE, e.getMessage()), e);
	      }

	      return outcome;
	}

	@Override
	public String getContainerTitle()
	{
		if(AUTH_TYPE_VALUE_GROUP.equals(authType))
		{
            return translate(MSG_REMOVE_PAGE_TITLE_PROF, displayName);
		}
		else
		{
			return translate(MSG_REMOVE_PAGE_TITLE_PROF, displayName);
		}
	}

	@Override
	public String getContainerDescription()
	{
		if(AUTH_TYPE_VALUE_GROUP.equals(authType))
		{
			return translate(MSG_REMOVE_PAGE_SUBTITLE_PROF);
		}
		else
		{
			return translate(MSG_REMOVE_PAGE_SUBTITLE_USER);
		}
	}

	/**
	 * Get the remove user/access profile translated content
	 **/
	public String getConfirmationMessage()
	{
		if(AUTH_TYPE_VALUE_GROUP.equals(authType))
		{
			return translate(MSG_REMOVE_PAGE_CONTENT_PROF, displayName);
		}
		else
		{
			return translate(MSG_REMOVE_PAGE_CONTENT_USER, displayName);
		}
	}

	@Override
	public String getCancelButtonLabel()
	{
	   return translate("no");
	}
	@Override
	public String getFinishButtonLabel()
	{
		return translate("yes");
	}

	public String getBrowserTitle()
	{
		return translate("remove_space_permission_page_content_user_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("remove_space_permission_page_content_user_icon_tooltip");
	}

	/**
	 * @param dynamicAuthorithiesCacheManager the dynamicAuthorithiesCacheManager to set
	 */
	public final void setDynamicAuthorithiesCacheManager(CacheManager dynamicAuthorithiesCacheManager)
	{
		this.dynamicAuthorithiesCacheManager = dynamicAuthorithiesCacheManager;
	}
}
