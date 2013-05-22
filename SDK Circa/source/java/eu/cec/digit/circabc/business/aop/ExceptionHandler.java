/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.business.aop;

import org.aopalliance.intercept.MethodInvocation;

/**
 * An exception handler is used to manage any kind of error occurend in the business layer or below.
 *
 * @author Yanick Pignot
 */
public interface ExceptionHandler
{

	/**
	 * Get a user friendly message to be display to the target client.
	 *
	 * @param error					The relevant error
	 * @return						a I18N message key
	 */
	public String getMessageKey(final Throwable error);


	/**
	 * Get message key parameters needed for translation of the message key.
	 *
	 * @param error					The relevant error
	 * @return						a I18N message key parameters
	 */
	public Object[] getMessageParameters(final Throwable error);

	/**
	 * Do someting when the managed kind error is throwed. (ie: send an email, ...)
	 *
	 * @param methodInvocation		The aop method call
	 * @param error					The relevant error
	 */
	public void onThrows(final MethodInvocation methodInvocation, final Throwable error);


}
