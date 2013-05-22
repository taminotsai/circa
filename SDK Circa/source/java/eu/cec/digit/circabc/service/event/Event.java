package eu.cec.digit.circabc.service.event;





public interface Event extends Appointment {

	public EventType getEventType();
	public void setEventType(EventType value);

	public EventPriority getPriority();
	public void setPriority( EventPriority value);

}
