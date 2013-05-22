package eu.cec.digit.circabc.service.event;
import org.alfresco.service.cmr.repository.NodeRef;
public interface Meeting extends Appointment{

	public MeetingAvailability getAvailability();
	public String getOrganization();
	public String getAgenda();
	public MeetingType getMeetingType();
	public NodeRef getLibrarySection();
	public Integer getSequence();

	public void setAvailability(MeetingAvailability value);
	public void setOrganization(String value);
	public void setAgenda(String value);
	public void setMeetingType(MeetingType value);
	public void setLibrarySection(NodeRef value);
	public void setSequence(Integer value);


}
