/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.business.api;

/**
 *
 * @author Yanick Pignot
 */
public class BusinessValidationError extends BusinessRuntimeExpection
{
	
	/** */
	private static final long serialVersionUID = 3241154549694518785L;

	/**
	 * @param message
	 */
	public BusinessValidationError(String messageKey)
	{
		super(messageKey);
	}

}
