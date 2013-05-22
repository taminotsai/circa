/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.profile;

/**
 * Baked bean for access profiles creation
 *
 * @author Yanick Pignot
 */
public class CreateAccessProfileDialog extends EditAccessProfileDialog
{
    private static final long serialVersionUID = -457333397844453605L;

    private static final String MSG_CONTAINER_TITLE = "create_access_profile_dialog_page_title";
    
    //private static final Log logger = LogFactory.getLog(CreateAccessProfileDialog.class);

    @Override
    public String getBrowserTitle()
    {
        return translate("create_access_profile_dialog_browser_title");
    }
    
    @Override
    public String getPageIconAltText()
    {
        return translate("create_access_profile_dialog_icon_tooltip");
    }
    
    @Override
    public String getContainerTitle()
	{
	      return translate(MSG_CONTAINER_TITLE);
	}
}
