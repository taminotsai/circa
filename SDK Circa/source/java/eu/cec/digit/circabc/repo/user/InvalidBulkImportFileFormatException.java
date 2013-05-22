/**
 * 
 */
package eu.cec.digit.circabc.repo.user;

/**
 * @author beaurpi
 *
 */
public class InvalidBulkImportFileFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8673796840049092712L;

	/**
	 * 
	 */
	public InvalidBulkImportFileFormatException() {

		super("The Bulk User Import file does not match good format. Please verify column labels.");
		
	}


}
