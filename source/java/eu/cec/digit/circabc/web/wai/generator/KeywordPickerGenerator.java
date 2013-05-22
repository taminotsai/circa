/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.generator;

import java.util.List;

import javax.faces.context.FacesContext;

import org.alfresco.web.app.Application;

import eu.cec.digit.circabc.service.keyword.Keyword;
import eu.cec.digit.circabc.service.keyword.KeywordsService;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.wai.dialog.keywords.SetKeywordsDialog;

/**
 * Generates a specify keyword on a document generator.
 *
 * @author Yanick Pignot
 */
public class KeywordPickerGenerator extends BaseDialogLauncherGenerator
{
	private static final String MSG_PICK_VALUE = "keywords_property_pick";
	private static final String MSG_PICK_TOOLTIP = "keywords_property_pick_tooltip";

    private static final String ACTION = SetKeywordsDialog.WAI_DIALOG_CALL;

	@Override
	protected String getTextContent(FacesContext context, String id)
	{
		final KeywordsService keywordService = Services.getCircabcServiceRegistry(context).getKeywordsService();
		final List<Keyword> keywords = keywordService.getKeywordsForNode(getNode().getNodeRef());
		final StringBuffer buff = new StringBuffer("");

		boolean first = true;

		for (Keyword keyword : keywords)
		{
			if(first)
			{
				first = false;
			}
			else
			{
				buff.append(", ");
			}

			if(keyword.isKeywordTranslated())
			{
				buff.append(keyword.getMLValues().getDefaultValue());
			}
			else
			{
				buff.append(keyword.getValue());
			}
		}

		return buff.toString();
	}


	@Override
	protected String getAction(FacesContext context, String id)
	{
		return ACTION;
	}


	@Override
	protected String getActionTooltip(FacesContext context, String id)
	{
		return Application.getMessage(context, MSG_PICK_TOOLTIP);
	}


	@Override
	protected String getActionValue(FacesContext context, String id)
	{
		return Application.getMessage(context, MSG_PICK_VALUE);
	}
}
