package eu.cec.digit.circabc.web.wai.dialog.sharespace;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.business.api.link.LinksBusinessSrv;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

public class RemoveIGShareSpaceDialog extends BaseWaiDialog
{

	/**
	 *
	 */
	private static final long serialVersionUID = 156220716021110795L;

	private NodeRef shareSpace;

	private NodeRef interestGroup;
	private String  interestGroupName;

	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null)
		{
			final String igAsString =  getParameter(parameters, ManageShareSpaceDialog.INTEREST_GROUP_ID_PARAMETER);
			interestGroup = new NodeRef(igAsString);

			final String shareSpaceAsString = getParameter(parameters, ManageShareSpaceDialog.SHARE_SPACE_ID_PARAMETER);
			shareSpace = new NodeRef(shareSpaceAsString);
			interestGroupName= getParameter(parameters, ManageShareSpaceDialog.INTEREST_GROUP_NAME_PARAMETER);

		}
	}

	private String getParameter(Map<String, String> parameters, String key)
	{
		final String value = parameters.get(key );
		if(value == null)
		{
			throw new IllegalArgumentException("Parameter is missing: " + key);
		}
		return value;
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Throwable
	{
		super.updateLogDocument(shareSpace, logRecord);
		final String spaceName = (String) getNodeService().getProperty(shareSpace, ContentModel.PROP_NAME);
		final String info = "Removed interest group " + interestGroupName  + " from space " + spaceName ;
		logRecord.setInfo(info);
		this.getLinksBusinessSrv().removeSharing(shareSpace, interestGroup);
		return outcome;


	}

	public String getCancelButtonLabel()
	   {
	      return translate("no");
	   }

	   public String getFinishButtonLabel()
	   {
		   return translate("yes");
	   }

	public String getBrowserTitle()
	{
		return translate("remove_ig_dialog_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("remove_ig_dialog_icon_tooltip");
	}

	/**
	 * @param interestGroupName the interestGroupName to set
	 */
	public void setInterestGroupName(String interestGroupName)
	{
		this.interestGroupName = interestGroupName;
	}

	/**
	 * @return the interestGroupName
	 */
	public String getInterestGroupName()
	{
		return interestGroupName;
	}

	/**
	 * @return the linksBusinessSrv
	 */
	protected LinksBusinessSrv getLinksBusinessSrv()
	{
		return getBusinessRegistry().getLinksBusinessSrv();
	}
}
