/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.manager;

import java.io.Serializable;

import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.web.wai.bean.navigation.WaiNavigator;


/**
 * Bean that manage the navigation beans. These beans must be an instance of WaiNavigator
 *
 * @see eu.cec.digit.circabc.web.wai.bean.navigation.WaiNavigator
 * @author yanick pignot
 */
public class NavigationState implements Serializable
{
	private static final long serialVersionUID = -6703095379617913245L;

	private WaiNavigator bean;
	private Node node;

	/**
	 * @param page
	 * @param currentBean
	 * @param currentNode
	 */
	public NavigationState(WaiNavigator currentBean, Node currentNode)
	{
		super();
		this.bean = currentBean;
		this.node = currentNode;

		currentBean.init(null);
	}

	/**
	 * @return the bean
	 */
	public WaiNavigator getBean()
	{
		return bean;
	}

	/**
	 * @return the node
	 */
	public Node getNode()
	{
		return node;
	}

	@Override
	public String toString()
	{
		return "" + this.bean.getClass().getSimpleName() + ":" + (node == null ? "null" : node.getId());
	}

	@Override
	public boolean equals(Object obj)
	{
		if(super.equals(obj))
		{
			return true;
		}
		else if(obj != null && obj instanceof NavigationState)
		{
			return obj.toString().equals(this.toString());
		}
		else
		{
			return false;
		}
	}
	@Override
	public int hashCode()
	{
		return this.toString().hashCode();
	}

}
