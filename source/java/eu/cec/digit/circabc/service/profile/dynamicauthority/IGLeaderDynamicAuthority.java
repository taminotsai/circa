/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile.dynamicauthority;

import java.util.Set;

import org.alfresco.repo.cache.SimpleCache;
import org.alfresco.repo.security.permissions.DynamicAuthority;
import org.alfresco.repo.security.permissions.PermissionReference;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.PermissionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.service.profile.CategoryProfileManagerService;
import eu.cec.digit.circabc.service.profile.IGRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.ProfileManagerService;

/**
 * This class manages the dynamic authority for ROLE_IGLEADER.
 *
 * @author Philippe Dubois
 * @author Clinckart Stephane
 */
public class IGLeaderDynamicAuthority implements DynamicAuthority, BeanFactoryAware
{
	/** The logger */
	private final static Log logger = LogFactory.getLog(IGLeaderDynamicAuthority.class);

	private SimpleCache<String, Boolean> dynamicAuthorityCache;

	private BeanFactory factory = null;

	private static NodeService nodeService = null;

	private static ProfileManagerService igRootProfileManagerService;

	private static ProfileManagerService categoryProfileManagerService;

	/**
	 * IOC - by Spring due of implementing BeanFactoryAware
	 */
	public void setBeanFactory(BeanFactory beanFactory)
	{
		this.factory = beanFactory;
	}

	/**
	 * @TODO make a constant for bean name
	 */
	public boolean hasAuthority(NodeRef nodeRef, String userName)
	{
		// TODO see with stephane if it is ok
		if  (userName.equals(PermissionService.GUEST_AUTHORITY))
		{
			return false;
		}
		if  (userName.equals("admin") || userName.equals("System"))
		{
			return false;
		}
		if (logger.isTraceEnabled())
		{
			logger.trace("DYNAMIC AUTHORITY on node:" + nodeRef + " for user:" + userName);
		}


		final String key = nodeRef + "||" + userName;;
		final Boolean result = dynamicAuthorityCache.get(key);

		if (result != null)
		{
			if (logger.isTraceEnabled())
			{
				logger.trace("CACHED DYNAMIC AUTHORITY FOUND on node:" + nodeRef + " for user:" + userName + " return"
						+ Boolean.TRUE);
			}
			return result.booleanValue();
		}


		try
		{
			if (nodeService == null)
			{
				nodeService = (NodeService) factory.getBean("nodeService");
			}

			if (igRootProfileManagerService == null)
			{
				igRootProfileManagerService = (ProfileManagerService) factory.getBean(IGRootProfileManagerService.SERVICE_NAME);
			}

			boolean isCircabcChild = nodeService.hasAspect(nodeRef, CircabcModel.ASPECT_CIRCABC_MANAGEMENT);

			boolean circaIGRootAspect = false;

			if (!isCircabcChild)
			{
				// Check if node has aspect IGRoot
				circaIGRootAspect = nodeService.hasAspect(nodeRef, CircabcModel.ASPECT_IGROOT);
				// Check if node has aspect IGRoot or CircaManagement
				if (!circaIGRootAspect)
				{
					if (logger.isTraceEnabled())
					{
						logger.trace("NO ACCESS for: " + userName + " on node:" + nodeRef);
					}

					dynamicAuthorityCache.put(key, Boolean.FALSE);
					return false;
				} else
				{
					if (logger.isTraceEnabled())
					{
						logger.trace("node:" + nodeRef + " has CircaIGRootAspect");
					}
				}
			} else
			{
				if (logger.isTraceEnabled())
				{
					logger.trace("node:" + nodeRef + " has CircaManagementAspect");
				}
			}
			/*
			 * if (profileManagerServiceFactory == null) {
			 * profileManagerServiceFactory = (ProfileManagerServiceFactory)
			 * factory
			 * .getBean(ProfileManagerServiceFactory.getBeanName(false)); }
			 */
			// TODO verify why factory make cyclic.
			// profileManagerServiceFactory
			// .getProfileManagerService(nodeRef);
			/*
			 * if (profileManagerService == null) { if (logger.isTraceEnabled()) {
			 * logger.trace("NO ACCESS for: " + userName + " on node:" +
			 * nodeRef); } return false; }
			 */
			// find the root of the IG meaning node having circaRoot aspect
			// applied
			// to.
			NodeRef igRoot = nodeRef;
			if (!circaIGRootAspect)
			{
				igRoot = igRootProfileManagerService.getCurrentAspectRoot(nodeRef);
			} else
			{
				igRoot = nodeRef;
			}
			if (igRoot == null)
			{
				if (logger.isTraceEnabled())
				{
					logger.trace("NO ACCESS for: " + userName + " on node:" + nodeRef);
				}
				dynamicAuthorityCache.put(key, Boolean.FALSE);
				return false;
			}
			String profileName = null;
			try
			{
				profileName = igRootProfileManagerService.getPersonProfile(igRoot, userName);
			} catch (Exception ex)
			{
				if (logger.isWarnEnabled())
				{
					logger.warn("Exception:" + ex.getMessage(), ex);
				}
			}
			if (profileName == null)
			{
				if (logger.isTraceEnabled())
				{
					logger.trace("NO ACCESS for: " + userName + " on node:" + nodeRef);
				}
				dynamicAuthorityCache.put(key, Boolean.FALSE);
				return false;
			}
			boolean access = profileName.equals(IGRootProfileManagerService.Profiles.IGLEADER);

			if (logger.isTraceEnabled())
			{
				if (access)
				{
					logger.trace("ACCESS granted for: " + userName + " on node:" + nodeRef);
				} else
				{
					//The current user is not a IGLeader.
					//Maybe he is a CategoryAdmin???
					final NodeRef categoryNodeRef = nodeService.getPrimaryParent(igRoot).getParentRef();
					if (categoryProfileManagerService == null)
					{
						categoryProfileManagerService = (ProfileManagerService) factory.getBean(CategoryProfileManagerService.SERVICE_NAME);
					}
					profileName = categoryProfileManagerService.getPersonProfile(categoryNodeRef, userName);

					if (profileName == null)
					{
						if (logger.isTraceEnabled())
						{
							logger.trace("NO ACCESS for: " + userName + " on node:" + nodeRef);
						}
						dynamicAuthorityCache.put(key, Boolean.FALSE);
						return false;
					} else {
						access = profileName.equals(CategoryProfileManagerService.Profiles.CIRCA_CATEGORY_ADMIN);
					}

				}
			}

			if (access)
			{
				dynamicAuthorityCache.put(key, Boolean.TRUE);
			} else
			{
				dynamicAuthorityCache.put(key, Boolean.FALSE);
			}

			return access;
		} catch (RuntimeException ex)
		{
			logger.fatal("AIE !!!" + ex.getMessage(), ex);
		}
		return false;
	}

	public String getAuthority()
	{
		return IGRootProfileManagerService.Roles.IGLEADER;
	}

	/**
	 * @param dynamicAuthorityCache the dynamicAuthorityCache to set
	 */
	public final void setDynamicAuthorityCache(SimpleCache<String, Boolean> dynamicAuthorityCache)
	{
		this.dynamicAuthorityCache = dynamicAuthorityCache;
	}

	public Set<PermissionReference> requiredFor() {
		return null;
	}

}
