package eu.cec.digit.circabc.service.bulk.indexes.message;

import eu.cec.digit.circabc.service.bulk.validation.ErrorType;

public class ValidationMessageImpl implements ValidationMessage {
	private int rowNumber;
	private String fileName;
	private String errorDescription;
	private ErrorType errorType;

	public ValidationMessageImpl(final int rowNumber, final String fileName, final String errorDescription, final ErrorType errorType) {
		this.rowNumber = rowNumber;
		this.fileName = fileName;
		this.errorDescription = errorDescription;
		this.errorType = errorType;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public String getFileName() {
		return fileName;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public ErrorType getErrorType() {
		return errorType;
	}





}
