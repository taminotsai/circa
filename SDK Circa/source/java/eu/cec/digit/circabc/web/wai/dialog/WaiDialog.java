/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog;

import java.io.Serializable;

import org.alfresco.web.bean.dialog.IDialogBean;

import eu.cec.digit.circabc.web.wai.manager.ActionsListWrapper;


/**
 * Base interface of each bean that want to back a dialog of the Circabc WAI webclient
 *
 * @author yanick pignot
 */
public interface WaiDialog extends IDialogBean, Serializable
{

	/**
	 * @return the alt text attribute of the icon
	 */
	public String getPageIconAltText();

	/**
	 * @return the main title that should be displayed in the top of the browser.
	 */
	public String getBrowserTitle();

	/**
	 * @return an object that wrap an action list for the right menu
	 */
	public ActionsListWrapper getActionList();


	/**
	 * @return true if the cancel button must be displayed
	 */
	public boolean isCancelButtonVisible();


	public boolean isFormProvided();

}
