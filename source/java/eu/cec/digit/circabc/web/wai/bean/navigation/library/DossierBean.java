package eu.cec.digit.circabc.web.wai.bean.navigation.library;
import java.util.Map;

import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.wai.bean.navigation.LibraryBean;

//
public class DossierBean extends LibraryBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 3115195309313119381L;
	public static final String JSP_NAME  = "dossier.jsp";
	public static final String BEAN_NAME = "DossierBean";

	public static final String DESCRPTION = "library_dossier_title_desc";
	public static final String TITLE = "library_dossier_title";

	public NavigableNodeType getManagedNodeType()
	{
		return NavigableNodeType.LIBRARY_DOSSIER;
	}

	public String getBrowserTitle()
	{
		return getCurrentNode().getName();
	}

	public String getPageDescription()
	{
		return translate(DESCRPTION, getCurrentNode().getName());
	}

	public String getPageTitle()
	{
		return  getCurrentNode().getName();
	}

	public String getRelatedJsp()
	{
		return NAVIGATION_JSP_FOLDER + "library/" + JSP_NAME;
	}

	public void init(final Map<String, String> parameters)
	{
		super.init(parameters);
	}
}

