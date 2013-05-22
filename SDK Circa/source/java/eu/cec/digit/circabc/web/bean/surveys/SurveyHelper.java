/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.bean.surveys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Helper to convert data from IPM webservice to local object. 
 * 
 * @author Matthieu Sprunck
 */
public class SurveyHelper
{
    /**
     * Transforms a Survey coming from the IPM webservice to the Survey object
     * used in Circabc
     * 
     * @param ipmSurveys
     *            An array of Survey coming from IPM
     * @return a list of circabc Survey
     */
    @SuppressWarnings("unchecked")
    public static List<Survey> transform(
            ipm.webservice.generic.Survey[] ipmSurveys)
    {
        
        if (ipmSurveys == null) {
            return Collections.EMPTY_LIST;
        }
        
        List<Survey> result = new ArrayList<Survey>();
        
        Survey survey = null;
        for (ipm.webservice.generic.Survey is : ipmSurveys)
        {
            survey = new Survey(is.getShortName(), is.getSubject(), is
                    .getStatus(), is.getStartDate(), is.getCloseDate(), Arrays
                    .asList(is.getTranslations()), is.getPivotLang());
            result.add(survey);
        }
        return result;
    }
}
