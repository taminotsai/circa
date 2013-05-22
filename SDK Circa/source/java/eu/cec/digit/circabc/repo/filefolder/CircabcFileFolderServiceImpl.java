package eu.cec.digit.circabc.repo.filefolder;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.alfresco.model.ContentModel;
import org.alfresco.model.ForumModel;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.Path;
import org.alfresco.service.cmr.search.LimitBy;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.AccessStatus;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.util.ISO9075;
import org.alfresco.web.bean.repository.Repository;

import eu.cec.digit.circabc.service.filefolder.CircabcFileFolderService;



public class CircabcFileFolderServiceImpl implements CircabcFileFolderService 
{
	private FileFolderService fileFolderService;
	private PermissionService permissionService;
	private SearchService searchService;
	private NamespaceService  namespaceService; 
	private NodeService nodeService;
 
	public List<FileInfo> list(NodeRef contextNodeRef)
	{
		
		final List<FileInfo> list = getFileFolderService().list(contextNodeRef);
		final List<FileInfo> result = new ArrayList<FileInfo>(list.size());
		for (FileInfo fileInfo : list)
		{
			final AccessStatus hasPermission = getPermissionService().hasPermission(fileInfo.getNodeRef(),PermissionService.READ_PROPERTIES);
			if (fileInfo.isFolder() )
			{
				if (hasPermission  != AccessStatus.ALLOWED)
				{
					if ( hasVisibleChildren(fileInfo.getNodeRef()))
					{
						result.add(fileInfo);
					}
				}
				else
				{
					result.add(fileInfo);
				}
			}
			else
			{
				if (hasPermission  == AccessStatus.ALLOWED)
				{
					result.add(fileInfo);
				}
			}
			
		}
		
		return result;
	}

	private boolean hasVisibleChildren(NodeRef nodeRef)
	{
		final SearchParameters sp = new SearchParameters();
		sp.setLanguage(SearchService.LANGUAGE_LUCENE);
		sp.setQuery(buildvisibleDocumentQuery(nodeRef));
		sp.addStore(Repository.getStoreRef());
		sp.setLimitBy(LimitBy.FINAL_SIZE);
		sp.setLimit(1);
		ResultSet result = null;
		boolean visibleChildren;
		try
		{
			result = getSearchService().query(sp);
			visibleChildren = ! result.getNodeRefs().isEmpty();
		}
		finally
		{
			if(result != null)
	        {
	            result.close();
	        }
		}
		return visibleChildren;
		
	}
	
	
	
	
	private String buildvisibleDocumentQuery(NodeRef nodeRef)
	{
		String query = "( PATH:\"" + getPathFromSpaceRef(nodeRef, true)+ "\"" + " )" ;
		query = query + "AND (( TYPE:\""+ ForumModel.TYPE_POST + "\" OR TYPE:\""+
	 			ForumModel.TYPE_FORUMS+"\") OR (TYPE:\""+
	 			ContentModel.TYPE_CONTENT+"\" OR TYPE:\""+
	 			ContentModel.TYPE_FOLDER+"\")) )";
		return query;
	}
	
	private String getPathFromSpaceRef(final NodeRef ref, boolean children)
	{
		final Path path = getNodeService().getPath(ref);
		final StringBuilder buf = new StringBuilder(64);
		String elementString;
		Path.Element element;
		ChildAssociationRef elementRef;
		Collection<?> prefixes;
		for (int i = 0; i < path.size(); i++)
		{
			elementString = "";
			element = path.get(i);
			if (element instanceof Path.ChildAssocElement)
			{
				elementRef = ((Path.ChildAssocElement) element).getRef();
				if (elementRef.getParentRef() != null)
				{
					prefixes = getNamespaceService().getPrefixes(elementRef.getQName().getNamespaceURI());
					if (prefixes.size() > 0)
					{
						elementString = '/' + (String) prefixes.iterator().next() + ':' + ISO9075.encode(elementRef.getQName().getLocalName());
					}
				}
			}

			buf.append(elementString);
		}
		if (children == true)
		{
			// append syntax to get all children of the path
			buf.append("//*");
		}
		else
		{
			// append syntax to just represent the path, not the children
			buf.append("/*");
		}

		return buf.toString();
	}

	public void setFileFolderService(FileFolderService fileFolderService)
	{
		this.fileFolderService = fileFolderService;
	}

	public FileFolderService getFileFolderService()
	{
		return fileFolderService;
	}

	public void setPermissionService(PermissionService permissionService)
	{
		this.permissionService = permissionService;
	}

	public PermissionService getPermissionService()
	{
		return permissionService;
	}

	public void setSearchService(SearchService searchService)
	{
		this.searchService = searchService;
	}

	public SearchService getSearchService()
	{
		return searchService;
	}

	public void setNamespaceService(NamespaceService namespaceService)
	{
		this.namespaceService = namespaceService;
	}

	public NamespaceService getNamespaceService()
	{
		return namespaceService;
	}

	public void setNodeService(NodeService nodeService)
	{
		this.nodeService = nodeService;
	}

	public NodeService getNodeService()
	{
		return nodeService;
	}



}
