/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.notification;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * The interface used to support reporting back the entire Notification Satus Report of an user.
 *
 * @author Yanick Pignot
 */
public interface UserNotificationReport
{

	public NodeRef getLocation();

	/**
     * Get the Global Notification Status enumeration value setted as the user preferences
     *
     * @return
     */
	public GlobalNotificationStatus getGlobalNotificationStatus();

	/**
     * Get the Notification Status enumeration setted for the user's profile for the current location
     *
     * @return
     */
	public NotificationStatus getProfileNotificationStatus();

	/**
     * Get the Notification Status enumeration setted for the specific user for the current location
     *
     * @return
     */
	public NotificationStatus getUserNotificationStatus();

	/**
     * Return true if the Notification Statuses allow the user to receive a Notification
     *
     * @return
     */
	public boolean isUserNotifiable();

    /**
     * Get the user authority to which this notification applies.
     *
     * @return
     */
    public String getUserAuthority();

    /**
     * Get the user authority profile to which this notification applies.
     *
     * @return
     */
    public String getUserProfile();

}