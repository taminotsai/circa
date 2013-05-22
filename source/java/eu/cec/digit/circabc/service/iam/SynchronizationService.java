package eu.cec.digit.circabc.service.iam;

import java.util.Set;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * @author Slobodan Filipovic 
 * CIRCABC IAM synchronization
 */
public interface SynchronizationService {
	/**
	 * Call this method to grant user role to IAM
	 * @param userName 
	 * @param themeID
	 * @param roleID
	 */
	void grantThemeRole(String userName,String themeID );
	
	

	void grantThemeRoles(Set<String> userName,String themeID  );
	/**
	 * Call this method to revoke user role to IAM
	 * @param userName
	 * @param themeID
	 * @param roleID
	 */
	void revokeThemeRole(String userName,String themeID );
	
	/**
	 * @param interestGroup node reference of interest group
	 * @return eCORDA Theme ID if Exists or Empty String otherwise
	 */
	String getEcordaThemeId(NodeRef interestGroup);
	
	
	
}
