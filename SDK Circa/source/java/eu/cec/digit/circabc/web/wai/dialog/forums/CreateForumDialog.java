/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.dialog.forums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.model.ApplicationModel;
import org.alfresco.model.ContentModel;
import org.alfresco.model.ForumModel;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.app.Application;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.newsgroup.ModerationService;
import eu.cec.digit.circabc.service.struct.ManagementService;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.WebClientHelper;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;


/**
 * Bean implementation of the "Create Forum Dialog".
 *
 * @author yanick Pignot
 */
public class CreateForumDialog extends BaseWaiDialog
{
	private static final String MSG_EMPTY_NAME = "newsgroups_forum_create_forum_name_mandatory";

	private static final long serialVersionUID = 8637487953006931563L;

	public static final String BEAN_NAME ="CircabcCreateForumDialog";

	private transient ModerationService moderationService;

	private String name;
	private String description;
	private boolean moderated;

	private static final Log logger = LogFactory.getLog(CreateForumDialog.class);

	// ------------------------------------------------------------------------------
	// Wizard implementation

	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);
		logRecord.setService("Newsgroup");
		logRecord.setActivity("Create forum");

		if(getActionNode() == null)
		{
			throw new IllegalArgumentException("The node id parameter is mandatory");
		}

		if(parameters != null)
		{
			this.name = null;
			this.description  = null;
			this.moderated = getModerationService().isContainerModerated(getActionNode().getNodeRef());
		}
	}

	@Override
	protected String finishImpl(final FacesContext context, final String outcome) throws Exception
	{
		if(name == null || name.trim().length() < 1)
		{
			Utils.addErrorMessage(translate(MSG_EMPTY_NAME));
			this.isFinished = false;
			return null;
		}

		final String cleanName = WebClientHelper.toValidFileName(this.name);
		final String title = this.name;

		final FileInfo fileInfo = getFileFolderService().create(
              getActionNode().getNodeRef(), cleanName, ForumModel.TYPE_FORUM);

		final NodeRef nodeRef = fileInfo.getNodeRef();

		if (logger.isDebugEnabled())
        {
			logger.debug("Created forum node with name: " + cleanName);
        }

        // apply the uifacets aspect - icon, title and description props
        final Map<QName, Serializable> uiFacetsProps = new HashMap<QName, Serializable>(5);
        uiFacetsProps.put(ApplicationModel.PROP_ICON, ManagementService.DEFAULT_FORUM_ICON_NAME);
        uiFacetsProps.put(ContentModel.PROP_TITLE, title);
        uiFacetsProps.put(ContentModel.PROP_DESCRIPTION, this.description);
        this.getNodeService().addAspect(nodeRef, ApplicationModel.ASPECT_UIFACETS, uiFacetsProps);

        if (logger.isDebugEnabled())
        {
        	logger.debug("Added uifacets aspect with properties: " + uiFacetsProps);
        }

        if(moderated)
		{
			getModerationService().applyModeration(nodeRef, false);

			if (logger.isDebugEnabled())
	        {
	        	logger.debug("Moderation activated on forum and furtu topics.");
	        }
		}

		return outcome;
	}

	@Override
	public String getFinishButtonLabel()
	{
		return Application.getMessage(FacesContext.getCurrentInstance(), "newsgroups_forum_create_forum");
	}

	public String getBrowserTitle()
	{
		return Application.getMessage(FacesContext.getCurrentInstance(), "newsgroups_forum_create_forum_browser_title");
	}

	public String getPageIconAltText()
	{
		return Application.getMessage(FacesContext.getCurrentInstance(), "newsgroups_forum_create_forum_icon_tooltip");
	}

	public final boolean isModerated()
	{
		return moderated;
	}

	public final void setModerated(boolean moderated)
	{
		this.moderated = moderated;
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

	public final String getDescription()
	{
		return description;
	}

	public final void setDescription(String description)
	{
		this.description = description;
	}

	public final String getName()
	{
		return name;
	}

	public final void setName(String name)
	{
		this.name = name;
	}
}
