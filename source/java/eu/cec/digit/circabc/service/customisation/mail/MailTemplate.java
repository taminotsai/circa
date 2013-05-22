/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.customisation.mail;

import java.text.MessageFormat;

import org.alfresco.service.cmr.repository.TemplateService;
import org.springframework.extensions.surf.util.I18NUtil;


/**
 * Define all email templates available.
 *
 * @author yanick Pignot
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * I18NUtil was moved to Spring.
 * This class seems to be developed for CircaBC
 */
public enum MailTemplate
{

	MAIL_ME_CONTENT
	{
		public String getTemplateDirectoryName()
		{
			return "mailMeContent";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("mail_me_content_subject", "${location.name}");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	MAIL_ME_DOSSIER
	{
		public String getTemplateDirectoryName()
		{
			return "mailMeDossier";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("mail_me_dossier_subject", "${location.name}");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	NOTIFY_DOC
	{
		public String getTemplateDirectoryName()
		{
			return "notifyDoc";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("notification_message_subject", "${location.name}");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	NOTIFY_POST
	{
		public String getTemplateDirectoryName()
		{
			return "notifyPost";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("notification_message_post_subject", "${titleOrName(location.parent)}");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	INVITE_USER
	{
		public String getTemplateDirectoryName()
		{
			return "inviteUser";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("invite_circabc_user_template_subject", "${titleOrName(interestGroup)}");
		}

		public boolean multipleAllowed()
		{
			return true;
		}
	},
	APPLY_FOR_MEMBERSHIP
	{
		public String getTemplateDirectoryName()
		{
			return "applyForMembership";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("apply_application_mail_template_subject", "${titleOrName(interestGroup)}");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	REFUSE_APPLICATION
	{
		public String getTemplateDirectoryName()
		{
			return "refuseApplication";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("reject_application_mail_template_subject", "${titleOrName(interestGroup)}");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	SEND_TO_MEMBERS
	{
		public String getTemplateDirectoryName()
		{
			return "sendToMembers";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return "";
		}

		public boolean multipleAllowed()
		{
			return true;
		}
	},
	SHARE_SPACE_NOTIFICATION
	{
		public String getTemplateDirectoryName()
		{
			return "shareSpace";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("share_space_subject", "${circabcPath(location)}");
		}

		public boolean multipleAllowed()
		{
			return true;
		}
	},
	EVENT_CREATE_NOTIFICATION
	{
		public String getTemplateDirectoryName()
		{
			return "eventCreateNotification";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("create_event_audience_notification_subject", "${appointment.title}");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	EVENT_UPDATE_NOTIFICATION
	{
		public String getTemplateDirectoryName()
		{
			return "eventUpdateNotification";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("update_event_audience_notification_subject", "${appointment.title}");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	EVENT_DELETE_NOTIFICATION
	{
		public String getTemplateDirectoryName()
		{
			return "eventDeleteNotification";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("delete_event_audience_notification_subject", "${appointment.title}");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	EVENT_REMINDER
	{
		public String getTemplateDirectoryName()
		{
			return "eventReminder";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("event_author_reminder_subject", "${appointment.title}");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	MEETING_REMINDER
	{
		public String getTemplateDirectoryName()
		{
			return "meetingReminder";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("meeting_author_reminder_subject", "${appointment.title}");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	REJECT_POST
	{
		public String getTemplateDirectoryName()
		{
			return "rejectPost";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("reject_post_mail_subject");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	SIGNAL_ABUSE
	{
		public String getTemplateDirectoryName()
		{
			return "signalAbuse";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("signal_abuse_mail_subject");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	SELF_REGISTRATION
	{
		public String getTemplateDirectoryName()
		{
			return "selfRegistration";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("self_registration_mail_subject");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	RESEND_OWN_PASSWORD
	{
		public String getTemplateDirectoryName()
		{
			return "resendOwnPwd";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("resend_password_mail_subject");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	RESEND_OTHER_PASSWORD
	{
		public String getTemplateDirectoryName()
		{
			return "resendOtherPwd";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("change_other_password_mail_subject");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	AUTO_UPLOAD_SUCCESS
	{
		public String getTemplateDirectoryName()
		{
			return "autoUploadSuccess";
		}

		public String getDefaultTemplateName()
		{
			return "success.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("auto_upload_mail_template_success_title");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	AUTO_UPLOAD_ERROR
	{
		public String getTemplateDirectoryName()
		{
			return "autoUploadError";
		}

		public String getDefaultTemplateName()
		{
			return "error.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("auto_upload_mail_template_error_title");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	AUTO_UPLOAD_FTP_PROBLEM
	{
		public String getTemplateDirectoryName()
		{
			return "autoUploadFtpProblem";
		}

		public String getDefaultTemplateName()
		{
			return "ftp-problem.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("auto_upload_mail_template_ftp_problem_title");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	},
	SUPPORT_REQUEST
	{
		public String getTemplateDirectoryName()
		{
			return "supportRequest";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}

		public String getDefaultSubject()
		{
			return translate("mail_template_support_contact_title");
		}

		public boolean multipleAllowed()
		{
			return false;
		}
	};

	public static final String KEY_ME = "me";
	public static final String KEY_PERSON = TemplateService.KEY_PERSON;
	public static final String KEY_DATE = TemplateService.KEY_DATE;
	public static final String KEY_IMAGE_RESOLVER = TemplateService.KEY_IMAGE_RESOLVER;
	public static final String KEY_COMPANY_HOME = TemplateService.KEY_COMPANY_HOME;
	public static final String KEY_USER_HOME = TemplateService.KEY_USER_HOME;
	public static final String KEY_LOCATION = "location";
	public static final String KEY_SPACE = "space";
	public static final String KEY_DOCUMENT = "document";
	public static final String KEY_CIRCABC = "circabc";
	public static final String KEY_INTEREST_GROUP = "interestGroup";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_APPOINTMENT = "appointment";
	public static final String KEY_EVENT_SERVICE = "eventService";
	public static final String KEY_APPOINTMENT_FIRST_OCCURENCE = "appointmentFirstOccurence";
	public static final String KEY_APPOINTMENT_ID = "appointmentId";
	public static final String KEY_PERMISSION = "permission";
	public static final String KEY_REJECT_DATE = "rejectDate";
	public static final String KEY_REJECT_REASON = "rejectReason";
	public static final String KEY_REJECTED_CONTENT = "rejectedContent";
	public static final String KEY_ABUSE_DATE = "abuseDate";
	public static final String KEY_ABUSE_REASON = "abuseReason";


	public abstract String getTemplateDirectoryName();

	public abstract String getDefaultTemplateName();

	public abstract String getDefaultSubject();

	public abstract boolean multipleAllowed();

	public static final MailTemplate getMailTemplateForFolderName(final String folder)
	{
		MailTemplate result = null;

		if(folder != null)
		{
			final MailTemplate[] templates = values();
			for (final MailTemplate template : templates)
			{
				if(template.getTemplateDirectoryName().equals(folder))
				{
					result = template;
					break;
				}
			}
		}

		return result;
	}

	public static String translate(final String key, final Object ... params)
	{
		if(params == null || params.length < 1)
		{
			return I18NUtil.getMessage(key);
		}
		else
		{
			return MessageFormat.format(I18NUtil.getMessage(key), params);
		}
	}
}