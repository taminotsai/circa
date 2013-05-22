package eu.cec.digit.circabc.service.bulk.indexes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessage;

public class ValidateHelperImpl implements ValidateHelper {

	public void validate(final IndexHeaders index, final Map<IndexEntry, List<ValidationMessage>> indexValidationMessages) {
		//final List<IndexRecord> indexRecords = index.getIndexRecords();
		final List<IndexEntry> indexRecords = Collections.<IndexEntry>emptyList();
		List<HeaderValidator> headerValidators;
		for(final IndexEntry indexRecord : indexRecords) {
			headerValidators = index.getHeader(indexRecord.getHeaderName()).getHeaderValidators();
			validate(indexRecord, headerValidators, indexValidationMessages);
		}

	}

	private void validate(final IndexEntry indexRecord, final List<HeaderValidator> headerValidators, final Map<IndexEntry, List<ValidationMessage>> indexValidationMessages) {
		boolean validate;
		for(HeaderValidator headerValidator : headerValidators) {
			validate = headerValidator.validate(indexRecord);
			if(!validate) {
				if(indexValidationMessages.containsKey(indexRecord)) {
					indexValidationMessages.get(indexRecord).add(headerValidator.getValidationMessage());
				} else {
					final List<ValidationMessage> validationMessages = new ArrayList<ValidationMessage>();
					validationMessages.add(headerValidator.getValidationMessage());
					indexValidationMessages.put(indexRecord, validationMessages);
				}
			}
		}
	}
}
