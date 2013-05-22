/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.model.UserModel;

/**
 * It is just a bean that contains all the data to be populated in Alfresco for
 * each CIRCABC user
 *
 * @author atadian
 * @author guillaume
 */
public class CircabcUserDataBean {

	/** Logger */
	private static final Log logger = LogFactory.getLog(CircabcUserDataBean.class);

	// Standard data for Alfresco Users
	private String userName;

	private String firstName;

	private String lastName;

	private String email;

	private String companyId;

	private NodeRef homeSpaceNodeRef;

	// Extra data for CIRCABC users
	private String title;

	private String phone;

	private String fax;

	private String URL;

	private String postalAddress;

	private String description;

	private String domain;

	private String orgdepnumber;

	private String password;

	/** If the profile is fully visible for everybody */
	private boolean visibility;

	/** If user want to get global notification */
	private boolean globalNotification = true;

	/** Last time user log on */
	private Date lastLoginTime;

	/** The last modification date of the details */
	private Date lastModificationDetailsTime;

	/** The creation date of the user */
	private Date creationDate;

	/** The ECAS user name */
	private String ecasUserName;

	/**
	 * Return attributes as a Map of QName (just person data)
	 *
	 * @return attributes as a Map of QName
	 */
	public Map<QName, Serializable> getAttributesAsMap() {
		Map<QName, Serializable> props = new HashMap<QName, Serializable>(15,
				1.0f);
		//Mandatory parameters !!!
		props.put(ContentModel.PROP_USERNAME, this.getUserName());
		props.put(ContentModel.PROP_FIRSTNAME, this.getFirstName());
		props.put(ContentModel.PROP_LASTNAME, this.getLastName());
		props.put(ContentModel.PROP_HOMEFOLDER, this.getHomeSpaceNodeRef());
		props.put(ContentModel.PROP_EMAIL, (this.getEmail() == null) ? "" : this.getEmail());
		logMissingMandatoryParameters();

		//Optional parameters
		props.put(ContentModel.PROP_ORGID, (this.getCompanyId() == null) ? "" : this.getCompanyId());

		return props;
	}

	/**
	 * A bug exist in the application. In some unknown situation an null value is inserted in the repository.
	 * This method should tell us from where the is generated
	 */
	private void logMissingMandatoryParameters() {
		if(logger.isErrorEnabled()) {
			final StringBuilder sb = new StringBuilder();
			if(this.getUserName() == null || this.getUserName().length() == 0) {
				sb.append("This value should be defined:").append(ContentModel.PROP_USERNAME).append("\n");
			}
			if(this.getFirstName() == null || this.getFirstName().length() == 0) {
				sb.append("This value should be defined:").append(ContentModel.PROP_FIRSTNAME).append("\n");
			}
			if(this.getLastName() == null || this.getLastName().length() == 0) {
				sb.append("This value should be defined:").append(ContentModel.PROP_LASTNAME).append("\n");
			}
			if(this.getHomeSpaceNodeRef() == null) {
				//sb.append("This value should be defined:").append(ContentModel.PROP_HOMEFOLDER).append("\n");
			}
			if(this.getEmail() == null || this.getEmail().length() == 0) {
				sb.append("This value should be defined:").append(ContentModel.PROP_EMAIL).append("\n");
			}
			if(sb.length() > 0) {
				//Error found
				final RuntimeException trickToGetStackTrace = new RuntimeException("");
				final StringBuilder sb2 = new StringBuilder();
				sb2.append("BUG FOUND: \n").append(sb);
				logger.error(sb2.toString(), trickToGetStackTrace);
			}
		}
	}

	/**
	 * Returns the attributes as a Map of QName (just a CIRCABC data)
	 *
	 * @return attributes as a Map of QName
	 */
	public Map<QName, Serializable> getAspectAttributesInMap() {
		Map<QName, Serializable> props = new HashMap<QName, Serializable>(7,
				1.0f);
		//Optional parameters
		props.put(UserModel.PROP_TITLE, (this.getTitle() == null) ? "" : this.getTitle());
		props.put(UserModel.PROP_PHONE, (this.getPhone() == null) ? "" : this.getPhone());
		props.put(UserModel.PROP_DESCRIPTION, (this.getDescription() == null) ? "" : this.getDescription());
		props.put(UserModel.PROP_DOMAIN, (this.getDomain() == null) ? "" : this.getDomain());
		props.put(UserModel.PROP_POSTAL_ADDRESS, (this.getPostalAddress()== null) ? "" : this.getPostalAddress());
		props.put(UserModel.PROP_FAX, (this.getFax()== null) ? "" : this.getFax());
		props.put(UserModel.PROP_URL, (this.getURL()== null) ? "" : this.getURL());
		props.put(UserModel.PROP_ORGDEPNUMBER, (this.getOrgdepnumber() == null) ? "" : this.getOrgdepnumber());
		props.put(UserModel.PROP_VISISBILITY, this.getVisibility());
		props.put(UserModel.PROP_GLOBAL_NOTIFICATION, this.getGlobalNotification());
		props.put(UserModel.PROP_LAST_LOGIN_TIME, this.getLastLoginTime());
		props.put(UserModel.PROP_LAST_MODIFICATION_DETAILS_TIME, this.getLastModificationDetailsTime());
		props.put(UserModel.PROP_CREATION_DATE, this.getCreationDate());
		props.put(UserModel.PROP_ECAS_USER_NAME, this.getEcasUserName());

		return props;

	}

	/**
	 * Returns all the attributes of the bean in the Map
	 *
	 * @return all attributes of the bean
	 */
	public Map<QName, Serializable> getAllAttributesInMap() {
		Map<QName, Serializable> all = this.getAttributesAsMap();
		all.putAll(getAspectAttributesInMap());
		return all;
	}

	/**
	 * Populates the bean from the Map
	 *
	 * @param pProps
	 *            Map with all Circabc User Data
	 */
	public void populateIt(final Map<QName, Serializable> pProps) {
		//Mandatory parameters !!!
		this.setUserName(pProps.get(ContentModel.PROP_USERNAME).toString());
		this.setFirstName(pProps.get(ContentModel.PROP_FIRSTNAME).toString());
		this.setLastName(pProps.get(ContentModel.PROP_LASTNAME).toString());
		this.setHomeSpaceNodeRef((NodeRef) pProps.get(ContentModel.PROP_HOMEFOLDER));
		this.setEmail(pProps.get(ContentModel.PROP_EMAIL).toString());
		this.setCompanyId(pProps.get(ContentModel.PROP_ORGID).toString());
		logMissingMandatoryParameters();

		//Optional parameters !!!
		this.setDescription(pProps.get(UserModel.PROP_DESCRIPTION).toString());
		this.setDomain(pProps.get(UserModel.PROP_DOMAIN).toString());
		this.setFax(pProps.get(UserModel.PROP_FAX).toString());
		this.setOrgdepnumber(pProps.get(UserModel.PROP_ORGDEPNUMBER).toString());
		this.setPhone(pProps.get(UserModel.PROP_PHONE).toString());
		this.setPostalAddress(pProps.get(UserModel.PROP_POSTAL_ADDRESS).toString());
		this.setTitle(pProps.get(UserModel.PROP_TITLE).toString());
		this.setURL(pProps.get(UserModel.PROP_URL).toString());
		this.setVisibility(((Boolean) pProps.get(UserModel.PROP_VISISBILITY)).booleanValue());
		this.setGlobalNotification(((Boolean) pProps.get(UserModel.PROP_GLOBAL_NOTIFICATION)).booleanValue());
		this.setLastLoginTime((Date) pProps.get(UserModel.PROP_LAST_LOGIN_TIME));
		this.setLastModificationDetailsTime((Date) pProps.get(UserModel.PROP_LAST_MODIFICATION_DETAILS_TIME));
		this.setCreationDate((Date) pProps.get(UserModel.PROP_CREATION_DATE));

	}

	public void copyLdapProperties(final CircabcUserDataBean circabcUserDataBean) {
		if (circabcUserDataBean != null) {
			//Mandatory parameters !!!
			this.ecasUserName = circabcUserDataBean.getEcasUserName();
			this.firstName = circabcUserDataBean.getFirstName();
			this.lastName = circabcUserDataBean.getLastName();
			this.email = circabcUserDataBean.getEmail();
			logMissingMandatoryParameters();

			//Optional parameters

			this.title = circabcUserDataBean.getTitle();
			this.orgdepnumber = circabcUserDataBean.getOrgdepnumber();
			this.phone = circabcUserDataBean.getPhone();
			this.description = circabcUserDataBean.getDescription();
			this.fax = circabcUserDataBean.getFax();
			this.postalAddress = circabcUserDataBean.getPostalAddress();
			this.domain = circabcUserDataBean.getDomain();

		}

	}

	/**
	 * The string version of this class
	 *
	 * @return string version of this class
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer(1000);
		sb.append("[");
		sb.append(getUserName());
		sb.append("|");
		sb.append(getLastName());
		sb.append("|");
		sb.append(getEmail());
		sb.append("|");
		sb.append(getPhone());
		sb.append("|");
		sb.append(getDomain());
		sb.append("]");

		return sb.toString();
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(final String companyId) {
		this.companyId = companyId;
	}

	/**
	 * Getter for creation Date
	 *
	 * @return Date The creation Date
	 */
	public Date getCreationDate() {
		return this.creationDate;
	}

	/**
	 * Setter for creation Date
	 *
	 * @param creationDate The creation Date to set
	 */
	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(final String domain) {
		this.domain = domain;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(final String fax) {
		this.fax = fax;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for global notification state
	 *
	 * @return boolean The global notification state
	 */
	public boolean getGlobalNotification() {
		return this.globalNotification;
	}

	/**
	 * Setter for global notification state
	 *
	 * @param globalNotification The global notification state to set
	 */
	public void setGlobalNotification(final boolean globalNotification) {
		this.globalNotification = globalNotification;
	}

	public NodeRef getHomeSpaceNodeRef() {
		return homeSpaceNodeRef;
	}

	public void setHomeSpaceNodeRef(final NodeRef homeSpaceNodeRef) {
		this.homeSpaceNodeRef = homeSpaceNodeRef;
	}

	/**
	 * Getter for last login time
	 *
	 * @return Date The last login time
	 */
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	/**
	 * Setter for last login time
	 *
	 * @param lastLoginTime The last login time to set
	 */
	public void setLastLoginTime(final Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * Getter for last modification details time
	 *
	 * @return Date The last modification details time
	 */
	public Date getLastModificationDetailsTime() {
		return this.lastModificationDetailsTime;
	}

	/**
	 * Setter for last modification details time
	 *
	 * @param lastModificationDetailsTime The last modification details time to set
	 */
	public void setLastModificationDetailsTime(final Date lastModificationDetailsTime) {
		this.lastModificationDetailsTime = lastModificationDetailsTime;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(final String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * Getter for visibility state
	 *
	 * @return boolean The visibility state
	 */
	public boolean getVisibility() {
		return this.visibility;
	}

	/**
	 * Setter for visibility state
	 *
	 * @param globalNotification The visibility state to set
	 */
	public void setVisibility(final boolean visibility) {
		this.visibility = visibility;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(final String url) {
		URL = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getOrgdepnumber() {
		return orgdepnumber;
	}

	public void setOrgdepnumber(final String orgdepnumber) {
		this.orgdepnumber = orgdepnumber;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getPassword()
	{
		return this.password;
	}

	/**
	 * @param ecasUserName the ecasUserName to set
	 */
	public void setEcasUserName(final String ecasUserName) {
		this.ecasUserName = ecasUserName;
	}

	/**
	 * @return the ecasUserName - also know as moniker
	 */
	public String getEcasUserName() {
		return ecasUserName;
	}

			
	

}
