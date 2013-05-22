/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.service.user;

import java.io.Serializable;

public class UserCategoryMembershipRecord implements Serializable {



	/**
	 *
	 */
	private static final long serialVersionUID = 3300655181779771569L;
	/**
	 *
	 */
	private String category;
	private String profile;
	private String categoryNodeId;

	public UserCategoryMembershipRecord( String category,String profile,String categoryNodeId)
	{
		this.category = category;
		this.profile = profile;
		this.categoryNodeId = categoryNodeId ; 

	}

	/**
	 * @param profile the profile to set
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}
	/**
	 * @return the profile
	 */
	public String getProfile() {
		return profile;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategory() {
		return category;
	}

	public void setCategoryNodeId(String categoryNodeId)
	{
		this.categoryNodeId = categoryNodeId;
	}

	public String getCategoryNodeId()
	{
		return categoryNodeId;
	}




}
