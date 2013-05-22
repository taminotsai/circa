package eu.cec.digit.circabc.service.event;

import java.util.Date;

import org.alfresco.service.cmr.repository.NodeRef;
import org.joda.time.TimeOfDay;

public class EventItem {



	private NodeRef eventNodeRef;
	private String  interestGroup;
	private String  interestGroupTitle;
	private String 	title;
	private Date 	date;
	private String  contact;
	private String  meetingStatus;
	private AppointmentType  eventType;
	private TimeOfDay  startTime;
	private TimeOfDay  endTime;
	/**
	 * @param eventNodeRef the eventNodeRef to set
	 */
	public void setEventNodeRef(NodeRef eventNodeRef) {
		this.eventNodeRef = eventNodeRef;
	}
	/**
	 * @return the eventNodeRef
	 */
	public NodeRef getEventNodeRef() {
		return eventNodeRef;
	}
	/**
	 * @param interestGroup the interestGroup to set
	 */
	public void setInterestGroup(String interestGroup) {
		this.interestGroup = interestGroup;
	}
	/**
	 * @return the interestGroup
	 */
	public String getInterestGroup() {
		return interestGroup;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * @param status the status to set
	 */
	public void setMeetingStatus(String status) {
		this.meetingStatus = status;
	}
	/**
	 * @return the status
	 */
	public String getMeetingStatus() {
		return meetingStatus;
	}
	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(AppointmentType eventType) {
		this.eventType = eventType;
	}
	/**
	 * @return the eventType
	 */
	public AppointmentType getEventType() {
		return eventType;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(TimeOfDay startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the startTime
	 */
	public TimeOfDay getStartTime() {
		return startTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(TimeOfDay endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the endTime
	 */
	public TimeOfDay getEndTime() {
		return endTime;
	}
	/**
	 * @return the interestGroupTitle
	 */
	public final String getInterestGroupTitle()
	{
		return (interestGroupTitle == null || interestGroupTitle.length() < 1) ? interestGroup : interestGroupTitle;
	}
	/**
	 * @param interestGroupTitle the interestGroupTitle to set
	 */
	public final void setInterestGroupTitle(String interestGroupTitle)
	{
		this.interestGroupTitle = interestGroupTitle;
	}

	/**
	 * @return the interestGroup50
	 */
	public String getInterestGroup50() {
		if (interestGroup.length() > 50)
		{
			return interestGroup.substring(0,50);
		}
		else
		{
			return interestGroup;
		}
	}

	/**
	 * @return the title
	 */
	public String getTitle50() {
		if (title.length() > 50)
		{
			return title.substring(0,50);
		}
		else
		{
			return title;
		}
	}
}
