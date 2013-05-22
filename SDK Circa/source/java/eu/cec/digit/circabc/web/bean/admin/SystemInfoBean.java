/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.bean.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.faces.event.ActionEvent;

import org.alfresco.web.ui.common.component.UIPanel.ExpandedEvent;

/**
 * Bean that backs the the System Info page
 *
 * TODO do a cron manager, drop down with the list of jobs and allow to perform some actions like start/stop/pause...
 *
 * @author Yanick Pignot
 */
public class SystemInfoBean implements Serializable
{

	/***/
	private static final long serialVersionUID = -1408703142340817609L;

	private String shellCmd = "";
	private String shellResult = "";
	private String shellError = "";

	private boolean cmdPanelExpanded = false;

	/**
    * @return
    *
    */
   public String submitSearch()
   {
	   shellError = "";
	   shellResult = "";
	   String cmd = null;

	   if(shellCmd != null && shellCmd.length() > 0)
	   {
		   try
		   {
			   String osName = System.getProperty("os.name" );


	           if( osName.equals( "Windows 95" ) )
	           {
	        	   cmd = "command.com " + "/C " + shellCmd;
	           }
	           else if(osName.startsWith("Windows"))
	           {
	        	   cmd = "cmd.exe " + "/C " + shellCmd;
	           }
	           else
	           {
	        	   cmd = getShellCmd();
	           }

			   Runtime rt= Runtime.getRuntime();

			   Process process = rt.exec(cmd);
			   //process.waitFor();

			   shellResult = readStream(process.getInputStream());
			   shellError  = readStream(process.getErrorStream());
		   }
		   catch (Exception e)
		   {
			   shellError = "Impossible to launch command " + cmd
			   					+ ". \nCause: " + e.getMessage()
			   					+ ". \nException: " + e.getClass().getName();
		   }
	   }

       return "dialog:close:dialog:showCircabcSystemInfo";
   }

	/**
	 * @return the shellCmd
	 */
	public String getShellCmd()
	{
		return shellCmd;
	}
	/**
	 * @param shellCmd the shellCmd to set
	 */
	public void setShellCmd(String shellCmd)
	{
		this.shellCmd = shellCmd;
	}
	/**
	 * @return the shellError
	 */
	public String getShellError()
	{
		return shellError;
	}
	/**
	 * @param shellError the shellError to set
	 */
	public void setShellError(String shellError)
	{
		this.shellError = shellError;
	}
	/**
	 * @return the shellResult
	 */
	public String getShellResult()
	{
		return shellResult;
	}
	/**
	 * @param shellResult the shellResult to set
	 */
	public void setShellResult(String shellResult)
	{
		this.shellResult = shellResult;
	}


	private String readStream(InputStream input) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(input));

		StringBuffer buff = new StringBuffer("");
		String line = null;

		while((line = br.readLine())!=null)
		{
			   buff.append(line).append("\n");
		}

		return buff.toString();
	}

	/**
	 * @return the cmdPanelExpanded
	 */
	public boolean isCmdPanelExpanded()
	{
		return cmdPanelExpanded;
	}

	/**
	 * @param cmdPanelExpanded the cmdPanelExpanded to set
	 */
	public void setCmdPanelExpanded(boolean cmdPanelExpanded)
	{
		this.cmdPanelExpanded = cmdPanelExpanded;
	}

	public void expandCmdPanel(ActionEvent event)
	{
	      if (event instanceof ExpandedEvent)
	      {
	    	  setCmdPanelExpanded(((ExpandedEvent)event).State);
	      }
	 }
}
