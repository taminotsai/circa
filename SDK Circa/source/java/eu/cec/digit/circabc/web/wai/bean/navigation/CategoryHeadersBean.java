/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.bean.navigation;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;

/**
 * Bean that backs the navigation inside a Category Header
 *
 * @author yanick pignot
 * @author Stephane Clinckart
 */
public final class CategoryHeadersBean extends BaseWaiNavigator
{
	/** */
	private static final long serialVersionUID = -6967164595499663893L;

	private static final Log logger = LogFactory.getLog(CategoryHeadersBean.class);

	public static final String JSP_NAME = "category-list.jsp";
	public static final String BEAN_NAME = "CategoryHeadersBean";

	public static final String MSG_PAGE_TITLE = "cat_list_title";
	public static final String MSG_PAGE_DESCRIPTION = "cat_list_title_desc";
	public static final String MSG_BROWSER_TITLE = "title_cat_list";


	public NavigableNodeType getManagedNodeType()
	{
		return NavigableNodeType.CATEGORY_HEADER;
	}

	public String getRelatedJsp()
	{
		return NAVIGATION_JSP_FOLDER + JSP_NAME;
	}

	public String getPageDescription()
	{
		return translate(MSG_PAGE_DESCRIPTION);
	}

	public String getPageTitle()
	{
		return translate(MSG_PAGE_TITLE);
	}

	@Override
	public String getBrowserTitle()
	{
		return translate(MSG_BROWSER_TITLE);
	}

	@Override
    public String getWebdavUrl()
	{
    	// not relevant for category header
		return null;
	}

	public void init(final Map<String, String> parameters)
	{
		//headerNodes = null;
		//categoryHeaders = null;
	}
	
	private CategoryHeadersBeanData categoryHeadersBeanData;
	private CategoryHeadersBeanData getCategoryHeadersBeanData()
	{	if(categoryHeadersBeanData == null)
		{
			//categoryHeadersBeanData = (CategoryHeadersBeanData) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("CategoryHeadersBeanData");
			categoryHeadersBeanData = (CategoryHeadersBeanData) Beans.getBean("CategoryHeadersBeanData");
		}
		return categoryHeadersBeanData;
	}
	

		

	/**
	 * Get the list of category spaces inside link to the subcategorie. The subcategory choose change at each call to the function
	 *
	 * @return List<NavigableNode> List of category spaces inside the subcategorie
	 */
	public List<CategoryHeader> getCategoryHeaders()
	{
		return getCategoryHeadersBeanData().getCategoryHeaders();
	}
}
