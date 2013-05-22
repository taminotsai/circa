package eu.cec.digit.circabc.service.bulk.validation.validator;

import java.util.List;

import org.alfresco.service.ServiceRegistry;
import org.springframework.extensions.surf.util.I18NUtil;

import eu.cec.digit.circabc.service.bulk.indexes.IndexRecord;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessage;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessageImpl;
import eu.cec.digit.circabc.service.bulk.validation.ErrorType;

public class OverwriteValidator extends AbstractIndexValidator {
	private static final String ERROR_DESCRIPTION = "bulk_upload_overwrite";

	public OverwriteValidator(final ServiceRegistry serviceRegistry) {
		super(serviceRegistry);
	}

	public void validate(final IndexRecord indexRecord,
			final List<ValidationMessage> messages) {
		final String overwrite = indexRecord.getOverwrite();
		if (overwrite != null && overwrite.length() > 0) {
			if (!(overwrite.equals("Y") || overwrite.equals("N"))) {
				final String errorDescription = I18NUtil
						.getMessage(ERROR_DESCRIPTION);
				final ValidationMessage validationMessage = new ValidationMessageImpl(
						indexRecord.getRowNumber(), indexRecord.getName(),
						errorDescription, ErrorType.Fatal);
				messages.add(validationMessage);
			}
		}
	}

}
