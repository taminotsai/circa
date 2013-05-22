/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.common.tag;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;

import org.alfresco.web.ui.common.tag.HtmlComponentTag;

/**
 * @author Guillaume
 */
public class ActionLinkTag extends HtmlComponentTag
{
	/**
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	@Override
	public String getComponentType()
	{
		return "eu.cec.digit.circabc.faces.ActionLink";
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	@Override
	public String getRendererType()
	{
		return "eu.cec.digit.circabc.faces.ActionLinkRenderer";
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	@Override
	protected void setProperties(UIComponent component)
	{
		super.setProperties(component);

		setActionProperty((UICommand) component, this.action);
		setActionListenerProperty((UICommand) component, this.actionListener);
		setStringProperty(component, "image", this.image);
		setBooleanProperty(component, "showLink", this.showLink);
		setStringProperty(component, "href", this.href);
		setStringProperty(component, "value", this.value);
		setStringProperty(component, "target", this.target);
		setStringProperty(component, "anchor", this.anchor);
		setBooleanProperty(component, "immediate", this.immediate);
		setBooleanProperty(component, "noDisplay", this.noDisplay);
		setIntProperty(component, "padding", this.padding);
		setStringProperty(component, "onclick", this.onclick);
	}

	/**
	 * @see org.alfresco.web.ui.common.tag.HtmlComponentTag#release()
	 */
	@Override
	public void release()
	{
		super.release();
		this.value = null;
		this.href = null;
		this.action = null;
		this.actionListener = null;
		this.image = null;
		this.showLink = null;
		this.anchor = null;
		this.target = null;
		this.immediate = null;
		this.noDisplay = null;
		this.padding = null;
		this.onclick = null;
	}

	/**
	 * Set the value
	 *
	 * @param value the value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * Set the href to use instead of a JSF action
	 *
	 * @param href the href
	 */
	public void setHref(String href)
	{
		this.href = href;
	}

	/**
	 * Set the action
	 *
	 * @param action the action
	 */
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * Set the actionListener
	 *
	 * @param actionListener the actionListener
	 */
	public void setActionListener(String actionListener)
	{
		this.actionListener = actionListener;
	}

	/**
	 * Set the anchor
	 *
	 * @param anchor the anchor
	 */
	public void setAnchor(String anchor)
	{
		this.anchor = anchor;
	}

	/**
	 * Set the image
	 *
	 * @param image the image
	 */
	public void setImage(String image)
	{
		this.image = image;
	}

	/**
	 * Set the showLink
	 *
	 * @param showLink the showLink
	 */
	public void setShowLink(String showLink)
	{
		this.showLink = showLink;
	}

	/**
	 * Set the target
	 *
	 * @param target the target
	 */
	public void setTarget(String target)
	{
		this.target = target;
	}

	/**
	 * Set the immediate
	 *
	 * @param immediate immediate
	 */
	public void setImmediate(String immediate)
	{
		this.immediate = immediate;
	}

	/**
	 * Set the noDisplay
	 *
	 * @param noDisplay noDisplay
	 */
	public void setNoDisplay(String noDisplay)
	{
		this.noDisplay = noDisplay;
	}

	/**
	 * @return the padding
	 */
	public final String getPadding()
    {
		return padding;
	}

	/**
	 * @param padding the padding to set
	 */
    public final void setPadding(String padding)
	{
		this.padding = padding;
	}

    /**
	 * @return the onclick
	 */
	public final String getOnclick()
    {
		return onclick;
	}

	/**
	 * @param onclick the onclick to set
	 */
    public final void setOnclick(String onclick)
	{
		this.onclick = onclick;
	}

	/** the target */
	private String target;

	/** the value (text to display) */
	private String value;

	/** the href link */
	private String href;

	/** the action */
	private String action;

	/** the actionListener */
	private String actionListener;

	/** the image */
	private String image;

	/** the showLink boolean */
	private String showLink;

	/** the anchor name */
	private String anchor;

	/** the immediate flag */
	private String immediate;

	/** the noDisplay flag */
	private String noDisplay;

	/** the padding flag */
	private String padding;

	/** the onclick flag */
	private String onclick;

}
