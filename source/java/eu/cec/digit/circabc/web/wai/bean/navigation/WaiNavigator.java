/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.bean.navigation;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.web.app.context.IContextListener;

import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;

/**
 * Base interface of each bean that want to back a navigation part of the Circabc WAI webclient
 *
 * @author yanick pignot
 */
public interface WaiNavigator extends IContextListener, Serializable
{

	/**
	 * Initialises the WaiNavigator bean
	 *
	 * @param parameters Map of parameters for the dialog
	 */
	public void init(Map<String, String> parameters);

	/**
	 * Initialises the parameters bean to apply
	 *
	 * @param parameters Map of parameters
	 */
	public void setParamsToApply(Map<String, String> parameters);

	/**
	 * Initialises the WaiNavigator bean especially for a circa displayer
	 *
	 * @see eu.cec.digit.circabc.web.ui.component.UIDisplayer
	 * @return whether the content of the displayer must be displayed or not
	 */
	public boolean getInit();

	/**
	 * @return the unique node type managed by the implementation of the bean
	 */
	public NavigableNodeType getManagedNodeType();

	/**
	 * @return the unique jsp that must be backed by the implementation of the bean
	 */
	public String getRelatedJsp();

	/**
	 * @return the translated I18N message for the the title of the page
	 */
	public String getPageTitle();

	/**
	 * @return the translated I18N message for the the description of the page
	 */
	public String getPageDescription();

	/**
	 * @return the icon near the title of the page if required or null
	 */
	public String getPageIcon();


	/**
	 * @return the alt text attribute of the icon
	 */
	public String getPageIconAltText();

	/**
	 * @return the main title that should be displayed in the top of the browser.
	 */
	public String getBrowserTitle();

	/**
	 * @return the number of element that contains the lists
	 */
	public int getListPageSize();

	/**
	 * Called when the dialog is restored after a nested dialog is closed
	 */
	public void restored();


	/**
	 * @return the Http url of the current node. Null if not applicable.
	 */
	public String getHttpUrl();

	/**
	 * @return the Http url of the current node. Null if not applicable.
	 */
	public String getWebdavUrl();

	/**
	 * @return the Http url of the previous node. Null if not applicable.
	 */
	public String getPreviousHttpUrl();

}
