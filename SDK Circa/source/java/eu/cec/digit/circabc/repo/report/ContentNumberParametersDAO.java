/**
 * 
 */
package eu.cec.digit.circabc.repo.report;

/**
 * @author beaurpi
 *
 */
public class ContentNumberParametersDAO {
	
	private Integer idContentQname;
	private Integer idCmObjectQname;
	private Integer idVersionHistoryQname;
	
	public ContentNumberParametersDAO(Integer idContentQname, Integer idCmObjectQname, Integer idVersionHistoryQname) {
	
		this.idContentQname = idContentQname;
		this.idCmObjectQname = idCmObjectQname;
		this.idVersionHistoryQname = idVersionHistoryQname;
	}

	/**
	 * @return the idContentQname
	 */
	public Integer getIdContentQname() {
		return idContentQname;
	}

	/**
	 * @param idContentQname the idContentQname to set
	 */
	public void setIdContentQname(Integer idContentQname) {
		this.idContentQname = idContentQname;
	}

	/**
	 * @return the idCmObjectQname
	 */
	public Integer getIdCmObjectQname() {
		return idCmObjectQname;
	}

	/**
	 * @param idCmObjectQname the idCmObjectQname to set
	 */
	public void setIdCmObjectQname(Integer idCmObjectQname) {
		this.idCmObjectQname = idCmObjectQname;
	}

	/**
	 * @return the idVersionHistoryQname
	 */
	public Integer getIdVersionHistoryQname() {
		return idVersionHistoryQname;
	}

	/**
	 * @param idVersionHistoryQname the idVersionHistoryQname to set
	 */
	public void setIdVersionHistoryQname(Integer idVersionHistoryQname) {
		this.idVersionHistoryQname = idVersionHistoryQname;
	}

	

}
