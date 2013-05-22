/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.impl.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.business.api.BusinessRuntimeExpection;
import eu.cec.digit.circabc.business.api.user.RemoteUserBusinessSrv;
import eu.cec.digit.circabc.business.api.user.UserDetails;
import eu.cec.digit.circabc.business.impl.AssertUtils;
import eu.cec.digit.circabc.service.user.UserService;
import eu.cec.digit.circabc.util.CircabcUserDataBean;


/**
 * Implementation of the Business service that manage ldap common tasks.
 *
 * @author Yanick Pignot
 */
public class LdapBusinessImpl implements RemoteUserBusinessSrv
{

	private static final String MSG_CANNOT_RELOAD = "business_error_cannot_reload_user_details";

	private final Log logger = LogFactory.getLog(LdapBusinessImpl.class);

	private boolean ldapAvailable = false;
	private UserService userService;

	//--------------
    //-- public methods


	public boolean isRemoteManagementAvailable()
	{
		return ldapAvailable;
	}

	public void reloadDetails(final UserDetails userDetails)
	{
		AssertUtils.notNull(userDetails);

		final CircabcUserDataBean ldapUserDetail = userService.getLDAPUserDataByUid(userDetails.getUserName());

		if  (ldapUserDetail != null)
		{
			if (ldapUserDetail.getFirstName() != null)
			{
				userDetails.setFirstName(ldapUserDetail.getFirstName());
			}
			if (ldapUserDetail.getLastName() != null)
			{
				userDetails.setLastName(ldapUserDetail.getLastName());
			}
			if (ldapUserDetail.getEmail() != null)
			{
				userDetails.setEmail(ldapUserDetail.getEmail());
			}

			if (ldapUserDetail.getTitle() != null)
			{
				userDetails.setTitle(ldapUserDetail.getTitle());
			}
			if (ldapUserDetail.getOrgdepnumber() != null)
			{
				userDetails.setOrganisation(ldapUserDetail.getOrgdepnumber());
			}
			if (ldapUserDetail.getPhone() != null)
			{
				userDetails.setPhone(ldapUserDetail.getPhone());
			}
			if (ldapUserDetail.getPostalAddress() != null)
			{
				userDetails.setPostalAddress(ldapUserDetail.getPostalAddress());
			}
			if (ldapUserDetail.getFax() != null)
			{
				userDetails.setFax(ldapUserDetail.getFax());
			}
			if (ldapUserDetail.getDescription() != null)
			{
				userDetails.setDescription(ldapUserDetail.getDescription());
			}
			if (ldapUserDetail.getURL() != null)
			{
				userDetails.setUrl(ldapUserDetail.getURL());
			}
		}
		else
		{
			if(logger.isErrorEnabled())
			{
				logger.error("Impossible to found " + userDetails.getUserName() + " in the ldap.");
			}

			throw new BusinessRuntimeExpection(MSG_CANNOT_RELOAD);
		}
	}

	public boolean userExists(final String userId)
	{
		if(userId == null || userId.length() < 1)
		{
			return false;
		}
		else
		{
			return userService.getLDAPUserDataByUid(userId) != null;
		}
	}


	//--------------
    //-- private helpers

    //--------------
    //-- IOC

	/**
	 * @param ldapAvailable the ldapAvailable to set
	 */
	public final void setRemoteManagementAvailable(boolean ldapAvailable)
	{
		this.ldapAvailable = ldapAvailable;
	}

	/**
	 * @param userService the userService to set
	 */
	public final void setUserService(UserService userService)
	{
		this.userService = userService;
	}

}
