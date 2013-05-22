/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.model;

import org.alfresco.service.namespace.QName;

/**
 * It is the model for the survey specification
 *
 * @author yanick pignot
 */
public interface SurveyModel extends BaseCircabcModel
{
	 /** Circabc Survey namespace */
	 public static final String CIRCABC_SURVEY_MODEL_1_0_URI = CIRCABC_NAMESPACE + "/model/survey/1.0";

	 /** Circabc Model Prefix */
	 public static final String CIRCABC_SURVEY_MODEL_PREFIX = "su";

	 /** Survey Type name */
	 public static final QName TYPE_SURVEY_SPACE = QName.createQName(CIRCABC_SURVEY_MODEL_1_0_URI, "surveys");

}
