package eu.cec.digit.circabc.repo.external.repositories;

/**
 * A user name resolver returns the name of the user that will be the sender 
 * of the document to publish.
 * 
 * @author schwerr
 */
public interface UserNameResolver {
	
	/**
	 * Returns the current user name.
	 * 
	 * @return
	 */
	public String getUserName();
}
