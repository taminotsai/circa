/**
 * 
 */
package eu.cec.digit.circabc.service.user;

import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import eu.cec.digit.circabc.repo.user.InvalidBulkImportFileFormatException;
import eu.cec.digit.circabc.service.profile.Profile;

/**
 * @author beaurpi
 *
 */
public interface BulkUserImportService {

	/**
	 * List members of one Interest group
	 * @param igRef
	 * @return
	 */
	public List<BulkImportUserData> listMembers(NodeRef igRef, Boolean igNameAsProfile);
	
	/**
	 * to download current work
	 * @param model
	 * @return XLS workbook
	 */
	public HSSFWorkbook saveWork(List<BulkImportUserData> model);
	
	/**
	 * to download current work
	 * @param fileName 
	 * @param workbook
	 * @return model
	 * @throws InvalidBulkImportFileFormatException 
	 */
	public List<BulkImportUserData> loadWork(HSSFWorkbook book, String fileName) throws InvalidBulkImportFileFormatException;
	
	/**
	 * Add list of user to model, this method simply check if user is not present twice. If yes only first is taken in account
	 * @param model
	 * @param newValues
	 */
	public void addAll(List<BulkImportUserData> model, List<BulkImportUserData> newValues);

	/**
	 * This method should be called in order to update list of users. According to the to helpers, we will create new access profiles in the target Interest Group.
	 * @param model
	 * @param createIgProfileHelper
	 * @param createDepartmentNumberProfileHelper
	 * @param profilesToBeCreated
	 */
	public void parseProfilesToBeCreated(List<BulkImportUserData> model, Boolean createIgProfileHelper,	Boolean createDepartmentNumberProfileHelper, List<String> profilesToBeCreated);
	
	/**
	 * return the list of profile for the specified group
	 * @param igRef
	 * @return
	 */
	public List<Profile> listGroupProfiles(NodeRef igRef);
	
	/**
	 * final step of this interface, it will invite all people in the target group
	 * @param model
	 * @param igRef
	 * @param igProfiles
	 */
	public void inviteUsers(List<BulkImportUserData> model, NodeRef igRef, Map<String, String> igProfiles, Boolean notify);
}
