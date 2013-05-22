package eu.cec.digit.circabc.service.bulk.validation.validator;

import java.util.List;

import org.alfresco.service.ServiceRegistry;
import org.springframework.extensions.surf.util.I18NUtil;

import eu.cec.digit.circabc.model.DocumentModel;
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
public class StatusValidator extends AbstractIndexValidator {

	private static final String ERROR_DESCRIPTION_1 = "bulk_upload_status_undefined";
	private static final String ERROR_DESCRIPTION_2 = "bulk_upload_status_invalid";

	public StatusValidator(final ServiceRegistry serviceRegistry) {
		super(serviceRegistry);
	}

	public void validate(final IndexRecord indexRecord,
			final List<ValidationMessage> messages) {
		boolean valid = false;
		if(indexRecord.getStatus().length() == 0) {
			if(indexRecord.getRelTrans() != null && indexRecord.getRelTrans().length() >= 0) {
				//Traduction... no Status provided for a translation (not pivot)
				//This is normal: Do nothing
			} else {
				//Set default value and raise a warning
				indexRecord.setStatus(DocumentModel.STATUS_VALUES.get(0));
				final String errorDescription = I18NUtil.getMessage(ERROR_DESCRIPTION_1);
				final ValidationMessage validationMessage = new ValidationMessageImpl(
						indexRecord.getRowNumber(), indexRecord.getName(), errorDescription, ErrorType.Warning);
				messages.add(validationMessage);
			}
		} else {
			for(final String status : DocumentModel.STATUS_VALUES) {
				if(status.equals(indexRecord.getStatus())) {
					valid = true;
					break;
				}
			}
			if(!valid) {
				final String errorDescription = I18NUtil.getMessage(ERROR_DESCRIPTION_2);
				final ValidationMessage validationMessage = new ValidationMessageImpl(
					indexRecord.getRowNumber(), indexRecord.getName(), errorDescription, ErrorType.Fatal);
				messages.add(validationMessage);
			}
		}
	}

}
