/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.api.nav;

/**
 * Enumeration of all interest group services
 *
 * @author Yanick Pignot
 */
public enum InterestGroupServices
{
	/**
	 * This kind of WEB space helps users present information about their interest group in HTML format.
	 **/
	INFORMATION,

	/**
	 * In the Library service, users manage and share their documents.
	 **/
	LIBRARY,

	/**
	 * Directory service gives users the opportunity of view and interact with other circvabc users.
	 **/
	DIRECTORY,

	/**
	 * The Newsgroups service allows the members of the Interest Group to create forums and hold discussions with one another.
	 **/
	NEWSGROUP,

	/**
	 * Event service gives users the opportunity of scheduling, publicising and managing events.
	 **/
	EVENTS,

	/**
	 * With <b>optional</b> survey service, users can build and follow up surveys through the integration of CIRCABC with IPM (Interactive Policy-Making).
	 **/
	SURVEY
}
