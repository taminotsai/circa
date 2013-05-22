/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.common.tag;

import javax.faces.component.UIComponent;

import org.alfresco.web.ui.common.tag.HtmlComponentTag;

/**
 *
 * @author Guillaume
 *
 */
public class ErrorsTag extends HtmlComponentTag
{
	private String errorClass;

	private String infoClass;

	private String warnClass;
	
	private Boolean escape;

	/**
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType()
	{
		return "eu.cec.digit.circabc.faces.Errors";
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType()
	{
		return "eu.cec.digit.circabc.faces.ErrorsRenderer";
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	protected void setProperties(UIComponent component)
	{
		super.setProperties(component);

		setStringProperty(component, "errorClass", this.errorClass);
		setStringProperty(component, "infoClass", this.infoClass);
		setStringProperty(component, "warnClass", this.warnClass);
		setBooleanProperty(component, "escape", (this.escape != null ? this.escape.toString() : "true"));
	}

	/**
	 * The CSS class to use for error and fatal messages
	 *
	 * @param errorClass
	 *            The CSS class to use for error and fatal messages
	 */
	public void setErrorClass(String errorClass)
	{
		this.errorClass = errorClass;
	}

	/**
	 * The CSS class to use for info messages
	 *
	 * @param infoClass
	 *            The CSS class to use for info messages
	 */
	public void setInfoClass(String infoClass)
	{
		this.infoClass = infoClass;
	}

	/**
	 * The CSS class to use for warn messages
	 *
	 * @param warnClass
	 *            The CSS class to use for warn messages
	 */
	public void setWarnClass(String warnClass)
	{
		this.warnClass = warnClass;
	}

	/**
	 * @param escape the escape to set
	 */
	public void setEscape(Boolean escape) {
		this.escape = escape;
	}
}
