package eu.cec.digit.circabc.service.bulk.upload;


public interface UploadedEntry {
	public String getFilePath();
	public void setFilePath(final String filePath);

	public String getFileName();
	public void setFileName(final String fileName);

	public String getStatus();
	public void setStatus(final String status);

	public String getRemarks();
	public void setRemarks(final String remarks);
}
