/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import java.util.HashMap;

import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.service.event.Appointment;
import eu.cec.digit.circabc.service.event.AudienceStatus;
import eu.cec.digit.circabc.service.event.Meeting;
import eu.cec.digit.circabc.service.event.MeetingRequestStatus;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.bean.override.CircabcNavigationBean;
import eu.cec.digit.circabc.web.wai.bean.navigation.event.AppointmentDetailsBean;


/**
 * Evaluate if the current user can accept a meeting. It means check if it is not already done.
 *
 * @author Yanick Pignot
 */
public class AcceptMeetingEvaluator extends BaseActionEvaluator
{
	private static final long serialVersionUID = 216436852785621419L;

	public boolean evaluate(final Node node)
	{
		final MeetingRequestStatus userStatus = getCurrentUserStatusOnMeeting(node);

		if(userStatus == null)
		{
			return false;
		}
		else
		{
			return !userStatus.equals(MeetingRequestStatus.Accepted);
		}
	}

	protected MeetingRequestStatus getCurrentUserStatusOnMeeting(Node node)
	{
		final Appointment appointment = (Appointment) node.getProperties().get(AppointmentDetailsBean.PROPERTY_APPOINTEMENT_OBJECT);

		if(isClosedMeeting(appointment))
		{
			final HashMap<String, MeetingRequestStatus> statuses = appointment.getAudience();
			final String userName = getCurrentUserName();

			if(userName == null || !statuses.containsKey(userName))
			{
				return null;
			}
			else
			{
				return	statuses.get(userName);
			}
		}
		else
		{
			return null;
		}
	}

	private String getCurrentUserName()
	{
		final CircabcNavigationBean navigator = Beans.getWaiNavigator();

		return (navigator.isGuest()) ? null : navigator.getCurrentUser().getUserName();
	}

	private boolean isClosedMeeting(Appointment appointment)
	{
		return appointment != null && appointment instanceof Meeting && AudienceStatus.Closed.equals(appointment.getAudienceStatus());
	}
}
