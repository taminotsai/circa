/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.repository;

import java.util.ArrayList;
import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.bean.repository.NodePropertyResolver;

import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;

/**
 * Wrap a The root circabc node for the Client Side.
 *
 * @author Yanick Pignot
 */
public class CircabcRootNode extends NavigableNode
{
	private static final long serialVersionUID = 4245045930595760474L;

	/** the list of categoryHeaders */
	private List<NavigableNode> categoryHeadersNode  = null;

	public CircabcRootNode(final NodeRef nodeRef)
	{
		super(nodeRef);

		if (!getNavigableNodeType().equals(NavigableNodeType.CIRCABC_ROOT))
        {
           throw new IllegalArgumentException("NodeRef must be a Circabc Root and have the " + NavigableNodeType.CIRCABC_ROOT.getComparatorQName() + " aspect applied.");
        }

		this.addPropertyResolver("rootCategoryHeader", resolverRootCategoryHeader);

	}

	@Override
	public List<NavigableNode> getNavigableChilds()
	{
		if(categoryHeadersNode == null)
		{
			final List<NodeRef> categoryHeadersNodeRef = getManagementService().getExistingCategoryHeaders();

			categoryHeadersNode = new ArrayList<NavigableNode>(categoryHeadersNodeRef.size());

			for(final NodeRef cat : categoryHeadersNodeRef)
			{
				categoryHeadersNode.add(new CategoryHeaderNode(cat, this));
			}
		}
		return categoryHeadersNode;
	}

	@Override
	public NavigableNode getNavigableParent()
	{
		// no node above manageable by circabc
		return null;
	}

	@Override
	public void resetCache()
	{
		categoryHeadersNode = null;
	}

	private NodePropertyResolver resolverRootCategoryHeader = new NodePropertyResolver() {

		private static final long serialVersionUID = 9182503427802227850L;

		public Object get(final Node node) {
	    	  return getManagementService().getRootCategoryHeader();
	      }
	   };


}
