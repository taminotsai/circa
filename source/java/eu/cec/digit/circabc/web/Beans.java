/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web;

import javax.faces.context.FacesContext;

import org.alfresco.web.app.servlet.FacesHelper;
import org.alfresco.web.bean.BrowseBean;
import org.alfresco.web.bean.NavigationBean;
import org.alfresco.web.bean.categories.CategoriesDialog;
import org.alfresco.web.bean.clipboard.ClipboardBean;
import org.alfresco.web.bean.coci.CCCheckinFileDialog;
import org.alfresco.web.bean.coci.CCCheckoutFileDialog;
import org.alfresco.web.bean.coci.CCUndoCheckoutFileDialog;
import org.alfresco.web.bean.coci.CCUpdateFileDialog;
import org.alfresco.web.bean.content.DocumentDetailsDialog;
import org.alfresco.web.bean.content.VersionedDocumentDetailsDialog;
import org.alfresco.web.bean.ml.MultilingualManageDialog;

import eu.cec.digit.circabc.web.bean.navigation.InterestGroupLogoBean;
import eu.cec.digit.circabc.web.bean.override.CircabcBrowseBean;
import eu.cec.digit.circabc.web.bean.override.CircabcNavigationBean;
import eu.cec.digit.circabc.web.bean.override.CircabcUserPreferencesBean;
import eu.cec.digit.circabc.web.wai.bean.LoginBean;
import eu.cec.digit.circabc.web.wai.bean.ml.AddTranslationBean;
import eu.cec.digit.circabc.web.wai.bean.navigation.WaiNavigator;
import eu.cec.digit.circabc.web.wai.dialog.content.edit.EditOnlineDocumentDialog;
import eu.cec.digit.circabc.web.wai.dialog.generic.EditNodePropertiesDialog;
import eu.cec.digit.circabc.web.wai.dialog.search.AdvancedSearchDialog;
import eu.cec.digit.circabc.web.wai.dialog.search.SearchProperties;
import eu.cec.digit.circabc.web.wai.dialog.search.SearchResultDialog;
import eu.cec.digit.circabc.web.wai.part.LeftMenuBean;

/**
 * Helper class for accessing Circa and Alfresco Beans utilities.
 *
 * @author Stephane Clinckart
 * @author Yanick Pignot
 */
public class Beans
{
	private static final String CATEGORIES_DIALOG = "CategoriesDialog";
	private static final String CLIPBOARD_BEAN = "ClipboardBean";
	private static final String VERSIONED_DOCUMENT_DETAILS_DIALOG = "VersionedDocumentDetailsDialog";
	private static final String DOCUMENT_DETAILS_DIALOG = "DocumentDetailsDialog";
	private static final String MULTILINGUAL_MANAGE_DIALOG = "MultilingualManageDialog";
	private static final String CC_CHECKOUT_FILE_DIALOG = "CCCheckoutFileDialog";
	private static final String CC_CHECKIN_FILE_DIALOG = "CCCheckinFileDialog";
	private static final String CC_UPDATE_FILE_DIALOG = "CCUpdateFileDialog";
	private static final String CC_UNDO_CHECKOUT_FILE_DIALOG = "CCUndoCheckoutFileDialog";

	private Beans()
	{

	}

	/**
	 * Get any bean
	 *
	 * @param beanName
	 * @return
	 */
	public static Object getBean(final String beanName)
	{
		return FacesHelper.getManagedBean(FacesContext.getCurrentInstance(),beanName);
	}

	public static WaiNavigator getWaiBrosableBean(final String beanName)
	{
		return (WaiNavigator) getBean(beanName);
	}


	// Get the most common beans */

	public static CircabcNavigationBean getWaiNavigator()
	{
		return (CircabcNavigationBean)getBean(CircabcNavigationBean.BEAN_NAME);
	}

	public static NavigationBean getAlfrescoNavigator()
	{
		return (NavigationBean)getBean(NavigationBean.BEAN_NAME);
	}

	public static CircabcBrowseBean getWaiBrowseBean()
	{
		return (CircabcBrowseBean)getBean(CircabcBrowseBean.BEAN_NAME);
	}

	public static LeftMenuBean getWaiLeftMenuBean()
	{
		return (LeftMenuBean) getBean(LeftMenuBean.BEAN_NAME);
	}

	public static BrowseBean getAlfrescoBrowseBean()
	{
		return (BrowseBean)getBean(BrowseBean.BEAN_NAME);
	}

	public static CircabcUserPreferencesBean getUserPreferencesBean()
	{
		return (CircabcUserPreferencesBean) getBean(CircabcUserPreferencesBean.BEAN_NAME);
	}

	public static AdvancedSearchDialog getAdvancedSearchDialog()
	{
		return (AdvancedSearchDialog) getBean(AdvancedSearchDialog.BEAN_NAME);
	}

	public static LoginBean getLoginBean()
	{
		return (LoginBean) getBean(LoginBean.BEAN_NAME);
	}

	public static EditNodePropertiesDialog getEditNodePropertiesDialog()
	{
		return (EditNodePropertiesDialog) getBean(EditNodePropertiesDialog.BEAN_NAME);
	}

	public static EditOnlineDocumentDialog getEditOnlineDocumentDialog()
	{
		return (EditOnlineDocumentDialog) getBean(EditOnlineDocumentDialog.BEAN_NAME);
	}

	public static DocumentDetailsDialog getDocumentDetailsDialog()
	{
		return (DocumentDetailsDialog) getBean(DOCUMENT_DETAILS_DIALOG);
	}

	public static VersionedDocumentDetailsDialog getVersionedDocumentDetailsDialog()
	{
		return (VersionedDocumentDetailsDialog) getBean(VERSIONED_DOCUMENT_DETAILS_DIALOG);
	}

	public static ClipboardBean getClipboardBean()
	{
		return (ClipboardBean) getBean(CLIPBOARD_BEAN);
	}

	public static CategoriesDialog getCategoriesDialog()
	{
		return (CategoriesDialog) getBean(CATEGORIES_DIALOG);
	}

	public static MultilingualManageDialog getMultilingualManageDialog()
	{
		return (MultilingualManageDialog) getBean(MULTILINGUAL_MANAGE_DIALOG);
	}

	public static AddTranslationBean getAddTranslationBean()
	{
		return (AddTranslationBean) getBean(AddTranslationBean.BEAN_NAME);
	}

	public static CCCheckoutFileDialog getCCCheckoutFileDialog() {
		return (CCCheckoutFileDialog) getBean(CC_CHECKOUT_FILE_DIALOG);
	}

	public static CCCheckinFileDialog getCCCheckinFileDialog() {
		return (CCCheckinFileDialog) getBean(CC_CHECKIN_FILE_DIALOG);
	}

	public static CCUpdateFileDialog getCCUpdateFileDialog() {
		return (CCUpdateFileDialog) getBean(CC_UPDATE_FILE_DIALOG);
	}

	public static CCUndoCheckoutFileDialog getCCUndoCheckoutFileDialog() {
		return (CCUndoCheckoutFileDialog) getBean(CC_UNDO_CHECKOUT_FILE_DIALOG);
	}

	public static SearchProperties getSearchProperties(){
		return (SearchProperties) getBean(SearchProperties.BEAN_NAME);
	}

	public static SearchResultDialog getSearchResultDialog()
	{
		return (SearchResultDialog) getBean(SearchResultDialog.BEAN_NAME);
	}
	
	public static InterestGroupLogoBean getInterestGroupLogoBean()
	{
		return (InterestGroupLogoBean) getBean(InterestGroupLogoBean.BEAN_NAME);
	}
}
