package eu.cec.digit.circabc.repo.external.repositories;

import java.util.List;

/**
 * Encapsulates the properties to add a workflow task that will be used to 
 * classify the document.
 * 
 * @author schwerr
 */
public class AssignmentTaskRequest {
	
	private String assignmentUserName = null;
	private String documentId = null;
	private List<String> assigneeIds = null;
	
	/**
	 * Gets the value of the assignmentUserName
	 * 
	 * @return the assignmentUserName
	 */
	public String getAssignmentUserName() {
		return assignmentUserName;
	}
	/**
	 * Sets the value of the assignmentUserName
	 * 
	 * @param assignmentUserName the assignmentUserName to set.
	 */
	public void setAssignmentUserName(String assignmentUserName) {
		this.assignmentUserName = assignmentUserName;
	}
	/**
	 * Gets the value of the documentId
	 * 
	 * @return the documentId
	 */
	public String getDocumentId() {
		return documentId;
	}
	/**
	 * Sets the value of the documentId
	 * 
	 * @param documentId the documentId to set.
	 */
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	/**
	 * Gets the value of the assigneeIds
	 * 
	 * @return the assigneeIds
	 */
	public List<String> getAssigneeIds() {
		return assigneeIds;
	}
	/**
	 * Sets the value of the assigneeIds
	 * 
	 * @param assigneeIds the assigneeIds to set.
	 */
	public void setAssigneeIds(List<String> assigneeIds) {
		this.assigneeIds = assigneeIds;
	}
}
