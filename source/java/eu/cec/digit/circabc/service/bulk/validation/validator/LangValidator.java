package eu.cec.digit.circabc.service.bulk.validation.validator;

import java.util.List;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.ml.ContentFilterLanguagesService;
import org.springframework.extensions.surf.util.I18NUtil;

import eu.cec.digit.circabc.service.bulk.indexes.IndexRecord;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessage;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessageImpl;
import eu.cec.digit.circabc.service.bulk.validation.ErrorType;

public class LangValidator extends AbstractIndexValidator {
	private static final String ERROR_DESCRIPTION = "bulk_upload_param_doc_lang";

	public LangValidator(final ServiceRegistry serviceRegistry) {
		super(serviceRegistry);
	}

	public void validate(final IndexRecord indexRecord,
			final List<ValidationMessage> messages) {

		if(indexRecord.getDocLang() != null) {
			final ContentFilterLanguagesService contentFilterLanguagesService = this.serviceRegistry.getContentFilterLanguagesService();
			if(indexRecord.getDocLang() != null &&
			   indexRecord.getDocLang().length() != 0 &&
			   !contentFilterLanguagesService.getFilterLanguages().contains(indexRecord.getDocLang())) {
				final String errorDescription = I18NUtil.getMessage(ERROR_DESCRIPTION);
				final ValidationMessage validationMessage = new ValidationMessageImpl(
					indexRecord.getRowNumber(), indexRecord.getName(), errorDescription, ErrorType.Fatal);
				messages.add(validationMessage);
			}
		}
	}

}
