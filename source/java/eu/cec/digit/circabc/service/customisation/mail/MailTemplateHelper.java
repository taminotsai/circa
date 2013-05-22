/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.customisation.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.springframework.extensions.surf.util.I18NUtil;

/**
 * @author Yanick Pignot
 */
abstract class MailTemplateHelper
{
	private MailTemplateHelper(){};

	/**
	 * For template editing needs, this method translate a template
	 *
	 * @param template
	 * @return
	 */
	/*package*/ static String escapeInstructions(final String template, final Locale locale)
    {
        final StringBuilder builder = new StringBuilder(template);
        final StringBuilder result = new StringBuilder("");

        boolean inInstruction = false;
        StringBuilder currentInstruction = null;

        for(int x = 0; x < builder.length(); ++x)
        {
            final char charat = builder.charAt(x);

            if(inInstruction)
            {
                if(charat == '}')
                {
                    currentInstruction.append(charat);
                    result.append(escapeSingleInstruction(currentInstruction.toString(), locale));
                    currentInstruction = null;
                    inInstruction = false;
                }
                else
                {
                    currentInstruction.append(charat);
                }
            }
            else
            {
                if(charat == '$')
                {
                    inInstruction = true;
                    currentInstruction = new StringBuilder();
                    currentInstruction.append(charat);
                }
                else
                {
                    result.append(charat);
                }
            }
        }

        return result.toString();
    }

    /**
     * @param currentInstruction
     * @return
     */
    private static String escapeSingleInstruction(String currentInstruction, final Locale locale)
    {
        currentInstruction = currentInstruction.replace(" ", "").replace("\n", "").replace("\r", "").replace("\t", "");
        if(currentInstruction.contains("${formatMessage("))
        {
            final int i18nKeyStart = currentInstruction.indexOf('\"') + 1;
            final int i18nKeyEnd = currentInstruction.indexOf('\"', i18nKeyStart);

            // remove first '(' and last ')}'
            final String params = currentInstruction.substring(i18nKeyEnd + 1, currentInstruction.length() - 2);

            return I18NUtil.getMessage(currentInstruction.substring(i18nKeyStart, i18nKeyEnd), locale, buildParams(params));
        }
        else
        {
            return escape(currentInstruction);
        }
    }

    /**
     * @param currentInstruction
     * @return
     */
    private static String escape(String currentInstruction)
    {
        return "${r\"" + currentInstruction + "\"}";
    }

    /**
     * @param paramString
     * @return
     */
    private static Object[] buildParams(final String paramString)
    {
    	final List<String> paramList = new ArrayList<String>();

    	final StringTokenizer tokens = new StringTokenizer(paramString, ",", false);

    	String token;
    	while(tokens.hasMoreTokens())
    	{
    		token = tokens.nextToken();

    		if(token.length() > 0)
    		{
    			if(token.contains("("))
    			{
    				// manage case a paramater that looks like: method(param, param2)
    				while(token.contains(")") == false)
    				{
    					token = token + tokens.nextToken();
    				}

    			}
    			paramList.add(escape("${" + token + "}"));
    		}
    	}
    	return paramList.toArray();
    }
}
