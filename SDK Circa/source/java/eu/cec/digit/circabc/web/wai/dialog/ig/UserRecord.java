package eu.cec.digit.circabc.web.wai.dialog.ig;



public class UserRecord {

	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String ecasDomain;
	private String moniker;
	private String profile;

	public UserRecord(String userName, String firstName, String lastName,
			String email, String ecasDomain, String moniker, String profile) {
	
		this.userName = userName;
		this.firstName =firstName;  
		this.lastName = lastName;
		this.email = email ;
		this.ecasDomain =ecasDomain;  
		this.moniker = moniker;  
		this.profile =profile;
	}

	public String getUserName() {
		if (this.userName != null)
		{
			return this.userName;
		}
		else
		{
			return "N/A";
		}
	}

	public String getLastName() {
		
		if (this.lastName != null)
		{
			return this.lastName;
		}
		else
		{
			return "N/A";
		}
	}

	public String getFirstName() {
		
		if (this.firstName != null)
		{
			return this.firstName;
		}
		else
		{
			return "N/A";
		}
	}

	public String getEmail() {
		
		if (this.email != null)
		{
			return this.email;
		}
		else
		{
			return "N/A";
		}
	}

	public String getECASDomain() {
		
		if (this.ecasDomain != null)
		{
			return this.ecasDomain;
		}
		else
		{
			return "N/A";
		}
	}

	public String getECASMoniker() {
		
		if (this.moniker != null)
		{
			return this.moniker;
		}
		else
		{
			return "N/A";
		}
	}

	public String getProfile() {
		
		if (this.profile != null)
		{
			return this.profile;
		}
		else
		{
			return "N/A";
		}
	}

}
