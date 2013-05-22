/**
 * 
 */
package eu.cec.digit.circabc.repo.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.MLText;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import eu.cec.digit.circabc.repo.notification.NotifiableUserImpl;
import eu.cec.digit.circabc.service.notification.NotifiableUser;
import eu.cec.digit.circabc.service.notification.NotificationService;
import eu.cec.digit.circabc.service.notification.NotificationType;
import eu.cec.digit.circabc.service.profile.IGRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.Profile;
import eu.cec.digit.circabc.service.profile.ProfileException;
import eu.cec.digit.circabc.service.profile.permissions.DirectoryPermissions;
import eu.cec.digit.circabc.service.profile.permissions.EventPermissions;
import eu.cec.digit.circabc.service.profile.permissions.InformationPermissions;
import eu.cec.digit.circabc.service.profile.permissions.LibraryPermissions;
import eu.cec.digit.circabc.service.profile.permissions.NewsGroupPermissions;
import eu.cec.digit.circabc.service.user.BulkImportUserData;
import eu.cec.digit.circabc.service.user.BulkUserImportService;
import eu.cec.digit.circabc.service.user.LdapUserService;
import eu.cec.digit.circabc.service.user.UserService;
import eu.cec.digit.circabc.util.CircabcUserDataBean;
import eu.cec.digit.circabc.web.wai.dialog.profile.AccessProfileWrapper;

/**
 * @author beaurpi
 *
 */
public class BulkUserImportServiceImpl implements BulkUserImportService {
	
	private IGRootProfileManagerService igRootProfileManagerService;
	private PersonService personService;
	private LdapUserService ldapUserService;
	private NodeService nodeService;
	private NotificationService notificationService;
	
	private Log logger = LogFactory.getLog(BulkUserImportServiceImpl.class);
	private UserService userService;
	
	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private final static List<String> xlsDefinedColumns = new ArrayList<String>(Arrays.asList("username", "firstname", "lastname", "email", "profile"));

	/**
	 * 
	 */
	public BulkUserImportServiceImpl() {
		
	}

	/***
	 * Get members of one group
	 */
	@Override
	public List<BulkImportUserData> listMembers(NodeRef igRef, Boolean igNameAsProfile) {
		
		Map<String, Profile> members = igRootProfileManagerService.getInvitedUsersProfiles(igRef);
		
		
		List<BulkImportUserData> result = new ArrayList<BulkImportUserData>();
		
		String igTitle = nodeService.getProperty(igRef, ContentModel.PROP_TITLE).toString();
		String igName = nodeService.getProperty(igRef, ContentModel.PROP_NAME).toString();
		
		for(String member: members.keySet())
		{
			BulkImportUserData user = new BulkImportUserData();
						
			user.setIgRef(igRef);
			user.setIgName((igTitle != null ? igTitle : igName));
			user.setUser(ldapUserService.getLDAPUserDataByUid(member));
			user.setStatus(BulkImportUserData.STATUS_OK);
			
			if(igNameAsProfile)
			{
				user.setProfile((igTitle != null ? igTitle : igName));
			}
			
			result.add(user);
		}
		
		return result;
	}

	/**
	 * @param igRef
	 */
	public List<Profile> listGroupProfiles(NodeRef igRef) {
		
		List<Profile> profilesList = igRootProfileManagerService.getProfiles(igRef);
		return profilesList;
	}

	@Override
	public HSSFWorkbook saveWork(List<BulkImportUserData> model) {

		// create a new workbook
		HSSFWorkbook wb = new HSSFWorkbook();
		// create a new sheet
		Sheet s = wb.createSheet();
		// declare a row object reference
		Row r = null;
		// declare a cell object reference
		Cell c = null;
			
		wb.setSheetName(0, "UserExport");
		
		Integer i = 0, j = 0;
		
		r = s.createRow(i);
		
		CellStyle header = wb.createCellStyle();
		header.setBorderBottom((short) 1);
		header.setBorderRight((short) 1);
		header.setBorderTop((short) 1);
		header.setBorderLeft((short) 1);
				
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		header.setFont(font);
		
		// init the column names
		for(String col: xlsDefinedColumns)
		{
			c = r.createCell(j);
			c.setCellStyle(header);
			c.setCellValue(col);
			 
			j++;
		}
		
		i++;
		
		// add all user to export file
		for(BulkImportUserData user: model)
		{
			r = s.createRow(i);
			c = r.createCell(0);
			c.setCellValue(user.getUser().getEcasUserName());
			c = r.createCell(1);
			c.setCellValue(user.getUser().getFirstName());
			c = r.createCell(2);
			c.setCellValue(user.getUser().getLastName());
			c = r.createCell(3);
			c.setCellValue(user.getUser().getEmail());
			c = r.createCell(4);
			c.setCellValue(user.getProfile());
			
			i++;
		}
		
		return wb;
	}

	/**
	 * @return the ldapUserService
	 */
	public LdapUserService getLdapUserService() {
		return ldapUserService;
	}

	/**
	 * @param ldapUserService the ldapUserService to set
	 */
	public void setLdapUserService(LdapUserService ldapUserService) {
		this.ldapUserService = ldapUserService;
	}

	/**
	 * @return the personService
	 */
	public PersonService getPersonService() {
		return personService;
	}

	/**
	 * @param personService the personService to set
	 */
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	/**
	 * @return the nodeService
	 */
	public NodeService getNodeService() {
		return nodeService;
	}

	/**
	 * @param nodeService the nodeService to set
	 */
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	/**
	 * @return the igRootProfileManagerService
	 */
	public IGRootProfileManagerService getIgRootProfileManagerService() {
		return igRootProfileManagerService;
	}

	/**
	 * @param igRootProfileManagerService the igRootProfileManagerService to set
	 */
	public void setIgRootProfileManagerService(
			IGRootProfileManagerService igRootProfileManagerService) {
		this.igRootProfileManagerService = igRootProfileManagerService;
	}

	@Override
	public List<BulkImportUserData> loadWork(HSSFWorkbook book, String fileName) throws InvalidBulkImportFileFormatException {

		List<BulkImportUserData> result =  new ArrayList<BulkImportUserData>();
		
		if(book.getNumberOfSheets() >= 1)
		{
			for(int iSheet = 0; iSheet < book.getNumberOfSheets(); iSheet++)
			{
				Sheet s = book.getSheetAt(iSheet);
				
				int iRow = s.getFirstRowNum();
				
				if(!validateSheetColumns(s))
				{
					throw new InvalidBulkImportFileFormatException();
				}
				
				int nbRows = s.getPhysicalNumberOfRows();
				
				for(int i = iRow + 1 ; i<nbRows; i++)
				{
					Row r = s.getRow(i);
					int iCeel = r.getFirstCellNum();
					
					BulkImportUserData tmpUser = new BulkImportUserData();
					
					String username = (r.getCell(iCeel) != null ? r.getCell(iCeel).getStringCellValue() : "");
					String lastname = (r.getCell(iCeel+2) != null ? r.getCell(iCeel+2).getStringCellValue() : "");
					String email = (r.getCell(iCeel+3) != null ? r.getCell(iCeel+3).getStringCellValue() : "");
					String profile = (r.getCell(iCeel+4) != null ? r.getCell(iCeel+4).getStringCellValue() : ""); 
					
					tmpUser.setFromFile(fileName);
					tmpUser.setProfile(profile);
									
					List<String> possibleUsers = ldapUserService.getLDAPUserIDByIdMonikerEmailCn("", username, email, lastname, true);
					if(possibleUsers.size()==1)
					{
						tmpUser.setUser(ldapUserService.getLDAPUserDataByUid(possibleUsers.get(0)));
						tmpUser.setStatus(BulkImportUserData.STATUS_OK);
						
						result.add(tmpUser);
					}
					else{
						tmpUser.setUser(new CircabcUserDataBean());
						tmpUser.getUser().setEcasUserName(username);
						tmpUser.getUser().setEmail(email);
						tmpUser.setStatus(BulkImportUserData.STATUS_IGNORE);
						
						result.add(tmpUser);
					}
				}
			}
		}
		
		return result;
	}

	private Boolean validateSheetColumns(Sheet s) {
		
		Boolean result = true;
		
		int iRow = s.getFirstRowNum();
		
		Row r = s.getRow(iRow);
		
		int iCellFirst = r.getFirstCellNum();

		
		if(r.getPhysicalNumberOfCells() >= 5)
		{
			// verify presence of uid column
			if(r.getCell(iCellFirst) != null)
			{
				if(!r.getCell(iCellFirst).getStringCellValue().equals(xlsDefinedColumns.get(0)))
				{
					result = false;
				}
			}
			else
			{
				result = false;
			}
			// verify presence of firstname column
			if(r.getCell(iCellFirst+1) != null)
			{
				if(!r.getCell(iCellFirst+1).getStringCellValue().equals(xlsDefinedColumns.get(1)))
				{
					result = false;
				}
			}
			else
			{
				result = false;
			}
			// verify presence of lastname column
			if(r.getCell(iCellFirst+2) != null)
			{
				if(!r.getCell(iCellFirst+2).getStringCellValue().equals(xlsDefinedColumns.get(2)))
				{
					result = false;
				}
			}
			else
			{
				result = false;
			}
			// verify presence of email column
			if(r.getCell(iCellFirst+3) != null)
			{
				if(!r.getCell(iCellFirst+3).getStringCellValue().equals(xlsDefinedColumns.get(3)))
				{
					result = false;
				}
			}
			else
			{
				result = false;
			}
			// verify presence of profile column
			if(r.getCell(iCellFirst) != null)
			{
				if(!r.getCell(iCellFirst+4).getStringCellValue().equals(xlsDefinedColumns.get(4)))
				{
					result = false;
				}
			}
			else
			{
				result = false;
			}
		}
		else
		{
			result = false;
		}
		
		return result;
		
	}

	public void addAll(List<BulkImportUserData> model, List<BulkImportUserData> newValues)
	{
		
		
		for(BulkImportUserData tmpUser: newValues)
		{
			if(tmpUser.getUser() != null)
			{
				Boolean found = false;
				
				for(BulkImportUserData existingUser: model)
				{
					
					if(tmpUser.getUser().getEmail().toLowerCase().equals(existingUser.getUser().getEmail().toLowerCase()))
					{
						found=true;
						break;
					}
				}
				
				if(!found)
				{
					model.add(tmpUser);
				}
			}
		}
		
		

	}

	@Override
	public void parseProfilesToBeCreated(List<BulkImportUserData> model, Boolean createIgProfileHelper, Boolean createDepartmentNumberProfileHelper, List<String> profilesToBeCreated) {

		for(BulkImportUserData tmpUser: model)
		{
			if(createIgProfileHelper && tmpUser.getIgRef() != null)
			{
				if(tmpUser.getIgName() != null)
				{
					tmpUser.setProfile(tmpUser.getIgName());
					
					if(!profilesToBeCreated.contains(tmpUser.getIgName()))
					{
						profilesToBeCreated.add(tmpUser.getIgName());
					}
				}
			}
			else if(!createIgProfileHelper && tmpUser.getIgRef() != null)
			{
				tmpUser.setProfile("");
				
				if(tmpUser.getIgName() != null)
				{
					if(profilesToBeCreated.contains(tmpUser.getIgName()))
					{
						profilesToBeCreated.remove(tmpUser.getIgName());
					}
				}
			}
			
			if(createDepartmentNumberProfileHelper && tmpUser.getDepartmentNumber() != null)
			{
				tmpUser.setProfile(tmpUser.getDepartmentNumber());
				
				if(!profilesToBeCreated.contains(tmpUser.getDepartmentNumber()))
				{
					profilesToBeCreated.add(tmpUser.getDepartmentNumber());
				}
			}
			else if(!createDepartmentNumberProfileHelper && tmpUser.getDepartmentNumber() != null)
			{
				tmpUser.setProfile("");
				
				if(tmpUser.getDepartmentNumber() != null)
				{
					if(profilesToBeCreated.contains(tmpUser.getDepartmentNumber()))
					{
						profilesToBeCreated.remove(tmpUser.getDepartmentNumber());
					}
				}
			}
		}
	}

	
	public void inviteUsers(List<BulkImportUserData> model, NodeRef igRef, Map<String, String> igProfiles, Boolean notify) {

		for(BulkImportUserData user: model)
		{
			if(user.getStatus().equals(BulkImportUserData.STATUS_OK))
			{
				if(igRootProfileManagerService.getPersonProfile(igRef, user.getUser().getUserName()) == null)
				{

					if(igProfiles.keySet().contains(user.getProfile()))
					{
						try
						{
							inviteUserToGroup(igRef, igProfiles, user, notify);
						}
						catch(ProfileException e)
						{
							if(logger.isErrorEnabled())
							{
								logger.error("BulkUserImport: Error during adding user: "+user.getUser().getUserName()+" to profile :"+user.getProfile(), e);
							}
						}
					}
					else
					{
						try
						{
							Map<String, String> newProfile = createNewAccessProfile(igRef, user.getProfile());
							igProfiles.putAll(newProfile);

							inviteUserToGroup(igRef, igProfiles, user, notify);
						}
						catch(ProfileException e)
						{

							if(logger.isErrorEnabled())
							{
								logger.error("BulkUserImport: Error during adding user: "+user.getUser().getUserName()+" to profile newly created :"+user.getProfile(), e);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * @param igRef
	 * @param igProfiles
	 * @param user
	 * @param notify 
	 */
	private void inviteUserToGroup(NodeRef igRef, Map<String, String> igProfiles,
			BulkImportUserData user, Boolean notify) 
	{
		if(!personService.personExists(user.getUser().getUserName()))
		{
			personService.createPerson(user.getUser().getAttributesAsMap());
		}
		
		igRootProfileManagerService.addPersonToProfile(igRef, user.getUser().getUserName(), igProfiles.get(user.getProfile()));
		
		if(notify)
		{
			
			Set<NotifiableUser> users = new HashSet<NotifiableUser>();
			
			NodeRef person = getPersonService().getPerson(user.getUser().getUserName());
			Map<QName, Serializable> properties = getNodeService().getProperties(person);
			
			

			Serializable langObject = userService.getPreference(person, UserService.PREF_INTERFACE_LANGUAGE);

			Locale locale;
			
			if(langObject == null)
			{
				locale = null;
			}
			else if (langObject instanceof Locale)
			{
				locale = (Locale) langObject;
			}
			else
			{
				locale = new Locale(langObject.toString());
			}

			users.add(new NotifiableUserImpl(person, locale, properties));
			
			try {
				
				notificationService.notify(igRef, users, NotificationType.NOTIFY_USER_INVITATION);
				
			} catch (Exception e) {
				if(logger.isErrorEnabled())
				{
					logger.error("Error during notification of user: "+user.getUser().getUserName()+" in the bulk user importation.", e);
				}
			}
		}
	}

	private Map<String, String> createNewAccessProfile(NodeRef igRef, String profileTitle)
	{
		Map<String, String> resultProfile = new HashMap<String, String>();
		
		AccessProfileWrapper apw = new AccessProfileWrapper(igRef);
		apw.withTitle(Locale.ENGLISH, profileTitle);
		apw.setDirPermission(DirectoryPermissions.DIRNOACCESS.toString());
		apw.setLibPermission(LibraryPermissions.LIBNOACCESS.toString());
		apw.setInfPermission(InformationPermissions.INFNOACCESS.toString());
		apw.setEvePermission(EventPermissions.EVENOACCESS.toString());
		apw.setNewPermission(NewsGroupPermissions.NWSNOACCESS.toString());
		apw.withDescription(Locale.ENGLISH, "Profile automatically created from Bulk User Import tool");
		
		Profile p = apw.toProfile();
		p.setTitles(new MLText(Locale.ENGLISH, profileTitle));
		p.setDescriptions(new MLText(Locale.ENGLISH, "Profile automatically created from Bulk User Import tool"));
		
		String name = p.getProfileName();
		
		igRootProfileManagerService.addProfile(igRef, name, p.getServicesPermissions());
							
		resultProfile.put(profileTitle, name);
		
		Profile createdP = igRootProfileManagerService.getProfile(igRef, name);
		p.setAlfrescoGroupName(createdP.getAlfrescoGroupName());
		p.setProfileName(createdP.getProfileName());
		p.setPrefixedAlfrescoGroupName(createdP.getPrefixedAlfrescoGroupName());
		
		
		// update the profile at the IG Root level and at ig services level
		// shitty stuff that should be done so profile is well recorded....
		igRootProfileManagerService.updateProfile(igRef, name, p.getServicesPermissions(), false);
		
		igRootProfileManagerService.updateProfileMap(igRef, p);
		
		return resultProfile;
	}

	/**
	 * @return the notificationService
	 */
	public NotificationService getNotificationService() {
		return notificationService;
	}

	/**
	 * @param notificationService the notificationService to set
	 */
	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
}
