/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.alfresco.service.ServiceRegistry;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.jsf.FacesContextUtils;

import eu.cec.digit.circabc.business.api.BusinessRegistry;
import eu.cec.digit.circabc.service.CircabcServiceRegistry;

/**
 * Helper class for accessing Circabc and Alfresco Service utilities.
 *
 * @author Stephane Clinckart
 */
public class Services
{
    /** reference to the ServiceRegistry */
    private static CircabcServiceRegistry serviceRegistry = null;

    /** reference to the ServiceRegistry */
    private static ServiceRegistry alfrescoServiceRegistry = null;

    /** refernce to the BusinessRegistry */
    private static BusinessRegistry businessRegistry = null;

    private Services()
    {

    }


    /**
     * Return the Circabc Service Registry
     *
     * @param context
     *            Faces Context
     * @return the Service Registry
     */
    public static CircabcServiceRegistry getCircabcServiceRegistry(
            FacesContext context)
    {
        if (serviceRegistry == null)
        {
            serviceRegistry = (CircabcServiceRegistry) FacesContextUtils
                    .getRequiredWebApplicationContext(context).getBean(
                            CircabcServiceRegistry.CIRCABC_SERVICE_REGISTRY);
        }
        return serviceRegistry;
    }

    /**
     * Return the Repository Service Registry
     *
     * @param context
     *            Servlet Context
     * @return the Service Registry
     */
    public static CircabcServiceRegistry getCircabcServiceRegistry(ServletContext context)
    {
        if (serviceRegistry == null)
        {
            serviceRegistry = (CircabcServiceRegistry) WebApplicationContextUtils
                    .getRequiredWebApplicationContext(context).getBean(
                            CircabcServiceRegistry.CIRCABC_SERVICE_REGISTRY);
        }
        return serviceRegistry;
    }

    /**
     * Return the Native Service Registry provided by alfresco
     *
     * @param context
     *            Faces Context
     * @return the Native Service Registry provided by alfresco
     */
    public static ServiceRegistry getAlfrescoServiceRegistry(
            FacesContext context)
    {
        if (alfrescoServiceRegistry == null)
        {
        	alfrescoServiceRegistry = (ServiceRegistry) FacesContextUtils
                    .getRequiredWebApplicationContext(context).getBean(
                    		ServiceRegistry.SERVICE_REGISTRY);
        }
        return alfrescoServiceRegistry;
    }

    /**
     * Return the Native Service Registry provided by alfresco
     *
     * @param context
     *            Servlet Context
     * @return the Native Service Registry provided by alfresco
     */
    public static ServiceRegistry getAlfrescoServiceRegistry(ServletContext context)
    {
        if (alfrescoServiceRegistry == null)
        {
        	alfrescoServiceRegistry = (ServiceRegistry) WebApplicationContextUtils
                    .getRequiredWebApplicationContext(context).getBean(
                            ServiceRegistry.SERVICE_REGISTRY);
        }
        return alfrescoServiceRegistry;
    }

    /**
     * Return the Circabc Business Service Registry
     *
     * @param context
     *            Faces Context
     * @return the Service Registry
     */
    public static BusinessRegistry getBusinessRegistry(FacesContext context)
    {
        if (businessRegistry == null)
        {
        	businessRegistry = (BusinessRegistry) FacesContextUtils
                    .getRequiredWebApplicationContext(context).getBean(
                    		BusinessRegistry.BUSINESS_REGISTRY);
        }
        return businessRegistry;
    }

    /**
     * Return the Repository Service Registry
     *
     * @param context
     *            Servlet Context
     * @return the Service Registry
     */
    public static BusinessRegistry getBusinessRegistry(ServletContext context)
    {
        if (businessRegistry == null)
        {
        	businessRegistry = (BusinessRegistry) WebApplicationContextUtils
                    .getRequiredWebApplicationContext(context).getBean(
                    		BusinessRegistry.BUSINESS_REGISTRY);
        }
        return businessRegistry;
    }

}
