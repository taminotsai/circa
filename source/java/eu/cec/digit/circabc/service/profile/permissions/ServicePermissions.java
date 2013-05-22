/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile.permissions;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ServicePermissions implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = -2568781927774798099L;

	private String serviceName;

	private Set<String> permissions = new HashSet<String>();

	private ServicePermissions() {

	}

	public ServicePermissions(final String serviceName)
	{
		this.serviceName = serviceName;
	}

	/**
	 * @return the serviceName
	 */
	public final String getServiceName()
	{
		return serviceName;
	}

	/**
	 * @return the permissions
	 */
	public final Set<String> getPermissions()
	{
		return permissions;
	}

	/**
	 * Set the permissions
	 */
	public final void setPermissions(Set<String> permissions)
	{
		this.permissions = permissions;
	}
}
