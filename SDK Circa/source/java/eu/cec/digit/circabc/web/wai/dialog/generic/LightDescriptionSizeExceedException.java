/**
 * 
 */
package eu.cec.digit.circabc.web.wai.dialog.generic;

import org.alfresco.error.AlfrescoRuntimeException;

/**
 * @author beaurpi
 *
 */
public class LightDescriptionSizeExceedException extends AlfrescoRuntimeException {
	
	public final static Integer MAX_CHARACTER_LIMIT = 500;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3527942824713417764L;


	/**
	 * @param arg0
	 */
	public LightDescriptionSizeExceedException(String arg0) {
		super(arg0);
		
	}


}
