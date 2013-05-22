package eu.cec.digit.circabc.web.wai.dialog.ig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.repo.external.repositories.RegistrationRequest;
import eu.cec.digit.circabc.service.external.repositories.ExternalRepositoriesManagementService;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;
import eu.cec.digit.circabc.web.wai.wizard.users.InviteCircabcUsersWizard.UserGroupProfile;

/**
 * Dialog that backs the external repository publication process.
 * 
 * Page: publish-in-external-repositories.jsp
 * 
 * @author schwerr
 */
public class PublishInExternalRepositoriesDialog extends BaseWaiDialog {
	
	private static final long serialVersionUID = 474135268940475915L;
	
	private static final String INTERNAL_USER_NOT_SELECTED = "internal_user_not_selected";
	private static final String EXTERNAL_USER_NOT_SELECTED = "external_user_not_selected";
	private static final String INTERNAL_EXTERNAL_USER_NOT_SELECTED = "internal_external_user_not_selected";
	private static final String ERROR_SUBJECT_NULL = "error_subject_null";
	private static final String PUBLISH_SUCCESS = "publish_success";
	
	private static final Log logger = LogFactory.getLog(PublishInExternalRepositoriesDialog.class);
	
	
	private ExternalRepositoriesManagementService externalRepositoriesManagementService = null;
	
	private transient List<UserGroupProfile> selectedExternalRecipients = new ArrayList<UserGroupProfile>();
	private transient DataModel selectedExternalRecipientsDataModel = null;
	
	private transient List<UserGroupProfile> selectedInternalSenders = new ArrayList<UserGroupProfile>();
	private transient DataModel selectedInternalSendersDataModel = null;
	
	private SelectItem[] mailTypes = {new SelectItem("INTERNAL", "INTERNAL"), 
									new SelectItem("INCOMING", "INCOMING"), 
									new SelectItem("OUTGOING", "OUTGOING")};
	
	private transient String selectedMailType = "INTERNAL";
	private transient String subject = "";
	private transient String comment = "";
	
	
	/**
	 * @see eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog#init(java.util.Map)
	 */
	@Override
	public void init(Map<String, String> parameters) {
		super.init(parameters);
		subject = getActionNode().getName();
	}
	
    /**
     * Fills the picker with the recipients to chose from.
     * 
     * @param filterIndex
     * @param contains
     * @return
     */
    public SelectItem[] pickerInternalSendersCallback(final int filterIndex, final String contains) {
    	
    	List<String> senders = externalRepositoriesManagementService.
    									getInternalSenders(null, contains);
    	
    	List<SelectItem> result = new ArrayList<SelectItem>();
    	
    	for (String sender : senders) {
    		result.add(new SelectItem(sender));
    	}
    	
    	return result.toArray(new SelectItem[result.size()]);
    }
    
    /**
     * Fills the picker with the recipients to chose from.
     * 
     * @param filterIndex
     * @param contains
     * @return
     */
    public SelectItem[] pickerExternalRecipientsCallback(final int filterIndex, final String contains) {
    	
    	List<String> recipients = externalRepositoriesManagementService.
    							getExternalRecipients(null, contains, null);
    	
    	List<SelectItem> result = new ArrayList<SelectItem>();
    	
    	for (String recipient : recipients) {
    		result.add(new SelectItem(recipient));
    	}
    	
    	return result.toArray(new SelectItem[result.size()]);
    }
    
    /**
     * Returns the properties for the selected user JSF DataModel
     * 
     * @return JSF DataModel representing the selected user
     */
    public DataModel getSelectedInternalSendersDataModel() {
    	
        if (selectedInternalSendersDataModel == null) {
        	selectedInternalSendersDataModel = new ListDataModel();
        }
        
        selectedInternalSendersDataModel.setWrappedData(this.selectedInternalSenders);
        
        return selectedInternalSendersDataModel;
    }
    
    /**
     * Returns the properties for the selected user JSF DataModel
     * 
     * @return JSF DataModel representing the selected user
     */
    public DataModel getSelectedExternalRecipientsDataModel() {
    	
        if (selectedExternalRecipientsDataModel == null) {
            selectedExternalRecipientsDataModel = new ListDataModel();
        }
        
        selectedExternalRecipientsDataModel.setWrappedData(this.selectedExternalRecipients);
        
        return selectedExternalRecipientsDataModel;
    }
    
    /**
     * Adds the selected user to the list of senders.
     * 
     * @param event
     */
    public void addSelectedInternalSenders(final ActionEvent event) {
    	
    	final Object picker = event.getComponent().findComponent("internalSendersPicker");
        
    	String[] results = 
    			((org.alfresco.web.ui.common.component.UIGenericPicker) picker).
    				getSelectedResults();
    	
        if (results == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(translate(INTERNAL_USER_NOT_SELECTED)));
            return;
        }
        
        String userName = results[0];
        
        if (userName.contains("@")) {
        	userName = userName.substring(0, userName.indexOf('@'));
        }
        
        UserGroupProfile profile = new UserGroupProfile(userName, null, null);
        
        if (!containsProfile(selectedInternalSenders, profile)) {
        	selectedInternalSenders.add(profile);
        }
    }
    
    /**
     * Adds the selected user to the list of recipients.
     * 
     * @param event
     */
    public void addSelectedExternalRecipients(final ActionEvent event) {
    	
    	final Object picker = event.getComponent().findComponent("externalRecipientsPicker");
        
    	String[] results = 
    			((org.alfresco.web.ui.common.component.UIGenericPicker) picker).
    				getSelectedResults();
    	
        if (results == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(translate(EXTERNAL_USER_NOT_SELECTED)));
            return;
        }
        
        String userName = results[0];
        
        if (userName.contains("@")) {
        	userName = userName.substring(0, userName.indexOf('@'));
        }
        
        UserGroupProfile profile = new UserGroupProfile(userName, null, null);
        
        if (!containsProfile(selectedExternalRecipients, profile)) {
        	selectedExternalRecipients.add(profile);
        }
    }
    
    /**
     * Checks if the user is already added to the list.
     * 
     * @param users
     * @param profile
     * @return
     */
    private boolean containsProfile(List<UserGroupProfile> users, UserGroupProfile profile) {
    	for (UserGroupProfile user : users) {
    		if (user.getAuthority().equals(profile.getAuthority())) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Action handler called when the Remove button is pressed to remove a
     * user selection
     */
    public void removeSelectedInternalSender(final ActionEvent event) {
    	
        final UserGroupProfile wrapper = 
        		(UserGroupProfile) selectedInternalSendersDataModel.getRowData();
        
        if (wrapper != null) {
            selectedInternalSenders.remove(wrapper);
        }
    }
    
    /**
     * Action handler called when the Remove button is pressed to remove a
     * user selection
     */
    public void removeSelectedExternalRecipient(final ActionEvent event) {
    	
        final UserGroupProfile wrapper = 
        		(UserGroupProfile) selectedExternalRecipientsDataModel.getRowData();
        
        if (wrapper != null) {
            selectedExternalRecipients.remove(wrapper);
        }
    }
    
	/**
	 * Gets the value of the mailTypes
	 * 
	 * @return the mailTypes
	 */
	public SelectItem[] getMailTypes() {
		return mailTypes;
	}
	
	/**
	 * Gets the value of the selectedMailType
	 * 
	 * @return the selectedMailType
	 */
	public String getSelectedMailType() {
		return selectedMailType;
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
	 * Gets the value of the comment
	 * 
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * Sets the value of the comment
	 * 
	 * @param comment the comment to set.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * Sets the value of the selectedMailType
	 * 
	 * @param selectedMailType the selectedMailType to set.
	 */
	public void setSelectedMailType(String selectedMailType) {
		this.selectedMailType = selectedMailType;
	}
	
	/**
	 * @see eu.cec.digit.circabc.web.wai.dialog.WaiDialog#getPageIconAltText()
	 */
	@Override
	public String getPageIconAltText() {
		return translate("publish_in_external_repositories_dialog_description");
	}
	
	/**
	 * @see eu.cec.digit.circabc.web.wai.dialog.WaiDialog#getBrowserTitle()
	 */
	@Override
	public String getBrowserTitle() {
		return translate("publish_in_external_repositories_dialog_title");
	}
	
	/**
	 * @see org.alfresco.web.bean.dialog.BaseDialogBean#finishImpl(javax.faces.context.FacesContext, java.lang.String)
	 */
	@Override
	protected String finishImpl(FacesContext context, String outcome)
			throws Throwable {
		
		if (selectedInternalSenders.isEmpty() && selectedExternalRecipients.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(translate(INTERNAL_EXTERNAL_USER_NOT_SELECTED)));
            return null;
        }
		
		if (selectedInternalSenders.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(translate(INTERNAL_USER_NOT_SELECTED)));
            return null;
        }
		
		if (selectedExternalRecipients.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(translate(EXTERNAL_USER_NOT_SELECTED)));
            return null;
        }
		
		// Test parameters not null
		if (subject == null || subject.length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(translate(ERROR_SUBJECT_NULL)));
            return null;
		}
		if (comment == null) {
			comment = "";
		}
		
		// Fill the registration data
		RegistrationRequest registrationRequest = new RegistrationRequest();
		registrationRequest.setSubject(subject);
		registrationRequest.setMailType(selectedMailType);
		registrationRequest.setComments(comment);
		
		// Senders
		List<String> senders = new ArrayList<String>();
		for (int idxSU = 0; idxSU < selectedInternalSenders.size(); idxSU ++) {
			String userId = selectedInternalSenders.get(idxSU).getAuthority();
			int idx = userId.lastIndexOf("-");
			senders.add(userId.substring(idx + 2));
		}
		registrationRequest.setSenderIds(senders);
		
		// Recipients
		List<String> recipients = new ArrayList<String>();
		for (int idxSU = 0; idxSU < selectedExternalRecipients.size(); idxSU ++) {
			String userId = selectedExternalRecipients.get(idxSU).getAuthority();
			int idx = userId.lastIndexOf("-");
			recipients.add(userId.substring(idx + 2));
		}
		registrationRequest.setRecipientIds(recipients);
		
		// Publish
		String error = externalRepositoriesManagementService.publishDocument(
				ExternalRepositoriesManagementService.EXTERNAL_REPOSITORY_NAME, 
				getActionNode().getNodeRef().toString(), registrationRequest);
		
		if (error != null) {
			
			// If workflow fails
			if (ExternalRepositoriesManagementService.
					PUBLISH_SUCCESS_WORKFLOW_FAILURE.equals(error)) {
				clear();
				Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(error));
				return outcome;
			}
			
			// Another error, we can stay on the page
			String errorMsg = "";
			
			int idx = error.indexOf(" ");
			
			if (idx > 0) {
				errorMsg = error.substring(idx + 1);
				error = error.substring(0, idx);
			}
			
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(translate(error) + " " + errorMsg));
            
            return null;
		}
		
		clear();
		
		// Info message to inform that the document was published successfully
		Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(PUBLISH_SUCCESS));
		
		return outcome;
	}
	
	/**
	 * @see org.alfresco.web.bean.dialog.BaseDialogBean#cancel()
	 */
	@Override
	public String cancel() {
		clear();
		return super.cancel();
	}
	
	/**
	 * Clears the content of the input fields.
	 */
	private void clear() {
		selectedExternalRecipients.clear();
		selectedInternalSenders.clear();
		subject = "";
		comment = "";
	}
	
	/**
	 * Sets the value of the externalRepositoriesManagementService
	 * 
	 * @param externalRepositoriesManagementService the externalRepositoriesManagementService to set.
	 */
	public void setExternalRepositoriesManagementService(
			ExternalRepositoriesManagementService externalRepositoriesManagementService) {
		this.externalRepositoriesManagementService = externalRepositoriesManagementService;
	}
}
