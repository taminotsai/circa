package eu.cec.digit.circabc.web.wai.bean.navigation;

import java.util.List;

import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;
import eu.cec.digit.circabc.web.repository.CategoryHeaderNode;

public class CategoryHeader implements Comparable<CategoryHeader> {
	
	private CategoryHeaderNode categoryHeader;
	
	private List<NavigableNode> links = null;
	private List<NavigableNode> categories = null;
	
	public CategoryHeader(final NavigableNode categoryHeader) {
		this.categoryHeader = (CategoryHeaderNode)categoryHeader;
	}
	
	public NavigableNode getCategoryHeader() {
		return categoryHeader;
	}
	
	public List<NavigableNode> getCategories() {
		if(categories == null)
		{
			categories = this.categoryHeader.getNavigableChilds(); 
		}
		return categories;
	}
	
	public List<NavigableNode> getExternalLinks() {
		if(links == null)
		{
			links = this.categoryHeader.getExternalLinks(); 
		}
		return links;
	}
	
	public int getCategoriesSize() {
		return getCategories().size();
	}

	public int compareTo(CategoryHeader o) {
		NavigableNode a = this.getCategoryHeader();
		NavigableNode b = o.getCategoryHeader();
		if (a == null && b == null) {
			return 0;
		}
		
	    if (a == null) {
	        return 1;
	    }
	    
		if (b == null) {
			return -1;
		}		
		
		return a.getTitle().compareToIgnoreCase(b.getTitle());
	}
}