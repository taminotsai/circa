/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.content;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.version.Version;
import org.alfresco.web.bean.content.VersionedDocumentDetailsDialog;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.service.external.repositories.ExternalRepositoriesManagementService;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * @author yanick pignot
 *
 */
public class ContentHistoryDialog extends BaseWaiDialog
{

	public static final String BEAN_NAME = "CircabcContentHistoryDialog";

	private VersionedDocumentDetailsDialog versionedDocumentDetailsDialog;

	private static final long serialVersionUID = -106348031705869971L;

	private ExternalRepositoriesManagementService externalRepositoriesManagementService = null;

    @Override
    public void init(Map<String, String> parameters)
    {
    	super.init(parameters);
    }


    public void init(ActionEvent event)
    {
    	getVersionedDocumentDetailsDialog().setBrowsingVersion(event);
    }

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		return CircabcNavigationHandler.CLOSE_WAI_DIALOG_OUTCOME;
	}

	@Override
	public boolean isCancelButtonVisible()
	{
		return false;
	}

	@Override
	public String getContainerDescription()
	{
		return translate("version_details_page_subtitle", getName());
	}


	public String getBrowserTitle()
	{
		return translate("version_details_page_title");
	}

	public String getPageIconAltText()
	{
		return getContainerDescription();
	}

	/**
	 * @return the versionedDocumentDetailsDialog
	 */
	protected final VersionedDocumentDetailsDialog getVersionedDocumentDetailsDialog()
	{
		if(versionedDocumentDetailsDialog == null)
		{
			versionedDocumentDetailsDialog = Beans.getVersionedDocumentDetailsDialog();
		}
		return versionedDocumentDetailsDialog;
	}
	/**
	 * @param versionedDocumentDetailsDialog the versionedDocumentDetailsDialog to set
	 */
	public final void setVersionedDocumentDetailsDialog(VersionedDocumentDetailsDialog versionedDocumentDetailsDialog)
	{
		this.versionedDocumentDetailsDialog = versionedDocumentDetailsDialog;
	}

	//// ---- Delegated methods

	/**
	 * @return
	 * @see org.alfresco.web.bean.content.VersionedDocumentDetailsDialog#getFileType32()
	 */
	public String getFileType32()
	{
		return getVersionedDocumentDetailsDialog().getFileType32();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.content.VersionedDocumentDetailsDialog#getFrozenStateDocument()
	 */
	public Node getFrozenStateDocument()
	{
		return getVersionedDocumentDetailsDialog().getFrozenStateDocument();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.content.VersionedDocumentDetailsDialog#getFrozenStateNodeRef()
	 */
	public NodeRef getFrozenStateNodeRef()
	{
		return getVersionedDocumentDetailsDialog().getFrozenStateNodeRef();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.content.VersionedDocumentDetailsDialog#getMultilingualContainerDocument()
	 */
	public Node getMultilingualContainerDocument()
	{
		return getVersionedDocumentDetailsDialog().getMultilingualContainerDocument();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.content.VersionedDocumentDetailsDialog#getName()
	 */
	public String getName()
	{
		return getVersionedDocumentDetailsDialog().getName();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.content.VersionedDocumentDetailsDialog#getTranslations()
	 */
	public List getTranslations()
	{
		return getVersionedDocumentDetailsDialog().getTranslations();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.content.VersionedDocumentDetailsDialog#getUrl()
	 */
	public String getUrl()
	{
		return getVersionedDocumentDetailsDialog().getUrl();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.content.VersionedDocumentDetailsDialog#getVersion()
	 */
	public Version getVersion()
	{
		return getVersionedDocumentDetailsDialog().getVersion();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.content.VersionedDocumentDetailsDialog#getVersionHistory()
	 */
	public List getVersionHistory()
	{
		return getVersionedDocumentDetailsDialog().getVersionHistory();
	}

	/**
	 * @return
	 * @see org.alfresco.web.bean.content.VersionedDocumentDetailsDialog#isEmptyTranslation()
	 */
	public boolean isEmptyTranslation()
	{
		return getVersionedDocumentDetailsDialog().isEmptyTranslation();
	}
	
	public boolean isPublishedInExternalRepository() {
		NodeRef nodeRef = getFrozenStateDocument().getNodeRef();
		return externalRepositoriesManagementService.wasPublishedTo(null, 
					nodeRef.toString());
	}
	
	public Map<String, Map<String, String>> getRepositoriesInfo() {
		
		NodeRef nodeRef = getFrozenStateDocument().getNodeRef();
		
		Map<String, Map<String, String>> data = externalRepositoriesManagementService.
				getRepositoryDataForDocument(nodeRef.toString());
		
		return data;
	}
	
	/**
	 * Sets the value of the externalRepositoriesManagementService
	 * 
	 * @param externalRepositoriesManagementService the externalRepositoriesManagementService to set.
	 */
	public void setExternalRepositoriesManagementService(
			ExternalRepositoriesManagementService externalRepositoriesManagementService) {
		this.externalRepositoriesManagementService = externalRepositoriesManagementService;
	}
}
