/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.app.context;

/**
 * Beans supporting the ICircabcContextListener interface are registered against this class. Then Beans
 * which wish to indicate that the UI should refresh itself i.e. dump all cached data and settings,
 * call the UICircabcContextService.igRootChanged() to inform all registered instances of the change.
 * <p>
 * Registered beans will also be informed of changes in location, for example when the current
 * ig root, circabc... changes or when the user has changed area i.e. from company home to my home.
 *
 * @author yanick pignot
 */
public interface ICircabcContextListener
{

	/**
    * Method called by UICircabcContextService.circabcLeaved() to inform all registered beans that
    * the user don't longer navigate into circabc
    */
	public void circabcLeaved();

	/**
     * Method called by UICircabcContextService.circabcEntered() to inform all registered beans that
     * the user was not in circabc and now it enters into.
     */
	public void circabcEntered();

	/**
     * Method called by UICircabcContextService.categoryHeaderChanged() to inform all registered beans that
     * the user change of Category Header
     */
	public void categoryHeaderChanged();

	/**
     * Method called by UICircabcContextService.categoryChanged() to inform all registered beans that
     * the user change of Category
     */
	public void categoryChanged();

	/**
     * Method called by UICircabcContextService.igRootChanged() to inform all registered beans that
     * the user change of IG root
     */
	public void igRootChanged();

	/**
     * Method called by UICircabcContextService.igServiceChanged() to inform all registered beans that
     * the user change of IG Service
     */
	public void igServiceChanged();

}
