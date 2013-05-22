package eu.cec.digit.circabc.repo.external.repositories;

import java.util.List;

/**
 * Encapsulates the properties to register a document.
 * 
 * @author schwerr
 */
public class RegistrationRequest {
	
	private String registrationUserName = null;
	private String subject = null;
	private String comments = null;
	private List<String> senderIds = null;
	private List<String> recipientIds = null;
	private String mailType = null;
	private String uploadedContentId = null;
	private String fileName = null;
	private List<String> translationIds = null;
	
	/**
	 * Gets the value of the registrationUserName
	 * 
	 * @return the registrationUserName
	 */
	public String getRegistrationUserName() {
		return registrationUserName;
	}
	/**
	 * Sets the value of the registrationUserName
	 * 
	 * @param registrationUserName the registrationUserName to set.
	 */
	public void setRegistrationUserName(String registrationUserName) {
		this.registrationUserName = registrationUserName;
	}
	/**
	 * Gets the value of the subject
	 * 
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * Sets the value of the subject
	 * 
	 * @param subject the subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * Gets the value of the comments
	 * 
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * Sets the value of the comments
	 * 
	 * @param comments the comments to set.
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * Gets the value of the recipientIds
	 * 
	 * @return the recipientIds
	 */
	public List<String> getRecipientIds() {
		return recipientIds;
	}
	/**
	 * Sets the value of the recipientIds
	 * 
	 * @param recipientIds the recipientIds to set.
	 */
	public void setRecipientIds(List<String> recipientIds) {
		this.recipientIds = recipientIds;
	}
	/**
	 * Gets the value of the mailType
	 * 
	 * @return the mailType
	 */
	public String getMailType() {
		return mailType;
	}
	/**
	 * Sets the value of the mailType
	 * 
	 * @param mailType the mailType to set.
	 */
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}
	/**
	 * Gets the value of the senderIds
	 * 
	 * @return the senderIds
	 */
	public List<String> getSenderIds() {
		return senderIds;
	}
	/**
	 * Sets the value of the senderIds
	 * 
	 * @param senderIds the senderIds to set.
	 */
	public void setSenderIds(List<String> senderIds) {
		this.senderIds = senderIds;
	}
	/**
	 * Gets the value of the uploadedContentId
	 * 
	 * @return the uploadedContentId
	 */
	public String getUploadedContentId() {
		return uploadedContentId;
	}
	/**
	 * Sets the value of the uploadedContentId
	 * 
	 * @param uploadedContentId the uploadedContentId to set.
	 */
	public void setUploadedContentId(String uploadedContentId) {
		this.uploadedContentId = uploadedContentId;
	}
	/**
	 * Gets the value of the fileName
	 * 
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * Sets the value of the fileName
	 * 
	 * @param fileName the fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * Gets the value of the translationIds
	 * 
	 * @return the translationIds
	 */
	public List<String> getTranslationIds() {
		return translationIds;
	}
	/**
	 * Sets the value of the translationIds
	 * 
	 * @param translationIds the translationIds to set.
	 */
	public void setTranslationIds(List<String> translationIds) {
		this.translationIds = translationIds;
	}
}
