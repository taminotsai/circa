/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.model;

import org.alfresco.service.namespace.QName;

/**
 * It is the model for the dossier specification
 *
 * @author Slobodan Filipovic
 */
public interface DossierModel extends BaseCircabcModel
{
	 /** Circabc Dossier namespace */
	 public static final String CIRCABC_DOSSIER_MODEL_1_0_URI = CIRCABC_NAMESPACE + "/model/dossier/1.0";

	 /** Circabc Model Prefix */
	 public static final String CIRCABC_DOSSIER_MODEL_PREFIX = "do";

	 /** Dossier Type name */
	 public static final QName TYPE_DOSSIER_SPACE = QName.createQName(CIRCABC_DOSSIER_MODEL_1_0_URI, "dossier");

}
