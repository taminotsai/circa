package eu.cec.digit.circabc.repo.iam;

import java.sql.SQLException;

import org.alfresco.repo.domain.activities.ibatis.IBatisSqlMapper;

public class EcordaDaoServiceImpl extends IBatisSqlMapper {

	@SuppressWarnings("unchecked")
	public String getEcordaThemaID(String nodeRef) throws SQLException
	{
		return  (String) getSqlMapClient().queryForObject("CircabcEcorda.select_ecorda_thema_id_by_ig_node_ref",nodeRef);
	}

}
