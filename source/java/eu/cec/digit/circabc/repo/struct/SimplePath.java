/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.struct;

import java.util.List;
import java.util.Set;

import javax.jcr.PathNotFoundException;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.namespace.RegexQNamePattern;
import org.alfresco.util.ParameterCheck;

import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.model.EventModel;


/**
 *	Use for managing simple 'circabc like' path. ie: /Circabc/Category/My Interest group/Library/My document.pdf
 *
 * @author Yanick Pignot
 */
public class SimplePath
{

	public static final char PATH_SEPARATOR = '/';

	private NodeRef nodeRef;
	private String name;
	private SimplePath parent;

	/*package*/ SimplePath(final NodeService nodeService, final NodeRef forcedParent, final NodeRef ref) throws PathNotFoundException
	{
		ParameterCheck.mandatory("NodeService", nodeService);
		ParameterCheck.mandatory("Node Reference", ref);

		final QName type = nodeService.getType(ref);
		final Set<QName> aspects = nodeService.getAspects(ref);

		ChildAssociationRef primaryParent = null;
		NodeRef primaryParentRef = null;
		this.nodeRef = ref;

		if(forcedParent != null)
		{
			this.parent = new SimplePath(nodeService, forcedParent);
		}
		else if(aspects.contains(CircabcModel.ASPECT_CIRCABC_ROOT))
		{
			this.parent = null;
		}
		else if(type.equals(ContentModel.TYPE_MULTILINGUAL_CONTAINER))
		{
			// Get first translation
	        final List<ChildAssociationRef> assocRefs = nodeService.getChildAssocs(
	        		ref,
	                ContentModel.ASSOC_MULTILINGUAL_CHILD,
	                RegexQNamePattern.MATCH_ALL);

	        if(assocRefs.size() == 0)
	        {
	        	this.parent = null;
	        }
	        else
	        {
	        	final NodeRef translationRef = assocRefs.get(0).getChildRef();
	        	final NodeRef spaceRef = nodeService.getPrimaryParent(translationRef).getParentRef();
				this.parent = new SimplePath(nodeService, spaceRef);
	        }
		}
		else if((primaryParent = nodeService.getPrimaryParent(ref)) != null && (primaryParentRef = primaryParent.getParentRef()) != null)
		{
			parent = new SimplePath(nodeService, primaryParentRef);
		}
		else
		{
			throw new PathNotFoundException("The node is not managed by Circabc");
		}

		if(EventModel.TYPE_EVENT.equals(type))
		{
			// TODO add a name to the event
			name = (String) nodeService.getProperty(ref, ContentModel.PROP_TITLE);
		}
		else if(CircabcModel.TYPE_DIRECTORY_SERVICE.equals(type))
		{
			// TODO add a name to the directory root
			name = "Directory";
		}
		else
		{
			name = (String) nodeService.getProperty(ref, ContentModel.PROP_NAME);
		}
	}

	public SimplePath(final NodeService nodeService, final NodeRef ref) throws PathNotFoundException
	{
		this(nodeService, null, ref);
	}


	public NodeRef getNodeRef()
	{
		return this.nodeRef;
	}

	public SimplePath getParent()
	{
		return this.parent;
	}

	public String getName()
	{
		return this.name;
	}

	@Override
	public String toString()
	{
		return ((parent != null) ? parent.toString() : "") + PATH_SEPARATOR + getName();
	}

}
