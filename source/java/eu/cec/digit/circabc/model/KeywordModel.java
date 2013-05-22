/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.model;

import org.alfresco.service.namespace.QName;

/**
 * It is the model for the keyword specification
 *
 * @author Yanick Pignot
 */
public interface KeywordModel extends BaseCircabcModel
{
	 /** Circabc Keywords namespace */
	 public static final String CIRCABC_KEYWORD_MODEL_1_0_URI = CIRCABC_NAMESPACE + "/model/keyword/1.0";

	 /** Circabc Keywords prefix */
	 public static final String CIRCABC_KEYWORD_MODEL_PREFIX = "kw";

	 /** Circabc Keywords root container */
	 public static final QName TYPE_KEYWORD_CONTAINER = QName.createQName(CIRCABC_KEYWORD_MODEL_1_0_URI, "keywordContainer");

	 /** Circabc Keywords element */
	 public static final QName TYPE_KEYWORD           = QName.createQName(CIRCABC_KEYWORD_MODEL_1_0_URI, "keyword");

	 /** Circabc Keywords association beetween the root container and the keyword elements */
	 public static final QName ASSOC_KEYWORDS = QName.createQName(CIRCABC_KEYWORD_MODEL_1_0_URI, "keywords");

	 /** Circabc Keywords association beetween the keyword elements and another keyword elements (Not used yet) */
	 public static final QName ASSOC_SUB_KEYWORDS = QName.createQName(CIRCABC_KEYWORD_MODEL_1_0_URI, "subkeywords");

	 /** Circabc keyword properties that define if the keyword is multilingal or not */
	 public static final QName PROP_TRANSLATED = QName.createQName(CIRCABC_KEYWORD_MODEL_1_0_URI, "translated");

}
