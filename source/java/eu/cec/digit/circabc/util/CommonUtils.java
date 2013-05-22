/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

/**
 * @author sprunma
 */
public class CommonUtils
{
	private static final Log logger = LogFactory.getLog(CommonUtils.class);

    public static final String LANGUAGES_FILE = "alfresco/extension/config/languages.xml";

    public static final String VISUAL_USER_DOMAIN_SEPARATOR = "@";

    public static List langs;
    
    static 
    {
    	if (langs == null || langs.isEmpty())
        {
            Digester digester = new Digester();
            digester.setValidating(false);

            digester.addObjectCreate("languages", ArrayList.class);
            digester.addObjectCreate("languages/language", Language.class);
            digester.addBeanPropertySetter("languages/language/label", "label");
            digester.addBeanPropertySetter("languages/language/code", "code");
            digester.addCallMethod("languages/language/available",
                    "setAvailable", 1, new Class[] { Boolean.class });
            digester.addCallParam("languages/language/available", 0);
            digester.addBeanPropertySetter("languages/language/order", "order");
            digester.addSetNext("languages/language", "add");

            try
            {
                langs = (ArrayList<Language>) digester.parse(CommonUtils.class
                        .getClassLoader().getResourceAsStream(LANGUAGES_FILE));
                // Sort the list according to the standard order
                Collections.sort(langs);
            } catch (IOException ioe)
            {
                logger.error("Unable to read the file languages.xml", ioe);
                langs = Collections.EMPTY_LIST;
            } catch (SAXException e)
            {
                logger.error("The file languages.xml is malformed", e);
                langs = Collections.EMPTY_LIST;
            }
        }
    }

    /**
     * Gets a list of all {@link Language} of the European Commission in the
     * right order.
     *
     * @return a list of language
     */
    @SuppressWarnings("unchecked")
    public static List<Language> getLanguages()
    {
        return langs;
    }
}
