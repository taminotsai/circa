/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.alfresco.web.app.servlet.BaseServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.web.bean.surveys.SurveysBean;

/**
 * When the user submit an IPM survey, the request is caught by this
 * filter to get all parameters which are the answers to the survey.
 * These parameters are set in the SurveysBean backed bean.
 *
 * @author Matthieu Sprunck
 * @author Guillaume
 */
public class EncodeSurveyFilter implements Filter
{
    /** The logger class* */
    private static final Log logger = LogFactory
            .getLog(EncodeSurveyFilter.class);

    /** The SurveysBean back bean name */
    private final static String SURVEYS_BEAN = "SurveysBean";

    /** Path to survey jsp file */
    private final static String ENCODE_PAGE = "/jsp/extension/surveys/survey.jsp";

    /** Path to survey jsp wai file */
    private final static String ENCODE_PAGE_WAI = "/jsp/extension/wai/navigation/surveys/survey.jsp";


    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @SuppressWarnings("unchecked")
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) res;

        if (logger.isDebugEnabled())
        {
            logger.debug("filtering " + httpReq.getRequestURI());
        }

        SurveysBean surveysBean = (SurveysBean) httpReq.getSession().getAttribute(SURVEYS_BEAN);

        surveysBean.setParameters(httpReq.getParameterMap());

        // redirect to the encode page
        if (surveysBean.isInWAI()) {
        	// TODO CircabcBrowseBean.clickSurvey
        	httpRes.sendRedirect(httpReq.getContextPath() + BaseServlet.FACES_SERVLET + ENCODE_PAGE_WAI);
        } else {
        	httpRes.sendRedirect(httpReq.getContextPath() + BaseServlet.FACES_SERVLET + ENCODE_PAGE);
        }
    }

    /**
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy()
    {
       // nothing to do
    }

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig config) throws ServletException
    {
        // nothing to do
    }
}
