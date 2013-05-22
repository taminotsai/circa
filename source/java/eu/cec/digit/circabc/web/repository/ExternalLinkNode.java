package eu.cec.digit.circabc.web.repository;

import java.util.List;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;

@SuppressWarnings("unchecked")
public class ExternalLinkNode extends NavigableNode 
{
	private static final long serialVersionUID = 4487878958844970845L;
	private CategoryHeaderNode categoryHeaderNode = null;
	
	public ExternalLinkNode(NodeRef nodeRef) 
	{
		this(nodeRef, null);
	}
	
	public ExternalLinkNode(final NodeRef nodeRef, final CategoryHeaderNode categoryHeaderNode)
	{
		super(nodeRef);
		this.categoryHeaderNode = categoryHeaderNode;
	}

	@Override
	public NavigableNode getNavigableParent()
	{
		if(categoryHeaderNode == null)
		{
			final List<NodeRef> cats = (List<NodeRef>) getProperties().get(ContentModel.PROP_CATEGORIES.toString());
			if(cats == null || cats.size() < 1)
			{
				throw new IllegalStateException("All the Categories MUST be linked to a Category Header." + this.getName());
			}
			else
			{
				// get first categoryHeader
				this.categoryHeaderNode = new CategoryHeaderNode(cats.get(0), null);
			}
		}

		return categoryHeaderNode;
	}

	@Override
	public List<NavigableNode> getNavigableChilds() {
		return null;
	}

	@Override
	public void resetCache() 
	{
		categoryHeaderNode = null;
	}
}
