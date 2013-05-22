package eu.cec.digit.circabc.service.user;

/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

import java.util.List;
import java.util.Map;

import org.alfresco.service.Auditable;
import org.alfresco.service.PublicService;

import eu.cec.digit.circabc.util.CircabcUserDataBean;

/**
 * It is a spring bean that manages basic operations on User over LDAP
 * 
 * @author stephane Clinckart
 * 
 */

@PublicService
public interface LdapUserService
{

	@Auditable(parameters = { "pLdapUserID" })
	public CircabcUserDataBean getLDAPUserDataByUid(final String pLdapUserID);

	@Auditable(parameters = { "uid", "moniker", "email", "cn", "conjunction" })
	public List<String> getLDAPUserIDByIdMonikerEmailCn(final String uid, final String moniker, final String email,
			final String cn, final boolean conjunction);

	@Auditable(parameters = { "pMail" })
	public List<String> getLDAPUserIDByMail(String mail);

	@Auditable(parameters = { "mail", "domain" })
	public List<String> getLDAPUserIDByMailDomain(final String mail, final String domain);

	@Auditable(parameters = { "pDomain", "pCriteria" })
	public List<SearchResultRecord> getUsersByDomainFirstNameLastNameEmail(final String pDomain, final String pCriteria);

	@Auditable(parameters = { "pDomain", "pCriteria" })
	public List<String> getUsersByDomainFirstNameLastNameWithoutEmail(final String pDomain, final String pCriteria);

	@Auditable(parameters = { "mail", "domain" })
	public List<SearchResultRecord> getUsersByMailDomain(final String mail, final String domain);
	
	@Auditable()
	public Map<String, String> getEcasUserDomains();
	
	/**
	 * Initialise method
	 */
	public void init();
}
