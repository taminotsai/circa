/*--+
 |     Copyright European Community 2011 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.authentication;

import org.alfresco.repo.security.authentication.AuthenticationServiceImpl;

/**
 * Service that was implemented to test why the ECAS authentication subsystem 
 * is destroying the user context...
 * 
 * @author schwerr
 */
public class EcasAuthenticationServiceImpl extends AuthenticationServiceImpl {
	
	/**
	 * Must always return false since the authentication must always exist 
	 * in Alfresco to determine if it should be created.
	 * 
	 * @see org.alfresco.repo.security.authentication.AuthenticationServiceImpl#authenticationExists(java.lang.String)
	 */
	@Override
	public boolean authenticationExists(String userName) {
		return false;
	}
}
