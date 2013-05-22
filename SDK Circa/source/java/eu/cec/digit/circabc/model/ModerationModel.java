/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.model;

import org.alfresco.service.namespace.QName;

/**
 * Model representing
 *
 * @author Yanick Pignot
 */
public interface ModerationModel extends BaseCircabcModel
{
	 /** Circabc Dossier namespace */
	 public static final String CIRCABC_MODERATION_MODEL_1_0_URI = CIRCABC_NAMESPACE + "/model/moderation/1.0";

	 /** Circabc Model Prefix */
	 public static final String CIRCABC_MODERATION_MODEL_PREFIX = "mo";

	 /** Moderated node Aspect name */
	 public static final QName ASPECT_MODERATED = QName.createQName(CIRCABC_MODERATION_MODEL_1_0_URI, "moderated");

	 /** Is moderated Property name for Moderated Aspect (Boolean) */
	 public static final QName PROP_IS_MODERATED = QName.createQName(CIRCABC_MODERATION_MODEL_1_0_URI, "ismoderated");

	 /** Waiting node Aspect name */
	 public static final QName ASPECT_WAITING_APPROVAL = QName.createQName(CIRCABC_MODERATION_MODEL_1_0_URI, "waittingApproval");

	 /** Approved node Aspect name */
	 public static final QName ASPECT_APPROVED = QName.createQName(CIRCABC_MODERATION_MODEL_1_0_URI, "approved");

	 /** Approved By Property name for Approved Aspect (String) */
	 public static final QName PROP_APPROVED_BY = QName.createQName(CIRCABC_MODERATION_MODEL_1_0_URI, "approvedBy");

	 /** Approved On Property name for Approved Aspect (Date) */
	 public static final QName PROP_APPROVED_ON = QName.createQName(CIRCABC_MODERATION_MODEL_1_0_URI, "approvedOn");

	 /** Rejected node Aspect name */
	 public static final QName ASPECT_REJECTED = QName.createQName(CIRCABC_MODERATION_MODEL_1_0_URI, "rejected");

	 /** Rejected by Property name for Rejected Aspect (String)*/
	 public static final QName PROP_REJECT_BY = QName.createQName(CIRCABC_MODERATION_MODEL_1_0_URI, "rejectedBy");

	 /** Rejected On Property name for Rejected Aspect (Date)*/
	 public static final QName PROP_REJECT_ON = QName.createQName(CIRCABC_MODERATION_MODEL_1_0_URI, "rejectedOn");

	 /** Rejected message Property name for Rejected Aspect (String)*/
	 public static final QName PROP_REJECT_MESSAGE = QName.createQName(CIRCABC_MODERATION_MODEL_1_0_URI, "rejectMessage");
	 
	 /** Abuse signaled on node Aspect name */
	 public static final QName ASPECT_ABUSE_SIGNALED = QName.createQName(CIRCABC_MODERATION_MODEL_1_0_URI, "abuseSignaled");

	 /** The messages signaled by users (AbuseMessage)*/
	 public static final QName PROP_ABUSE_MESSAGES = QName.createQName(CIRCABC_MODERATION_MODEL_1_0_URI, "messages");	 
}
