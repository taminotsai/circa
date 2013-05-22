package eu.cec.digit.circabc.service.bulk.validation.validator;

import java.util.List;

import org.alfresco.service.ServiceRegistry;
import org.springframework.extensions.surf.util.I18NUtil;

import eu.cec.digit.circabc.model.DocumentModel;
import eu.cec.digit.circabc.service.bulk.indexes.IndexRecord;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessage;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessageImpl;
import eu.cec.digit.circabc.service.bulk.validation.ErrorType;

public class SecurityRankingValidator extends AbstractIndexValidator {

	private static final String ERROR_DESCRIPTION = "bulk_upload_secrank_invalid";

	public SecurityRankingValidator(final ServiceRegistry serviceRegistry) {
		super(serviceRegistry);
	}

	public void validate(final IndexRecord indexRecord,
			final List<ValidationMessage> messages) {

		final String currentSecurityRanking = indexRecord.getSecurityRanking();
		if(currentSecurityRanking != null && currentSecurityRanking.length() > 0) {
			boolean valid = false;
			for(final String securityRanking : DocumentModel.SECURITY_RANKINGS) {
				if(securityRanking.equals(currentSecurityRanking)) {
					valid = true;
					break;
				}
			}
			if(!valid) {
				final String errorDescription = I18NUtil.getMessage(ERROR_DESCRIPTION);
				final ValidationMessage validationMessage = new ValidationMessageImpl(
					indexRecord.getRowNumber(), indexRecord.getName(), errorDescription, ErrorType.Fatal);
				messages.add(validationMessage);
			}
		}
	}

}
