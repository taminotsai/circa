package eu.cec.digit.circabc.repo.event;

import static eu.cec.digit.circabc.model.EventModel.PROP_EVENT_PRIORITY;
import static eu.cec.digit.circabc.model.EventModel.PROP_EVENT_TYPE;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.service.namespace.QName;
import org.alfresco.util.PropertyMap;

import eu.cec.digit.circabc.service.event.AppointmentUpdateInfo;
import eu.cec.digit.circabc.service.event.Event;
import eu.cec.digit.circabc.service.event.EventPriority;
import eu.cec.digit.circabc.service.event.EventType;

public class EventImpl extends AppointmentImpl implements Event
{
	private EventType eventType;
	private EventPriority priority;

	public EventType getEventType() {

		return eventType;
	}

	public EventPriority getPriority() {
		return priority;
	}

	public void setEventType(EventType value) {
		eventType =  value;

	}

	public void setPriority(EventPriority value) {
		priority =  value;

	}
	@Override
	public PropertyMap getProperties()
	{
		PropertyMap properties =  super.getProperties();

		if (this.getPriority() != null)
		{
			properties.put(PROP_EVENT_PRIORITY ,this.getPriority());
		}
		if (this.getEventType() != null)
		{
			properties.put(PROP_EVENT_TYPE ,this.getEventType());
		}


		return properties;

	}

	@Override
	public PropertyMap getProperties(AppointmentUpdateInfo updateInfo)
	{
		PropertyMap properties =  super.getProperties();

		switch (updateInfo) {
		case GeneralInformation:
			if (this.getPriority() != null)
			{
				properties.put(PROP_EVENT_PRIORITY ,this.getPriority());
			}
			if (this.getEventType() != null)
			{
				properties.put(PROP_EVENT_TYPE ,this.getEventType());
			}

			break;

		default:
			break;
		}
		return properties;

	}

	@Override
	public void init(Map<QName, Serializable> properties )
	{
		super.init(properties);

		Serializable eventPriority = properties.get(PROP_EVENT_PRIORITY );
		if (eventPriority != null)
		{
			this.setPriority(EventPriority.valueOf(eventPriority.toString()));
		}
		Serializable propertyEventType = properties.get(PROP_EVENT_TYPE );
		if (propertyEventType != null)
		{
			this.setEventType(EventType.valueOf(propertyEventType.toString()));
		}

	}



}
