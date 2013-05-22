package eu.cec.digit.circabc.service.bulk.validation.validator;

import java.util.List;

import org.alfresco.service.ServiceRegistry;

import eu.cec.digit.circabc.service.bulk.indexes.IndexRecord;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessage;

public class Attr3Validator extends AbstractIndexValidator {

	public Attr3Validator(final ServiceRegistry serviceRegistry) {
		super(serviceRegistry);
	}

	public void validate(final IndexRecord indexRecord,
			final List<ValidationMessage> messages) {
		// No validations defined yet
	}

}
