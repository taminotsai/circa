/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.config.CircabcConfiguration;

/**
 * Helper class for the HttpClient
 *
 * @author Matthieu Sprunck
 */
public class HttpClientHelper
{
    /** The attribute name used to store the cookies into the session */
    private static final String ATT_COOKIES = "cookies";
    private static final String IPM_LOGIN = "ipm.login";
    private static final String IPM_PASSWORD = "ipm.password";

    /** The logger */
    private static final Log logger = LogFactory.getLog(HttpClientHelper.class);

    private static Properties props = CircabcConfiguration.getProperties();

    public static final int BUFFER_SIZE = 4096;

    /**
     * Calls the server with the given url and get the response as an input
     * stream.
     *
     * @param url
     *            The url to call
     * @return the resulting input stream
     */
    public static final  InputStream call(String url)
    {
        return call(null, url);
    }

    /**
     * Calls the server with the given url and get the response as an input
     * stream.
     *
     * @param session
     *            The session to get or store informations
     * @param url
     *            The url to call
     * @return the resulting input stream
     */
    public static final  InputStream call(HttpSession session, String url)
    {
        InputStream in = null;
        Cookie[] cookies = null;
        HttpClient client = new HttpClient();
        HttpMethod method = new PostMethod(url);
        String ipmLogin, ipmPassword;

        // Check if it's not the first call and
        // if some cookies must be added into the request
        if (session != null && session.getAttribute(ATT_COOKIES) != null)
        {
            cookies = (Cookie[]) session.getAttribute(ATT_COOKIES);
            // Set the state
            HttpState state = new HttpState();
            state.addCookies(cookies);
            client.setState(state);
        }

        // Call the server
        try
        {
            ipmLogin = props.getProperty(IPM_LOGIN, "circabc");
            ipmPassword = props.getProperty(IPM_PASSWORD, "changeit");

            if (logger.isDebugEnabled())
            {
                logger.debug("Call the server:\nURL:" + url);
            }

			client.getParams().setAuthenticationPreemptive(true);
			Credentials defaultcreds = new UsernamePasswordCredentials(ipmLogin, ipmPassword);
			client.getState().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM), defaultcreds);

            client.executeMethod(method);
            in = method.getResponseBodyAsStream();

            // Store the cookies
            if (session != null)
            {
                cookies = client.getState().getCookies();
                session.setAttribute(ATT_COOKIES, cookies);
                if (logger.isDebugEnabled())
                {
                    logger.debug("Cookies stored");
                }
            }
        } catch (HttpException e)
        {
            logger.error("Unable to get the InputStream from the server", e);
        } catch (IOException e)
        {
            logger.error("Read/Write error", e);
        }
        return in;
    }

    /**
     * Copy the contents of the given Reader to the given Writer.
     *
     * @param in
     *            the Reader to copy from
     * @param out
     *            the Writer to copy to
     * @return the number of characters copied
     * @throws IOException
     *             in case of I/O errors
     */
    public static final  int copy(Reader in, Writer out) throws IOException
    {
        int byteCount = 0;
        char[] buffer = new char[BUFFER_SIZE];
        int bytesRead = -1;
        while ((bytesRead = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, bytesRead);
            byteCount += bytesRead;
        }
        out.flush();
        return byteCount;
    }
}
