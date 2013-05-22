/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.wizard;

import org.alfresco.web.bean.wizard.IWizardBean;

import eu.cec.digit.circabc.web.wai.dialog.WaiDialog;


/**
 * Base interface of each bean that want to back a wizard of the Circabc WAI webclient
 *
 * @author yanick pignot
 */
public interface WaiWizard extends IWizardBean, WaiDialog
{

}
