package eu.cec.digit.circabc.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import eu.cec.digit.circabc.model.UserModel;

/**
 *
 * @author Clinckart Stephane
 *
 */
public class CircabcUserDataFillerUtil {

	private CircabcUserDataFillerUtil()
	{

	}
	public static CircabcUserDataBean getCircabcUserDataBean(
			final Map<QName, Serializable> properties) {
		final CircabcUserDataBean circabcUserDataBean = new CircabcUserDataBean();

		final String firstName = (String) properties.get(ContentModel.PROP_FIRSTNAME);
		if(firstName != null) circabcUserDataBean.setFirstName(firstName);

		final String lastName = (String) properties.get(ContentModel.PROP_LASTNAME);
		if(lastName != null) circabcUserDataBean.setLastName(lastName);

		final String userName = (String) properties.get(ContentModel.PROP_USERNAME);
		if(userName != null) circabcUserDataBean.setUserName(userName);

		final String email = (String) properties.get(ContentModel.PROP_EMAIL);
		if(email != null) circabcUserDataBean.setEmail(email);

		final String companyId = (String) properties.get(ContentModel.PROP_ORGID);
		if(companyId != null) circabcUserDataBean.setCompanyId(companyId);

		final NodeRef homeSpaceNodeRef = (NodeRef) properties.get(ContentModel.PROP_HOMEFOLDER);
		if(homeSpaceNodeRef != null) circabcUserDataBean.setHomeSpaceNodeRef(homeSpaceNodeRef);


		// additionnal
		final String phone = (String) properties.get(UserModel.PROP_PHONE);
		if(phone != null) circabcUserDataBean.setPhone(phone);

		final String fax = (String) properties.get(UserModel.PROP_FAX);
		if(fax != null) circabcUserDataBean.setFax(fax);

		final String url = (String) properties.get(UserModel.PROP_URL);
		if(url != null) circabcUserDataBean.setURL(url);

		final String postalAdress = (String) properties.get(UserModel.PROP_POSTAL_ADDRESS);
		if(postalAdress != null) circabcUserDataBean.setPostalAddress(postalAdress);

		final String description = (String) properties.get(UserModel.PROP_DESCRIPTION);
		if(description != null) circabcUserDataBean.setDescription(description);

		final String orgdepnumber = (String) properties.get(UserModel.PROP_ORGDEPNUMBER);
		if(orgdepnumber != null) circabcUserDataBean.setOrgdepnumber(orgdepnumber);

		final String title = (String) properties.get(UserModel.PROP_TITLE);
		if(title != null) circabcUserDataBean.setTitle(title);

		final String domain = (String) properties.get(UserModel.PROP_DOMAIN);
		if(domain != null) circabcUserDataBean.setDomain(domain);

		final Date creationDate = (Date) properties.get(UserModel.PROP_CREATION_DATE);
		if(creationDate != null) circabcUserDataBean.setCreationDate(creationDate);

		final Boolean globalNotification = (Boolean) properties.get(UserModel.PROP_GLOBAL_NOTIFICATION);
		if(globalNotification != null) circabcUserDataBean.setGlobalNotification(globalNotification);

		final Boolean visibility = (Boolean) properties.get(UserModel.PROP_VISISBILITY);
		if(visibility != null) circabcUserDataBean.setVisibility(visibility);

		final Date lastModificationDetailsTime = (Date) properties.get(UserModel.PROP_LAST_MODIFICATION_DETAILS_TIME);
		if(lastModificationDetailsTime != null) circabcUserDataBean.setLastModificationDetailsTime(lastModificationDetailsTime);

		final Date lastLoginTime = (Date) properties.get(UserModel.PROP_LAST_LOGIN_TIME);
		if(lastLoginTime != null) circabcUserDataBean.setLastLoginTime(lastLoginTime);

		final String ecasUserName = (String) properties.get(UserModel.PROP_ECAS_USER_NAME);
		if(ecasUserName != null) circabcUserDataBean.setEcasUserName(ecasUserName);

		return circabcUserDataBean;
	}
}
