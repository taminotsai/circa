/**
 * 
 */
package eu.cec.digit.circabc.service.ftp;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;



/**
 * @author beaurpi
 *
 */
public interface SimpleFtpClient {
	
	/***
	 * Init parameters to connect to FTP server
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param path
	 * @throws FTPException 
	 * @throws FTPIllegalReplyException 
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void initParameters(String host, Integer port, String username, String password, String path) throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException;
	
	/***
	 * list files of remote server
	 * @return
	 * @throws FTPException 
	 * @throws FTPIllegalReplyException 
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * @throws FTPListParseException 
	 * @throws FTPAbortedException 
	 * @throws FTPDataTransferException 
	 */
	public String[] listFiles() throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException, FTPDataTransferException, FTPAbortedException, FTPListParseException;

	/***
	 * disconnect from ftp
	 */
	public void logout();

	/***
	 * verify if one file is present on server
	 * @param property
	 * @return
	 * @throws FTPListParseException 
	 * @throws FTPAbortedException 
	 * @throws FTPDataTransferException 
	 * @throws FTPException 
	 * @throws FTPIllegalReplyException 
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public Boolean fileExists(String fileName) throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException, FTPDataTransferException, FTPAbortedException, FTPListParseException;
	
	/***
	 * download file locally
	 * @param filename
	 * @return
	 * @throws IllegalStateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws FTPIllegalReplyException
	 * @throws FTPException
	 * @throws FTPDataTransferException
	 * @throws FTPAbortedException
	 * @throws FTPListParseException
	 */
	public File downloadFile(String filename) throws IllegalStateException, FileNotFoundException, IOException, FTPIllegalReplyException, FTPException, FTPDataTransferException, FTPAbortedException, FTPListParseException;

	/***
	 * rename file in the remote ftp server
	 * @param fileName
	 * @param newFileName
	 * @throws FTPException 
	 * @throws FTPIllegalReplyException 
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void renameRemoteFile(String fileName, String newFileName) throws IllegalStateException, IOException, FTPIllegalReplyException, FTPException;

	/***
	 * 
	 * @return
	 */
	public String getFileName();

}
