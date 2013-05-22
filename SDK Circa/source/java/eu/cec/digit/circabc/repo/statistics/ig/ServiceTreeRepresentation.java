/**
 * 
 */
package eu.cec.digit.circabc.repo.statistics.ig;


/**
 * @author beaurpi
 * this class helps to represent one service in a tree structure 
 */
public class ServiceTreeRepresentation {
	
	private String name;
	private Child child;
	
	public ServiceTreeRepresentation() {
		
	}
	
	public ServiceTreeRepresentation(String name) {
		this.name=name;
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
	 * @return the children
	 */
	public Child getChild() {
		return child;
	}

	/**
	 * @param children the children to set
	 */
	public void setChild(Child child) {
		this.child = child;
	}

}
