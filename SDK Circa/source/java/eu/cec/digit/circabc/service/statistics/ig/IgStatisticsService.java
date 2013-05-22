/**
 * 
 */
package eu.cec.digit.circabc.service.statistics.ig;

import java.io.Serializable;
import java.util.List;

import org.alfresco.service.PublicService;
import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.repo.log.ActivityCountDAO;
import eu.cec.digit.circabc.repo.statistics.ig.ServiceTreeRepresentation;

/**
 * 
 * @author beaurpi
 *
 */
@PublicService
public interface IgStatisticsService {
	
	/***
	 * @param igRoot
	 * @return the number of invited users in InterestGroup
	 */
	public Integer getNumberOfUsers(NodeRef igRoot);
	
	/***
	 * @param igRoot
	 * @return the number of document stored in the library
	 */
	public Integer getNumberOfLibraryDocuments(NodeRef igRoot);
	
	/***
	 * @param igRoot
	 * @return the number of Spaces stored in the library
	 */
	public Integer getNumberOfLibrarySpaces(NodeRef igRoot);
	
	/***
	 * @param igRoot
	 * @return the number of document stored in the library
	 */
	public Integer getNumberOfInformationDocuments(NodeRef igRoot);
	
	/***
	 * @param igRoot
	 * @return the number of Spaces stored in the library
	 */
	public Integer getNumberOfInformationSpaces(NodeRef igRoot);
	
	/***
	 * @param igRoot
	 * @return the title of the group.
	 */
	public String getIGTitle(NodeRef igRoot);
	
	/***
	 * @param igRoot
	 * @return the created date of Interest group
	 */
	public Serializable getIGCreationDate(NodeRef igRoot);
	
	/***
	 * 
	 * @param igRoot
	 * @return the number of Meeting objects in this IG
	 */
	public Integer getNumbetOfMeetings(NodeRef igRoot);
	
	/***
	 * 
	 * @param igRoot
	 * @return the number of Event objects in this IG
	 */
	public Integer getNumbetOfEvents(NodeRef igRoot);
	
	/***
	 * 
	 * @param igRoot
	 * @return the number of forums in this IG
	 */
	public Integer getNumberOfForums(NodeRef igRoot);
	
	/***
	 * 
	 * @param igRoot
	 * @return the number of topics in this IG
	 */
	public Integer getNumberOfTopics(NodeRef igRoot);
	
	/***
	 * 
	 * @param igRoot
	 * @return the number of topics in this IG
	 */
	public Integer getNumberOfPosts(NodeRef igRoot);
	
	/***
	 * 
	 * @param igRoot
	 * @return the total size of content in Library
	 */
	public Long getContentSizeOfLibrary(NodeRef igRoot);
	
	/***
	 * 
	 * @param igRoot
	 * @return the total size of content in Library
	 */
	public Long getContentSizeOfInformation(NodeRef igRoot);
	
	/***
	 * 
	 * @param igRoot
	 * @return the Tree structure of the library service
	 */
	public  ServiceTreeRepresentation getLibraryStructure(NodeRef igRoot);
	
	/***
	 * 
	 * @param igRoot
	 * @return the Tree structure of the information service
	 */
	public  ServiceTreeRepresentation getInformationStructure(NodeRef igRoot);

	/***
	 * 
	 * @param currentNode
	 * @return the Tree structure of the newsgroup service
	 */
	public ServiceTreeRepresentation getNewsgroupsStructure(
			NodeRef currentNode);
	
	/***
	 * get the list from audit database
	 * @param igRoot
	 * @return
	 */
	public List<ActivityCountDAO> getListOfActivityCount(NodeRef igRoot);
}
