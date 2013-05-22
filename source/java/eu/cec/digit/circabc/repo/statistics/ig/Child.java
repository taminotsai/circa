/**
 * 
 */
package eu.cec.digit.circabc.repo.statistics.ig;

import java.util.ArrayList;
import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * @author beaurpi
 *
 */
public class Child {

	private String name;
	private NodeRef node;
	private List<Child> childrenContainer;
	
	/**
	 * Empty constructor
	 */
	public Child() {
		this.childrenContainer=new ArrayList<Child>();
	}
	
	/***
	 * complete constructor
	 * @param name
	 * @param node
	 * @param childrenContainer
	 */
	public Child(String name, NodeRef node , List<Child> childrenContainer) {
		this.name=name;
		this.node=node;
		if(childrenContainer==null)
		{
			this.childrenContainer=new ArrayList<Child>();
		}
		else
		{
			this.childrenContainer=childrenContainer;
		}
		
	}

	/**
	 * @return the children
	 */
	public List<Child> getChildren() {
		return childrenContainer;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<Child> children) {
		this.childrenContainer = children;
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
	 * @return the node
	 */
	public NodeRef getNode() {
		return node;
	}

	/**
	 * @param node the node to set
	 */
	public void setNode(NodeRef node) {
		this.node = node;
	}

	public Boolean isLeaf(){
		return ((this.childrenContainer == null) || (this.childrenContainer.size()==0)) ? true : false ;
	}
	
}
