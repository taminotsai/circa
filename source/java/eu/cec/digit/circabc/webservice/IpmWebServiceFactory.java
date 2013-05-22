/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.webservice;

import ipm.webservice.IpmServiceServiceLocator;
import ipm.webservice.IpmServiceSoapBindingStub;

import java.util.Properties;

import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.config.CircabcConfiguration;

/**
 * The factory which creates all web services concerning IPM. The location of
 * the IPM server to connect to is specified in the file
 * circabc-settings.properties located in /alfresco/extension.
 *
 * @author Matthieu Sprunck
 */
public class IpmWebServiceFactory
{
    /** Log */
    private static final Log logger = LogFactory.getLog(IpmWebServiceFactory.class);

    private static final String IPM_LOCATION = "ipm.location";
    private static final String IPM_LOGIN = "ipm.login";
    private static final String IPM_PASSWORD = "ipm.password";

    /** Default endpoint address * */
    private static final String DEFAULT_ENDPOINT_ADDRESS = "http://localhost:7001/ipm";

    /** Service addresses */
    private static final String IPM_SERVICE_ADDRESS = "/services/IpmService";

    /** */
    private static Properties props = null;

    /** Services */
    private static IpmServiceSoapBindingStub ipmService = null;
    
    
    static 
    {
    	String ipmLogin, ipmPassword;

        if (ipmService == null)
        {
            try
            {
                // Get the authentication service
                IpmServiceServiceLocator locator = new IpmServiceServiceLocator();
                locator.setIpmServiceEndpointAddress(getEndpointAddress()
                        + IPM_SERVICE_ADDRESS);
                if (logger.isDebugEnabled())
                {
                    logger.debug("Get the IPM webservice from the end point: "
                            + locator.getIpmServiceAddress());
                }
                ipmLogin = props.getProperty(IPM_LOGIN, "circabc");
                ipmPassword = props.getProperty(IPM_PASSWORD, "changeit");

                ipmService = (IpmServiceSoapBindingStub) locator.getIpmService();
                ipmService.setUsername(ipmLogin);
                ipmService.setPassword(ipmPassword);
            }
            catch (final ServiceException jre)
            {
                if (logger.isErrorEnabled() == true)
                {
                    if (jre.getLinkedCause() != null)
                    {
                    	if(logger.isErrorEnabled()) {
            				logger.error("Error", jre.getLinkedCause());
                    	}
                    } else {
                    	if(logger.isErrorEnabled()) {
            				logger.error("Error", jre);
                    	}
                    }
                }

                throw new RuntimeException(
                        "Error creating authentication service: "
                                + jre.getMessage(), jre);
            }

            // Time out after a minute
            ipmService.setTimeout(60000);
        }
    }
    

    /**
     * Gets the Circabc user service
     *
     * @return
     */
    public static IpmServiceSoapBindingStub getIpmService()
    {
        return ipmService;
    }

    /**
     * Gets the end point address from the properties file
     *
     * @return
     */
    private static String getEndpointAddress()
    {
        String endPoint = DEFAULT_ENDPOINT_ADDRESS;

        if (props == null)
        {
              props = CircabcConfiguration.getProperties();
        }
        endPoint = props.getProperty(IPM_LOCATION, DEFAULT_ENDPOINT_ADDRESS);

        if (logger.isDebugEnabled())
        {
            logger.debug("Using endpoint " + endPoint);
        }

        return endPoint;
    }
}
