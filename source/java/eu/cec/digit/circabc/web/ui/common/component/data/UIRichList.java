/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.common.component.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.http.HttpSession;

import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.data.IDataContainer;
import org.alfresco.web.ui.common.component.IBreadcrumbHandler;
import org.alfresco.web.ui.common.component.data.GridArrayDataModel;
import org.alfresco.web.ui.common.component.data.GridListDataModel;
import org.alfresco.web.ui.common.component.data.IGridDataModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.ui.common.renderer.data.IRichListRenderer;

/**
 * @author Guillaume
 */
public class UIRichList extends UIComponentBase implements IDataContainer {
	// ------------------------------------------------------------------------------
	// Construction

	/**
	 * Default constructor
	 */
	public UIRichList(final String rederer) {
		setRendererType(rederer);

		List<String> views = new ArrayList<String>(1);
		views.add("eu.cec.digit.circabc.web.ui.common.renderer.data.RichListRenderer$CircaViewRenderer");

		// instantiate each renderer and add to the list
		for (String view : views) {
			try {
				Class clazz = Class.forName(view);
				IRichListRenderer renderer = (IRichListRenderer) clazz
						.newInstance();
				viewRenderers.put(renderer.getViewModeID(), renderer);

				if (logger.isDebugEnabled())
					logger.debug("Added view '" + renderer.getViewModeID()
							+ "' to UIRichList");
			} catch (Exception e) {
				if (logger.isWarnEnabled()) {
					logger.warn("Failed to create renderer: " + view, e);
				}
			}
		}
	}

	/**
	 * Default constructor
	 */
	public UIRichList() {
		this("eu.cec.digit.circabc.faces.RichListRenderer");
	}

	// ------------------------------------------------------------------------------
	// Component implementation

	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	public String getFamily() {
		return "eu.cec.digit.circabc.faces.Data";
	}


	/**
	 * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		// standard component attributes are restored by the super class
		super.restoreState(context, values[0]);
		this.setCurrentPage(((Integer) values[1]).intValue());
		this.sortColumn = (String) values[2];
		this.sortDescending = ((Boolean) values[3]).booleanValue();
		this.value = values[4]; // not serializable!
		this.dataModel = (IGridDataModel) values[5]; // not serializable!
		this.viewMode = (String) values[6];
		this.pageSize = ((Integer) values[7]).intValue();
		this.initialSortColumn = (String) values[8];
		this.initialSortDescending = ((Boolean) values[9]).booleanValue();
	}

	/**
	 * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext context) {
		Object values[] = new Object[10];
		// standard component attributes are saved by the super class
		values[0] = super.saveState(context);
		values[1] = Integer.valueOf(this.getCurrentPage());
		values[2] = this.sortColumn;
		values[3] = (this.sortDescending ? Boolean.TRUE : Boolean.FALSE);
		values[4] = this.value;
		values[5] = this.dataModel;
		values[6] = this.viewMode;
		values[7] = Integer.valueOf(this.pageSize);
		values[8] = this.initialSortColumn;
		values[9] = (this.initialSortDescending ? Boolean.TRUE : Boolean.FALSE);

		return (values);
	}

	/**
	 * Get the value (for this component the value is an object used as the
	 * DataModel)
	 *
	 * @return the value
	 */
	public Object getValue() {
		if (this.value == null) {
			ValueBinding vb = getValueBinding("value");
			if (vb != null) {
				this.value = vb.getValue(getFacesContext());
			}
		}
		return this.value;
	}

	/**
	 * Set the value (for this component the value is an object used as the
	 * DataModel)
	 *
	 * @param value
	 *            the value
	 */
	public void setValue(Object value) {
		this.dataModel = null;
		this.value = value;
	}

	/**
	 * Clear the current sorting settings back to the defaults
	 */
	public void clearSort() {
		this.sortColumn = null;
		this.sortDescending = true;
		this.initialSortColumn = null;
		this.initialSortDescending = false;
	}

	/**
	 * Get the view mode for this Rich List
	 *
	 * @return view mode as a String
	 */
	public String getViewMode() {
		ValueBinding vb = getValueBinding("viewMode");
		if (vb != null) {
			this.viewMode = (String) vb.getValue(getFacesContext());
		}

		return this.viewMode;
	}

	/**
	 * Set the current view mode for this Rich List
	 *
	 * @param viewMode
	 *            the view mode as a String
	 */
	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	/**
	 * Return the UI Component to be used as the "no items available" message
	 *
	 * @return UIComponent
	 */
	public UIComponent getEmptyMessage() {
		return getFacet("empty");
	}

	// ------------------------------------------------------------------------------
	// IDataContainer implementation

	/**
	 * Return the currently sorted column if any
	 *
	 * @return current sorted column if any
	 */
	public String getCurrentSortColumn() {
		return this.sortColumn;
	}

	/**
	 * @see org.alfresco.web.data.IDataContainer#isCurrentSortDescending()
	 */
	public boolean isCurrentSortDescending() {
		return this.sortDescending;
	}

	/**
	 * @return Returns the initialSortColumn.
	 */
	public String getInitialSortColumn() {
		return this.initialSortColumn;
	}

	/**
	 * @param initialSortColumn
	 *            The initialSortColumn to set.
	 */
	public void setInitialSortColumn(String initialSortColumn) {
		this.initialSortColumn = initialSortColumn;
	}

	/**
	 * @return Returns the initialSortDescending.
	 */
	public boolean isInitialSortDescending() {
		return this.initialSortDescending;
	}

	/**
	 * @param initialSortDescending
	 *            The initialSortDescending to set.
	 */
	public void setInitialSortDescending(boolean initialSortDescending) {
		this.initialSortDescending = initialSortDescending;
	}

	/**
	 * Returns the current page size used for this list, or -1 for no paging.
	 */
	public int getPageSize() {
		ValueBinding vb = getValueBinding("pageSize");
		if (vb != null) {
			int pageSize = ((Integer) vb.getValue(getFacesContext()))
					.intValue();
			if (pageSize != this.pageSize) {
				// force a reset of the current page - else the bind may show a
				// page that isn't there
				setPageSize(pageSize);
			}
		}

		return this.pageSize;
	}

	/**
	 * Sets the current page size used for the list.
	 *
	 * @param val
	 */
	public void setPageSize(int val) {
		if (val >= -1) {
			this.pageSize = val;
		}
	}

	/**
	 * @see org.alfresco.web.data.IDataContainer#getPageCount()
	 */
	public int getPageCount() {
		return this.pageCount;
	}

	/** 
	 * Overrides the getCurrentPage method to store 
	 */
	public int getCurrentPage() {
		Integer pageInSession = this.getPageFromSession();
		if(pageInSession != null) {
			return pageInSession.intValue();
		}
		else {
			return 0;
		}	
	}
	
	public void setCurrentPage(int page) {
		this.setPageInSession(page);
	}

	/**
	 * Returns true if a row of data is available
	 *
	 * @return true if data is available, false otherwise
	 */
	public boolean isDataAvailable() {
		return this.rowIndex < this.maxRowIndex;
	}

	/**
	 * Returns the next row of data from the data model
	 *
	 * @return next row of data as a Bean object
	 */
	@SuppressWarnings("unchecked")
	public Object nextRow() {
		// get next row and increment row count
		Object rowData = getDataModel().getRow(this.rowIndex + 1);

		// Prepare the data-binding variable "var" ready for the next cycle of
		// renderering for the child components.
		String var = (String) getAttributes().get("var");
		if (var != null) {
			Map requestMap = getFacesContext().getExternalContext()
					.getRequestMap();
			if (isDataAvailable() == true) {
				requestMap.put(var, rowData);
			} else {
				requestMap.remove(var);
			}
		}

		this.rowIndex++;

		return rowData;
	}

	/**
	 * Sort the dataset using the specified sort parameters
	 *
	 * @param column
	 *            Column to sort
	 * @param descending
	 *            True for descending sort, false for ascending
	 * @param mode
	 *            Sort mode to use (see IDataContainer constants)
	 */
	public void sort(final String column, final boolean descending, final String mode) {
		this.sortColumn = column;
		this.sortDescending = descending;

		// delegate to the data model to sort its contents
		// place in a User Transaction as we may need to perform a LOT of node
		// calls to complete
		final FacesContext context = FacesContext.getCurrentInstance();
		final RetryingTransactionHelper txnHelper = Repository.getRetryingTransactionHelper(context);
		final RetryingTransactionCallback<Object> callback = new RetryingTransactionCallback<Object>()
		{
			public Object execute() throws Throwable
			{
				getDataModel().sort(column, descending, mode);
				return null;
			}
		};
		txnHelper.doInTransaction(callback, true);
	}

	// ------------------------------------------------------------------------------
	// UIRichList implementation

	/**
	 * Method called to bind the RichList component state to the data model
	 * value
	 */
	public void bind() {
		int rowCount = getDataModel().size();
		// if a page size is specified, then we use that
		int pageSize = getPageSize();
		if (pageSize != -1 && pageSize != 0) {

			// calc total number of pages available
			this.pageCount = (rowCount / this.pageSize) + 1;
			if (rowCount % pageSize == 0 && this.pageCount != 1) {
				this.pageCount--;
			}

			// fix current page 
			if (getCurrentPage() >=  pageCount )
			{
				setCurrentPage(0);
			}
			// calc start row index based on current page index
			this.rowIndex = (this.getCurrentPage() * pageSize) - 1;


			// calc the maximum row index that can be returned
			this.maxRowIndex = this.rowIndex + pageSize;
			if (this.maxRowIndex >= rowCount) {
				this.maxRowIndex = rowCount - 1;
			}
		
			
		}
		// else we are not paged so show all data from start
		else {
			this.rowIndex = -1;
			this.pageCount = 1;
			this.maxRowIndex = (rowCount - 1);
		}
		if (logger.isDebugEnabled())
			logger.debug("Bound datasource: PageSize: " + pageSize
					+ "; CurrentPage: " + this.getCurrentPage() + "; RowIndex: "
					+ this.rowIndex + "; MaxRowIndex: " + this.maxRowIndex
					+ "; RowCount: " + rowCount);
	}

	/**
	 * @return A new IRichListRenderer implementation for the current view mode
	 */
	public IRichListRenderer getViewRenderer() {
		// get type from current view mode, then create an instance of the
		// renderer
		IRichListRenderer renderer = null;
		if (getViewMode() != null) {
			renderer = (IRichListRenderer) viewRenderers.get(getViewMode());
		}
		return renderer;
	}

	/**
	 * Return the data model wrapper
	 *
	 * @return IGridDataModel
	 */
	private IGridDataModel getDataModel() {
		if (this.dataModel == null) {
			// build the appropriate data-model wrapper object
			Object val = getValue();
			if (val instanceof List)
			{
				this.dataModel = new GridListDataModel((List) val);
			}
			else if (val instanceof List)
			{
				this.dataModel = new GridListDataModel((List) val);
			}
			else if ((java.lang.Object[].class).isAssignableFrom(val.getClass()))
			{
				this.dataModel = new GridArrayDataModel((Object[]) val);
			}
			else {
				throw new IllegalStateException(
						"UIRichList 'value' attribute binding should specify data model of a supported type!");
			}

			// sort first time on initially sorted column if set
			if (this.sortColumn == null) {
				String initialSortColumn = getInitialSortColumn();
				if (initialSortColumn != null
						&& initialSortColumn.length() != 0) {
					boolean descending = isInitialSortDescending();

					// TODO: add support for retrieving correct column sort mode
					// here
					this.sortColumn = initialSortColumn;
					this.sortDescending = descending;
				}
			}
			if (this.sortColumn != null) {
				// delegate to the data model to sort its contents
				this.dataModel.sort(this.sortColumn, this.sortDescending,
						IDataContainer.SORT_CASEINSENSITIVE);
			}
		}

		return this.dataModel;
	}
	
	@SuppressWarnings("unchecked")
	private void setPageInSession(int page) 
	{
		// try to save the persistent page number in the session
		HttpSession session = getSession();
		HashMap<String, Integer> pagerMap = null;
		if(session.getAttribute(CIRCABC_PAGER_MAP) != null)
		{
			pagerMap = (HashMap<String, Integer>)session.getAttribute(CIRCABC_PAGER_MAP);
		}
		else
		{
			pagerMap = new HashMap<String, Integer>();
		}
		pagerMap.put(this.getUniqueId(), page);
		session.setAttribute(CIRCABC_PAGER_MAP, pagerMap);
	}
	
	@SuppressWarnings("unchecked")
	private Integer getPageFromSession()
	{
		// get the persistent page number from the session
		HttpSession session = getSession();
		if (session != null) {
			Object objPagerMap = session.getAttribute(CIRCABC_PAGER_MAP);
			if(objPagerMap != null) {
				HashMap<String, Integer> pagerMap = (HashMap<String, Integer>)objPagerMap;
				Object objPage = pagerMap.get(this.getUniqueId());
				if(objPage != null)
				{
					return (Integer)objPage;
				}
			}
		}
		return null;
	}
	
	private HttpSession getSession()
	{
		return (HttpSession)this.getFacesContext().getExternalContext().getSession(false);
	}
	
	/**
	 * Builds a system-wide unique ID based on the component ID and ID of the current Node 
	 * @return a unique ID for this component
	 */
	private String getUniqueId()
	{
		if (this.uniqueId == null) {
			StringBuilder uniqueIdBuilder = new StringBuilder();
			
			// add the location path to the ID
			for(IBreadcrumbHandler part : Beans.getWaiNavigator().getLocation()) {
				uniqueIdBuilder.append(part.toString());
				uniqueIdBuilder.append("_");
			}
			
			// add the component ID
			uniqueIdBuilder.append(this.getId());
			
			this.uniqueId = uniqueIdBuilder.toString();
		}
		return this.uniqueId;
	}

	// ------------------------------------------------------------------------------
	// Private data

	/** map of available IRichListRenderer instances */
	private final Map<String, IRichListRenderer> viewRenderers = new HashMap<String, IRichListRenderer>(
			4, 1.0f);

	// component state
	private String sortColumn = null;

	private boolean sortDescending = true;

	private Object value = null;

	private IGridDataModel dataModel = null;

	private String viewMode = null;

	private int pageSize = -1;

	private String initialSortColumn = null;

	private boolean initialSortDescending = false;

	// transient component state that exists during a single page refresh only
	private int rowIndex = -1;

	private int maxRowIndex = -1;

	private int pageCount = 1;

	private static final Log logger = LogFactory.getLog(IDataContainer.class);
	
	private static final String CIRCABC_PAGER_MAP = "circabcPagerMap";
	
	private String uniqueId = null;
}
