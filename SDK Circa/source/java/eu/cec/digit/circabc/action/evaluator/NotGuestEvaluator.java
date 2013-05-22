/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.web.Beans;

/**
 * We can't email a dossier if user is guest
 *
 * @author Slobodan Filipovic
 */
public class NotGuestEvaluator extends BaseActionEvaluator
{
	private static final long serialVersionUID = 2164369999290163419L;

	public boolean evaluate(final Node node)
	{
		return !Beans.getWaiNavigator().getIsGuest() ;
	}

}
