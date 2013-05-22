/**
 * 
 */
package eu.cec.digit.circabc.repo.report;

import java.sql.SQLException;

import org.alfresco.repo.domain.activities.ibatis.IBatisSqlMapper;

import eu.cec.digit.circabc.service.report.ReportDaoService;

/**
 * @author beaurpi
 *
 */
public class ReportDaoServiceImpl extends IBatisSqlMapper implements ReportDaoService {

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.service.report.ReportDaoService#queryDbForNumberOfDocuments()
	 */
	public Integer queryDbForNumberOfDocuments() throws SQLException {
		
		Integer idContent = (Integer) getSqlMapClient().queryForObject("CircabcReporting.select_qname_content");
		Integer idCmObject = (Integer) getSqlMapClient().queryForObject("CircabcReporting.select_qname_cmobject");
		Integer idVersionHistory = (Integer) getSqlMapClient().queryForObject("CircabcReporting.select_qname_version_history");
		
		ContentNumberParametersDAO parameter = new ContentNumberParametersDAO(idContent, idCmObject, idVersionHistory);
		
		return  (Integer) getSqlMapClient().queryForObject("CircabcReporting.select_count_documents", parameter );
	}

}
