/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.dialog.headers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.search.CategoryService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.ui.common.Utils;

import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.wai.bean.navigation.CategoryHeadersBeanData;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;


/**
 * Bean implementation of the "Add Catgory Header Dialog".
 *
 * @author Yanick Pignot
 */
public class AddCategoryHeaderDialog extends BaseWaiDialog
{

	private static final long serialVersionUID = 8736487953006951363L;

	public static final String BEAN_NAME ="AddCategoryHeaderDialog";

	protected static final String FILENAME_REGEX = "(.*[\\\"\\*\\\\\\>\\<\\?\\/\\:\\|]+.*)|(.*[\\.]?.*[\\.]+$)|(.*[ ]+$)";

	private static final String MSG_DUPLICATE_NODE = "create_node_duplicate_name";
	private static final String MSG_EMPTY_NAME = "create_cat_header_dialog_error_empty_name";
	private static final String MSG_INVALLID_NAME = "create_cat_header_dialog_error_invalid_name";
	private static final String MSG_NO_ROOT = "create_cat_header_dialog_error_no_root";

	private static final String MSG_SUCCESS = "create_cat_header_dialog_success";

	private String name;
	private String description;

	private CategoryService categoryService;


	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		name = null;
		description = null;
	}


	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		if(!validate())
		{
			// error were found
			return null;
		}

		NodeRef rootHeader = getManagementService().getRootCategoryHeader();

		// create category using categoryservice
        NodeRef ref = getCategoryService().createCategory(rootHeader, name.trim());

        Map<QName, Serializable> titledProps = new HashMap<QName, Serializable>(1, 1.0f);
        // apply the titled aspect - for description
        titledProps.put(ContentModel.PROP_DESCRIPTION, description);
        getNodeService().addAspect(ref, ContentModel.ASPECT_TITLED, titledProps);

        String confirmation = translate(MSG_SUCCESS, name);

        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, confirmation, confirmation);
        context.addMessage(null, facesMsg);
        
        //reset cache
        final CategoryHeadersBeanData categoryHeadersBeanData = (CategoryHeadersBeanData) Beans.getBean("CategoryHeadersBeanData");
        categoryHeadersBeanData.reset();

        return CircabcNavigationHandler.CLOSE_WAI_DIALOG_OUTCOME;
	}


	protected boolean validate()
	{
		boolean valid = true;

		if(getManagementService().getRootCategoryHeader() == null)
		{
			Utils.addErrorMessage(translate(MSG_NO_ROOT));
			valid = false;
		}

		//	test if the name is not empty
		if (getName() == null || getName().trim().length() < 1)
        {
			Utils.addErrorMessage(translate(MSG_EMPTY_NAME));
			valid = false;
        }

		//	test if the name is correct
		if (getName().matches(FILENAME_REGEX))
        {
			Utils.addErrorMessage(translate(MSG_INVALLID_NAME));
			valid = false;
        }

		if(valid)
		{
			final List<NodeRef> existingCat = getManagementService().getCategories();
			Serializable catName = null;

			for (NodeRef ref : existingCat)
			{
				catName = getNodeService().getProperty(ref, ContentModel.PROP_NAME);
				if(name.equalsIgnoreCase((String) catName))
				{
					Utils.addErrorMessage(translate(MSG_DUPLICATE_NODE));
					valid = false;
					break;
				}
			}

		}

		return valid;
	}



	public String getBrowserTitle()
	{
		return translate("create_cat_header_dialog_browser_title");
	}


	public String getPageIconAltText()
	{
		return translate("create_cat_header_dialog_icon_alt");
	}


	@Override
	public String getFinishButtonLabel()
	{
		return translate("create_cat_header_button_create");
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}


	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	  * @param categoryService  The CategoryService to set.
	  */
	public void setCategoryService(CategoryService categoryService)
	{
	    this.categoryService = categoryService;
	}

	protected CategoryService getCategoryService()
	{
	   if (categoryService == null)
	   {
	      categoryService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getCategoryService();
	   }
	   return categoryService;
	}
}
