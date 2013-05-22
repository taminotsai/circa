/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.model.ApplicationModel;
import org.alfresco.model.ContentModel;
import org.alfresco.model.WCMAppModel;
import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.model.DocumentModel;

/**
 * Test if the document is inline editable
 *
 * @author Yanick Pignot
 */
public class EditInlineDocumentEvaluator extends BaseActionEvaluator
{
	private static final long serialVersionUID = 2888369999290163419L;

	public boolean evaluate(final Node node)
	{
		if (node.hasAspect(WCMAppModel.ASPECT_FORM_INSTANCE_DATA)
				|| node.isLocked()
				|| node.hasAspect(DocumentModel.ASPECT_URLABLE)
				|| node.hasAspect(ContentModel.ASPECT_MULTILINGUAL_EMPTY_TRANSLATION))
		{
			return false;
		}
		else
		{
			return node.hasAspect(ApplicationModel.ASPECT_INLINEEDITABLE) &&
						node.getProperties().get(ApplicationModel.PROP_EDITINLINE.toString()) != null &&
							((Boolean)node.getProperties().get(ApplicationModel.PROP_EDITINLINE.toString())).booleanValue() == true;
		}

	}

}
