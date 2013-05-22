/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.common.tag.data;

import javax.faces.component.UIComponent;

import org.alfresco.web.ui.common.tag.HtmlComponentTag;

/**
 * @author Guillaume
 */
public class SortLinkTag extends HtmlComponentTag
{
	// ------------------------------------------------------------------------------
	// Component methods

	/**
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType()
	{
		return "eu.cec.digit.circabc.faces.SortLink";
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType()
	{
		return null;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release()
	{
		super.release();
		this.mode = null;
		this.value = null;
		this.label = null;
		this.tooltipAscending = null;
		this.tooltipDescending = null;
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	protected void setProperties(UIComponent component)
	{
		super.setProperties(component);
		setStringProperty(component, "value", this.value);
		setStringProperty(component, "label", this.label);
		setStringProperty(component, "mode", this.mode);
		setStringProperty(component, "tooltipAscending", this.tooltipAscending);
		setStringProperty(component, "tooltipDescending", this.tooltipDescending);
	}

	/**
	 * Set the value
	 *
	 * @param value
	 *            the value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * Set the sorting mode (see IDataContainer constants)
	 *
	 * @param mode
	 *            the sort mode
	 */
	public void setMode(String mode)
	{
		this.mode = mode;
	}

	/**
	 * Set the label
	 *
	 * @param label
	 *            the label
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * Set the tooltip for ascending order
	 *
	 * @param tooltipAscending
	 *            the tooltipAscending
	 */
	public void setTooltipAscending(String tooltipAscending)
	{
		this.tooltipAscending = tooltipAscending;
	}

	/**
	 * Set the tooltip for descending order
	 *
	 * @param tooltipDescending
	 *            the tooltipDescending
	 */
	public void setTooltipDescending(String tooltipDescending)
	{
		this.tooltipDescending = tooltipDescending;
	}

	/** the label */
	private String label;

	/** the value */
	private String value;

	/** the sorting mode */
	private String mode;

	/** the tooltip for ascending order */
	private String tooltipAscending;

	/** the tooltip for descending ordere */
	private String tooltipDescending;
}
