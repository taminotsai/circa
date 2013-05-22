/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import java.util.Set;

import javax.faces.context.FacesContext;

import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.service.profile.CategoryProfileManagerService;
import eu.cec.digit.circabc.service.profile.ProfileManagerServiceFactory;
import eu.cec.digit.circabc.service.profile.permissions.CategoryPermissions;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.bean.override.CircabcNavigationBean;

/**
 * Evaluate if the user can create an interest group.
 *
 * The user must be In profile CategoryAmdin !!!
 *
 * @author Yanick Pignot
 */
public class CreateInterestGroupEvaluator extends BaseActionEvaluator
{
	private static final long serialVersionUID = 308051234539899525L;

	/**
	 * Return false if the given node is a dossier
	 */
	public boolean evaluate(final Node node)
	{

		if(NavigableNodeType.CATEGORY.isNodeFromType(node) == false)
		{
			return false;
		}
		else if(node.hasPermission(CategoryPermissions.CIRCACATEGORYADMIN.toString()) == false)
		{
			return false;
		}
		else
		{
			final ProfileManagerServiceFactory factory = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getProfileManagerServiceFactory();
			final CategoryProfileManagerService profileManager = factory.getCategoryProfileManagerService();

			Set<String> admins = profileManager.getPersonInProfile(node.getNodeRef(), CategoryProfileManagerService.Profiles.CIRCA_CATEGORY_ADMIN);

			return admins.contains(getCurrentUserName());
		}
	}

	private String getCurrentUserName()
	{
		final CircabcNavigationBean navigator = Beans.getWaiNavigator();

		return (navigator.isGuest()) ? null : navigator.getCurrentUser().getUserName();
	}
}
