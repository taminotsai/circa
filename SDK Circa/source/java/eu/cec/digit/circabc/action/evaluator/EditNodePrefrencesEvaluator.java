/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.web.Services;


/**
 * Evalute if the user have rights (AnyIgServicesAdminEvaluator) to change the node preferences
 * and if the node is a container.
 *
 * @author yanick pignot
 **/


public class EditNodePrefrencesEvaluator extends AnyIgServicesAdminEvaluator {

	private static final long serialVersionUID = -1795135756859512399L;

	public boolean evaluate(final Node node)
	{
		if(super.evaluate(node))
		{
			final QName type = node.getType();
			if(type.equals(ContentModel.TYPE_FOLDER))
			{
				// the most of the cases
				return true;
			}
			else
			{
				final FacesContext context = FacesContext.getCurrentInstance();
				final ServiceRegistry registry = Services.getAlfrescoServiceRegistry(context);
				final DictionaryService dictionaryService = registry.getDictionaryService();


				return dictionaryService.isSubClass(type, ContentModel.TYPE_CONTAINER);
			}
		}
		else
		{
			return false;
		}
	}
}