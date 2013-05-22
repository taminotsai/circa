/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.newsgroup;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.alfresco.service.Auditable;
import org.alfresco.service.cmr.repository.DuplicateChildNodeNameException;
import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Interface to manage post attachement.
 * 
 * <pre>
 * 		Ideally containers are TYPE_FORUM or TYPE_TOPIC and contents are TYPE_POST. But it is not required.
 * </pre>
 * 
 * @author Yanick Pignot
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * Commented the key parameter of the @Auditable annotation.
 * Commented the deprecated @PublicService annotation. 
 */
//@PublicService
public interface AttachementService
{

	/**
	 * Create the refered node (refered) in an hidden folder, and attach it to
	 * an existiong nodeRef (referer)
	 * 
	 * @param referer
	 * @param refered
	 * @param encoding
	 * @param mimetype
	 * @return the create refered nodeRef
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "referer", "referedFile", "encoding", "mimetype" })
	public abstract NodeRef attach(final NodeRef referer, final File referedFile, final String encoding, final String mimetype);

	/**
	 * Create the refered node (refered) in an hidden folder, and attach it to
	 * an existiong nodeRef (referer)
	 * 
	 * @param referer
	 * @param refered
	 * @param encoding
	 * @param mimetype
	 * @return the create refered nodeRef
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "referer", "referedIs", "name", "encoding", "mimetype" })
	public abstract NodeRef attach(final NodeRef referer, final InputStream referedIs, final String name, final String encoding,
			final String mimetype);

	/**
	 * Attach an existing nodeRef (refered) to another one (referer)
	 * 
	 * @param referer
	 * @param refered
	 * @return always the refered nodeRef
	 * @throws DuplicateChildNodeNameException
	 *             If the node is already attached
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "referer", "refered" })
	public abstract NodeRef attach(final NodeRef referer, final NodeRef refered) throws DuplicateChildNodeNameException;

	/**
	 * Get all attachements of a referer
	 * 
	 * @param referer
	 * @return
	 */
	public abstract List<NodeRef> getAttachements(final NodeRef referer);

	/**
	 * get if a node is an hidden attachement
	 * 
	 * @param refered
	 * @return
	 */
	public boolean isHiddenAttachement(final NodeRef refered);
}
