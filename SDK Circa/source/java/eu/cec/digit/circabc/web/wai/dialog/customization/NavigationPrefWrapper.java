/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.customization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.PathNotFoundException;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.repo.struct.SimplePath;
import eu.cec.digit.circabc.service.customisation.nav.ColumnConfig;
import eu.cec.digit.circabc.service.customisation.nav.NavigationPreference;
import eu.cec.digit.circabc.service.customisation.nav.NavigationPreferencesService;
import eu.cec.digit.circabc.service.customisation.nav.ServiceConfig;
import eu.cec.digit.circabc.service.profile.permissions.CategoryPermissions;
import eu.cec.digit.circabc.service.profile.permissions.InformationPermissions;
import eu.cec.digit.circabc.service.profile.permissions.LibraryPermissions;
import eu.cec.digit.circabc.service.profile.permissions.NewsGroupPermissions;
import eu.cec.digit.circabc.web.WebClientHelper;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.repository.InterestGroupNode;

/**
 * @author Yanick Pignot
 *
 */
public class NavigationPrefWrapper implements Serializable
{
	/*package*/ static final String MSG_CURRENT_NODE = "manage_navigation_dialog_currentNode";
	/*package*/ static final String MSG_UNDEFINED = "manage_navigation_dialog_undefined";
	private static final String MSG_PREFIX_SERVICE = "manage_navigation_dialog_service_";
	private static final String MSG_PREFIX_TYPE = "manage_navigation_dialog_type_";
	/** */
	private static final long serialVersionUID = -599623620499393697L;

	final NavigationPreference preferences;
	final NodeService nodeService;
	final Node currentNode;
	/*package*/ NavigationPrefWrapper(final NavigationPreference preferences, final NodeService nodeService, final Node currentNode)
	{
		this.preferences = preferences;
		this.nodeService = nodeService;
		this.currentNode = currentNode;
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.customisation.nav.NavigationPreference#getActions()
	 */
	public List<String> getActions()
	{
		return preferences.getActions();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.customisation.nav.NavigationPreference#getActions()
	 */
	public int getActionsNumber()
	{
		return preferences.getActions().size();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.customisation.nav.NavigationPreference#getColumns()
	 */
	public List<String> getColumns()
	{
		final List<ColumnConfig> columns = preferences.getColumns();
		final List<String> columnsLabel = new ArrayList<String>(columns.size());
		for (final ColumnConfig column : columns)
		{
			columnsLabel.add(translate(column.getLabel()));
		}
		return columnsLabel;
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.customisation.nav.NavigationPreference#getColumns()
	 */
	public int getColumnsNumber()
	{
		return preferences.getColumns().size();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.customisation.nav.NavigationPreference#getCustomizedOn()
	 */
	public NodeRef getCustomizedOn()
	{
		return preferences.getCustomizedOn();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.customisation.nav.NavigationPreference#getCustomizedOn()
	 */
	public String getCustomizedOnPath()
	{
		final NodeRef customizedOn = getCustomizedOn();

		if(customizedOn.equals(currentNode.getNodeRef()))
		{
			return translate(MSG_CURRENT_NODE);
		}
		else
		{
			SimplePath path;
			try
			{
				path = new SimplePath(nodeService, customizedOn);
				return path.toString();
			}
			catch (PathNotFoundException e)
			{
				return "<i>" + translate(MSG_UNDEFINED) + "</i>";
			}
		}

	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.customisation.nav.NavigationPreference#getInitialSortColumn()
	 */
	public String getInitialSortColumn()
	{
		return translate(preferences.getInitialSortColumn().getLabel());
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.customisation.nav.NavigationPreference#getLinkTarget()
	 */
	public String getLinkTarget()
	{
		return preferences.getLinkTarget();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.customisation.nav.NavigationPreference#getListSize()
	 */
	public Integer getListSize()
	{
		return preferences.getListSize();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.customisation.nav.NavigationPreference#getService()
	 */
	public String getService()
	{


		return translate(MSG_PREFIX_SERVICE + getServiceName());
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.customisation.nav.NavigationPreference#getService()
	 */
	public String getServiceName()
	{
		final ServiceConfig service = preferences.getService();

		return service.getName();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.customisation.nav.NavigationPreference#getService()
	 */
	public String getType()
	{
		return translate(MSG_PREFIX_TYPE + getTypeName());
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.customisation.nav.NavigationPreference#getService()
	 */
	public String getTypeName()
	{
		final ServiceConfig service = preferences.getService();

		return service.getType();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.customisation.nav.NavigationPreference#getViewMode()
	 */
	public String getViewMode()
	{
		return preferences.getViewMode();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.customisation.nav.NavigationPreference#isDisplayActionColumn()
	 */
	public boolean isDisplayActionColumn()
	{
		return preferences.isDisplayActionColumn();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.customisation.nav.NavigationPreference#isInitialSortDescending()
	 */
	public boolean isInitialSortDescending()
	{
		return preferences.isInitialSortDescending();
	}

	public boolean isEditable()
	{
		final String service = preferences.getService().getName();


		if(NavigableNodeType.CATEGORY.isNodeFromType(currentNode))
		{
			return currentNode.hasPermission(CategoryPermissions.CIRCACATEGORYADMIN.toString());
		}
		else if(NavigableNodeType.IG_ROOT.isNodeFromType(currentNode))
		{
			final InterestGroupNode igNode = new InterestGroupNode(currentNode.getNodeRef());

			if(service.equals(NavigationPreferencesService.LIBRARY_SERVICE))
			{
				return igNode.getLibrary().hasPermission(LibraryPermissions.LIBADMIN.toString());
			}
			else if(service.equals(NavigationPreferencesService.NEWSGROUP_SERVICE))
			{
				return igNode.getNewsgroup().hasPermission(NewsGroupPermissions.NWSADMIN.toString());
			}
			else if(service.equals(NavigationPreferencesService.INFORMATION_SERVICE))
			{
				return igNode.getInformation().hasPermission(InformationPermissions.INFADMIN.toString());
			}
			else
			{
				return false;
			}
		}
		else
		{
			if(service.equals(NavigationPreferencesService.LIBRARY_SERVICE))
			{
				return currentNode.hasPermission(LibraryPermissions.LIBADMIN.toString());
			}
			else if(service.equals(NavigationPreferencesService.NEWSGROUP_SERVICE))
			{
				return currentNode.hasPermission(NewsGroupPermissions.NWSADMIN.toString());
			}
			else if(service.equals(NavigationPreferencesService.INFORMATION_SERVICE))
			{
				return currentNode.hasPermission(InformationPermissions.INFADMIN.toString());
			}
			else
			{
				return false;
			}
		}
	}

	public boolean isRemovable()
	{
		// return if the customization is done on the current node
		return currentNode.getNodeRef().equals(preferences.getCustomizedOn());
	}


	private String translate(final String message)
	{
		return WebClientHelper.translate(message);
	}
}
