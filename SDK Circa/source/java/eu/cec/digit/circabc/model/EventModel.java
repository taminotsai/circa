/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.alfresco.service.namespace.QName;

/**
 * It is the model for the dynamic properties specification
 *
 * @author Slobodan Filipovic
 */
public interface EventModel extends BaseCircabcModel {
	/** Circabc Event namespace */
	public static final String CIRCABC_EVENT_MODEL_1_0_URI = CIRCABC_NAMESPACE
			+ "/model/events/1.0";

	/** Circabc event prefix */
	public static final String CIRCABC_EVENT_MODEL_PREFIX = "ce";

	public  static final QName ASSOC_BASE_EVANT_DATE_CONTAINER_CONTAINER  = QName.createQName(CIRCABC_EVENT_MODEL_1_0_URI, "baseEvantDateContainer");


	public static final QName ASSOC_EVENT_DATES = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "eventDatesAssociation");


	/** Circabc event root container */
	public static final QName TYPE_EVENT_DATES_CONTAINER = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "datesContainer");

	public static final QName TYPE_EVENT = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "event");

	/** Circabc  */
	public static final QName TYPE_EVENT_MEETING_DEFINITION = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "meetingDefinition");

	/** Circabc  */
	public static final QName TYPE_EVENT_DEFINITION = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "eventDefinition");

	/** Circabc  */
	public static final QName ASSOC_EVENT = QName.createQName(
			CircabcModel.CIRCABC_CONTENT_MODEL_1_0_URI, "eventAssociation");

	/** Circabc  index */
	public static final QName PROP_EVENT_ABSTRACT = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "abstract");

	/** Circabc  */
	public static final QName PROP_EVENT_AUDIENCE = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "audience");

	/** Circabc  */



	public static final QName PROP_KIND_OF_EVENT = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "kindOfEvent");



	public static final QName PROP_EVENT_LANGUAGE = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "language");

	public static final QName PROP_EVENT_TITLE = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "title");
	public static final QName PROP_EVENT_NAME = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "name");
	public static final QName PROP_EVENT_URL = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "url");
	public static final QName PROP_EVENT_EMAIL = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "email");
	public static final QName PROP_EVENT_PHONE = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "phone");
	public static final QName PROP_EVENT_OCCURENCE_RATE = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "occurenceRate");
	public static final QName PROP_EVENT_DATE= QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "date");
	public static final QName PROP_EVENT_START_DATE= QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "startDate");
	public static final QName PROP_EVENT_TIMEZONE = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "timezone");
	public static final QName PROP_EVENT_START_TIME = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "startTime");
	public static final QName PROP_EVENT_END_TIME = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "endTime");

	public static final QName PROP_EVENT_INVITED_USERS = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "invitedUsers");

	public static final QName PROP_MEETING_ACCEPTED_USERS = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "acceptUserList");

	public static final QName PROP_MEETING_REJECTED_USERS = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "rejectUserList");

	public static final QName PROP_EVENT_INVITATION_MESSAGE = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "invitationMessage");

	public static final QName PROP_EVENT_LOCATION = QName.createQName(
				CIRCABC_EVENT_MODEL_1_0_URI, "location");



	// event specific properties
	public static final QName PROP_EVENT_PRIORITY = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "eventPriority");

	public static final QName PROP_EVENT_TYPE = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "eventType");


	// meeting specific properties

	public static final QName PROP_MEETING_AVAILABILITY = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "availability");

	public static final QName PROP_MEETING_ORGAINZATION = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "orgainzation");

	public static final QName PROP_MEETING_AGENDA = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "agenda");

	public static final QName PROP_MEETING_TYPE = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "meetingType");

	public static final QName PROP_MEETING_LIBRARY_SECTION = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "librarySection");

	public static final QName PROP_WEEK_START_DAY = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "weekStartDay");

	public static final QName PROP_SEQUENCE = QName.createQName(
			CIRCABC_EVENT_MODEL_1_0_URI, "sequence");




	/** The possible values of the timezone */
	public static final List<String> EVENT_TIME_ZONE_CONSTRAINT_VALUES = Collections.unmodifiableList(Arrays.asList(
			"GMT-12", "GMT-11", "GMT-10", "GMT-9", "GMT-8", "GMT-7", "GMT-6",
			"GMT-5", "GMT-4", "GMT-3", "GMT-2", "GMT-1", "GMT", "GMT+1",
			"GMT+2", "GMT+3", "GMT+4", "GMT+5", "GMT+6", "GMT+7", "GMT+8",
			"GMT+9", "GMT+10", "GMT+11" ));

	public static final String[] EVENT_AVAILABILITY_CONSTRAINT_VALUES = {
			"Private", "Public" };

	public static final String[] EVENT_AUDIENCE_CONSTRAINT_VALUES = { "Open",
			"Close" };

	public static final String[] EVENT_PRIORITY_CONSTRAINT_VALUES = { "Low",
			"Medium", "High", "Urgent" };

	public static final String[] EVENT_TYPE_CONSTRAINT_VALUES = {
			"Appointment", "Task", "Other" };

	public static final String[] MEETING_TYPE_CONSTRAINT_VALUES = {
			"FaceToFace", "VirtualMeeting",
			"ElectronicWithConnectixVideoPhone",
			"ElectronicWithEnhancedSeeYouSeeMe",
			"ElectronicWithInternetVideoPhone", "ElectronicWithIntelProshare",
			"ElectronicWithMicrosoftNetMeeting",
			"ElectronicWithNetscapeConference",
			"ElectronicWithNetscapeCooltalk", "ElectronicWithVDOnetVDOPhone",
			"ElectronicWithotherSoftware" };


	public static final List<String> WEEK_START_DAY_CONSTRAINT_VALUES = Collections.unmodifiableList(Arrays.asList(
		"sunday" ,"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "today" )) ;
}
