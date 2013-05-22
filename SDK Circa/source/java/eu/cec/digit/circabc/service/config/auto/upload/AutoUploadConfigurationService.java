/**
 * 
 */
package eu.cec.digit.circabc.service.config.auto.upload;

import java.sql.SQLException;
import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.repo.config.auto.upload.Configuration;

/**
 * @author beaurpi
 * Service Interface to be able to configure autoupload inside CIRCABC
 * Auto upload in CIRCABC enterprise is implemented by Oracle Service Bus
 *
 */
public interface AutoUploadConfigurationService {

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
	 * Get one configuration by document node ref.
	 * @param nodeRef
	 * @return
	 * @throws SQLException
	 */
	public Configuration getConfigurationByNodeRef(NodeRef nodeRef) throws SQLException;

	/***
	 * all configurations
	 * @return
	 * @throws SQLException 
	 */
	public List<Configuration> getAllConfigurations() throws SQLException;
}
