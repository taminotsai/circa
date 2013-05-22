/**
 * 
 */
package eu.cec.digit.circabc.repo.statistics.global;

import java.util.List;
import java.util.Set;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.repo.statistics.ig.IgDescriptor;

/**
 * @author beaurpi
 *
 */
public class CategoryDescriptor {
	
	private String name;
	private String title;
	private NodeRef ref;
	private List<IgDescriptor> listOfIgs;
	private Set<String> listOfAdmins;

	/**
	 * 
	 */
	public CategoryDescriptor() {
		
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the ref
	 */
	public NodeRef getRef() {
		return ref;
	}

	/**
	 * @param ref the ref to set
	 */
	public void setRef(NodeRef ref) {
		this.ref = ref;
	}

	/**
	 * @return the listOfIgs
	 */
	public List<IgDescriptor> getListOfIgs() {
		return listOfIgs;
	}

	/**
	 * @param listOfIgs the listOfIgs to set
	 */
	public void setListOfIgs(List<IgDescriptor> listOfIgs) {
		this.listOfIgs = listOfIgs;
	}

	/**
	 * @return the listOfAdmins
	 */
	public Set<String> getListOfAdmins() {
		return listOfAdmins;
	}

	/**
	 * @param listOfAdmins the listOfAdmins to set
	 */
	public void setListOfAdmins(Set<String> listOfAdmins) {
		this.listOfAdmins = listOfAdmins;
	}

}
