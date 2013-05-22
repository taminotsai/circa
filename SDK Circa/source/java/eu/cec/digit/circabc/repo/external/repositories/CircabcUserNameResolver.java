package eu.cec.digit.circabc.repo.external.repositories;

import org.alfresco.service.cmr.security.AuthenticationService;

/**
 * User name resolver that returns the name of the current logged user by 
 * getting it from Alfresco.
 * 
 * @author schwerr
 */
public class CircabcUserNameResolver implements UserNameResolver {
	
	private AuthenticationService authenticationService = null;
	
	/**
	 * @see eu.cec.digit.circabc.repo.external.repositories.UserNameResolver#getUserName()
	 */
	@Override
	public String getUserName() {
		return authenticationService.getCurrentUserName();
	}
	
	/**
	 * Sets the value of the authenticationService
	 * 
	 * @param authenticationService the authenticationService to set.
	 */
	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
}
