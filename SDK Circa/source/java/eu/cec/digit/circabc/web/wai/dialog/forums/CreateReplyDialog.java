/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.dialog.forums;

import java.text.MessageFormat;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.app.Application;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Bean implementation of the "Create Reply Dialog".
 *
 * @author Yanick Pignot
 */
public class CreateReplyDialog extends CreatePostDialog
{

	private static final String CITATION_OF = "newsgroups_topic_subject_citation_of";
	private static final String BLOCKQUOTE = "<table style=\"width:90%;padding:5px;\" border=\"0\" bgcolor=\"#C0D0F0\"><tbody><tr><td><p><strong>{0}</strong></p><blockquote>{1}</blockquote></td></tr></tbody></table><br /><br />";

	private static final long serialVersionUID = 4379003192922420796L;

	public static final String BEAN_NAME ="CircabcCreateReplyDialog";

	/** the nodeRef ofthe previous post we are replying */
	protected NodeRef previousNodeRef;

	private static final Log logger = LogFactory.getLog(CreateReplyDialog.class);

	private static final String DIALOG_NAME = "createReplyWai";


	//private static final Log logger = LogFactory.getLog(CreateTopicDialog.class);

	// ------------------------------------------------------------------------------
	// Wizard implementation

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception {
		try
		{
			outcome = super.finishImpl(context, outcome);

			if(outcome != null && getNodeService().hasAspect(this.postRef, ContentModel.ASPECT_REFERENCING) == false)
			{
				//setup the referencing aspect with the references association
			    // between the new post and the one being replied to
			    getNodeService().addAspect(this.postRef, ContentModel.ASPECT_REFERENCING, null);
			    getNodeService().createAssociation(this.postRef, getActionNode().getNodeRef(),  ContentModel.ASSOC_REFERENCES);

			    if (logger.isDebugEnabled())
			    {
			       logger.debug("created new node: " + this.postRef);
			       logger.debug("existing node: " + previousNodeRef);
			    }
			}
		    return outcome;
		}
		catch (Throwable e)
		{
			Utils.addErrorMessage(MessageFormat.format(Application
					.getMessage(context, Repository.ERROR_GENERIC), e
					.getMessage()), e);

			return null;
		}
	}

	@Override
	protected String getDialogName()
	{
		return DIALOG_NAME;
	}

	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null)
		{
			this.previousNodeRef = null;

			String id = parameters.get("id");
			previousNodeRef = new NodeRef(Repository.getStoreRef(), id);

			final Node node = new Node(previousNodeRef);
			final String creator = (String) getBrowseBean().resolverCreatorUserName.get(node);

			String replyContent = "";
			ContentReader reader = getContentService().getReader(previousNodeRef, ContentModel.PROP_CONTENT);
			if (reader != null)
			{
				replyContent = reader.getContentString();
			}

			setHtmlContent(
				MessageFormat.format(
						BLOCKQUOTE,
						translate(CITATION_OF, creator),
						replyContent
						)
			);
		}
	}

	/**
	 * Get the post we are replying
	 *
	 * @return The post we are replying
	 */
	public String getPrevious()
	{
		String replyContent = "";
		ContentReader reader = getContentService().getReader(previousNodeRef,
				ContentModel.PROP_CONTENT);
		if (reader != null) {
			replyContent = reader.getContentString();
		}
		// Unescaped alfresco work do by Utils.replaceLineBreaks
		replyContent = Utils.replace(replyContent, "<br/>", "\n");

		return replyContent;
	}

	public String getBrowserTitle()
	{
		return Application.getMessage(FacesContext.getCurrentInstance(), "create_reply_title_wai");
	}

	public String getPageIconAltText()
	{
		return Application.getMessage(FacesContext.getCurrentInstance(), "create_reply_icon_tooltip");
	}
}
