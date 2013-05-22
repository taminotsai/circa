package eu.cec.digit.circabc.service.bulk.validation;

import java.util.ArrayList;
import java.util.List;

import eu.cec.digit.circabc.service.bulk.indexes.IndexEntry;
import eu.cec.digit.circabc.service.bulk.indexes.IndexRecord;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessage;

public class ValidationImpl implements Validation {

	public List<ValidationMessage> validate(final List<IndexRecord> indexRecords) {
		final List<ValidationMessage> validationMessages = new ArrayList<ValidationMessage>();
		return validationMessages;
	}

	public List<ValidationMessage> validate(final IndexRecord indexRecord) {
		final List<ValidationMessage> validationMessages = new ArrayList<ValidationMessage>();
		return validationMessages;
	}

	public List<ValidationMessage> validate(final IndexEntry indexEntry) {
		final List<ValidationMessage> validationMessages = new ArrayList<ValidationMessage>();



		return validationMessages;
	}

}
