/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.support;

import java.util.List;

import org.alfresco.service.NotAuditable;
import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.repo.support.SupportContact;
import eu.cec.digit.circabc.repo.support.SupportTypes;

/**
 * Interface to manage support capabilities
 * 
 * @author Stephane Clinckart
 * @author Pierre Beauregard
 */

public interface SupportService
{

	/**
	 * check if the user is from the support
	 * 
	 * @param user
	 * @return
	 */
	@NotAuditable
	public boolean isUserFromSupport(final String user);

	@NotAuditable
	public void setSupportGroupName(final String supportGroupName);
	
	public List<SupportContact> getAllSupportContacts();
	
	public SupportContact getContactById(String id);

	public Boolean sendSupportRequest(String subject, String description, SupportTypes byId, String currentUserName, SupportContact contact, NodeRef actionNodeRef);
	
	public Boolean sendSupportRequestAsGuest(String subject, String description, SupportTypes byId, String mailAddress, SupportContact contact, NodeRef actionNodeRef);
}
