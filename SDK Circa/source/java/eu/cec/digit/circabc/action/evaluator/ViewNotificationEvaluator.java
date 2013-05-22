/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.LIBRARY_DOSSIER;

import javax.faces.context.FacesContext;

import org.alfresco.model.ApplicationModel;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.web.Services;

/**
 * Evaluate if the notification can be setted on the current node.
 *
 * @author Yanick Pignot
 */
public class ViewNotificationEvaluator extends BaseActionEvaluator
{
	private static final long serialVersionUID = 308051234539899525L;

	/**
	 * Return false if the given node is a dossier
	 */
	public boolean evaluate(final Node node)
	{
		final DictionaryService dService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getDictionaryService();
		final QName type = node.getType();

		if( ApplicationModel.TYPE_FILELINK.equals(type) ||
			ApplicationModel.TYPE_FOLDERLINK.equals(type) ||
			dService.isSubClass(type, ApplicationModel.TYPE_FILELINK) ||
			dService.isSubClass(type, ApplicationModel.TYPE_FOLDERLINK) ||
			LIBRARY_DOSSIER.isNodeFromType(node))
		{
			return false;
		}
		else
		{
			return true ;
		}
	}
}
