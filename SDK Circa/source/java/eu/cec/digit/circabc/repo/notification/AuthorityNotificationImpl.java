/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.notification;

import org.alfresco.service.cmr.security.AuthorityType;

import eu.cec.digit.circabc.service.notification.AuthorityNotification;
import eu.cec.digit.circabc.service.notification.NotificationStatus;


/**
 * The interface used to support reporting back the notification status.
 *
 * @author Yanick Pignot
 */
public class AuthorityNotificationImpl implements AuthorityNotification
{
	private NotificationStatus notificationStatus;
	private String authority;
	private AuthorityType authorityType;

	/**
	 * @param notificationStatus
	 * @param authority
	 * @param authorityType
	 */
	public AuthorityNotificationImpl(NotificationStatus notificationStatus, String authority)
	{
		super();
		this.notificationStatus = notificationStatus;
		this.authority = authority;
		this.authorityType = AuthorityType.getAuthorityType(authority);
	}

	public NotificationStatus getNotificationStatus()
	{
		return notificationStatus;
	}

	public String getAuthority()
	{
		return authority;
	}

	public AuthorityType getAuthorityType()
	{
		return authorityType;
	}

    @Override
    public String toString()
    {
        return notificationStatus + " " + this.authority + " (" + this.authorityType + ")";
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof AuthorityNotificationImpl))
        {
            return false;
        }
        final AuthorityNotification other = (AuthorityNotification) o;
        return this.getNotificationStatus() == other.getNotificationStatus()
        		&& this.getAuthority().equals(other.getAuthority());
    }

    @Override
    public int hashCode()
    {
        return (authority.hashCode() * 37) +  notificationStatus.hashCode();
    }
}