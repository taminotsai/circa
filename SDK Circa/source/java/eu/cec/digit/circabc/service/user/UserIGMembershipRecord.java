/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.service.user;

import java.io.Serializable;

public class UserIGMembershipRecord implements Serializable {



	/**
	 *
	 */
	private static final long serialVersionUID = -8706766125898983357L;
	
	private String category;
	private String categoryNodeId;
	private String interestGroup;
	private String interestGroupNodeId;
	private String interestGroupTitle;
	private String profile;
	private String categoryTitle;
	private String profileTitle;

	public UserIGMembershipRecord(String interestGroupNodeId,String interesGroup , String categoryNodeId, String category, String profile, String categoryTitle, String interesGroupTitle, String profileTitle)
	{
		this.interestGroupNodeId= interestGroupNodeId;
		this.category= category;
		this.categoryNodeId= categoryNodeId;
		this.interestGroup= interesGroup;
		this.profile = profile;
		this.categoryTitle = bestTitleOrName(categoryTitle, category);
		this.interestGroupTitle = bestTitleOrName(interesGroupTitle, interesGroup);
		this.profileTitle = bestTitleOrName(profileTitle, profile);
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
	public void setInterestGroup(String interesGroup) {
		this.interestGroup = interesGroup;
	}
	public String getInterestGroup() {
		return interestGroup;
	}

	public void setInterestGroupNodeId(String interestGroupNodeId) {
		this.interestGroupNodeId = interestGroupNodeId;
	}

	public String getInterestGroupNodeId() {
		return interestGroupNodeId;
	}

	/**
	 * @return the categoryTitle
	 */
	public final String getCategoryTitle()
	{
		return categoryTitle;
	}

	/**
	 * @param categoryTitle the categoryTitle to set
	 */
	public final void setCategoryTitle(String categoryTitle)
	{
		this.categoryTitle = categoryTitle;
	}

	/**
	 * @return the interestGroupTitle
	 */
	public final String getInterestGroupTitle()
	{
		return interestGroupTitle;
	}

	/**
	 * @param interestGroupTitle the interestGroupTitle to set
	 */
	public final void setInterestGroupTitle(String interestGroupTitle)
	{
		this.interestGroupTitle = interestGroupTitle;
	}

	/**
	 * @return the profileTitle
	 */
	public final String getProfileTitle()
	{
		return profileTitle;
	}

	/**
	 * @param profileTitle the profileTitle to set
	 */
	public final void setProfileTitle(String profileTitle)
	{
		this.profileTitle = profileTitle;
	}


	private String bestTitleOrName(final String title, final String name)
	{
		if(title == null || title.trim().length() < 1)
		{
			return name;
		}
		else
		{
			return title;
		}
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
