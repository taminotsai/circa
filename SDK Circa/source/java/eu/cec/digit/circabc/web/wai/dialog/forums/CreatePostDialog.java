/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.dialog.forums;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.model.ForumModel;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.app.Application;
import org.alfresco.web.ui.common.Utils;

import eu.cec.digit.circabc.service.newsgroup.ModerationService;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.wai.dialog.content.edit.CreateContentBaseDialog;
import eu.cec.digit.circabc.web.wai.manager.ActionsListWrapper;


/**
 * Bean implementation of the "Create Post Dialog".
 *
 * @author Yanick Pignot
 */
public class CreatePostDialog extends CreateContentBaseDialog
{

	private static final long serialVersionUID = 8637487953006931563L;

	public static final String BEAN_NAME ="CircabcCreatePostDialog";

	private static final String MSG_WARN_MODERATION = "newsgroups_forum_create_post_moderation_warning";

	private static final String DIALOG_NAME = "createPostWai";

	private static final String MSG_SAVED = "create_post_successfullysaved";

	private transient ModerationService moderationService;
	// ------------------------------------------------------------------------------
	// Dialog implementation

	protected NodeRef postRef;


	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(getActionNode() == null)
		{
			throw new IllegalArgumentException("The node id parameter is mandatory");
		}

		logRecord.setService(super.getServiceFromActionNode());
		logRecord.setActivity("Create post");


		if(parameters != null)
		{
			this.postRef = null;
		}
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		String content = getSelectedContent();
		if (content != null && content.length() > 0)
		{
			if(MODE_TEXT.equals(getEditMode()))
		    {
		    	  // remove link breaks and replace with <br>
		    	  content = Utils.replaceLineBreaks(content, false);
		    }

			if(isAlreadySaved() == false)
			{
				// we are in creation mode
				final CreateTopicDialog createTopicDialog = (CreateTopicDialog) Beans.getBean(CreateTopicDialog.BEAN_NAME);
				final NodeRef topic;
				if(ForumModel.TYPE_TOPIC.equals(getActionNode().getType()))
				{
					topic = getActionNode().getNodeRef();
				}
				else
				{
					topic = getNodeService().getPrimaryParent(getActionNode().getNodeRef()).getParentRef();
				}

				this.postRef = createTopicDialog.createPost(context, topic, content);

				if(getModerationService().isContainerModerated(topic))
				{
					Utils.addStatusMessage(FacesMessage.SEVERITY_WARN, translate(MSG_WARN_MODERATION));
				}
				if(reopen())
				{
					Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_SAVED));
				}
			}
			else
			{
				 final ContentWriter writer = getContentService().getWriter(this.postRef, ContentModel.PROP_CONTENT, true);
			     if (writer != null)
			     {
			    	 writer.putContent(content);
			     }
			}
			
			attachFiles(this.postRef);

		    return outcome;
		}
		else
		{
			Utils.addErrorMessage(translate(CreateTopicDialog.MSG_EMPTY_MESSAGE));
			return null;
		}
	}

	/**
	 * @return
	 */
	public boolean isAlreadySaved()
	{
		return this.postRef != null;
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
	public String getFinishButtonLabel()
	{
		return translate("create_post_label");
	}

	public String getBrowserTitle()
	{
		return Application.getMessage(FacesContext.getCurrentInstance(), "create_post_title_wai");
	}

	public String getPageIconAltText()
	{
		return Application.getMessage(FacesContext.getCurrentInstance(), "create_post_icon_tooltip");
	}

	public ActionsListWrapper getActionList()
	{
		return null;
	}

	public boolean isCancelButtonVisible()
	{
		return true;
	}

	@Override
	public boolean getFinishButtonDisabled()
	{
		return false;
	}

	public boolean isFormProvided()
	{
		return false;
	}

	@Override
	public String getDefaultMode()
	{
		return MODE_HTML;
	}

	@Override
	protected NodeRef getEditablePost()
	{
		return this.postRef;
	}

	public final ModerationService getModerationService()
	{
		if(moderationService == null)
		{
			moderationService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getModerationService();
		}
		return moderationService;
	}

	public final void setModerationService(ModerationService moderationService)
	{
		this.moderationService = moderationService;
	}


}
