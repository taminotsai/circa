package eu.cec.digit.circabc.web.wai.dialog.audit;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;

import eu.cec.digit.circabc.repo.log.LogSearchResultDAO;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

public class DocumentHistoryDialog extends BaseWaiDialog
{

	private static final String CONTAINER_TITLE = "history_dialog_title";
	private static final String CONTAINER_DESC = "history_dialog_description";

	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);
		Long documentID = (Long) getNodeService().getProperty(getActionNode().getNodeRef(), ContentModel.PROP_NODE_DBID) ;
		setAuditList(getLogService().getHistory(documentID));

	}

	private static final long serialVersionUID = 6196990576366355844L;

	private List<LogSearchResultDAO> auditList;

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Throwable
	{
		// nothing to do
		return null;
	}

	public String getBrowserTitle()
	{
		return translate("history_dialog_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("history_dialog_icon_tooltip");
	}

	@Override
	public String getCancelButtonLabel()
	{
		return translate("close");
	}

	@Override
	public String getContainerDescription()
	{
		return translate(CONTAINER_DESC, getActionNode().getName());
	}

	@Override
	public String getContainerTitle()
	{
		return translate(CONTAINER_TITLE);
	}

	/**
	 * @param auditList the auditList to set
	 */
	public void setAuditList(List<LogSearchResultDAO> auditList)
	{
		this.auditList = auditList;
	}

	/**
	 * @return the auditList
	 */
	public List<LogSearchResultDAO> getAuditList()
	{
		return auditList;
	}



}
