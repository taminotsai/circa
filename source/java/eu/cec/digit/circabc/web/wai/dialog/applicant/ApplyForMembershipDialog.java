/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.applicant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.extensions.surf.util.I18NUtil;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.web.app.Application;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.bean.repository.User;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.service.customisation.mail.MailTemplate;
import eu.cec.digit.circabc.service.customisation.mail.MailWrapper;
import eu.cec.digit.circabc.service.profile.ProfileManagerService;
import eu.cec.digit.circabc.service.profile.permissions.DirectoryPermissions;
import eu.cec.digit.circabc.service.user.UserService;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.repository.InterestGroupNode;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;


/**
 *	Bean that backs the apply for membership WAI page. The behaviour of the page is different accroding to
 *	the user is authenticated (logged) or not (guest).
 *
 * @author Yanick Pignot
 */
public class ApplyForMembershipDialog extends BaseWaiDialog
{

	private static final long serialVersionUID = 4283648430341451014L;

	/** Public JSF Bean name */
	public static final String BEAN_NAME = "ApplyForMembershipDialog";

	/** the circabc message bundle name */
	public static final String CIRCA_MESSAGE_BUNDLE = "alfresco.extension.webclient";

	public static final Locale DEFAULT_MAIL_LANGUAGE = I18NUtil.parseLocale("en");

	/** Logger (coppepa: logger must be final) */
	private static final Log logger = LogFactory.getLog(ApplyForMembershipDialog.class);

	/** the person service reference */
	private transient PersonService personService;

	// the message to send to the ig group leaders
	private String message;
	// the language of this mail
	private Locale mailLanguage;

	private static final String KEY_APPLICATION_MESSAGE = "applicationMessage";
	private static final String KEY_APPLICATION_DATE= "applicationDate";

	//	the used message keys
	private static final String MESSAGE_ID_PAGE_TITLE_LOG = "apply_for_membership_page_title";
	private static final String MESSAGE_ID_PAGE_TITLE_NO_LOG = "apply_for_membership_no_log_page_title";

	private static final String MESSAGE_ID_PAGE_DESCRIPTION_LOG = "apply_for_membership_page_description";
	private static final String MESSAGE_ID_PAGE_DESCRIPTION_NO_LOG = "apply_for_membership_no_log_page_description";


	/**
	 * @return the title of the page
	 */
	@Override
	public String getContainerTitle()
	{
		return isGuest() ?
				translate(MESSAGE_ID_PAGE_TITLE_NO_LOG) :
				translate(MESSAGE_ID_PAGE_TITLE_LOG, getActionNode().getName());
	}

	/**
	 * @return the description of the page (line juste after the title)
	 */
	@Override
	public String getContainerDescription()
	{
		return isGuest() ?
				translate(MESSAGE_ID_PAGE_DESCRIPTION_NO_LOG) :
				translate(MESSAGE_ID_PAGE_DESCRIPTION_LOG, getActionNode().getProperties().get(ContentModel.PROP_DESCRIPTION));
	}

	/**
	 * If the user is authenticated, the introduction text is the description of the Interest Group
	 * If the user is not authenticated (guest), the introduction text describe how to apply
	 *
	 * @return the introduction text of the page.
	 */
	public String getInterestGroupDesc()
	{
		// get the properties and return it if setted
		final Object desc  = getActionNode().getProperties().get(ContentModel.PROP_DESCRIPTION);
		if(desc != null)
		{
			return desc.toString();
		}

		// or get the title and return it if setted
		final Object title = getActionNode().getProperties().get(ContentModel.PROP_TITLE);
		if(title != null)
		{
			return title.toString();
		}
		else
		{
			// if no titke and no description setted. Return the name that is a secure property.
			return getActionNode().getName();
		}
	}

	/**
	 * @return true if the user is logged as Guest.
	 */
	public boolean isGuest()
	{
		return getNavigator().getIsGuest();
	}

	public String getContactInfo()
	{
		final Object contactInfo = getActionNode().getProperties().get(CircabcModel.PROP_CONTACT_INFORMATION);

		return (contactInfo == null || contactInfo.toString().length() < 1)
  	  			? translate(InterestGroupNode.MSG_NO_CONTACT_INFO)
  	  			: contactInfo.toString();
	}

	/**
	 * Action launched by the submit of the application form.
	 *
	 * It gets the message to send, the email of the applicant and send a pre-formated
	 * message to all lib admin and user admin of the current interest group
	 */

	protected String finishImpl(final FacesContext context, final String outcome)
	{

		final NodeRef currentNodeRef = getActionNode().getNodeRef();

		//get the current user and get the needed properties.
    	final User user = Application.getCurrentUser(context);

    	final String userName = user.getUserName();
    	final String firstName = (String) getNodeService().getProperty(user.getPerson(), ContentModel.PROP_FIRSTNAME);
    	final String lastName = (String) getNodeService().getProperty(user.getPerson(), ContentModel.PROP_LASTNAME);
    	final Date date = new Date();

    	RetryingTransactionHelper txnHelper = Repository .getRetryingTransactionHelper(context);
		RetryingTransactionCallback<String> callback = new RetryingTransactionCallback<String>()
		{
			public String execute() throws Throwable
			{
				try
				{
					// set the user being an applicant
					ProfileManagerService profService = getProfileManagerServiceFactory().getProfileManagerService(currentNodeRef);
					profService.addApplicantPerson(currentNodeRef, userName, getMessage(), firstName, lastName, date);

					return Boolean.valueOf(true).toString();

				}
				catch (Exception e)
				{
					if (logger.isErrorEnabled())
						logger.error("Impossible to add "
												+ userName
												+ " in the list of applicants of the interest group:"
												+ getActionNode().getName(), e);

					Utils.addErrorMessage(translate(ManageApplicantDialog.MESSAGE_APPLY_MEMBERSHIP_FAILED,
													e.getMessage()), e);

					return Boolean.valueOf(false).toString();
				}
			}
		};

         String done = txnHelper.doInTransaction(callback, false, true);

         if( Boolean.valueOf(done).booleanValue() == true)
         {
   		   // the application is correctly taken in account.
 		   // add confirmation message
     	   Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(ManageApplicantDialog.MESSAGE_APPLY_MEMBERSHIP_SUCCESS));

           // send the mails
           sendMail(context, date);
         }

         return CircabcNavigationHandler.CLOSE_DIALOG_OUTCOME;
	}

    /**
     * Util method a preconfigured mail to notify to the user the reject of its application
     * @param date
     */
	private void sendMail(final FacesContext context, final Date date)
    {
    	final NodeRef currentNodeRef = getActionNode().getNodeRef();

    	//get the current user and properties, he is the applicant
    	final User user = Application.getCurrentUser(context);
		final NodeRef applicant = user.getPerson();
		final String applicantAdr = (String) getNodeService().getProperty(applicant, ContentModel.PROP_EMAIL);

    	// get the dir admins of the interest group
		final Set<String> dirAdmins = getUserService().getUsersWithPermission(currentNodeRef, DirectoryPermissions.DIRADMIN.toString());
    	// get the manage_members persons of the interest group
    	final Set<String> userAdmins = getUserService().getUsersWithPermission(currentNodeRef, DirectoryPermissions.DIRMANAGEMEMBERS.toString());

    	// Eliminate the eventual doublons and construct a list.
    	final List<String> receivers = new ArrayList<String>(dirAdmins.size() + userAdmins.size());
    	receivers.addAll(dirAdmins);
    	receivers.addAll(userAdmins);

    	// Send an email to each admin
    	for(final String receiver : receivers)
    	{
    		final NodeRef adminRef  = getPersonService().getPerson(receiver);
    		final String adminEmail = (String) getNodeService().getProperty(adminRef, ContentModel.PROP_EMAIL);

    		final Map<String, Object> model = getMailPreferencesService().buildDefaultModel(currentNodeRef, adminRef, null);
    		model.put(KEY_APPLICATION_MESSAGE, this.message.trim());
    		model.put(KEY_APPLICATION_DATE, date);

			final Serializable langObject = getUserService().getPreference(adminRef, UserService.PREF_INTERFACE_LANGUAGE);
			final Locale locale;
			if(langObject == null)
			{
				locale = null;
			}
			else if (langObject instanceof Locale)
			{
				locale = (Locale) langObject;
			}
			else
			{
				locale = new Locale(langObject.toString());
			}

    		final MailWrapper mail = getMailPreferencesService().getDefaultMailTemplate(currentNodeRef, MailTemplate.APPLY_FOR_MEMBERSHIP);

    		final String subject = mail.getSubject(model, locale);
    		final String body = mail.getBody(model, locale);

    		try
		    {
				// Send the message
	      		getMailService().send(applicantAdr, adminEmail,null , subject, body, true);
		    }
		    catch (Exception e)
		    {
		    	// don't stop the action but let admins know email is not getting sent
		    	if(logger.isWarnEnabled())
		    		logger.warn("Failed to send application email to " + adminEmail
		    				+ "\n...with subject:\n" + subject
		    				+ "\n...with body:\n" + body, e);

		    }

    	}
    }

	/**
	 * Display the corresponding Circa IG Group. The user click on a Circa Category inside the Circa Categorie Home page
	 *
	 * @param event The actionEvent which launch the action
	 */
    @Override
	public void init(final Map<String, String> parameters)
	{
		super.init(parameters);

		this.message = "";
		this.mailLanguage = DEFAULT_MAIL_LANGUAGE;

		if(getActionNode() == null)
		{
			throw new IllegalArgumentException("The node id is a mandatory parameter");
		}
	}

	/**
	 * @return the message to sent to ig leaders
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message the message to sent to ig leaders set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return the mailLanguage
	 */
	public Locale getMailLanguage()
	{
		return mailLanguage;
	}

	/**
	 * @param mailLanguage the mailLanguage to set
	 */
	public void setMailLanguage(final Locale mailLanguage)
	{
		this.mailLanguage = mailLanguage;
	}

	public String getBrowserTitle()
	{
		return translate("apply_for_membership_page");
	}

	public String getPageIconAltText()
	{
		return getActionNode().getName();
	}

	@Override
	public boolean isCancelButtonVisible()
	{
		return false;
	}

	/**
	 * @return the personService
	 */
	protected final PersonService getPersonService()
	{
		if(personService == null)
		{
			personService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getPersonService();
		}
		return personService;
	}

	/**
	 * @param personService the personService to set
	 */
	public final void setPersonService(final PersonService personService)
	{
		this.personService = personService;
	}


}
