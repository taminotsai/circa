/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.dialog.forums;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.cmr.version.Version;
import org.alfresco.service.cmr.version.VersionHistory;
import org.alfresco.service.cmr.version.VersionService;
import org.alfresco.web.ui.common.Utils;

import eu.cec.digit.circabc.model.ModerationModel;
import eu.cec.digit.circabc.service.newsgroup.ModerationService;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.WebClientHelper;
import eu.cec.digit.circabc.web.wai.dialog.content.edit.CreateContentBaseDialog;

/**
 * Bean implementation of the "Edit Post Dialog".
 *
 * @author Yanick Pignot
 */
public class EditPostDialog extends CreateContentBaseDialog
{
	private static final long serialVersionUID = 333000053006931563L;

	public static final String BEAN_NAME ="CircabcEditPostDialog";

	private static final String DIALOG_NAME = "editPostWai";

	private static final String MSG_VERSIONS = "edit_post_dialog_version";

	private transient ModerationService moderationService;
	private transient PersonService personService;
	private transient VersionService versionService;

	private List<SelectItem> olderVersions = null;
	private SelectItem selectedVersion = null;

	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);
		logRecord.setService("Newsgroup");
		logRecord.setActivity("Edit post");

		if(parameters != null)
		{
			olderVersions = null;
			selectedVersion = null;
		}

		if(getActionNode() == null)
		{
			throw new IllegalArgumentException("The node id is a mandatory parameter.");
		}
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		  String content = getSelectedContent();
		  if (content.length() < 1)
		  {
			  //Message too small -> error
			  Utils.addStatusMessage(FacesMessage.SEVERITY_ERROR,translate("create_post_error_content_mandatory"));
			  return null;
		  }

		  if(MODE_TEXT.equals(getEditMode()))
	      {
	    	  // remove link breaks and replace with <br>
	    	  content = Utils.replaceLineBreaks(content, false);
	      }


	      // check that the name of this post does not contain the :
	      // character (used in previous versions), if it does rename the post.
	      final String name = (String)getActionNode().getProperties().get(ContentModel.PROP_NAME.toString());
	      if (name.indexOf(':') != -1)
	      {
	    	  final String newName = name.replace(':', '-');
	    	  this.getFileFolderService().rename(getActionNode().getNodeRef(), newName);
	      }

	      final ContentWriter writer = this.getContentService().getWriter(getActionNode().getNodeRef(), ContentModel.PROP_CONTENT, true);
	      if (writer != null)
	      {
	         writer.putContent(content);
	      }

	      attachFiles(getActionNode().getNodeRef());
	      
	      return outcome;
	}

	@Override
	public final String getHtmlContent()
	{
		if(selectedVersion == null)
		{
			return super.getHtmlContent();
		}
		else
		{
			return selectedVersion.getDescription();
		}
	}

	@Override
	public final String getTextContent()
	{
		if(selectedVersion == null)
		{
			return super.getTextContent();
		}
		else
		{
			return selectedVersion.getDescription();
		}
	}


	public boolean isRejected()
	{
		return getModerationService().isRejected(getActionNode().getNodeRef());
	}

	public boolean isRejectReasonAvailable()
	{
		final String reason = getRejectMessage();
		return reason != null && reason.trim().length() > 0;
	}

	public String getRejectMessage()
	{
		final String message = (String) getActionNode().getProperties().get(ModerationModel.PROP_REJECT_MESSAGE.toString());

		return message == null ? null : message.replace("\n", "<br />");
	}

	public String getRejectModerator()
	{
		final String moderator = (String) getActionNode().getProperties().get(ModerationModel.PROP_REJECT_BY.toString());

		if(getPersonService().personExists(moderator))
		{
			return getUserService().getUserFullName(moderator);
		}
		else
		{
			return moderator;
		}
	}

	public String getRejectDate()
	{
		final Date date = (Date) getActionNode().getProperties().get(ModerationModel.PROP_REJECT_ON.toString());

		return safeDate(date);
	}

	/**
	 * @param date
	 * @return
	 */
	private String safeDate(final Date date)
	{
		if(date == null)
		{
			return "N/A";
		}
		else
		{
			final FacesContext fc = FacesContext.getCurrentInstance();
			return WebClientHelper.formatLocalizedDate(date, fc, true, true);
		}
	}

	public List<SelectItem> getOlderVersions()
	{
		if(olderVersions == null)
		{
			final NodeRef postRef = getActionNode().getNodeRef();
			final VersionHistory history = getVersionService().getVersionHistory(postRef);
			final Collection<Version> allVersions = history.getAllVersions();
			olderVersions = new ArrayList<SelectItem>(allVersions.size());

			for(final Version version: allVersions)
			{
				final String label = version.getVersionLabel();
				final Date date = version.getFrozenModifiedDate();
				final NodeRef frozenRef = version.getFrozenStateNodeRef();
				final String content = computeContent(getContent(frozenRef));

				if(content != null)
				{
					olderVersions.add(new SelectItem(
							label,
							translate(MSG_VERSIONS, label, safeDate(date)),
							content
							));
				}
			}
		}

		return olderVersions;
	}


	public String getBrowserTitle()
	{
		return translate("edit_post_dialog_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("edit_post_dialog_icon_tooltip");
	}

	@Override
	public String getDefaultMode()
	{
		return MODE_HTML;
	}

	@Override
	protected String getDialogName()
	{
		return DIALOG_NAME;
	}

	@Override
	public boolean isAttachementAllowed()
    {
        return true;
    }
	
	@Override
	protected NodeRef getEditablePost()
	{
		return getActionNode().getNodeRef();
	}

	@Override
	protected String doPostCommitProcessing(FacesContext context, String outcome)
	{
		olderVersions = null;
		selectedVersion = null;
		return super.doPostCommitProcessing(context, outcome);
	}

	/**
	 * @return the moderationService
	 */
	protected final ModerationService getModerationService()
	{
		if(moderationService == null)
		{
			moderationService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getModerationService();
		}
		return moderationService;
	}

	/**
	 * @param moderationService the moderationService to set
	 */
	public final void setModerationService(ModerationService moderationService)
	{
		this.moderationService = moderationService;
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

	/**
	 * @return the versionService
	 */
	protected final VersionService getVersionService()
	{
		if(versionService == null)
		{
			versionService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getVersionService();
		}
		return versionService;
	}

	/**
	 * @param versionService the versionService to set
	 */
	public final void setVersionService(VersionService versionService)
	{
		this.versionService = versionService;
	}

	/**
	 * @return the selectedVersion
	 */
	public final String getSelectedVersion()
	{
		return selectedVersion == null ? "" : (String) selectedVersion.getValue();
	}

	/**
	 * @param selectedVersion the selectedVersion to set
	 */
	public final void setSelectedVersion(String selectedVersion)
	{
		if(selectedVersion != null && selectedVersion.length() > 0)
		{
			for(final SelectItem item: getOlderVersions())
			{
				if(selectedVersion.equals(item.getValue()))
				{
					this.selectedVersion = item;
					break;
				}
			}
		}

	}
}
