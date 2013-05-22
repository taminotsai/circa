package eu.cec.digit.circabc.web.wai.dialog.audit;

import java.util.List;

import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 *
 *
 *
 * @link http://balusc.blogspot.com/2008/10/effective-datatable-paging-and-sorting.html
 */
public abstract class BaseWaiPageableDialog<T> extends BaseWaiDialog  {

    // Properties ---------------------------------------------------------------------------------


    /**
	 *
	 */
	private static final long serialVersionUID = 5656085976894131409L;
	// Data.
    protected List<T> dataList;
    protected int totalRows;

    // Paging.
    protected int firstRow;
    protected int rowsPerPage;
    private int totalPages;
    private int pageRange;
    private Integer[] pages;
    private int currentPage;

    // Sorting.
    private String sortField;
    private boolean sortAscending;


    // Constructors -------------------------------------------------------------------------------

    public BaseWaiPageableDialog() {
        // Set default values somehow (properties files?).
        rowsPerPage = 10; // Default rows per page (max amount of rows to be displayed at once).
        pageRange = 10; // Default page range (max amount of page links to be displayed at once).
        sortField = "id"; // Default sort field.
        sortAscending = true; // Default sort direction.
    }

    // Paging actions -----------------------------------------------------------------------------

    public void pageFirst() {
        page(0);
    }

    public void pageNext() {
        page(firstRow + rowsPerPage);
    }

    public void pagePrevious() {
        page(firstRow - rowsPerPage);
    }

    public void pageLast() {
        page(totalRows - ((totalRows % rowsPerPage != 0) ? totalRows % rowsPerPage : rowsPerPage));
    }

    public void page(ActionEvent event) {
        page(((Integer) ((UICommand) event.getComponent()).getValue() - 1) * rowsPerPage);
    }

    private void page(int firstRow) {
        this.firstRow = firstRow;
        loadDataList(); // Load requested page.
    }

    // Sorting actions ----------------------------------------------------------------------------

    public void sort(ActionEvent event) {

        String sortFieldAttribute = (String) event.getComponent().getAttributes().get("sortField");

        // If the same field is sorted, then reverse order, else sort the new field ascending.
        if (sortField.equals(sortFieldAttribute)) {
            sortAscending = !sortAscending;
        } else {
            sortField = sortFieldAttribute;
            sortAscending = true;
        }

        pageFirst(); // Go to first page and load requested page.
    }

    // Loaders ------------------------------------------------------------------------------------

    private void loadDataList() {

    	getRecordCount();
        getRecords();
        setPages();
    }

	private void getRecords()
	{
		final String dataListKey = "dataList";
		// Load list and totalCount.
    	if (this.getRequestValue(dataListKey) == null)
		{
    		dataList = getList(firstRow, rowsPerPage, sortField, sortAscending);
    		this.setRequestValue(dataListKey,dataList);
		}
	}

	private void getRecordCount()
	{
		final String totalRowsKey = "totalRows";
		// Load list and totalCount.
    	if (this.getRequestValue(totalRowsKey) == null)
		{
    		 totalRows = getCount();
    		this.setRequestValue(totalRowsKey,totalRows);
		}
	}

	private void setPages()
	{
		// Set currentPage, totalPages and pages.
        currentPage = (totalRows / rowsPerPage) - ((totalRows - firstRow) / rowsPerPage) + 1;
        totalPages = (totalRows / rowsPerPage) + ((totalRows % rowsPerPage != 0) ? 1 : 0);
        int pagesLength = Math.min(pageRange, totalPages);
        pages = new Integer[pagesLength];

        // firstPage must be greater than 0 and lesser than totalPages-pageLength.
        int firstPage = Math.min(Math.max(0, currentPage - (pageRange / 2)), totalPages - pagesLength);

        // Create pages (page numbers for page links).
        for (int i = 0; i < pagesLength; i++) {
            pages[i] = ++firstPage;
        }
	}

    protected abstract int getCount();

	protected abstract  List<T> getList(int firstRow, int rowsPerPage, String sortField, boolean sortAscending);


	// Getters ------------------------------------------------------------------------------------

    public List<T> getDataList() {

    	if (FacesContext.getCurrentInstance().getRenderResponse()) {

    		loadDataList(); // Reload to get most recent data.
        }
        return dataList;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public Integer[] getPages() {
        return pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    // Setters ------------------------------------------------------------------------------------

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }



}