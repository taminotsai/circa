package eu.cec.digit.circabc.web.wai.dialog.ig;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.AccessPermission;
import org.alfresco.service.cmr.security.AccessStatus;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import eu.cec.digit.circabc.business.api.link.LinksBusinessSrv;
import eu.cec.digit.circabc.model.UserModel;
import eu.cec.digit.circabc.service.profile.CategoryProfileManagerService;
import eu.cec.digit.circabc.service.profile.IGRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.Profile;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;
import eu.cec.digit.circabc.web.repository.CategoryHeaderNode;
import eu.cec.digit.circabc.web.wai.bean.navigation.CategoryHeader;
import eu.cec.digit.circabc.web.wai.bean.navigation.CategoryHeadersBean;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

@SuppressWarnings("rawtypes")
public class MoveIgDialog extends BaseWaiDialog {

	private static final long serialVersionUID = -5400857267102221360L;
	
	// Properties used in JSP:
	private String sourceHeader;
	private String sourceCategory;
	private String sourceIg;
	private String targetHeader;
	private String targetCategory;
	private List allHeaders;
	
	// Properties injected by JSF:
	private CategoryHeadersBean categoryHeadersBean;
	
	private AuthorityService authorityService;

	private PersonService personService;
	
	private static final Log logger = LogFactory.getLog(MoveIgDialog.class);

	//***************************************************************
	//                                                 DIALOG METHODS
	//***************************************************************
	
	public String getPageIconAltText() {
		return translate("move_ig_dialog_icon_tooltip");
	}

	public String getBrowserTitle() {
		return translate("move_ig_dialog_browser_title");
	}
	 
	protected String finishImpl(FacesContext context, String outcome)
			throws Throwable {
			if(this.getTargetCategory() != null && this.getSourceIg() != null) {
				
				
				// do not allow to move if there is some share spaces, imported or exported profiles
				
				// get the node references
				NodeRef igRef = new NodeRef(this.getSourceIg());
								
				NodeRef newCategoryRef = new NodeRef(this.getTargetCategory());
				NodeRef oldCategoryRef = new NodeRef(this.getSourceCategory());
				
				IGRootProfileManagerService igRootProfileManagerService = getProfileManagerServiceFactory().getIGRootProfileManagerService();
				
				
				
				if (hasSharedSpaces(igRef) || hasExportedOrImportedProfiles(igRef,igRootProfileManagerService))
				{
					this.isFinished = false;
					Utils.addErrorMessage(translate("move_ig_dialog_msg_invalid"));
					
				}
				else
				{
					try {
						CategoryProfileManagerService categoryProfileManagerService = getProfileManagerServiceFactory().getCategoryProfileManagerService();
						
						String masterInvitedGroupName = igRootProfileManagerService.getMasterInvitedGroupName(igRef);
						String newSubsGroupName = categoryProfileManagerService.getSubsGroupName(newCategoryRef);
						String oldSubsGroupName = categoryProfileManagerService.getSubsGroupName(oldCategoryRef);
						
						Profile newCategoryProfile = categoryProfileManagerService.getProfile(newCategoryRef, CategoryProfileManagerService.Profiles.CIRCA_CATEGORY_ADMIN);
						Profile oldCategoryProfile = categoryProfileManagerService.getProfile(oldCategoryRef, CategoryProfileManagerService.Profiles.CIRCA_CATEGORY_ADMIN);
						
						moveUserGroups(oldSubsGroupName,newSubsGroupName,masterInvitedGroupName);
									
						getRuleService().disableRules();
						// move the node
						this.getFileFolderService().move(igRef, newCategoryRef, null);
						
						
						changeCategoryAdminPermissionsReqursivly(igRef,oldCategoryProfile.getPrefixedAlfrescoGroupName(),newCategoryProfile.getPrefixedAlfrescoGroupName()) ;
						
						
						// confirm
						this.isFinished = true;
						Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate("move_ig_dialog_msg_confirmation", getName(igRef)));
					}
					finally
					{
						getRuleService().enableRules();
					}

			}
			}
			else if(this.getTargetCategory() == null)
			{
				this.isFinished = false;
				Utils.addErrorMessage(translate("move_ig_dialog_msg_nocat"));
			}
			else if(this.getSourceIg() == null)
			{
				this.isFinished = false;
				Utils.addErrorMessage(translate("move_ig_dialog_msg_noig"));
			}
			

		
		// stay in the dialog
		return null;
	}

	private boolean hasExportedOrImportedProfiles(NodeRef igRef,
			IGRootProfileManagerService igRootProfileManagerService) {
		boolean result = false ;
		List<Profile> profiles = igRootProfileManagerService.getProfiles(igRef);
		for (Profile profile : profiles) {
			if (profile.isExported() || profile.isImported() )
			{
				result = true;
				break;
			}
		}
		return result;
	}

	private boolean hasSharedSpaces(NodeRef igRef) {
		return getLinksBusinessSrv().findSharedSpaces(igRef).size() > 0 ;
	}
		
	private void changeCategoryAdminPermissionsReqursivly(
			NodeRef nodeRef, String oldPrefixedGroupName, String newPrefixedGroupName) 
	{
		movePermissionRecursively(nodeRef,  oldPrefixedGroupName, newPrefixedGroupName);
	}
	
	
	private void movePermissionRecursively(final NodeRef nodeRef, final String oldAuthority,final String newAuthority)
	{
		final Set<AccessPermission> accessPermissions = getPermissionService().getAllSetPermissions(nodeRef);
		for(final AccessPermission accessPermission : accessPermissions) {
			if(accessPermission.getAuthority().equals(oldAuthority)) {
				getPermissionService().setPermission(nodeRef , newAuthority , accessPermission.getPermission(), accessPermission.getAccessStatus() == AccessStatus.ALLOWED );
				getPermissionService().deletePermission(nodeRef , accessPermission.getAuthority(), accessPermission.getPermission());
			}
		}

		// clear permissions on files, discussions, ...
		final List<FileInfo> inList = getFileFolderService().list(nodeRef);
		for (final FileInfo fileInfo : inList)
		{
			movePermissionRecursively(fileInfo.getNodeRef(),oldAuthority, newAuthority);
		}
	}
	
	

	private void moveUserGroups(String oldParentName ,String newParentName ,String childName) {
		String newParentGroupName = authorityService.getName(AuthorityType.GROUP, newParentName);
		String oldParentGroupName = authorityService.getName(AuthorityType.GROUP, oldParentName);
		String childGroupName = authorityService.getName(AuthorityType.GROUP, childName);
		
		authorityService.addAuthority(newParentGroupName, childGroupName);
		authorityService.removeAuthority(oldParentGroupName, childGroupName);
		
	}

	//***************************************************************
	//                                                         HELPER
	//***************************************************************
	
	@SuppressWarnings("unchecked")
	private List getAllCategoryHeaders() {
		List result = new ArrayList();
		result.add(new SelectItem("null", "<Select Header>"));
		for(CategoryHeader h : this.getCategoryHeadersBean().getCategoryHeaders()) {
			NavigableNode header = h.getCategoryHeader();
			result.add(new SelectItem(
					header.getNodeRefAsString(),
					header.getName()));
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private List getCategoriesOfHeader(String headerRefStr) {
		List result = new ArrayList();
		if(isNotEmpty(headerRefStr)) {
			NodeRef nodeRef = new NodeRef(headerRefStr);
			CategoryHeaderNode node = new CategoryHeaderNode(nodeRef);
			CategoryHeader header = new CategoryHeader(node);
			result.add(new SelectItem("null", "<Select Category>"));
			for(NavigableNode category : header.getCategories()) {
				NodeRef categoryRef = category.getNodeRef();
				result.add(new SelectItem(categoryRef.toString(), getName(categoryRef)));
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private List getInterestGroupsOfCategory(String catRefStr) {
		List result = new ArrayList();
		if(isNotEmpty(catRefStr)) {
			NodeRef catRef = new NodeRef(catRefStr);
			List<NodeRef> igs = getManagementService().getInterestGroups(catRef);
			for(NodeRef igRef : igs) {
				result.add(new SelectItem(igRef.toString(), getName(igRef)));
			}
		}
		return result;
	}
	
	private String getName(NodeRef ref) {
		Map<QName, Serializable> props = this.getNodeService().getProperties(ref);
		if(props.containsKey(ContentModel.PROP_NAME)) {
			return props.get(ContentModel.PROP_NAME).toString();
		}
		else {
			return ref.toString();
		}
	}
	
	private boolean isNotEmpty(String s) {
		return !(s == null || s.equals("") || s.equalsIgnoreCase("null"));
	}
	
	public void export()
	{
		if (this.getSourceIg() !=null)
		{
			FacesContext context = FacesContext.getCurrentInstance();
			
			HttpServletResponse response = ( HttpServletResponse ) context.getExternalContext().getResponse();
				
			
			List<UserRecord> userList =  getUserList();
			
	        ServletOutputStream outStream = null;
	        try 
			{
	        	response.reset();
				outStream = response.getOutputStream();	
				String interestGroupName = getInterestGroupName();
				response.setHeader("Content-Disposition","attachment;filename=" + interestGroupName+ ".xls");
	    		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	    		writeXLS(userList, outStream);
	    	
	            context.responseComplete();
	
			} catch (Exception ex)
			{
				logger.error("Error exporting members "  ,ex);
			}
			finally
			{
				if (outStream != null)
				{				
					try {
						outStream.close();
					} catch (IOException ex) {
						logger.error("Error closing stream",ex);
					}
				}
			}
		}
	}
	
private String getInterestGroupName() {
	NodeRef igRef = new NodeRef(this.getSourceIg());
	return (String) getNodeService().getProperty(igRef,ContentModel.PROP_NAME ); 
	
	}

private List<UserRecord> getUserList() {
		List<UserRecord> result = new ArrayList<UserRecord>();
		NodeRef igRef = new NodeRef(this.getSourceIg());
		IGRootProfileManagerService igRootProfileManagerService = getProfileManagerServiceFactory().getIGRootProfileManagerService();
		Map<String, Profile> invitedUsersProfiles = igRootProfileManagerService.getInvitedUsersProfiles(igRef);
		for (Map.Entry<String, Profile>  entry : invitedUsersProfiles.entrySet()) {
			
			String userName = entry.getKey();
			NodeRef person = getPersonService().getPerson(userName);
			
			String firstName = (String) getNodeService().getProperty(person,ContentModel.PROP_FIRSTNAME);
			String lastName = (String) getNodeService().getProperty(person,ContentModel.PROP_LASTNAME);
			String email = (String) getNodeService().getProperty(person,ContentModel.PROP_EMAIL);
			String ecasDomain = (String) getNodeService().getProperty(person, UserModel.PROP_DOMAIN);
			String moniker = (String) getNodeService().getProperty(person,UserModel.PROP_ECAS_USER_NAME);
			String profile = entry.getValue().getProfileDisplayName();
			UserRecord user = new UserRecord(userName , firstName , lastName,  email , ecasDomain, moniker, profile);
			result.add(user);
			
		}
		return result;
	}

private void writeXLS(List<UserRecord> userList, ServletOutputStream outStream) throws IOException {
		
		Workbook workbook = new HSSFWorkbook();
		
		Sheet sheet = workbook.createSheet("Members");
		
		Row titleRow = sheet.createRow(0);
		titleRow.createCell(0).setCellValue("User Name");
		titleRow.createCell(1).setCellValue("First Name");
		titleRow.createCell(2).setCellValue("Last Name");
		titleRow.createCell(3).setCellValue("Email");
		titleRow.createCell(4).setCellValue("ECAS Domain");
		titleRow.createCell(5).setCellValue("ECAS User Name");
		titleRow.createCell(6).setCellValue("Profile");
		
		int idx = 1;
		
		for (UserRecord user : userList) {
			Row row = sheet.createRow(idx);
			row.createCell(0).setCellValue(user.getUserName());
			row.createCell(1).setCellValue(user.getFirstName());
			row.createCell(2).setCellValue(user.getLastName());
			row.createCell(3).setCellValue(user.getEmail());
			row.createCell(4).setCellValue(user.getECASDomain());
			row.createCell(5).setCellValue(user.getECASMoniker());
			row.createCell(6).setCellValue(user.getProfile());
			idx ++;
		}
   
		workbook.write(outStream);
    }
	

	//***************************************************************
	//                                            GETTERS AND SETTERS
	//***************************************************************

	public CategoryHeadersBean getCategoryHeadersBean() {
		return categoryHeadersBean;
	}

	public void setCategoryHeadersBean(CategoryHeadersBean categoryHeadersBean) {
		this.categoryHeadersBean = categoryHeadersBean;
	}

	public String getSourceHeader() {
		return sourceHeader;
	}

	public void setSourceHeader(String sourceHeader) {
		if(isNotEmpty(sourceHeader)) {
			this.sourceHeader = sourceHeader;
		}
	}

	public String getSourceCategory() {
		return sourceCategory;
	}

	public void setSourceCategory(String sourceCategory) {
		if(isNotEmpty(sourceCategory)) {
			this.sourceCategory = sourceCategory;
		}
	}

	public String getSourceIg() {
		return sourceIg;
	}

	public void setSourceIg(String sourceIg) {
		if(isNotEmpty(sourceIg)) {
			this.sourceIg = sourceIg;
		}
	}

	public String getTargetHeader() {
		return targetHeader;
	}

	public void setTargetHeader(String targetHeader) {
		if(isNotEmpty(targetHeader)) {
			this.targetHeader = targetHeader;
		}
	}

	public String getTargetCategory() {
		return targetCategory;
	}

	public void setTargetCategory(String targetCategory) {
		if(isNotEmpty(targetCategory)) {
			this.targetCategory = targetCategory;
		}
	}
	

	public List getAllHeaders() {
		if(this.allHeaders == null) {
			this.allHeaders = getAllCategoryHeaders();
		}
		return this.allHeaders;
	}
	
	public List getSourceCategories() {
		if(this.getSourceHeader() != null) {
			return this.getCategoriesOfHeader(this.getSourceHeader());
		}
		else
		{
			return new ArrayList();
		}
	}

	public List getSourceIgs() {
		if(this.getSourceCategory() != null) {
			return this.getInterestGroupsOfCategory(this.getSourceCategory());
		}
		else
		{
			return new ArrayList();
		}
	}
	
	public List getTargetCategories() {
		if(this.getTargetHeader() != null) {
			return this.getCategoriesOfHeader(this.getTargetHeader());
		}
		else
		{
			return new ArrayList();
		}
	}

	public AuthorityService getAuthorityService() {
		return authorityService;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}
	
	/**
	 * @return the linksBusinessSrv
	 */
	protected LinksBusinessSrv getLinksBusinessSrv()
	{
		return getBusinessRegistry().getLinksBusinessSrv();
	}
	
	/**
	 * @return the personService
	 */
	protected final PersonService getPersonService()
	{
		if(personService == null)
    	{
			personService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getPersonService();
    	}
		return personService;
	}

	/**
	 * @param personService the personService to set
	 */
	public final void setPersonService(final PersonService personService)
	{
		this.personService = personService;
	}
	/**
	 * @return the UserService
	 */

}
