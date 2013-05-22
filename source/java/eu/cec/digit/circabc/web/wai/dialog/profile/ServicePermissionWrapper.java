/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.profile;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.alfresco.web.app.Application;
import org.alfresco.web.ui.common.SortableSelectItem;

import eu.cec.digit.circabc.service.profile.CircabcServices;
import eu.cec.digit.circabc.web.PermissionUtils;

/**
 * @author Yanick Pignot
 *
 */
public class ServicePermissionWrapper
{
	private static final String SERVICE_NAME_SUFFIX = "_menu";

	private String permissionValue;
	private final CircabcServices service;
	private final List<SortableSelectItem> permissions;
	private final boolean locked;

	/*package*/ ServicePermissionWrapper(final CircabcServices service, final String permission, final Enum[] permissions)
	{
		this(service, permission, permissions, false);
	}

	/*package*/ ServicePermissionWrapper(final CircabcServices service, final String permission, final Enum[] permissions, final boolean locked)
	{
		this.permissionValue = permission;
		this.service = service;
		this.locked = locked;
		this.permissions = new ArrayList<SortableSelectItem>(permissions.length);

		for(final Enum permEnum: permissions)
		{
			final String perm = permEnum.toString();

			this.permissions.add(
					new SortableSelectItem(perm,
							PermissionUtils.getPermissionLabel(perm),
							PermissionUtils.getPermissionTooltip(perm)));
		}
	}

	/**
	 * @return the permissions
	 */
	public final List<SortableSelectItem> getPermissions()
	{
		return permissions;
	}

	/**
	 * @return the permissionValue
	 */
	public final String getPermissionValue()
	{
		return permissionValue;
	}

	/**
	 * @return the service
	 */
	public final CircabcServices getService()
	{
		return service;
	}

	/**
	 * @return the service
	 */
	public final String getServiceLabel()
	{
		return Application.getMessage(FacesContext.getCurrentInstance(), service.toString().toLowerCase() + SERVICE_NAME_SUFFIX);
	}

	/**
	 * @param permissionValue the permissionValue to set
	 */
	public final void setPermissionValue(String permissionValue)
	{
		this.permissionValue = permissionValue;
	}

	/**
	 * @return the locked
	 */
	public final boolean isLocked()
	{
		return locked;
	}



}
