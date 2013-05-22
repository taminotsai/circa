package eu.cec.digit.circabc.business.api.user;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import eu.cec.digit.circabc.business.acl.AclAwareWrapper;

/**
 * Encapsulate all details of a user in a RW mode. (properties / preferences / configuration)
 *
 * @author Yanick Pignot
 */
public interface UserDetails extends AclAwareWrapper
{

	//-----------------
	//--  Mandatory values (identifiers)

	/**
	 * @return the personRef
	 */
	public abstract NodeRef getNodeRef();

	/**
	 * @return the personRef
	 */
	public abstract String getUserName();
	
	/**
	 * @return the if the user is created in the repository 
	 */
	public abstract boolean isUserCreated();

	//-----------------
	//--  Properties of Alfresco user model

	/**
	 * @return the email
	 */
	public abstract String getEmail();

	/**
	 * @param email the email to set
	 */
	public abstract void setEmail(String email);

	/**
	 * @return the firstName
	 */
	public abstract String getFirstName();

	/**
	 * @param firstName the firstName to set
	 */
	public abstract void setFirstName(String firstName);

	/**
	 * @return the lastName
	 */
	public abstract String getLastName();

	/**
	 * @param lastName the lastName to set
	 */
	public abstract void setLastName(String lastName);

	//-----------------
	//--  Properties of circabc user aspect

	/**
	 * @return the description
	 */
	public abstract String getDescription();

	/**
	 * @param description the description to set
	 */
	public abstract void setDescription(String description);

	/**
	 * @return the fax
	 */
	public abstract String getFax();

	/**
	 * @param fax the fax to set
	 */
	public abstract void setFax(String fax);

	/**
	 * @return the organisation
	 */
	public abstract String getOrganisation();

	/**
	 * @param organisation the organisation to set
	 */
	public abstract void setOrganisation(String organisation);

	/**
	 * @return the phone
	 */
	public abstract String getPhone();

	/**
	 * @param phone the phone to set
	 */
	public abstract void setPhone(String phone);

	/**
	 * @return the postalAddress
	 */
	public abstract String getPostalAddress();

	/**
	 * @param postalAddress the postalAddress to set
	 */
	public abstract void setPostalAddress(String postalAddress);

	/**
	 * @return the title
	 */
	public abstract String getTitle();

	/**
	 * @param title the title to set
	 */
	public abstract void setTitle(String title);

	/**
	 * @return the url
	 */
	public abstract String getUrl();

	/**
	 * @param url the url to set
	 */
	public abstract void setUrl(String url);

	//-----------------
	//--  Properties located under circabc aspect that should be moved in the preferences

	/**
	 * @return the globalNotification
	 */
	public abstract Boolean getGlobalNotification();

	/**
	 * @param globalNotification the globalNotification to set
	 */
	public abstract void setGlobalNotification(Boolean globalNotification);

	/**
	 * @return the visibility
	 */
	public abstract Boolean getVisibility();

	/**
	 * @param visibility the visibility to set
	 */
	public abstract void setVisibility(Boolean visibility);

	//-----------------
	//--  Preferences

	/**
	 * @return the userInterfaceLanguage
	 */
	public abstract String getUserInterfaceLanguage();

	/**
	 * @param userInterfaceLanguage the userInterfaceLanguage to set
	 */
	public abstract void setUserInterfaceLanguage(String userInterfaceLanguage);

	/**
	 * @return the contentFilterLanguage
	 */
	public abstract Locale getContentFilterLanguage();

	/**
	 * @param contentFilterLanguage the contentFilterLanguage to set
	 */
	public abstract void setContentFilterLanguage(final Locale contentFilterLanguage);

	/**
	 * @return the signature
	 */
	public abstract String getSignature();

	/**
	 * @param signature the signature to set
	 */
	public abstract void setSignature(String signature);

	/**
	 * @return the avatar
	 */
	public abstract NodeRef getAvatar();

	/**
	 * @param avatarRef
	 */
	public abstract void setAvatar(NodeRef avatarRef);


	//-- Other methods

	/**
	 * @return all preferences updated by the user
	 */
	public abstract Map<QName, Serializable> getUpdatedPreferences();

	/**
	 * @return all properties updated by the user
	 */
	public abstract Map<QName, Serializable> getUpdatedProperties();

	/**
	 * @return all properties defined on the user ignoring the user updated ones
	 */
	public abstract Map<QName, Serializable> getOriginalProperties();

	/**
	 * @return all preferences defined on the user ignoring the user updated ones
	 */
	public abstract Map<QName, Serializable> getOriginalPreferences();

	/**
	 * The full name of the person.
	 *
	 * @return	Usually FirstName LastName
	 */
	public abstract String getFullName();

	/**
	 * The user identifier entered by user when it authenticate itself if different that the UserName.
	 *
	 * @return	The user id used for display.
	 */
	public abstract String getDisplayId();
	
	/**
	 * Some properties must be hidden to <b>other</b> users if the user specify its visibility to false (or nothing)
	 * 
	 * @return				Current user != getUsername && getVisibility != TRUE 
	 */
	public abstract boolean isPersonalDataHidden();

}