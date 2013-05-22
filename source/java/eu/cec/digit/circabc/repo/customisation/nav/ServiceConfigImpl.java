/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.customisation.nav;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.alfresco.util.ParameterCheck;

import eu.cec.digit.circabc.service.customisation.nav.ColumnConfig;
import eu.cec.digit.circabc.service.customisation.nav.ServiceConfig;

/**
 * Concrete implementation of a column navigation customisation
 *
 * @author Yanick Pignot
 */
public class ServiceConfigImpl implements Serializable, ServiceConfig
{
	/** */
	private static final long serialVersionUID = 9063564530415575753L;

	private final String name;
	private final String type ;

	private String actionConfigName;
	private int displayRowMin = -1;
	private int displayRowMax = -1;
	private int displayColMin = -1;
	private int displayColMax = -1;
	private int displayActionMin = -1;
	private int displayActionMax = -1;

	private String mandatoryAction;
	private boolean bulkOperationAllowed;

	private List<ColumnConfig> columns = new ArrayList<ColumnConfig>(20);
	private List<ColumnConfig> keyColumns = new ArrayList<ColumnConfig>(2);
	private List<String> actions = new ArrayList<String>(20);

	/*package*/ ServiceConfigImpl(final String name, final String type)
	{
		ParameterCheck.mandatoryString("The Service name", name);
		ParameterCheck.mandatoryString("The Service type", type);
		this.name = name;
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public final String getType()
	{
		return type;
	}

	/**
	 * @return the type
	 */
	public final String getName()
	{
		return name;
	}

	/**
	 * @return the actionGroup
	 */
	public final List<String> getActions()
	{
		return actions;
	}

	/**
	 * @return the columns
	 */
	public final List<ColumnConfig> getColumns()
	{
		return columns;
	}

	public final List<ColumnConfig> getKeyColumns()
	{
		return keyColumns;
	}

	/**
	 * @return the displayActionMax
	 */
	public final int getDisplayActionMax()
	{
		return displayActionMax;
	}

	/**
	 * @return the displayActionMin
	 */
	public final int getDisplayActionMin()
	{
		return displayActionMin;
	}

	/**
	 * @return the displayColMax
	 */
	public final int getDisplayColMax()
	{
		return displayColMax;
	}

	/**
	 * @return the displayColMin
	 */
	public final int getDisplayColMin()
	{
		return displayColMin;
	}

	/**
	 * @return the displayRowMax
	 */
	public final int getDisplayRowMax()
	{
		return displayRowMax;
	}

	/**
	 * @return the displayRowMin
	 */
	public final int getDisplayRowMin()
	{
		return displayRowMin;
	}

	public final String getActionConfigName()
	{
		return actionConfigName;
	}

	/**
	 * @return the mandatoryAction
	 */
	public final String getMandatoryAction()
	{
		return mandatoryAction;
	}
	/**
	 * @return the bulkOperationAllowed
	 */
	public final boolean isBulkOperationAllowed()
	{
		return bulkOperationAllowed;
	}

	/**
	 * @param name2
	 * @param actionGroup the actionGroup to set
	 */
	/*package*/ final void addAction(Collection<String> actions)
	{
		this.actions.addAll(actions);
	}

	/**
	 * @param columns the columns to set
	 */
	/*package*/ final void setColumns(List<ColumnConfig> columns)
	{
		this.columns = columns;
	}

	/**
	 * @param columns the columns to set
	 */
	/*package*/ final void addColumn(ColumnConfig column)
	{
		this.columns.add(column);
	}

	/**
	 * @param columns the columns to set
	 */
	/*package*/ final void setKeyColumns(List<ColumnConfig> columns)
	{
		this.keyColumns = columns;
	}

	/**
	 * @param columns the columns to set
	 */
	/*package*/ final void addKeyColumn(ColumnConfig column)
	{
		this.keyColumns.add(column);
	}

	/**
	 * @param displayActionMax the displayActionMax to set
	 */
	/*package*/ final void setDisplayActionMax(int displayActionMax)
	{
		this.displayActionMax = displayActionMax;
	}

	/**
	 * @param displayActionMin the displayActionMin to set
	 */
	/*package*/ final void setDisplayActionMin(int displayActionMin)
	{
		this.displayActionMin = displayActionMin;
	}

	/**
	 * @param displayColMax the displayColMax to set
	 */
	/*package*/ final void setDisplayColMax(int displayColMax)
	{
		this.displayColMax = displayColMax;
	}

	/**
	 * @param displayColMin the displayColMin to set
	 */
	/*package*/ final void setDisplayColMin(int displayColMin)
	{
		this.displayColMin = displayColMin;
	}

	/**
	 * @param displayRowMax the displayRowMax to set
	 */
	/*package*/ final void setDisplayRowMax(int displayRowMax)
	{
		this.displayRowMax = displayRowMax;
	}

	/**
	 * @param displayRowMin the displayRowMin to set
	 */
	/*package*/ final void setDisplayRowMin(int displayRowMin)
	{
		this.displayRowMin = displayRowMin;
	}

	/**
	 * @param bulkOperationAllowed the bulkOperationAllowed to set
	 */
	/*package*/ final void setBulkOperationAllowed(boolean bulkOperationAllowed)
	{
		this.bulkOperationAllowed = bulkOperationAllowed;
	}

	/**
	 * @param mandatoryAction the mandatoryAction to set
	 */
	/*package*/ final void setMandatoryAction(String mandatoryAction)
	{
		this.mandatoryAction = mandatoryAction;
	}

	/**
	 * @param actionConfigName the actionConfigName to set
	 */
	/*package*/  final void setActionConfigName(String actionConfigName)
	{
		this.actionConfigName = actionConfigName;
	}

	@Override
	public String toString()
	{
		return "Service [name:" + name + ",type:" + type + ",bulkAllowed:" + bulkOperationAllowed
		+ ",rowNum:" + displayRowMin + "-" + displayRowMax
		+ ",colNum:" + displayColMin + "-" + displayColMax
		+ ",actionNum:" + displayActionMin + "-" + displayActionMax
		+ ",\nActions (" + actionConfigName + ", mandatory: + " + mandatoryAction + "):" + actions
		+ ",\nColumns:" + columns + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((actionConfigName == null) ? 0 : actionConfigName.hashCode());
		result = PRIME * result + ((actions == null) ? 0 : actions.hashCode());
		result = PRIME * result + (bulkOperationAllowed ? 1231 : 1237);
		result = PRIME * result + ((columns == null) ? 0 : columns.hashCode());
		result = PRIME * result + displayActionMax;
		result = PRIME * result + displayActionMin;
		result = PRIME * result + displayColMax;
		result = PRIME * result + displayColMin;
		result = PRIME * result + displayRowMax;
		result = PRIME * result + displayRowMin;
		result = PRIME * result + ((keyColumns == null) ? 0 : keyColumns.hashCode());
		result = PRIME * result + ((mandatoryAction == null) ? 0 : mandatoryAction.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ServiceConfigImpl other = (ServiceConfigImpl) obj;
		if (actionConfigName == null)
		{
			if (other.actionConfigName != null)
				return false;
		} else if (!actionConfigName.equals(other.actionConfigName))
			return false;
		if (actions == null)
		{
			if (other.actions != null)
				return false;
		} else if (!actions.equals(other.actions))
			return false;
		if (bulkOperationAllowed != other.bulkOperationAllowed)
			return false;
		if (columns == null)
		{
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		if (displayActionMax != other.displayActionMax)
			return false;
		if (displayActionMin != other.displayActionMin)
			return false;
		if (displayColMax != other.displayColMax)
			return false;
		if (displayColMin != other.displayColMin)
			return false;
		if (displayRowMax != other.displayRowMax)
			return false;
		if (displayRowMin != other.displayRowMin)
			return false;
		if (keyColumns == null)
		{
			if (other.keyColumns != null)
				return false;
		} else if (!keyColumns.equals(other.keyColumns))
			return false;
		if (mandatoryAction == null)
		{
			if (other.mandatoryAction != null)
				return false;
		} else if (!mandatoryAction.equals(other.mandatoryAction))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null)
		{
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}