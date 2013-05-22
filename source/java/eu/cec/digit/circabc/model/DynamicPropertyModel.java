/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.model;

import org.alfresco.service.namespace.QName;

/**
 * It is the model for the dynamic properties specification
 *
 * @author Slobodan Filipovic
 */
public interface DynamicPropertyModel extends BaseCircabcModel
{
	 /** Circabc Dynamic Properties namespace */
	 public static final String CIRCABC_DYNAMIC_PROPERTY_MODEL_1_0_URI = CIRCABC_NAMESPACE + "/model/dynamicproperties/1.0";

	 /** Circabc dynamic property prefix */
	 public static final String CIRCABC_DYNAMIC_PROPERTY_MODEL_PREFIX = "dp";

	 /** Circabc dynamic property root container */
	 public static final QName TYPE_DYNAMIC_PROPERTY_CONTAINER = QName.createQName(CIRCABC_DYNAMIC_PROPERTY_MODEL_1_0_URI, "Container");

	 /** Circabc dynamic property */
	 public static final QName TYPE_DYNAMIC_PROPERTY = QName.createQName(CIRCABC_DYNAMIC_PROPERTY_MODEL_1_0_URI, "DynProp");

	 /** Circabc dynamic property */
	 public static final QName ASSOC_DYNAMIC_PROPERTY = QName.createQName(CIRCABC_DYNAMIC_PROPERTY_MODEL_1_0_URI, "DynPropAss");

	 /** Circabc dynamic property index */
	 public static final QName PROP_DYNAMIC_PROPERTY_INDEX = QName.createQName(CIRCABC_DYNAMIC_PROPERTY_MODEL_1_0_URI, "Index");

	 /** Circabc dynamic property */
	 public static final QName PROP_DYNAMIC_PROPERTY_LABEL = QName.createQName(CIRCABC_DYNAMIC_PROPERTY_MODEL_1_0_URI, "Label");

	 /** Circabc dynamic property label */
	 public static final QName PROP_DYNAMIC_PROPERTY_TYPE = QName.createQName(CIRCABC_DYNAMIC_PROPERTY_MODEL_1_0_URI, "Type");

	 /** Circabc dynamic property  */
	 public static final QName PROP_DYNAMIC_PROPERTY_VALID_VALUES = QName.createQName(CIRCABC_DYNAMIC_PROPERTY_MODEL_1_0_URI, "ValidValues");

	 /** The possible values of the dynamic property type*/
	 public static final String[] DYNAMIC_PROPERTY_TYPE_VALUES = {
				"DATE_FIELD","TEXT_FIELD","TEXT_AREA", "SELECTION"
		 };

}
