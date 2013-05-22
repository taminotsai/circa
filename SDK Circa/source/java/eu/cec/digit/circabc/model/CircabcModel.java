/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.model;

import org.alfresco.model.ContentModel;
import org.alfresco.service.namespace.QName;

/**
 * Constants for models used in Circabc
 *
 * @author atadian
 */
public interface CircabcModel extends BaseCircabcModel
{
	/** Circabc Model Prefix */
	public static final String CIRCABC_MODEL_PREFIX = "ci";


	/** Circabc model namespace */
    public static final String CIRCABC_CONTENT_MODEL_1_0_URI = CIRCABC_NAMESPACE + "/model/content/1.0";

	/** Circabc childs node Aspect name */
	public static final QName ASPECT_CIRCABC_MANAGEMENT = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "circabcManagement");
	/** Circabc Root node Aspect name */
	public static final QName ASPECT_CIRCABC_ROOT = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "circaBC");
	/** Library Aspect name */
	public static final QName ASPECT_CATEGORY = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "circaCategory");
	/** Interest Group root Aspect name */
	public static final QName ASPECT_IGROOT = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "circaIGRoot");
	/** Lirary Root Aspect Name */
	public static final QName ASPECT_LIBRARY_ROOT = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "circaLibraryRoot");
	/** Lirary childs Aspect Name */
	public static final QName ASPECT_LIBRARY = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "circaLibrary");
	/** NewsGroup root Aspect name */
	public static final QName ASPECT_NEWSGROUP_ROOT = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "circaNewsGroupRoot");
	/** NewsGroup childs Aspect name */
	public static final QName ASPECT_NEWSGROUP = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "circaNewsGroup");
    /** Survey Root Aspect name */
	public static final QName ASPECT_SURVEY_ROOT = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "circaSurveyRoot");
	/** Survey Childs Aspect name */
	public static final QName ASPECT_SURVEY = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "circaSurvey");
	/** Information Root Aspect name */
	public static final QName ASPECT_INFORMATION_ROOT = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "circabcInformationRoot");
	/** Information Childs Aspect name */
	public static final QName ASPECT_INFORMATION = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "circabcInformation");
	/** Event Root Aspect name */
	public static final QName ASPECT_EVENT_ROOT = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "circabcEventRoot");
	/** Event Childs Aspect name */
	public static final QName ASPECT_EVENT = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "circabcEvent");
	/** Shared space aspect */
	public static final QName ASPECT_SHARED_SPACE = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "circabcSharedSpace");
	/** Shared space aspect */
	public static final QName ASPECT_REVISIONABLE = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "revisionable");

	/** Profile addon importable aspect */
	public static  final QName ASPECT_PROFILE_IMPORTABLE = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "importable");

	public static  final QName ASPECT_NOTIFY_PASTE_ALL = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "notifyPasteAll");
	public static  final QName ASPECT_NOTIFY_PASTE = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "notifyPaste");

	/** Category Headers Type name */
	public static final QName TYPE_CATEGORY_HEADER = ContentModel.TYPE_CATEGORY;
	/** Directory Root type name */
	public static final QName TYPE_DIRECTORY_SERVICE = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "circaDirectoryRoot");

	/** Circabc root profile type */
	public static final QName TYPE_CIRCABC_ROOT_PROFILE = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "circaBCProfile");
	/** Category profile type */
	public static final QName TYPE_CATEGORY_PROFILE = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "circaCategoryProfile");
	/** Interest Group profile type */
	public static final QName TYPE_INTEREST_GROUP_PROFILE = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "circaIGRootProfile");
	
	public static final QName TYPE_CUSTOMIZATION_CONTENT = QName.createQName(CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "customizationContent");

	/** The association between the ig root and the directory */
	public static final QName ASSOC_IG_DIRECTORY_CONTAINER = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "igDirectoryContainer");

	/** Contact information property for interest group leader */
	public static  final QName PROP_CONTACT_INFORMATION = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "contact");
	/** Contact information property for interest group leader */
	public static  final QName PROP_LIGHT_DESCRIPTION= QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "lightDescription");
	/** Ig Root node id to put on archived node when node is deleted*/
	public static  final QName PROP_IG_ROOT_NODE_ID_ARCHIVED = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "igRootNodeIdArchived");
	/** The boolean that define if the information service should be adpat to the screen or not */
	public static  final QName PROP_INF_ADAPT = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "infAdapt");
	/** The name of the index page of the information service */
	public static  final QName PROP_INF_INDEX_PAGE = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "infIndexPage");
	/** The revision number as a Integer */
	public static  final QName PROP_REVISION_NUMBER = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "revisionNumber");
	
	/** The navigationListRenderType as a String */
	public static  final QName PROP_NAVIGATION_LIST_RENDER_TYPE = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "navigationListRenderType");

	public static final QName PROP_CONTENT = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "content");
	
	/** IAM synchronization property */ 
	public static  final QName PROP_ECORDA_THEME_ID = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "ecordaThemeID");
	
	/** External Repository type & properties */
	public static  final QName TYPE_EXTERNAL_REPOSITORY_CONFIGURATION_FOLDER = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "externalRepositoryConfigurationFolder");
	public static  final QName ASSOC_CONTAINSCON_FIGURATIONS = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "containsConfigurations");
	public static  final QName TYPE_EXTERNAL_REPOSITORY_CONFIGURATION = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "externalRepositoryConfiguration");
	
	public static  final QName ASPECT_EXTERNALLY_PUBLISHED = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "externallyPublished");
	public static  final QName PROP_REPOSITORIES_INFO = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "repositoriesInfo");
	
	/** Root reference for the saved searches. It holds the reference of the 
	 *  Root where this search was taken from to not collide with searches 
	 *  saved in other IGs or services */
	public static  final QName ASPECT_SAVED_ROOT_SEARCHABLE = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "savedRootSearchable");
	public static  final QName PROP_LOCATION = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "location");
}
