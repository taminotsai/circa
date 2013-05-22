/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.customisation.nav;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.util.ParameterCheck;

import eu.cec.digit.circabc.service.customisation.nav.ColumnConfig;
import eu.cec.digit.circabc.service.customisation.nav.NavigationPreference;
import eu.cec.digit.circabc.service.customisation.nav.ServiceConfig;

/**
 * Concrete implementation of a navigation customization
 *
 * @author Yanick Pignot
 */
public class NavigationPreferenceImpl implements Serializable, NavigationPreference
{
	/** */
	private static final long serialVersionUID = 9013474530415575122L;

	private ServiceConfig service;
	private final List<String> actions = new ArrayList<String>();
	private final List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
	private Integer listSize;
	private String linkTarget;
	private Boolean displayActionColumn;
	private ColumnConfig initialSortColumn;
	private Boolean initialSortDescending;
	private String viewMode;
	private Date modifiedDate;
	private NodeRef customizedOn;
	private String renderPropertyName;

	/**
	 * @param service
	 * @param type
	 * @param listSize
	 * @param columns
	 * @param actions
	 */
	/*package*/NavigationPreferenceImpl(final ServiceConfig service)
	{
		super();

		ParameterCheck.mandatory("The circabc service", service);
		ParameterCheck.mandatory("The circabc service name", service.getName());
		ParameterCheck.mandatory("The circabc service type", service.getType());

		this.service = service;
	}

	/**
	 * @param customizedOn
	 * @param displayActionColumn
	 * @param initialSortColumn
	 * @param initialSortDescending
	 * @param linkTarget
	 * @param listSize
	 * @param modifiedDate
	 * @param service
	 * @param viewMode
	 * @param renderPropertyName
	 */
	public NavigationPreferenceImpl(final List<ColumnConfig> columns, final List<String> actions, final Integer listSize, final ColumnConfig initialSortColumn, final Boolean initialSortDescending, final Boolean displayActionColumn, final String linkTarget, final String viewMode, final String renderPropertyName)
	{
		super();
		this.displayActionColumn = displayActionColumn;
		this.initialSortColumn = initialSortColumn;
		this.initialSortDescending = initialSortDescending;
		this.linkTarget = linkTarget;
		this.listSize = listSize;
		this.viewMode = viewMode;
		this.renderPropertyName=renderPropertyName;
		if(actions != null)
		{
			this.actions.addAll(actions);
		}
		if(columns != null)
		{
			this.columns.addAll(columns);
		}

	}


	/**
	 * @return the actions
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


	/**
	 * @return the displayActionColumn
	 */
	public final Boolean isDisplayActionColumn()
	{
		return displayActionColumn;
	}


	/**
	 * @return the linkTarget
	 */
	public final String getLinkTarget()
	{
		return linkTarget;
	}


	/**
	 * @return the listSize
	 */
	public final Integer getListSize()
	{
		return listSize;
	}


	/**
	 * @return the service
	 */
	public final ServiceConfig getService()
	{
		return service;
	}


	/**
	 * @param actions the actions to set
	 */
	/*package*/ final void setActions(List<String> actions)
	{
		this.actions.addAll(actions);
	}


	/**
	 * @param columns the columns to set
	 */
	/*package*/ final void setColumns(List<ColumnConfig> columns)
	{
		this.columns.addAll(columns);
	}


	/**
	 * @param displayActionColumn the displayActionColumn to set
	 */
	/*package*/ final void setDisplayActionColumn(Boolean displayActionColumn)
	{
		this.displayActionColumn = displayActionColumn;
	}

	/**
	 * @param linkTarget the linkTarget to set
	 */
	/*package*/ final void setLinkTarget(String linkTarget)
	{
		this.linkTarget = linkTarget;
	}


	/**
	 * @param listSize the listSize to set
	 */
	/*package*/ final void setListSize(Integer listSize)
	{
		this.listSize = listSize;
	}


	/**
	 * @param service the service to set
	 */
	/*package*/ final void setService(ServiceConfig service)
	{
		if(this.service != null)
		{
			throw new IllegalAccessError("The service filed is read only!");
		}

		this.service = service;
	}


	/**
	 * @return the initialSortColumn
	 */
	public final ColumnConfig getInitialSortColumn()
	{
		return initialSortColumn;
	}


	/**
	 * @param initialSortColumn the initialSortColumn to set
	 */
	/*package*/ final void setInitialSortColumn(ColumnConfig initialSortColumn)
	{
		this.initialSortColumn = initialSortColumn;
	}


	/**
	 * @return the initialSortDescending
	 */
	public final Boolean isInitialSortDescending()
	{
		return initialSortDescending;
	}


	/**
	 * @param initialSortDescending the initialSortDescending to set
	 */
	/*package*/ final void setInitialSortDescending(Boolean initialSortDescending)
	{
		this.initialSortDescending = initialSortDescending;
	}


	/**
	 * @return the viewMode
	 */
	public final String getViewMode()
	{
		return viewMode;
	}


	/**
	 * @param viewMode the viewMode to set
	 */
	/*package*/ final void setViewMode(String viewMode)
	{
		this.viewMode = viewMode;
	}

	/**
	 * @return the customizedOn
	 */
	public final NodeRef getCustomizedOn()
	{
		return customizedOn;
	}


	/**
	 * @param customizedOn the customizedOn to set
	 */
	/*package*/ final void setCustomizedOn(NodeRef customizedOn)
	{
		this.customizedOn = customizedOn;
	}

	/**
	 * @param listSize the listSize to set
	 */
	/*package*/ final void setListSizeStr(String listSize) throws NumberFormatException
	{
		if(listSize == null || listSize.length() < 1)
		{
			this.listSize = DEFAULT_LIST_SIZE;
		}
		else
		{
			this.listSize = Integer.parseInt(listSize);
		}
	}

	/**
	 * @param initialSortDescending the initialSortDescending to set
	 */
	/*package*/ final void setInitialSortDescendingStr(String initialSortDescending)
	{
		if(initialSortDescending == null || initialSortDescending.length() < 1)
		{
			this.initialSortDescending = DEFAULT_SORT_DESC;
		}
		else
		{
			this.initialSortDescending = Boolean.parseBoolean(initialSortDescending);
		}
	}

	/**
	 * @param displayActionColumn the displayActionColumn to set
	 */
	/*package*/ final void setDisplayActionColumnStr(String displayActionColumn)
	{
		if(displayActionColumn == null || displayActionColumn.length() < 1)
		{
			this.displayActionColumn = DEFAULT_VIEW_ACTIONS;
		}
		else
		{
			this.displayActionColumn = Boolean.parseBoolean(displayActionColumn);
		}
	}


	/*package*/ void setInitialSortColumnStr(String initialSortColumn)
	{
		ColumnConfig col = null;

		if(initialSortColumn == null || initialSortColumn.length() < 1)
		{
			this.displayActionColumn = DEFAULT_VIEW_ACTIONS;

			if(columns.size() > 0)
			{
				col = columns.get(0);
			}
		}
		else
		{
			for(ColumnConfig c: columns)
			{
				if(initialSortColumn.equalsIgnoreCase(c.getName()))
				{
					col = c;
					break;
				}
			}
		}
		this.initialSortColumn = col;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((actions == null) ? 0 : actions.hashCode());
		result = PRIME * result + ((columns == null) ? 0 : columns.hashCode());
		result = PRIME * result + ((displayActionColumn == null) ? 0 : displayActionColumn.hashCode());
		result = PRIME * result + ((initialSortColumn == null) ? 0 : initialSortColumn.hashCode());
		result = PRIME * result + ((initialSortDescending == null) ? 0 : initialSortDescending.hashCode());
		result = PRIME * result + ((linkTarget == null) ? 0 : linkTarget.hashCode());
		result = PRIME * result + ((listSize == null) ? 0 : listSize.hashCode());
		result = PRIME * result + ((service == null) ? 0 : service.hashCode());
		result = PRIME * result + ((viewMode == null) ? 0 : viewMode.hashCode());
		result = PRIME * result + ((renderPropertyName == null) ? 0 : renderPropertyName.hashCode());
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
		final NavigationPreferenceImpl other = (NavigationPreferenceImpl) obj;
		if (actions == null)
		{
			if (other.actions != null)
				return false;
		} else if (!actions.equals(other.actions))
			return false;
		if (columns == null)
		{
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		if (displayActionColumn == null)
		{
			if (other.displayActionColumn != null)
				return false;
		} else if (!displayActionColumn.equals(other.displayActionColumn))
			return false;
		if (initialSortColumn == null)
		{
			if (other.initialSortColumn != null)
				return false;
		} else if (!initialSortColumn.equals(other.initialSortColumn))
			return false;
		if (initialSortDescending == null)
		{
			if (other.initialSortDescending != null)
				return false;
		} else if (!initialSortDescending.equals(other.initialSortDescending))
			return false;
		if (linkTarget == null)
		{
			if (other.linkTarget != null)
				return false;
		} else if (!linkTarget.equals(other.linkTarget))
			return false;
		if (listSize == null)
		{
			if (other.listSize != null)
				return false;
		} else if (!listSize.equals(other.listSize))
			return false;
		if (service == null)
		{
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		if (viewMode == null)
		{
			if (other.viewMode != null)
				return false;
		} else if (!viewMode.equals(other.viewMode))
			return false;
		if (renderPropertyName == null)
		{
			if (other.renderPropertyName != null)
				return false;
		} else if (!renderPropertyName.equals(other.renderPropertyName))
			return false;
		return true;
	}


	/**
	 * @return the modifiedDate [use by the service for caching purposes]
	 */
	/*package*/ final Date getModifiedDate()
	{
		return modifiedDate;
	}


	/**
	 * @param modifiedDate the modifiedDate to set [use by the service for caching purposes]
	 */
	/*package*/  final void setModifiedDate(Date modifiedDate)
	{
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the renderPropertyName
	 */
	 public final String getRenderPropertyName() {
		return renderPropertyName;
	}

	/**
	 * @param renderPropertyName the renderPropertyName to set
	 */
	 public final void setRenderPropertyName(String renderPropertyName) {
		this.renderPropertyName = renderPropertyName;
	}
}