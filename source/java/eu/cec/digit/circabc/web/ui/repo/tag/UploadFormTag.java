/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.tag;

import javax.faces.component.UIComponent;

import org.alfresco.web.ui.common.tag.HtmlComponentTag;

/**
 * This is to handle the upload form required by the uploadServlet
 *
 * @author Guillaume
 */
public class UploadFormTag extends HtmlComponentTag
{
	/**
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType()
	{
		return "eu.cec.digit.circabc.faces.UploadForm";
	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType()
	{
		return "eu.cec.digit.circabc.faces.UploadFormRenderer";

	}

	/**
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	protected void setProperties(final UIComponent component)
	{
		super.setProperties(component);

		setStringProperty(component, "returnPage", this.returnPage);
		setStringProperty(component, "submitCallback", this.submitCallback);
	}

	/**
	 * @see org.alfresco.web.ui.common.tag.HtmlComponentTag#release()
	 */
	public void release()
	{
		super.release();

		this.returnPage = null;
	}


	/**
	 * Set the return page url
	 *
	 * @param returnPage the returnPage
	 */
	public void setReturnPage(final String returnPage)
	{
		this.returnPage = returnPage;
	}

	/**
	 * @return the submitCallback
	 */
	public final String getSubmitCallback()
	{
		return submitCallback;
	}

	/**
	 * @param submitCallback the submitCallback to set
	 */
	public final void setSubmitCallback(String submitCallback)
	{
		this.submitCallback = submitCallback;
	}


	/** The return page url */
	private String returnPage = null;
	/** What to do after comit (require callback with parameter of type 'FileBean) */
	private String submitCallback = null;

}
