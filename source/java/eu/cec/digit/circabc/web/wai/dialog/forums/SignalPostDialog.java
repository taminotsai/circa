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
 * Base bean that back the signal an abuse dialog
 *
 * @author Yanick Pignot
 */
public class SignalPostDialog extends ModerationDialog
{
	/** */
	private static final long serialVersionUID = -1607630889160963579L;

	private static final String MSG_SIGNALED_SUCCESS = "post_moderation_signal_success";
	private static final String MSG_SIGNALED_FAILURE = "post_moderation_signal_failure";

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
			signalAbuse(message);
			Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(MSG_SIGNALED_SUCCESS));

			if(message != null)
			{
				logRecord.setInfo(message);
			}

			return outcome;
		}
		catch(final Exception e)
		{
			Utils.addErrorMessage(translate(MSG_SIGNALED_FAILURE, e.getMessage()), e);

			isFinished = false;
			return null;
		}
	}

	public String getBrowserTitle()
	{
		return translate("moderation_signal_abuse_dialog_page_title");
	}

	public String getPageIconAltText()
	{
		return translate("moderation_signal_abuse_dialog_icon_tooltip");
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
