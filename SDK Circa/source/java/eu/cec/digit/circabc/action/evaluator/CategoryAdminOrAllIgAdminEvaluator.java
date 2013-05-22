/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.service.profile.permissions.CategoryPermissions;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.repository.CategoryNode;


/**
 * Due to a lack of the Alfresco security model, this evaluator tests if the current user is
 * administrator on <b>each</b> ig services or CategorAdmin.
 *
 * @author Stephane Clinckart
 **/
public class CategoryAdminOrAllIgAdminEvaluator extends AllIgServicesAdminEvaluator {



	/**
	 *
	 */
	private static final long serialVersionUID = 5785343930963034329L;

	public boolean evaluate(final Node node)
	{
		boolean isAdmin = false;
		final CategoryNode categoryNode = (CategoryNode)Beans.getWaiNavigator().getCurrentCategory();
		if(categoryNode != null && categoryNode.getNodeRef().equals(node.getNodeRef()))
		{
			//Current node is the category
			//Chek if current user is categoryAdmin
			isAdmin = categoryNode.hasPermission(CategoryPermissions.CIRCACATEGORYADMIN.toString());
		}

		if(!isAdmin)
		{
			isAdmin = super.evaluate(node);
		}
		return isAdmin;
	}
}