/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.support;

import java.util.Map;

import javax.faces.component.UIInput;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.alfresco.web.bean.repository.Node;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.support.SupportService;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;


/**
 *	Bean that backs the "Manage support" WAI page.
 *
 * @author Stephane Clinckart
 */
public class ManageForSupportDialog extends BaseWaiDialog
{
	/** Logger */
	private static final Log logger = LogFactory.getLog(ManageForSupportDialog.class);
	
	private static final long serialVersionUID = 6521387670111634025L;
	/** Public JSF Bean name */
	public static final String BEAN_NAME = "ManageForSupportDialog";
	/** The constant for the 'keyword' parameter */
	public static final String KEYWORD_PARAMETER = "keyword";

	protected static final String NULL_VALUE = "null";

	private transient SupportService supportService;

	@Override
	public void init(final Map<String, String> parameters)
	{
		super.init(parameters);

	}
	
	public void generateError(final ActionEvent event)
    {
		int x = 5/0;
		//final UIInput input = (UIInput) event.getComponent().findComponent(COMPONENT_EDIT_PROFILE_VALUE);
        //final UISelectOne select = (UISelectOne) event.getComponent().findComponent(COMPONENT_EDIT_PROFILE_LANGUAGE);

        //final String selLang = (String) select.getValue();
        //final String selValue = ((String) input.getValue()).trim();
    }
	
	
	

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		// nothing to do
		return null;
	}

	@Override
	public void restored()
	{
		
	}

	@Override
	public String getCancelButtonLabel()
	{
		return translate("close");
	}

	public String getBrowserTitle()
	{
		return translate("manage_for_support_dialog_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("manage_for_support_dialog_icon_tooltip");
	}

	public Node getInterestGroup()
	{
		return getActionNode();
	}

   /**

	/**
	 * @return the supportService
	 */
	protected final SupportService getSupportService()
	{
		if(supportService == null)
		{
			supportService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getSupportService();
		}
		return supportService;
	}

	/**
	 * @param supportService the supportService to set
	 */
	public final void setSupportService(final SupportService supportService)
	{
		this.supportService = supportService;
	}
}
