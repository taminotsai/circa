/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile.permissions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProfilePermissions implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 5879727028955430081L;

	/** A logger for the class */
	private transient static Log logger = LogFactory.getLog(ProfilePermissions.class);

	private HashMap<String, ServicePermissions> servicesPermissions = new HashMap<String, ServicePermissions>();

	/**
	 *
	 * @param servicesPermissions
	 *            HashMap<String serviceName, Set<String> permissions>
	 */
	public void setServicesPermissions(final HashMap<String, Set<String>> servicesPermissions)
	{
		for (final Map.Entry<String, Set<String>> entry : servicesPermissions.entrySet())
		{
			setServicePermissions(entry.getKey(), entry.getValue());
		}
	}

	public void setServicePermissions(final String serviceName, final Set<String> permissions)
	{
		ServicePermissions servicePermissions;
		if (servicesPermissions.containsKey(serviceName))
		{
			servicePermissions = servicesPermissions.get(serviceName);
			servicePermissions.setPermissions(permissions);
		} else
		{
			if (logger.isTraceEnabled())
			{
				logger.trace("new ServicePermissions: " + serviceName);
			}
			servicePermissions = new ServicePermissions(serviceName);
			servicePermissions.setPermissions(permissions);
			servicesPermissions.put(serviceName, servicePermissions);

		}
		if (logger.isTraceEnabled())
		{
			logger.trace("set Permission: " + permissions + " for " + serviceName);
		}

	}

	public HashMap<String, ServicePermissions> getServicePermissions()
	{
		return servicesPermissions;
	}

	public Set<String> getPermissions(final String serviceName)
	{
		final ServicePermissions servicePermissions = servicesPermissions.get(serviceName);
		return servicePermissions.getPermissions();
	}

	public void clearNodePermissions(final NodeRef nodeRef, final String prefixedGroupName, final String serviceName,
			final ServiceRegistry serviceRegistry)
	{
		//final PermissionService permissionService = serviceRegistry.getPermissionService();
		final QName permissionServiceQName = QName.createQName(NamespaceService.ALFRESCO_URI, "permissionService");
		final PermissionService permissionService = (PermissionService)serviceRegistry.getService(permissionServiceQName);
		if (servicesPermissions != null && servicesPermissions.containsKey(serviceName))
		{
			//final ServicePermissions servicePermissions = servicesPermissions.get(serviceName);
			//for (final String permission : servicePermissions.getPermissions())
			//{
				permissionService.deletePermission(nodeRef, prefixedGroupName, null);
			//}
		}

	}

	public void setNodePermissions(final NodeRef nodeRef, final String prefixedGroupName,
			final HashMap<String, Set<String>> servicesPermissions, final ServiceRegistry serviceRegistry)
	{
		final PermissionService permissionService = serviceRegistry.getPermissionService();
		if (logger.isTraceEnabled())
		{
			logger.trace("setPermissions on node:" + nodeRef + "");
		}

		AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>()
		{
			public Object doWork()
			{
				Set<String> permissions;
				for (final String serviceName : servicesPermissions.keySet())
				{
					setServicePermissions(serviceName, servicesPermissions.get(serviceName));

					permissions = servicesPermissions.get(serviceName);
					for (final String permission : permissions)
					{

						permissionService.setPermission(nodeRef, prefixedGroupName, permission, true);

						if (logger.isTraceEnabled())
						{
							logger.trace("setPermission: " + prefixedGroupName + " " + permission + " TRUE on node:" + nodeRef);
						}
					}
				}
				return null;
			}
		}, AuthenticationUtil.getSystemUserName());
	}

	public void setServicePermissions(final Map<String, Set<String>> servicesPermissions)
	{
		for (final Map.Entry<String, Set<String>> entry : servicesPermissions.entrySet())
		{
			setServicePermissions(entry.getKey(), entry.getValue());
		}
	}
}
