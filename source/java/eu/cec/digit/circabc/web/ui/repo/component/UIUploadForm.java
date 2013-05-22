/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.component;

import java.io.IOException;

import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is to handle the upload form required by the uploadServlet. It ouputs the form skeleton and the return-page field.
 *
 * @author Guillaume
 */
public class UIUploadForm extends UICommand
{
	// ------------------------------------------------------------------------------
	// Component implementation

	/**
	 * Default Constructor
	 */
	public UIUploadForm()
	{
		// specifically set the renderer type to null to indicate to the framework that this component renders itself - there is no abstract renderer class
		setRendererType(null);
	}

	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	public String getFamily()
	{
		return "eu.cec.digit.circabc.faces.UploadForm";
		//return null;
	}

	/**
	 * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext context, Object state)
	{
		Object values[] = (Object[]) state;
		// standard component attributes are restored by the super class
		super.restoreState(context, values[0]);
		this.returnPage = (String) values[1];
		this.submitCallback = (String) values[2];
	}

	/**
	 * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext context)
	{
		Object values[] = new Object[3];
		values[0] = super.saveState(context);
		values[1] = this.returnPage;
		values[2] = this.submitCallback;
		return (values);
	}

	/**
	 * @see javax.faces.component.UIComponentBase#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(final FacesContext context) throws IOException
	{
		if (isRendered() == false)
		{
			return;
		}

		final ResponseWriter out = context.getResponseWriter();
		getReturnPage();
		getSubmitCallback();
		final String contextPath = context.getExternalContext().getRequestContextPath();

		if(logger.isInfoEnabled()) {
			logger.info("Output the form skeleton");
		}
		out.write("<f:verbatim><form name=\"upload-form\" acceptCharset=\"UTF-8\" method=\"post\" enctype=\"multipart/form-data\" action=\"");
        out.write(contextPath);
        out.write("/uploadFileServlet\">\n");
        if(logger.isInfoEnabled()) {
        	logger.info("Output the input returnPage");
        }

        out.write("<input type=\"hidden\" name=\"return-page\" value=\"");
        if (this.returnPage == null) {
        	out.write(contextPath + context.getExternalContext().getRequestServletPath() + context.getExternalContext().getRequestPathInfo());
        } else {
        	out.write(contextPath + context.getExternalContext().getRequestServletPath() + this.returnPage);
        }
        out.write("\">\n");

        if(this.submitCallback != null && this.submitCallback.length() > 0)
        {
        	out.write("<input type=\"hidden\" name=\"submit-callback\" value=\"");
        	out.write(submitCallback);
        	out.write("\">\n");
        }

        out.write("</f:verbatim>\n");
	}

	/**
	 * @see javax.faces.component.UIComponentBase#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(final FacesContext context) throws IOException
	{
		if (isRendered() == false)
		{
			return;
		}

		final ResponseWriter out = context.getResponseWriter();
        out.write("\n</form>");
	}

	/**
	 * Get the return page url
	 *
	 * @return String the return page url
	 */
	public String getReturnPage()
	{
		final ValueBinding vb = getValueBinding("returnPage");
		if (vb != null)
		{
			this.returnPage = (String) vb.getValue(getFacesContext());
		}

		return this.returnPage;
	}

	/**
	 * Set the return page url
	 *
	 * @param String the return page url
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
		final ValueBinding vb = getValueBinding("submitCallback");
		if (vb != null)
		{
			this.submitCallback = (String) vb.getValue(getFacesContext());
		}

		return this.submitCallback;
	}

	/**
	 * @param submitCallback the submitCallback to set
	 */
	public final void setSubmitCallback(String submitCallback)
	{
		this.submitCallback = submitCallback;
	}

	// ------------------------------------------------------------------------------
	// Private data

	private static final Log logger = LogFactory.getLog(UIUploadForm.class);

	/** The return page url */
	private String returnPage = null;
	private String submitCallback = null;
}
