package eu.cec.digit.circabc.service.bulk.validation;

import java.util.List;

import eu.cec.digit.circabc.service.bulk.indexes.IndexRecord;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessage;

public interface Validation {
	public List<ValidationMessage> validate(final List<IndexRecord> indexRecords);
	public List<ValidationMessage> validate(final IndexRecord indexRecord);
}
