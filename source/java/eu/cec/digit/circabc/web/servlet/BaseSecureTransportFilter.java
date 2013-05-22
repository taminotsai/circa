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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.alfresco.service.ServiceRegistry;
import org.apache.commons.logging.Log;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 *
 * @author Slobodan Filipovic
 */
abstract public class BaseSecureTransportFilter implements Filter
{
	//use in production to get the url from the reverse proxy
	//it is saved in request header with name full-url
    private static final String FULL_URL = "full-url";

    private ServletContext context;


    /**
     * Gets the logger to use for this request.
     * <p>
     * This will show all debug entries from this class as though they
     * came from the subclass.
     *
     * @return The logger
     */
    protected abstract Log getLogger();

    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @SuppressWarnings("unchecked")
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException
    {
    	Log logger = getLogger();
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) res;

        String fullURL = httpReq.getHeader(FULL_URL);
        if (fullURL == null)
        {
        	StringBuffer fullURLBuffer = httpReq.getRequestURL();
        	String queryString =  httpReq.getQueryString();
        	if (  queryString != null  )
        	{
        		fullURLBuffer.append("?");
        		fullURLBuffer.append(queryString);
        	}
        	fullURL = fullURLBuffer.toString();

        }
        if (logger.isDebugEnabled())
        {
        	logger.debug("filtering " + httpReq.getRequestURI());
        	logger.debug(fullURL);
        }
        if (fullURL.startsWith("http:"))
		{
	    	if ( isSecureDocument(httpReq)  )
	    	{
	    		fullURL = fullURL.replace("http:", "https:");
	    		// TODO parameterize port numbers
	    		if (fullURL.contains(":8080"))
	        		fullURL = fullURL.replace(":8080", ":8443");
				httpRes.sendRedirect(fullURL);
	        	
	        	
	        }
	    	else
	    	{
	    		chain.doFilter(req, res);
	    	}

	    }
		else {
            chain.doFilter(req, res);
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
    	this.context = config.getServletContext();
    }
    /**
     * Return the ServiceRegistry helper instance
     *
     * @param sc      ServletContext
     *
     * @return ServiceRegistry
     */
    public static ServiceRegistry getServiceRegistry(ServletContext sc)
    {
       WebApplicationContext wc = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
       return (ServiceRegistry)wc.getBean(ServiceRegistry.SERVICE_REGISTRY);
    }


	abstract protected boolean isSecureDocument(HttpServletRequest httpReq) ;


	public ServletContext getServletContext() {
		return context;
	}
}
