/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action;

import java.io.IOException;

import org.alfresco.service.cmr.repository.ContentReader;
import org.springframework.core.io.InputStreamSource;

/**
 * @author Ph Dubois
 * @author Roy Wetherall
 *
 * 26-juin-07 - 14:29:46
 */
public class InputSourceWrapper implements InputStreamSource
{
    ContentReader cr = null;

    /**
     * constructor.
     * @param cr ContentReader
     */
    public InputSourceWrapper(final ContentReader cr) {
        this.cr = cr;
    }


    /**
     * @see org.springframework.core.io.InputStreamSource#getInputStream()
     */
    public java.io.InputStream getInputStream() throws IOException
    {
        if (cr.exists()) {
            if (cr.isClosed()) {
                cr = cr.getReader();
            }
            return cr.getContentInputStream();
        }
        return null;
    }
}
