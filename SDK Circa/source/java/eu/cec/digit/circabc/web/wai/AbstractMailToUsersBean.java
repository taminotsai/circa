/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.mail.MessagingException;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.TemplateService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.alfresco.web.bean.TemplateSupportBean;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.ui.common.Utils;
import org.alfresco.web.ui.common.component.UIActionLink;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import eu.cec.digit.circabc.business.api.nav.NavigationBusinessSrv;
import eu.cec.digit.circabc.service.customisation.mail.MailTemplate;
import eu.cec.digit.circabc.service.customisation.mail.MailWrapper;
import eu.cec.digit.circabc.service.profile.CategoryProfileManagerService;
import eu.cec.digit.circabc.service.profile.CircabcRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.IGRootProfileManagerService;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.wai.wizard.BaseWaiWizard;

/**
 * @author Stephane Clinckart
 */
public abstract class AbstractMailToUsersBean extends BaseWaiWizard
{
	private static final Log logger = LogFactory.getLog(AbstractMailToUsersBean.class);

	private static final String MESSAGE_ID_NO_SUBJECT = "mail_no_subject_error";
	private static final String MESSAGE_ID_NO_BODY = "mail_no_body_error";

	/** dialog state */
	private String subject = null;
	private String body = null;
	private String template = null;
	private String usingTemplate = null;

	private List<MailWrapper> templates = null;


	private String callBackMethodName;

	/** personService bean reference */
	private transient PersonService personService;

	/** AuthorityService bean reference */
	private transient AuthorityService authorityService;

	private transient TemplateService templateService;

	private transient TransactionService transactionService;

	private transient NavigationBusinessSrv navigationBusinessSrv;
	/**
	 * Initialises the wizard
	 */
	@Deprecated
	public void init(final ActionEvent event) {
		// Get the id of the node and the selected version
		final UIActionLink link = (UIActionLink) event.getComponent();
		final Map<String, String> params = link.getParameterMap();
		this.init(params);
	}

	/**
	 * Initialises the wizard
	 */
	public void init(final Map<String, String> params) {

		super.init(params);

		template = null;
		usingTemplate = null;
		templates = null;
	}

	protected abstract String finishImpl(final FacesContext context, final String outcome) throws Exception;

	/**
	 * Send an email notification to the specified User authority
	 *
	 * @param person
	 *            Person node representing the user
	 * @param node
	 *            Node they are invited too
	 * @param from
	 *            From text message
	 * @param extraBodyParams  Parameters added to the mail body defined in the text area (used if this.usingTemplate == null)
	 * @param extraModelParams Parameters added to the mail template model (used if this.usingTemplate != null)
	 */
	protected void mailToUser(final NodeRef person, final NodeRef node, final String from, final Map<String, Object> extraModelParams, final Map<String, String> extraBodyParams)
	{
		final Map<QName, Serializable> personProperties = getNodeService().getProperties(person);
		final String to = (String) personProperties.get(ContentModel.PROP_EMAIL);
		String noReply = getMailService().getNoReplyEmailAddress();

		if (to != null && to.length() != 0)
		{
			String bodyToSend = getBody();

			if (this.usingTemplate != null)
			{
				final Map<String, Object> model = getMailPreferencesService().buildDefaultModel(node, person, null);
				model.putAll(extraModelParams);
				final NodeRef templateRef = new NodeRef(Repository.getStoreRef(), this.usingTemplate);
				bodyToSend = getTemplateService().processTemplate("freemarker", templateRef.toString(), model);
			}
			else
			{
				bodyToSend = applyParams(getBody(), extraBodyParams).replace("\n", "<br />").replace("\r", "<br />");
			}

			try
			{
				// Send the message
				getMailService().send(noReply , to, from,  applyParams(getSubject(), extraBodyParams), bodyToSend, isMailHtml());
			}
			catch (final MessagingException e)
			{
				// the parameters should be false
				if (logger.isWarnEnabled()) {
					logger.warn("Failed to send email to " + to, e);
				}
			}
		}
	}

	protected boolean validateMailData()
	{
		boolean valid = true;

		if(getSubject() == null || getSubject().trim().length() < 1)
		{
			valid = false;
			Utils.addErrorMessage(translate(MESSAGE_ID_NO_SUBJECT));
		}
		if(getBody() == null || getBody().trim().length() < 1)
		{
			valid = false;
			Utils.addErrorMessage(translate(MESSAGE_ID_NO_BODY));
		}

		return valid;
	}

	private String applyParams(final String body, final Map<String, String> extraBodyParams) {
		String bodyToUpdate = body;
		for (final Entry<String, String> entry : extraBodyParams.entrySet()) {
			if (entry.getValue() != null) {
				bodyToUpdate = bodyToUpdate.replace(entry.getKey(), entry.getValue());
			}
		}
		return bodyToUpdate;
	}

	protected boolean isMailHtml() {
		return true;
	}

	/**
	 * Action handler called to insert a template as the email body
	 */
	public void insertTemplate(final ActionEvent event)
	{
		if (this.template != null && this.template.equals(TemplateSupportBean.NO_SELECTION) == false)
		{
			// get the content of the template so the user can get a basic preview of it
			try
			{
				for (final MailWrapper wrap : templates)
				{
					if(wrap.getTemplateNodeRef().getId().equals(this.template))
					{
						String mailBody = wrap.getBody(null);
						mailBody = cleanBody(mailBody);
						setBody(mailBody);
						setSubject(wrap.getSubject(null));

						break;
					}
				}

				this.usingTemplate = this.template;
			}
			catch (final Throwable err)
			{
				Utils.addErrorMessage(translate(Repository.ERROR_GENERIC, err.getMessage()), err);
			}
		}
	}

	/**
	 * Action handler called to discard the template from the email body
	 */
	public void discardTemplate(final ActionEvent event)
	{
		// don't reset the template
		usingTemplate = null;
	}

	/**
	 * @return Returns the email body text.
	 */
	public String getBody() {
		return this.body;
	}

	/**
	 * @param body
	 *            The email body text to set.
	 */
	public void setBody(final String bdy) 
	{

		this.body =  bdy ;
	}
	
	public String cleanBody(final String bdy)
	{
		Whitelist basicWhitelist = new Whitelist();
		basicWhitelist.addTags("p", "span", "strong", "b", "i", "u", "br", "sub", "sup", "a");
		basicWhitelist.addAttributes(":all", "style", "href");
		return Jsoup.clean(bdy, basicWhitelist);
	}	/**
	 * @return Returns the email subject text.
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * @param subject
	 *            The email subject text to set.
	 */
	public void setSubject(final String subject) {
		this.subject = subject;
	}

	/**
	 * @return Returns the email template Id
	 */
	public String getTemplate()
	{
		return this.template;
	}

	/**
	 * @param template
	 *            The email template to set.
	 */
	public void setTemplate(final String template) {
		this.template = template;
	}

	/**
	 * @return Returns if a template has been inserted by a user for email body.
	 */
	public String getUsingTemplate() {
		return this.usingTemplate;
	}

	/**
	 * @return the list of available Email Templates.
	 */
	public List<SelectItem> getEmailTemplates() {

		if (template == null)
		{
			templates = getMailPreferencesService().getMailTemplates(getActionNode().getNodeRef(), getMailTemplateDefinition());
		}

		final List<SelectItem> items = new ArrayList<SelectItem>(templates.size());

		for (final MailWrapper wrap : templates)
		{
			if(wrap.isOriginalTemplate() == false)
			{
				items.add(new SelectItem(wrap.getTemplateNodeRef().getId(), wrap.getName()));
			}
		}
		return items;
	}

	/**
	 * @param usingTemplate
	 *            Template that has been inserted by a user for the email body.
	 */
	public void setUsingTemplate(final String usingTemplate)
	{
		this.usingTemplate = usingTemplate;
	}

	public String getBuildTextMessage()
	{
		final RetryingTransactionHelper txnHelper = getTransactionService().getRetryingTransactionHelper();

		final RetryingTransactionCallback<String> callback = new RetryingTransactionCallback<String>()
		{
			public String execute() throws Throwable
			{
				final NodeRef person = getTemplatePerson();
				final MailWrapper mail = getMailPreferencesService().getDefaultMailTemplate(getActionNode().getNodeRef(), getMailTemplateDefinition());
				final Map<String, Object> model = getMailPreferencesService().buildDefaultModel(getActionNode().getNodeRef(),
						person, null);

				model.putAll(getDisplayModelToAdd());
				// remove template noise
				String htmlBody = mail.getBody(model).replace("\n", "").replace("\r", "");
				
				// the following is not necessary anymore when using tinymce
				// the input text area doesn't escape html. Make the display user friendly.
				//final String textBody = htmlBody.replace("<br />", "\n").replace("<br/>", "\n");
							
				setBody(htmlBody);
				setSubject(mail.getSubject(model));

				return "true";
			}
		};

		return txnHelper.doInTransaction(callback, false, true);
	}

	protected abstract MailTemplate getMailTemplateDefinition();

	protected abstract Map<String, Object> getDisplayModelToAdd();

	/**
	 * @return the personService
	 */
	protected final PersonService getPersonService() {
		if (personService == null) {
			personService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getPersonService();
		}
		return personService;
	}

	/**
	 * @param personService
	 *            the personService to set
	 */
	public final void setPersonService(final PersonService personService) {
		this.personService = personService;
	}

	/**
	 * @return the authorityService
	 */
	protected final AuthorityService getAuthorityService() {
		if (authorityService == null) {
			authorityService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getAuthorityService();
		}
		return authorityService;
	}

	protected CircabcRootProfileManagerService getCircabcRootProfileManagerService() {
		return getProfileManagerServiceFactory().getCircabcRootProfileManagerService();
	}

	protected CategoryProfileManagerService getCategoryProfileManagerService() {
		return getProfileManagerServiceFactory().getCategoryProfileManagerService();
	}

	protected IGRootProfileManagerService getIGRootProfileManagerService() {
		return getProfileManagerServiceFactory().getIGRootProfileManagerService();
	}

	/**
	 * @param authorityService
	 *            the authorityService to set
	 */
	public final void setAuthorityService(final AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public boolean isFormProvided() {
		return false;
	}

	public String getCallBackMethodName() {
		return callBackMethodName;
	}

	public void setCallBackMethodName(final String callBackMethodName) {
		this.callBackMethodName = "#{" + callBackMethodName + "}";
	}


	/**
	 * @return the templateService
	 */
	protected final TemplateService getTemplateService()
	{
		if(templateService == null)
		{
			templateService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getTemplateService();
		}
		return templateService;
	}

	/**
	 * @param templateService the templateService to set
	 */
	public final void setTemplateService(TemplateService templateService)
	{
		this.templateService = templateService;
	}

	/**
	 * @return the transactionService
	 */
	protected final TransactionService getTransactionService()
	{
		if(transactionService == null)
		{
			transactionService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getTransactionService();
		}
		return transactionService;
	}

	/**
	 * @param transactionService the transactionService to set
	 */
	public final void setTransactionService(TransactionService transactionService)
	{
		this.transactionService = transactionService;
	}

	/**
	 * @return
	 */
	protected NodeRef getTemplatePerson()
	{
		final String templateUser = getMailPreferencesService().getTemplateUserDetails().getUserName();
		final NodeRef person = getPersonService().getPerson(templateUser);
		return person;
	}

	/**
	 * @return
	 */
	protected NodeRef getCurrentPerson()
	{
		 final String currentUsername = AuthenticationUtil.getFullyAuthenticatedUser();
		 final NodeRef person = getPersonService().getPerson(currentUsername);
		 return person;
	}
	
	public NavigationBusinessSrv getNavigationBusinessSrv()
	{
		return navigationBusinessSrv;
	}

	public void setNavigationBusinessSrv(final NavigationBusinessSrv navigationBusinessSrv)
	{
		this.navigationBusinessSrv = navigationBusinessSrv;
	}
	
}