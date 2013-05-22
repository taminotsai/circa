/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.content.edit;

import java.util.ArrayList;
import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.util.Pair;

import eu.cec.digit.circabc.web.WebClientHelper;
import eu.cec.digit.circabc.web.WebClientHelper.ExtendedURLMode;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;
import eu.cec.digit.circabc.web.bean.override.CircabcNavigationBean;
import eu.cec.digit.circabc.web.wai.dialog.content.edit.CreateContentBaseDialog.AttachementWrapper;

/**
 * @author Yanick Pignot
 *
 */
public abstract class OnlineEditingHelper
{
	private OnlineEditingHelper(){}


	/**
	 * Generate a list of well know urls to be used in a WYSIWYG
	 *
	 * @param navigator
	 * @return
	 */
	public static List<Pair<String, String>> generateLinks(final CreateContentBaseDialog dialog, final CircabcNavigationBean navigator)
	{
		final List<Pair<String, String>> links = new ArrayList<Pair<String,String>>(5);

		links.add(new Pair<String, String>("Europa", "http://www.europa.eu"));

		addLink(links, navigator.getCircabcHomeNode());
		addLink(links, navigator.getCurrentCategory());
		addLink(links, navigator.getCurrentIGRoot());
		addLink(links, navigator.getCurrentIGService());

		if(navigator.isGuest() == false && navigator.getCurrentUser() != null)
		{
			links.add(new Pair<String, String>("My Profile", WebClientHelper.getGeneratedWaiFullUrl(navigator.getCurrentUser().getPerson(), ExtendedURLMode.HTTP_USERDETAILS)));
		}

		addAttachements(dialog, links);

		return links;
	}

	/**
	 * Generate a list of well know urls to be used in a WYSIWYG
	 *
	 * @param navigator
	 * @return
	 */
	public static List<Pair<String, String>> generateMediafiles(final CreateContentBaseDialog dialog)
	{
		final List<Pair<String, String>> links = new ArrayList<Pair<String,String>>(5);

		addAttachements(dialog, links);

		return links;
	}


	private static void addAttachements(final CreateContentBaseDialog dialog, final List<Pair<String, String>> links)
	{
		final List<AttachementWrapper> wrappers = dialog.getAttachementWrappers();

		if(wrappers != null)
		{
			for(final AttachementWrapper wrapper: wrappers)
			{
				addLink(links, wrapper);
			}
		}
	}

	private static void addLink(final List<Pair<String, String>> links, final AttachementWrapper wrapper)
	{
		if(wrapper != null)
		{
			final NodeRef attachRef ;

			if(wrapper.isCreated())
			{
				attachRef = wrapper.getAttachement().getNodeRef();
			}
			else
			{
				attachRef = wrapper.getAttachRef();
			}

			links.add(
					new Pair<String, String>(
							wrapper.getName(),
							WebClientHelper.getGeneratedWaiFullUrl(attachRef, ExtendedURLMode.HTTP_DOWNLOAD))
					);
		}
	}

	private static void addLink(final List<Pair<String, String>> links, final NavigableNode node)
	{
		if(node != null)
		{
			links.add(
					new Pair<String, String>(
							(String) node.get(NavigableNode.BEST_TITLE_RESOLVER_NAME),
							WebClientHelper.getGeneratedWaiFullUrl(node, ExtendedURLMode.HTTP_WAI_BROWSE))
					);
		}
	}

}
