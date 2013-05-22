package eu.cec.digit.circabc.web.wai.dialog.system;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.alfresco.repo.security.authentication.TicketComponent;

import eu.cec.digit.circabc.action.config.ImportConfig;
import eu.cec.digit.circabc.business.api.user.UserDetails;
import eu.cec.digit.circabc.web.servlet.UploadFileServletConfig;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

public class SystemMonitorDialog extends BaseWaiDialog
{
	private static final long serialVersionUID = 6158073330963347674L;
	
	private UploadFileServletConfig config;
	private ImportConfig importConfig; 
	
// Migration 3.1 -> 3.4.6 - 20/12/2011 - AuthenticationServiceImpl changed because of the authentication subsystems in Alfresco 3.4.6
// The accessed methods belong to the TicketComponent, so this was replaced instead.
// This code suggests me that before a custom ticket component could have been implemented.
//		private AuthenticationService authenticationService;
//
//		public AuthenticationService getAuthenticationService2() {
//			return authenticationService;
//		}
//
//		public void setAuthenticationService(
//				AuthenticationService authenticationService) {
//			this.authenticationService = authenticationService;
//		}
		
	private TicketComponent ticketComponent = null;
		
	//***********************************************************************
	//                                                              OVERRIDES
	//***********************************************************************
	
	@Override
	public void init(final Map<String, String> parameters)
	{
		super.init(parameters);
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome)
			throws Throwable {
		return null;
	}
	
	@Override
	public String getFinishButtonLabel()
	{
		return this.translate("system_monitor_refresh");
	}
	
	@Override
	public String getCancelButtonLabel()
	{
		return this.translate("system_monitor_close");
	}

	public String getPageIconAltText() {
		return null;
	}

	public String getBrowserTitle() {
		return null;
	}
	
	//***********************************************************************
	//                                                         PRIVATE HELPER
	//***********************************************************************
	
	
	//***********************************************************************
	//                                                      GETTER AND SETTER
	//***********************************************************************
	
	/**
	 * @param ticketComponent the ticketComponent to set
	 */
	public void setTicketComponent(TicketComponent ticketComponent) {
		this.ticketComponent = ticketComponent;
	}
	
	public ArrayList<UserDetails> getUsers()
	{
//		Set<String> userNames = this.getAuthenticationService().getUsersWithTickets(true);
		Set<String> userNames = ticketComponent.getUsersWithTickets(true);
		ArrayList<UserDetails> users = new ArrayList<UserDetails>();
		for (String userName : userNames)
		{
			if(!userName.equals("guest"))
			{
				UserDetails userDets = getBusinessRegistry().getUserDetailsBusinessSrv().getUserDetails(userName);
				users.add(userDets);
			}
		}
		return users;
	}
	
	public int getUserCount()
	{
//		Set<String> userNames = this.getAuthenticationService().getUsersWithTickets(true);
		Set<String> userNames = ticketComponent.getUsersWithTickets(true);
		return userNames.size() - 1;
	}
	
	public int getTicketCount()
	{
//		return this.getAuthenticationService().countTickets(true);
		return ticketComponent.countTickets(true);
	}

	public void setConfig(UploadFileServletConfig config)
	{
		this.config = config;
	}

	public int getMaxFileSize()
	{
		return this.config.getMaxSizeInMegaBytes();
	}
	
	public void setMaxFileSize(int value)
	{
		this.config.setMaxSizeInMegaBytes(value);
	}
	
	
	public int getImportMaxFileSize()
	{
		return this.importConfig.getMaxSizeInMegaBytes();
	}
	
	public void setImportMaxFileSize(int value)
	{
		this.importConfig.setMaxSizeInMegaBytes(value);
	}

	public void setImportConfig(ImportConfig importConfig) {
		this.importConfig = importConfig;
	}

	public ImportConfig getImportConfig() {
		return importConfig;
	}
}
