/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.bean.navigation.event;

import java.io.Serializable;
import java.util.Comparator;

import eu.cec.digit.circabc.web.ui.tag.AppointmentUnit;

/**
 * Order events (ideally of the same day) according the start hour
 *
 * @author yanick pignot
 */
public class AppointmentUnitComparator implements Comparator<AppointmentUnit>, Serializable
{
	private static final long serialVersionUID = 6227528170880231785L;

    public int compare(AppointmentUnit a1, AppointmentUnit a2)
    {
    	final int diffDate = a1.getStartDay().compareTo(a2.getStartDay());

    	if(diffDate == 0)
    	{
    		// if same day, compare the time
    		return a1.getStart().compareTo(a2.getStart());
    	}
    	else
    	{
    		return diffDate;
    	}
    }
}
