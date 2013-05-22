/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.service.event.MeetingRequestStatus;


/**
 * Evaluate if the current user can reject a meeting. It means check if it is not already done.
 *
 * @author Yanick Pignot
 */
public class DeclineMeetingEvaluator extends AcceptMeetingEvaluator
{
	private static final long serialVersionUID = -216436852785621419L;
	public boolean evaluate(final Node node)
	{
		final MeetingRequestStatus userStatus = getCurrentUserStatusOnMeeting(node);

		if(userStatus == null)
		{
			return false;
		}
		else
		{
			return !userStatus.equals(MeetingRequestStatus.Rejected);
		}

	}

}
