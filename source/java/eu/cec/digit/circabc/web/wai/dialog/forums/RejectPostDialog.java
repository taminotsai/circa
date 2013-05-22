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

import org.alfresco.web.ui.common.Utils;

/**
 * Base bean that back the reject of a moderated post
 *
 * @author Yanick Pignot
 */
public class RejectPostDialog extends ModerationDialog
{
	/** */
	private static final long serialVersionUID = -160763188876964829L;

	private static final String MSG_REJECTED_SUCCESS = "post_moderation_reject_success";
	private static final String MSG_REJECTED_FAILURE = "post_moderation_reject_failure";

	String message;

	@Override
	public void init(final Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null)
		{
			message = null;
		}
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Throwable
	{
		try
		{
			reject(message);
			Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_REJECTED_SUCCESS));

			if(message != null)
			{
				logRecord.setInfo(message);
			}

			return outcome;
		}
		catch(final Exception e)
		{
			Utils.addErrorMessage(translate(MSG_REJECTED_FAILURE, e.getMessage()), e);
			isFinished = false;

			return null;
		}
	}

	public String getBrowserTitle()
	{

		return translate("moderation_reject_post_dialog_page_title");
	}

	public String getPageIconAltText()
	{
		return translate("moderation_reject_post_dialog_icon_tooltip");
	}

	public final String getMessage()
	{
		return message;
	}

	public final void setMessage(String message)
	{
		this.message = message;
	}


}
