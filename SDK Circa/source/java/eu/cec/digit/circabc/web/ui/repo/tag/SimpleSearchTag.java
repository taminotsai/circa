/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.tag;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;

import org.alfresco.web.ui.common.tag.HtmlComponentTag;

/**
 * @author Guillaume
 */
public class SimpleSearchTag extends HtmlComponentTag
{
	/**
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType()
	{
		return "eu.cec.digit.circabc.faces.SimpleSearch";
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType()
	{
		// self rendering component
		return null;
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	protected void setProperties(UIComponent component)
	{
		super.setProperties(component);

		setActionListenerProperty((UICommand) component, this.actionListener);
		setStringProperty(component, "label", this.label);
		setStringProperty(component, "labelAltText", this.labelAltText);
		setStringProperty(component, "button", this.button);
		setStringProperty(component, "buttonAltText", this.buttonAltText);
	}

	/**
	 * @see org.alfresco.web.ui.common.tag.HtmlComponentTag#release()
	 */
	public void release()
	{
		super.release();

		this.actionListener = null;
		this.label = null;
		this.labelAltText = null;
		this.button = null;
		this.buttonAltText = null;
	}

	/**
	 * Set the actionListener
	 *
	 * @param actionListener
	 *            the actionListener
	 */
	public void setActionListener(String actionListener)
	{
		this.actionListener = actionListener;
	}

	/**
	 * Set the label of the Box
	 *
	 * @param label
	 *            the label
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * Set the alt text of the image at the left of the label
	 *
	 * @param labelAltText
	 *            the labelAltText
	 */
	public void setLabelAltText(String labelAltText)
	{
		this.labelAltText = labelAltText;
	}

	/**
	 * Set the button of the Box
	 *
	 * @param button
	 *            the button
	 */
	public void setButton(String button)
	{
		this.button = button;
	}

	/**
	 * Set the alt text of the button for the search action
	 *
	 * @param buttonAltText
	 *            the buttonAltText
	 */
	public void setButtonAltText(String buttonAltText)
	{
		this.buttonAltText = buttonAltText;
	}

	/** The label of the Box */
	private String label;

	/** The alt text of the image at the left of the label */
	private String labelAltText;

	/** The label of the button */
	private String button;

	/** The alt text of the image for the search action */
	private String buttonAltText;

	/** the actionListener */
	private String actionListener;
}
