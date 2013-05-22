/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.dialog.sharespace;

import java.io.Serializable;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.business.api.link.InterestGroupItem;
import eu.cec.digit.circabc.web.PermissionUtils;

/**
 * Web side wrapper for Applicant object encapsulation
 *
 * @author Yanick Pignot
 *
 *  TODO since InterestGroupItem object is in the business layer,
 *  		move this logic in the business layer. But we have first refractor PermissionUtils.
 */
public class WebInterestgroupItem implements Serializable
{

	/** */
	private static final long serialVersionUID = -2395746635023897818L;
	final InterestGroupItem interestGroupItem;
	final String igTitle;
	final String permissionTitle;

	/**
	 * @param interestGroupItem
	 */
	/*package*/ WebInterestgroupItem(final InterestGroupItem item, final String igTitle)
	{
		super();
		this.interestGroupItem = item;
		this.igTitle = igTitle;
		this.permissionTitle = PermissionUtils.getPermissionLabel(item.getPermission());
	}

	/**
	 * @return the igTitle
	 */
	public final String getIgTitle()
	{
		return igTitle;
	}

	/**
	 * @return the permissionTitle
	 */
	public final String getPermissionTitle()
	{
		return permissionTitle;
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.sharespace.InterestGroupItem#getId()
	 */
	public NodeRef getId()
	{
		return interestGroupItem.getNodeRef();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.sharespace.InterestGroupItem#getName()
	 */
	public String getName()
	{
		return interestGroupItem.getName();
	}

	/**
	 * @return
	 * @see eu.cec.digit.circabc.service.sharespace.InterestGroupItem#getPermission()
	 */
	public String getPermission()
	{
		return interestGroupItem.getPermission();
	}

}
