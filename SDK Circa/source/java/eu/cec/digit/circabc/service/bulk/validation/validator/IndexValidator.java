package eu.cec.digit.circabc.service.bulk.validation.validator;

import java.util.List;

import eu.cec.digit.circabc.service.bulk.indexes.IndexRecord;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessage;

public interface IndexValidator {
	public void validate(final IndexRecord indexRecord, final List<ValidationMessage> messages);
}
