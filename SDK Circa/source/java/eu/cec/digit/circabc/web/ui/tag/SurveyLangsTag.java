/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.ui.tag;

import javax.faces.component.UIComponent;

import org.alfresco.web.ui.common.tag.HtmlComponentTag;

/**
 * Displays the list of translations for a survey.
 * 
 * @author Matthieu Sprunck
 * @author Guillaume
 */
public class SurveyLangsTag extends HtmlComponentTag
{

	// ------------------------------------------------------------------------------
	// Component methods

	/**
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	@Override
	public String getComponentType()
	{
		return "eu.cec.digit.circabc.faces.SurveyLangs";
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	@Override
	public String getRendererType()
	{
		return null;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	@Override
	public void release()
	{
		super.release();
		this.value = null;
		this.wai = null;
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	@Override
	protected void setProperties(UIComponent component)
	{
		super.setProperties(component);
		setStringBindingProperty(component, ATTR_VALUE, this.value);
		setBooleanProperty(component, ATTR_WAI, this.wai);
	}

	// ------------------------------------------------------------------------------
	// Bean implementation

	/**
	 * Setter method for value
	 *
	 * @param value the value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * Setter method for wai status
	 *
	 * @param wai the wai status to set
	 */
	public void setWai(String wai)
	{
		this.wai = wai;
	}

	// ------------------------------------------------------------------------------
	// Data

	/** The variable's name of the attribute value */
	public static final String ATTR_VALUE = "value";

	/** The variable's name of the attribute value */
	public static final String ATTR_WAI = "wai";

	/** The value */
	private String value;

	/** The wai status */
	private String wai;
}
