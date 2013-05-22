/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.applicant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.web.app.AlfrescoNavigationHandler;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.ui.common.Utils;
import org.alfresco.web.ui.common.component.UIActionLink;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.util.I18NUtil;

import eu.cec.digit.circabc.repo.applicant.Applicant;
import eu.cec.digit.circabc.service.customisation.mail.MailTemplate;
import eu.cec.digit.circabc.service.customisation.mail.MailWrapper;
import eu.cec.digit.circabc.service.profile.ProfileManagerService;
import eu.cec.digit.circabc.service.user.UserService;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * Bean that backs manage the refuse applicant dialog.
 *
 * @author Yanick Pignot
 */
public class RefuseApplicantDialog extends BaseWaiDialog
{
	private static final long serialVersionUID = 4509470922669983727L;

	private static final Log logger = LogFactory.getLog(RefuseApplicantDialog.class);

	private static final String KEY_APPLICATION_DATE = "applicationDate";
	private static final String KEY_REASON = "reason";

	/** The name of this dialog */
	public static final String DIALOG_NAME = "RefuseApplicantDialog";

	public static final Locale DEFAULT_MAIL_LANGUAGE = I18NUtil.parseLocale("en");

	/** PersonService bean reference */
	private transient PersonService personService;

	//	the used message keys
	private static final String MESSAGE_ID_TITLE = "refuse_applicant_page_title";
	private static final String MESSAGE_ID_DESCRIPTION = "refuse_applicant_page_description";
	private static final String MESSAGE_ID_OK = "ok";
	private static final String MESSAGE_ID_CANCEL = "cancel";

	private String message;
	private Locale mailLanguage;
	private List<WebApplicant> applicants;

	@Override
	public void init(Map<String, String> arg0)
	{
		super.init(arg0);

		if(getActionNode() == null)
		{
			throw new IllegalArgumentException("The node ID is a manadtory parameter");
		}

		// the mail will be sent in English by default.
		this.mailLanguage = DEFAULT_MAIL_LANGUAGE;
		this.message = "";
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		final NodeRef currentSpace = getActionNode().getNodeRef();
		final ProfileManagerService profManagerService = getProfileManagerServiceFactory().getProfileManagerService(currentSpace);

        try
        {
			// send the mails
			sendMail();

			for (WebApplicant webApplicant : applicants)
			{
				// remove the currenty
				profManagerService.removeApplicantPerson(currentSpace, webApplicant.getApplicant().getUserName());
			}

			applicants = new ArrayList<WebApplicant>();

			// All the applications is correctly refused, Add confirmation
			// message
			Map<Object,Object> session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			session.put(ManageApplicantDialog.MANAGE_APPLICANT_DIALOG_INFORMATION_MESSAGE ,translate(ManageApplicantDialog.MESSAGE_REFUSE_APPLICATION_SUCCESS));


		}
        catch (Exception e)
        {
			logger.error("Impossible to refuse this/these appliation(s): "
					+ applicants + " in the interest group "
					+ getCurrentSpaceName(), e);

			Utils
				.addErrorMessage(translate(ManageApplicantDialog.MESSAGE_REFUSE_APPLICATION_FAILED), e);

		}

		return AlfrescoNavigationHandler.CLOSE_DIALOG_OUTCOME;
	}

    /**
     * @return the current space where the user launch this dialog
     */
    public String getCurrentSpaceName()
    {
    	return getActionNode().getName();
    }

    /**
     * Set the detail view of the user as Read Only
     */
    public void refuseApplicantAction(ActionEvent event)
    {
 	   // Get the id of the node and the selected version
 	   UIActionLink link = (UIActionLink)event.getComponent();
 	   Map<String, String> params = link.getParameterMap();

 	   // get the user name
 	   String userName = params.get("userName");

 	   // check that the parameter is not null. It can be empty, it means to remove all applicants.
       if (userName == null)
       {
           throw new IllegalArgumentException("userName is a mandatory parameter");
       }

 	   applicants = new ArrayList<WebApplicant>();

	   final ProfileManagerService profManagerService = getProfileManagerServiceFactory().getProfileManagerService(getActionNode().getNodeRef());

	   final Map<String, Applicant> allApplicants = profManagerService.getApplicantUsers(getActionNode().getNodeRef());

	   // if empty string, refuse all applicants!
	   if(userName.trim().length() < 1)
	   {
			// add all applicants
			for(final Applicant applicant: allApplicants.values())
			{
				applicants.add(new WebApplicant(applicant));
			}
	   }
	   else
	   {
			// add only the selected user
			applicants.add(new WebApplicant(allApplicants.get(userName)));
	   }
    }

    /**
     * Util method a preconfigured mail to notify to the user the reject of its application
     */
	private void sendMail()
    {
    	// For each applicant, send a refusing email
		for(final WebApplicant applicant : applicants)
		{
			final NodeRef personNodeRef = getPersonService().getPerson(applicant.getUserName());
			final String receiverEmail = (String) getNodeService().getProperty(personNodeRef, ContentModel.PROP_EMAIL);

			final NodeRef circabcRoot = getManagementService().getCircabcNodeRef();

        	final Map<String, Object> model = getMailPreferencesService().buildDefaultModel(getActionNode().getNodeRef(), personNodeRef, null);

        	model.put(KEY_APPLICATION_DATE, applicant.getDate());
        	model.put(KEY_REASON, this.message.trim());

        	final MailWrapper mail = getMailPreferencesService().getDefaultMailTemplate(circabcRoot, MailTemplate.REFUSE_APPLICATION);

        	final Serializable langObject = getUserService().getPreference(personNodeRef, UserService.PREF_INTERFACE_LANGUAGE);
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

    		try
		    {
				// Send the message
				getMailService().send(getMailService().getNoReplyEmailAddress(), receiverEmail,null ,mail.getSubject(model, locale), mail.getBody(model, locale), true);
		    }
		    catch (Exception e)
		    {
				// don't stop the action but let admins know email is not getting sent
				logger.warn("Failed to send email to " + receiverEmail, e);
		    }
		    catch (Throwable t)
		    {
				// don't stop the action but let admins know email is not getting sent
				logger.warn("Failed to send email to " + receiverEmail, t);
		    }
		}
    }

    @Override
    public Node getActionNode()
	{
		return getNavigator().getCurrentIGRoot();
	}

	/* (non-Javadoc)
	 * @see org.alfresco.web.bean.dialog.BaseDialogBean#getContainerDescription()
	 */
	@Override
	public String getContainerDescription()
	{
		// get the I18N description of the dialog in the extension/webclient.properties
		return translate(MESSAGE_ID_DESCRIPTION, getBestTitle(getActionNode()));
	}

	/* (non-Javadoc)
	 * @see org.alfresco.web.bean.dialog.BaseDialogBean#getContainerTitle()
	 */
	@Override
	public String getContainerTitle()
	{
		// get the I18N title of the dialog in the extension/webclient.properties
		return translate(MESSAGE_ID_TITLE);
	}

	/* (non-Javadoc)
	 * @see org.alfresco.web.bean.dialog.BaseDialogBean#getCancelButtonLabel()
	 */
	@Override
	public String getCancelButtonLabel()
	{
		// The cancel button must be renamed as 'Close'. No sens to keep 'cancel'
		return translate(MESSAGE_ID_CANCEL);
	}

	/* (non-Javadoc)
	 * @see org.alfresco.web.bean.dialog.BaseDialogBean#getFinishButtonLabel()
	 */
	@Override
	public String getFinishButtonLabel()
	{
		// The finish button must be renamed as 'OK'
		return translate(MESSAGE_ID_OK);
	}

	/* (non-Javadoc)
	 * @see org.alfresco.web.bean.dialog.BaseDialogBean#getFinishButtonDisabled()
	 */
	@Override
	public boolean getFinishButtonDisabled()
	{
		// always available, the message is optional
	    return false;
	}



	/* (non-Javadoc)
	 * @see org.alfresco.web.bean.dialog.BaseDialogBean#getErrorMessageId()
	 */
	@Override
	protected String getErrorMessageId()
	{
		return ManageApplicantDialog.MESSAGE_REFUSE_APPLICATION_FAILED;
	}

	/**
	 * @return the applicants to be refused
	 */
	public List<WebApplicant> getApplicants()
	{
		return applicants;
	}

	/**
	 * @return the message to explain the reason of the refusing to sent to the applicant
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message the message to explain the reason of the refusing to sent to the applicant to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return the Language in which the mail will be sent
	 */
	public Locale getMailLanguage()
	{
		return mailLanguage;
	}


	/**
	 * @param Language  in which the mail will be sent to set
	 */
	public void setMailLanguage(Locale mailLanguage)
	{
		this.mailLanguage = mailLanguage;
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
	public final void setPersonService(PersonService personService)
	{
		this.personService = personService;
	}

	public String getBrowserTitle()
	{
		//TODO change me
		return getContainerTitle();
	}

	public String getPageIconAltText()
	{
		// TODO change me
		return getContainerTitle();
	}
}
