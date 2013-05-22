/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.MLText;
import org.alfresco.service.cmr.repository.NodeRef;

public interface Profile
{
	/**
	 * @return the profileName
	 */
	public abstract String getProfileName();

	/**
	 * set the profileName
	 */
	public abstract void setProfileName(final String profileName);

	/**
	 * @return the alfrescoGroupName
	 */
	public abstract String getAlfrescoGroupName();
	public abstract String getPrefixedAlfrescoGroupName();

	public abstract void setPrefixedAlfrescoGroupName(final String prefixedAlfrescoGroupName);

	public abstract void setAlfrescoGroupName(final String alfrescoGroupName);

	public void clearNodeServicesPermissions(final NodeRef noderef, Set<String> services, final ServiceRegistry serviceRegistry);

	public void setNodeServicesPermissions(final NodeRef nodeRef,
			final Map<String, Set<String>> servicesPermissions, final ServiceRegistry serviceRegistry);

	public void setServicesPermissions(final Map<String, Set<String>> servicesPermissions);

	public Set<String> getServicePermissions(final String serviceName);

	public HashMap<String, Set<String>> getServicesPermissions();

	public MLText getTitle();

	public void setTitles(final MLText titles);

	public MLText getDescription();

	public void setDescriptions(final MLText descriptions);

	public boolean isExported();

	public void setExported(final boolean isExported);

	public boolean isImported();

	public void setImported(final boolean isImported);

	public void setImportedNodeRef(final NodeRef importedNodeRef);

	public NodeRef getImportedNodeRef();
	
	public void setImportedNodeName(final String property);
	
	public String getImportedNodeName();
	
	public String getProfileDisplayName();
	
	public String getProfileDescription();
	
	public boolean isAdmin();
	
}