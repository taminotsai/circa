/**
 * 
 */
package eu.cec.digit.circabc.service.user;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.util.CircabcUserDataBean;

/**
 * @author beaurpi
 *
 */
public class BulkImportUserData {

	private Boolean selected = false;
	
	private NodeRef igRef;
	private String igName;
	private String departmentNumber;
	private String fromFile;
	private CircabcUserDataBean user;
	private String profile;
	private String status;
	
	public static final String STATUS_OK = "ok";
	public static final String STATUS_ERROR = "nok";
	public static final String STATUS_IGNORE = "ignore";
	
	/**
	 * 
	 */
	public BulkImportUserData() {
		
	}



	/**
	 * @return the igRef
	 */
	public NodeRef getIgRef() {
		return igRef;
	}



	/**
	 * @param igRef the igRef to set
	 */
	public void setIgRef(NodeRef igRef) {
		this.igRef = igRef;
	}



	/**
	 * @return the profile
	 */
	public String getProfile() {
		return profile;
	}



	/**
	 * @param profile the profile to set
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}



	/**
	 * @return the user
	 */
	public CircabcUserDataBean getUser() {
		return user;
	}



	/**
	 * @param user the user to set
	 */
	public void setUser(CircabcUserDataBean user) {
		this.user = user;
	}



	/**
	 * @return the departmentNumber
	 */
	public String getDepartmentNumber() {
		return departmentNumber;
	}



	/**
	 * @param departmentNumber the departmentNumber to set
	 */
	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}



	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}



	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}



	/**
	 * @return the fromFile
	 */
	public String getFromFile() {
		return fromFile;
	}



	/**
	 * @param fromFile the fromFile to set
	 */
	public void setFromFile(String fromFile) {
		this.fromFile = fromFile;
	}



	/**
	 * @return the igName
	 */
	public String getIgName() {
		return igName;
	}



	/**
	 * @param igName the igName to set
	 */
	public void setIgName(String igName) {
		this.igName = igName;
	}



	/**
	 * @return the selected
	 */
	public Boolean getSelected() {
		return selected;
	}



	/**
	 * @param selected the selected to set
	 */
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

}
