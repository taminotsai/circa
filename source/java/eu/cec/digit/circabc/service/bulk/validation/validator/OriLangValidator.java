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
public class OriLangValidator extends AbstractIndexValidator {
	private static final String ERROR_DESCRIPTION = "bulk_upload_origlang_invalid";
	private static final String ERROR_DESCRIPTION1 = "bulk_upload_origlang_lang_not_specified";

	public OriLangValidator(final ServiceRegistry serviceRegistry) {
		super(serviceRegistry);
	}

	public void validate(final IndexRecord indexRecord,
			final List<ValidationMessage> messages) {
		final String origLang = indexRecord.getOriLang();
		if (origLang != null && origLang.length() > 0) {
			if (!(origLang.equals("Y") || origLang.equals("N"))) {
				final String errorDescription = I18NUtil
						.getMessage(ERROR_DESCRIPTION);
				final ValidationMessage validationMessage = new ValidationMessageImpl(
						indexRecord.getRowNumber(), indexRecord.getName(),
						errorDescription, ErrorType.Fatal);
				messages.add(validationMessage);
			}

			if (indexRecord.getOriLang().equals("Y") && indexRecord.getDocLang() == null) {
				final String errorDescription = I18NUtil
						.getMessage(ERROR_DESCRIPTION1);
				final ValidationMessage validationMessage = new ValidationMessageImpl(
						indexRecord.getRowNumber(), indexRecord.getName(),
						errorDescription, ErrorType.Fatal);
				messages.add(validationMessage);
			}
		}
	}
}
