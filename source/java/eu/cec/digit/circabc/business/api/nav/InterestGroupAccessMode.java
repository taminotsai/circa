/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.api.nav;

/**
 * Enumeration of all interest group access mode (or visibility mode)
 *
 * @author Yanick Pignot
 */
public enum InterestGroupAccessMode
{
	/**
	 * Gathers the Interest Groups which are accessible to everybody.
	 *
	 * <p><b>Visible for</b></p>
	 * <ul>
	 * 		<li>Guest</li>
	 * 		<li>Not invited but authenticated user</li>
	 * 		<li>Invited user</li>
	 * </ul>
	 * <p><b>Not visible for</b></p>
	 * <ul>
	 * 		<li>Nobody</li>
	 * </ul>
	 *
	 **/
	PUBLIC,

	/**
	 * Gathers the Interest Groups accessible for any logged-in user.
	 *
	 * <p><b>Visible for</b></p>
	 * <ul>
	 * 		<li>Not invited but authenticated user</li>
	 * 		<li>Invited user</li>
	 * </ul>
	 * <p><b>Not visible for</b></p>
	 * <ul>
	 * 		<li>Guest</li>
	 * </ul>
	 **/
	REGISTRED,

	/**
	 * Gathers the Interest Groups accessible for any explicitly invited user.
	 *
	 * <p><b>Visible for</b></p>
	 * <ul>
	 * 		<li>Invited user</li>
	 * </ul>
	 * <p><b>Not visible for</b></p>
	 * <ul>
	 * 		<li>Guest</li>
	 * 		<li>Not invited but authenticated user</li>
	 * </ul>
	 **/
	MEMBERS
}
