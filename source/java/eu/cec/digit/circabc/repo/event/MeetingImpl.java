package eu.cec.digit.circabc.repo.event;

import static eu.cec.digit.circabc.model.EventModel.PROP_MEETING_ACCEPTED_USERS;
import static eu.cec.digit.circabc.model.EventModel.PROP_MEETING_AGENDA;
import static eu.cec.digit.circabc.model.EventModel.PROP_MEETING_AVAILABILITY;
import static eu.cec.digit.circabc.model.EventModel.PROP_MEETING_LIBRARY_SECTION;
import static eu.cec.digit.circabc.model.EventModel.PROP_MEETING_ORGAINZATION;
import static eu.cec.digit.circabc.model.EventModel.PROP_MEETING_REJECTED_USERS;
import static eu.cec.digit.circabc.model.EventModel.PROP_MEETING_TYPE;
import static eu.cec.digit.circabc.model.EventModel.PROP_SEQUENCE;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.util.PropertyMap;

import eu.cec.digit.circabc.service.event.AppointmentUpdateInfo;
import eu.cec.digit.circabc.service.event.AudienceStatus;
import eu.cec.digit.circabc.service.event.Meeting;
import eu.cec.digit.circabc.service.event.MeetingAvailability;
import eu.cec.digit.circabc.service.event.MeetingRequestStatus;
import eu.cec.digit.circabc.service.event.MeetingType;

public class MeetingImpl extends AppointmentImpl implements Meeting,Serializable
{


	private static final long serialVersionUID = 5849473709093936742L;

	private String agenda;

	private MeetingAvailability availability;

	private MeetingType meetingType;

	private String organization;

	private NodeRef librarySection;

	private List<String> rejectedUsers;

	private List<String> acceptedUsers;

	private Integer sequence;

	public String getAgenda()
	{

		return agenda;
	}

	public MeetingAvailability getAvailability()
	{

		return availability;
	}

	public String getAvailabilityAsString()
	{

		return availability.toString().toUpperCase();
	}

	public MeetingType getMeetingType()
	{

		return meetingType;
	}

	public String getOrganization()
	{

		return organization;
	}

	public NodeRef getLibrarySection()
	{

		return librarySection;
	}

	public void setAgenda(String value)
	{
		agenda = value;

	}

	public void setAvailability(MeetingAvailability value)
	{
		availability = value;

	}

	public void setMeetingType(MeetingType value)
	{
		meetingType = value;

	}

	public void setOrganization(String value)
	{
		organization = value;

	}

	public void setLibrarySection(NodeRef value)
	{

		librarySection = value;

	}

	@Override
	public PropertyMap getProperties(AppointmentUpdateInfo updateInfo)
	{
		PropertyMap properties = super.getProperties();

		switch (updateInfo)
		{
		case GeneralInformation:
			if (this.getAgenda() != null)
			{
				properties.put(PROP_MEETING_AGENDA, this.getAgenda());
			}
			if (this.getAvailability() != null)
			{
				properties.put(PROP_MEETING_AVAILABILITY, this.getAvailability());
			}
			if (this.getOrganization() != null)
			{
				properties.put(PROP_MEETING_ORGAINZATION, this.getOrganization());
			}
			if (this.getMeetingType() != null)
			{
				properties.put(PROP_MEETING_TYPE, this.getMeetingType());
			}
			break;
		case Audience:
			String acceptedUsersList = this.getAcceptedUsersList();
			if (acceptedUsersList != null && !acceptedUsersList.isEmpty())
			{
				properties.put(PROP_MEETING_ACCEPTED_USERS, acceptedUsersList);
			}

			String rejectedUsersList = this.getRejectedUsersList();
			if (rejectedUsersList != null && !rejectedUsersList.isEmpty())
			{
				properties.put(PROP_MEETING_ACCEPTED_USERS, rejectedUsersList);
			}
			break;

		case ContactInformation:

			break;
		case RelevantSpace:
			if (this.getLibrarySection() != null)
			{
				properties.put(PROP_MEETING_LIBRARY_SECTION, this.getLibrarySection());
			}

			break;

		default:
			break;
		}

		return properties;

	}

	@Override
	public PropertyMap getProperties()
	{
		PropertyMap properties = super.getProperties();

		properties.put(PROP_SEQUENCE, this.getSequence());

		if (this.getAgenda() != null)
		{
			properties.put(PROP_MEETING_AGENDA, this.getAgenda());
		}
		if (this.getAvailability() != null)
		{
			properties.put(PROP_MEETING_AVAILABILITY, this.getAvailability());
		}
		if (this.getOrganization() != null)
		{
			properties.put(PROP_MEETING_ORGAINZATION, this.getOrganization());
		}
		if (this.getMeetingType() != null)
		{
			properties.put(PROP_MEETING_TYPE, this.getMeetingType());
		}
		if (this.getLibrarySection() != null)
		{
			properties.put(PROP_MEETING_LIBRARY_SECTION, this.getLibrarySection());
		}

		if (this.getAcceptedUsersList() != null)
		{
			properties.put(PROP_MEETING_ACCEPTED_USERS, this.getAcceptedUsersList());
		}

		if (this.getRejectedUsersList() != null)
		{
			properties.put(PROP_MEETING_ACCEPTED_USERS, this.getRejectedUsersList());
		}

		return properties;

	}

	public String getRejectedUsersList()
	{
		if (rejectedUsers == null)
			return "";

		StringBuilder result = new StringBuilder(separator);
		for (String user : rejectedUsers)
		{
			result.append(user);
			result.append(separator);
		}
		return result.toString();
	}

	public String getAcceptedUsersList()
	{
		if (acceptedUsers == null)
			return "";

		StringBuilder result = new StringBuilder(separator);
		for (String user : acceptedUsers)
		{
			result.append(user);
			result.append(separator);
		}
		return result.toString();
	}

	@Override
	public void init(Map<QName, Serializable> properties)
	{
		super.init(properties);

		Serializable sequence = properties.get(PROP_SEQUENCE );
		if (sequence !=null)
		{
			this.setSequence(Integer.valueOf(sequence.toString()));
		}

		Serializable agenda = properties.get(PROP_MEETING_AGENDA);
		if (agenda != null)
		{
			this.setAgenda(agenda.toString());
		}
		Serializable availability = properties.get(PROP_MEETING_AVAILABILITY);
		if (availability != null)
		{
			this.setAvailability(MeetingAvailability.valueOf(availability.toString()));
		}
		Serializable organization = properties.get(PROP_MEETING_ORGAINZATION);
		if (organization != null)
		{
			this.setOrganization(organization.toString());
		}
		Serializable type = properties.get(PROP_MEETING_TYPE);
		if (type != null)
		{
			this.setMeetingType(MeetingType.valueOf(type.toString()));
		}
		Serializable library = properties.get(PROP_MEETING_LIBRARY_SECTION);
		if (library != null)
		{
			this.setLibrarySection((NodeRef) library);
		}

		if (this.getAudienceStatus() == AudienceStatus.Closed)
		{
			Serializable propertyAcceptedUsers = properties.get(PROP_MEETING_ACCEPTED_USERS);
			if (propertyAcceptedUsers != null)
			{
				String acceptedUsersList = propertyAcceptedUsers.toString();
				if (acceptedUsersList.length() > 1)
				{
					acceptedUsersList = acceptedUsersList.substring(1, acceptedUsersList.length() - 1);
					String[] acceptedElements = acceptedUsersList.split("\\" + separator);
					this.setAcceptedUsers(Arrays.asList(acceptedElements));
				}
			}

			Serializable propertyRejectedUsers = properties.get(PROP_MEETING_REJECTED_USERS);
			if (propertyRejectedUsers != null)
			{
				String rejectedUsersList = propertyRejectedUsers.toString();
				if (rejectedUsersList.length() > 1)
				{
					rejectedUsersList = rejectedUsersList.substring(1, rejectedUsersList.length() - 1);
					String[] rejectedElements = rejectedUsersList.split("\\" + separator);
					this.setRejectedUsers(Arrays.asList(rejectedElements));
				}
			}

			audience = new HashMap<String, MeetingRequestStatus>();
			for (String user : this.getInvitedUsers())
			{
				if (this.getAcceptedUsersList().indexOf("|" + user) > -1)
				{
					audience.put(user, MeetingRequestStatus.Accepted);
				}
				else if (this.getRejectedUsersList().indexOf("|" + user) > -1)
				{
					audience.put(user, MeetingRequestStatus.Rejected);
				} else
				{
					audience.put(user, MeetingRequestStatus.Pending);
				}

			}

		}

	}

	public void setRejectedUsers(List<String> value)
	{
		rejectedUsers = value;

	}

	public void setAcceptedUsers(List<String> value)
	{
		acceptedUsers = value;

	}

	public Integer getSequence()
	{
		return sequence;
	}

	public void setSequence(Integer value)
	{
		sequence = value;
	}

}
