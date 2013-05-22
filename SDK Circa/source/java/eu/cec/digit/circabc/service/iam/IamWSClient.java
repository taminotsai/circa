package eu.cec.digit.circabc.service.iam;

public interface IamWSClient {
	/**
	 * Call this method to grant user role to IAM
	 * @param userID 
	 * @param themeID
	 * @param roleID
	 */
	void grantThemeRole(String userID,String themeID ,String roleID );
	
	/**
	 * Call this method to revoke user role to IAM
	 * @param userID
	 * @param themeID
	 * @param roleID
	 */
	void revokeThemeRole(String userID,String themeID ,String roleID );

}
