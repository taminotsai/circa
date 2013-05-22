/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.bean.navigation;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.bean.repository.MapNode;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.bean.repository.NodePropertyResolver;

import eu.cec.digit.circabc.service.CircabcServiceRegistry;
import eu.cec.digit.circabc.service.struct.ManagementService;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.servlet.SimpleDownloadServlet;

/**
 * The base node representation of each Circabc navigable node type
 *
 * @author yanick pignot
 * @author Stephane Clinckart
 */
public abstract class NavigableNode extends MapNode implements Comparable<NavigableNode>
{
	private static final long serialVersionUID = -8494188639400816217L;
	private static final String NAVIGABLE_PARENT_RESOLVER_NAME = "navigableParent";
	private static final String NAVIGABLE_CHILDS_RESOLVER_NAME = "navigableChilds";
	private static final String HAS_CHILDS_RESOLVER_NAME = "hasChilds";
	public static final String BEST_TITLE_RESOLVER_NAME = "bestTitle";
	public static final String TITLE_OR_NAME = "titleOrName";
	public static final String PARENT_ID = "parentId";

	private final NavigableNodeType navigableNodeType;
	private transient ManagementService managementService;
	private transient CircabcServiceRegistry circabcServiceRegistry;
	

	private static final String ALTERNATIVE_DOWNLOAD_URL = "alternativeDownloadUrl";
	private static final String ALTERNATIVE_BROWSE_URL = "alternativeBrowseUrl";
	
	public NavigableNode(final NodeRef nodeRef)
	{
		super(nodeRef);
		this.navigableNodeType = AspectResolver.resolveType(this);
		addResolvers();
	}

	protected NavigableNode(final NodeRef nodeRef, final NavigableNodeType trustedType)
	{
		super(nodeRef);
		this.navigableNodeType = trustedType;
		addResolvers();
	}

	public NavigableNode(final NodeRef nodeRef, final NodeService nodeService, final Map<QName, Serializable> props)
	{
		super(nodeRef, nodeService, props);
		this.navigableNodeType = AspectResolver.resolveType(this);
		addResolvers();
	}

	private void addResolvers()
	{
		this.addPropertyResolver(NAVIGABLE_PARENT_RESOLVER_NAME, resolverNavigableParent);
		this.addPropertyResolver(NAVIGABLE_CHILDS_RESOLVER_NAME, resolverNavigableChilds);
		this.addPropertyResolver(HAS_CHILDS_RESOLVER_NAME, resolverHasChilds);
		this.addPropertyResolver(BEST_TITLE_RESOLVER_NAME, resolverBestTitle);
		this.addPropertyResolver("selected", resolverSelected);
		this.addPropertyResolver(TITLE_OR_NAME , resolverBestTitle);
		this.addPropertyResolver(PARENT_ID, resolverParentId);
		this.addPropertyResolver(ALTERNATIVE_DOWNLOAD_URL, this.resolverAlternativeDownload);
		this.addPropertyResolver(ALTERNATIVE_BROWSE_URL, this.resolverAlternativeBrowse);
	}

	public String getTitle() {
		return (String) this.get(NavigableNode.BEST_TITLE_RESOLVER_NAME);
	}
	
	boolean selected = false; 
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {		
		this.selected = selected;
	}
	
	
	/**
	 * @return the navigableNodeType
	 */
	public NavigableNodeType getNavigableNodeType()
	{
		return navigableNodeType;
	}

	public ManagementService getManagementService()
	{
		if(managementService == null)
		{
			managementService = getCircabcServices().getManagementService();
		}
		return managementService;
	}

	public CircabcServiceRegistry getCircabcServices()
	{
		if(circabcServiceRegistry == null)
		{
			circabcServiceRegistry = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance());
		}
		return circabcServiceRegistry;
	}

	@Override
	public void reset()
	{
		super.reset();
		this.resetCache();
	}
	

	   public NodePropertyResolver resolverAlternativeDownload = new NodePropertyResolver() {
		private static final long serialVersionUID = -5583174774838740436L;

		public Object get(Node node) {
	         return SimpleDownloadServlet.generateDownloadURL(node.getNodeRef(), node.getName());
	      }
	   };

	   public NodePropertyResolver resolverAlternativeBrowse = new NodePropertyResolver() {

		   private static final long serialVersionUID = 9133660645868747741L;

			public Object get(Node node) {
		         return SimpleDownloadServlet.generateBrowseURL(node.getNodeRef(), node.getName());
		      }
		   };

	
	

	/**
	 *	Resolver to get the title or the name if not exists.
	 */
	public NodePropertyResolver resolverBestTitle = new NodePropertyResolver()
	{
	    private static final long serialVersionUID = -8543304786599792888L;

		public Object get(final Node node)
	    {
			final String title = (String) node.getProperties().get(ContentModel.PROP_TITLE.toString());

			if(title == null || title.trim().length() < 1)
			{
				return node.getName();
			}
			else
			{
				return title;
			}

	    }
	};
	
	
	public NodePropertyResolver resolverSelected = new NodePropertyResolver()
	{
		public Object get(final Node node)
	    {		
			return ((NavigableNode)node).selected;
	    }
	};	
	
	
	private NodePropertyResolver resolverParentId = new NodePropertyResolver() 
	{
		private static final long serialVersionUID = 9133660645868747741L;
		public Object get(final Node node) 
		{
			FacesContext fc = FacesContext.getCurrentInstance();
			NodeService nodeService = Services.getAlfrescoServiceRegistry(fc).getNodeService();
			ChildAssociationRef assoc = nodeService.getPrimaryParent(node.getNodeRef());
			return assoc.getParentRef().getId();
		}
	};

	private NodePropertyResolver resolverNavigableParent = new NodePropertyResolver() {

		private static final long serialVersionUID = -5583174774838740436L;

		public Object get(final Node node) {
	         return ((NavigableNode) node).getNavigableParent();
	      }
	   };

    private NodePropertyResolver resolverNavigableChilds = new NodePropertyResolver() {

    	private static final long serialVersionUID = 9133660645868747741L;

		public Object get(final Node node) {
		         return ((NavigableNode) node).getNavigableChilds();
		      }
		  };

    private NodePropertyResolver resolverHasChilds = new NodePropertyResolver() {

    	private static final long serialVersionUID = -7940464414075882820L;

		public Object get(final Node node) {
				final List<NavigableNode> childs = ((NavigableNode) node).getNavigableChilds();

		         return childs != null && childs.size() > 0;
		      }
		  };

	public abstract NavigableNode getNavigableParent();

	public abstract List<NavigableNode> getNavigableChilds();

	public abstract void resetCache();
	
	public int compareTo(NavigableNode o) {
		return this.getTitle().compareToIgnoreCase(o.getTitle());
	}
}
