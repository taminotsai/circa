/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.admin;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.alfresco.web.app.context.UIContextService;
import org.alfresco.web.bean.categories.CategoriesDialog;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.ui.common.component.data.UIRichList;

import eu.cec.digit.circabc.service.profile.permissions.CategoryPermissions;
import eu.cec.digit.circabc.service.profile.permissions.CircabcRootPermissions;
import eu.cec.digit.circabc.service.profile.permissions.DirectoryPermissions;
import eu.cec.digit.circabc.service.profile.permissions.EventPermissions;
import eu.cec.digit.circabc.service.profile.permissions.InformationPermissions;
import eu.cec.digit.circabc.service.profile.permissions.LibraryPermissions;
import eu.cec.digit.circabc.service.profile.permissions.NewsGroupPermissions;
import eu.cec.digit.circabc.service.profile.permissions.SurveyPermissions;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.bean.override.CircabcBrowseBean;
import eu.cec.digit.circabc.web.repository.CircabcRootNode;
import eu.cec.digit.circabc.web.repository.InterestGroupNode;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;


/**
 *	Bean that backs the administration console
 *
 * @author Yanick Pignot
 */
public class AdministrationConsole extends BaseWaiDialog
{

	private static final String CLOSE = "close";

	private static final String ADMIN_CONSOLE_DIALOG_ICON_TOOLTIP = "admin_console_dialog_icon_tooltip";

	private static final String ADMIN_CONSOLE_DIALOG_BROWSER_TITLE = "admin_console_dialog_browser_title";

	private static final long serialVersionUID = 4283648437777451014L;

	/** Public JSF Bean name */
	public static final String BEAN_NAME = "CircabcAdminConsoleDialog";

	private Boolean viewSuperAdminRootConsole = null;
	private Boolean viewAdminRootConsole = null;
	private Boolean viewAdminHeaderConsole = null;
	private Boolean viewCategoryConsole = null;
	private Boolean viewIgConsole = null;
	private Boolean viewLibChildConsole = null;
	private Boolean viewForumChildConsole = null;
	private Boolean viewSurveyChildConsole = null;
	private Boolean viewInformationChildConsole = null;
	private Boolean viewNewsConsole = null;
	private Boolean viewSurveyConsole = null;
	private Boolean viewLibraryConsole = null;
	private Boolean viewInformationConsole = null;
	private Boolean viewEventConsole = null;
	private Boolean viewOtherServiceConsole = null;

	@Override
    public void init(final Map<String, String> parameters)
    {
        super.init(parameters);

        // clear the cache
        this.viewSuperAdminRootConsole = null;
        this.viewAdminRootConsole = null;
        this.viewAdminHeaderConsole = null;
        this.viewCategoryConsole = null;
        this.viewIgConsole = null;
        this.viewLibChildConsole = null;
        this.viewForumChildConsole = null;
        this.viewSurveyChildConsole = null;
        this.viewInformationChildConsole = null;
        this.viewNewsConsole = null;
        this.viewSurveyConsole = null;
    	this.viewLibraryConsole = null;
    	this.viewInformationConsole = null;
    	this.viewEventConsole = null;
    	this.viewOtherServiceConsole = null;

    	final CategoriesDialog categoriesDialog = (CategoriesDialog) Beans.getBean("CategoriesDialog");
    	if(categoriesDialog.getCategoriesRichList() == null)
    	{
    		final UIRichList richList = new UIRichList();
    		richList.setViewMode("details");
			categoriesDialog.setCategoriesRichList(richList);
    	}

        if(parameters == null)
		{
			return;
		}

        //in the restaure mode, the parameters can be null
        if(parameters != null)
		{
	        final String id  = parameters.get(NODE_ID_PARAMETER);

			// test if the application is in right state (id should be equals to the current node)
			if(!getNavigator().getCurrentNodeId().equals(id))
			{
				// reset permission cache on the action node
		        getActionNode().reset();
				// the verfication of id parameter will be perfom here
				getNavigator().setCurrentNodeId(id);
				// reset permission cache on all navigation node
		        getNavigator().updateCircabcNavigationContext();

				UIContextService.getInstance(FacesContext.getCurrentInstance()).spaceChanged();
			}
		}


    }

	public String getMigrationURL()
	{
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return  req.getContextPath() + "/faces/jsp/extension/wai/dialog/admin/migration/migration-console.jsp";
	}
	
	public boolean isDisplayRootMenuForSuperAdmin()
	{
		if(viewSuperAdminRootConsole == null)
		{
			viewSuperAdminRootConsole = Boolean.valueOf(isSuperAdmin() && isCurrentNodeCircabc());
		}

		return viewSuperAdminRootConsole.booleanValue();
	}

	public boolean isDisplayRootMenuForCircabcAdmin()
	{
		if(viewAdminRootConsole == null)
		{
			if(!isSuperAdmin() && isCurrentNodeCircabc())
			{
				final CircabcRootNode root = getNavigator().getCircabcHomeNode();

				viewAdminRootConsole = Boolean.valueOf(
						root.hasPermission(CircabcRootPermissions.CIRCABCMANAGEMEMBERS.toString())
							|| root.hasPermission(CircabcRootPermissions.CIRCABCADMIN.toString()));
			}
			else
			{
				viewAdminRootConsole =   Boolean.FALSE;
			}
		}

		return viewAdminRootConsole.booleanValue();

	}

	public boolean isDisplayHeaderMenuForAdmin()
	{
		if(viewAdminHeaderConsole == null)
		{
			if(isCurrentNodeHeader())
			{
				final CircabcRootNode root = getNavigator().getCircabcHomeNode();

				viewAdminHeaderConsole = isSuperAdmin() || Boolean.valueOf(root.hasPermission(CircabcRootPermissions.CIRCABCADMIN.toString()));
			}
			else
			{
				viewAdminHeaderConsole =   Boolean.FALSE;
			}
		}

		return viewAdminHeaderConsole.booleanValue();

	}

	public boolean isDisplayCategoryMenu()
	{
		if(viewCategoryConsole == null)
		{
			if(!isSuperAdmin() && isCurrentNodeCategory())
			{
				final Node cat = getNavigator().getCurrentCategory();

				viewCategoryConsole = Boolean.valueOf(
						cat.hasPermission(CategoryPermissions.CIRCACATEGORYMANAGEMEMBERS.toString())
							|| cat.hasPermission(CategoryPermissions.CIRCACATEGORYADMIN.toString()));

			}
			else
			{
				viewCategoryConsole = Boolean.FALSE ;
			}
		}

		return viewCategoryConsole.booleanValue();
	}

	public boolean isDisplayNewsgroupMenu()
	{
		if(viewNewsConsole == null)
		{
			if(!isSuperAdmin() && isCurrentNodeNewsgroup())
			{
				final Node news = getNavigator().getCurrentNode();

				viewNewsConsole = Boolean.valueOf(
						news.hasPermission(NewsGroupPermissions.NWSMODERATE.toString())
							|| news.hasPermission(NewsGroupPermissions.NWSADMIN.toString()));

			}
			else
			{
				viewNewsConsole = Boolean.FALSE ;
			}
		}

		return viewNewsConsole.booleanValue();
	}

	public boolean isDisplaySurveyMenu()
	{
		if(viewSurveyConsole == null)
		{
			if(!isSuperAdmin() && isCurrentNodeSurvey())
			{
				final Node survey = getNavigator().getCurrentNode();

				viewSurveyConsole = Boolean.valueOf(
						survey.hasPermission(SurveyPermissions.SURADMIN.toString()));
			}
			else
			{
				viewSurveyConsole = Boolean.FALSE ;
			}
		}

		return viewSurveyConsole.booleanValue();
	}

	public boolean isDisplayOtherServiceConsole()
	{
		if(viewOtherServiceConsole == null)
		{
			boolean isAdmin = false;
			final InterestGroupNode ig = (InterestGroupNode) getNavigator().getCurrentIGRoot();
			if(!isSuperAdmin() && ig != null)
			{
				if(ig.getDirectory() != null && ig.hasPermission(DirectoryPermissions.DIRMANAGEMEMBERS.toString()))
				{
					isAdmin = true;
				}
				else if(ig.getLibrary() != null && ig.getLibrary().hasPermission(LibraryPermissions.LIBADMIN.toString()))
				{
					isAdmin = true;
				}
				else if(ig.getNewsgroup() != null && ig.getNewsgroup().hasPermission(NewsGroupPermissions.NWSMODERATE.toString()))
				{
					isAdmin = true;
				}
				else if(ig.getEvent() != null && ig.getEvent().hasPermission(EventPermissions.EVEADMIN.toString()))
				{
					isAdmin = true;
				}
				else if(ig.getInformation() != null && ig.getInformation().hasPermission(InformationPermissions.INFMANAGE.toString()))
				{
					isAdmin = true;
				}
				else if(ig.getSurvey() != null && ig.getSurvey().hasPermission(SurveyPermissions.SURADMIN.toString()))
				{
					isAdmin = true;
				}
			}
			viewOtherServiceConsole = Boolean.valueOf(isAdmin);
		}
		return viewOtherServiceConsole;
	}

	public boolean isDisplayIgMenu()
	{
		if(viewIgConsole == null)
		{
			boolean isAdmin = false;

			if(!isSuperAdmin() && isCurrentNodeInterestGroup())
			{
				final InterestGroupNode ig = (InterestGroupNode) getNavigator().getCurrentIGRoot();

				if(ig != null)
				{
					if(ig.getDirectory() != null && ig.hasPermission(DirectoryPermissions.DIRMANAGEMEMBERS.toString()))
					{
						isAdmin = true;
					}
					else if(ig.getLibrary() != null && ig.getLibrary().hasPermission(LibraryPermissions.LIBADMIN.toString()))
					{
						isAdmin = true;
					}
					else if(ig.getNewsgroup() != null && ig.getNewsgroup().hasPermission(NewsGroupPermissions.NWSADMIN.toString()))
					{
						isAdmin = true;
					}
					else if(ig.getEvent() != null && ig.getEvent().hasPermission(EventPermissions.EVEADMIN.toString()))
					{
						isAdmin = true;
					}
					else if(ig.getInformation() != null && ig.getInformation().hasPermission(InformationPermissions.INFADMIN.toString()))
					{
						isAdmin = true;
					}
					else if(ig.getSurvey() != null && ig.getSurvey().hasPermission(SurveyPermissions.SURADMIN.toString()))
					{
						isAdmin = true;
					}
				}
			}

			viewIgConsole = Boolean.valueOf(isAdmin);
		}

		return viewIgConsole.booleanValue();
	}

	public boolean isDisplayLibraryChildMenu()
	{
		if(viewLibChildConsole == null)
		{
			if(isCurrentNodeLibraryChild())
			{
				viewLibChildConsole = Boolean.valueOf(getNavigator().getCurrentNode().hasPermission(LibraryPermissions.LIBADMIN.toString()));
			}
			else
			{
				viewLibChildConsole = Boolean.FALSE ;
			}
		}
		return viewLibChildConsole.booleanValue();
	}

	public boolean isDisplayForumChildMenu()
	{
		if(viewForumChildConsole == null)
		{
			if(isCurrentNodeForumChild())
			{
				viewForumChildConsole = Boolean.valueOf(getNavigator().getCurrentNode().hasPermission(NewsGroupPermissions.NWSADMIN.toString()));
			}
			else
			{
				viewForumChildConsole = Boolean.FALSE;
			}
		}
		return viewForumChildConsole.booleanValue();
	}

	public boolean isDisplaySurveyChildSpaceMenu()
	{
		if(viewSurveyChildConsole == null)
		{
			if(isCurrentNodeSurveyChild())
			{
				viewSurveyChildConsole = Boolean.valueOf(getNavigator().getCurrentNode().hasPermission(SurveyPermissions.SURADMIN.toString()));
			}
			else
			{
				viewSurveyChildConsole = Boolean.FALSE;
			}
		}
		return viewSurveyChildConsole.booleanValue();
	}

	public boolean isDisplayInformationChildSpaceMenu()
	{
		if(viewInformationChildConsole == null)
		{
			if(isCurrentNodeInformationChild())
			{
				viewInformationChildConsole = Boolean.valueOf(getNavigator().getCurrentNode().hasPermission(InformationPermissions.INFADMIN.toString()));
			}
			else
			{
				viewInformationChildConsole = Boolean.FALSE;
			}
		}
		return viewInformationChildConsole.booleanValue();
	}
	public boolean isDisplayLibraryMenu()
	{
		if(viewLibraryConsole == null)
		{
			if(isCurrentNodeLibraryRoot())
			{
				viewLibraryConsole = Boolean.valueOf(getNavigator().getCurrentNode().hasPermission(LibraryPermissions.LIBADMIN.toString()));
			}
			else
			{
				viewLibraryConsole = Boolean.FALSE;
			}
		}
		return viewLibraryConsole.booleanValue();
	}

	public boolean isDisplayInformationMenu()
	{
		if(viewInformationConsole == null)
		{
			if(!isSuperAdmin() && isCurrentNodeInformationRoot())
			{
				final Node inf = getNavigator().getCurrentNode();

				viewInformationConsole =
					Boolean.valueOf(inf.hasPermission(InformationPermissions.INFMANAGE.toString()))
					|| Boolean.valueOf(inf.hasPermission(InformationPermissions.INFADMIN.toString()));

			}
			else
			{
				viewInformationConsole = Boolean.FALSE ;
			}
		}

		return viewInformationConsole.booleanValue();
	}

	public boolean isDisplayEventsMenu()
	{
		if(viewEventConsole == null)
		{
			if(!isSuperAdmin() && isCurrentNodeEventsRoot())
			{
				final Node eve = getNavigator().getCurrentNode();

				viewEventConsole =
					Boolean.valueOf(eve.hasPermission(EventPermissions.EVEADMIN.toString()));
			}
			else
			{
				viewEventConsole = Boolean.FALSE ;
			}
		}

		return viewEventConsole.booleanValue();
	}

	private boolean isSuperAdmin()
	{
		return getNavigator().getCurrentUser().isAdmin();
	}


	private boolean isCurrentNodeHeader()
	{
		return isCurrentNodeOfType(NavigableNodeType.CATEGORY_HEADER);
	}

	private boolean isCurrentNodeCircabc()
	{
		return isCurrentNodeOfType(NavigableNodeType.CIRCABC_ROOT);
	}

	private boolean isCurrentNodeCategory()
	{
		return isCurrentNodeOfType(NavigableNodeType.CATEGORY);
	}

	private boolean isCurrentNodeInterestGroup()
	{
		return isCurrentNodeOfType(NavigableNodeType.IG_ROOT);
	}

	private boolean isCurrentNodeLibraryChild()
	{
		return isCurrentNodeOfSubtypeType(NavigableNodeType.LIBRARY, NavigableNodeType.LIBRARY_CHILD);
	}

	private boolean isCurrentNodeForumChild()
	{
		return isCurrentNodeOfSubtypeType(NavigableNodeType.NEWSGROUP, NavigableNodeType.NEWSGROUP_CHILD);
	}

	private boolean isCurrentNodeSurveyChild()
	{
		return isCurrentNodeOfSubtypeType(NavigableNodeType.SURVEY, NavigableNodeType.SURVEY_CHILD);
	}

	private boolean isCurrentNodeInformationChild()
	{
		return isCurrentNodeOfSubtypeType(NavigableNodeType.INFORMATION, NavigableNodeType.INFORMATION_CHILD);
	}

	private boolean isCurrentNodeLibraryRoot()
	{
		return isCurrentNodeOfType(NavigableNodeType.LIBRARY);
	}


	private boolean isCurrentNodeNewsgroup()
	{
		return isCurrentNodeOfType(NavigableNodeType.NEWSGROUP);
	}

	private boolean isCurrentNodeSurvey()
	{
		return isCurrentNodeOfType(NavigableNodeType.SURVEY);
	}

	private boolean isCurrentNodeInformationRoot()
	{
		return isCurrentNodeOfType(NavigableNodeType.INFORMATION);
	}

	private boolean isCurrentNodeEventsRoot()
	{
		return isCurrentNodeOfType(NavigableNodeType.EVENT);
	}

	private boolean isCurrentNodeOfType(NavigableNodeType targetType)
	{
		final NavigableNodeType type = getNavigator().getCurrentNodeType();

		return type != null && type.equals(targetType);
	}

	private boolean isCurrentNodeOfSubtypeType(final NavigableNodeType rootType, final NavigableNodeType subType)
	{
		final NavigableNodeType type = getNavigator().getCurrentNodeType();

		return type != null
				&& (subType.equals(type) == true || type.getRequireNodeType() != null && type.getRequireNodeType().equals(subType) == true)
				&& rootType.equals(type) == false;

	}

	@Override
	public String cancel()
	{
		// Refresh the navigation...
		getBrowseBean().refreshBrowsing();

		return super.cancel()
					+ CircabcNavigationHandler.OUTCOME_SEPARATOR
					+ CircabcBrowseBean.WAI_BROWSE_OUTCOME;
	}

	@Override
	public void restored()
	{
		// refresh the state of the dialog
		this.init(null);
	}

	@Override
	protected String finishImpl(final FacesContext context, final String outcome) throws Exception
	{
		// nothing to do
		return outcome;
	}

	public String getBrowserTitle()
	{
		return translate(ADMIN_CONSOLE_DIALOG_BROWSER_TITLE);
	}

	public String getPageIconAltText()
	{
		return translate(ADMIN_CONSOLE_DIALOG_ICON_TOOLTIP);
	}

	@Override
	public String getCancelButtonLabel()
	{
		return translate(CLOSE);
	}
}
