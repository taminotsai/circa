/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.notification;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;


/**
 * The interface used to support reporting back a notifiable user.
 *
 * @author Yanick Pignot
 */
public interface NotifiableUser
{
    /**
     * Get the username of the person that will be notified
     *
     * @return
     */
    public String getUserName();

    /**
     * Get the user last name of the person that will be notified
     *
     * @return
     */
    public String getLastName();

    /**
     * Get the user last name of the person that will be notified
     *
     * @return
     */
    public String getFirstName();

    /**
     * Get the user email address of the person that will be notified
     *
     * @return
     */
    public String getEmailAddress();

    /**
     * Get the user email properties of the person that will be notified
     *
     * @return
     */
    public Map<QName, Serializable> getUserProperties();


    /**
     * Get the language in which the user want to be notified
     *
     * @return
     */
    public Locale getNotificationLanguage();


    /**
     * Get the noderef representation of the user that will be notified
     *
     * @return
     */
    public NodeRef getPerson();

}