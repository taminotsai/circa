/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.tag;

import javax.faces.component.UIComponent;

import org.alfresco.web.ui.repo.tag.ItemSelectorTag;

/**
 * @author Slobodan Filipovic
 *
 * Add new parameter rootNode
 *
 */
public class NodeSelectorTag extends ItemSelectorTag {


	   /** Root node for selection */
	   private String rootNode;
	   private String showContents;
	   private String pathLabel;
	   private String pathErrorMessage;


	   /**
	    * @see javax.faces.webapp.UIComponentTag#getRendererType()
	    */
	   public String getRendererType()
	   {
	      return null;
	   }

	   /**
	    * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	    */
	   protected void setProperties(UIComponent component)
	   {
	      super.setProperties(component);
	      setStringBindingProperty(component, "rootNode", this.rootNode);
	      setBooleanProperty(component, "showContents", this.showContents);
	      setStringBindingProperty(component, "pathLabel", this.pathLabel);
	      setStringBindingProperty(component, "pathErrorMessage", this.pathErrorMessage);

	   }

	   /**
	    * @see org.alfresco.web.ui.common.tag.HtmlComponentTag#release()
	    */
	   public void release()
	   {
	      super.release();

	      this.rootNode = null;
	      this.showContents = null;
	      this.pathLabel = null;
	      this.pathErrorMessage= null;
	   }

	   /**
	    * Sets the root node of so user cqn not brozse above root node
	    * @param rootNode The id of the root node item
	    */
	   public void setRootNode(String rootNode)
	   {
	      this.rootNode = rootNode;
	   }

	   /**
	    * Sets if the contents must be displayed or not
	    * @param showContents
	    */
	   public void setShowContents(String showContents)
	   {
		   this.showContents = showContents;
	   }

	   /**
	    * @see javax.faces.webapp.UIComponentTag#getComponentType()
	    */
	   	@Override
	   	public String getComponentType()
	   	{
	   		return "eu.cec.digit.circabc.faces.NodeSelector";
	   	}

	/**
	 * @param pathLabel the pathLabel to set
	 */
	public void setPathLabel(String pathLabel)
	{
		this.pathLabel = pathLabel;
	}

	/**
	 * @return the pathLabel
	 */
	public String getPathLabel()
	{
		return pathLabel;
	}

	/**
	 * @param pathErrorMessage the pathErrorMessage to set
	 */
	public void setPathErrorMessage(String pathErrorMessage)
	{
		this.pathErrorMessage = pathErrorMessage;
	}

	/**
	 * @return the pathErrorMessage
	 */
	public String getPathErrorMessage()
	{
		return pathErrorMessage;
	}


}
