package eu.cec.digit.circabc.service.bulk.validation.validator;

import java.util.List;

import org.alfresco.service.ServiceRegistry;

import eu.cec.digit.circabc.service.bulk.indexes.IndexRecord;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessage;

public class KeywordsValidator extends AbstractIndexValidator {

	public KeywordsValidator(final ServiceRegistry serviceRegistry) {
		super(serviceRegistry);
	}

	public void validate(final IndexRecord indexRecord,
			final List<ValidationMessage> messages) {
		// No validations defined yet
	}

}
