/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.bean;

import javax.faces.event.ActionEvent;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.web.bean.ErrorBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.error.CircabcRuntimeException;


/**
 * Bean extends the not management error management.
 *
 * @author Yanick Pignot.
 */
public class CircabcErrorBean extends ErrorBean
{
	private static final long serialVersionUID = 68736574648784143L;
	private static final Log logger = LogFactory.getLog(CircabcErrorBean.class);

	public static final String BEAN_NAME = "ErrorBean";
	public static final String LAST_ERROR_TIMESTAMP = "LastErrorTimeStamp";
	public static final long   MIN_MILLISEC_BETWEEN_TWO_ERRORS = 15000;
	public static final String MAIN_PAGE = "/jsp/extension/wai/error/error-wai.jsp";
	public static final String ERROR_REDIRECT_LOGOUT_PAGE = "/jsp/extension/wai/error/error-redirect-logout-wai.jsp";

	private boolean expanded;

	public void initError(ServletRequest request)
	{
		
		
		Throwable lastError = (Throwable)request.getAttribute("javax.servlet.error.exception");
		if (lastError == null)
		{
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpSession session = httpRequest.getSession();
		    ErrorBean errorBean = (ErrorBean)session.getAttribute(ErrorBean.ERROR_BEAN_NAME);
		    lastError = errorBean.getLastError();
		}
		setLastError(lastError);
		
        final String returnPage = (String)request.getAttribute("javax.servlet.error.request_uri");
		setReturnPage(returnPage);

        logger.error("Unexpected and not managed error for user " + AuthenticationUtil.getFullyAuthenticatedUser(), getLastError());

		expanded = false;
	}

	public String getExceptionClass()
	{
		if(getLastError() == null)
		{
			return "Internal Error";
		}
		else
		{
			return getLastError().getClass().getName();
		}
	}

	/**
	 * @return the expanded
	 */
	public boolean isExpanded()
	{
		return expanded;
	}

	/**
	 * @param expanded the expanded to set
	 */
	public void setExpanded(boolean expanded)
	{
		this.expanded = expanded;
	}


	/**
	 * @param action to perform to see the details
	 */
	public void expandDetails(ActionEvent event)
	{
		this.expanded = true;
	}

	/**
	 * @param action to perform to hide the details
	 */
	public void hideDetails(ActionEvent event)
	{
		this.expanded = false;
	}
	
	public boolean isCircabcRuntimeException()
	{
		return getLastError() instanceof CircabcRuntimeException ;
	}
	
	
	public String getLastCircabcErrorMessage ()
	{
		if ( getLastError() instanceof CircabcRuntimeException)
		{
			return getLastError().getMessage();
		}
		else
		{
			return "";
		}
	}

}
