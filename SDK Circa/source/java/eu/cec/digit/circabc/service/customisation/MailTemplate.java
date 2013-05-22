/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.customisation;

import org.alfresco.service.cmr.repository.TemplateService;

/**
 * Define all email templates available.
 *
 * @author yanick Pignot
 */
public enum MailTemplate
{
	BODY_INVITE_USER
	{
		public String getTemplateDirectoryName()
		{
			return "inviteUser";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_MAIL_ME_CONTENT
	{
		public String getTemplateDirectoryName()
		{
			return "mailMeContent";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_MAIL_ME_DOSSIER
	{
		public String getTemplateDirectoryName()
		{
			return "mailMeDossier";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_APPLY_FOR_MEMEBERSHIP
	{
		public String getTemplateDirectoryName()
		{
			return "applyForMembership";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_REFUSE_APPLICATION
	{
		public String getTemplateDirectoryName()
		{
			return "refuseApplication";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_SELF_REGISTRATION
	{
		public String getTemplateDirectoryName()
		{
			return "selfRegistration";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_NOTIFY_DOC
	{
		public String getTemplateDirectoryName()
		{
			return "notifyDoc";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_NOTIFY_POST
	{
		public String getTemplateDirectoryName()
		{
			return "notifyPost";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_RESEND_OWN_PASSWORD
	{
		public String getTemplateDirectoryName()
		{
			return "resendOwnPwd";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_RESEND_OTHER_PASSWORD
	{
		public String getTemplateDirectoryName()
		{
			return "resendOtherPwd";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_MEETING_REMINDER
	{
		public String getTemplateDirectoryName()
		{
			return "meetingReminder";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_EVENT_REMINDER
	{
		public String getTemplateDirectoryName()
		{
			return "eventReminder";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_EVENT_DELETE_NOTIFICATION
	{
		public String getTemplateDirectoryName()
		{
			return "eventDeleteNotification";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_EVENT_UPDATE_NOTIFICATION
	{
		public String getTemplateDirectoryName()
		{
			return "eventUpdateNotification";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_EVENT_CREATE_NOTIFICATION
	{
		public String getTemplateDirectoryName()
		{
			return "eventCreateNotification";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_MEETING_DELETE_NOTIFICATION
	{
		public String getTemplateDirectoryName()
		{
			return "meetingDeleteNotification";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_MEETING_UPDATE_NOTIFICATION
	{
		public String getTemplateDirectoryName()
		{
			return "meetingUpdateNotification";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_MEETING_CREATE_NOTIFICATION
	{
		public String getTemplateDirectoryName()
		{
			return "meetingCreateNotification";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	},
	BODY_SHARE_SPACE
	{
		public String getTemplateDirectoryName()
		{
			return "shareSpace";
		}

		public String getDefaultTemplateName()
		{
			return "default.ftl";
		}
	};

	public static final String KEY_ME = "me";
	public static final String KEY_PERSON = TemplateService.KEY_PERSON;
	public static final String KEY_DATE = TemplateService.KEY_DATE;
	public static final String KEY_IMAGE_RESOLVER = TemplateService.KEY_IMAGE_RESOLVER;
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

	public abstract String getTemplateDirectoryName();

	public abstract String getDefaultTemplateName();

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

}