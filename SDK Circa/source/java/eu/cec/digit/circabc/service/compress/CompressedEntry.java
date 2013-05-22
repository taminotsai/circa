package eu.cec.digit.circabc.service.compress;


public interface CompressedEntry {
	public String getFileName();
	public long getFileSize();
	public boolean isDirectory();
	public String getComment();
	public long getFileCompressedSize();
	public long getCrc();
	public long getTime();
}
