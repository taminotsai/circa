/**
 * 
 */
package eu.cec.digit.circabc.repo.config.auto.upload;

import java.sql.SQLException;
import java.util.List;

import org.alfresco.repo.domain.activities.ibatis.IBatisSqlMapper;
import org.alfresco.service.cmr.repository.NodeRef;

import com.ibatis.sqlmap.client.SqlMapClient;

import eu.cec.digit.circabc.service.config.auto.upload.AutoUploadConfigurationService;

/**
 * @author beaurpi
 *
 */
public class AutoUploadConfigurationServiceImpl extends IBatisSqlMapper implements
		AutoUploadConfigurationService {

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.service.config.auto.upload.AutoUploadConfigurationService#registerConfiguration(eu.cec.digit.circabc.repo.config.auto.upload.Configuration)
	 */
	@Override
	public void registerConfiguration(Configuration config) throws SQLException {

		final SqlMapClient sqlMapClient = getSqlMapClient();
		try
		{
					
			sqlMapClient.startTransaction();
			sqlMapClient.startBatch();
			sqlMapClient.insert("AutoUploadConfiguration.insert_configuration", config);
			sqlMapClient.executeBatch();
			sqlMapClient.commitTransaction();
		} finally
		{
			sqlMapClient.endTransaction();
		}	

	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.service.config.auto.upload.AutoUploadConfigurationService#listConfigurations(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Configuration> listConfigurations(String igName)
			throws SQLException {
		
		return (List<Configuration>) getSqlMapClient().queryForList("AutoUploadConfiguration.select_all_configurations",igName);
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.service.config.auto.upload.AutoUploadConfigurationService#deleteConfiguration(eu.cec.digit.circabc.repo.config.auto.upload.Configuration)
	 */
	@Override
	public void deleteConfiguration(Configuration config) throws SQLException {

		getSqlMapClient().delete("AutoUploadConfiguration.delete_configuration", config);

	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.service.config.auto.upload.AutoUploadConfigurationService#updateConfiguration(eu.cec.digit.circabc.repo.config.auto.upload.Configuration)
	 */
	@Override
	public void updateConfiguration(Configuration config) throws SQLException {

		getSqlMapClient().update("AutoUploadConfiguration.update_configuration", config);

	}

	@Override
	public Configuration getConfigurationById(Integer idConfig) throws SQLException {
		
		return (Configuration) getSqlMapClient().queryForObject("AutoUploadConfiguration.select_configuration_by_id", idConfig.toString());
	}

	@Override
	public Configuration getConfigurationByNodeRef(NodeRef nodeRef)
			throws SQLException {
		
		return (Configuration) getSqlMapClient().queryForObject("AutoUploadConfiguration.select_configuration_by_file_ref", nodeRef.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Configuration> getAllConfigurations() throws SQLException {

		return (List<Configuration>) getSqlMapClient().queryForList("AutoUploadConfiguration.select_all_configurations_all");
	}

}
