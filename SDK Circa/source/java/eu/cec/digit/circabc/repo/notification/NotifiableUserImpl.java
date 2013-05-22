/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.notification;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import eu.cec.digit.circabc.service.notification.NotifiableUser;


/**
 * The interface used to support reporting back a notifiable user.
 *
 * @author Yanick Pignot
 */
public class NotifiableUserImpl implements NotifiableUser
{

	private String  userName;
    private String  lastName;
    private String  firstName;
    private String  email;
    private NodeRef person;
    private Locale locale;
    private Map<QName, Serializable> properties;

	/**
	 * @param person
	 */
	public NotifiableUserImpl(NodeRef person, Locale locale, Map<QName, Serializable> properties)
	{
		super();
		this.person = person;
		this.properties = properties;
		this.locale = locale;
		userName  = (String) properties.get(ContentModel.PROP_USERNAME);
		lastName  = (String) properties.get(ContentModel.PROP_LASTNAME);
		firstName = (String) properties.get(ContentModel.PROP_FIRSTNAME);
		email     = (String) properties.get(ContentModel.PROP_EMAIL);

	}
	/**
	 * @return the emailName
	 */
	public final String getEmailAddress()
	{
		return email;
	}
	/**
	 * @return the firstName
	 */
	public final String getFirstName()
	{
		return firstName;
	}
	/**
	 * @return the lastName
	 */
	public final String getLastName()
	{
		return lastName;
	}
	/**
	 * @return the person
	 */
	public final NodeRef getPerson()
	{
		return person;
	}
	/**
	 * @return the userName
	 */
	public final String getUserName()
	{
		return userName;
	}

	public Map<QName, Serializable> getUserProperties()
	{
		return properties;
	}

	public Locale getNotificationLanguage()
	{
		return locale;
	}


	@Override
	public String toString()
	{
		return userName + '('+ email + ')';
	}

}