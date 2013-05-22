package eu.cec.digit.circabc.service.bulk.validation.validator;

import java.util.List;

import org.alfresco.service.ServiceRegistry;
import org.springframework.extensions.surf.util.I18NUtil;

import eu.cec.digit.circabc.service.bulk.indexes.IndexRecord;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessage;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessageImpl;
import eu.cec.digit.circabc.service.bulk.validation.ErrorType;

/**
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * I18NUtil was moved to Spring.
 * This class seems to be developed for CircaBC
 */
public class NameValidator extends AbstractIndexValidator {
	private static final String EMPTY_FILE_NAME = "";
	private static final String ERROR_DESCRIPTION = "bulk_upload_mandatory_name_not_provided";
	private static final String ERROR_DESCRIPTION1 = "bulk_upload_mandatory_name_not_begin_with_point";

	public NameValidator(final ServiceRegistry serviceRegistry) {
		super(serviceRegistry);
	}

	public void validate(final IndexRecord indexRecord, final List<ValidationMessage> messages) {
		if (indexRecord.getName() == null || indexRecord.getName().length() == 0) {
			//Check if it's not an empty translation
			if(indexRecord.getNoContent() == null || (indexRecord.getNoContent().equals("N"))) {
				final String errorDescription = I18NUtil.getMessage(ERROR_DESCRIPTION);
				final ValidationMessage validationMessage = new ValidationMessageImpl(
					indexRecord.getRowNumber(), EMPTY_FILE_NAME, errorDescription, ErrorType.Fatal);
				messages.add(validationMessage);
			}
		} else {
			//Check it doenst begin with ..
			if (indexRecord.getName().startsWith("..")) {
				final String errorDescription = I18NUtil.getMessage(ERROR_DESCRIPTION1);
				final ValidationMessage validationMessage = new ValidationMessageImpl(
					indexRecord.getRowNumber(), EMPTY_FILE_NAME, errorDescription, ErrorType.Fatal);
				messages.add(validationMessage);
			}
		}
	}

}
