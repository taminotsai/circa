/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.dialog.sharespace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.business.api.link.InterestGroupItem;
import eu.cec.digit.circabc.business.api.link.LinksBusinessSrv;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

public class ManageShareSpaceDialog extends BaseWaiDialog
{
	private static final long serialVersionUID = 5508006034551532971L;

	/** Public JSF Bean name */
	public static final String BEAN_NAME = "ManageShareSpaceDialog";

	public static final String DIALOG_NAME = "manageShareSpaceDialog";
	public static final String DIALOG_CALL = CircabcNavigationHandler.WAI_DIALOG_PREFIX + DIALOG_NAME;

	public  static final String INTEREST_GROUP_ID_PARAMETER = "interestGroupID";
	public  static final String SHARE_SPACE_ID_PARAMETER = "shareSpaceID";
	public  static final String INTEREST_GROUP_NAME_PARAMETER = "interestGroupName";

	private List<WebInterestgroupItem> invitedInterestGroups;

	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null)
		{
			if(getActionNode() == null)
			{
				throw new IllegalArgumentException("Node id is a mandatory parameter");
			}

			fillItems();
		}
	}

	private void fillItems()
	{
		final NodeRef nodeRef = getActionNode().getNodeRef();

		final List<InterestGroupItem> interestGroups = getLinksBusinessSrv().getInvitationsForSharing(nodeRef);
		if(interestGroups != null)
		{
			invitedInterestGroups = new ArrayList<WebInterestgroupItem>(interestGroups.size());
			for(final InterestGroupItem item: interestGroups)
			{
				invitedInterestGroups.add(new WebInterestgroupItem(item, getIgTitle(item.getNodeRef(), item.getName())));
			}
		}
		else
		{
			invitedInterestGroups = Collections.<WebInterestgroupItem>emptyList();
		}
	}

	private String getIgTitle(final NodeRef id, final String name)
	{

		final String title = (String) AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>()
		{
			public Object doWork()
			{
				final String title = (String) getNodeService().getProperty(id, ContentModel.PROP_TITLE);

				if (title == null || title.trim().length() < 1)
				{
					return name;
				} else
				{
					return title;
				}
			}
		}, AuthenticationUtil.getAdminUserName());
		return title;
	}


	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Throwable
	{
		// Nothing to do
		return null;
	}
	@Override
	public void restored()
	{

		fillItems();
	}

	@Override
	public String getCancelButtonLabel()
	{
		return translate("close");
	}


	public String getBrowserTitle()
	{
		return translate("manage_share_space_dialog_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("manage_share_space_dialog_icon_tooltip");
	}

	/**
	 * @return the invitedInterestGroups
	 */
	public List<WebInterestgroupItem> getInvitedInterestGroups()
	{
		return invitedInterestGroups;
	}

	protected LinksBusinessSrv getLinksBusinessSrv()
	{
		return getBusinessRegistry().getLinksBusinessSrv();
	}

}
