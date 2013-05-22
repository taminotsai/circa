/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.helper;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.AccessStatus;
import org.alfresco.service.cmr.security.PermissionService;
import org.apache.commons.lang.NotImplementedException;


/**
 * Business manager to perform permission related common tasks.
 *
 * @author Yanick Pignot
 */
public class PermissionManager
{
	private PermissionService permissionService;

	//--------------
	//-- public methods

	/**
	 * Return if the current authenticated user can read the given node.
	 *
	 * @param nodeRef			The noderef
	 * @return
	 */
	public boolean canRead(final NodeRef nodeRef)
	{
		return hasPermission(nodeRef, PermissionService.READ);
	}

	/**
	 * Return if the current authenticated user can delete the given node.
	 *
	 * @param nodeRef			The noderef
	 * @return
	 */
	public boolean canDelete(final NodeRef nodeRef)
	{
		return hasPermission(nodeRef, PermissionService.DELETE);
	}

	/**
	 * Return if the current authenticated user can edit the content of the given node;
	 *
	 * @param nodeRef			The noderef
	 * @return
	 */
	public boolean canEditContent(final NodeRef nodeRef)
	{
		return hasPermission(nodeRef, PermissionService.WRITE_CONTENT);
	}

	/**
	 * Return if the current authenticated user can edit the given node properties.
	 *
	 * @param nodeRef			The noderef
	 * @return
	 */
	public boolean canEditProperties(final NodeRef nodeRef)
	{
		return hasPermission(nodeRef, PermissionService.WRITE_PROPERTIES);
	}

	/**
	 * Return if the current authenticated user can add/create a child to the given node.
	 *
	 * @param nodeRef			The noderef
	 * @return
	 */
	public boolean canAddChild(final NodeRef nodeRef)
	{
		return hasPermission(nodeRef, PermissionService.CREATE_CHILDREN);
	}


	/**
	 * Return if the current authenticated in adminsitrator in all services of the interest group.
	 *
	 * @param nodeRef			An interest group or any of its child.
	 * @return
	 */
	public boolean isAdminInAllService(final NodeRef nodeRef)
	{
		throw new NotImplementedException();
	}

	/**
	 * Return if the current authenticated in adminsitrator in at least one service of the interest group.
	 *
	 * @param nodeRef			An interest group or any of its child.
	 * @return
	 */
	public boolean isAdminInOneService(final NodeRef nodeRef)
	{
		throw new NotImplementedException();
	}


	//--------------
	//-- private helpers

	private boolean hasPermission(final NodeRef nodeRef, final String permission)
	{
		if(nodeRef == null)
		{
			return false;
		}
		else
		{
			return AccessStatus.ALLOWED.equals(getPermissionService().hasPermission(nodeRef, permission));
		}
	}

	//--------------
	//-- IOC

	/**
	 * @return the permissionService
	 */
	protected final PermissionService getPermissionService()
	{
		return permissionService;
	}

	/**
	 * @param permissionService the permissionService to set
	 */
	public final void setPermissionService(PermissionService permissionService)
	{
		this.permissionService = permissionService;
	}
}
