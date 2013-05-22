/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.customisation.nav;

import java.util.List;

/**
 * The base representation of a service navigation customisation.
 *
 * @author Yanick Pignot
 */
public interface ServiceConfig
{
	/**
	 * @return the name
	 */
	public abstract String getName();
	/**
	 * @return the type
	 */
	public abstract String getType();

	/**
	 * @return the actions
	 */
	public abstract List<String> getActions();

	/**
	 * @return the columns
	 */
	public abstract List<ColumnConfig> getColumns();

	/**
	 * @return the columns
	 */
	public abstract List<ColumnConfig> getKeyColumns();

	/**
	 * @return the displayActionMax
	 */
	public abstract int getDisplayActionMax();

	/**
	 * @return the displayActionMin
	 */
	public abstract int getDisplayActionMin();

	/**
	 * @return the displayColMax
	 */
	public abstract int getDisplayColMax();

	/**
	 * @return the displayColMin
	 */
	public abstract int getDisplayColMin();

	/**
	 * @return the displayRowMax
	 */
	public abstract int getDisplayRowMax();

	/**
	 * @return the displayRowMin
	 */
	public abstract int getDisplayRowMin();

	/**
	 * @return the actionConfigName
	 */
	public abstract String getActionConfigName();

	/**
	 * @return the mandatory action
	 */
	public abstract String getMandatoryAction();

	/**
	 * @return is Bulk Operation Allowed
	 */
	public abstract boolean isBulkOperationAllowed();
}

