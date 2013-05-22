/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.dialog.ml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.Path;
import org.alfresco.web.app.AlfrescoNavigationHandler;
import org.alfresco.web.app.Application;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.service.log.LogRecord;
import eu.cec.digit.circabc.service.log.LogService;
import eu.cec.digit.circabc.service.struct.ManagementService;
import eu.cec.digit.circabc.util.PathUtils;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.wai.dialog.WaiDialog;
import eu.cec.digit.circabc.web.wai.manager.ActionsListWrapper;

/**
 * Dialog bean to create a new edition
 *
 * @author Guillaume
 */
public class NewEditionDialog extends org.alfresco.web.bean.ml.NewEditionWizard implements WaiDialog
{
	private static final long serialVersionUID = -5372740078894071426L;

	public static final String BEAN_NAME = "CircabcNewEditionDialog";

	private transient LogService logService;
    private transient ManagementService managementService;

    protected LogRecord logRecord = new LogRecord();


	/**
	 * @see org.alfresco.web.bean.ml.NewEditionWizard#init(Map<String, String>)
	 */
	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		// To get the new edition ref via browseBean.getDocument();
		setOtherProperties(true);
	}

	/**
	 * Get the list of the translated documents availiable for the next pivot language
	 *
	 * @return List of translated documents availiable for the next pivot language
	 */
	public SelectItem[] getTranslatedDocuments()
	{
		List<SelectItem> items = new ArrayList<SelectItem>();

		DataModel dataModel = getAvailableTranslationsDataModel();
		@SuppressWarnings("unchecked")
		ArrayList<TranslationWrapper> list = (ArrayList<TranslationWrapper>) dataModel.getWrappedData();

		for (TranslationWrapper wrapper : list)
		{
			items.add(new SelectItem(wrapper.getLanguage(), wrapper.getName()));
		}

		return items.toArray(new SelectItem[items.size()]);
	}

    /**
     * Returns the properties for checked out translations JSF DataModel
     *
     *We need to destroy the list alfreco has constructed
     *
     * @return List representing the translations checked out
     */
    public List getListTranslationsCheckedOutDataModel()
    {

    	return (List) getTranslationsCheckedOutDataModel().getWrappedData();
    }

	/**
	 * @see org.alfresco.web.bean.ml.NewEditionWizard#finishImpl(FacesContext, String)
	 */
	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		outcome = super.finishImpl(context, outcome);

		this.navigator.setCurrentNodeId(this.browseBean.getDocument().getId());

		return AlfrescoNavigationHandler.CLOSE_DIALOG_OUTCOME;
	}

	/**
	 * @see org.alfresco.web.bean.dialog.BaseDialogBean#doPostCommitProcessing(javax.faces.context.FacesContext, java.lang.String)
	 */
	@Override
	protected String doPostCommitProcessing(FacesContext context, String outcome)
	{
		if (outcome == null)
		{
			// Previous error -> redisplay
			this.isFinished =false;
			return null;
		}
		// todo fix path if possible
		setLogRecord(this.browseBean.getDocument());
		logRecord.setService("Library");
		logRecord.setActivity("Create new edition");
		logRecord.setOK(true);
		logRecord.setInfo("Created a new version based on " + this.browseBean.getDocument().getName());
	    getLogService().log(logRecord);

		// close the dialog
		return outcome;
	}

	/**
	 * Indicate if the finish button must be disbled or not
	 *
	 * @return false all the time
	 */
	@Override
	public boolean getFinishButtonDisabled()
	{
		// Must be only active if we can do the work
		return (getHasTranslationCheckedOut());
	}

	public String getBrowserTitle()
	{
		return Application.getMessage(FacesContext.getCurrentInstance(), "title_new_edition");
	}

	public String getPageIconAltText()
	{
		return Application.getMessage(FacesContext.getCurrentInstance(), "title_new_edition_icon_tooltip");
	}

	public ActionsListWrapper getActionList()
	{
		return null;
	}

	public boolean isCancelButtonVisible()
	{
		return true;
	}

	public boolean isFormProvided()
	{
		return false;
	}

	/**
	 * @return the logService
	 */
	protected final LogService getLogService()
	{
		if(logService == null)
		{
			logService  = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getLogService();
		}
		return logService;
	}

	/**
	 * @param logService the logService to set
	 */
	public final void setLogService(LogService logService)
	{
		this.logService = logService;
	}

	/**
	 * @return the ManagementService
	 */
	protected final ManagementService getManagementService()
	{
		if(managementService == null)
		{
			managementService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getManagementService();
		}
		return managementService;
	}
	private void setLogRecord(Node node)
	{
		logRecord.setDocumentID((Long) getNodeService().getProperty(node.getNodeRef(), ContentModel.PROP_NODE_DBID ));
		final NodeRef igNodeRef = getManagementService().getCurrentInterestGroup(node.getNodeRef());
		logRecord.setIgID((Long) getNodeService().getProperty(igNodeRef, ContentModel.PROP_NODE_DBID )) ;
		logRecord.setIgName((String) getNodeService().getProperty(igNodeRef, ContentModel.PROP_NAME));
		logRecord.setUser(AuthenticationUtil.getFullyAuthenticatedUser());
		Path path = node.getNodePath();
		logRecord.setPath(PathUtils.getCircabcPath(path,true ));
	}
}
