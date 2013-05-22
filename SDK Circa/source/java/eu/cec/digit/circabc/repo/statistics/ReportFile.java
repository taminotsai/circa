/**
 * 
 */
package eu.cec.digit.circabc.repo.statistics;

import java.math.BigDecimal;

import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.web.app.servlet.DownloadContentServlet;

/**
 * @author beaurpi
 *
 */
public class ReportFile{

	private String name;
	private FileInfo fileInfo;
	private String downloadUrl;
	private String sizeAsString;
	
	public ReportFile() {
		// TODO Auto-generated constructor stub
	}

	public ReportFile(FileInfo f, String name) {
		this.fileInfo = f;
		this.name = name;
		convertFileInfoSize();
		downloadUrl = getUrl(f);
	}

	/**
	 * 
	 */
	private void convertFileInfoSize() {
		BigDecimal size = new BigDecimal(fileInfo.getContentData().getSize() / 1024);
		
		this.sizeAsString =  size.setScale(2).toString()+"kbs";

	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the fileInfo
	 */
	public FileInfo getFileInfo() {
		return fileInfo;
	}

	/**
	 * @param fileInfo the fileInfo to set
	 */
	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
		convertFileInfoSize();
	}

	/**
	 * @return the downloadUrl
	 */
	public String getDownloadUrl() {
		return downloadUrl;
	}

	/**
	 * @param downloadUrl the downloadUrl to set
	 */
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	
	private String getUrl(FileInfo fileInfo)
	{

		return DownloadContentServlet.generateBrowserURL(fileInfo.getNodeRef(), fileInfo.getName());
	}

	/**
	 * @return the sizeAsString
	 */
	public String getSizeAsString() {
		return sizeAsString;
	}

	/**
	 * @param sizeAsString the sizeAsString to set
	 */
	public void setSizeAsString(String sizeAsString) {
		this.sizeAsString = sizeAsString;
	}
	
}
