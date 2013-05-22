/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.customisation.mail;

import java.util.Locale;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * The base mail wrapper
 *
 * @author yanick pignot
 */
public interface MailWrapper
{

	/**
	 * @return the name of the template
	 */
	public String getName();

	/**
	 * @return the subject of the mail by applying the given model
	 */
	public String getSubject(final Map<String, Object> model);

	/**
	 * @return the subject of the mail by applying the given model
	 */
	public String getSubject(final Map<String, Object> model, final Locale language);

	/**
	 * @return the body of the mail by applying the given model
	 */
	public String getBody(final Map<String, Object> model);

	/**
	 * @return the body of the mail by applying the given model
	 */
	public String getBody(final Map<String, Object> model, final Locale language);

	/**
	 * return if this mail is the default one
	 *
	 * @return
	 */
	public boolean isOriginalTemplate();

	/**
	 * @return the template type of the mail.
	 */
	public MailTemplate getMailTemplate();

	/**
	 * @return the nodereference of the mail template
	 */
	public NodeRef getTemplateNodeRef();

}
