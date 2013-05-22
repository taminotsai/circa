package eu.cec.digit.circabc.repo.external.repositories;

/**
 * Basic user name resolver. Returns a static user name given at wiring time.
 * Used for playground.
 * 
 * @author schwerr
 */
public class StaticUserNameResolver implements UserNameResolver {
	
	private String userName = null;
	
	/**
	 * @see eu.cec.digit.circabc.repo.external.repositories.UserNameResolver#getUserName()
	 */
	@Override
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Sets the value of the userName
	 * 
	 * @param userName the userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
