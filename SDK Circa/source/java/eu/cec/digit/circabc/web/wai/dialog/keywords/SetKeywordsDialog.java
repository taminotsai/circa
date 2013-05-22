/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.keywords;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.alfresco.web.bean.repository.Node;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.model.DocumentModel;
import eu.cec.digit.circabc.service.keyword.Keyword;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;


/**
 *	Bean that backs the "Set Keyword to a document" WAI Dialog.
 *
 * @author Yanick Pignot
 */
public class SetKeywordsDialog extends SelectKeywordsBaseDialog
{
	private static final long serialVersionUID = -2381287486464195753L;

	/** Public JSF Bean name */
	public static final String BEAN_NAME = "SetKeywordsDialog";

	public static final String WAI_DIALOG_CALL = CircabcNavigationHandler.WAI_DIALOG_PREFIX + "setKeywordsDialogWai";

	/** Logger (coppepa: logger must be final) */
	private static final Log logger = LogFactory.getLog(SetKeywordsDialog.class);
	private Node interestGroup = null;

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		final List<Keyword> docKeywordsAsList = new ArrayList<Keyword>(settedKeywords.size());
        docKeywordsAsList.addAll(settedKeywords.values());
        String info = "Trying to set the keywords to the current document : "
			   + "\n   Document: " + getActionNode().getName() +  "(" + getActionNode().getNodeRef() + ")"
			   + "\n   IG      : " + getInterestGroup().getName() +  "(" + getInterestGroup().getNodeRef() + ")"
			   + "\n   Keywords: " + docKeywordsAsList +  "(" + settedKeywords.keySet() + ")";
        logRecord.setInfo(info);
        logRecord.setService("Library");
        logRecord.setActivity("Add keyword to document");

		if(logger.isDebugEnabled())
		{
		   logger.debug(info);
		}

		getKeywordsService().setKeywordsToNode(getActionNode().getNodeRef(), docKeywordsAsList);

		if(logger.isDebugEnabled())
		{
		   logger.debug("Keywords successfully updated.");
		}

		// refresh the edit content properties dialog
		Beans.getEditNodePropertiesDialog().setPropetyDefinedOutside(DocumentModel.PROP_KEYWORD);

		return outcome;
	}

	public String getDialogCloseAndLaunchAction()
	{
		return CircabcNavigationHandler.CLOSE_WAI_DIALOG_OUTCOME + CircabcNavigationHandler.OUTCOME_SEPARATOR + WAI_DIALOG_CALL;
	}

	@Override
	public String getBrowserTitle()
	{
		return translate("set_document_keyword_dialog_browser_title");
	}

	@Override
	public String getPageIconAltText()
	{
		return translate("set_document_keyword_dialog_icon_tooltip");
	}

	@Override
	public Node getInterestGroup()
	{
		if(this.interestGroup == null)
		{
			interestGroup = new Node(getManagementService().getCurrentInterestGroup(getActionNode().getNodeRef()));
		}
		return this.interestGroup;
	}
}
