/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.model;

import org.alfresco.service.namespace.QName;

/**
 * It is the model for the share space specification
 *
 * @author Stephane Clinckart
 * @author Slobodan Filipovic
 */
public interface SharedSpaceModel extends BaseCircabcModel
{
	 /** Circabc Shared Space namespace */
	 public static final String CIRCABC_SHARED_SPACE_MODEL_1_0_URI = CIRCABC_NAMESPACE + "/model/sharespace/1.0";


	 /** Circabc Shared Space prefix */
	 public static final String CIRCABC_SHARED_SPACE_MODEL_PREFIX = "ss";

	 public static final QName ASSOC_SHARE_SPACE_CONTAINER = QName.createQName(
				CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "shareSpaceContainer");


	 public static final QName TYPE_CONTAINER = QName.createQName(
				CIRCABC_SHARED_SPACE_MODEL_1_0_URI, "Container");

	 /** Circabc  */
	public static final QName ASSOC_ITEREST_GROUP = QName.createQName(
				CIRCABC_SHARED_SPACE_MODEL_1_0_URI, "InterestGroupAss");

	public static final QName TYPE_INVITED_INTEREST_GROUP = QName.createQName(
			 CIRCABC_SHARED_SPACE_MODEL_1_0_URI, "invitedInterestGroup");
	public static final QName PROP_INTEREST_GROUP_NODE_REF = QName.createQName(
			 CIRCABC_SHARED_SPACE_MODEL_1_0_URI, "ignoderef");

	public static final QName PROP_PERMISSION = QName.createQName(
			 CIRCABC_SHARED_SPACE_MODEL_1_0_URI, "permission");














}
