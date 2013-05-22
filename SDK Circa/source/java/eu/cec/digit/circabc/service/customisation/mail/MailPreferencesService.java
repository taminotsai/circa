/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.customisation.mail;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.alfresco.service.Auditable;
import org.alfresco.service.NotAuditable;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.TemplateImageResolver;

import eu.cec.digit.circabc.repo.customisation.CustomizationException;
import eu.cec.digit.circabc.util.CircabcUserDataBean;

/**
 * Interface for the customisation of the mail sended Interest Groups.
 * 
 * @see eu.cec.digit.circabc.service.customisation.NodePreferencesService
 * 
 * @author Yanick Pignot
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * Commented the key parameter of the @Auditable annotation.
 * Commented the deprecated @PublicService annotation.
 */
//@PublicService
public interface MailPreferencesService
{
	/**
	 * Add a mail to a given <b>configurable node</b>. The method
	 * <code>makeConfigurable(ref)</code> must be called on the node reference.
	 * 
	 * A new template will be created if the template doen't exists or
	 * translation will be added on it.
	 * 
	 * @param ref
	 *            the node on which we have to add a template
	 * @param forTemplate
	 *            the mandatory template to define the associated "mail group"
	 * @param name
	 *            the unique name of the template
	 * @param body
	 *            the mandatory body of the mail
	 * @param subject
	 *            the mandatory subject of the mail
	 * @param language
	 *            the mandatory language of the mail
	 * @throws CustomizationException
	 *             if a template already exist with this name and this Locale.
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "ref", "forTemplate", "name", "body", "subject", "language" })
	public void addMailTemplate(final NodeRef ref, final MailTemplate forTemplate, final String name, final String body,
			final String subject, final Locale language) throws CustomizationException;

	/**
	 * Build a default model for the mail template processing.
	 * 
	 * @param currentRef
	 *            The current noderef
	 * @param otherPerson
	 *            The person that will receive the email
	 * @param imageResolver
	 *            The image resolver
	 * @return A map ti use as model for the template processing.
	 */
	@NotAuditable
	public Map<String, Object> buildDefaultModel(final NodeRef currentRef, final NodeRef otherPerson,
			final TemplateImageResolver imageResolver);

	/**
	 * Return the default mail template define on a given node.
	 * 
	 * @see eu.cec.digit.circabc.service.customisation.NodePreferencesService#getDefaultConfigurationFile(NodeRef,
	 *      String, String, String)
	 * 
	 * @param ref
	 * @param mailTemplate
	 * @return
	 */
	public MailWrapper getDefaultMailTemplate(final NodeRef ref, final MailTemplate mailTemplate);

	/**
	 * Get the disclamer logo configuration for the given node
	 * 
	 * @param ref
	 * @return
	 */
	public abstract NodeRef getDisclamerLogo(final NodeRef ref);

	/**
	 * Return the list of mail templates for a given node. If templates not
	 * found, check recusivly until a parent denine it.
	 * 
	 * @param ref
	 *            The node on which we have to found prefernces
	 * @param mailTemplate
	 *            The mail template to return
	 * @return The list of template defined for this node.
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "ref", "mailTemplate" })
	public List<MailWrapper> getMailTemplates(final NodeRef ref, final MailTemplate mailTemplate);

	/**
	 * Build a default user data for template edition needs
	 * 
	 * @return
	 */
	public abstract CircabcUserDataBean getTemplateUserDetails();

	/**
	 * Replace the existing default mail to a given <b>configurable node</b>.
	 * The method <code>makeConfigurable(ref)</code> must be called on the node
	 * reference.
	 * 
	 * A new template will be created if the template doen't exists or
	 * translation will be added on it.
	 * 
	 * @param ref
	 *            the node on which we have to add a template
	 * @param forTemplate
	 *            the mandatory template to define the associated "mail group"
	 * @param body
	 *            the mandatory body of the mail
	 * @param subject
	 *            the mandatory subject of the mail
	 * @param language
	 *            the mandatory language of the mail
	 * @throws CustomizationException
	 *             if a template already exist with this name and this Locale.
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "ref", "forTemplate", "body", "subject", "language" })
	public void replaceDefaultMailTemplate(final NodeRef ref, final MailTemplate forTemplate, final String body,
			final String subject, final Locale language) throws CustomizationException;

}
