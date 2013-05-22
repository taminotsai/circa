/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.notification;

/**
 * The enumeration used to determine a Global Notification Status.
 *
 * @author Yanick Pignot
 */
public enum GlobalNotificationStatus
{
	ENABLED
	{
		public boolean toBoolean()
		{
			return true;
		}
	},
	DISABLED
	{
		public boolean toBoolean()
		{
			return false;
		}
	};

	abstract public boolean toBoolean();
}