/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web;

import java.util.ArrayList;
import java.util.List;

import org.alfresco.web.ui.common.component.UIListItem;

import eu.cec.digit.circabc.business.api.space.ContainerIcon;

/**
 * Contains Common jsf convertion methds
 *
 * @author Yanick Pignot
 */
public abstract class JSFUtils
{
	private JSFUtils(){}


	/**
	 * Convert a list of logo to JSF selectable items
	 *
	 * @param containerIcons
	 * @return
	 */
	public static List<UIListItem> convertLogos(final List<ContainerIcon> containerIcons)
	{
		final List<UIListItem> icons = new ArrayList<UIListItem>(containerIcons.size());
		UIListItem item;
		for(final ContainerIcon containerIcon: containerIcons)
		{
			item = new UIListItem();
			item.setValue(containerIcon.getIconName());
			item.setImage(containerIcon.getIconPath());
			icons.add(item);
		}
		return icons;
	}
}
