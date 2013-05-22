/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.customisation.logo;

import org.alfresco.service.cmr.repository.NodeRef;


/**
 * The base logo configuration wrapper
 *
 * @author yanick pignot
 */
public interface DefaultLogoConfiguration
{

	/**
	 * @return the the default logo reference.
	 */
	public LogoDefinition getLogo();

	/**
	 * @return if the logo must be displayed on the main page (IG-HOME)
	 */
	public boolean isLogoDisplayedOnMainPage();

	/**
	 * @return	the Logo Width for the main page or a negative value if not set
	 */
	public int getMainPageLogoWidth();

	/**
	 * @return	the Logo Height for the main page or a negative value if not set
	 */
	public int getMainPageLogoHeight();

	/**
	 * @return	if the logo size must to be forced or not
	 */
	public boolean isMainPageSizeForced();

	/**
	 * @return	if the logo is display at the left (default) or at the right of the screen
	 */
	public boolean isMainPageLogoAtLeft();

	/**
	 * @return	if the logo must be displayed on all other navigation pages
	 */
	public boolean isLogoDisplayedOnAllPages();

	/**
	 * @return	the Logo Width for all other pages or a negative value if not set
	 */
	public int getOtherPagesLogoWidth();

	/**
	 * @return	the Logo Height for all other pages or a negative value if not set
	 */
	public int getOtherPagesLogoHeight();

	/**
	 * @return	if the logo size must to be forced or not on all other pages
	 */
	public boolean isOtherPagesSizeForced();

	/**
	 * @return the node reference on which the configuration is setted.
	 */
	public NodeRef getConfiguredOn();



}
