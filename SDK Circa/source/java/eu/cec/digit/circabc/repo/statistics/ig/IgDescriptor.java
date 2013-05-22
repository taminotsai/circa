/**
 * 
 */
package eu.cec.digit.circabc.repo.statistics.ig;

import java.util.Set;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * @author beaurpi
 *
 */
public class IgDescriptor {
	
	private String name;
	private String title;
	private NodeRef ref;
	private Set<String> setOfLeaders;

	/**
	 * 
	 */
	public IgDescriptor() {
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
	 * @return the setOfLeaders
	 */
	public Set<String> getSetOfLeaders() {
		return setOfLeaders;
	}

	/**
	 * @param setOfLeaders the setOfLeaders to set
	 */
	public void setSetOfLeaders(Set<String> setOfLeaders) {
		this.setOfLeaders = setOfLeaders;
	}
	
	

}
