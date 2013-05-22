package eu.cec.digit.circabc.web.wai.bean.navigation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.alfresco.repo.cache.SimpleCache;

import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;
import eu.cec.digit.circabc.web.bean.override.CircabcNavigationBean;
import eu.cec.digit.circabc.web.repository.CircabcRootNode;

public class CategoryHeadersBeanData
{
	private static final String HEADER_NODES_KEY = "headerNodes";
	private static final String CATEGORY_HEADERS_KEY = "categoryHeaders";
	private transient List<NavigableNode> headerNodes = null;
	private transient List<CategoryHeader> categoryHeaders = null;
	private SimpleCache<String, List<NavigableNode>> headerNodesCache;
	private SimpleCache<String, List<CategoryHeader>> categoryHeadersCache;

	/**
	 * Get the list of category spaces inside link to the sub category. The sub category choose change at each call to the function
	 *
	 * @return List<NavigableNode> List of category spaces inside the sub category
	 */
	public synchronized List<CategoryHeader> getCategoryHeaders()
	{
		categoryHeaders =  categoryHeadersCache.get(CATEGORY_HEADERS_KEY);
		if (categoryHeaders != null)
		{
			return categoryHeaders;
		}
		final List<NavigableNode> categoryHeadersHasNodes = getCategoryHeadersHasNode();
		if(categoryHeaders == null || categoryHeadersHasNodes.size() != categoryHeaders.size())
		{			
			categoryHeaders = new ArrayList<CategoryHeader>(categoryHeadersHasNodes.size());			
			for(final NavigableNode navigableNode : categoryHeadersHasNodes) {
				categoryHeaders.add(new CategoryHeader(navigableNode));
			}
			
		}
		categoryHeadersCache.put(CATEGORY_HEADERS_KEY, this.categoryHeaders);
		return categoryHeaders;
	}
	
	/**
	 * Get the list of category headers inside the category CircaBCHeader
	 *
	 * @return List<NavigableNode> List of category headers
	 */
	private List<NavigableNode> getCategoryHeadersHasNode()
	{
		headerNodes =  headerNodesCache.get(HEADER_NODES_KEY);
		if (headerNodes != null)
		{
			return headerNodes;
		}
		
		if(this.headerNodes == null)
		{
			final CircabcNavigationBean navigator = Beans.getWaiNavigator();
			
			final CircabcRootNode circabcHomeNode = navigator.getCircabcHomeNode();
			circabcHomeNode.resetCache();
			this.headerNodes = circabcHomeNode.getNavigableChilds();

			if(headerNodes == null)
			{
				this.headerNodes = Collections.<NavigableNode> emptyList();
			}
		}
		headerNodesCache.put(HEADER_NODES_KEY, this.headerNodes);
		return this.headerNodes;
	}
	
	public synchronized void reset()
	{
		headerNodes = null;
		categoryHeaders = null;
		headerNodesCache.clear();
		categoryHeadersCache.clear();
		
	}

	public void setHeaderNodesCache(SimpleCache<String, List<NavigableNode>> headerNodesCache)
	{
		this.headerNodesCache = headerNodesCache;
	}

	public SimpleCache<String, List<NavigableNode>> getHeaderNodesCache()
	{
		return headerNodesCache;
	}

	public void setCategoryHeadersCache(SimpleCache<String, List<CategoryHeader>> categoryHeadersCache)
	{
		this.categoryHeadersCache = categoryHeadersCache;
	}

	public SimpleCache<String, List<CategoryHeader>> getCategoryHeadersCache()
	{
		return categoryHeadersCache;
	}
}
