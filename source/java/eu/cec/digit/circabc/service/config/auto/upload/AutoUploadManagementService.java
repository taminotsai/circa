/**
 * 
 */
package eu.cec.digit.circabc.service.config.auto.upload;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.rule.Rule;

import eu.cec.digit.circabc.repo.config.auto.upload.Configuration;

/**
 * @author beaurpi
 *
 */
public interface AutoUploadManagementService {

	/***
	 * Insert one configuration in the audit database of CIRCABC
	 * @param config
	 * @throws SQLException
	 */
	public void registerConfiguration(Configuration config) throws SQLException;
	
	/***
	 * Get all configurations for one Interest Group
	 * @param igName
	 * @return
	 * @throws SQLException
	 */
	public List<Configuration> listConfigurations(String igName) throws SQLException;
	
	/***
	 * Remove configuration from Database
	 * @param config
	 * @throws SQLException
	 */
	public void deleteConfiguration(Configuration config) throws SQLException;
	
	/***
	 * Update configuration inside audit database
	 * @param config
	 * @throws SQLException
	 */
	public void updateConfiguration(Configuration config) throws SQLException;

	/***
	 * Get one configuration by ID.
	 * @param idConfig
	 * @return
	 * @throws SQLException
	 */
	public Configuration getConfigurationById(Integer idConfig) throws SQLException;
	
	/***
	 * 
	 * @param nodeRef
	 * @return
	 * @throws SQLException
	 */
	public Configuration getConfigurationByNodeRef(NodeRef nodeRef) throws SQLException;
	
	/***
	 * Configure auto extract rule & action for given sapce noderef
	 * @param spaceRef
	 * @return
	 */
	public Rule buildDefaultExtractRule(NodeRef spaceRef);
	
	/***
	 * save & build auto extract rule & action for the given space noderef
	 * @param spaceRef
	 */
	public void addAutoExtractRuleToSpace(NodeRef spaceRef);

	/***
	 * remove the auto extract rule applied to the space 
	 * @param spaceRef
	 */
	public void removeAutoExtractRule(NodeRef spaceRef);

	/***
	 * get all configurations
	 * @return
	 * @throws SQLException 
	 */
	public List<Configuration> listAllConfigurations() throws SQLException;

	/***
	 * call this method to update content of node when new ftp file has been detected
	 * @param fileRef
	 * @param file
	 */
	public void updateContent(NodeRef fileRef, File file);


	/***
	 * log result of auto upload
	 * @param conf
	 * @param isOk
	 */
	public void logJobResult(Configuration conf, AutoUploadJobResult result);

	/***
	 * send email for job if configured
	 * @param conf
	 * @param b
	 * @throws Exception 
	 */
	public void sendJobNofitication(Configuration conf, AutoUploadJobResult result);

	/***
	 * use circabc importer executor to extract zip
	 * @param fileRef
	 */
	public void extractZip(NodeRef fileRef);

	/***
	 * 
	 * @param fileRef
	 * @return
	 */
	public boolean documentExists(NodeRef fileRef);

	/***
	 * create content for configuration without fileNoderef -> it will update with filenodeRef just after
	 * @param fileRef
	 * @param tmpFile
	 * @return
	 */
	public NodeRef createContent(NodeRef fileRef, File tmpFile, NodeRef destinationFolder, String fileName);

	public Integer lockJobFile(Integer idConfiguration);
	public Integer unlockJobFile(Integer idConfiguration);
}
