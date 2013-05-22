/**
 * 
 */
package eu.cec.digit.circabc.repo.support;

/**
 * @author beaurpi
 *
 */
public enum SupportTypes {
	TYPE_ECAS_USER_ACCOUNT(0, "support_type_ecas_related_title"),
	TYPE_UPLOAD_LIBRARY(1, "support_type_upload_related_title"),
	TYPE_DOWNLOAD_LIBRARY(2, "support_type_download_related_title"),
	TYPE_IG_MANAGEMENT(3, "support_type_ig_management_related_title"),
	TYPE_NEWSGROUP(4, "support_type_newsgroup_related_title"),
	TYPE_EVENT(5, "support_type_event_related_title"),
	TYPE_MEMBERSHIP(6, "support_type_membership_related_title"),
	TYPE_OTHER(7, "support_type_other_related_title");
	
	private Integer id;
	private String description;
	
	SupportTypes(Integer id, String description)
	{
		this.setId(id);
		this.setDescription(description);
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public static SupportTypes getById(Integer id)
	{
		SupportTypes result = SupportTypes.TYPE_OTHER;
		for(SupportTypes val : SupportTypes.values())
		{
			if(val.getId().equals(id))
			{
				result = val;
				break;
			}
		}
		
		return result;
	}
}
