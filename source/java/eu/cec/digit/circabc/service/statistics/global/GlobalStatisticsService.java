/**
 * 
 */
package eu.cec.digit.circabc.service.statistics.global;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.alfresco.service.PublicService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.NodeRef;

/**
 * @author beaurpi
 *
 */
@PublicService
public interface GlobalStatisticsService {
	
	/**
	 * prepare the space where reports will be stored
	 * dataDictionary/Circabc/statistics/reports/
	 * @throws Exception 
	 */
	public void prepareFolderRecipient();
	
	/**
	 * 
	 * @return list of data
	 */
	public Map<String, Object> makeGlobalStats(); 
	
	/**
	 * make and save stats into the designed folder
	 * @param destinationFolder
	 * @param lData
	 * @return noderef of the saved file
	 */
	public NodeRef saveStatsToExcel(NodeRef destinationFolder, Map<String, Object> lData);
	
	/**
	 * 
	 */
	public NodeRef getReportSaveFolder();
	
	/**
	 * Verify if report folder for statistics exists
	 * @return
	 */
	public Boolean isReportSaveFolderExisting();
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public Date getLastLoginDateOfUser(String username);
	
	/**
	 * 
	 * @return Map<String, String> String: File Title,  String, download link
	 */
	public List<FileInfo> getListOfReportFiles();
	
	
	/**
	 * look and zip files in report folder
	 */
	public void cleanAndZipPreviousReportFiles();
	
}
