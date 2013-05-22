/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.space;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.ui.common.Utils;
import org.alfresco.web.ui.common.component.UIListItem;

import eu.cec.digit.circabc.business.api.BusinessStackError;
import eu.cec.digit.circabc.business.api.link.LinksBusinessSrv;
import eu.cec.digit.circabc.business.api.link.ShareSpaceItem;
import eu.cec.digit.circabc.business.api.space.ContainerIcon;
import eu.cec.digit.circabc.business.api.space.DossierBusinessSrv;
import eu.cec.digit.circabc.business.api.space.SpaceBusinessSrv;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.JSFUtils;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;
import eu.cec.digit.circabc.web.wai.dialog.sharespace.ManageShareSpaceDialog;

/**
 * Bean responsible for the create space dialog
 *
 * @author Yanick Pignot
 */
public class CreateSpaceDialog extends BaseWaiDialog
{
	private static final String VALUE_NO_SHARESPACE = "__NO_SHARED_SPACE";

	private static final long serialVersionUID = 8166820634281692712L;

	private static final String MSG_TYPE_SPACE = "library_create_space_type_space";
	private static final String MSG_TYPE_DOSSIER = "library_create_space_type_dossier";
	private static final String MSG_TYPE_SHARED_LINK = "library_create_space_type_shared";

	private static final String MSG_TYPE_SPACE_TOOLTIP = "library_create_space_type_space_tooltip";
	private static final String MSG_TYPE_DOSSIER_TOOLTIP = "library_create_space_type_dossier_tooltip";
	private static final String MSG_TYPE_SHARED_LINK_TOOLTIP = "library_create_space_type_shared_tooltip";

	private static final String MSG_NO_SHARE_SPACE_AVAILABLE = "library_create_space_no_shared_space";

	private static final String TYPE_SPACE = "__TYPE_SPACE";
	private static final String TYPE_DOSSIER = "__TYPE_DOSSIER";
	private static final String TYPE_SHARED_LINK = "__TYPE_SHARED_LINK";

	private Date expirationDate;
	private String description;
	private String title;
	private String spaceIcon;
	private String dossierIcon;
	private String containerType;
	private List<SelectItem> availableSharedSpacesItems;
	private List<SelectItem> typeSelect;
	private String selectedSharedSpace;
	private boolean manageShareAfter;
	private NodeRef createdRef;
	private boolean libraryChild;

	public static final String BEAN_NAME = "CircabcCreateSpaceDialog";

	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(getActionNode() == null)
        {
        	throw new IllegalArgumentException("Node id parameter is mandatory");
        }

        // in the restaure mode, the parameters can be null
		if(parameters != null)
		{
			this.description = null;
			this.title = null;
			this.expirationDate = null;
			this.availableSharedSpacesItems = null;
			this.libraryChild = NavigableNodeType.LIBRARY_CHILD.isNodeFromType(getActionNode());
			this.typeSelect = null;
			this.manageShareAfter = false;
			this.containerType = TYPE_SPACE;

			// init shared spaces
			getAvailableSharedSpaces();
		}
	}

	public boolean isViewExpirationDate()
	{
		return libraryChild;
	}

	public boolean isViewShareThisSpace()
	{
		return libraryChild;
	}

	public boolean isViewDossierPanel()
	{
		return libraryChild;
	}

	public boolean isViewSharePanel()
	{
		return libraryChild;
	}

	public boolean isViewSpacePanel()
	{
		return true;
	}

	public int getStartYear()
	{
		return Calendar.getInstance().get(Calendar.YEAR);
	}


	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		try
		{
			final NodeRef parent = getActionNode().getNodeRef();
			final String activity;

			if(TYPE_DOSSIER.endsWith(containerType))
			{
				createdRef = getDossierBusinessSrv().createDossier(parent, title, description, getDossierIcon());
				activity = "Create Dossier";
			}
			else if(TYPE_SHARED_LINK.endsWith(containerType))
			{
				createdRef = getLinksBusinessSrv().createSharedSpaceLink(parent, new NodeRef(getSelectedSharedSpace()), title, description);
				activity = "Create shared space link";
			}
			else //	if(TYPE_SPACE.endsWith(containerType))
			{
				activity = "Create space";
				createdRef = getSpaceBusinessSrv().createSpace(parent, title, description, spaceIcon, expirationDate);
			}

			//logging
			{
				logRecord.setService(super.getServiceFromActionNode());
				logRecord.setActivity(activity);
				logRecord.setInfo("Title: " + getTitle());
				super.updateLogDocument(createdRef, logRecord);
				logRecord.setOK(true);
				getLogService().log(logRecord);
			}

			return outcome;

		}
		catch(final BusinessStackError validationErrors)
		{
			for(final String msg: validationErrors.getI18NMessages())
			{
				Utils.addErrorMessage(msg);
			}
			this.isFinished = false;
			return null;
		}
	}

	@Override
	protected String doPostCommitProcessing(FacesContext context, String outc)
	{
		final String outcome = super.doPostCommitProcessing(context, outc);

		if(outcome != null && createdRef != null && isManageShareAfter() && TYPE_SPACE.endsWith(containerType))
		{
	        // as we were successful, go to the set properties dialog and set the dialog parameters
	        final Map<String, String> parameters = Collections.singletonMap(BaseWaiDialog.NODE_ID_PARAMETER, createdRef.getId());
	       ((ManageShareSpaceDialog) Beans.getBean(ManageShareSpaceDialog.BEAN_NAME)).init(parameters);

			return
				CircabcNavigationHandler.CLOSE_DIALOG_OUTCOME
				+ CircabcNavigationHandler.OUTCOME_SEPARATOR
				+ ManageShareSpaceDialog.DIALOG_CALL;
		}
		else
		{
			return outcome;
		}
	}

	public List<SelectItem> getTypes()
	{
		if(typeSelect == null)
		{
			typeSelect = new ArrayList<SelectItem>();
			typeSelect.add(new SelectItem(TYPE_SPACE, translate(MSG_TYPE_SPACE), translate(MSG_TYPE_SPACE_TOOLTIP)));

			if(libraryChild)
			{
				typeSelect.add(new SelectItem(TYPE_DOSSIER, translate(MSG_TYPE_DOSSIER), translate(MSG_TYPE_DOSSIER_TOOLTIP)));
				typeSelect.add(new SelectItem(TYPE_SHARED_LINK, translate(MSG_TYPE_SHARED_LINK), translate(MSG_TYPE_SHARED_LINK_TOOLTIP)));
			}
		}

		return typeSelect;
	}

	/**
	 * @return the availableSharedSpaces
	 */
	public final List<SelectItem> getAvailableSharedSpaces()
	{
		if(availableSharedSpacesItems == null)
		{
			if(libraryChild == false)
			{
				availableSharedSpacesItems = Collections.<SelectItem>emptyList();
			}
			else
			{
				final List<ShareSpaceItem> sharedSpaces = getLinksBusinessSrv().getAvailableSharedSpaces(getActionNode().getNodeRef());

				if(sharedSpaces.size() > 0)
				{
					availableSharedSpacesItems = new ArrayList<SelectItem>(sharedSpaces.size());
					for(final ShareSpaceItem sharedSpace: sharedSpaces)
					{
						availableSharedSpacesItems.add(new SelectItem(sharedSpace.getNodeRef().toString(),  sharedSpace.getPath()));
					}
				}
				else
				{
					// make select shared space not availabel
					getTypes().get(2).setDisabled(true);
					final SelectItem emptySelect = new SelectItem(VALUE_NO_SHARESPACE, "<" + translate(MSG_NO_SHARE_SPACE_AVAILABLE) + ">");
					this.availableSharedSpacesItems = Collections.singletonList(emptySelect);
				}
			}

		}

		return availableSharedSpacesItems;
	}

	public String getBrowserTitle()
	{
		return translate("library_create_space_title");
	}

	public String getPageIconAltText()
	{
		return translate("library_create_space_icon_tooltip");
	}

	@Override
	public String getFinishButtonLabel()
	{
		return translate("library_create_space_action");
	}

	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate()
	{
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate)
	{
		this.expirationDate = expirationDate;
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.spaces.CreateSpaceWizard#getDescription()
	 */
	public String getDescription()
	{
		return this.description;
	}


	/**
	 * @return
	 * @see org.alfresco.web.bean.spaces.CreateSpaceWizard#getTitle()
	 */
	public String getTitle()
	{
		return this.title;
	}

	/**
	 * @param description
	 * @see org.alfresco.web.bean.spaces.CreateSpaceWizard#setDescription(java.lang.String)
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @param title
	 * @see org.alfresco.web.bean.spaces.CreateSpaceWizard#setTitle(java.lang.String)
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the icon
	 */
	public final String getSpaceIcon()
	{
		return spaceIcon;
	}

	/**
	 * @param icon the icon to set
	 */
	public final void setSpaceIcon(String icon)
	{
		this.spaceIcon = icon;
	}

	/**
	 * @return the icon
	 */
	public final String getDossierIcon()
	{
		return dossierIcon;
	}

	/**
	 * @param icon the icon to set
	 */
	public final void setDossierIcon(String icon)
	{
		this.dossierIcon = icon;
	}

	public List<UIListItem> getSpaceIcons()
	{
		final List<ContainerIcon> containerIcons = getSpaceBusinessSrv().getSpaceIcons();
		final List<UIListItem> convertLogos = JSFUtils.convertLogos(containerIcons);

		if(this.spaceIcon == null && containerIcons.size() > 0)
		{
			this.spaceIcon = containerIcons.get(0).getIconName();
		}

		return convertLogos;
	}


	public List<UIListItem> getDossierIcons()
	{
		final List<ContainerIcon> containerIcons = getDossierBusinessSrv().getDossierIcons();
		final List<UIListItem> convertLogos = JSFUtils.convertLogos(containerIcons);

		if(this.dossierIcon == null && containerIcons.size() > 0)
		{
			this.dossierIcon = containerIcons.get(0).getIconName();
		}

		return convertLogos;
	}


	/**
	 * @return the selectedSharedSpace
	 */
	public final String getSelectedSharedSpace()
	{
		return selectedSharedSpace;
	}

	/**
	 * @param selectedSharedSpace the selectedSharedSpace to set
	 */
	public final void setSelectedSharedSpace(String selectedSharedSpace)
	{
		this.selectedSharedSpace = selectedSharedSpace;
	}

	/**
	 * @return the manageShareAfter
	 */
	public final boolean isManageShareAfter()
	{
		return manageShareAfter;
	}

	/**
	 * @param manageShareAfter the manageShareAfter to set
	 */
	public final void setManageShareAfter(boolean manageShareAfter)
	{
		this.manageShareAfter = manageShareAfter;
	}

	/**
	 * @return the createSpaceBusinessSrv
	 */
	protected final SpaceBusinessSrv getSpaceBusinessSrv()
	{
		return getBusinessRegistry().getSpaceBusinessSrv();
	}

	/**
	 * @return the dossierBusinessSrv
	 */
	protected final DossierBusinessSrv getDossierBusinessSrv()
	{
		return getBusinessRegistry().getDossierBusinessSrv();
	}

	/**
	 * @return the linksBusinessSrv
	 */
	protected final LinksBusinessSrv getLinksBusinessSrv()
	{
		return getBusinessRegistry().getLinksBusinessSrv();
	}

	/**
	 * @return the containerType
	 */
	public final String getContainerType()
	{
		return containerType;
	}

	/**
	 * @param containerType the containerType to set
	 */
	public final void setContainerType(String containerType)
	{
		this.containerType = containerType;
	}
}
