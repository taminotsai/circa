/**
 * 
 */
package eu.cec.digit.circabc.service.report;

import java.sql.SQLException;

/**
 * @author beaurpi
 *
 */
public interface ReportDaoService {
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Integer queryDbForNumberOfDocuments() throws SQLException;

}
