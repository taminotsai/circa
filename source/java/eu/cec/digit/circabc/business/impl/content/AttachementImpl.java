/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.business.impl.content;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;

import eu.cec.digit.circabc.business.api.content.Attachement;
import eu.cec.digit.circabc.model.DocumentModel;

/**
 * @author Yanick Pignot
 *
 */
public class AttachementImpl implements Attachement, Serializable
{

	/** */
	private static final long serialVersionUID = 9009856942455481374L;
	private final NodeRef referer;
	private final NodeRef refered;

	private final AttachementType type;
	private final String name;
	private final String title;

	/**
	 * @param referer
	 * @param refered
	 */
	public AttachementImpl(final NodeRef referer, final NodeRef refered, final NodeService nodeService)
	{
		super();
		this.referer = referer;
		this.refered = refered;

		final Map<QName, Serializable> props = nodeService.getProperties(refered);
		name = (String) props.get(ContentModel.PROP_NAME);
		title = (String) props.get(ContentModel.PROP_TITLE);

		final QName nodeType = nodeService.getType(refered);

		if(DocumentModel.TYPE_HIDDEN_ATTACHEMENT_CONTENT.equals(nodeType))
		{
			type = AttachementType.HIDDEN_FILE;
		}
		else
		{
			type = AttachementType.REPO_LINK;
		}
	}

	public AttachementType geType()
	{
		return type;
	}

	public NodeRef getAttachedOn()
	{
		return referer;
	}

	public String getName()
	{
		return name;
	}

	public NodeRef getNodeRef()
	{
		return refered;
	}

	public String getTitle()
	{
		if(title != null && title.length() > 0)
		{
			return title;
		}
		else
		{
			return getName();
		}
	}
}
