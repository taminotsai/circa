/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.comparator;

import java.util.Comparator;

import eu.cec.digit.circabc.service.cmr.security.CircabcConstant;
import eu.cec.digit.circabc.service.profile.CircabcRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.IGRootProfileManagerService;

/**
 * @author Clinckart Stephane
 *
 */
public class CircabcProfileStringSort implements Comparator<String>
{

	private CircabcRootProfileManagerService circabcRootProfileManagerService;

	private CircabcProfileStringSort()
	{

	}

	public CircabcProfileStringSort(CircabcRootProfileManagerService circabcRootProfileManagerService)
	{
		this.circabcRootProfileManagerService = circabcRootProfileManagerService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(String profile1, String profile2)
	{
		int pos1 = getPosition(profile1);
		int pos2 = getPosition(profile2);

		if (Integer.MAX_VALUE == pos1)
		{
			if (Integer.MAX_VALUE == pos2)
			{
				// Two dynamic profiles
				return profile1.compareTo(profile2);
			}
		}
		if (pos1 < pos2)
		{
			return -1;
		}
		if (pos1 == pos2)
		{
			return 0;
		} else
			// pos1 > pos2
			return 1;

	}

	private int getPosition(final String profile)
	{
		if (IGRootProfileManagerService.Profiles.IGLEADER.equals(profile))
		{
			return 1;
		}
		if (IGRootProfileManagerService.Profiles.AUTHOR.equals(profile))
		{
			return 2;
		}
		if (IGRootProfileManagerService.Profiles.CONTRIBUTOR.equals(profile))
		{
			return 3;
		}
		if (IGRootProfileManagerService.Profiles.REVIEWER.equals(profile))
		{
			return 4;
		}
		if (IGRootProfileManagerService.Profiles.SECRETARY.equals(profile))
		{
			return 5;
		}
		if (IGRootProfileManagerService.Profiles.ACCESS.equals(profile))
		{
			return 6;
		}
		if (circabcRootProfileManagerService.getAllCircaUsersGroupName().equals(profile))
		{
			return 7;
		}
		if (CircabcConstant.GUEST_AUTHORITY.equals(profile)) {
			return 8;
		}

		return Integer.MAX_VALUE;
	}

}
