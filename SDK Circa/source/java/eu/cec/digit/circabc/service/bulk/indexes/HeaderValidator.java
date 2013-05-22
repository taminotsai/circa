package eu.cec.digit.circabc.service.bulk.indexes;

import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessage;

public interface HeaderValidator {
	public boolean validate(final IndexEntry indexRecord);
	public ValidationMessage getValidationMessage();
}
