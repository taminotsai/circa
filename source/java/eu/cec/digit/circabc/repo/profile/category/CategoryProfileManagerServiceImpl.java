/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.profile.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.model.PermissionModel;
import eu.cec.digit.circabc.repo.profile.ProfileManagerServiceImpl;
import eu.cec.digit.circabc.service.profile.CategoryProfileManagerService;
import eu.cec.digit.circabc.service.profile.CircabcServices;
import eu.cec.digit.circabc.service.profile.IGRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.Profile;
import eu.cec.digit.circabc.service.profile.ProfileException;

public class CategoryProfileManagerServiceImpl extends ProfileManagerServiceImpl
        implements CategoryProfileManagerService
{
	private static final String PROFILE_PREFIX = "circaCategory";
	/** A logger for the class */
    @SuppressWarnings("unused")
	private static Log logger = LogFactory
            .getLog(CategoryProfileManagerServiceImpl.class);

    private static final Set<String> services = new HashSet<String>();

    public CategoryProfileManagerServiceImpl() {
    	services.add(CircabcServices.CATEGORY.toString());
    }

    /**
	 * @return the services
	 */
	public final Set<String> getServices()
	{
		return services;
	}

    public List<QName> getQNameServiceRoles()
    {
    	final List<QName> list = new ArrayList<QName>();
        //list.add(CircaCategoryAspect.CIRCABC_PERMISSION);
        list.add(PermissionModel.CATEGORY_PERMISSION);
        //list.add(CircaCategoryAspect.LIBRARY_PERMISSION);
        //list.add(CircaCategoryAspect.DIRECTORY_PERMISSION);

        return list;
    }



    public Map<QName, String> getServiceRolesEnum()
    {
    	final Map<QName, String> map = new HashMap<QName, String>();
        //map.put(CircaCategoryAspect.CIRCABC_PERMISSION, CircaServices.CIRCABC.toString());
        map.put(PermissionModel.CATEGORY_PERMISSION, CircabcServices.CATEGORY.toString());
        //map.put(CircaCategoryAspect.LIBRARY_PERMISSION, CircaServices.LIBRARY.toString());
        //map.put(CircaCategoryAspect.DIRECTORY_PERMISSION, CircaServices.DIRECTORY.toString());
        return map;
    }

    @Override
	public QName getNodeAspect()
	{
		return getNodeRootAspect();
	}

	@Override
	public QName getNodeRootAspect()
	{
		return CircabcModel.ASPECT_CATEGORY;
	}

    public String getProfilePrefix()
    {
        return PROFILE_PREFIX;
    }

	public boolean hasParentSubsGroup()
	{
		return true;
	}

	public boolean hasMasterParentGroup()
	{
		return true;
	}

	public boolean hasApplicantFeature()
	{
		return false;
	}

	@Override
	public Profile updateProfile(final NodeRef nodeRef, final String profileName,
			final Map<String, Set<String>> servicesPermissions, final boolean updateProfileMap)
	{
		Profile profile = super.updateProfile(nodeRef, profileName, servicesPermissions, updateProfileMap);

		resetCache();

		return profile;
	}


	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.service.profile.ProfileManagerServiceImpl#addApplicantPerson(org.alfresco.service.cmr.repository.NodeRef, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addApplicantPerson(final NodeRef nodeRef, final String userID, final String message, final String firstName, final String lastName) throws ProfileException
	{
		throw new IllegalStateException("This method is not yet implemented for the aspect " + getNodeAspect());
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.service.profile.ProfileManagerServiceImpl#removeApplicantPerson(org.alfresco.service.cmr.repository.NodeRef, java.lang.String)
	 */
	@Override
	public void removeApplicantPerson(final NodeRef nodeRef, final String userID) throws ProfileException
	{
		throw new IllegalStateException("This method is not yet implemented for the aspect " + getNodeAspect());
	}

	public List<Profile> getProfilesRecursivly(final NodeRef nodeRef) {
		final List<Profile> profiles = new ArrayList<Profile>();
		if(nodeService.hasAspect(nodeRef, CircabcModel.ASPECT_CATEGORY)) {
			NodeRef igNodeRef;
			final IGRootProfileManagerService iGRootProfileManagerService = getProfileManagerServiceFactory().getIGRootProfileManagerService();
			for(final ChildAssociationRef childs : nodeService.getChildAssocs(nodeRef)) {
				igNodeRef = childs.getChildRef();
				if(nodeService.hasAspect(igNodeRef, CircabcModel.ASPECT_IGROOT)) {
					profiles.addAll(iGRootProfileManagerService.getProfilesRecursivly(igNodeRef));
				}
			}
			profiles.addAll(getProfiles(nodeRef));
		}
		return profiles;
	}
}
