/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.notification;

import org.alfresco.service.cmr.security.AuthorityType;


/**
 * The interface used to support reporting back the notification status.
 *
 * @author Yanick Pignot
 */
public interface AuthorityNotification
{

    /**
     * Get the Notification Status enumeration value
     *
     * @return
     */
    public NotificationStatus getNotificationStatus();


    /**
     * Get the authority to which this notification applies.
     *
     * @return
     */
    public String getAuthority();


    /**
     * Get the type of authority to which this notification applies.
     *
     * @return
     */
    public AuthorityType getAuthorityType();
}