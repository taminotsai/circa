/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.service.profile.permissions.CategoryPermissions;
import eu.cec.digit.circabc.service.profile.permissions.DirectoryPermissions;
import eu.cec.digit.circabc.service.profile.permissions.EventPermissions;
import eu.cec.digit.circabc.service.profile.permissions.InformationPermissions;
import eu.cec.digit.circabc.service.profile.permissions.LibraryPermissions;
import eu.cec.digit.circabc.service.profile.permissions.NewsGroupPermissions;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.repository.CategoryNode;
import eu.cec.digit.circabc.web.repository.IGServicesNode;
import eu.cec.digit.circabc.web.repository.InterestGroupNode;


/**
 * Due to a lack of the Alfresco security model, this evaluator tests if the current user is
 * administrator on <b>at least one</b> ig services.
 *
 * @author yanick pignot
 **/


public class AnyIgServicesAdminEvaluator extends BaseActionEvaluator {

	private static final long serialVersionUID = -1795135756856439999L;

	public boolean evaluate(final Node node) {

		final InterestGroupNode ig = (InterestGroupNode) Beans.getWaiNavigator().getCurrentIGRoot();
		final CategoryNode cat = (CategoryNode) Beans.getWaiNavigator().getCurrentCategory();

		if(cat != null && cat.hasPermission(CategoryPermissions.CIRCACATEGORYADMIN.toString()))
		{
			return true;
		}
		else if(ig == null)
		{
			return false;
		}

		final IGServicesNode directory = ig.getDirectory();
		if(directory != null && directory.hasPermission(DirectoryPermissions.DIRADMIN.toString()))
		{
			return true;
		}

		final IGServicesNode library = ig.getLibrary();
		if(library != null && library.hasPermission(LibraryPermissions.LIBADMIN.toString()))
		{
			return true;
		}

		final IGServicesNode newsgroup = ig.getNewsgroup();
		if(newsgroup != null && newsgroup.hasPermission(NewsGroupPermissions.NWSADMIN.toString()))
		{
			return true;
		}

		final IGServicesNode event = ig.getEvent();
		if(event != null && event.hasPermission(EventPermissions.EVEADMIN.toString()))
		{
			return true;
		}

		final IGServicesNode information = ig.getInformation();
		if(information != null && information.hasPermission(InformationPermissions.INFADMIN.toString()))
		{
			return true;
		}

		return false;
	}
}