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
 * @author Stephane Clinckart
 */
public class CategoryListTag extends HtmlComponentTag {
	/**
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType() {
		return "eu.cec.digit.circabc.faces.CategoryList";
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType() {
		return null;
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	protected void setProperties(final UIComponent component) {
		super.setProperties(component);		
		setStringBindingProperty(component, "value", this.value);
		setStringBindingProperty(component, "chooseHeader", this.chooseHeader);
		setBooleanProperty(component, "displayCategories", this.displayCategories);
	}

	/**
	 * @see org.alfresco.web.ui.common.tag.HtmlComponentTag#release()
	 */
	public void release() {
		super.release();
		this.value = null;
		this.chooseHeader = null;
		this.displayCategories = null;
	}



	/**
	 * Set the value (ie the navigation list)
	 *
	 * @param value
	 *            the value (ie the navigation list)
	 */
	public void setValue(final String value) {
		this.value = value;
	}
	
	public void setChooseHeader(final String chooseHeader) {
		this.chooseHeader = chooseHeader;
	}
	
	public void setDisplayCategories(final String displayCategories) {
		this.displayCategories = displayCategories;
	}

	/** The value (ie the navigation list) */
	private String value;
	
	private String chooseHeader;
	
	/**
	 * Define if the Categories List has to be displayed or not.
	 */
	private String displayCategories;

}
