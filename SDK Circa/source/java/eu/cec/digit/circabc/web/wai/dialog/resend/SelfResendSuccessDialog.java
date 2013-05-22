/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.resend;

import javax.faces.context.FacesContext;

import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * @author yanick pignot
 */
public class SelfResendSuccessDialog extends BaseWaiDialog
{

	private static final long serialVersionUID = -685695045385990641L;

	public static String BEAN_NAME = "SelfResendSuccessDialog";

	public SelfResendSuccessDialog()
	{
		super();
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		return CircabcNavigationHandler.CLOSE_WAI_DIALOG_OUTCOME;
	}

	public String getBrowserTitle()
	{
		return translate("resend_congratulation_page_title");
	}

	public String getPageIconAltText()
	{
		return translate("resend_congratulation_page_title");
	}

	@Override
	public boolean isCancelButtonVisible()
	{
		return false;
	}

	@Override
	public String getFinishButtonLabel()
	{
		return translate("ok");
	}

}
