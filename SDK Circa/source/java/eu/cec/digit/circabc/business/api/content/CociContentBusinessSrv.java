/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.api.content;

import java.io.File;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Business service to check in, check out, update, lock a document.
 *
 * @author Yanick Pignot
 */
public interface CociContentBusinessSrv
{


	/**
	 * Check out a file in the same folder
	 *
	 * @param nodeRef			The content to lock
	 * @return					The working copy reference
	 */
	public NodeRef checkOut(final NodeRef nodeRef);

	/**
	 * Check out a file in the same folder in a workflow action
	 *
	 * @param nodeRef			The content to lock
	 * @return					The working copy reference
	 */
	public NodeRef checkOutForWorkflow(final NodeRef nodeRef, final String workflowTaskId);

	/**
	 * Undo / cancel a node being checked out.
	 *
	 * @param workingCopyRef			The working copy
	 * @return							The original document
	 */
	public NodeRef cancelCheckOut(final NodeRef workingCopyRef);

	/**
	 * Check in a document with the content of the working copy node
	 *
	 * @param workingCopy				The working copy document noderef
	 * @param minor						If the new version is minor (true, 1.0 -> 1.1) or major (false, 1.0 -> 2.0)
	 * @param versionNote				The version note
	 * @param keepCheckOut				Keep the document checked out
	 * @return 							The original document (locked if keepCheckedOut)
	 */
	public NodeRef checkIn(final NodeRef workingCopy, final boolean minor, final String versionNote, boolean keepCheckOut);

	/**
	 * Check in a document with the content of a given file
	 *
	 * @param workingCopy				The working copy document noderef
	 * @param minor						If the new version is minor (true, 1.0 -> 1.1) or major (false, 1.0 -> 2.0)
	 * @param versionNote				The version note
	 * @param keepCheckOut				Keep the document checked out
	 * @param file						The file that update the content
	 * @param uploadedfilename			Filename of original file that update content
	 * @return 							The original document (locked if keepCheckedOut)
	 */
	
	public NodeRef checkIn(final NodeRef workingCopy, final boolean minor, final String versionNote, boolean keepCheckOut, final File file,String uploadedfilename);

	/**
	 * Check in a document with the content of a given file
	 *
	 * @param workingCopy				The working copy document noderef
	 * @param minor						If the new version is minor (true, 1.0 -> 1.1) or major (false, 1.0 -> 2.0)
	 * @param versionNote				The version note
	 * @param keepCheckOut				Keep the document checked out
	 * @param file						The file that update the content
	 * @param disableNotification		If the notifications are disabled or not
	 * @param uploadedfilename			Filename of original file that update content 
	 * @return 							The original document (locked if keepCheckedOut)
	 */
	public NodeRef checkIn(final NodeRef workingCopy, final boolean minor, final String versionNote, boolean keepCheckOut, final File file, boolean disableNotification,String uploadedfilename);

	/**
	 * Lock a given node
	 *
	 * @param nodeRef
	 */
	public void lock(final NodeRef nodeRef);

	/**
	 * Unlock a given node
	 *
	 * @param nodeRef
	 */
	public void unlock(final NodeRef nodeRef);
	/**
	 * Update a document with a given file
	 *
	 * @param document								The document to update
	 * @param file									The file where is located the new content
	 * @param disableNotification					Disable notification or not
	 * @param uploadedfilename						Filename of original file that update content 
	 */
	public void update(final NodeRef document, final File file, final boolean disableNotification, String uploadedfilename) ;

	/**
	 * Update a document with a given file
	 *
	 * @param document								The document to update
	 * @param file									The file where is located the new content
	 * @param uploadedfilename						Filename of original file that update content 
	 */
	public void update(final NodeRef document, final File file,String uploadedfilename) ;

	/**
	 * Return the working copy of the given locked document
	 *
	 * @param lockedRef
	 * @return
	 */
	public NodeRef getWorkingCopy(final NodeRef lockedRef);

	/**
	 * Return the original document of a working copy
	 *
	 * @param workingCopyRef
	 * @return
	 */
	public NodeRef getWorkingCopyOf(final NodeRef workingCopyRef);

}
