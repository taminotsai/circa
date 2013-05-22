/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.customisation.nav;

import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * The base representation of a navigation customisation.
 *
 * @author Yanick Pignot
 */
public interface NavigationPreference
{
	public static final String BROWSE_LINK_ACTION = "browse";
	public static final String DOWNLOAD_LINK_ACTION = "download";

	public static final Integer MAX_LIST_SIZE = Integer.valueOf(250);
	public static final Integer DEFAULT_LIST_SIZE = Integer.valueOf(10);
	public static final Boolean DEFAULT_VIEW_ACTIONS = Boolean.TRUE;
	public static final Boolean DEFAULT_SORT_DESC = Boolean.FALSE;
	public static final String DEFAULT_LINK_ACTION = BROWSE_LINK_ACTION;


	/**
	 * @return the service
	 */
	public abstract ServiceConfig getService();

	/**
	 * @return the actions
	 */
	public abstract List<String> getActions();

	/**
	 * @return the columns
	 */
	public abstract List<ColumnConfig> getColumns();

	/**
	 * @return the listSize
	 */
	public abstract Integer getListSize();

	/**
	 * @return	the link target
	 */
	public abstract String getLinkTarget();

	/**
	 * @return	if the action column is displayed
	 */
	public abstract Boolean isDisplayActionColumn();

	/**
	 * @return the initialSortColumn
	 */
	public abstract ColumnConfig getInitialSortColumn();

	/**
	 * @return the initialSortDescending
	 */
	public abstract Boolean isInitialSortDescending();

	/**
	 * @return the viewMode
	 */
	public abstract String getViewMode();

	/**
	 * @return the customizedOn
	 */
	public abstract NodeRef getCustomizedOn();
	
	/***
	 * 
	 * @return the property used to be displayed in navigation list
	 */
	public abstract String getRenderPropertyName();

}