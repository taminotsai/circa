/**
 *
 */
package eu.cec.digit.circabc.model;

import org.alfresco.service.namespace.QName;

/**
 * @author yanick pignot
 */
public interface PermissionModel extends CircabcModel
{
	/**
	 * Qualified name of the property of type ci:profile containing the
	 * circaBC permission
	 */
	public static final QName CIRCABC_PERMISSION = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "circaBCPermission");

	/**
	 * Qualified name of the property of type ci:profile containing the
	 * category permission
	 */
	public static final QName CATEGORY_PERMISSION = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "circaCategoryPermission");

	/**
	 * Qualified name of the property of type ci:profile containing the
	 * directory permission
	 */
	public static final QName DIRECTORY_PERMISSION = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "circaIGRootDirectoryPermission");

	/**
	 * Qualified name of the property of type ci:profile containing the
	 * library permission
	 */
	public static final QName LIBRARY_PERMISSION = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "circaLibraryPermission");

	/**
	 * Qualified name of the property of type ci:profile containing the
	 * directory permission
	 */
	public static final QName NEWSGROUP_PERMISSION = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "circaNewsGroupPermission");

	/**
	 * Qualified name of the property of type ci:profile containing the
	 * survey permission
	 */
	public static final QName SURVEY_PERMISSION = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "circaSurveyPermission");

	/**
	 * Qualified name of the property of type ci:profile containing the
	 * information permission
	 */
	public static final QName INFORMATION_PERMISSION = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "circabcInformationPermission");

	/**
	 * Qualified name of the property of type ci:profile containing the
	 * event permission
	 */
	public static final QName EVENT_PERMISSION = QName.createQName(CIRCABC_CONTENT_MODEL_1_0_URI, "circabcEventPermission");
}
