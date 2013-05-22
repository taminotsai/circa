package eu.cec.digit.circabc.service.event;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.namespace.QName;
import org.alfresco.util.PropertyMap;
import org.joda.time.TimeOfDay;

import com.google.ical.values.DateValue;

public interface Appointment {

	String getId();

	String getLanguage();

	String getTitle();

	String getEventAbstract();

	DateValue getDate();

	Date getDateAsDate();

	DateValue getStartDate();

	Date getStartDateAsDate();

	OccurenceRate getOccurenceRate();

	TimeOfDay getStartTime();

	Date getStartTimeAsDate();

	TimeOfDay getEndTime();

	Date getEndTimeAsDate();

	String getTimeZoneId();

	String getLocation();

	List<String> getInvitedUsers();

	String getInvitedUsersList();

	String getInvitationMessage();

	AudienceStatus getAudienceStatus();

	String getName();

	String getPhone();

	String getRRule();

	String getEmail();

	String getUrl();

	Boolean getEnableNotification();

	void  setId(String id);

	void setLanguage( String value );

	void setTitle( String value );

	void setEventAbstract(String value);

	void setDateAsDate(Date value);

	void setStartDate(DateValue value);

	void setStartDateAsDate(Date value);

	void setOccurenceRate(OccurenceRate value);

	void setStartTime(TimeOfDay value);

	void setStartTimeAsDate(Date value);

	void setEndTime(TimeOfDay  value);

	void setEndTimeAsDate(Date  value);

	void setTimeZoneId(String value);

	void setLocation(String value);

	void setInvitedUsers(List<String> value);

	void setInvitationMessage(String value);

	void setAudienceStatus(AudienceStatus value);

	void setName(String value);

	void setPhone(String value);

	void setEmail(String value);

	void setUrl(String value);

	void setEnableNotification(Boolean value );

	PropertyMap getProperties();

	PropertyMap getProperties(AppointmentUpdateInfo updateInfo );

	List<PropertyMap> getEventDatesProperties( AppointmentType  appointmentType );

	 void init(Map<QName, Serializable> properties );

	HashMap<String, MeetingRequestStatus > getAudience();

	void addAudience(String  user , MeetingRequestStatus status );

}