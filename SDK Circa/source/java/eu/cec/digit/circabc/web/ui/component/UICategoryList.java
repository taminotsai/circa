/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.ui.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;

import org.alfresco.web.app.Application;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared_impl.renderkit.RendererUtils;
import org.apache.myfaces.shared_impl.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.shared_impl.renderkit.html.util.FormInfo;
import org.springframework.extensions.webscripts.ui.common.component.SelfRenderingComponent;

import eu.cec.digit.circabc.model.DocumentModel;
import eu.cec.digit.circabc.web.WebClientHelper;
import eu.cec.digit.circabc.web.WebClientHelper.ExtendedURLMode;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;
import eu.cec.digit.circabc.web.ui.common.WebResourcesCircabc;
import eu.cec.digit.circabc.web.wai.bean.navigation.CategoryHeader;

/**
 * 
 * 
 * @author Stephane Clinckart
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * SelfRenderingComponent was moved to Spring.
 * This class seems to be developed for CircaBC
 */
public class UICategoryList extends SelfRenderingComponent {

	// ------------------------------------------------------------------------------
	// Component implementation

	private static final Log logger = LogFactory.getLog(UICategoryList.class);

	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	public String getFamily() {
		return "eu.cec.digit.circabc.faces.CategoryList";
	}

	/**
	 * @see javax.faces.component.UIComponentBase#encodeBegin(javax.faces.context.FacesContext)
	 */
	@SuppressWarnings("unchecked")
	public void encodeBegin(final FacesContext context) throws IOException {
		if (isRendered() == false) {
			return;
		}

		final ValueBinding vbValue = getValueBinding("value");
		final List<CategoryHeader> categoryHeaders =  (List<CategoryHeader>) vbValue.getValue(getFacesContext());

		final ValueBinding vbChooseHeader = getValueBinding("chooseHeader");
		
		final String chooseHeader = (vbChooseHeader != null ? (String)vbChooseHeader.getValue(getFacesContext()) : "");
		
		final Map<?, ?> attrs = this.getAttributes();
		final Boolean displayCategories = ((Boolean) (attrs.get("displayCategories") != null) ? (Boolean) attrs.get("displayCategories") : Boolean.FALSE); 
		

		final ResponseWriter writer = context.getResponseWriter();

		writer.append("<div id=\"panelCatListHeaderGlobal\" class=\"panelCatListGlobal\">");
		writer.append("<div id=\"panelCatListHeader\" class=\"panelCatListLabel\" >");
		generateTitle(writer, chooseHeader);
		generateCategoryHeadersHasList(writer, categoryHeaders);
		writer.append("</div>");
		writer.append("</div>");

		if (displayCategories) {
			int indexCategory = 1;
			for (final CategoryHeader tmp : categoryHeaders) {
				final NavigableNode navigableNode = tmp.getCategoryHeader();
				final String categoryHeaderTitle = (String) navigableNode
						.getTitle();
				final String categoryHeaderId = (String) navigableNode.getId();

				writer.append("<div id=\"panel" + categoryHeaderId
						+ "Global\" class=\"panelCatListGlobal\" >");
				generateTitle(writer, categoryHeaderTitle, categoryHeaderId);
				generateCategoriesHasList(writer, tmp);
				generateGoToTop(writer, indexCategory);
				indexCategory++;
				writer.append("</div>");
			}
		}
	}

	private void generateGoToTop(final ResponseWriter writer, final int index)
			throws IOException {
		writer.append("<a href=\"#top\"");
		writer.append(" id=\"topOfPageAnchorCatListEndImg" + index + "\"");
		writer.append(" class=\"topOfPageAnchor\"");
		writer.append(" title=\"Back to the top of the page\" >");
		writer.append("Top of the page&nbsp;");
		generateTopAnchorImg(writer);
		writer.append("</a>");
	}

	private void generateTitle(final ResponseWriter writer, final String title)
			throws IOException {
		generateTitle(writer, title, null);
	}

	private void generateTitle(final ResponseWriter writer, final String title,
			final String id) throws IOException {
		writer.append("<h3>");
		if (id != null) {
			writer.append("<a name=\"n" + id + "\" ></a>");
		}
		writer.append("<img src=\""
				+ FacesContext.getCurrentInstance().getExternalContext()
						.getRequestContextPath()
				+ "/images/extension/expanded.gif\" alt=\".\" title=\".\" />");
		writer.append(title);
		writer.append("</h3>");
	}

	private void generateTopAnchorImg(final ResponseWriter writer)
			throws IOException {
		writer.append("<img src=\""
				+ FacesContext.getCurrentInstance().getExternalContext()
						.getRequestContextPath()
				+ "/images/extension/top_ns.gif\"");
		writer.append(" alt=\"Back to the top of the page\"");
		writer.append(" title=\"Back to the top of the page\" />");
	}

	private void generateCategoryHeadersHasList(final ResponseWriter writer, final List<CategoryHeader> categoryHeaders) throws IOException {
				
		final int categoryHeaderCount = categoryHeaders.size();
		Collections.sort(categoryHeaders);
		if (categoryHeaderCount <= 10) {
			writer.append("<ul class=\"categoryListItem\" style=\"width:100%\">");
			int currentRow = 1;			
			for (final CategoryHeader tmp : categoryHeaders) {
				final String liClass = (currentRow % 2 == 0) ? "recordSetRow"
						: "recordSetRowAlt";
				writer.append("<li class=\"" + liClass + "\">");
				writer.append("<a href=\"#n" + tmp.getCategoryHeader().getId()
						+ "\"");
				writer.append(" >" + tmp.getCategoryHeader().getTitle()
						+ "</a>");
				writer.append("</li>");
				currentRow++;
			}
			writer.append("</ul>");
		} else {
			final int desiredColumnCount = 3;
			final int maxCategoryHeaderByList = (categoryHeaders.size() / desiredColumnCount) + 1;

			final Iterator<CategoryHeader> categoryHeaderIterator = categoryHeaders
					.iterator();

			while (categoryHeaderIterator.hasNext()) {
				int currentCategoryHeaderCount = 0;
				final List<CategoryHeader> subCategoryHeaderList = new ArrayList<CategoryHeader>(
						10);
				while (categoryHeaderIterator.hasNext()
						&& currentCategoryHeaderCount < maxCategoryHeaderByList) {
					subCategoryHeaderList.add(categoryHeaderIterator.next());
					currentCategoryHeaderCount++;
				}
				writer.append("<ul class=\"categoryListItem\" style=\"width:"
						+ 100 / desiredColumnCount + "%\">");
				int currentRow = 1;

				if (subCategoryHeaderList.size() > 0) {
					for (final CategoryHeader tmp : subCategoryHeaderList) {
						// check if current row is even to determine style
						final String liClass = (currentRow % 2 == 0) ? "recordSetRow"
								: "recordSetRowAlt";
						writer.append("<li class=\"" + liClass + "\">");
						writer.append("<a href=\"#n"
								+ tmp.getCategoryHeader().getId() + "\"");
						writer.append(" >" + tmp.getCategoryHeader().getTitle()
								+ "</a>");
						writer.append("</li>");
						currentRow++;
					}
				}
				while (currentRow <= maxCategoryHeaderByList) {
					// check if current row is even to determine style
					final String liClass = (currentRow % 2 == 0) ? "recordSetRow"
							: "recordSetRowAlt";
					currentRow++;
					writer.append("<li class=\"" + liClass + "\">");
					writer.append("&nbsp;");
					writer.append("</li>");
				}
				writer.append("</ul>");
			}
		}
	}

	private void generateCategoriesHasList(final ResponseWriter writer, final CategoryHeader tmp) throws IOException 
	{
		// merge the items
		List<NavigableNode> allItems = new ArrayList<NavigableNode>();
		for (final NavigableNode category : tmp.getCategories()) 
		{
			allItems.add(category);
		}
		for (final NavigableNode externalLink : tmp.getExternalLinks()) 
		{
			allItems.add(externalLink);
		}
		
		// rendering arithmetics
		final int columnCount = (allItems.size() <= 10) ? 1 : 3 ;
		final int columnWidth = 100 / columnCount; // in percent
		final double columnLength = allItems.size() / (double)columnCount;
		final int columnLengthRounded = (int) Math.ceil(columnLength);
		Collections.sort(allItems);
		// write the columns
		for(int i=0; i<columnCount;i++)
		{
			int offset = i * columnLengthRounded;
			writeColumn(writer, allItems, columnWidth, offset, columnLengthRounded);
		}
	}

	private void writeColumn(final ResponseWriter writer, List<NavigableNode> items, int width, int offset, int count) throws IOException
	{
		writer.append("<ul class=\"categoryListItem\" style=\"width:" + width + "%\">");
		for(int i=offset; i<(offset+count); i++)
		{
			String liClass = (i % 2 == 0) ? "recordSetRow" : "recordSetRowAlt";
			try
			{
				NavigableNode item = items.get(i); //will cause IndexOutOfBoundsException
				if(item.hasAspect(DocumentModel.ASPECT_URLABLE))
				{
					writeExternalLink(writer, item, liClass);
				}
				else
				{
					writeCategoryLink(writer, item, liClass);
				}
			}
			catch(IndexOutOfBoundsException ioob)
			{
				//write empty list item to fill the list
				writeDummyLink(writer, liClass);
			}
		}
		writer.append("</ul>");
	}
	
	private void writeDummyLink(final ResponseWriter writer, String liClass) throws IOException
	{
		writer.append("<li class=\"" + liClass + "\">");
		writer.append("&nbsp;");
		writer.append("</li>");
	}
	
	private void writeCategoryLink(final ResponseWriter writer, final NavigableNode category, String liClass) throws IOException
	{
		String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		String url = path + WebClientHelper.getGeneratedWaiUrl(category, ExtendedURLMode.HTTP_WAI_BROWSE, true);

		writer.append("<li class=\"" + liClass + "\">");
		writer.append("<a name=\"n" + category.getTitle() + "\"");
		writer.append(" href=\"" + url + "\"");
		writer.append(" onclick=\"showWaitProgress();\"");
		writer.append(" id=\"" + category.getId() + "\">");
		writer.append(category.getTitle());
		writer.append("</a>");
		writer.append("</li>");
	}
	
	private void writeExternalLink(final ResponseWriter writer, NavigableNode externalLink, String liClass) throws IOException
	{
		String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		String url = path + WebClientHelper.getGeneratedWaiUrl(externalLink, ExtendedURLMode.HTTP_INLINE, true);
		
		writer.append("<li class=\"" + liClass + "\">");
		writer.append("<a name=\"n" + externalLink.getTitle() + "\"");
		writer.append(" href=\"" + url + "\"");
		writer.append(" target=\"_blank\"");
		writer.append(" id=\"" + externalLink.getId() + "\">");
		writer.append(externalLink.getTitle());
		writer.append("</a>");
		writer.append("</li>");
	}
	
	/**
	 * get the href part of the tag
	 * 
	 * @param context
	 *            The FacesContext
	 * @param component
	 *            The component
	 * @param clientId
	 *            The clientId of the component
	 * 
	 * @throws IOException
	 */
	protected String getNonJavaScriptAnchor(final FacesContext facesContext,
			final String clientId) throws IOException {
		if (logger.isInfoEnabled()) {
			logger.info("UICategoryList : encodeBegin - renderNonJavaScriptAnchorStart - Start");
		}
		final ViewHandler viewHandler = facesContext.getApplication()
				.getViewHandler();
		final String viewId = facesContext.getViewRoot().getViewId();
		final String path = viewHandler.getActionURL(facesContext, viewId);

		String href = "";
		final StringBuffer hrefBuf = new StringBuffer(path);
		if (logger.isInfoEnabled()) {
			logger.info("hrefBuf -> path |" + hrefBuf + "|");
		}

		// add clientId parameter for decode

		if (path.indexOf('?') == -1) {
			hrefBuf.append('?');
		} else {
			hrefBuf.append("&amp;");
		}

		final FormInfo forInfo = RendererUtils.findNestingForm(this,
				facesContext);

		final String hiddenFieldName = HtmlRendererUtils
				.getHiddenCommandLinkFieldName(forInfo);
		if (logger.isInfoEnabled()) {
			logger.info("NavigationListRenderer : hiddenFieldName |"
					+ hiddenFieldName + "|");
		}
		hrefBuf.append(hiddenFieldName);
		hrefBuf.append('=');
		if (logger.isInfoEnabled()) {
			logger.info("NavigationListRenderer : clientId |" + clientId + "|");
		}
		hrefBuf.append(clientId);

		hrefBuf.append("&amp;");
		hrefBuf.append(RendererUtils.findNestingForm(this, facesContext)
				.getFormName());
		hrefBuf.append("_SUBMIT");
		hrefBuf.append('=');
		hrefBuf.append(1);

		final StateManager stateManager = facesContext.getApplication()
				.getStateManager();

		if (stateManager.isSavingStateInClient(facesContext)) {
			hrefBuf.append("&amp;");
			logger.info("NavigationListRenderer : isSavingStateInClient |"
					+ WebResourcesCircabc.URL_STATE_MARKER + "|");
			hrefBuf.append(WebResourcesCircabc.URL_STATE_MARKER);
		}
		
        // Migration 3.1 -> 3.4.6 - 12/01/2012
        // Adding this line to correct the improvement "isPostBack" from the newer version of alfresco (3.4.1)
        // Between the 3.1.2 to 3.4.1 of alfresco version, a test has been changed in the class RestoreViewExecutor.java (line 93),
        // uses the isPostBack method
        hrefBuf.append("&amp;").append(WebResourcesCircabc.URL_FIX_ISPOSTBACK);
        
		href = hrefBuf.toString();
		if (logger.isInfoEnabled()) {
			logger.info("href |" + href + "|");
		}
		href = facesContext.getExternalContext().encodeActionURL(href);
		if (logger.isDebugEnabled()) {
			logger.info("href encode |" + href + "|");
		}
		if (logger.isInfoEnabled()) {
			logger.info("UICategoryList : encodeBegin - renderNonJavaScriptAnchorStart - Stop");
		}
		return href;
	}
	
	protected String translate(final String key, final Object ... params)
	{
		return Application.getMessage(FacesContext.getCurrentInstance(), key);
		//return WebClientHelper.translate(key, params);
	}

	/**
	 * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void restoreState(final FacesContext context, final Object state) {
		final Object values[] = (Object[]) state;
		// standard component attributes are restored by the super class
		super.restoreState(context, values[0]);
		this.value = (List<CategoryHeader>) restoreAttachedState(context,
				values[1]);
	}

	/**
	 * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext context) {
		Object values[] = new Object[2];
		// standard component attributes are saved by the super class
		values[0] = super.saveState(context);
		values[1] = saveAttachedState(context, this.value);
		return (values);
	}

	/**
	 * @see javax.faces.component.UIComponentBase#getRendersChildren()
	 */
	public boolean getRendersChildren() {
		return true;
	}

	/**
	 * Get the value (for this component the value is an object used as the
	 * DataModel)
	 * 
	 * @return the value
	 */
	@SuppressWarnings("unchecked")
	public List<CategoryHeader> getValue() {
		final ValueBinding vb = getValueBinding("");
		if (vb != null) {
			this.value = (List<CategoryHeader>) vb.getValue(getFacesContext());
		}
		return this.value;
	}

	/**
	 * Set the value
	 * 
	 * @param value
	 *            the value
	 */
	public void setValue(final List<CategoryHeader> value) {
		this.value = value;
		if (logger.isDebugEnabled()) {
			logger.debug("value setted " + value);
		}
	}

	// ------------------------------------------------------------------------------
	// Private data

	/** The categoryHeaders */
	private List<CategoryHeader> value;
}
