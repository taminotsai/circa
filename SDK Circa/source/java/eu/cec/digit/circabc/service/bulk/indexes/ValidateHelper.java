package eu.cec.digit.circabc.service.bulk.indexes;

import java.util.List;
import java.util.Map;

import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessage;

public interface ValidateHelper {
	public void validate(final IndexHeaders index, final Map<IndexEntry, List<ValidationMessage>> indexValidationMessages);
}
