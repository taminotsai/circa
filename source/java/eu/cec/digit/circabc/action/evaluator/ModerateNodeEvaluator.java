/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.action.evaluator;

import javax.faces.context.FacesContext;

import org.alfresco.repo.security.authentication.AuthenticationException;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.security.OwnableService;
import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.model.ModerationModel;
import eu.cec.digit.circabc.service.profile.permissions.LibraryPermissions;
import eu.cec.digit.circabc.service.profile.permissions.NewsGroupPermissions;
import eu.cec.digit.circabc.web.Services;

/**
 * Evaluates whether the current user can approve/reject the given node in the moderation process
 *
 * @author yanick pignot
 */
public class ModerateNodeEvaluator extends BaseActionEvaluator
{
	private static final long serialVersionUID = 6112283424092389996L;

	public boolean evaluate(final Node node)
	{
		if(node.hasAspect(ModerationModel.ASPECT_WAITING_APPROVAL))
		{
			if(node.hasAspect(CircabcModel.ASPECT_LIBRARY))
			{
				return node.hasPermission(LibraryPermissions.LIBADMIN.toString()) && isOwner(node) == false;
			}
			else
			{
				return node.hasPermission(NewsGroupPermissions.NWSMODERATE.toString()) && isOwner(node) == false;

			}
		}
		else
		{
			return false;
		}
	}

	/**
	 * @param node
	 * @throws AuthenticationException
	 */
	private boolean isOwner(final Node node) throws AuthenticationException
	{
		final FacesContext fc = FacesContext.getCurrentInstance();
		final ServiceRegistry registry = Services.getAlfrescoServiceRegistry(fc);
		final OwnableService ownableService = registry.getOwnableService();

		final String currentUser = AuthenticationUtil.getFullyAuthenticatedUser();
		final String owner = ownableService.getOwner(node.getNodeRef());

		return owner != null && owner.equals(currentUser);
	}

}
