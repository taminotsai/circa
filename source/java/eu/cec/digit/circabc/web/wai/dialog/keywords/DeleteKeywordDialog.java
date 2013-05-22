/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.keywords;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.lock.NodeLockedException;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.keyword.Keyword;
import eu.cec.digit.circabc.service.keyword.KeywordsService;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;


/**
 *	Bean that backs the "Delete Keyword" WAI Dialog.
 *
 * @author Yanick Pignot
 */
public class DeleteKeywordDialog extends BaseWaiDialog
{
	private static final long serialVersionUID = 6677076443571143699L;

	/** Public JSF Bean name */
	public static final String BEAN_NAME = "DeleteKeywordDialog";

	/** Logger (coppepa: logger must be final) */
	private static final Log logger = LogFactory.getLog(DeleteKeywordDialog.class);

	private static final String MSG_ERROR_NODE_LOCKED = "delete_keyword_dialog_error_node_locked";

	private transient KeywordsService keywordsService;
	private Keyword keywordToDelete = null;


	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null)
		{
			final String keyAsString = parameters.get(ManageKeywordsDialog.KEYWORD_PARAMETER);
			keywordToDelete = null;

			if(keyAsString == null)
			{
				throw new IllegalArgumentException("Impossible to delete the keyword if the keyword is not setted in the parameters with the key " + ManageKeywordsDialog.KEYWORD_PARAMETER);
			}

			keywordToDelete = getKeywordsService().buildKeywordWithId(keyAsString);
		}
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		long startTime = 0;

		if(logger.isDebugEnabled())
		{
			startTime = System.currentTimeMillis();
			logger.debug("Trying to delete the keyword " + keywordToDelete.toString() + " ...");
		}

		try
		{
			getKeywordsService().removeKeyword(keywordToDelete);
		}
		catch(NodeLockedException e)
		{
			Utils.addErrorMessage(translate(MSG_ERROR_NODE_LOCKED));

			if(logger.isDebugEnabled())
			{
				logger.debug("Impossible to delete a keyword since documents are locked");
			}

			return null;
		}


		if(logger.isDebugEnabled())
		{
			long endTime = System.currentTimeMillis();
			logger.debug("Time to delete the keyword and to remove from it related documents: " + (endTime - startTime) + "ms");
		}

		return outcome;
	}

	public String getKeywordTranslations()
	{
		String value = null;

		if(keywordToDelete.isKeywordTranslated())
		{
			value = keywordToDelete.getMLValues().getValues().toString();
			value = value.substring(1, value.length()-1);
		}
		else
		{
			value = keywordToDelete.getValue();
		}

		return value;
	}

	public String getBrowserTitle()
	{
		return translate("delete_keyword_dialog_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("delete_keyword_dialog_icon_tooltip");
	}

	/**
	 * @return the keywordsService
	 */
	protected final KeywordsService getKeywordsService()
	{
		if(keywordsService == null)
		{
			keywordsService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getKeywordsService();
		}
		return keywordsService;
	}

	/**
	 * @param keywordsService the keywordsService to set
	 */
	public final void setKeywordsService(KeywordsService keywordsService)
	{
		this.keywordsService = keywordsService;
	}


}
