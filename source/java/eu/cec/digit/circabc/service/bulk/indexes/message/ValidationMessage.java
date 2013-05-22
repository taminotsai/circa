package eu.cec.digit.circabc.service.bulk.indexes.message;

import eu.cec.digit.circabc.service.bulk.validation.ErrorType;

public interface ValidationMessage {
	public int getRowNumber();
	public String getFileName();
	public String getErrorDescription();
	public ErrorType getErrorType();
}
