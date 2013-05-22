/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.notification;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import org.alfresco.model.ContentModel;
import org.alfresco.model.ForumModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.Auditable;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.repository.MLText;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.Path;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailSendException;

import eu.cec.digit.circabc.service.customisation.mail.MailPreferencesService;
import eu.cec.digit.circabc.service.customisation.mail.MailTemplate;
import eu.cec.digit.circabc.service.customisation.mail.MailWrapper;
import eu.cec.digit.circabc.service.log.LogRecord;
import eu.cec.digit.circabc.service.log.LogService;
import eu.cec.digit.circabc.service.mail.MailService;
import eu.cec.digit.circabc.service.notification.NotifiableUser;
import eu.cec.digit.circabc.service.notification.NotificationService;
import eu.cec.digit.circabc.service.notification.NotificationType;
import eu.cec.digit.circabc.service.profile.IGRootProfileManagerService;
import eu.cec.digit.circabc.service.struct.ManagementService;
import eu.cec.digit.circabc.util.PathUtils;
import eu.cec.digit.circabc.web.Services;

/**
 * @author filipsl
 *
 */
public class NotificationServiceImpl implements NotificationService
{
	private static final Log logger = LogFactory.getLog(NotificationServiceImpl.class);
	private static final Locale DEFAULT_MAIL_LOCALE = new Locale("en");

	// the services to inject
	private NodeService nodeService;
	/** Mail Service reference */
	private MailService mailService;
	/** Dictionary Service reference */
	private DictionaryService dictionaryService;
	/** The mail Preferences Service*/
	private MailPreferencesService mailPreferencesService;
	
	private IGRootProfileManagerService igRootProfileManagerService;
	
	private transient ManagementService managementService;	
	
    private LogService logService;

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.service.notification.NotificationService#notify(org.alfresco.service.cmr.repository.NodeRef)
	 */
	public void notify(final NodeRef nodeRef, final Set<NotifiableUser> users) throws Exception
	{
		if(users == null || users.size() < 1)
		{
			// no user to notify ... exit ...
			return;
		}

		if(nodeRef == null )
		{
			// node not specfied
			return;
		}

		if (getNodeService().exists(nodeRef))
		{
            // find it's type so we can see if it's a node we are interested in
            final QName type = getNodeService().getType(nodeRef);

            if(ForumModel.TYPE_POST.equals(type) || getDictionaryService().isSubClass(type, ForumModel.TYPE_POST))
            {
            	notifyImplForPost(nodeRef, users);
            }
            else
            {
            	notifyImplForContent(nodeRef, users);
            }
		}
	}
	
	private void LogNotification(NodeRef nodeRef, String to, String service, boolean ok, boolean AdminLog)
	{
		LogRecord logRecord = new LogRecord();
		logRecord.setActivity("Send Notification");
		logRecord.setService(service);
		logRecord.setInfo("Node: " + getBestTitle(nodeRef) + "; To: " + to);
		logRecord.setOK(ok);
		
		logRecord.setDocumentID((Long) getNodeService().getProperty(nodeRef, ContentModel.PROP_NODE_DBID));
		
		if (AdminLog)
		{
		    final NodeRef circabcNodeRef = getManagementService().getCircabcNodeRef();
			logRecord.setIgID((Long) getNodeService().getProperty(circabcNodeRef, ContentModel.PROP_NODE_DBID ));
			logRecord.setIgName((String) getNodeService().getProperty(circabcNodeRef, ContentModel.PROP_NAME));
		} else
		{
			final NodeRef igNodeRef = getManagementService().getCurrentInterestGroup(nodeRef);
			if (igNodeRef != null)
			{
				logRecord.setIgID((Long) getNodeService().getProperty(igNodeRef, ContentModel.PROP_NODE_DBID));
				logRecord.setIgName((String) getNodeService().getProperty(igNodeRef, ContentModel.PROP_NAME));
			}
		}
			
		logRecord.setUser(AuthenticationUtil.getFullyAuthenticatedUser());
		Path path = getNodeService().getPath(nodeRef);
		String displayPath = PathUtils.getCircabcPath(path ,true);
		displayPath = displayPath.endsWith("contains") ? displayPath.substring(0, displayPath.length() - "contains".length() ) : displayPath;
		logRecord.setPath(displayPath);
		
		getLogService().log(logRecord);	
	}
	
	protected String getBestTitle(final NodeRef nodeRef)
	{
		final String name = (String)getNodeService().getProperty(nodeRef, ContentModel.PROP_NAME);
		final String title = (String)getNodeService().getProperty(nodeRef, ContentModel.PROP_TITLE);

		if(title == null || title.trim().length() < 1)
		{
			return name;
		}
		else
		{
			return title;
		}
	}	

	private void notifyImplForContent(final NodeRef nodeRef, final Set<NotifiableUser> users) throws Exception {

		String email = null;
		Locale userLocale = null;
		MailWrapper mail = null;
		Map<String, Object> model = null;
		boolean connectFailed = false;
		// iterate users and send them emails
		for (final NotifiableUser user : users)
		{
			email = user.getEmailAddress();
			userLocale = user.getNotificationLanguage();
			if (userLocale == null)
			{
				userLocale = DEFAULT_MAIL_LOCALE;
			}

			model = getMailPreferencesService().buildDefaultModel(nodeRef, user.getPerson(), null);
			mail = getMailPreferencesService().getDefaultMailTemplate(nodeRef, MailTemplate.NOTIFY_DOC);

			boolean result = false;
			if (email != null)
			{
				try
				{
					result = getMailService().send(getMailFrom(), email, null, mail.getSubject(model, userLocale), mail.getBody(model, userLocale), true);
				}
				catch (final MailSendException mse)
				{
					if(!connectFailed)
					{
						//Don't want to log all errors for the same kind of exception
						connectFailed = true;
						if (logger.isErrorEnabled())
						{
							logger.error("Could not connect to Mail Server:" , mse);
						}
					}
				} catch (final Exception e)
				{
					if (logger.isWarnEnabled())
					{
						logger.warn("Could not send notification to user:" + user, e);
					}
				}
			}
			String to = (email == null ? user.getUserName() : email);
			to = (to == null ? "email and user name are null!" : to);
			LogNotification(nodeRef, to, "Library", result, false);
			LogNotification(nodeRef, to, "Library", result, true);			
		}
	}

	private void notifyImplForPost(final NodeRef nodeRef, final Set<NotifiableUser> users) throws Exception
	{
		String email = null;
		Locale userLocale = null;
		MailWrapper mail = null;
		Map<String, Object> model = null;

		// iterate users and send them emails
		for (final NotifiableUser user : users)
		{
			email = user.getEmailAddress();
			userLocale = user.getNotificationLanguage();
			if(userLocale == null)
			{
				userLocale = DEFAULT_MAIL_LOCALE;
			}

			model = getMailPreferencesService().buildDefaultModel(nodeRef, user.getPerson(), null);
			mail = getMailPreferencesService().getDefaultMailTemplate(nodeRef, MailTemplate.NOTIFY_POST);

			boolean result = false;
			if(email != null)
			{
				 result = getMailService().send(getMailFrom(), email,  null, mail.getSubject(model, userLocale), mail.getBody(model, userLocale), true);
			}
			String to = (email == null ? user.getUserName() : email);
			to = (to == null ? "email and user name are null!" : to);			
			LogNotification(nodeRef, to, "Newsgroup", result, false);
			LogNotification(nodeRef, to, "Newsgroup", result, true);			
		}
	}

	private String getSafeProperty(Locale locale, final QName qname, final Map<QName, Serializable> props)
	{
		if(locale == null)
		{
			locale = DEFAULT_MAIL_LOCALE;
		}

		final Serializable value = props.get(qname);

		return getSafeValue(locale, value);
	}

	private String getSafeValue(Locale locale, final Object value)
	{
		if(value == null)
		{
			return null;
		}
		else if(value instanceof MLText)
		{
			final MLText mlValues = (MLText) value;

			if(mlValues.containsKey(locale))
			{
				return mlValues.get(locale);
			}
			else if(mlValues.containsKey(DEFAULT_MAIL_LOCALE))
			{
				return mlValues.get(DEFAULT_MAIL_LOCALE);
			}
			else
			{
				return mlValues.getClosestValue(locale);
			}

		}
		else if(value instanceof List)
		{
			final List list = (List) value;
			final StringBuffer buff = new StringBuffer("");
			boolean first = true;

			for (final Object object : list)
			{
				if(first)
				{
					first = false;
				}
				else
				{
					buff.append(", ");
				}

				buff.append(getSafeValue(locale, object));
			}

			return buff.toString();

		}
		else if(value instanceof NodeRef)
		{
			// for node ref, get arbitrary the title
			return getSafeProperty(locale, ContentModel.PROP_TITLE, getNodeService().getProperties((NodeRef) value));
		}
		else
		{
			return value.toString();
		}
	}

	/**
	 * @return the mailFrom
	 */
	public String getMailFrom() {
		return getMailService().getNoReplyEmailAddress();
	}


	/**
	 * @return the mailService
	 */
	private final MailService getMailService()
	{
		return mailService;
	}

	/**
	 * @param mailService the mailService to set
	 */
	public final void setMailService(MailService mailService)
	{
		this.mailService = mailService;
	}

	/**
	 * @return the nodeService
	 */
	private final NodeService getNodeService()
	{
		return nodeService;
	}

	/**
	 * @param nodeService the nodeService to set
	 */
	public final void setNodeService(NodeService nodeService)
	{
		this.nodeService = nodeService;
	}

   /**
    * @param dictionaryService The DictionaryService to set.
    */
   public void setDictionaryService(DictionaryService dictionaryService)
   {
	   this.dictionaryService = dictionaryService;
   }

   private DictionaryService getDictionaryService()
   {
	   return dictionaryService;
   }

   /**
	 * @return the nodePreferencesService
	 */
   	private final MailPreferencesService getMailPreferencesService()
	{
		return mailPreferencesService;
	}


	/**
	 * @param nodePreferencesService the nodePreferencesService to set
	 */
	public final void setMailPreferencesService(MailPreferencesService mailPreferencesService)
	{
		this.mailPreferencesService = mailPreferencesService;
	}
	
	/**
	 * @param managementService the managementService to set
	 */
	public void setManagementService(ManagementService managementService)
	{
		this.managementService = managementService;
	}

	/**
	 * @return the managementService
	 */
	public ManagementService getManagementService()
	{
		if (managementService == null)
		{
			managementService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getManagementService();
		}
		return managementService;
	}

	/**
	 * @return the logService
	 */
	public LogService getLogService() {
		return logService;
	}


	/**
	 * @param logService the logService to set
	 */
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Override
	public void notify(NodeRef nodeRef, List<String> mails, MailTemplate templateType) throws Exception {

		if(mails == null || mails.size() < 1)
		{
			// no user to notify ... exit ...
			return;
		}

		if(nodeRef == null )
		{
			// node not specfied
			return;
		}

		if (getNodeService().exists(nodeRef))
		{
            // find it's type so we can see if it's a node we are interested in
            final QName nodeType = getNodeService().getType(nodeRef);

            if(ForumModel.TYPE_POST.equals(nodeType) || getDictionaryService().isSubClass(nodeType, ForumModel.TYPE_POST))
            {
            	notifyImplForPost(nodeRef, mails, templateType);
            }
            else
            {
            	notifyImplForContent(nodeRef, mails, templateType);
            }
		}
		
	}

	private void notifyImplForContent(NodeRef nodeRef, List<String> mails, MailTemplate templateType) {

		MailWrapper mail = null;
		Map<String, Object> model = null;
		boolean connectFailed = false;
		
		// iterate users and send them emails
		
		for (final String email : mails)
		{


			model = getMailPreferencesService().buildDefaultModel(nodeRef, null, null);
			mail = getMailPreferencesService().getDefaultMailTemplate(nodeRef, templateType);

			boolean result = false;
			if (email != null)
			{
				try
				{
					
					result = getMailService().send(getMailFrom(), email,  null, mail.getSubject(model ,DEFAULT_MAIL_LOCALE), mail.getBody(model , DEFAULT_MAIL_LOCALE), true);
					LogNotification(nodeRef, email, "Auto-upload", result, true);
				}
				catch (final MailSendException mse)
				{
					if(!connectFailed)
					{
						//Don't want to log all errors for the same kind of exception
						connectFailed = true;
						if (logger.isErrorEnabled())
						{
							logger.error("Could not connect to Mail Server:" , mse);
						}
					}

					LogNotification(nodeRef, email, "Auto-upload", result, false);
				} catch (final Exception e)
				{
					if (logger.isWarnEnabled())
					{
						logger.warn("Could not send notification to user:" + email, e);
					}

					LogNotification(nodeRef, email, "Auto-upload", result, false);
				}
			}

						
		}
		
	}

	private void notifyImplForPost(NodeRef nodeRef, List<String> mails, MailTemplate templateType) {
		
		
	}

	/***
	 * currently notification service only provide methods to notify about upload & edition....
	 * As we need to send notification when inviting users, we need another method more generic (can be used in future for many kind of notifications)
	 */
	@Override
	@Auditable(parameters = { "nodeRef", "users" , "notificationType"})
	public void notify(NodeRef nodeRef, Set<NotifiableUser> users,	NotificationType notificationType) throws Exception {

		if(nodeRef != null)
		{
			Iterator<NotifiableUser> iUser = users.iterator();
			
			while(iUser.hasNext())
			{
				NotifiableUser nUser = iUser.next();
				
				if(notificationType.equals(NotificationType.NOTIFY_USER_INVITATION))
				{
					notifyImplInviteUser(nodeRef, nUser);

				}
			}
		}
		
	}

	/**
	 * @param nodeRef
	 * @param nUser
	 * @throws MessagingException
	 */
	private void notifyImplInviteUser(NodeRef nodeRef, NotifiableUser nUser)
			throws MessagingException {
		String email = nUser.getEmailAddress();
		Locale userLocale = nUser.getNotificationLanguage();
		if(userLocale == null)
		{
			userLocale = DEFAULT_MAIL_LOCALE;
		}

		Map<String, Object> model = getMailPreferencesService().buildDefaultModel(nodeRef, null, null);
		MailWrapper mail = getMailPreferencesService().getDefaultMailTemplate(nodeRef, MailTemplate.INVITE_USER);
		
		String profile = igRootProfileManagerService.getPersonProfile(nodeRef, nUser.getUserName());
		model.put("profile", profile);

		boolean result = false;
		if(email != null)
		{
			 result = getMailService().send(getMailFrom(), email,  null, mail.getSubject(model, userLocale), mail.getBody(model, userLocale), true);
		}
		
		String to = (email == null ? nUser.getUserName() : email);
		to = (to == null ? "email and user name are null!" : to);	

		LogNotification(nodeRef, to, "Directory", result, false);
	}

	/**
	 * @return the igRootProfileManagerService
	 */
	public IGRootProfileManagerService getIgRootProfileManagerService() {
		return igRootProfileManagerService;
	}

	/**
	 * @param igRootProfileManagerService the igRootProfileManagerService to set
	 */
	public void setIgRootProfileManagerService(
			IGRootProfileManagerService igRootProfileManagerService) {
		this.igRootProfileManagerService = igRootProfileManagerService;
	}
}
