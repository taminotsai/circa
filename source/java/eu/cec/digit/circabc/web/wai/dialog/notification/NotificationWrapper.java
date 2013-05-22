/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.dialog.notification;

import java.io.Serializable;

import org.alfresco.service.cmr.security.AuthorityType;

import eu.cec.digit.circabc.service.notification.NotificationStatus;

/**
 * Light weight object that represents a dispalyable Notification element for the UI
 *
 * @author Yanick Pignot
 */
public class NotificationWrapper implements Serializable
{

	/** */
	private static final long serialVersionUID = -8112745596456613819L;


	private String username;
	private NotificationStatus status;
	private AuthorityType type;
	private String authority;
	private String nodeId;

	/**
	 * @param type
	 * @param username
	 * @param status
	 * @param autority
	 */
	public NotificationWrapper(final AuthorityType type, final String username, final NotificationStatus status, final String authority, final String nodeId)
	{
		super();
		this.type = type;
		this.username = username;
		this.status = status;
		this.authority = authority;
		this.nodeId = nodeId;
	}

	@Override
    public String toString()
    {
        return this.authority + " ( " + this.username + " ) " + this.status ;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof NotificationWrapper))
        {
            return false;
        }
        final NotificationWrapper other = (NotificationWrapper) o;
        return this.getAuthority().equals(other.getAuthority())
        		&& this.getStatus().equals(other.getStatus());
    }

    @Override
    public int hashCode()
    {
        return authority.hashCode() + nodeId.hashCode() +  status.hashCode();
    }

	/**
	 * @return the autority
	 */
	public final String getAuthority()
	{
		return authority;
	}

	/**
	 * @return the user friendly status as String
	 */
	public final String getStatus()
	{
		return NotificationUtils.translateStatus(status);
	}

	/**
	 * @return the user friendly tye As string
	 */
	public final String getType()
	{
		return NotificationUtils.translateAuthorityType(type);
	}

	/**
	 * @return the username
	 */
	public final String getUsername()
	{
		return username;
	}

	/**
	 * @return the nodeId
	 */
	public final String getNodeId()
	{
		return nodeId;
	}

	/**
	 * @return the status
	 */
	public final NotificationStatus getStatusValue()
	{
		return status;
	}

	/**
	 * @return the type
	 */
	public final AuthorityType getTypeValue()
	{
		return type;
	}

	/**
	 * @return the status as an object string
	 */
	public final String getStatusValueToString()
	{
		return status.toString();
	}

	/**
	 * @return the type as an object string
	 */
	public final String getTypeValueToString()
	{
		return type.toString();
	}

}
