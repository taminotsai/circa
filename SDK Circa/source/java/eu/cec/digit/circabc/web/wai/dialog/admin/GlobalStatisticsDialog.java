/**
 * 
 */
package eu.cec.digit.circabc.web.wai.dialog.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.alfresco.service.cmr.model.FileInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.repo.statistics.ReportFile;
import eu.cec.digit.circabc.service.statistics.global.GlobalStatisticsService;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * @author beaurpi
 *
 */
public class GlobalStatisticsDialog extends BaseWaiDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2303657789984298507L;
	
	private GlobalStatisticsService globalStatsServ;

	private List<FileInfo> contents;
	private List<ReportFile> reports;
	
	private static final Log logger = LogFactory.getLog(GlobalStatisticsDialog.class);
	
	private String userid;
	private String result;
	
	/** Small icon default name */
	   public static final String SPACE_SMALL_DEFAULT = "space_small";

	public String getPageIconAltText() {
		return translate("global_statistics_dialog_alt_text");
	}

	public String getBrowserTitle() {
		return translate("global_statistics_dialog_title");
	}

	protected String finishImpl(FacesContext context, String outcome)
			throws Throwable {

		return outcome;
	}
	
	public String fireme(ActionEvent event)
	{

		
		globalStatsServ.saveStatsToExcel(globalStatsServ.getReportSaveFolder(), globalStatsServ.makeGlobalStats());
		
		loadContents();
		
		return null;
	}
	
	public String fireme2(ActionEvent event)
	{
		globalStatsServ.cleanAndZipPreviousReportFiles();
		
		
		loadContents();
		
		return null;
	}


	/**
	 * 
	 */
	public void loadContents() {
		this.contents = Collections.emptyList();
		this.reports = Collections.emptyList();
		
		this.contents = globalStatsServ.getListOfReportFiles();
		
		this.reports = new ArrayList<ReportFile>();
		for(FileInfo f: this.contents)
		{
			this.reports.add(new ReportFile(f, f.getName()));
		}
	}
	
	@Override
	public void init(Map<String, String> parameters) {
		
		super.init(parameters);
		
		if(!globalStatsServ.isReportSaveFolderExisting())
		{
			globalStatsServ.prepareFolderRecipient();
		}

		loadContents();

	}
	
	public List<ReportFile> getReports()
	{
		return reports;
	}
	
	public String searchLastLogin(ActionEvent event)
	{
		Date date;
		date = globalStatsServ.getLastLoginDateOfUser(userid);
		
		if(date == null)
		{
			result = "user never connected";
		}
		else
		{
			GregorianCalendar gcLogin = new GregorianCalendar();
			gcLogin.setTime(date);
			
			result = gcLogin.get(Calendar.DAY_OF_MONTH)+"-"+(gcLogin.get(Calendar.MONTH)+1)+"-"+gcLogin.get(Calendar.YEAR);
		}
		
		return null;
	}
	
	/***
	 * GETTERS & SETTERS
	 */

	
	/**
	 * @return the globalStatsServ
	 */
	public GlobalStatisticsService getGlobalStatsServ() {
		return globalStatsServ;
	}

	/**
	 * @param globalStatsServ the globalStatsServ to set
	 */
	public void setGlobalStatsServ(GlobalStatisticsService globalStatsServ) {
		this.globalStatsServ = globalStatsServ;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	
	/**
	 * @param containers the containers to set
	 */
	public void setContents(List<FileInfo> contents) {
		this.contents = contents;
	}

	/**
	 * @param reports the reports to set
	 */
	public void setReports(List<ReportFile> reports) {
		this.reports = reports;
	}

	/**
	 * @return the contents
	 */
	public List<FileInfo> getContents() {
		return contents;
	}	

}
