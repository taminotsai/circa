/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.customisation.mail;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.TemplateService;
import org.springframework.extensions.surf.util.I18NUtil;

import eu.cec.digit.circabc.service.customisation.mail.MailTemplate;
import eu.cec.digit.circabc.service.customisation.mail.MailWrapper;

/**
 * The mail wrapper implementation
 *
 * @author yanick pignot
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * I18NUtil was moved to Spring.
 * This class seems to be developed for CircaBC
 */
public class MailWrapperImpl implements MailWrapper
{
	private static final String TEMPLATE_FREEMARKER = "freemarker";

	private static final Locale NO_LOCALE = new Locale("__NO__LOCALE");

	final private MailTemplate mailTemplate;
	final private String name;
	final private Map<Locale, NodeRef> templateTranslations;
	final private TemplateService templateService;
	final private NodeService nodeService;
	final private Locale pivotLocale;

	/*package*/ MailWrapperImpl(final Map<Locale, NodeRef> templateTranslations, final MailTemplate template, final TemplateService templateService, final NodeService nodeService, final Locale pivotLocale)
	{
		// build non multilingual template
		this.templateTranslations = templateTranslations;
		this.mailTemplate = template;
		this.templateService = templateService;
		this.pivotLocale = pivotLocale;
		this.nodeService = nodeService;
		this.name = (String) nodeService.getProperty(getSafeTemplate(pivotLocale), ContentModel.PROP_NAME);
	}

	/*package*/ MailWrapperImpl(final NodeRef templateRef, final MailTemplate template, final TemplateService templateService, final NodeService nodeService)
	{
		// build a non multilingual template
		this(Collections.singletonMap(NO_LOCALE, templateRef), template, templateService, nodeService, NO_LOCALE);
	}

	public String getName()
	{
		return this.name;
	}

	public final String getSubject(final Map<String, Object> model)
	{
		return getSubject(model, null);
	}

	public final String getSubject(final Map<String, Object> model, final Locale locale)
	{
		final Locale currentLocale = I18NUtil.getLocale();
		try
		{
			if(locale != null)
			{
				I18NUtil.setLocale(locale);
			}

			final Serializable subjectProp =  nodeService.getProperty(getSafeTemplate(locale), ContentModel.PROP_TITLE);

			final String subject;
			if(subjectProp != null && subjectProp.toString().length() > 0)
			{
				subject = (String) subjectProp;
			}
			else
			{
				subject = this.mailTemplate.getDefaultSubject();
			}

			return templateService.processTemplateString(TEMPLATE_FREEMARKER, subject, safeModel(model));
		}
		finally
		{
			I18NUtil.setLocale(currentLocale);
		}
	}

	public final String getBody(final Map<String, Object> model)
	{
		return getBody(model, null);
	}

	public String getBody(final Map<String, Object> model, final Locale locale)
	{
		final Locale currentLocale = I18NUtil.getLocale();
		try
		{
			if(locale != null)
			{
				I18NUtil.setLocale(locale);
			}

			final String translationId = getSafeTemplate(locale).toString();
			return templateService.processTemplate(TEMPLATE_FREEMARKER, translationId, safeModel(model));
		}
		finally
		{
			I18NUtil.setLocale(currentLocale);
		}
	}

	public MailTemplate getMailTemplate()
	{
		return this.mailTemplate;
	}

	@Override
	public String toString()
	{
		return "Template " + getName() + " (" + getMailTemplate() + ")" ;
	}

	private final Map<String, Object> safeModel(final Map<String, Object> model)
	{
		if(model == null)
		{
			return new HashMap<String, Object>();
		}
		else
		{
			return model;
		}
	}


	public NodeRef getTemplateNodeRef()
	{
		return getSafeTemplate(null);
	}

	private NodeRef getSafeTemplate(final Locale locale)
	{
		if(locale == null || templateTranslations.containsKey(locale) == false)
		{
			return this.templateTranslations.get(this.pivotLocale);
		}
		else
		{
			return this.templateTranslations.get(locale);
		}


	}

	public boolean isOriginalTemplate()
	{
		return getName().equals(mailTemplate.getDefaultTemplateName());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((mailTemplate == null) ? 0 : mailTemplate.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + ((pivotLocale == null) ? 0 : pivotLocale.hashCode());
		result = PRIME * result + ((templateTranslations == null) ? 0 : templateTranslations.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MailWrapperImpl other = (MailWrapperImpl) obj;
		if (mailTemplate == null)
		{
			if (other.mailTemplate != null)
				return false;
		} else if (!mailTemplate.equals(other.mailTemplate))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pivotLocale == null)
		{
			if (other.pivotLocale != null)
				return false;
		} else if (!pivotLocale.equals(other.pivotLocale))
			return false;
		if (templateTranslations == null)
		{
			if (other.templateTranslations != null)
				return false;
		} else if (!templateTranslations.equals(other.templateTranslations))
			return false;
		return true;
	}

}
