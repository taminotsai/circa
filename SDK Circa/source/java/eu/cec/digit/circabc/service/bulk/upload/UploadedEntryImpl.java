package eu.cec.digit.circabc.service.bulk.upload;

import java.io.File;

public class UploadedEntryImpl implements UploadedEntry {

	private String filePath;
	private String fileName;
	private String remarks;
	private String status;

	public UploadedEntryImpl ()
	{
	}
	public UploadedEntryImpl (String fileName,String filePath)
	{
		this.fileName = fileName;
		this.filePath = filePath;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(final String filePath) {
		String name;
		if(File.separatorChar == '/') {
			name = filePath.replace('\\', File.separatorChar);
		} else {
			name = filePath.replace('/', File.separatorChar);
		}
		this.filePath = name;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(final String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(final String status) {
		this.status = status;
	}
}
